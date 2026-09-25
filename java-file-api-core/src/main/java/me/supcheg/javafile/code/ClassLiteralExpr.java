package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

/// A class literal, `type.class`.
///
/// @param type the referenced type
public record ClassLiteralExpr(TypeRef type) implements Expr {}
