package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.DeclaredKind;
import me.supcheg.javafile.facts.Heritage;
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
import me.supcheg.javafile.type.UnboundedTypeArg;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.lang.constant.ClassDesc;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// Whether a generated class can extend or implement a type, as the class
/// `X_Heritage` beside its full metamodel tells: by a proof with a step per
/// abstract method of the type ([Offered]), or not at all, and why
/// ([Refused]). A type without the entry is neither extended nor
/// implemented: the typed layer takes the proof alone.
sealed interface Entry permits Entry.Offered, Entry.Refused {

    /// The names the class of a heritage uses itself, which a type parameter of the type is not named like.
    Set<String> RESERVED = Set.of(
            "Complete",
            "Steps",
            "RefToken",
            "DeclaredToken",
            "UnsafeFacts",
            "Prim",
            "Signature",
            "Param",
            "Told",
            "Method",
            "Constructor",
            "Supplier",
            "ClassDesc",
            "List",
            "Set",
            "Types",
            "String",
            "Object",
            "Override");

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
    /// @param method the method, as a member of the type
    record Step(String name, Heritage.Method method) {}

    /// The entry of a type.
    ///
    /// @param type the type
    /// @param model the type as it is read, with its members
    /// @param elements the element utilities of the compilation
    /// @return the entry, or why there is none
    static Entry of(TypeElement type, TypeModel model, Elements elements) {
        return refusal(type, model, elements).<Entry>map(Refused::new).orElseGet(() -> steps(model));
    }

    private static Optional<String> refusal(TypeElement type, TypeModel model, Elements elements) {
        if (!(model.kind() instanceof DeclaredKind.Inheritable)) {
            return Optional.of("it is " + article(model.kind().toString()) + ", which no class extends");
        }
        if (model.sealed()) {
            return Optional.of("it is sealed: only the classes it permits extend or implement it");
        }
        String name = type.getQualifiedName().toString();
        if (name.equals("java.lang.Enum") || name.equals("java.lang.Record")) {
            return Optional.of("no class extends " + name + " but an enum or a record does");
        }
        if (type.getNestingKind() == NestingKind.MEMBER && !type.getModifiers().contains(Modifier.STATIC)) {
            return Optional.of("it is an inner class, which is extended with an instance of the class it is nested in");
        }
        return switch (model.heritage()) {
            case Heritage.Untold _ ->
                Optional.of("a member a class would inherit of it has a signature that cannot be told");
            case Heritage.Told told -> refusal(model, told, elements);
        };
    }

    private static Optional<String> refusal(TypeModel model, Heritage.Told heritage, Elements elements) {
        boolean isClass = !(model.kind() instanceof DeclaredKind.Interface);
        if (isClass
                && heritage.constructors().stream().noneMatch(constructor -> switch (constructor.visibility()) {
                    case PUBLIC, PROTECTED -> true;
                    case PACKAGE -> false;
                })) {
            return Optional.of("it has no constructor a class of another package calls");
        }
        if (!model.nonPublicBoundTypes().isEmpty()) {
            return Optional.of("a bound of its type parameters mentions a type that is not public: "
                    + String.join(", ", model.nonPublicBoundTypes()));
        }
        Optional<String> hidden = model.typeParams().stream()
                .flatMap(param -> param.bounds().stream())
                .flatMap(Entry::mentions)
                .filter(mentioned -> !nameable(mentioned, elements))
                .map(Models::binaryName)
                .findFirst();
        if (hidden.isPresent()) {
            return Optional.of("a bound of its type parameters mentions " + hidden.get() + ", which a class of another"
                    + " package cannot name");
        }
        return abstractMethods(heritage)
                .flatMap(method ->
                        unimplementable(method, elements)
                                .map(why -> "its abstract method " + method.signature() + " " + why)
                                .stream())
                .findFirst()
                .or(() -> model.typeParams().stream()
                        .map(TypeParam::name)
                        .filter(classNames(model, heritage)::contains)
                        .findFirst()
                        .map(param -> "its type parameter " + param + " is named like a class the proof mentions"));
    }

    /// The names the class of a heritage starts the name of a class with: of what it is written with, of
    /// the type, and of the classes the bounds of its type parameters and its abstract methods mention.
    private static Set<String> classNames(TypeModel model, Heritage.Told heritage) {
        return Stream.of(
                        RESERVED.stream(),
                        simpleNames(model.desc()),
                        model.typeParams().stream()
                                .flatMap(param -> param.bounds().stream())
                                .flatMap(Entry::mentions)
                                .flatMap(Entry::simpleNames),
                        abstractMethods(heritage).flatMap(Entry::mentions).flatMap(Entry::simpleNames))
                .flatMap(Function.identity())
                .collect(Collectors.toSet());
    }

    private static Stream<Heritage.Method> abstractMethods(Heritage.Told heritage) {
        return heritage.methods().stream().filter(method -> switch (method.dispatch()) {
            case ABSTRACT -> true;
            case DEFAULT, CONCRETE, FINAL, STATIC -> false;
        });
    }

    /// Why no generated class of another package implements an abstract method, if none does.
    private static Optional<String> unimplementable(Heritage.Method method, Elements elements) {
        boolean packageAccess =
                switch (method.visibility()) {
                    case PUBLIC, PROTECTED -> false;
                    case PACKAGE -> true;
                };
        if (packageAccess) {
            return Optional.of("has package access, so a class of another package cannot implement it");
        }
        if (!method.typeParams().isEmpty()) {
            return Optional.of("is generic, and a generated method has no type parameters yet");
        }
        if (method.params().size() > MAX_ARITY) {
            return Optional.of("has " + method.params().size() + " parameters, more than " + MAX_ARITY);
        }
        return mentions(method)
                .filter(mentioned -> !nameable(mentioned, elements))
                .map(Models::binaryName)
                .findFirst()
                .map(hidden -> "mentions " + hidden + ", which a class of another package cannot name");
    }

    private static Entry steps(TypeModel model) {
        Heritage.Told heritage =
                switch (model.heritage()) {
                    case Heritage.Told told -> told;
                    case Heritage.Untold _ -> throw new IllegalStateException(model.desc() + " tells no heritage");
                };
        List<Heritage.Method> methods = abstractMethods(heritage).toList();
        Map<String, Long> plain = methods.stream()
                .collect(Collectors.groupingBy(method -> stepName(method, false), Collectors.counting()));
        List<Step> steps = methods.stream()
                .map(method -> new Step(stepName(method, plain.get(stepName(method, false)) > 1), method))
                .sorted(Comparator.comparing(Step::name)
                        .thenComparing(step -> step.method().signature().toString()))
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
        Set<String> taken = Stream.concat(
                        model.typeParams().stream().map(TypeParam::name), classNames(model, heritage).stream())
                .collect(Collectors.toSet());
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
    private static String stepName(Heritage.Method method, boolean qualified) {
        return Stream.concat(Stream.of(method.name()), method.params().stream().map(param -> suffix(param, qualified)))
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

    private static Stream<String> simpleNames(ClassDesc desc) {
        return Stream.concat(
                Stream.of(desc.displayName().split("\\$")),
                Stream.of(desc.packageName().split("\\.")).limit(1).filter(first -> !first.isEmpty()));
    }

    /// The classes and interfaces the signature of a method mentions.
    static Stream<ClassDesc> mentions(Heritage.Method method) {
        return Stream.of(
                        method.params().stream(),
                        switch (method.result()) {
                            case Heritage.Result.Nothing _ -> Stream.<TypeRef>empty();
                            case Heritage.Result.Of(TypeRef type) -> Stream.of(type);
                        },
                        method.throwsTypes().stream().<TypeRef>map(Function.identity()))
                .flatMap(Function.identity())
                .flatMap(Entry::mentions);
    }

    private static Stream<ClassDesc> mentions(TypeRef type) {
        return switch (type) {
            case PrimitiveTypeRef _, TypeVarRef _ -> Stream.empty();
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

    private static String article(String kind) {
        return (kind.startsWith("e") || kind.startsWith("i") || kind.startsWith("a") || kind.startsWith("o")
                        ? "an "
                        : "a ")
                + kind;
    }
}
