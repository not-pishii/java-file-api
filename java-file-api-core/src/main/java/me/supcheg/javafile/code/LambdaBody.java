package me.supcheg.javafile.code;

/// The body of a [LambdaExpr]: either a single result expression
/// ([ExprLambdaBody]) or a statement block ([BlockLambdaBody]).
public sealed interface LambdaBody permits ExprLambdaBody, BlockLambdaBody {}
