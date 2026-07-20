package me.supcheg.javafile.code;

/// An instance-bound method reference target, e.g. the `expr` in `expr::method`.
///
/// @param expr the bound instance expression
public record ExprMethodRefTarget(Expr expr) implements MethodRefTarget {}
