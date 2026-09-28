package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.TypeRef;

import java.lang.constant.ClassDesc;

/// A branded handle of a type of the generated code: the fact "this type
/// exists", with the Java type it stands for carried as the phantom `T`.
///
/// Primitive types use their box as the phantom — [PrimitiveToken#INT] is a
/// `TypeToken<Integer>` that renders `int` — so the phantom never decides
/// between `int` and `Integer`; the token does. Where Java requires a
/// reference type (type arguments, `instanceof`, casts), APIs take a
/// [RefToken] instead.
///
/// Tokens are introduced only by fact sources: metamodels, the mirror source
/// of `java-file-api-lang-model`, and the typed declarations of
/// `java-file-api-typed`. Each fact class has a static `introduce` factory
/// for that purpose; generator code never calls it directly.
///
/// @param <T> the Java type this token stands for
public sealed interface TypeToken<T> permits RefToken, PrimitiveToken {

    /// The type as written in generated code.
    ///
    /// @return the core type reference
    TypeRef typeRef();

    /// The erasure of the type (JLS 4.6), as a descriptor.
    ///
    /// @return the erased type
    ClassDesc erasure();
}
