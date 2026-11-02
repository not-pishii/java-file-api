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
// the parameter is the phantom of the result of the method or block, which the token is held to
@SuppressWarnings("UnusedTypeParameter")
public final class Terminated<R> {
    private final Block<?, ?> issuer;
    private final boolean completesNormally;

    Terminated(Block<?, ?> issuer, boolean completesNormally) {
        this.issuer = issuer;
        this.completesNormally = completesNormally;
    }

    Block<?, ?> issuer() {
        return issuer;
    }

    /// Whether the block that issued this token completes normally: it ended with `end()` rather than with a
    /// statement that cannot complete normally (JLS 14.22). An initializer block must complete normally, so the
    /// bodies of its type refuse a token that does not.
    ///
    /// @return whether the block completes normally
    boolean completesNormally() {
        return completesNormally;
    }
}
