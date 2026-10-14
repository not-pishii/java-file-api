package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

/// An array creation by dimension, `new componentType[dim1][dim2]...`.
///
/// The component type may itself be an array type: its dimensions are the ones
/// left without a length, written after those that have one, so the array of
/// three `int[]` — `newArray(Types.array(Types.INT), literal(3))` — is
/// `new int[3][]`.
///
/// @param componentType the type of what the innermost dimension with a length holds
/// @param dimensions the per-dimension size expressions, outermost first; at least one
public record ArrayCreationExpr(TypeRef componentType, NonEmptyList<Expr> dimensions) implements Expr {}
