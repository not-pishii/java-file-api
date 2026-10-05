package me.supcheg.javafile.facts.processor.harness;

import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.typed.Expr;
import me.supcheg.javafile.typed.TypedJavaFile;

import java.util.List;
import java.util.function.Function;

/// The way of the facts through the typed layer, for the `use/` code of a
/// fixture: a check that takes a `Typed` builds an expression out of the
/// facts, and what it renders is compiled by javac and run by the JVM.
///
/// The typed layer renders inside a compilation whose classpath is the
/// library alone, against the target classpath of that compilation
/// (`TargetClasspaths.of`), as a generator that is an annotation processor
/// does: every metamodel the expression uses is checked against the library.
/// [#against] gives the same against another version of the library than
/// the one the metamodels were generated from.
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

    /// The source of class `out.Out` as `spec` declares it, rendered as [#render(TypeToken, TypeToken,
    /// Function)] renders: against the target classpath of a compilation that has the library alone.
    /// For what one static method that returns an expression does not show — a `throws` clause, a
    /// `try`.
    ///
    /// @param spec declares the members of the class
    /// @return the source
    String render(TypedJavaFile.TypedClassSpec spec);

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

    /// What the target classpath was asked while [#render] rendered, and
    /// what it found: a line per metamodel it read, in the order it read
    /// them — `p.Svc: unchanged` for the type the metamodel was generated
    /// from, `p.Svc: changed` for another one the metamodel holds of,
    /// `p.Svc: mismatched` for one it does not hold of, after which nothing
    /// more is read. A metamodel read twice is there twice.
    ///
    /// @param result the type of the result
    /// @param parameter the type of the parameter
    /// @param body the expression of the parameter to return
    /// @param <R> the type of the result
    /// @param <P> the type of the parameter
    /// @return the lines
    <R, P> List<String> verified(TypeToken<R> result, TypeToken<P> parameter, Function<Expr<P>, Expr<R>> body);

    /// The typed layer against another version of the library:
    /// `targets/<version>/` of the case, whose files replace those of the
    /// library with the same path or add to it, and take a file out with an
    /// empty one. The metamodels stay the ones generated from the library
    /// of the case; what is rendered is checked against the version,
    /// compiled against it and run with it.
    ///
    /// An argument of [#apply] is then of the JDK: an object of the library
    /// the checks were compiled with is of the other version. Make what
    /// the body needs of the library in the body.
    ///
    /// @param version the name of the version, a directory of `targets/`
    /// @return the typed layer against that version
    Typed against(String version);
}
