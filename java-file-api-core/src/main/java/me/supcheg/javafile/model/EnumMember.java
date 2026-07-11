package me.supcheg.javafile.model;

/// A member that may appear directly in an enum's own body.
///
/// The permitted implementations mirror what the Java language allows
/// directly inside an `enum` declaration: fields, methods, the (implicitly
/// private) constructor, abstract methods each constant must implement, and
/// nested type declarations. A constant's constant-specific body has a
/// narrower set of allowed members — see [EnumConstantMember].
public sealed interface EnumMember permits AbstractMethodDecl, EnumConstructorDecl, FieldDecl, MethodDecl, TypeDecl {}
