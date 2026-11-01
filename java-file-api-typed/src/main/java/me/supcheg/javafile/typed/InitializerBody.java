package me.supcheg.javafile.typed;

/// The block that is an initializer block body (§6.3): it ends with [#end()],
/// or with a statement that cannot complete normally, and has no `return` -
/// javac rejects a `return` in an initializer block (JLS 14.17), so the typed
/// layer does not write one.
public final class InitializerBody extends Block<Void, InitializerBody> {

    private InitializerBody(Enclosing enclosing, Nesting nesting, String what, ExceptionScope exceptionScope) {
        super(enclosing, nesting, what, exceptionScope);
    }

    /// The body of an initializer block: the root of a scope tree.
    ///
    /// @param what the block, for diagnostics
    /// @param exceptionScope what the body does with a checked exception
    static InitializerBody root(String what, ExceptionScope exceptionScope) {
        return new InitializerBody(new Enclosing.Root(), Nesting.PLAIN, what, exceptionScope);
    }

    @Override
    InitializerBody self() {
        return this;
    }

    @Override
    InitializerBody child(String what, Nesting nesting, ExceptionScope exceptionScope) {
        return new InitializerBody(new Enclosing.In(this), nesting, what, exceptionScope);
    }

    /// Ends this block without an explicit `return;`, e.g. when the body
    /// simply falls off the end. Unlike the other ways to end a block, the
    /// block still completes normally: in a nested block this just closes
    /// it, and the enclosing construct is continued or ended as usual.
    ///
    /// @return the proof that this block ended
    public Terminated<Void> end() {
        return endHere("end()");
    }
}
