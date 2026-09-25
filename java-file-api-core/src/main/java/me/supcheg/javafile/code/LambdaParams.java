package me.supcheg.javafile.code;

/// The parameters of a [LambdaExpr]: either all untyped, `(a, b)`, or all
/// typed, `(String a, int b)`, as Java requires.
public sealed interface LambdaParams permits InferredLambdaParams, TypedLambdaParams {}
