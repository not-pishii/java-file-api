package me.supcheg.javafile.typed;

import java.util.function.Supplier;

/// The innermost scope being built, bound for the duration of [#within].
///
/// Only the innermost scope accepts statements. Building a nested block or a
/// lambda rebinds it, so using the builder of an enclosing block inside
/// it — which would put statements in the wrong place — fails fast. While
/// any scope is open, a method body is being built, so no class or member
/// may be declared (§9: a declaration from inside a method body).
final class Scopes {
    private static final ScopedValue<Object> SCOPE = ScopedValue.newInstance();

    private Scopes() {}

    static <X> X within(Object scope, Supplier<X> action) {
        return ScopedValue.where(SCOPE, scope).call(action::get);
    }

    static void requireInnermost(Object scope, String what) {
        if (!SCOPE.isBound() || SCOPE.get() != scope) {
            throw new IllegalStateException(what
                    + " is not the innermost open scope: a builder of an enclosing block was used inside a"
                    + " nested block or lambda, or a builder was used after its scope ended");
        }
    }

    /// Rejects a declaration while a method body is being built.
    ///
    /// @param what the declaration, for the message
    static void requireNoneOpen(String what) {
        if (SCOPE.isBound()) {
            Object innermost = SCOPE.get();
            String where = innermost instanceof Block<?, ?> block ? block.path() : innermost.toString();
            throw new IllegalStateException("cannot declare " + what + " while the " + where
                    + " is being built: a class or member is declared outside of any method body, not from"
                    + " inside one");
        }
    }
}
