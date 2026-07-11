package me.supcheg.javafile.model;

/// A member that may appear in an enum constant's constant-specific body
/// (an anonymous subclass of the enum).
///
/// Narrower than [EnumMember]: an anonymous class body cannot declare a
/// constructor or an abstract method, so [EnumConstructorDecl] and
/// [AbstractMethodDecl] are deliberately excluded, making that combination
/// unrepresentable rather than rejected at runtime.
public sealed interface EnumConstantMember permits FieldDecl, MethodDecl, TypeDecl {}
