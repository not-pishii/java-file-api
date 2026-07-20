package me.supcheg.javafile.code;

/// A statement that may appear in a method or block body.
///
/// The permitted implementations cover control flow (`if`, `while`,
/// `do`-`while`, `for`, enhanced `for`, `switch`, `try`, and `synchronized`),
/// local variable declarations, assignments, expression statements, labeled
/// statements, the empty statement, `assert`, and the terminal forms
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
                LabeledStmt,
                SynchronizedStmt,
                AssertStmt,
                EmptyStmt {}
