package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.SuperCtorRef0;
import me.supcheg.javafile.facts.SuperCtorRef1;
import me.supcheg.javafile.facts.SuperCtorRef2;
import me.supcheg.javafile.typed.TypedClassBuilder.InstanceBody0;

import java.util.List;

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
public final class SuperSite<Self, S> {
    private final Superclass superclass;
    private final VoidBody root;
    private final This<Self> self;

    SuperSite(Superclass superclass, VoidBody root, This<Self> self) {
        this.superclass = superclass;
        this.root = root;
        this.self = self;
    }

    /// `super();` by a constructor a subclass alone calls, then the rest of the body.
    ///
    /// @param ctor the constructor of the superclass
    /// @param rest what follows the call, given its block and `this`
    /// @return the proof that the body ended
    /// @throws IllegalStateException if `super_` was called already, or the constructor of the
    ///     superclass throws a checked exception the constructor does not declare
    public Terminated<Void> super_(SuperCtorRef0<S> ctor, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, List.of(), rest);
    }

    /// `super(a1);` by a constructor a subclass alone calls, then the rest of the body.
    ///
    /// @param ctor the constructor of the superclass
    /// @param a1 the argument
    /// @param rest what follows the call, given its block and `this`
    /// @param <A1> the parameter type
    /// @return the proof that the body ended
    /// @throws IllegalStateException as [#super_(SuperCtorRef0, InstanceBody0)], and if the argument
    ///     uses a variable out of scope or throws a checked exception the constructor does not declare
    public <A1> Terminated<Void> super_(
            SuperCtorRef1<S, A1> ctor, Expr<? extends A1> a1, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1), rest);
    }

    /// `super(a1, a2);` by a constructor a subclass alone calls, then the rest of the body.
    ///
    /// @param ctor the constructor of the superclass
    /// @param a1 the first argument
    /// @param a2 the second argument
    /// @param rest what follows the call, given its block and `this`
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the proof that the body ended
    /// @throws IllegalStateException as [#super_(SuperCtorRef1, Expr, InstanceBody0)]
    public <A1, A2> Terminated<Void> super_(
            SuperCtorRef2<S, A1, A2> ctor,
            Expr<? extends A1> a1,
            Expr<? extends A2> a2,
            InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2), rest);
    }

    /// `super();` by a `public` constructor of a superclass that is not abstract, then the rest of
    /// the body.
    ///
    /// @param ctor the constructor of the superclass
    /// @param rest what follows the call, given its block and `this`
    /// @return the proof that the body ended
    /// @throws IllegalStateException as [#super_(SuperCtorRef0, InstanceBody0)]
    public Terminated<Void> super_(CtorRef0<S> ctor, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, List.of(), rest);
    }

    /// `super(a1);` by a `public` constructor of a superclass that is not abstract, then the rest
    /// of the body.
    ///
    /// @param ctor the constructor of the superclass
    /// @param a1 the argument
    /// @param rest what follows the call, given its block and `this`
    /// @param <A1> the parameter type
    /// @return the proof that the body ended
    /// @throws IllegalStateException as [#super_(SuperCtorRef1, Expr, InstanceBody0)]
    public <A1> Terminated<Void> super_(
            CtorRef1<S, A1> ctor, Expr<? extends A1> a1, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1), rest);
    }

    /// `super(a1, a2);` by a `public` constructor of a superclass that is not abstract, then the
    /// rest of the body.
    ///
    /// @param ctor the constructor of the superclass
    /// @param a1 the first argument
    /// @param a2 the second argument
    /// @param rest what follows the call, given its block and `this`
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the proof that the body ended
    /// @throws IllegalStateException as [#super_(SuperCtorRef1, Expr, InstanceBody0)]
    public <A1, A2> Terminated<Void> super_(
            CtorRef2<S, A1, A2> ctor,
            Expr<? extends A1> a1,
            Expr<? extends A2> a2,
            InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2), rest);
    }

    /// Whether the body called `super_`: the call is its first statement.
    boolean called() {
        return root.instrs().stream()
                .findFirst()
                .filter(Instr.SuperInit.class::isInstance)
                .isPresent();
    }

    private Terminated<Void> call(Invocable ctor, List<Node.Operand> args, InstanceBody0<Self, VoidBody, Void> rest) {
        if (!root.instrs().isEmpty()) {
            throw new IllegalStateException("the " + root.path() + " has called super(...) already: a"
                    + " constructor begins with one call of a constructor of its superclass (JLS 8.8.7)");
        }
        if (!Tokens.sameType(ctor.owner(), superclass.token())) {
            throw new IllegalArgumentException(ctor + " is not a constructor of " + superclass.token()
                    + ", the superclass: super(...) calls a constructor of the direct superclass (JLS 8.8.7.1)");
        }
        root.append(new Instr.SuperInit(ctor, args));
        return rest.build(root, self);
    }
}
