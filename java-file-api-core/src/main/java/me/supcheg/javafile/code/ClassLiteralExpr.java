package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

/// A class literal, `type.class`.
///
/// Not a [ConstantExpr]: JLS 15.29 explicitly excludes class literals from
/// the constant-expression forms valid in a `case` label.
///
/// @param type the referenced type
public record ClassLiteralExpr(TypeRef type) implements Expr {}
