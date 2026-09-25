package me.supcheg.javafile.model;

/// A class, interface, record, enum, or annotation type. Used both for a
/// file's top-level type and for nested types.
public sealed interface TypeDecl
        extends JavaFileElement, ClassMember, InterfaceMember, RecordMember, EnumMember, EnumConstantMember
        permits ClassDecl, InterfaceDecl, RecordDecl, EnumDecl, AnnotationTypeDecl {}
