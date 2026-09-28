package me.supcheg.javafile.typed;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Supplier;

/// The stack of scopes being built on this thread.
///
/// Only the innermost scope accepts statements. Building a nested block or a
/// lambda pushes a scope, so using the builder of an enclosing block inside
/// it — which would put statements in the wrong place — fails fast.
final class Scopes {
    private static final ThreadLocal<Deque<Object>> STACK = ThreadLocal.withInitial(ArrayDeque::new);

    private Scopes() {}

    static <X> X within(Object scope, Supplier<X> action) {
        Deque<Object> stack = STACK.get();
        stack.push(scope);
        try {
            return action.get();
        } finally {
            stack.pop();
        }
    }

    static void requireInnermost(Object scope, String what) {
        if (STACK.get().peek() != scope) {
            throw new IllegalStateException(what
                    + " is not the innermost open scope: a builder of an enclosing block was used inside a"
                    + " nested block or lambda, or a builder was used after its scope ended");
        }
    }
}
