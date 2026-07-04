package me.supcheg.javafile.type;

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
    public static TypeRef of(ClassDesc desc) {
        return new ClassTypeRef(desc);
    }

    /// Creates a reference to an array type.
    ///
    /// @param component the type of the array's elements
    /// @return a type reference to an array of `component`
    public static TypeRef array(TypeRef component) {
        return new ArrayTypeRef(component);
    }

    /// Creates a reference to a generic type applied to type arguments.
    ///
    /// @param raw the generic type's raw class or interface
    /// @param args the type arguments applied to `raw`, in order
    /// @return a parameterized type reference
    public static TypeRef parameterized(ClassDesc raw, TypeArg... args) {
        return new ParameterizedTypeRef(raw, List.of(args));
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
