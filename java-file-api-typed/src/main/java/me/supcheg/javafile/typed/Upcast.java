package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.RefToken;

/// The witness that the class `Self` being declared is a subtype of `X`:
/// what declaring a supertype of the class gives, in place of a bound
/// `Self extends X` the brand of the class cannot have.
///
/// ```java
/// Upcast<Self, Comparable<Self>> asComparable = oh.implements_(cmp.implement(h.self()).compareTo_T(compareTo));
/// ...
/// call(asComparable.apply(self), cmp.compareTo_T, other)
/// ```
///
/// [#apply(Expr)] changes the type of an expression and nothing in the
/// rendered code: `this` stays `this`. Lowering knows the expression is
/// still of the class to javac, and renders a member reached through the
/// witness so that javac resolves the one the fact names — through a cast to
/// `X` where the class has an overload of its own that javac would prefer.
///
/// @param <Self> the class being declared
/// @param <X> a supertype of it
public final class Upcast<Self, X> {
    private final RefToken<Self> self;
    private final RefToken<X> supertype;

    Upcast(RefToken<Self> self, RefToken<X> supertype) {
        this.self = self;
        this.supertype = supertype;
    }

    /// The supertype the witness is of.
    ///
    /// @return the token of the supertype
    public RefToken<X> supertype() {
        return supertype;
    }

    /// A class that is an exception by a witness alone, as the exception class a `throws`
    /// clause, a `catch` or a `throw` takes: the class being declared, whose brand has no bound
    /// `extends Throwable` to tell it.
    ///
    /// @param witness the witness that the class is a `Throwable`
    /// @param type the class
    /// @param <E> the class
    /// @return `type`, as the exception class it is
    /// @throws IllegalArgumentException if the witness is of another class than `type`
    static <E> ClassToken<? extends Throwable> throwable(Upcast<E, ? extends Throwable> witness, ClassToken<E> type) {
        if (!Tokens.sameType(witness.self, type)) {
            throw new IllegalArgumentException(
                    "the witness that " + witness + " is no witness that " + type + " is an exception");
        }
        // The witness is that E is a subtype of Throwable, which the brand E cannot say.
        @SuppressWarnings("unchecked")
        ClassToken<? extends Throwable> exception = (ClassToken<? extends Throwable>) (ClassToken<?>) type;
        return exception;
    }

    /// `expr` as an expression of the supertype; rendered as `expr` itself.
    ///
    /// @param expr an expression of the class
    /// @return the same expression, of the supertype
    public Expr<X> apply(Expr<? extends Self> expr) {
        return Expr.of(new Node.Upcast(expr.node(), expr.type(), supertype), supertype);
    }

    @Override
    public String toString() {
        return self + " is a " + supertype;
    }
}
