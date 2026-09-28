package me.supcheg.javafile.typed;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.Map;

/// Assigns deterministic, collision-free names to [Var]s during lowering
/// (§6.2), in a stack of lexical scopes that mirrors the rendered blocks. A
/// variable's name is fixed the moment lowering passes its declaration site
/// (a parameter, `let`/`letVar`, a loop variable, a pattern binding, or a
/// `catch` binding) and is visible until the scope it was declared in is
/// left; any [Node.Local] reached in between looks the name up by identity.
///
/// The scope check of [ScopeCheck] already rejects a variable used outside
/// its block when the statement is built; this is defence in depth. A
/// [Node.Local] whose [Var] is not in any open scope — never declared, or
/// declared in a scope already left — is rejected instead of rendering a
/// dangling reference.
final class NameEnv {
    private final Deque<Map<Var<?>, String>> scopes = new ArrayDeque<>();
    private int counter;

    NameEnv() {
        push();
    }

    /// Enters a nested scope.
    void push() {
        scopes.push(new IdentityHashMap<>());
    }

    /// Leaves the innermost scope; its variables are no longer visible.
    void pop() {
        scopes.pop();
    }

    /// Declares a variable in the innermost scope and returns its assigned name.
    String declare(Var<?> var) {
        String name = "v" + (counter++);
        scopes.element().put(var, name);
        return name;
    }

    /// Looks up the name of a variable visible in the current scope.
    String nameOf(Var<?> var) {
        for (Map<Var<?>, String> scope : scopes) {
            String name = scope.get(var);
            if (name != null) {
                return name;
            }
        }
        throw new IllegalStateException(var + " is not in scope where it is used: it was never declared in this"
                + " body, or its declaration's scope has already ended (it escaped the lambda that introduced it)");
    }
}
