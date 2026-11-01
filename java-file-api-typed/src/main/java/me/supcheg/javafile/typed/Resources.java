package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/// The resources of a [Block#tryWithResources] being built: variables declared with their initializer,
/// and at most one existing effectively-final variable. Each is a subtype of `java.lang.AutoCloseable`,
/// and its close runs on every way out of the try block.
///
/// The variables are handed back as they are declared, so the body of the try uses them, and an
/// initializer uses the resources declared before it, as in Java (JLS 14.20.3).
public final class Resources {
    private final Block<?, ?> body;
    private final List<Instr.Resource> resources = new ArrayList<>();
    private boolean existing;
    private boolean closed;

    Resources(Block<?, ?> body) {
        this.body = body;
    }

    /// Declares a resource with its initializer, `T name = init`, and returns the variable, which the
    /// body of the try uses.
    ///
    /// @param type the declared resource type, a subtype of `java.lang.AutoCloseable`
    /// @param init the initializer
    /// @param <T> the resource type
    /// @return the resource variable
    /// @throws IllegalArgumentException if `type` is not `AutoCloseable`, or `init` is primitive where
    ///     `type` is a reference, which Java would box
    /// @throws IllegalStateException if the resources of the try are complete already
    public <T> Var<T> resource_(TypeToken<T> type, Expr<? extends T> init) {
        requireOpen();
        Tokens.requireAutoCloseable(type, "resource_");
        Tokens.requireNoBoxing(init.type(), type, "resource_");
        Var<T> var = new Var<>(type, Var.Role.Fixed.RESOURCE, body);
        ScopeCheck.check(new Instr.Let(var, init.node()), body);
        Exceptions.check(new Instr.Let(var, init.node()), body);
        resources.add(new Instr.Resource(var, Optional.of(init.node())));
        return var;
    }

    /// References an existing effectively-final variable as a resource, `try (name)`. At most one
    /// resource of a try is an existing variable.
    ///
    /// @param var the existing variable
    /// @param <T> the resource type
    /// @return the variable, for the body of the try
    /// @throws IllegalArgumentException if `var` is not `AutoCloseable`, or is mutable
    /// @throws IllegalStateException if the resources of the try are complete, or one of them already
    ///     is an existing variable
    public <T> Var<T> resource_(Var<T> var) {
        requireOpen();
        if (existing) {
            throw new IllegalStateException(
                    "a try has at most one resource that is an existing variable (JLS 14.20.3): " + var
                            + " is another");
        }
        if (var instanceof MutVar<?>) {
            throw new IllegalArgumentException("a try references an effectively-final variable as a resource, and "
                    + var + " is mutable: declare it with let, not letVar");
        }
        Tokens.requireAutoCloseable(var.type(), "resource_");
        ScopeCheck.check(new Instr.Exec(var.node()), body);
        resources.add(new Instr.Resource(var, Optional.empty()));
        existing = true;
        return var;
    }

    /// The resources, complete: none can be added afterwards.
    ///
    /// @return the resources, in declaration order
    /// @throws IllegalStateException if the try declares no resource
    List<Instr.Resource> close() {
        requireOpen();
        if (resources.isEmpty()) {
            throw new IllegalStateException("tryWithResources requires at least one resource");
        }
        closed = true;
        return List.copyOf(resources);
    }

    private void requireOpen() {
        if (closed) {
            throw new IllegalStateException(
                    "the resources of the try are complete: a resource is declared by its body, before it runs");
        }
    }
}
