package me.supcheg.javafile.code;

/// An array access, `array[index]`.
///
/// @param array the accessed array expression
/// @param index the index expression
public record ArrayAccessExpr(Expr array, Expr index) implements Expr, AssignTarget {}
