package me.supcheg.javafile.code;

/// A lambda body consisting of a single result expression, e.g. `x -> result`.
///
/// @param result the expression the lambda evaluates to
public record ExprLambdaBody(Expr result) implements LambdaBody {}
