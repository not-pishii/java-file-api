package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

/// A cast expression, `(type) operand`.
///
/// @param type the target type
/// @param operand the cast operand
public record CastExpr(TypeRef type, Expr operand) implements Expr {}
