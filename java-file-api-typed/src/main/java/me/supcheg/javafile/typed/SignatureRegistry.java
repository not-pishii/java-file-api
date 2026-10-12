package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.InvocableKind;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.TypeNames;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.TypeVarRef;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// The member signatures of a class being declared (§6.5, §9.7): rejects,
/// when the member is declared, what javac would reject in the rendered
/// class —
///
/// - two methods with the same erased signature (JLS 8.4.2), whatever their
///   return types and whether they are `static`;
/// - two constructors with the same erased parameter types (JLS 8.8.2);
/// - two fields with the same name (JLS 8.3);
/// - a method declared by name that has the erased signature of a method the
///   class inherits ([Inheritance]): a method overrides one only by the fact
///   of the overridden method, so a declaration by name is never an override
///   by accident — nor a `static` method that would hide an instance one
///   (JLS 8.4.8.2), nor an override of a `final` one (JLS 8.4.3.3);
/// - a method that erases as an inherited one of another signature does, or
///   as one that one overrides: the bridge javac makes would be the method
///   (JLS 8.4.8.3);
/// - an override ([#override(Invocable)]) of a method that is `final` or
///   `static`, of other parameters, of a result that is neither that of the
///   method nor proven a subtype of it ([Subtyping]; JLS 8.4.5), or that
///   declares a checked exception a method it overrides does not (JLS
///   8.4.8.3). Each is held against every method the class inherits under
///   the signature, not the one the override names alone.
///
/// When the declaration is complete ([#requireComplete()]): methods the
/// class cannot have together without one of its own — the bodies of two
/// unrelated interfaces, a method of the superclass that cannot stand for the
/// default one of an interface — are overridden (JLS 8.4.8.4, 9.4.1.3), and
/// a class that overrides `equals` overrides `hashCode`.
final class SignatureRegistry {
    private static final MethodSignature EQUALS = new MethodSignature("equals", List.of(ConstantDescs.CD_Object));
    private static final MethodSignature HASH_CODE = new MethodSignature("hashCode", List.of());

    private final String owner;
    private final Inheritance inherited;
    private final List<Subtyping.Bounded> variables;
    private Members members = new Members(List.of(), Set.of(), List.of(), Set.of());

    /// What the class declared so far.
    ///
    /// @param methods the methods, in the order they were declared
    /// @param overrides the signatures of those that override
    /// @param constructors the constructors
    /// @param fields the names of the fields
    private record Members(
            List<Invocable> methods, Set<MethodSignature> overrides, List<Invocable> constructors, Set<String> fields) {
        Members {
            methods = List.copyOf(methods);
            overrides = Set.copyOf(overrides);
            constructors = List.copyOf(constructors);
            fields = Set.copyOf(fields);
        }

        Optional<Invocable> method(MethodSignature signature) {
            return methods.stream()
                    .filter(method -> method.signature().equals(signature))
                    .findFirst();
        }

        Members withMethod(Invocable method) {
            return new Members(with(methods, method), overrides, constructors, fields);
        }

        Members withOverride(Invocable method) {
            return new Members(
                    with(methods, method),
                    Stream.concat(overrides.stream(), Stream.of(method.signature()))
                            .collect(Collectors.toSet()),
                    constructors,
                    fields);
        }

        Members withConstructor(Invocable constructor) {
            return new Members(methods, overrides, with(constructors, constructor), fields);
        }

        Members withField(String name) {
            return new Members(
                    methods,
                    overrides,
                    constructors,
                    Stream.concat(fields.stream(), Stream.of(name)).collect(Collectors.toSet()));
        }

        private static List<Invocable> with(List<Invocable> members, Invocable added) {
            return Stream.concat(members.stream(), Stream.of(added)).toList();
        }
    }

    /// @param owner the class being declared, for messages
    /// @param inherited what the class inherits
    /// @param variables the type parameters of the class, each with its bounds
    SignatureRegistry(String owner, Inheritance inherited, List<Subtyping.Bounded> variables) {
        this.owner = owner;
        this.inherited = inherited;
        this.variables = List.copyOf(variables);
    }

    /// Registers a field.
    ///
    /// @param name the field name
    /// @throws IllegalArgumentException if a field of that name is declared already
    void field(String name) {
        if (members.fields().contains(name)) {
            throw new IllegalArgumentException("field " + name + " of " + owner
                    + " is already declared: field names are unique in a class (JLS 8.3)");
        }
        members = members.withField(name);
    }

    /// Registers a method or constructor declared by its name: one that overrides nothing.
    ///
    /// @param member the member being declared
    /// @throws IllegalArgumentException if javac would reject the member, or it would override or
    ///     hide a method the class inherits
    void member(Invocable member) {
        if (member.kind() == InvocableKind.CONSTRUCTOR) {
            List<ClassDesc> params = member.signature().params();
            members.constructors().stream()
                    .filter(declared -> declared.signature().params().equals(params))
                    .findFirst()
                    .ifPresent(clash -> {
                        throw new IllegalArgumentException("the constructor " + member + " of " + owner
                                + " clashes with the constructor " + clash + " declared before: both erase to"
                                + " the parameters " + member.signature() + " (JLS 8.8.2)");
                    });
            members = members.withConstructor(member);
            return;
        }
        MethodSignature signature = member.signature();
        requireUndeclared(member, signature);
        inherited.group(signature).ifPresent(group -> {
            throw new IllegalArgumentException(declaredOverInherited(member, group));
        });
        inherited.bridged(signature).ifPresent(entry -> {
            throw new IllegalArgumentException("the method " + member + " of " + owner + " clashes with " + entry
                    + ": name clash, it erases as that method or one it overrides does, and does not override"
                    + " it (JLS 8.4.8.3)");
        });
        members = members.withMethod(member);
    }

    /// Registers a method that overrides the methods of its signature the class inherits.
    ///
    /// @param own the method the class declares
    /// @throws IllegalArgumentException if the class inherits no method of that signature, or the
    ///     method cannot override one it inherits
    void override(Invocable own) {
        MethodSignature signature = own.signature();
        requireUndeclared(own, signature);
        Inheritance.Group group = inherited
                .group(signature)
                .orElseThrow(() -> new IllegalArgumentException("the method " + own + " of " + owner
                        + " overrides nothing: no supertype of the class has a method " + signature
                        + "; a class overrides the methods of the types it extends or implements, which its"
                        + " header names before its members begin — extends_ and implements_ come before"
                        + " body()"));
        requireOverrides(owner, own, group, inherited.declaring(own.owner()).knowing(variables));
        members = members.withOverride(own);
    }

    private void requireUndeclared(Invocable member, MethodSignature signature) {
        members.method(signature).ifPresent(clash -> {
            throw new IllegalArgumentException("the method " + member + " of " + owner + " clashes with the method "
                    + clash + " declared before: both erase to " + signature + " (JLS 8.4.2)");
        });
    }

    /// Why a method declared by name cannot have the signature of an inherited one.
    private String declaredOverInherited(Invocable member, Inheritance.Group group) {
        Inheritance.Entry entry = group.stands();
        Heritage.Dispatch standing = group.standing();
        if (member.kind() == InvocableKind.STATIC_METHOD && standing != Heritage.Dispatch.STATIC) {
            return "the static method " + member + " of " + owner + " would hide the instance method " + entry
                    + ", which a static method cannot (JLS 8.4.8.2)";
        }
        return switch (standing) {
            case FINAL ->
                "the method " + member + " of " + owner + " would override the final method " + entry
                        + " (JLS 8.4.3.3)";
            case STATIC ->
                "the method " + member + " of " + owner + " has the signature of the static method " + entry
                        + ", which the class has from its superclass: an instance method cannot override a"
                        + " static one, and the typed layer declares no static method that hides one"
                        + " (JLS 8.4.8.1, 8.4.8.2)";
            case ABSTRACT, DEFAULT, CONCRETE ->
                "the method " + member + " of " + owner + " has the signature of " + entry
                        + ", which the class inherits: a method declared by its name overrides nothing. "
                        + switch (entry.method().declared()) {
                            case Heritage.Plain _ ->
                                "Override it by its fact — override(fact, body), or overriding(fact) in the"
                                        + " header of the class for a method a supertype leaves abstract";
                            case Heritage.Generic _ ->
                                "The inherited method is generic, and the typed layer declares no generic"
                                        + " method: the class neither overrides it nor has another method of"
                                        + " its erased signature";
                        };
        };
    }

    /// Rejects `own` as an override of the methods of `group`, whose signature it has (JLS 8.4.8.1,
    /// 8.4.8.3).
    ///
    /// @param owner the class being declared, for messages
    /// @param own the method the class declares
    /// @param group the methods of its signature the class inherits
    /// @param declaring the class, and what it is a subtype of so far
    /// @throws IllegalArgumentException if it cannot override one of them
    static void requireOverrides(String owner, Invocable own, Inheritance.Group group, Subtyping.Declaring declaring) {
        group.entries()
                .forEach(entry -> overrideRefusal(own, entry, declaring).ifPresent(reason -> {
                    throw new IllegalArgumentException(
                            "the method " + own + " of " + owner + " cannot override " + entry + ": " + reason);
                }));
    }

    /// Rejects `inherited`, a method the class has from its superclass with its body, as the
    /// implementation of the methods of `group` the class has through `through`: what a method of
    /// the class would have to observe to override them, the method of the superclass has to
    /// (JLS 8.4.8.4).
    ///
    /// @param owner the class being declared, for messages
    /// @param inherited the fact of the method of the superclass
    /// @param through the interface whose methods it stands for
    /// @param group the methods of its signature the class inherits
    /// @param declaring the class, and what it is a subtype of so far
    /// @throws IllegalArgumentException if it cannot stand for one of them
    static void requireInherited(
            String owner,
            Invocable inherited,
            DeclaredToken<?> through,
            Inheritance.Group group,
            Subtyping.Declaring declaring) {
        group.entries().stream()
                .filter(entry -> entry.through() == through)
                .forEach(entry -> overrideRefusal(inherited, entry, declaring).ifPresent(reason -> {
                    throw new IllegalArgumentException(owner + " cannot have " + entry + " by inheriting " + inherited
                            + " from its superclass: " + reason);
                }));
    }

    private static Optional<String> overrideRefusal(
            Invocable own, Inheritance.Entry entry, Subtyping.Declaring declaring) {
        Inheritance.Member method = entry.method();
        return Stream.<Supplier<Optional<String>>>of(
                        () -> byDispatch(method), () -> byVisibility(method), () -> switch (method.declared()) {
                            case Heritage.Generic _ ->
                                Optional.of("the method is generic, and the typed layer declares no generic method");
                            case Heritage.Plain plain ->
                                Stream.<Supplier<Optional<String>>>of(
                                                () -> byForm(plain),
                                                () -> byParameters(own, plain),
                                                () -> byResult(own, plain, declaring),
                                                () -> byThrows(own, plain))
                                        .flatMap(refusal -> refusal.get().stream())
                                        .findFirst();
                        })
                .flatMap(refusal -> refusal.get().stream())
                .findFirst();
    }

    private static Optional<String> byDispatch(Inheritance.Member method) {
        return switch (method.dispatch()) {
            case FINAL -> Optional.of("the method is final (JLS 8.4.3.3)");
            case STATIC -> Optional.of("an instance method cannot override a static one (JLS 8.4.8.1)");
            case ABSTRACT, DEFAULT, CONCRETE -> Optional.empty();
        };
    }

    private static Optional<String> byVisibility(Inheritance.Member method) {
        return switch (method.visibility()) {
            case PACKAGE ->
                Optional.of("the method has package access and the class is in its package: whether the class"
                        + " inherits it, and so overrides it, depends on the packages of the classes between"
                        + " (JLS 8.4.8.1), which the typed layer does not tell");
            case PUBLIC, PROTECTED -> Optional.empty();
        };
    }

    private static Optional<String> byForm(Heritage.Plain method) {
        return switch (method.arity()) {
            case VARIABLE ->
                Optional.of("the method is of variable arity, and the typed layer declares none: javac would"
                        + " warn of an override that is not");
            case FIXED -> Optional.empty();
        };
    }

    private static Optional<String> byParameters(Invocable own, Heritage.Plain method) {
        List<TypeRef> params =
                own.params().stream().<TypeRef>map(TypeToken::typeRef).toList();
        return params.equals(method.params())
                ? Optional.empty()
                : Optional.of("its parameters are "
                        + method.params().stream().map(TypeNames::describe).collect(Collectors.joining(", ", "(", ")"))
                        + ", which erase as those of the method do and are not the same: name clash"
                        + " (JLS 8.4.8.3)");
    }

    /// The result of a method is that of the one it overrides, or — a reference type — a subtype of
    /// it the typed layer can prove one ([Subtyping]); `void` answers `void` alone (JLS 8.4.5).
    private static Optional<String> byResult(Invocable own, Heritage.Plain method, Subtyping.Declaring declaring) {
        Optional<TypeToken<?>> result = own.resultType();
        String incompatible = "its result is "
                + switch (method.result()) {
                    case Heritage.Result.Nothing<TypeRef> _ -> "void";
                    case Heritage.Result.Of<TypeRef>(TypeRef type) -> TypeNames.describe(type);
                }
                + ", and that of the method "
                + result.map(type -> TypeNames.describe(type.typeRef())).orElse("void")
                + ": the return types are incompatible (JLS 8.4.8.3)";
        return switch (method.result()) {
            case Heritage.Result.Nothing<TypeRef> _ ->
                result.map(_ -> incompatible + " — a method that overrides a void one is void");
            case Heritage.Result.Of<TypeRef>(TypeRef overridden) ->
                result.map(type -> switch (Subtyping.prove(declaring, type, overridden)) {
                            case Subtyping.Verdict.Proven _ -> Optional.<String>empty();
                            case Subtyping.Verdict.Unproven(var why) ->
                                Optional.of(incompatible + " — the result of an override is that of the method"
                                        + " or a subtype of it the typed layer can prove one, and " + why);
                        })
                        .orElseGet(() -> Optional.of(incompatible + " — a void method overrides a void one alone"));
        };
    }

    private static Optional<String> byThrows(Invocable own, Heritage.Plain method) {
        List<ExceptionType> undeclared = ExceptionType.ofAll(own.traits().throwsTypes()).stream()
                .filter(ExceptionType::isChecked)
                .filter(thrown -> !declares(method, thrown))
                .toList();
        return undeclared.isEmpty()
                ? Optional.empty()
                : Optional.of("the method declares "
                        + (method.throwsTypes().isEmpty()
                                ? "no exception"
                                : method.throwsTypes().stream()
                                        .map(TypeNames::describe)
                                        .collect(Collectors.joining(", ")))
                        + ": an override cannot declare the checked exception "
                        + undeclared.stream().map(Object::toString).collect(Collectors.joining(", "))
                        + " (JLS 8.4.8.3)");
    }

    /// Whether the `throws` clause of `method` covers `thrown`: it is a class of the clause or a
    /// subclass of one. A type variable is known to be neither, and covers nothing.
    private static boolean declares(Heritage.Plain method, ExceptionType thrown) {
        return switch (thrown) {
            case ExceptionType.OfClass(var cls) ->
                method.throwsTypes().stream()
                        .flatMap(SignatureRegistry::thrownClass)
                        .anyMatch(cls::isSubclassOf);
            case ExceptionType.OfVariable _ -> false;
        };
    }

    private static Stream<ClassDesc> thrownClass(ClassOrInterfaceTypeRef thrown) {
        return switch (thrown) {
            case ClassTypeRef cls -> Stream.of(cls.desc());
            case ParameterizedTypeRef parameterized -> Stream.of(parameterized.raw());
            case TypeVarRef _ -> Stream.empty();
        };
    }

    /// Rejects a complete declaration that leaves undone what only a method of the class can do.
    ///
    /// @throws IllegalStateException if the methods the class inherits under a signature cannot be had
    ///     together ([Inheritance.Group#unsettled()]) and the class does not override them, or `equals` is
    ///     overridden and `hashCode` is not
    void requireComplete() {
        inherited
                .groups()
                .filter(group -> !members.overrides().contains(group.signature()))
                .flatMap(group -> group.unsettled().stream())
                .findFirst()
                .ifPresent(why -> {
                    throw new IllegalStateException("the declaration of " + owner + " is complete, but it inherits"
                            + " methods it cannot have together and overrides none of them: " + why
                            + " — a class that has a method from several supertypes that do not agree on it"
                            + " overrides it");
                });
        if (members.overrides().contains(EQUALS) && !members.overrides().contains(HASH_CODE)) {
            throw new IllegalStateException("the declaration of " + owner + " is complete, and it overrides"
                    + " equals(java.lang.Object) but not hashCode(): equal objects must have equal hash codes,"
                    + " override both (javac -Xlint:overrides)");
        }
    }

    /// The methods of the class: the instance methods declared and those inherited, and the
    /// `static` ones, declared and inherited from the superclass — javac takes both kinds for the
    /// candidates of a call on a value of the class (JLS 15.12.2.1). The class is final, so no
    /// method is abstract.
    MethodTable table() {
        Map<Boolean, Set<MethodSignature>> declared = members.methods().stream()
                .collect(Collectors.partitioningBy(
                        method -> method.kind() == InvocableKind.INSTANCE_METHOD,
                        Collectors.mapping(Invocable::signature, Collectors.toSet())));
        Map<Boolean, Set<MethodSignature>> had = inherited
                .groups()
                .collect(Collectors.partitioningBy(
                        group -> switch (group.standing()) {
                            case ABSTRACT, DEFAULT, CONCRETE, FINAL -> true;
                            case STATIC -> false;
                        },
                        Collectors.mapping(Inheritance.Group::signature, Collectors.toSet())));
        return new MethodTable(
                Set.of(),
                Stream.concat(declared.get(true).stream(), had.get(true).stream())
                        .collect(Collectors.toSet()),
                Stream.concat(declared.get(false).stream(), had.get(false).stream())
                        .collect(Collectors.toSet()));
    }
}
