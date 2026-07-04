package me.supcheg.javafile.model;

/// A member that may appear in a record body.
///
/// The permitted implementations mirror what the Java language allows inside a
/// `record` declaration in addition to its implicit canonical constructor and
/// component accessors: a compact constructor, methods, static fields, and
/// nested type declarations. Class and interface bodies use their own member
/// hierarchies ([ClassMember], [InterfaceMember]), so an impossible
/// combination is unrepresentable rather than rejected at runtime.
public sealed interface RecordMember permits CompactConstructorDecl, MethodDecl, StaticFieldDecl, TypeDecl {}
