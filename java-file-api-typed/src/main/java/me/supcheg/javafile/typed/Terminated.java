package me.supcheg.javafile.typed;

/// The proof that a block ended (§6.3): with `return`, `throw`, `break`,
/// `continue`, `yield`, or a construct that cannot complete normally (JLS 14.22) —
/// an `ifElse` whose branches both end, a `tryTerminated`, a `loopForever`.
/// For `void` code, [VoidBody#end()] also gives it.
///
/// A method, constructor, or block lambda body is a function that must
/// return a `Terminated` — a missing `return` is a compile error of the
/// generator. Only the block itself issues its token, and once issued the
/// block accepts no more statements.
///
/// A token proves the end of the one block that issued it. Java types cannot
/// brand every block, so a token of another block — a sibling branch, a
/// nested block, another method — is rejected where it is handed back, when
/// the construct is built.
///
/// @param <R> the result type of the enclosing method or lambda, or the type of the `switch` expression
///     the block is of
public final class Terminated<R> {
    private final Block<?, ?> issuer;

    Terminated(Block<?, ?> issuer) {
        this.issuer = issuer;
    }

    Block<?, ?> issuer() {
        return issuer;
    }
}
