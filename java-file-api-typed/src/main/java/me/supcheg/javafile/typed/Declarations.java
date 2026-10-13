package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;

import java.util.List;

/// The declarations of the methods and constructors of a class being
/// declared (§6.5): what the builder of its members ([MemberBuilder])
/// declares of them itself, and what its `throwing` declares with a `throws`
/// clause ([ThrowingDeclarations]). The two differ in the clause alone.
///
/// **A declaration has no body.** It gives the fact of the member; the body
/// is given to the stage after, [MemberBodies], which the builder of the
/// members ends in. So every member of the class is declared before any body
/// is built, and a body is lowered where it is defined, against all the
/// class has.
///
/// **Overriding is by the fact.** A method the class inherits is overridden
/// by naming its fact, never by declaring a method of its name:
/// `declareOverride(Object_.toString)` declares `@Override public String
/// toString()`. A fact of the superclass `S` or of a class above it is taken
/// as it is — javac holds the generator to it, `S` being the type the class
/// was declared to extend; the method of an interface by the witness that
/// the class is of the interface, `declareOverride(up, fact)`. The witness is
/// asked for its type alone: that the compiler of the generator takes it is
/// the proof that the class is of a type that has the method, and nothing of
/// it is read. What the class inherits under the signature is held against
/// the method where it is declared ([SignatureRegistry]).
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public abstract sealed class Declarations<Self, S> permits MemberBuilder, ThrowingDeclarations {

    Declarations() {}

    /// The builder of the class the members are declared in.
    abstract MemberBuilder<Self, S> builder();

    /// The exception classes of the `throws` clause of every member declared here.
    abstract List<ClassToken<? extends Throwable>> thrown();

    private <F extends Invocable> F declare(F member) {
        return builder().declare(member, thrown());
    }

    private <F extends Invocable> F overriding(F own) {
        return builder().declareOverride(own, thrown());
    }

    /// The traits of a member declared here: `traits`, with the `throws` clause.
    private MemberTraits traits(MemberTraits traits) {
        return new MemberTraits(List.copyOf(thrown()), traits.overridability(), traits.typeArgs(), traits.access());
    }

    // ------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------

    /// Declares a `public` constructor without parameters, to be defined by the bodies of the
    /// class ([MemberBodies]).
    ///
    /// @return the fact of the constructor
    /// @throws IllegalArgumentException if javac would reject the constructor (see [SignatureRegistry])
    /// @throws IllegalStateException if the declarations of the class have ended
    public CtorRef0<Self> declareConstructor() {
        return declare(UnsafeInvocables.ctor(builder().self(), traits(MemberTraits.DEFAULT)));
    }

    /// Declares a `public` constructor with one parameter.
    ///
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the constructor
    /// @throws IllegalArgumentException if javac would reject the constructor
    /// @throws IllegalStateException if the declarations of the class have ended
    public <A1> CtorRef1<Self, A1> declareConstructor(TypeToken<A1> p1) {
        return declare(UnsafeInvocables.ctor(builder().self(), p1, traits(MemberTraits.DEFAULT)));
    }

    /// Declares a `public` constructor with two parameters.
    ///
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the constructor
    /// @throws IllegalArgumentException if javac would reject the constructor
    /// @throws IllegalStateException if the declarations of the class have ended
    public <A1, A2> CtorRef2<Self, A1, A2> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeInvocables.ctor(builder().self(), p1, p2, traits(MemberTraits.DEFAULT)));
    }

    // ------------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------------

    /// Declares a `public` method returning `R`, to be defined by the bodies of the class
    /// ([MemberBodies#define(MethodRef0, MemberBodies.InstanceBody0)]).
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R> MethodRef0<Self, R> declareMethod(String name, TypeToken<R> result) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public` method with one parameter.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R, A1> MethodRef1<Self, R, A1> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, p1, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public` method with two parameters.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, p1, p2, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public void` method.
    ///
    /// @param name the method name
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public VoidMethodRef0<Self> declareVoidMethod(String name) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public void` method with one parameter.
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public <A1> VoidMethodRef1<Self, A1> declareVoidMethod(String name, TypeToken<A1> p1) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, p1, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public void` method with two parameters.
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, p1, p2, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public static` method.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R> StaticMethodRef0<R> declareStaticMethod(String name, TypeToken<R> result) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public static` method with one parameter.
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R, A1> StaticMethodRef1<R, A1> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, p1, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public static void` method.
    ///
    /// @param name the method name
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public VoidStaticMethodRef0 declareVoidStaticMethod(String name) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public static void` method with one parameter.
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    /// @throws IllegalStateException if the declarations of the class have ended
    public <A1> VoidStaticMethodRef1<A1> declareVoidStaticMethod(String name, TypeToken<A1> p1) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, p1, traits(MemberTraits.FINAL)));
    }

    // ------------------------------------------------------------------
    // Overrides of the methods of the superclass
    // ------------------------------------------------------------------

    /// Overrides a method the class inherits from its superclass: declares it anew, `@Override public`.
    ///
    /// @param method the fact of the overridden method
    /// @param <R> the result type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R> MethodRef0<Self, R> declareOverride(MethodRef0<? super S, R> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /// Overrides a method with one parameter the class inherits from its superclass.
    ///
    /// @param method the fact of the overridden method
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R, A1> MethodRef1<Self, R, A1> declareOverride(MethodRef1<? super S, R, A1> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /// Overrides a method with two parameters the class inherits from its superclass.
    ///
    /// @param method the fact of the overridden method
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> declareOverride(MethodRef2<? super S, R, A1, A2> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /// Overrides a `void` method the class inherits from its superclass.
    ///
    /// @param method the fact of the overridden method
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public VoidMethodRef0<Self> declareOverride(VoidMethodRef0<? super S> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /// Overrides a `void` method with one parameter the class inherits from its superclass.
    ///
    /// @param method the fact of the overridden method
    /// @param <A1> the parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <A1> VoidMethodRef1<Self, A1> declareOverride(VoidMethodRef1<? super S, A1> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /// Overrides a `void` method with two parameters the class inherits from its superclass.
    ///
    /// @param method the fact of the overridden method
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <A1, A2> VoidMethodRef2<Self, A1, A2> declareOverride(VoidMethodRef2<? super S, A1, A2> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /// Overrides a method the class inherits from its superclass with a method of a narrower result
    /// (JLS 8.4.5): `result` is a subtype of the result of the method — which the compiler of the
    /// generator holds the token to — and is proven one of the result of every method the class
    /// inherits under the signature ([Subtyping]).
    ///
    /// ```java
    /// var clone = cb.declareOverride(cb.protected_(Object_.clone), cb.self());
    /// ```
    ///
    /// @param method the fact of the overridden method
    /// @param result the result type of the method of the class
    /// @param <R> the result type of the overridden method
    /// @param <R2> the result type of the method of the class
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden so, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R, R2 extends R> MethodRef0<Self, R2> declareOverride(
            MethodRef0<? super S, R> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /// Overrides a method with one parameter of the superclass with a method of a narrower result,
    /// see [#declareOverride(MethodRef0, RefToken)].
    ///
    /// @param method the fact of the overridden method
    /// @param result the result type of the method of the class
    /// @param <R> the result type of the overridden method
    /// @param <R2> the result type of the method of the class
    /// @param <A1> the parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden so, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R, R2 extends R, A1> MethodRef1<Self, R2, A1> declareOverride(
            MethodRef1<? super S, R, A1> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /// Overrides a method with two parameters of the superclass with a method of a narrower
    /// result, see [#declareOverride(MethodRef0, RefToken)].
    ///
    /// @param method the fact of the overridden method
    /// @param result the result type of the method of the class
    /// @param <R> the result type of the overridden method
    /// @param <R2> the result type of the method of the class
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden so, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R, R2 extends R, A1, A2> MethodRef2<Self, R2, A1, A2> declareOverride(
            MethodRef2<? super S, R, A1, A2> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /// Overrides a method the class inherits from its superclass with a method whose result is the
    /// class itself (JLS 8.4.5): that the class is of the result type of the method is said by the
    /// witness `result` — the brand `Self` is a subtype of `Object` alone to the compiler of the
    /// generator — and proven of every method the class inherits under the signature ([Subtyping]).
    ///
    /// @param method the fact of the overridden method
    /// @param result the witness that the class is of the result type of the method
    /// @param <R> the result type of the overridden method
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden so, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R> MethodRef0<Self, Self> declareOverride(
            MethodRef0<? super S, R> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /// Overrides a method with one parameter of the superclass with a method whose result is the
    /// class itself, see [#declareOverride(MethodRef0, Upcast)].
    ///
    /// @param method the fact of the overridden method
    /// @param result the witness that the class is of the result type of the method
    /// @param <R> the result type of the overridden method
    /// @param <A1> the parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden so, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R, A1> MethodRef1<Self, Self, A1> declareOverride(
            MethodRef1<? super S, R, A1> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /// Overrides a method with two parameters of the superclass with a method whose result is the
    /// class itself, see [#declareOverride(MethodRef0, Upcast)].
    ///
    /// @param method the fact of the overridden method
    /// @param result the witness that the class is of the result type of the method
    /// @param <R> the result type of the overridden method
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden so, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <R, A1, A2> MethodRef2<Self, Self, A1, A2> declareOverride(
            MethodRef2<? super S, R, A1, A2> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    // ------------------------------------------------------------------
    // Overrides of the methods of an interface, by the witness of it
    // ------------------------------------------------------------------

    /// Overrides a method of a supertype the class has by the witness `up` — one that has a body
    /// there, a default method: an abstract one is implemented by the proof the supertype is
    /// declared with.
    ///
    /// @param up the witness that the class is of a type that has the method
    /// @param method the fact of the overridden method
    /// @param <O> the type that declares the method
    /// @param <R> the result type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <O, R> MethodRef0<Self, R> declareOverride(Upcast<Self, ? extends O> up, MethodRef0<O, R> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /// Overrides a method with one parameter of a supertype the class has by the witness `up`,
    /// see [#declareOverride(Upcast, MethodRef0)].
    ///
    /// @param up the witness that the class is of a type that has the method
    /// @param method the fact of the overridden method
    /// @param <O> the type that declares the method
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <O, R, A1> MethodRef1<Self, R, A1> declareOverride(
            Upcast<Self, ? extends O> up, MethodRef1<O, R, A1> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /// Overrides a method with two parameters of a supertype the class has by the witness `up`,
    /// see [#declareOverride(Upcast, MethodRef0)].
    ///
    /// @param up the witness that the class is of a type that has the method
    /// @param method the fact of the overridden method
    /// @param <O> the type that declares the method
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <O, R, A1, A2> MethodRef2<Self, R, A1, A2> declareOverride(
            Upcast<Self, ? extends O> up, MethodRef2<O, R, A1, A2> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /// Overrides a `void` method of a supertype the class has by the witness `up`, see
    /// [#declareOverride(Upcast, MethodRef0)].
    ///
    /// @param up the witness that the class is of a type that has the method
    /// @param method the fact of the overridden method
    /// @param <O> the type that declares the method
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <O> VoidMethodRef0<Self> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef0<O> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /// Overrides a `void` method with one parameter of a supertype the class has by the witness
    /// `up`, see [#declareOverride(Upcast, MethodRef0)].
    ///
    /// @param up the witness that the class is of a type that has the method
    /// @param method the fact of the overridden method
    /// @param <O> the type that declares the method
    /// @param <A1> the parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <O, A1> VoidMethodRef1<Self, A1> declareOverride(
            Upcast<Self, ? extends O> up, VoidMethodRef1<O, A1> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /// Overrides a `void` method with two parameters of a supertype the class has by the witness
    /// `up`, see [#declareOverride(Upcast, MethodRef0)].
    ///
    /// @param up the witness that the class is of a type that has the method
    /// @param method the fact of the overridden method
    /// @param <O> the type that declares the method
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <O, A1, A2> VoidMethodRef2<Self, A1, A2> declareOverride(
            Upcast<Self, ? extends O> up, VoidMethodRef2<O, A1, A2> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /// Overrides a method of a supertype the class has by the witness `up` with a method of a
    /// narrower result (JLS 8.4.5): `result` is a subtype of the result of the method — which the
    /// compiler of the generator holds the token to — and is proven one of the result of every
    /// method the class inherits under the signature ([Subtyping]).
    ///
    /// @param up the witness that the class is of the supertype
    /// @param method the fact of the overridden method
    /// @param result the result type of the method of the class
    /// @param <O> the type that has the method
    /// @param <R> the result type of the overridden method
    /// @param <R2> the result type of the method of the class
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden so, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <O, R, R2 extends R> MethodRef0<Self, R2> declareOverride(
            Upcast<Self, ? extends O> up, MethodRef0<O, R> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /// Overrides a method with one parameter of a supertype the class has by the witness `up`
    /// with a method of a narrower result, see [#declareOverride(Upcast, MethodRef0, RefToken)].
    ///
    /// @param up the witness that the class is of the supertype
    /// @param method the fact of the overridden method
    /// @param result the result type of the method of the class
    /// @param <O> the type that has the method
    /// @param <R> the result type of the overridden method
    /// @param <R2> the result type of the method of the class
    /// @param <A1> the parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden so, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <O, R, R2 extends R, A1> MethodRef1<Self, R2, A1> declareOverride(
            Upcast<Self, ? extends O> up, MethodRef1<O, R, A1> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /// Overrides a method with two parameters of a supertype the class has by the witness `up`
    /// with a method of a narrower result, see [#declareOverride(Upcast, MethodRef0, RefToken)].
    ///
    /// @param up the witness that the class is of the supertype
    /// @param method the fact of the overridden method
    /// @param result the result type of the method of the class
    /// @param <O> the type that has the method
    /// @param <R> the result type of the overridden method
    /// @param <R2> the result type of the method of the class
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden so, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <O, R, R2 extends R, A1, A2> MethodRef2<Self, R2, A1, A2> declareOverride(
            Upcast<Self, ? extends O> up, MethodRef2<O, R, A1, A2> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /// Overrides a method of a supertype the class has by the witness `up` with a method whose
    /// result is the class itself (JLS 8.4.5): that the class is of the result type of the method
    /// is said by the witness `result`, and proven of every method the class inherits under the
    /// signature ([Subtyping]).
    ///
    /// @param up the witness that the class is of the supertype
    /// @param method the fact of the overridden method
    /// @param result the witness that the class is of the result type of the method
    /// @param <O> the type that has the method
    /// @param <R> the result type of the overridden method
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden so, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <O, R> MethodRef0<Self, Self> declareOverride(
            Upcast<Self, ? extends O> up, MethodRef0<O, R> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /// Overrides a method with one parameter of a supertype the class has by the witness `up` with
    /// a method whose result is the class itself, see [#declareOverride(Upcast, MethodRef0, Upcast)].
    ///
    /// @param up the witness that the class is of the supertype
    /// @param method the fact of the overridden method
    /// @param result the witness that the class is of the result type of the method
    /// @param <O> the type that has the method
    /// @param <R> the result type of the overridden method
    /// @param <A1> the parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden so, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <O, R, A1> MethodRef1<Self, Self, A1> declareOverride(
            Upcast<Self, ? extends O> up, MethodRef1<O, R, A1> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /// Overrides a method with two parameters of a supertype the class has by the witness `up` with
    /// a method whose result is the class itself, see [#declareOverride(Upcast, MethodRef0, Upcast)].
    ///
    /// @param up the witness that the class is of the supertype
    /// @param method the fact of the overridden method
    /// @param result the witness that the class is of the result type of the method
    /// @param <O> the type that has the method
    /// @param <R> the result type of the overridden method
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden so, see [SignatureRegistry]
    /// @throws IllegalStateException if the declarations of the class have ended
    public <O, R, A1, A2> MethodRef2<Self, Self, A1, A2> declareOverride(
            Upcast<Self, ? extends O> up, MethodRef2<O, R, A1, A2> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }
}
