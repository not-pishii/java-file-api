package me.supcheg.javafile.code;

/// The operator applied by a [BinaryExpr].
public enum BinaryOp {
    /// Addition, `+`.
    ADD("+"),
    /// Subtraction, `-`.
    SUB("-"),
    /// Multiplication, `*`.
    MUL("*"),
    /// Division, `/`.
    DIV("/"),
    /// Modulo, `%`.
    MOD("%"),
    /// Equality, `==`.
    EQ("=="),
    /// Inequality, `!=`.
    NEQ("!="),
    /// Less-than, `<`.
    LT("<"),
    /// Less-than-or-equal, `<=`.
    LE("<="),
    /// Greater-than, `>`.
    GT(">"),
    /// Greater-than-or-equal, `>=`.
    GE(">="),
    /// Logical AND, `&&`.
    AND("&&"),
    /// Logical OR, `||`.
    OR("||"),
    /// Bitwise/logical AND, `&`.
    BIT_AND("&"),
    /// Bitwise/logical OR, `|`.
    BIT_OR("|"),
    /// Bitwise/logical XOR, `^`.
    BIT_XOR("^"),
    /// Left shift, `<<`.
    SHL("<<"),
    /// Signed right shift, `>>`.
    SHR(">>"),
    /// Unsigned right shift, `>>>`.
    USHR(">>>");

    private final String symbol;

    BinaryOp(String symbol) {
        this.symbol = symbol;
    }

    /// The operator's source-code symbol, e.g. `"+"` for [#ADD].
    ///
    /// @return the rendered symbol
    public String symbol() {
        return symbol;
    }
}
