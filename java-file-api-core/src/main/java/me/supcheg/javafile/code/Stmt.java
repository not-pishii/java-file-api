package me.supcheg.javafile.code;

/// A statement that may appear in a method or block body.
///
/// Add statements with the methods of [CodeBuilder].
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
                EmptyStmt,
                LocalTypeDeclStmt {}
