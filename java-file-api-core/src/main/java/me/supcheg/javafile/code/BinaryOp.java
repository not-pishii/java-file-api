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
    OR("||");

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
