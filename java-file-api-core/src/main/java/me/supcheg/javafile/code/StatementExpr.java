package me.supcheg.javafile.code;

/// An expression that can stand alone as a statement: a method call, `new`,
/// or `++`/`--`. Pass it to [CodeBuilder#exprStatement(StatementExpr)].
///
/// For assignments use [CodeBuilder#assign(AssignTarget,Expr)].
public sealed interface StatementExpr extends Expr permits IncDecExpr, MethodCallExpr, NewExpr, StaticMethodCallExpr {}
