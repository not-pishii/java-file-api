package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

/// An array creation with an initializer, `new componentType[] {e1, e2, ...}` (JLS 15.10.1).
///
/// It is an expression, with a type of its own, and so it stands anywhere an expression does. The short form
/// `{e1, e2, ...}` is the [ArrayInitializer], which stands where the type of the array is known.
///
/// @param componentType the array's component type
/// @param initializer the initializer of the array, in braces
public record ArrayInitializerExpr(TypeRef componentType, ArrayInitializer initializer) implements Expr {}
