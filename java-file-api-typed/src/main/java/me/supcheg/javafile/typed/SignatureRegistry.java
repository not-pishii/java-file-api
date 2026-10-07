package me.supcheg.javafile.typed;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
///   `static`, of other parameters or another result, or that declares a
///   checked exception a method it overrides does not (JLS 8.4.8.3).
///
/// When the declaration is complete ([#requireComplete()]): a method two
/// unrelated supertypes give a body each is overridden (JLS 8.4.8.4,
/// 9.4.1.3 — and stricter than javac where one of the bodies is that of a
/// class), and a class that overrides `equals` overrides `hashCode`.
final class SignatureRegistry {
    private static final MethodSignature EQUALS = new MethodSignature("equals", List.of(ConstantDescs.CD_Object));
    private static final MethodSignature HASH_CODE = new MethodSignature("hashCode", List.of());

    private final String owner;
    private final Inheritance inherited;
    private final Map<MethodSignature, Invocable> methods = new LinkedHashMap<>();
    private final Set<MethodSignature> overrides = new HashSet<>();
    private final Map<List<ClassDesc>, Invocable> constructors = new HashMap<>();
    private final Set<String> fields = new HashSet<>();

    /// @param owner the class being declared, for messages
    /// @param inherited what the class inherits
    SignatureRegistry(String owner, Inheritance inherited) {
        this.owner = owner;
        this.inherited = inherited;
    }

    /// Registers a field.
    ///
    /// @param name the field name
    /// @throws IllegalArgumentException if a field of that name is declared already
    void field(String name) {
        if (!fields.add(name)) {
            throw new IllegalArgumentException("field " + name + " of " + owner
                    + " is already declared: field names are unique in a class" + " (JLS 8.3)");
        }
    }

    /// Registers a method or constructor declared by its name: one that overrides nothing.
    ///
    /// @param member the member being declared
    /// @throws IllegalArgumentException if javac would reject the member, or it would override or
    ///     hide a method the class inherits
    void member(Invocable member) {
        if (member.kind() == InvocableKind.CONSTRUCTOR) {
            List<ClassDesc> params = member.signature().params();
            Invocable clash = constructors.putIfAbsent(params, member);
            if (clash != null) {
                throw new IllegalArgumentException("the constructor " + member + " of " + owner
                        + " clashes with the constructor " + clash + " declared before: both erase to the"
                        + " parameters " + member.signature() + " (JLS 8.8.2)");
            }
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
        methods.put(signature, member);
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
                        + "; a class overrides the methods of the types it extends or implements"));
        requireOverrides(owner, own, group);
        methods.put(signature, own);
        overrides.add(signature);
    }

    private void requireUndeclared(Invocable member, MethodSignature signature) {
        Invocable clash = methods.get(signature);
        if (clash != null) {
            throw new IllegalArgumentException("the method " + member + " of " + owner + " clashes with the method "
                    + clash + " declared before: both erase to " + signature + " (JLS 8.4.2)");
        }
    }

    /// Why a method declared by name cannot have the signature of an inherited one.
    private String declaredOverInherited(Invocable member, Inheritance.Group group) {
        Inheritance.Entry entry = group.entries().getFirst();
        if (member.kind() == InvocableKind.STATIC_METHOD && !entry.isStatic()) {
            return "the static method " + member + " of " + owner + " would hide the instance method " + entry
                    + ", which a static method cannot (JLS 8.4.8.2)";
        }
        return switch (entry.method().dispatch()) {
            case FINAL ->
                "the method " + member + " of " + owner + " would override the final method " + entry
                        + " (JLS 8.4.3.3)";
            case STATIC ->
                "the method " + member + " of " + owner + " has the signature of the static method " + entry
                        + ": the typed layer declares no method that hides or would override a static one"
                        + " (JLS 8.4.8.1, 8.4.8.2)";
            case ABSTRACT, DEFAULT, CONCRETE ->
                "the method " + member + " of " + owner + " has the signature of " + entry
                        + ", which the class inherits: a method declared by its name overrides nothing."
                        + " Override it by its fact — override(fact, body), or overriding(fact) in the header"
                        + " of the class for a method a supertype leaves abstract";
        };
    }

    /// Rejects `own` as an override of the methods of `group`, whose signature it has (JLS 8.4.8.1,
    /// 8.4.8.3).
    ///
    /// @param owner the class being declared, for messages
    /// @param own the method the class declares
    /// @param group the methods of its signature the class inherits
    /// @throws IllegalArgumentException if it cannot override one of them
    static void requireOverrides(String owner, Invocable own, Inheritance.Group group) {
        group.entries()
                .forEach(entry -> overrideRefusal(own, entry).ifPresent(reason -> {
                    throw new IllegalArgumentException(
                            "the method " + own + " of " + owner + " cannot override " + entry + ": " + reason);
                }));
    }

    private static Optional<String> overrideRefusal(Invocable own, Inheritance.Entry entry) {
        Heritage.Method method = entry.method();
        return Stream.<java.util.function.Supplier<Optional<String>>>of(
                        () -> byDispatch(method),
                        () -> byVisibility(method),
                        () -> byForm(method),
                        () -> byParameters(own, method),
                        () -> byResult(own, method),
                        () -> byThrows(own, method))
                .flatMap(refusal -> refusal.get().stream())
                .findFirst();
    }

    private static Optional<String> byDispatch(Heritage.Method method) {
        return switch (method.dispatch()) {
            case FINAL -> Optional.of("the method is final (JLS 8.4.3.3)");
            case STATIC -> Optional.of("an instance method cannot override a static one (JLS 8.4.8.1)");
            case ABSTRACT, DEFAULT, CONCRETE -> Optional.empty();
        };
    }

    private static Optional<String> byVisibility(Heritage.Method method) {
        return switch (method.visibility()) {
            case PACKAGE ->
                Optional.of("the method has package access, and the typed layer does not tell whether the"
                        + " class is in its package (JLS 8.4.8.1)");
            case PUBLIC, PROTECTED -> Optional.empty();
        };
    }

    private static Optional<String> byForm(Heritage.Method method) {
        if (!method.typeParams().isEmpty()) {
            return Optional.of("the method is generic, and the typed layer declares no generic method");
        }
        return switch (method.arity()) {
            case VARIABLE ->
                Optional.of("the method is of variable arity, and the typed layer declares none: javac would"
                        + " warn of an override that is not");
            case FIXED -> Optional.empty();
        };
    }

    private static Optional<String> byParameters(Invocable own, Heritage.Method method) {
        List<TypeRef> params =
                own.params().stream().<TypeRef>map(TypeToken::typeRef).toList();
        return params.equals(method.params())
                ? Optional.empty()
                : Optional.of("its parameters are "
                        + method.params().stream().map(TypeNames::describe).collect(Collectors.joining(", ", "(", ")"))
                        + ", which erase as those of the method do and are not the same: name clash"
                        + " (JLS 8.4.8.3)");
    }

    private static Optional<String> byResult(Invocable own, Heritage.Method method) {
        Optional<TypeRef> result = own.resultType().map(TypeToken::typeRef);
        Optional<TypeRef> overridden =
                switch (method.result()) {
                    case Heritage.Result.Nothing _ -> Optional.empty();
                    case Heritage.Result.Of(TypeRef type) -> Optional.of(type);
                };
        return result.equals(overridden)
                ? Optional.empty()
                : Optional.of(
                        "its result is " + overridden.map(TypeNames::describe).orElse("void")
                                + ", and that of the method "
                                + result.map(TypeNames::describe).orElse("void")
                                + ": the return types are incompatible (JLS 8.4.8.3) — an override has the result of"
                                + " the method it overrides, a narrower one is not supported yet");
    }

    private static Optional<String> byThrows(Invocable own, Heritage.Method method) {
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
    private static boolean declares(Heritage.Method method, ExceptionType thrown) {
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
    /// @throws IllegalStateException if a method two unrelated supertypes give a body each is not
    ///     overridden, or `equals` is overridden and `hashCode` is not
    void requireComplete() {
        inherited
                .groups()
                .filter(group -> group.bodies().size() > 1 && !overrides.contains(group.signature()))
                .findFirst()
                .ifPresent(group -> {
                    throw new IllegalStateException("the declaration of " + owner + " is complete, but it inherits "
                            + group.bodies().stream().map(Object::toString).collect(Collectors.joining(" and "))
                            + ", unrelated methods with a body each, and overrides neither: a class that has"
                            + " a method from several supertypes overrides it (JLS 8.4.8.4, 9.4.1.3)");
                });
        if (overrides.contains(EQUALS) && !overrides.contains(HASH_CODE)) {
            throw new IllegalStateException("the declaration of " + owner + " is complete, and it overrides"
                    + " equals(java.lang.Object) but not hashCode(): equal objects must have equal hash codes,"
                    + " override both (javac -Xlint:overrides)");
        }
    }

    /// The methods of the class: the instance methods declared and those inherited, and the
    /// `static` ones. The class is final, so no method is abstract.
    MethodTable table() {
        Map<Boolean, Set<MethodSignature>> declared = methods.entrySet().stream()
                .collect(Collectors.partitioningBy(
                        e -> e.getValue().kind() == InvocableKind.INSTANCE_METHOD,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toSet())));
        Set<MethodSignature> concrete = Stream.concat(
                        declared.get(true).stream(),
                        inherited
                                .groups()
                                .filter(group -> group.entries().stream().noneMatch(Inheritance.Entry::isStatic))
                                .map(Inheritance.Group::signature))
                .collect(Collectors.toSet());
        return new MethodTable(Set.of(), concrete, declared.get(false));
    }
}
