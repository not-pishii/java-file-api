package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

/// A constructor reference, `type::new`.
///
/// @param type the referenced type
public record ConstructorRefExpr(TypeRef type) implements Expr {}
