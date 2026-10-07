package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.TypeToken;

/// A variable of the generated code: a local, a parameter, a lambda
/// parameter, a loop variable, a pattern binding, or a caught exception
/// (§6.2).
///
/// A variable is only ever handed to the lambda that is its scope, so using
/// it before its declaration is a compile error of the generator. Every
/// variable knows the block that owns it — the block its declaration is in
/// scope for — and a statement that refers to it can be appended only to that
/// block or a block nested in it: a variable that escapes its lambda anyway
/// (stored in a field or a list and used later, in a sibling branch, after
/// its block, or in another method) is rejected when that statement is
/// built, with the stack pointing at the misuse. Names are assigned by
/// lowering, deterministically and without collisions.
///
/// A plain `Var` cannot be assigned, so it is effectively final and a lambda
/// may capture it; a [MutVar] can be assigned and cannot be captured.
///
/// @param <T> the Java type of the variable
public sealed class Var<T> extends Expr<T> permits MutVar {
    /// The node handed to the superclass, never read: [#node()] is overridden.
    private static final Node UNUSED = new Node.RawLit(me.supcheg.javafile.code.Exprs.literalNull());

    /// The role of the variable of a loop.
    static final String LOOP_VARIABLE = "loop variable";

    private final Node local;
    private final String role;
    private final Block<?, ?> owner;

    Var(TypeToken<T> type, String role, Block<?, ?> owner) {
        super(UNUSED, type);
        this.local = new Node.Local(this);
        this.role = role;
        this.owner = owner;
    }

    static <T> Var<T> param(TypeToken<T> type, Block<?, ?> owner) {
        return new Var<>(type, "parameter", owner);
    }

    /// Whether this is the variable of a `for` loop, in scope in the
    /// condition and the update of the loop as in its body: the loop
    /// variable that can be assigned, as that of a `for`-each loop cannot.
    boolean isVariableOfFor() {
        return this instanceof MutVar<?> && role.equals(LOOP_VARIABLE);
    }

    /// The block this variable is in scope for.
    Block<?, ?> owner() {
        return owner;
    }

    @Override
    Node node() {
        return local;
    }

    @Override
    public String toString() {
        return role + " of type " + type();
    }
}
