package me.supcheg.javafile.code;

/// A value usable in `case`: a literal (`case 1 ->`) or a name
/// (`case RED ->`, `case Limits.MAX ->`).
///
/// Whether a name really refers to a constant is not checked; the compiler
/// will report it.
public sealed interface ConstantExpr extends Expr permits FieldAccessExpr, LiteralExpr, StaticFieldAccessExpr {}
