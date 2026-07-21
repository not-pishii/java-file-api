package me.supcheg.javafile.code;

/// An expression valid as the left-hand side of an [AssignStmt], per JLS
/// 15.26: an expression name, a field access, or an array access.
///
/// [FieldAccessExpr] represents both an unqualified expression name and a
/// qualified instance field access; [StaticFieldAccessExpr] represents a
/// qualified static field access; [ArrayAccessExpr] represents an array
/// access.
public sealed interface AssignTarget permits FieldAccessExpr, StaticFieldAccessExpr, ArrayAccessExpr {}
