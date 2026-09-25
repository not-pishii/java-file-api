package me.supcheg.javafile.code;

/// Something that can be assigned to: a variable or field (`x`, `this.x`,
/// `Config.x`) or an array element (`items[i]`).
///
/// Create it with [Exprs#field(String)], [Expr#field(String)],
/// [Exprs#staticField(java.lang.constant.ClassDesc,String)], or [Expr#arrayAccess(Expr)].
public sealed interface AssignTarget permits FieldAccessExpr, StaticFieldAccessExpr, ArrayAccessExpr {}
