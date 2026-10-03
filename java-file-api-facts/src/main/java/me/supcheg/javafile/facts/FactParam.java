package me.supcheg.javafile.facts;

/// A parameter of a method or constructor as a fact is made of it
/// ([UnsafeFacts]): its type as a member of the owner, and how the member
/// declares it — the parameter of its signature in the
/// [MethodTableTemplate] of the owner's type.
///
/// A [TypeToken] is a parameter declared as it erases: the right one for a
/// parameter whose declared type is not a type variable, nor an array of
/// one. A parameter declared `T`, `T[]` or `T...` for a type parameter of
/// the owner's type or of the method is made by
/// [UnsafeFacts#param(TypeToken, MethodTableTemplate.Param)]: the token is
/// what `T` stands for in the fact, and erases differently for each type
/// argument, the declaration is the same for all.
///
/// The declarations are what tells the fact from another overload:
/// `m(T)` of a `Box<String>` and its `m(String)` take the same token, and
/// only one of them is declared `#0`. See [Invocable#declared()].
///
/// @param <T> the type of the parameter in the fact
public sealed interface FactParam<T> permits TypeToken, DeclaredParam {}
