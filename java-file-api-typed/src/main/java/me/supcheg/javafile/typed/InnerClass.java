package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.CtorRef3;
import me.supcheg.javafile.facts.CtorRef4;
import me.supcheg.javafile.facts.FinalClassToken;

import java.util.List;

/// The declaration of an inner member class, `Inner`, and the two expressions
/// that only an inner class has (§6.5): the enclosing instance of its body,
/// `Outer.this` ([#outerThis()]), and an instance created from an outer
/// instance, `outer.new Inner(...)` ([#new_]). The handle is made by
/// [MemberBuilder#declareInnerClass], so a handle exists for an inner class
/// alone; a `static` member class has none.
///
/// @param <Outer> the class the inner class is declared in
/// @param <Inner> the inner class
public final class InnerClass<Outer, Inner> {
    private final FinalClassToken<Outer> outer;
    private final FinalClassToken<Inner> inner;

    InnerClass(FinalClassToken<Outer> outer, FinalClassToken<Inner> inner) {
        this.outer = outer;
        this.inner = inner;
    }

    /// The token of the inner class, as the type of its expressions and members.
    ///
    /// @return the token
    public FinalClassToken<Inner> token() {
        return inner;
    }

    /// `Outer.this`: the instance of the enclosing class from the body of the inner class (JLS 15.8.4).
    ///
    /// @return the enclosing instance
    public Expr<Outer> outerThis() {
        return Expr.of(new Node.OuterThis(outer), outer);
    }

    /// Creates an inner instance from an outer instance, `outer.new Inner()`, by the fact of the
    /// constructor of the inner class.
    ///
    /// @param enclosing the instance of the enclosing class
    /// @param ctor the constructor of the inner class
    /// @return the instance
    public Expr<Inner> new_(Expr<? extends Outer> enclosing, CtorRef0<Inner> ctor) {
        return innerNew(enclosing, ctor, List.of());
    }

    /// Creates an inner instance from an outer instance, `outer.new Inner()`, by a fact of the
    /// constructor of the inner class the generator holds as an [me.supcheg.javafile.facts.Invocable]:
    /// the owner of the fact is held to the inner class.
    ///
    /// @param enclosing the instance of the enclosing class
    /// @param ctor the constructor of the inner class
    /// @return the instance
    /// @throws IllegalArgumentException if `ctor` is no constructor of the inner class
    public Expr<Inner> new_(Expr<? extends Outer> enclosing, me.supcheg.javafile.facts.Invocable ctor) {
        return innerNew(enclosing, ctor, List.of());
    }

    /// Creates an inner instance with one argument, `outer.new Inner(a1)`.
    ///
    /// @param enclosing the instance of the enclosing class
    /// @param ctor the constructor of the inner class
    /// @param a1 the argument
    /// @param <A1> the type of the argument
    /// @return the instance
    public <A1> Expr<Inner> new_(Expr<? extends Outer> enclosing, CtorRef1<Inner, A1> ctor, Expr<? extends A1> a1) {
        return innerNew(enclosing, ctor, List.of(a1));
    }

    /// Creates an inner instance with two arguments, see [#new_(Expr, CtorRef1, Expr)].
    ///
    /// @param enclosing the instance of the enclosing class
    /// @param ctor the constructor of the inner class
    /// @param a1 the first argument
    /// @param a2 the second argument
    /// @param <A1> the type of the first argument
    /// @param <A2> the type of the second argument
    /// @return the instance
    public <A1, A2> Expr<Inner> new_(
            Expr<? extends Outer> enclosing,
            CtorRef2<Inner, A1, A2> ctor,
            Expr<? extends A1> a1,
            Expr<? extends A2> a2) {
        return innerNew(enclosing, ctor, List.of(a1, a2));
    }

    /// Creates an inner instance with three arguments, see [#new_(Expr, CtorRef1, Expr)].
    ///
    /// @param enclosing the instance of the enclosing class
    /// @param ctor the constructor of the inner class
    /// @param a1 the first argument
    /// @param a2 the second argument
    /// @param a3 the third argument
    /// @param <A1> the type of the first argument
    /// @param <A2> the type of the second argument
    /// @param <A3> the type of the third argument
    /// @return the instance
    public <A1, A2, A3> Expr<Inner> new_(
            Expr<? extends Outer> enclosing,
            CtorRef3<Inner, A1, A2, A3> ctor,
            Expr<? extends A1> a1,
            Expr<? extends A2> a2,
            Expr<? extends A3> a3) {
        return innerNew(enclosing, ctor, List.of(a1, a2, a3));
    }

    /// Creates an inner instance with four arguments, see [#new_(Expr, CtorRef1, Expr)].
    ///
    /// @param enclosing the instance of the enclosing class
    /// @param ctor the constructor of the inner class
    /// @param a1 the first argument
    /// @param a2 the second argument
    /// @param a3 the third argument
    /// @param a4 the fourth argument
    /// @param <A1> the type of the first argument
    /// @param <A2> the type of the second argument
    /// @param <A3> the type of the third argument
    /// @param <A4> the type of the fourth argument
    /// @return the instance
    public <A1, A2, A3, A4> Expr<Inner> new_(
            Expr<? extends Outer> enclosing,
            CtorRef4<Inner, A1, A2, A3, A4> ctor,
            Expr<? extends A1> a1,
            Expr<? extends A2> a2,
            Expr<? extends A3> a3,
            Expr<? extends A4> a4) {
        return innerNew(enclosing, ctor, List.of(a1, a2, a3, a4));
    }

    private Expr<Inner> innerNew(
            Expr<? extends Outer> enclosing, me.supcheg.javafile.facts.Invocable ctor, List<Expr<?>> args) {
        if (!Tokens.sameType(ctor.owner(), inner)) {
            throw new IllegalArgumentException("the constructor " + ctor + " is not one of " + inner
                    + ": an inner instance is created by the constructor of the inner class");
        }
        if (ctor.kind() != me.supcheg.javafile.facts.InvocableKind.CONSTRUCTOR) {
            throw new IllegalArgumentException(ctor + " is no constructor: an inner instance is created by one");
        }
        return Expr.of(
                new Node.InnerNew(
                        new Node.Operand(enclosing.node(), enclosing.type()),
                        ctor,
                        args.stream()
                                .map(argument -> new Node.Operand(argument.node(), argument.type()))
                                .toList(),
                        Node.Passing.EACH),
                inner);
    }
}
