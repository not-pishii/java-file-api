package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

import java.lang.constant.ClassDesc;
import java.util.List;

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

    /// The erasures the method table of [#shape()] is instantiated with, one
    /// per type parameter of the type: the erasure of a type argument that is
    /// a type or an upper bound, otherwise that of the bound of the type
    /// parameter, as for the raw type.
    ///
    /// @return the erasures, empty for a type that is not generic
    List<ClassDesc> argumentErasures();

    /// The type arguments the token applies [#shape()] to, as tokens: what
    /// [TargetClasspath#verify(TypeToken)] reaches the metamodels of the
    /// type arguments through.
    ///
    /// @return the type arguments, one per type parameter; empty for a type that is not generic, for a
    ///         raw type, and for a token vouched for by a type reference, which has no tokens of them
    List<TokenArg> typeArguments();

    /// The methods of the type, instance and `static`, declared and inherited, as runtime
    /// data for the completeness checks of lowering.
    ///
    /// @return the method table
    MethodTable methods();
}
