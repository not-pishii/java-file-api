package me.supcheg.javafile.code;

/// The operator applied by a [UnaryExpr].
public enum UnaryOp {
    /// Logical negation, `!operand`.
    NOT,
    /// Arithmetic negation, `-operand`.
    NEG,
    /// Bitwise complement, `~operand`.
    BIT_NOT,
    /// Unary plus, `+operand`.
    UNARY_PLUS
}
