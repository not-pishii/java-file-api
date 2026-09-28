package me.supcheg.javafile.typed;

/// An assignment to a mutable variable, a non-final field, or an array
/// element, created by the `assign` combinators of [Expressions]. It is an
/// [Effect], never a value.
public final class Assignment implements Effect {
    private final Node.Assign node;

    Assignment(Node.Assign node) {
        this.node = node;
    }

    Node.Assign node() {
        return node;
    }

    static Node nodeOf(Effect effect) {
        return switch (effect) {
            case Invocation<?> invocation -> invocation.node();
            case VoidInvocation invocation -> invocation.node();
            case Assignment assignment -> assignment.node();
        };
    }
}
