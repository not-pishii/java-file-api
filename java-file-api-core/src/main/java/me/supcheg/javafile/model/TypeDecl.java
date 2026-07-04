package me.supcheg.javafile.model;

/// A top-level type declaration: a class, interface, record, or enum.
///
/// A [TypeDecl] is itself a [JavaFileElement], a [ClassMember], an
/// [InterfaceMember], and a [RecordMember], so any of the four kinds can
/// appear as the sole content of a Java file or, in principle, as a nested
/// type member of another declaration.
public sealed interface TypeDecl extends JavaFileElement, ClassMember, InterfaceMember, RecordMember
        permits ClassDecl, InterfaceDecl, RecordDecl, EnumDecl {}
