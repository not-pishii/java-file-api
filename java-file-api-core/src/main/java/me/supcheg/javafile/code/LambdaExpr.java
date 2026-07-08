package me.supcheg.javafile.code;

/// A lambda expression, e.g. `(name) -> body` or `(String name) -> body`.
///
/// The parameter list always renders parenthesized, even for a single
/// parameter.
///
/// @param params the lambda's parameters
/// @param body the lambda's body
public record LambdaExpr(LambdaParams params, LambdaBody body) implements Expr {}
