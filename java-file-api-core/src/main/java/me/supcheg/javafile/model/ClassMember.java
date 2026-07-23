package me.supcheg.javafile.model;

/// A member that may appear in a class body.
///
/// The permitted implementations mirror what the Java language allows inside a
/// `class` declaration: fields, methods, constructors, abstract methods,
/// nested type declarations, and initializer blocks. Interface and record
/// bodies use their own member hierarchies ([InterfaceMember],
/// [RecordMember]), so an impossible combination is unrepresentable rather
/// than rejected at runtime.
public sealed interface ClassMember
        permits FieldDecl, MethodDecl, ConstructorDecl, AbstractMethodDecl, TypeDecl, InitializerBlock {}
