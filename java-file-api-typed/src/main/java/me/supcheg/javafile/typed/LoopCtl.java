package me.supcheg.javafile.typed;

/// The capability to `break` or `continue` a loop, handed to the loop body
/// only (§6.3): outside a loop there is no `LoopCtl`, so a stray `break` does
/// not compile.
///
/// Breaking an outer loop from an inner one renders a labeled `break`.
/// Using it from a lambda inside the loop, which Java forbids, is rejected by
/// lowering.
public final class LoopCtl {
    LoopCtl() {}
}
