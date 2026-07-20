package me.supcheg.javafile.code;

/// An expression valid as a bare [ExprStmt], per JLS 14.8: method invocation,
/// class instance creation, or a pre/post increment or decrement.
///
/// Assignment is JLS's fourth statement-expression form, but this model
/// represents it as the separate statement [AssignStmt] rather than an
/// [Expr], so it does not appear here.
public sealed interface StatementExpr extends Expr permits IncDecExpr, MethodCallExpr, NewExpr, StaticMethodCallExpr {}
