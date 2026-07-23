package me.supcheg.javafile.type;

import me.supcheg.javafile.annotation.AnnotationUse;

import java.lang.constant.ClassDesc;
import java.util.List;

/// Factory methods for constructing [TypeRef] and [TypeArg] values.
///
/// This is the intended entry point for building type references; the
/// permitted implementations of [TypeRef] and [TypeArg] are not meant to be
/// instantiated directly.
public final class Types {

    private Types() {}

    /// Creates a reference to a non-generic class or interface type.
    ///
    /// @param desc the referenced class or interface
    /// @return a type reference wrapping `desc`
    public static ClassTypeRef of(ClassDesc desc) {
        return new ClassTypeRef(desc);
    }

    /// Creates a reference to a non-generic class or interface type carrying
    /// type-use annotations (JLS 9.7.4), e.g. `@NonNull String`.
    ///
    /// @param desc the referenced class or interface
    /// @param annotations the type-use annotations on this reference
    /// @return a type reference wrapping `desc`
    public static ClassTypeRef of(ClassDesc desc, AnnotationUse... annotations) {
        return new ClassTypeRef(desc, List.of(annotations));
    }

    /// Creates a reference to an array type.
    ///
    /// @param component the type of the array's elements
    /// @return a type reference to an array of `component`
    public static ArrayTypeRef array(TypeRef component) {
        return new ArrayTypeRef(component);
    }

    /// Creates a reference to an array type carrying type-use annotations
    /// (JLS 9.7.4) on the array level itself, e.g. `String @NonNull []`.
    ///
    /// @param component the type of the array's elements
    /// @param annotations the type-use annotations on this array level
    /// @return a type reference to an array of `component`
    public static ArrayTypeRef array(TypeRef component, AnnotationUse... annotations) {
        return new ArrayTypeRef(component, List.of(annotations));
    }

    /// Creates a reference to a generic type applied to type arguments.
    ///
    /// @param raw the generic type's raw class or interface
    /// @param args the type arguments applied to `raw`, in order
    /// @return a parameterized type reference
    public static ParameterizedTypeRef parameterized(ClassDesc raw, TypeArg... args) {
        return new ParameterizedTypeRef(raw, List.of(args));
    }

    /// Creates a reference to a generic type applied to type arguments,
    /// carrying type-use annotations (JLS 9.7.4), e.g. `@NonNull List<String>`.
    ///
    /// @param raw the generic type's raw class or interface
    /// @param args the type arguments applied to `raw`, in order
    /// @param annotations the type-use annotations on this reference
    /// @return a parameterized type reference
    public static ParameterizedTypeRef parameterized(ClassDesc raw, List<TypeArg> args, AnnotationUse... annotations) {
        return new ParameterizedTypeRef(raw, args, List.of(annotations));
    }

    /// Creates a reference to a type variable declared by an enclosing
    /// generic declaration, e.g. `T`.
    ///
    /// @param name the type variable's name
    /// @return a type reference to the variable
    public static TypeVarRef typeVar(String name) {
        return new TypeVarRef(name);
    }

    /// Creates a reference to a type variable declared by an enclosing
    /// generic declaration, carrying type-use annotations (JLS 9.7.4),
    /// e.g. `@NonNull T`.
    ///
    /// @param name the type variable's name
    /// @param annotations the type-use annotations on this reference
    /// @return a type reference to the variable
    public static TypeVarRef typeVar(String name, AnnotationUse... annotations) {
        return new TypeVarRef(name, List.of(annotations));
    }

    /// Creates a type argument that is a concrete type with no wildcard.
    ///
    /// @param type the exact type argument
    /// @return a type argument wrapping `type`
    public static TypeArg exact(TypeRef type) {
        return new ExactTypeArg(type);
    }

    /// Creates an upper-bounded wildcard type argument, e.g. `? extends Number`.
    ///
    /// @param bound the upper bound of the wildcard
    /// @return an upper-bounded wildcard type argument
    public static TypeArg extendsBound(TypeRef bound) {
        return new ExtendsTypeArg(bound);
    }

    /// Creates a lower-bounded wildcard type argument, e.g. `? super Integer`.
    ///
    /// @param bound the lower bound of the wildcard
    /// @return a lower-bounded wildcard type argument
    public static TypeArg superBound(TypeRef bound) {
        return new SuperTypeArg(bound);
    }

    /// Returns the unbounded wildcard type argument `?`.
    ///
    /// @return the unbounded wildcard type argument
    public static TypeArg unbounded() {
        return UnboundedTypeArg.INSTANCE;
    }
}
