package me.supcheg.javafile.model;

/// Anything that can go inside a record body: compact and canonical
/// constructors, additional constructors, methods, static fields, a static
/// initializer block, and nested types.
public sealed interface RecordMember
        permits CompactConstructorDecl,
                CanonicalConstructorDecl,
                ConstructorDecl,
                MethodDecl,
                NativeMethodDecl,
                StaticFieldDecl,
                InitializerBlock,
                TypeDecl {}
