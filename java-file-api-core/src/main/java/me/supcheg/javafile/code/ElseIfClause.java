package me.supcheg.javafile.code;

/// An `else if` clause attached to an [IfStmt].
///
/// @param condition the clause's condition
/// @param body the clause's body
public record ElseIfClause(Expr condition, CodeBody body) {}
