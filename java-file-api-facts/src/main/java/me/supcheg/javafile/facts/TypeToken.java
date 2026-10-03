package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.TypeRef;

import java.lang.constant.ClassDesc;

/// A branded handle of a type of the generated code: the fact "this type
/// exists", with the Java type it stands for carried as the phantom `T`.
///
/// Primitive types use their marker from [Prim] as the phantom —
/// [PrimitiveToken#INT] is a `TypeToken<Prim.Int>` — so `int` and `Integer`
/// are different types to javac and never convert into each other
/// implicitly (§6.1). Where Java requires a reference type (type arguments,
/// `instanceof`, casts), APIs take a [RefToken] instead.
///
/// Tokens are introduced only by fact sources: metamodels, the mirror source
/// of `java-file-api-lang-model`, and the typed declarations of
/// `java-file-api-typed`. The constructors of the token classes are not
/// public; a token that is not derived from another one is created by
/// [UnsafeFacts] (§3.1).
///
/// A token is also the [FactParam] of a parameter that is declared as the
/// token erases.
///
/// @param <T> the Java type this token stands for
public sealed interface TypeToken<T> extends FactParam<T> permits RefToken, PrimitiveToken {

    /// The type as written in generated code.
    ///
    /// @return the core type reference
    TypeRef typeRef();

    /// The erasure of the type (JLS 4.6), as a descriptor.
    ///
    /// @return the erased type
    ClassDesc erasure();
}
