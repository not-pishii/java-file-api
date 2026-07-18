package me.supcheg.javafile.code;

/// The operator applied by an [IncDecExpr].
public enum IncDecOp {
    /// Pre-increment, `++operand`.
    PRE_INC,
    /// Pre-decrement, `--operand`.
    PRE_DEC,
    /// Post-increment, `operand++`.
    POST_INC,
    /// Post-decrement, `operand--`.
    POST_DEC
}
