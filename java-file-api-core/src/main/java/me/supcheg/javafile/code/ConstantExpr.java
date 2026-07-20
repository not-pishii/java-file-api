package me.supcheg.javafile.code;

/// An expression valid as a [ConstantLabel]'s matched value, per JLS 15.29's
/// `ConstantExpression` production restricted to the forms this model
/// represents: a literal, or a simple/qualified name (used for an enum
/// constant, e.g. `case RED ->`).
///
/// Whether a given [FieldAccessExpr] or [StaticFieldAccessExpr] actually
/// names a compile-time constant is a semantic property (constant folding)
/// and is not checked here — this interface narrows the representable
/// *forms*, not their constant-ness.
public sealed interface ConstantExpr extends Expr permits FieldAccessExpr, LiteralExpr, StaticFieldAccessExpr {}
