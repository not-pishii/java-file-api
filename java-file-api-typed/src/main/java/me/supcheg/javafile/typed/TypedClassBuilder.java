package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;

import java.util.List;

/// Builds a typed `final` class declaration (§6.5), branded with `Self`.
///
/// **Declaring is introducing.** Declaring a member returns its fact —
/// `MethodRef1<Self, R, A1>`, `CtorRef0<Self>`, `FieldRef<Self, T>` — the
/// only way to obtain a fact of `Self` without `UnsafeFacts`.
///
/// **Declare, then define.** A method or constructor is declared first,
/// which gives its fact, and defined later with a body; any body may use the
/// fact of any member declared before it is built, so recursion and mutual
/// calls are expressible:
///
/// ```java
/// var isEven = cb.declareMethod("isEven", PrimitiveToken.BOOLEAN, PrimitiveToken.INT);
/// var isOdd = cb.declareMethod("isOdd", PrimitiveToken.BOOLEAN, PrimitiveToken.INT);
/// cb.define(isEven, (b, self, n) -> b.ifElse(eqInt(n, literal(0)),
///         t -> t.return_(literal(true)),
///         e -> e.return_(call(self, isOdd, subInt(n, literal(1))))));
/// cb.define(isOdd, (b, self, n) -> ...);
/// ```
///
/// When nothing needs the fact before the body, `method`, `voidMethod`,
/// `staticMethod`, `voidStaticMethod` and `constructor` declare and define in
/// one call. Every declared member must be defined exactly once: defining it
/// twice is rejected at the second `define`, and a member left undefined is
/// rejected when the class declaration completes. `define` takes the fact of
/// a member of `Self` only, so the fact of another class does not compile; a
/// static member's fact carries no owner brand, and one not declared by this
/// builder is rejected when defined.
///
/// **`this` is a parameter.** The body of an instance method or constructor
/// receives `this` as an `Expr<Self>` HOAS parameter, next to its block; a
/// static method body does not. `this` is in scope in that body only —
/// lambdas in it included — and using it anywhere else, e.g. smuggled into
/// the body of another member or a field initializer, is rejected when the
/// statement is built (§6.2).
///
/// **Declarations match their facts.** The class renders `public final`,
/// as its [FinalClassToken] says. A [#field] is `public final` and has an
/// initializer — a blank `final` field, definitely assigned in every
/// constructor, is not supported yet; a [#mutableField] is `public`, and
/// only its [MutableFieldRef] can be assigned. Methods are `public` and,
/// the class being final, never overridden. Once the declaration completes,
/// [FinalClassToken#methods()] of [#self()] lists the declared instance
/// methods and those inherited from `Object`; asking earlier is rejected.
///
/// **Checked exceptions.** A method or constructor declares the exceptions
/// of its `throws` clause through [#throwing]: `cb.throwing(IOException_.TOKEN)`
/// declares methods and constructors as this builder does ([Declarations]),
/// each with `throws IOException`. The fact of such a member carries the clause, so a
/// call of it is checked like a call of any other fact, and its body may
/// throw what the clause covers; a body that throws a checked exception it
/// neither catches nor declares is rejected at the statement that throws
/// (see [Block]). A field initializer can throw no checked exception.
///
/// **Signatures.** A member that javac would reject is rejected when it is
/// declared: a duplicate by erasure, a method with the signature of one the
/// class inherits (§9.7; see [SignatureRegistry]).
///
/// **Overriding is by the fact.** A method the class inherits is overridden
/// by naming its fact, never by declaring a method of its name:
/// `cb.override(Object_.toString, (b, self) -> ...)` declares and defines
/// `@Override public String toString()`. A method declared by name that
/// would override or hide an inherited one — `declareMethod("toString",
/// ...)` — is rejected where it is declared, so no method overrides by
/// accident and none is rendered without `@Override` that does. The
/// override has the parameters and the result of the method — or a narrower
/// result, said by its token, `cb.override(fact, token, body)` (JLS 8.4.5;
/// [Subtyping]) — and a `throws`
/// clause — that of [#throwing] — the method allows (JLS 8.4.8.3). A class
/// that overrides `equals` and not `hashCode` is rejected when its
/// declaration completes.
///
/// Members are declared and defined from the class specification only:
/// doing so while a method body is being built, or after the declaration
/// completed, is rejected.
///
/// **`clone` and `finalize`.** The `protected` methods of `Object` are
/// overridden and called as those of any superclass are ([SuperAccess]):
/// `cb.protected_(Object_.clone)` gives the fact, `override` and `superCall`
/// take it.
///
/// This builder is of a class that extends `Object`; one with another
/// superclass has a [TypedSubclassBuilder]. This phase covers non-generic
/// `final` classes, methods and constructors up to arity 2 (static methods
/// up to 1); type parameters and `abstract`/open classes come later, as does
/// a generator of the arity families analogous to `java-file-api-facts`'s
/// `FactsCodegen`.
///
/// @param <Self> the brand of the class being declared, unique to one
///     [TypedJavaFile#class_(TargetClasspath, java.lang.constant.ClassDesc, TypedJavaFile.TypedClassSpec)] call
public final class TypedClassBuilder<Self> extends MemberBuilder<Self>
        implements ObjectConstructors<Self>, OverridingDeclarations<Self, Object>, SuperAccess<Self, Object> {

    TypedClassBuilder(ClassDeclaration<Self> declared, Inheritance inherited, Superclass superclass) {
        super(declared, inherited, superclass);
    }

    // ------------------------------------------------------------------
    // The throws clause
    // ------------------------------------------------------------------

    /// The declarations of the methods and constructors of this class
    /// that declare `exceptions` in their `throws` clause:
    ///
    /// ```java
    /// var read = cb.throwing(IOException_.TOKEN).declareMethod("read", PrimitiveToken.INT);
    /// cb.define(read, (b, self) -> b.return_(call(field(self, in), InputStream_.read)));
    /// cb.throwing(IOException_.TOKEN)
    ///         .method("twice", PrimitiveToken.INT, (b, self) -> b.return_(call(self, read)));
    /// ```
    ///
    /// The clause is part of the fact the declaration returns: a call of
    /// the member is accepted only where each of its checked exceptions is
    /// caught or declared, and the body the member is defined with may
    /// throw them and their subclasses. Unchecked exceptions may be
    /// declared, and change nothing.
    ///
    /// The result declares methods and constructors and nothing else — a
    /// field has no `throws` clause, and a member is defined by this
    /// builder whatever it declares.
    ///
    /// @param exceptions the exception types of the `throws` clause, in order
    /// @return the declarations of the members that declare `exceptions`
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final ThrowingDeclarations<Self> throwing(ClassToken<? extends Throwable>... exceptions) {
        return new ThrowingDeclarations<>(this, List.of(exceptions));
    }

    /// Defines a declared constructor; the body gets `this`.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given its block and `this`
    public void define(CtorRef0<Self> ctor, InstanceBody0<Self, VoidBody, Void> body) {
        defineConstructor(ctor, body);
    }

    /// Defines a declared constructor; the body gets `this` and the parameter.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given its block, `this` and the parameter
    /// @param <A1> the parameter type
    public <A1> void define(CtorRef1<Self, A1> ctor, InstanceBody1<Self, VoidBody, Void, A1> body) {
        defineConstructor(ctor, body);
    }

    /// Defines a declared constructor; the body gets `this` and the parameters.
    ///
    /// @param ctor the declared constructor
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    public <A1, A2> void define(CtorRef2<Self, A1, A2> ctor, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        defineConstructor(ctor, body);
    }

    // ------------------------------------------------------------------
    // Bodies of instance members (§6.5 HOAS: the block, `this`, the parameters)
    // ------------------------------------------------------------------

    /// The body of an instance method or constructor without parameters.
    ///
    /// @param <Self> the class
    /// @param <B> the body block
    /// @param <R> the result type, [Void] for `void`
    @FunctionalInterface
    public interface InstanceBody0<Self, B, R> {
        /// Builds the body.
        ///
        /// @param body the body block
        /// @param self `this`
        /// @return the proof that the body ended
        Terminated<R> build(B body, This<Self> self);
    }

    /// The body of an instance method or constructor with one parameter.
    ///
    /// @param <Self> the class
    /// @param <B> the body block
    /// @param <R> the result type, [Void] for `void`
    /// @param <A1> the parameter type
    @FunctionalInterface
    public interface InstanceBody1<Self, B, R, A1> {
        /// Builds the body.
        ///
        /// @param body the body block
        /// @param self `this`
        /// @param p1 the parameter
        /// @return the proof that the body ended
        Terminated<R> build(B body, This<Self> self, Var<A1> p1);
    }

    /// The body of an instance method or constructor with two parameters.
    ///
    /// @param <Self> the class
    /// @param <B> the body block
    /// @param <R> the result type, [Void] for `void`
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    @FunctionalInterface
    public interface InstanceBody2<Self, B, R, A1, A2> {
        /// Builds the body.
        ///
        /// @param body the body block
        /// @param self `this`
        /// @param p1 the first parameter
        /// @param p2 the second parameter
        /// @return the proof that the body ended
        Terminated<R> build(B body, This<Self> self, Var<A1> p1, Var<A2> p2);
    }
}
