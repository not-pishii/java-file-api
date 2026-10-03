package me.supcheg.javafile.facts.processor.harness;

import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.typed.Expr;

import java.util.function.Function;

/// The way of the facts through the typed layer, for the `use/` code of a
/// fixture: a check that takes a `Typed` builds an expression out of the
/// facts, and what it renders is compiled by javac and run by the JVM.
///
/// ```java
/// public static void anAdoptedMethodIsCalled(Typed typed) {
///     assertThat(typed.apply(String_.TOKEN, Pub_.TOKEN, pub -> call(pub, Pub_.near), new Pub()))
///             .isEqualTo("near");
/// }
/// ```
public interface Typed {

    /// The source [#apply] compiles: class `out.Out` with `public static R go(P p)`
    /// that returns `body` of its parameter. Nothing is compiled or run, so the
    /// source can be asserted on, and a fact the typed layer rejects — one that
    /// javac would resolve to another member — fails here, with its exception.
    ///
    /// @param result the type of the result
    /// @param parameter the type of the parameter
    /// @param body the expression of the parameter to return
    /// @param <R> the type of the result
    /// @param <P> the type of the parameter
    /// @return the source
    <R, P> String render(TypeToken<R> result, TypeToken<P> parameter, Function<Expr<P>, Expr<R>> body);

    /// Renders class `out.Out` with `public static R go(P p)` that returns
    /// `body` of its parameter, compiles it under every lint with warnings
    /// as errors against the library of the fixture alone — it is code of
    /// another package, that knows nothing of the facts — and calls it.
    ///
    /// @param result the type of the result
    /// @param parameter the type of the parameter
    /// @param body the expression of the parameter to return
    /// @param argument what to call the method with
    /// @param <R> the type of the result
    /// @param <P> the type of the parameter
    /// @return what the method returned, boxed if it is of a primitive type
    <R, P> Object apply(TypeToken<R> result, TypeToken<P> parameter, Function<Expr<P>, Expr<R>> body, P argument);
}
