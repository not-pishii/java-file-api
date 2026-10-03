package me.supcheg.javafile.facts;

/// A [FactParam] with a declaration of its own, see
/// [UnsafeFacts#param(TypeToken, MethodTableTemplate.Param)].
///
/// @param token the type of the parameter in the fact
/// @param declared the parameter in the signature the member declares
/// @param <T> the type of the parameter in the fact
record DeclaredParam<T>(TypeToken<T> token, MethodTableTemplate.Param declared) implements FactParam<T> {}
