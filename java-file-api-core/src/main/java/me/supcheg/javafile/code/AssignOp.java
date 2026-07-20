package me.supcheg.javafile.code;

/// The operator applied by an [AssignStmt].
public enum AssignOp {
    /// Plain assignment, `=`.
    ASSIGN("="),
    /// `+=`.
    ADD_ASSIGN("+="),
    /// `-=`.
    SUB_ASSIGN("-="),
    /// `*=`.
    MUL_ASSIGN("*="),
    /// `/=`.
    DIV_ASSIGN("/="),
    /// `%=`.
    MOD_ASSIGN("%="),
    /// `&=`.
    AND_ASSIGN("&="),
    /// `|=`.
    OR_ASSIGN("|="),
    /// `^=`.
    XOR_ASSIGN("^="),
    /// `<<=`.
    SHL_ASSIGN("<<="),
    /// `>>=`.
    SHR_ASSIGN(">>="),
    /// `>>>=`.
    USHR_ASSIGN(">>>=");

    private final String symbol;

    AssignOp(String symbol) {
        this.symbol = symbol;
    }

    /// The operator's source-code symbol, e.g. `"+="` for [#ADD_ASSIGN].
    ///
    /// @return the rendered symbol
    public String symbol() {
        return symbol;
    }
}
