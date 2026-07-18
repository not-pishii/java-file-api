package me.supcheg.javafile.code;

/// An expression valid as the left-hand side of an [AssignStmt], per JLS
/// 15.26: an expression name, a field access, or an array access.
///
/// Only [FieldAccessExpr] is permitted here today — this model represents
/// both an unqualified expression name and a qualified field access as
/// [FieldAccessExpr]. Array access joins this hierarchy once introduced.
public sealed interface AssignTarget permits FieldAccessExpr {}
