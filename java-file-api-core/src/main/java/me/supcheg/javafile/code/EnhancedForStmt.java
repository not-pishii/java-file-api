package me.supcheg.javafile.code;

import me.supcheg.javafile.type.TypeRef;

/// An enhanced `for` loop, e.g. `for (elementType varName : iterable)`.
///
/// @param elementType the declared type of the loop variable
/// @param varName the loop variable name
/// @param iterable the iterated expression
/// @param body the loop body
public record EnhancedForStmt(TypeRef elementType, String varName, Expr iterable, CodeBody body) implements Stmt {}
