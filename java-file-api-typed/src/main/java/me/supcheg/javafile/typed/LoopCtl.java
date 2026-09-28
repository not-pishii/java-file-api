package me.supcheg.javafile.typed;

/// The capability to `break` or `continue` a loop, handed to the loop body
/// only (§6.3): outside a loop there is no `LoopCtl`, so a stray `break` does
/// not compile.
///
/// Breaking an outer loop from an inner one renders a labeled `break`. A
/// `LoopCtl` that escapes its loop body — stored and used after the loop, in
/// another loop, or from a lambda body inside the loop, which Java forbids —
/// is rejected when the `break_`/`continue_` is built.
public final class LoopCtl {
    private final Block<?, ?> body;

    LoopCtl(Block<?, ?> body) {
        this.body = body;
    }

    /// The body of the loop this capability belongs to.
    Block<?, ?> body() {
        return body;
    }
}
