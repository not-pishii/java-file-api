package me.supcheg.javafile.type;

import java.lang.constant.ClassDesc;

/// A reference to a non-generic class or interface type.
///
/// @param desc the referenced class or interface
public record ClassTypeRef(ClassDesc desc) implements TypeRef {}
