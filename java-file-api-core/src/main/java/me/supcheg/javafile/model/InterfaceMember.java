package me.supcheg.javafile.model;

/// A member that may appear in an interface body.
///
/// The permitted implementations mirror what the Java language allows inside
/// an `interface` declaration: abstract, default, and static methods, `public
/// static final` constants, and nested type declarations. Class and record
/// bodies use their own member hierarchies ([ClassMember], [RecordMember]), so
/// an impossible combination is unrepresentable rather than rejected at
/// runtime.
public sealed interface InterfaceMember
        permits AbstractMethodDecl, DefaultMethodDecl, StaticMethodDecl, ConstantDecl, TypeDecl {}
