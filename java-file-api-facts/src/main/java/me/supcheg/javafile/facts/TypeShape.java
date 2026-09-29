package me.supcheg.javafile.facts;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeVarRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/// What is known about a declared type apart from its type arguments: its
/// kind, type parameters, superclass chain, parameterized supertypes, method
/// table, enum constants, whether it is `sealed`, and who vouches for all of
/// it (§3.1). A [DeclaredToken] is a shape applied to type arguments, made
/// by the factories of [UnsafeFacts] that take a shape: the one shape of
/// `List<E>` makes the tokens of `List<String>`, `List<? super T>` and the
/// raw `List`.
///
/// A shape vouches for a type, so it is made only by [UnsafeFacts]; its
/// constructors are not public. It is checked for consistency when made —
/// duplicate or undeclared type parameters, a template referring to a type
/// parameter the type does not have, a superclass chain that does not end
/// in `Object`, enum constants of a type that is not an enum, and so on are
/// rejected — but not against the type it describes.
///
/// Shapes are compared by identity: each metamodel holds its type's shape
/// once, and the target-classpath check (§5) is done once per shape.
///
/// @param <K> the kind of the type, which decides its token class
public final class TypeShape<K extends DeclaredKind> {
    private static final ClassDesc CD_ENUM = ClassDesc.of("java.lang.Enum");

    private final ShapeOrigin origin;
    private final K kind;
    private final ClassDesc desc;
    private final List<TypeParam> typeParameters;
    private final List<ClassDesc> boundErasures;
    private final List<ClassDesc> superclasses;
    private final Supertypes supertypes;
    private final Supplier<MethodTableTemplate> methods;
    private final List<String> enumConstants;
    private final boolean sealed;

    TypeShape(
            ShapeOrigin origin,
            K kind,
            ClassDesc desc,
            List<TypeParam> typeParameters,
            List<ClassDesc> superclasses,
            Supertypes supertypes,
            MethodTableTemplate methods,
            List<String> enumConstants,
            boolean sealed) {
        this(origin, kind, desc, typeParameters, superclasses, supertypes, () -> methods, enumConstants, sealed);
        if (methods.typeParameterCount() > this.typeParameters.size()) {
            throw new IllegalArgumentException("the method table of " + this + " refers to type parameter #"
                    + (methods.typeParameterCount() - 1) + ", but the type has " + this.typeParameters.size()
                    + " type parameters");
        }
    }

    /// For a type whose methods are known only later; the template must not
    /// refer to type parameters.
    TypeShape(
            ShapeOrigin origin,
            K kind,
            ClassDesc desc,
            List<TypeParam> typeParameters,
            List<ClassDesc> superclasses,
            Supertypes supertypes,
            Supplier<MethodTableTemplate> methods,
            List<String> enumConstants,
            boolean sealed) {
        this.origin = origin;
        this.kind = kind;
        if (!desc.isClassOrInterface()) {
            throw new IllegalArgumentException("a declared type is a class or interface, got " + desc.displayName());
        }
        this.desc = desc;
        this.typeParameters = List.copyOf(typeParameters);
        this.boundErasures = boundErasures(this.typeParameters);
        this.superclasses = List.copyOf(superclasses);
        this.supertypes = supertypes;
        this.methods = methods;
        this.enumConstants = List.copyOf(enumConstants);
        this.sealed = sealed;
        requireSupertypesOfTheTypeParameters();
        requireSuperclasses();
        requireKindData();
    }

    /// A shape vouched for by a legacy factory of [UnsafeFacts] that takes a
    /// type reference instead of a shape: the type parameters are those of
    /// `supertypes`, or, with none recorded, named `T1`, `T2`, … after the
    /// type arguments of `typeRef`.
    static <K extends DeclaredKind> TypeShape<K> unsafe(
            K kind,
            ClassOrInterfaceTypeRef typeRef,
            List<ClassDesc> superclasses,
            Supertypes supertypes,
            Supplier<MethodTable> methods,
            List<String> enumConstants) {
        ClassDesc desc =
                switch (typeRef) {
                    case ClassTypeRef cls -> cls.desc();
                    case ParameterizedTypeRef parameterized -> parameterized.raw();
                    case TypeVarRef var ->
                        throw new IllegalArgumentException(
                                "a declared type token needs a class or parameterized type, got type variable "
                                        + var.name());
                };
        int arity = typeRef instanceof ParameterizedTypeRef parameterized
                ? parameterized.args().size()
                : 0;
        List<TypeParam> typeParameters;
        if (supertypes.equals(Supertypes.NONE)) {
            typeParameters = IntStream.rangeClosed(1, arity)
                    .mapToObj(i -> new TypeParam("T" + i, List.of()))
                    .toList();
        } else if (supertypes.typeParameters().size() == arity) {
            typeParameters = supertypes.typeParameters().stream()
                    .map(v -> new TypeParam(v.name(), List.of()))
                    .toList();
        } else {
            throw new IllegalArgumentException("the supertypes of " + TypeNames.describe(typeRef) + " are given for "
                    + supertypes.typeParameters().size() + " type parameters, but it has " + arity
                    + " type arguments");
        }
        return new TypeShape<>(
                ShapeOrigin.UNSAFE,
                kind,
                desc,
                typeParameters,
                superclasses,
                supertypes,
                () -> MethodTableTemplate.of(methods.get()),
                enumConstants,
                false);
    }

    /// The shape of a type this module knows by definition.
    static TypeShape<DeclaredKind.FinalClass> builtin(ClassDesc desc, List<ClassDesc> superclasses) {
        return new TypeShape<>(
                ShapeOrigin.BUILTIN,
                DeclaredKind.FINAL_CLASS,
                desc,
                List.of(),
                superclasses,
                Supertypes.NONE,
                MethodTableTemplate.EMPTY,
                List.of(),
                false);
    }

    /// Who vouches for the shape.
    ///
    /// @return the origin
    public ShapeOrigin origin() {
        return origin;
    }

    /// The kind of the type.
    ///
    /// @return the kind
    public K kind() {
        return kind;
    }

    /// The type's class or interface.
    ///
    /// @return the descriptor
    public ClassDesc desc() {
        return desc;
    }

    /// The type parameters of the type, with their bounds; empty for a type
    /// that is not generic.
    ///
    /// @return the type parameters, in declaration order
    public List<TypeParam> typeParameters() {
        return typeParameters;
    }

    /// The erasures of the superclass chain, the direct superclass first and
    /// `java.lang.Object` last; empty for `Object` and for an interface.
    ///
    /// @return the superclass chain
    public List<ClassDesc> superclasses() {
        return superclasses;
    }

    /// The parameterized supertypes of the type, in terms of
    /// [#typeParameters()]; [Supertypes#NONE] when not recorded.
    ///
    /// @return the supertypes
    public Supertypes supertypes() {
        return supertypes;
    }

    /// The methods of the type, instance and `static`, declared and inherited.
    ///
    /// @return the method table template
    /// @throws IllegalStateException if the methods of the type are not known yet:
    ///                               the type is still being declared
    public MethodTableTemplate methods() {
        return methods.get();
    }

    /// The enum constants of the type, in declaration order; empty for a type
    /// that is not an enum.
    ///
    /// @return the constant names
    public List<String> enumConstants() {
        return enumConstants;
    }

    /// Whether the type is `sealed`.
    ///
    /// @return `true` for a sealed class or interface
    public boolean sealed() {
        return sealed;
    }

    /// The type applied to type arguments.
    ///
    /// @param args one argument per type parameter, or none for the raw type
    /// @return the type reference
    /// @throws IllegalArgumentException if `args` is neither empty nor one per type parameter
    ClassOrInterfaceTypeRef typeRef(List<TokenArg> args) {
        if (args.isEmpty()) {
            return Types.of(desc);
        }
        if (args.size() != typeParameters.size()) {
            throw new IllegalArgumentException(TypeNames.describe(desc) + " has " + typeParameters.size()
                    + " type parameters, but " + args.size() + " type arguments are given");
        }
        return new ParameterizedTypeRef(
                desc, args.stream().map(TokenArg::typeArg).toList());
    }

    /// The erasures [#methods()] are instantiated with for `args`: the
    /// erasure of an exact type or an upper bound, otherwise of the bound of
    /// the type parameter, as for the raw type.
    List<ClassDesc> erasures(List<TokenArg> args) {
        if (args.isEmpty()) {
            return boundErasures;
        }
        List<ClassDesc> erasures = new ArrayList<>(args.size());
        for (int i = 0; i < args.size(); i++) {
            erasures.add(
                    switch (args.get(i)) {
                        case TokenArg.Exact(RefToken<?> type) -> type.erasure();
                        case TokenArg.Extends(RefToken<?> bound) -> bound.erasure();
                        case TokenArg.Super ignored -> boundErasures.get(i);
                        case TokenArg.Unbounded ignored -> boundErasures.get(i);
                    });
        }
        return erasures;
    }

    /// The erasures of the type parameters' leftmost bounds.
    List<ClassDesc> boundErasures() {
        return boundErasures;
    }

    @Override
    public String toString() {
        String parameters = typeParameters.isEmpty()
                ? ""
                : typeParameters.stream().map(TypeParam::name).collect(Collectors.joining(", ", "<", ">"));
        return kind + " " + TypeNames.describe(desc) + parameters;
    }

    private static List<ClassDesc> boundErasures(List<TypeParam> typeParameters) {
        Map<String, TypeParam> byName = new HashMap<>();
        for (TypeParam parameter : typeParameters) {
            if (byName.putIfAbsent(parameter.name(), parameter) != null) {
                throw new IllegalArgumentException("duplicate type parameter " + parameter.name());
            }
        }
        for (TypeParam parameter : typeParameters) {
            for (ClassOrInterfaceTypeRef bound : parameter.bounds()) {
                TypeVariables.requireDeclared(
                        bound, byName.keySet(), "the bound of type parameter " + parameter.name());
                if (bound instanceof TypeVarRef var && parameter.bounds().size() > 1) {
                    throw new IllegalArgumentException("type parameter " + parameter.name()
                            + " is bounded by type variable " + var.name() + " and other types (JLS 4.4)");
                }
            }
        }
        return typeParameters.stream()
                .map(p -> boundErasure(p, byName, new HashSet<>()))
                .toList();
    }

    private static ClassDesc boundErasure(TypeParam parameter, Map<String, TypeParam> byName, Set<String> seen) {
        if (!seen.add(parameter.name())) {
            throw new IllegalArgumentException("type parameter " + parameter.name() + " is bounded by itself");
        }
        if (parameter.bounds().isEmpty()) {
            return ConstantDescs.CD_Object;
        }
        return switch (parameter.bounds().getFirst()) {
            case ClassTypeRef cls -> cls.desc();
            case ParameterizedTypeRef parameterized -> parameterized.raw();
            case TypeVarRef var -> boundErasure(byName.get(var.name()), byName, seen);
        };
    }

    private void requireSupertypesOfTheTypeParameters() {
        if (supertypes.equals(Supertypes.NONE)) {
            return;
        }
        List<String> declared = typeParameters.stream().map(TypeParam::name).toList();
        List<String> given =
                supertypes.typeParameters().stream().map(TypeVarRef::name).toList();
        if (!declared.equals(given)) {
            throw new IllegalArgumentException("the supertypes of " + this + " are given for type parameters " + given
                    + ", but the type has " + declared);
        }
    }

    private void requireSuperclasses() {
        if (kind instanceof DeclaredKind.Interface) {
            if (!superclasses.isEmpty()) {
                throw new IllegalArgumentException("interface " + TypeNames.describe(desc)
                        + " has no superclasses, got " + describe(superclasses));
            }
            return;
        }
        if (desc.equals(ConstantDescs.CD_Object)) {
            if (!superclasses.isEmpty()) {
                throw new IllegalArgumentException(
                        "java.lang.Object has no superclasses, got " + describe(superclasses));
            }
            return;
        }
        if (superclasses.isEmpty() || !superclasses.getLast().equals(ConstantDescs.CD_Object)) {
            throw new IllegalArgumentException(
                    "the superclass chain of " + this + " must end in java.lang.Object, got " + describe(superclasses));
        }
        if (superclasses.contains(desc) || Set.copyOf(superclasses).size() != superclasses.size()) {
            throw new IllegalArgumentException(
                    "the superclass chain of " + this + " repeats a class: " + describe(superclasses));
        }
    }

    private void requireKindData() {
        if (!(kind instanceof DeclaredKind.EnumClass)) {
            if (!enumConstants.isEmpty()) {
                throw new IllegalArgumentException(this + " is not an enum, but has enum constants " + enumConstants);
            }
            if (sealed && kind instanceof DeclaredKind.FinalClass) {
                throw new IllegalArgumentException("a final class cannot be sealed: " + this);
            }
            return;
        }
        if (!typeParameters.isEmpty()) {
            throw new IllegalArgumentException("an enum class cannot be generic: " + this);
        }
        if (!superclasses.equals(List.of(CD_ENUM, ConstantDescs.CD_Object))) {
            throw new IllegalArgumentException("the superclass chain of " + this
                    + " must be java.lang.Enum, java.lang.Object, got " + describe(superclasses));
        }
        Identifiers.requireValid(enumConstants);
        if (Set.copyOf(enumConstants).size() != enumConstants.size()) {
            throw new IllegalArgumentException("duplicate enum constants in " + this + ": " + enumConstants);
        }
    }

    private static String describe(List<ClassDesc> classes) {
        return classes.stream().map(TypeNames::describe).collect(Collectors.joining(", ", "[", "]"));
    }
}
