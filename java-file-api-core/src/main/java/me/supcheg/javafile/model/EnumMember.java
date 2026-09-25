package me.supcheg.javafile.model;

/// Anything that can go inside an enum body after the constants: fields,
/// methods, the constructor, abstract methods, initializer blocks, and nested
/// types.
public sealed interface EnumMember
        permits AbstractMethodDecl, EnumConstructorDecl, FieldDecl, MethodDecl, TypeDecl, InitializerBlock {}
