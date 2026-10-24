package me.supcheg.javafile.code;

/// What a variable or field is initialized with: an expression, or the `{e1, e2, ...}` of an array (JLS 8.3, 14.4,
/// 10.6).
///
/// An [ArrayInitializer] has no type of its own, so it stands only where the type is known: the initializer of a
/// declaration with a type written, or the body of an [ArrayInitializerExpr]. No `var x = {1};`, no `return {1};`,
/// no `f({1})`: these are no Java, and no place of this library takes one.
public sealed interface VariableInitializer permits Expr, ArrayInitializer {}
