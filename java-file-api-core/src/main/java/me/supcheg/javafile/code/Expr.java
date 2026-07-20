package me.supcheg.javafile.code;

/// An expression that produces a value when evaluated.
///
/// The permitted implementations cover field access, static field access,
/// method calls, static method calls, literals, text blocks, binary and
/// unary operators, `instanceof` pattern matching, object creation, `switch`
/// expressions, lambda expressions, casts, ternary conditionals, class
/// literals, and the `this` and `super` keyword expressions. Statements
/// ([Stmt]) form a separate hierarchy, so an expression cannot appear where a
/// statement is expected without an explicit wrapper such as [ExprStmt].
public sealed interface Expr
        permits FieldAccessExpr,
                StaticFieldAccessExpr,
                MethodCallExpr,
                StaticMethodCallExpr,
                LiteralExpr,
                TextBlockExpr,
                BinaryExpr,
                UnaryExpr,
                IncDecExpr,
                InstanceOfExpr,
                NewExpr,
                SwitchExpr,
                LambdaExpr,
                StatementExpr,
                ConstantExpr,
                ThisExpr,
                SuperExpr,
                CastExpr,
                ConditionalExpr,
                ClassLiteralExpr {}
