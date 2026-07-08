package me.supcheg.javafile.code;

/// The parameter list of a [LambdaExpr]: either all names with inferred
/// types ([InferredLambdaParams]) or all explicitly typed
/// ([TypedLambdaParams]).
///
/// Java forbids mixing the two forms; the sealed split makes a mixed list
/// unrepresentable.
public sealed interface LambdaParams permits InferredLambdaParams, TypedLambdaParams {}
