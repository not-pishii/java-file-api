package me.supcheg.javafile.code;

/// A statement that may appear in a method or block body.
///
/// The permitted implementations cover control flow (`if`, `while`,
/// `do`-`while`, `for`, enhanced `for`, `switch`, and `try`), local variable
/// declarations, assignments, expression statements, and the terminal forms
/// `return`, `throw`, `break`, `continue`, and `yield`. A sequence of
/// statements is assembled into a [CodeBody].
public sealed interface Stmt
        permits ReturnStmt,
                ExprStmt,
                AssignStmt,
                LocalVarDeclStmt,
                IfStmt,
                WhileStmt,
                DoWhileStmt,
                ForStmt,
                EnhancedForStmt,
                SwitchStmt,
                YieldStmt,
                ThrowStmt,
                BreakStmt,
                ContinueStmt,
                TryStmt,
                LabeledStmt {}
