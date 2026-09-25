package me.supcheg.javafile.model;

/// Anything that can go inside a class body: fields, methods, constructors,
/// abstract methods, initializer blocks, and nested types.
public sealed interface ClassMember
        permits FieldDecl, MethodDecl, ConstructorDecl, AbstractMethodDecl, TypeDecl, InitializerBlock {}
