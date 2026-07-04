package me.supcheg.javafile.code;

/// The operator applied by a [UnaryExpr].
public enum UnaryOp {
    /// Logical negation, `!operand`.
    NOT,
    /// Arithmetic negation, `-operand`.
    NEG,
    /// Pre-increment, `++operand`.
    PRE_INC,
    /// Pre-decrement, `--operand`.
    PRE_DEC,
    /// Post-increment, `operand++`.
    POST_INC,
    /// Post-decrement, `operand--`.
    POST_DEC
}
