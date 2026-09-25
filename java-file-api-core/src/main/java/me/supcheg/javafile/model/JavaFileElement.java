package me.supcheg.javafile.model;

/// The content of a Java source file: a [TypeDecl].
public sealed interface JavaFileElement permits TypeDecl {}
