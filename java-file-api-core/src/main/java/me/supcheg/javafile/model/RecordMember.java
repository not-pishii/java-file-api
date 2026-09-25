package me.supcheg.javafile.model;

/// Anything that can go inside a record body: compact and canonical
/// constructors, methods, static fields, and nested types.
public sealed interface RecordMember
        permits CompactConstructorDecl, CanonicalConstructorDecl, MethodDecl, StaticFieldDecl, TypeDecl {}
