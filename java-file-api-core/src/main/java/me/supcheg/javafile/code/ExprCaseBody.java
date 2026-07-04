package me.supcheg.javafile.code;

/// A switch case body that is a single expression, e.g. `-> expr`.
///
/// @param expr the case's result expression
public record ExprCaseBody(Expr expr) implements CaseBody {}
