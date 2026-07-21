package me.supcheg.javafile.code;

/// The qualifier of a [MethodRefExpr]: a type (`Type::method`) or a bound
/// instance expression (`expr::method`).
public sealed interface MethodRefTarget permits TypeMethodRefTarget, ExprMethodRefTarget {}
