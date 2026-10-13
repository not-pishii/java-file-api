package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.langmodel.mirror.CtorModel;
import me.supcheg.javafile.langmodel.mirror.MethodModel;
import me.supcheg.javafile.langmodel.mirror.MirrorTranslator;
import me.supcheg.javafile.langmodel.mirror.TypeModel;
import me.supcheg.javafile.type.ArrayTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ExtendsTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.SuperTypeArg;
import me.supcheg.javafile.type.TypeArg;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.Types;
import me.supcheg.javafile.type.UnboundedTypeArg;
import me.supcheg.routine.Either;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.lang.constant.ClassDesc;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// Whether a generated class can extend or implement a type, as the class
/// `X_Heritage` beside its full metamodel tells: by a proof with a step per
/// abstract method of the type ([Offered]), or not at all, and why
/// ([Refused]). A type without the entry is neither extended nor
/// implemented: the typed layer takes the proof alone.
///
/// An entry is offered only where a generator can go all the way: every
/// abstract method has a step a generated method can take, and the fact
/// that names it; a class has a constructor with a fact to call. Whatever
/// the entry is refused for is read off the model of the type
/// ([TypeModel]), which its canonical form tells, and off the facts of its
/// metamodel.
sealed interface Entry permits Entry.Offered, Entry.Refused {

    /// The greatest number of parameters of a method an `ImplementationN` is of.
    int MAX_ARITY = 12;

    /// The type has an entry: `extend` of a class, `implement` of an interface.
    ///
    /// @param self the name of the type variable of the class being declared: `Self`, or with `_` appended
    ///     until no type parameter of the type has it
    /// @param steps the steps, one per abstract method of the type, in the order they are taken: by name
    record Offered(String self, List<Step> steps) implements Entry {
        public Offered {
            steps = List.copyOf(steps);
        }
    }

    /// The type has no entry.
    ///
    /// @param reason why, as the rest of a sentence about the type: `it is sealed: …`
    record Refused(String reason) implements Entry {}

    /// A step of the proof: an abstract method of the type.
    ///
    /// @param name the name of the step, made as that of the fact of a method is: the name of the method,
    ///     and after `_` the types of its parameters
    /// @param index the position of the method among the methods of the heritage of the type: where the
    ///     step reads the signature it takes an implementation of
    /// @param told the method as the heritage tells it, with every declaration
    /// @param params the parameter types, in terms of the type parameters of the type as it names them
    /// @param result the result an implementation has, or a subtype of it: that of the declaration whose
    ///     result suits the others
    record Step(
            String name,
            int index,
            Heritage.Method<Heritage.Declaration> told,
            List<TypeRef> params,
            Heritage.Result<TypeRef> result) {
        public Step {
            params = List.copyOf(params);
        }
    }

    /// The entry of a type.
    ///
    /// @param model the type as it is read, with its members
    /// @param plan the facts of its metamodel
    /// @param elements the element utilities of the compilation
    /// @return the entry, or why there is none
    static Entry of(TypeModel model, MemberPlan plan, Elements elements) {
        return switch (model.heritage()) {
            case Heritage.Untold _ ->
                new Refused(refusal(model)
                        .orElse("a member a class would inherit of it has a signature that cannot be told"));
            case Heritage.Told told ->
                refusal(model)
                        .or(() -> refusal(model, told, plan, elements))
                        .<Entry>map(Refused::new)
                        .orElseGet(() -> steps(model, told, plan, elements));
        };
    }

    private static Optional<String> refusal(TypeModel model) {
        if (!(model.kind() instanceof DeclaredKind.Inheritable)) {
            return Optional.of("it is " + article(model.kind().toString()) + ", which no class extends");
        }
        if (model.sealed()) {
            return Optional.of("it is sealed: only the classes it permits extend or implement it");
        }
        String name = Models.binaryName(model.desc());
        if (name.equals("java.lang.Enum") || name.equals("java.lang.Record")) {
            return Optional.of("no class extends " + name + " but an enum or a record does");
        }
        boolean inner =
                switch (model.nesting()) {
                    case INNER -> true;
                    case TOP_LEVEL, STATIC_MEMBER -> false;
                };
        if (inner) {
            return Optional.of("it is an inner class, which is extended with an instance of the class it is nested in");
        }
        return Optional.empty();
    }

    private static Optional<String> refusal(
            TypeModel model, Heritage.Told heritage, MemberPlan plan, Elements elements) {
        boolean isClass = !(model.kind() instanceof DeclaredKind.Interface);
        if (isClass
                && heritage.constructors().stream().noneMatch(constructor -> switch (constructor.visibility()) {
                    case PUBLIC, PROTECTED -> true;
                    case PACKAGE -> false;
                })) {
            return Optional.of("it has no constructor a class of another package calls");
        }
        if (isClass && plan.models().noneMatch(CtorModel.class::isInstance)) {
            return Optional.of("none of its constructors has a fact, so no generated constructor calls one");
        }
        if (!model.nonPublicBoundTypes().isEmpty()) {
            return Optional.of("a bound of its type parameters mentions a type that is not public: "
                    + String.join(", ", model.nonPublicBoundTypes()));
        }
        return model.typeParams().stream()
                .flatMap(param -> param.bounds().stream())
                .flatMap(Entry::mentions)
                .filter(mentioned -> !nameable(mentioned, elements))
                .map(Models::binaryName)
                .findFirst()
                .map(hidden -> "a bound of its type parameters mentions " + hidden + ", which a class of another"
                        + " package cannot name");
    }

    /// The methods of a heritage a class has to implement, each with its position among the methods.
    private static Stream<Map.Entry<Integer, Heritage.Method<Heritage.Declaration>>> abstractMethods(
            Heritage.Told heritage) {
        return IntStream.range(0, heritage.methods().size())
                .mapToObj(i -> Map.entry(i, heritage.methods().get(i)))
                .filter(method -> switch (method.getValue().standing()) {
                    case ABSTRACT -> true;
                    case DEFAULT, CONCRETE, FINAL, STATIC -> false;
                });
    }

    /// The step of an abstract method, named as its fact is, or why no generated class of another
    /// package implements the method.
    ///
    /// @param qualified whether the classes of the name are with their packages
    private static Either<String, Step> step(
            int index,
            Heritage.Method<Heritage.Declaration> method,
            TypeModel model,
            MemberPlan plan,
            Elements elements,
            boolean qualified) {
        List<Heritage.Declaration> declarations = method.declarations();
        Optional<Heritage.Declarer> hidden = declarations.stream()
                .map(Heritage.Declaration::by)
                .filter(by -> switch (by.visibility()) {
                    case PUBLIC, PROTECTED -> false;
                    case PACKAGE -> true;
                })
                .findFirst();
        if (hidden.isPresent()) {
            return Either.left("has package access in "
                    + Models.binaryName(hidden.get().type())
                    + ", so a class of another package cannot implement it");
        }
        if (declarations.stream()
                .anyMatch(declaration -> !declaration.typeParams().isEmpty())) {
            return Either.left("is generic, and a generated method has no type parameters yet");
        }
        if (declarations.stream().map(Heritage.Declaration::arity).distinct().count() > 1) {
            return Either.left("is of variable arity in one of "
                    + declarations.stream()
                            .map(declaration ->
                                    Models.binaryName(declaration.by().type()))
                            .collect(Collectors.joining(" and "))
                    + " and not in another, so javac warns of whichever a generated method is");
        }
        List<Heritage.Plain> plain = declarations.stream()
                .map(declaration -> declaration.applied(model.typeParams().stream()
                        .<TypeRef>map(param -> Types.typeVar(param.name()))
                        .toList()))
                .map(inherited -> switch (inherited) {
                    case Heritage.Plain told -> told;
                    case Heritage.Generic _ -> throw new IllegalStateException("a generic method was refused");
                })
                .toList();
        Heritage.Plain first = plain.getFirst();
        if (plain.stream().anyMatch(other -> !other.params().equals(first.params()))) {
            return Either.left("is declared with other parameter types by "
                    + declarations.stream()
                            .map(declaration ->
                                    Models.binaryName(declaration.by().type()))
                            .collect(Collectors.joining(" and "))
                    + ", so no one method of a generated class implements each");
        }
        if (first.params().size() > MAX_ARITY) {
            return Either.left("has " + first.params().size() + " parameters, more than " + MAX_ARITY);
        }
        boolean pollutes =
                switch (first.arity()) {
                    case FIXED -> false;
                    case VARIABLE ->
                        first.params().getLast() instanceof ArrayTypeRef array && !reifiable(array.component());
                };
        if (pollutes) {
            return Either.left("is of variable arity with an element type that is not reifiable, so javac warns"
                    + " of heap pollution where a generated method overrides it");
        }
        Optional<String> unnameable = mentions(first)
                .filter(mentioned -> !nameable(mentioned, elements))
                .map(Models::binaryName)
                .findFirst();
        if (unnameable.isPresent()) {
            return Either.left("mentions " + unnameable.get() + ", which a class of another package cannot name");
        }
        // the fact of a method is in the metamodel of a public type that declares it: here, if only the type
        // itself and supertypes that have no metamodel do
        boolean factHere = declarations.stream()
                .map(declaration -> declaration.by().type())
                .allMatch(declarer -> declarer.equals(model.desc()) || !nameable(declarer, elements));
        if (factHere
                && plan.models()
                        .noneMatch(member -> member instanceof MethodModel fact
                                && !fact.isStatic()
                                && fact.name().equals(method.name())
                                && fact.declared().equals(method.signature().params()))) {
            return Either.left("has no fact in the metamodel of the type, so no class says it overrides it");
        }
        return Either.right(
                new Step(stepName(method.name(), first, qualified), index, method, first.params(), first.result()));
    }

    private static Entry steps(TypeModel model, Heritage.Told heritage, MemberPlan plan, Elements elements) {
        List<Either<String, Step>> plain = abstractMethods(heritage)
                .map(method -> step(method.getKey(), method.getValue(), model, plan, elements, false)
                        .mapLeft(why ->
                                "its abstract method " + method.getValue().signature() + " " + why))
                .toList();
        Optional<String> refused =
                plain.stream().flatMap(step -> step.left().stream()).findFirst();
        if (refused.isPresent()) {
            return new Refused(refused.get());
        }
        Map<String, Long> named = plain.stream()
                .flatMap(step -> step.right().stream())
                .collect(Collectors.groupingBy(Step::name, Collectors.counting()));
        // two methods told apart by their packages alone have the classes with their packages
        List<Step> steps = plain.stream()
                .flatMap(step -> step.right().stream())
                .map(step -> named.get(step.name()) > 1
                        ? step(step.index(), step.told(), model, plan, elements, true)
                                .right()
                                .orElseThrow()
                        : step)
                .sorted(Comparator.comparing(Step::name)
                        .thenComparing(step -> step.told().signature().toString()))
                .toList();
        Optional<String> clash =
                steps.stream().collect(Collectors.groupingBy(Step::name, Collectors.counting())).entrySet().stream()
                        .filter(entry -> entry.getValue() > 1)
                        .map(Map.Entry::getKey)
                        .sorted()
                        .findFirst();
        if (clash.isPresent()) {
            return new Refused("two of its abstract methods would have one step, " + clash.get());
        }
        // neither a type parameter of the type nor the type: the stages name both beside the class
        List<String> taken = Stream.concat(
                        model.typeParams().stream().map(TypeParam::name),
                        Stream.of(model.desc().displayName().split("\\$")))
                .toList();
        String self = Stream.iterate("Self", name -> name + "_")
                .filter(name -> !taken.contains(name))
                .findFirst()
                .orElseThrow();
        return new Offered(self, steps);
    }

    /// The name of the step of a method, as [MetamodelNames] names the fact of one: the name of the method
    /// alone if it has no parameters, else with `_` and the type of each — the keyword of a primitive, the
    /// simple name of a class, with the names of the classes it is nested in, the name of a type
    /// variable, `Array` appended for an array. Two methods told apart by their packages alone have the
    /// classes with their packages.
    private static String stepName(String name, Heritage.Plain method, boolean qualified) {
        return Stream.concat(Stream.of(name), method.params().stream().map(param -> suffix(param, qualified)))
                .collect(Collectors.joining("_"));
    }

    private static String suffix(TypeRef type, boolean qualified) {
        return switch (type) {
            case PrimitiveTypeRef primitive -> primitive.name().toLowerCase(Locale.ROOT);
            case ArrayTypeRef array -> suffix(array.component(), qualified) + "Array";
            case TypeVarRef variable -> variable.name();
            case ClassTypeRef cls -> suffix(cls.desc(), qualified);
            case ParameterizedTypeRef parameterized -> suffix(parameterized.raw(), qualified);
        };
    }

    private static String suffix(ClassDesc desc, boolean qualified) {
        String name = desc.displayName().replace('$', '_');
        return qualified && !desc.packageName().isEmpty() ? desc.packageName().replace('.', '_') + "_" + name : name;
    }

    /// Whether a class of another package can name a type: it and the types it is nested in are `public`,
    /// and it is in a named package.
    private static boolean nameable(ClassDesc desc, Elements elements) {
        TypeElement element = elements.getTypeElement(Models.binaryName(desc).replace('$', '.'));
        return element != null && !desc.packageName().isEmpty() && MirrorTranslator.isPublic(element);
    }

    /// The classes and interfaces the signature of a method mentions.
    static Stream<ClassDesc> mentions(Heritage.Plain method) {
        return Stream.of(
                        method.params().stream(),
                        switch (method.result()) {
                            case Heritage.Result.Nothing<TypeRef> _ -> Stream.<TypeRef>empty();
                            case Heritage.Result.Of<TypeRef>(TypeRef type) -> Stream.of(type);
                        },
                        method.throwsTypes().stream().<TypeRef>map(Function.identity()))
                .flatMap(Function.identity())
                .flatMap(Entry::mentions);
    }

    private static Stream<ClassDesc> mentions(TypeRef type) {
        return switch (type) {
            case PrimitiveTypeRef _ -> Stream.empty();
            case TypeVarRef _ -> Stream.empty();
            case ArrayTypeRef array -> mentions(array.component());
            case ClassTypeRef cls -> Stream.of(cls.desc());
            case ParameterizedTypeRef parameterized ->
                Stream.concat(
                        Stream.of(parameterized.raw()),
                        parameterized.args().stream().flatMap(Entry::mentions));
        };
    }

    private static Stream<ClassDesc> mentions(TypeArg argument) {
        return switch (argument) {
            case ExactTypeArg exact -> mentions(exact.type());
            case ExtendsTypeArg bound -> mentions(bound.bound());
            case SuperTypeArg bound -> mentions(bound.bound());
            case UnboundedTypeArg _ -> Stream.empty();
        };
    }

    /// Whether a type is reifiable (JLS 4.7): no type variable, and no parameterization but that of
    /// unbounded wildcards alone.
    private static boolean reifiable(TypeRef type) {
        return switch (type) {
            case PrimitiveTypeRef _ -> true;
            case ClassTypeRef _ -> true;
            case ArrayTypeRef array -> reifiable(array.component());
            case TypeVarRef _ -> false;
            case ParameterizedTypeRef parameterized ->
                parameterized.args().stream().allMatch(UnboundedTypeArg.class::isInstance);
        };
    }

    private static String article(String kind) {
        return (kind.startsWith("e") || kind.startsWith("i") || kind.startsWith("a") || kind.startsWith("o")
                        ? "an "
                        : "a ")
                + kind;
    }
}
