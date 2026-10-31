package me.supcheg.javafile.model;

/// Anything that can go inside an interface body: abstract, default, static,
/// and private methods, constants, and nested types.
public sealed interface InterfaceMember
        permits AbstractMethodDecl, DefaultMethodDecl, StaticMethodDecl, PrivateMethodDecl, ConstantDecl, TypeDecl {}
