package me.supcheg.javafile.typed;

/// The proof that a block cannot complete normally (§6.3): it ends with
/// `return`, `throw`, `break`, `continue`, or an exhaustive construct whose
/// every branch ends so. For `void` code, [VoidBody#end()] also gives it.
///
/// A method, constructor, or block lambda body is a function that must
/// return a `Terminated` — a missing `return` is a compile error of the
/// generator. Only the block itself issues its token, and once issued the
/// block accepts no more statements.
///
/// @param <R> the result type of the enclosing method or lambda
public final class Terminated<R> {
    private final Block<?, ?> issuer;

    Terminated(Block<?, ?> issuer) {
        this.issuer = issuer;
    }

    Block<?, ?> issuer() {
        return issuer;
    }
}
