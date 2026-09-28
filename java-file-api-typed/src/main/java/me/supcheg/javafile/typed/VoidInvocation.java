package me.supcheg.javafile.typed;

/// A call of a `void` method. It is an [Effect] but not an [Expr], so a
/// `void` call can never be used as a value.
public final class VoidInvocation implements Effect {
    private final Node node;

    VoidInvocation(Node node) {
        this.node = node;
    }

    Node node() {
        return node;
    }
}
