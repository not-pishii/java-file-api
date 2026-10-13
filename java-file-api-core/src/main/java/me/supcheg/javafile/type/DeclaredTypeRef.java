package me.supcheg.javafile.type;

/// The type of a class or an interface, raw or with its type arguments — a
/// [ClassOrInterfaceTypeRef] that is no type variable.
///
/// Required where a type has to name a declaration: the token of a declared
/// type, whose supertypes and members are those of the class it names.
public sealed interface DeclaredTypeRef extends ClassOrInterfaceTypeRef permits ClassTypeRef, ParameterizedTypeRef {}
