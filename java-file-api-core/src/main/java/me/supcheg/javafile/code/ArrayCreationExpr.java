package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

/// An array creation by dimension, `new componentType[dim1][dim2]...`.
///
/// @param componentType the array's component type
/// @param dimensions the per-dimension size expressions, outermost first; at least one, per JLS
public record ArrayCreationExpr(TypeRef componentType, NonEmptyList<Expr> dimensions) implements Expr {}
