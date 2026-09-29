package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

/// A token of a declared type — a class or an interface, possibly
/// parameterized, e.g. `String` or `List<String>`.
///
/// @param <T> the Java type this token stands for
public sealed interface DeclaredToken<T> extends RefToken<T> permits ClassToken, InterfaceToken {

    /// The type as written in generated code: a class or a parameterized type,
    /// never a type variable.
    ///
    /// @return the core type reference
    @Override
    ClassOrInterfaceTypeRef typeRef();

    /// The shape the token applies to its type arguments: the data of the
    /// type that does not depend on them, and who vouches for it.
    ///
    /// @return the shape
    TypeShape<?> shape();

    /// The parameterized supertypes of the type's generic class or interface,
    /// in terms of its type parameters; [Supertypes#NONE] when not recorded
    /// and for a raw type.
    ///
    /// @return the supertypes
    Supertypes supertypes();

    /// The instance methods of the type, declared and inherited, as runtime
    /// data for the completeness checks of lowering.
    ///
    /// @return the method table
    MethodTable methods();
}
