package me.supcheg.javafile.model;

/// A top-level type declaration: a class, interface, record, enum, or
/// annotation type.
///
/// A [TypeDecl] is itself a [JavaFileElement], a [ClassMember], an
/// [InterfaceMember], a [RecordMember], an [EnumMember], and an
/// [EnumConstantMember], so any of these kinds can appear as the sole
/// content of a Java file or, in principle, as a nested type member of
/// another declaration.
public sealed interface TypeDecl
        extends JavaFileElement, ClassMember, InterfaceMember, RecordMember, EnumMember, EnumConstantMember
        permits ClassDecl, InterfaceDecl, RecordDecl, EnumDecl, AnnotationTypeDecl {}
