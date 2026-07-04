package me.supcheg.javafile.code;

/// An expression that produces a value when evaluated.
///
/// The permitted implementations cover field access, method calls, literals,
/// text blocks, binary and unary operators, `instanceof` pattern matching,
/// object creation, and `switch` expressions. Statements ([Stmt]) form a
/// separate hierarchy, so an expression cannot appear where a statement is
/// expected without an explicit wrapper such as [ExprStmt].
public sealed interface Expr
        permits FieldAccessExpr,
                MethodCallExpr,
                LiteralExpr,
                TextBlockExpr,
                BinaryExpr,
                UnaryExpr,
                InstanceOfExpr,
                NewExpr,
                SwitchExpr {}
