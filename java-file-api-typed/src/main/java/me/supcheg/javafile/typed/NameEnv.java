package me.supcheg.javafile.typed;

import java.util.IdentityHashMap;
import java.util.Map;

/// Assigns deterministic, collision-free names to [Var]s during lowering
/// (§6.2). A variable's name is fixed the moment lowering passes its
/// declaration site (a parameter, `let`/`letVar`, a loop variable, a pattern
/// binding, or a `catch` binding); any [Node.Local] reached afterward looks
/// the name up by identity.
///
/// If a [Node.Local] is reached whose [Var] was never declared through this
/// environment, the variable escaped the lexical scope that was supposed to
/// be its only means of reference (e.g. it was captured and stored outside
/// the HOAS lambda that introduced it) — lowering rejects this instead of
/// rendering a dangling reference.
final class NameEnv {
    private final Map<Var<?>, String> names = new IdentityHashMap<>();
    private int counter;

    /// Declares a variable and returns its assigned name.
    String declare(Var<?> var) {
        String name = "v" + (counter++);
        names.put(var, name);
        return name;
    }

    /// Looks up a previously declared variable's name.
    String nameOf(Var<?> var) {
        String name = names.get(var);
        if (name == null) {
            throw new IllegalStateException(
                    var + " is used before its declaration was lowered, or it escaped the scope of the lambda"
                            + " that introduced it (stored and used outside that lambda)");
        }
        return name;
    }
}
