package me.supcheg.javafile.model;

/// The root of the declaration model: anything that can be the top-level
/// content of a Java source file.
///
/// Currently the only top-level content Java allows is a single type
/// declaration ([TypeDecl]); this interface exists so future top-level forms
/// have a place to attach without widening [TypeDecl] itself.
public sealed interface JavaFileElement permits TypeDecl {}
