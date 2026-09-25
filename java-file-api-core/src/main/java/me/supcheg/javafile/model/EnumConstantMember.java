package me.supcheg.javafile.model;

/// Anything that can go inside an enum constant's body or an anonymous class:
/// fields, methods, and nested types.
public sealed interface EnumConstantMember permits FieldDecl, MethodDecl, TypeDecl {}
