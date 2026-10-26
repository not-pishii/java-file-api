package me.supcheg.javafile.typed;

import me.supcheg.javafile.Invariants;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody0;

import java.util.List;
import java.util.Optional;

/// The place of `super(...)` in a constructor of a class that extends `S`:
/// what the body of the constructor is given, and the only thing it can end
/// by.
///
/// ```java
/// (ctor, name) -> ctor.super_(base.super_String, name, (b, self) -> b.end())
/// ```
///
/// `super_` takes a constructor of `S` — a `SuperCtorRefN<S, ...>`, of an
/// abstract class or `protected`, or a `CtorRefN<S, ...>` — its arguments,
/// and what follows the call: the rest of the body, given its block and
/// `this`. So `this` does not exist in the arguments of `super(...)` (JLS
/// 8.8.7.1), the call is the first statement, and there is exactly one. The
/// arguments are in the scope of the parameters of the constructor, and what
/// they and the constructor of `S` throw is caught nowhere: the constructor
/// declares it (JLS 11.2.2).
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class SuperSite<Self, S> extends SuperSiteArities<Self, S> {
    private final Superclass superclass;
    private final VoidBody root;
    private final This<Self> self;
    private Phase phase = new Phase.Waiting();

    /// Whether the body has called `super(...)`.
    private sealed interface Phase {
        /// Not yet.
        record Waiting() implements Phase {}

        /// It has, by this invocation.
        ///
        /// @param invocation the invocation
        record Invoked(SuperInvocation invocation) implements Phase {}
    }

    SuperSite(Superclass superclass, VoidBody root, This<Self> self) {
        this.superclass = superclass;
        this.root = root;
        this.self = self;
    }

    @Override
    Terminated<Void> call(
            Invocable ctor, List<Node.Operand> args, Node.Passing passing, InstanceBody0<Self, VoidBody, Void> rest) {
        if (phase instanceof Phase.Invoked) {
            throw new IllegalStateException("the " + root.path() + " has called super(...) already: a"
                    + " constructor begins with one call of a constructor of its superclass (JLS 8.8.7)");
        }
        if (!Tokens.sameType(ctor.owner(), superclass.token())) {
            throw new IllegalArgumentException(ctor + " is not a constructor of " + superclass.token()
                    + ", the superclass: super(...) calls a constructor of the direct superclass (JLS 8.8.7.1)");
        }
        SuperInvocation invocation = new SuperInvocation(ctor, args, passing);
        root.requireOpen();
        ScopeCheck.check(invocation, root);
        Exceptions.check(invocation, root);
        phase = new Phase.Invoked(invocation);
        return rest.build(root, self);
    }

    /// The invocation the body began with: the body ends by what follows it alone, so once it is
    /// built, it has.
    SuperInvocation invocation() {
        return Invariants.present(
                phase instanceof Phase.Invoked(var invocation) ? Optional.of(invocation) : Optional.empty(),
                "the %s ended without super(...), which alone ends it",
                root.path());
    }
}
