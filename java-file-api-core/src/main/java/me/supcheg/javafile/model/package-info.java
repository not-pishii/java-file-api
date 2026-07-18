/// Immutable value types describing a Java type declaration and its members.
///
/// [me.supcheg.javafile.model.TypeDecl] is the sealed root: a top-level
/// declaration is exactly one of [me.supcheg.javafile.model.ClassDecl],
/// [me.supcheg.javafile.model.InterfaceDecl],
/// [me.supcheg.javafile.model.RecordDecl], or
/// [me.supcheg.javafile.model.EnumDecl]. Each declaration kind has its own
/// member hierarchy ([me.supcheg.javafile.model.ClassMember],
/// [me.supcheg.javafile.model.InterfaceMember],
/// [me.supcheg.javafile.model.RecordMember]), so an impossible combination —
/// e.g. a constant inside a class body — is unrepresentable rather than
/// rejected at runtime. All types in this package are immutable records and
/// sealed interfaces; the mutable [me.supcheg.javafile.builder] types
/// produce them.
@NullMarked
package me.supcheg.javafile.model;

import org.jspecify.annotations.NullMarked;
