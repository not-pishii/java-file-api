package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.typed.TypedClassBuilder.InstanceBody0;
import me.supcheg.javafile.typed.TypedClassBuilder.InstanceBody1;
import me.supcheg.javafile.typed.TypedClassBuilder.InstanceBody2;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/// The declarations of the methods of a class being declared (§6.5): what
/// the builder of its members ([MemberBuilder]) declares of them itself, and
/// what its `throwing` declares with a `throws` clause
/// ([ThrowingDeclarations]). The two differ in the clause alone.
///
/// What depends on the superclass of the class is declared by the interfaces
/// a builder has beside: the overrides of the methods it inherits from the
/// superclass ([OverridingDeclarations]) and its constructors
/// ([ObjectConstructors]).
///
/// A method or constructor is declared, which gives its fact, and defined
/// with a body by the builder of the class
/// ([TypedClassBuilder#define(MethodRef0, InstanceBody0)] and its
/// overloads); `method`, `voidMethod`, `staticMethod`, `voidStaticMethod` and
/// `constructor` do both in one call.
///
/// @param <Self> the brand of the class being declared
public abstract sealed class Declarations<Self> permits MemberBuilder, ThrowingDeclarations {

    Declarations() {}

    /// The builder of the class the members are declared in and defined by.
    abstract MemberBuilder<Self> builder();

    /// These declarations themselves: what the default methods of the interfaces a builder has —
    /// [OverridingDeclarations], [ObjectConstructors] — reach it by. Of no use to a generator.
    ///
    /// @return these declarations
    public final Declarations<Self> declarations() {
        return this;
    }

    /// The exception classes of the `throws` clause of every member declared here.
    abstract List<ClassToken<? extends Throwable>> thrown();

    final <F extends Invocable> F declare(F member) {
        return builder().declare(member, thrown());
    }

    /// The traits of a member declared here: `traits`, with the `throws` clause.
    final MemberTraits traits(MemberTraits traits) {
        return new MemberTraits(List.copyOf(thrown()), traits.overridability(), traits.typeArgs(), traits.access());
    }

    // ------------------------------------------------------------------
    // Declarations
    // ------------------------------------------------------------------

    /// Declares a `public` method returning `R`, to be defined with
    /// [TypedClassBuilder#define(MethodRef0, InstanceBody0)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
    public <R> MethodRef0<Self, R> declareMethod(String name, TypeToken<R> result) {
        return declare(UnsafeFacts.method(builder().self(), name, result, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public` method, to be defined with [TypedClassBuilder#define(MethodRef1, InstanceBody1)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public <R, A1> MethodRef1<Self, R, A1> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declare(UnsafeFacts.method(builder().self(), name, result, p1, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public` method, to be defined with [TypedClassBuilder#define(MethodRef2, InstanceBody2)].
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
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> declareMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeFacts.method(builder().self(), name, result, p1, p2, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public void` method, to be defined with [TypedClassBuilder#define(VoidMethodRef0, InstanceBody0)].
    ///
    /// @param name the method name
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public VoidMethodRef0<Self> declareVoidMethod(String name) {
        return declare(UnsafeFacts.voidMethod(builder().self(), name, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public void` method, to be defined with [TypedClassBuilder#define(VoidMethodRef1, InstanceBody1)].
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public <A1> VoidMethodRef1<Self, A1> declareVoidMethod(String name, TypeToken<A1> p1) {
        return declare(UnsafeFacts.voidMethod(builder().self(), name, p1, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public void` method, to be defined with [TypedClassBuilder#define(VoidMethodRef2, InstanceBody2)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeFacts.voidMethod(builder().self(), name, p1, p2, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public static` method, to be defined with [TypedClassBuilder#define(StaticMethodRef0, Function)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param <R> the result type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public <R> StaticMethodRef0<R> declareStaticMethod(String name, TypeToken<R> result) {
        return declare(UnsafeFacts.staticMethod(builder().self(), name, result, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public static` method, to be defined with [TypedClassBuilder#define(StaticMethodRef1, BiFunction)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public <R, A1> StaticMethodRef1<R, A1> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declare(UnsafeFacts.staticMethod(builder().self(), name, result, p1, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public static void` method, to be defined with
    /// [TypedClassBuilder#define(VoidStaticMethodRef0, Function)].
    ///
    /// @param name the method name
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public VoidStaticMethodRef0 declareVoidStaticMethod(String name) {
        return declare(UnsafeFacts.voidStaticMethod(builder().self(), name, traits(MemberTraits.FINAL)));
    }

    /// Declares a `public static void` method, to be defined with
    /// [TypedClassBuilder#define(VoidStaticMethodRef1, BiFunction)].
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    /// @throws IllegalArgumentException if javac would reject the method
    public <A1> VoidStaticMethodRef1<A1> declareVoidStaticMethod(String name, TypeToken<A1> p1) {
        return declare(UnsafeFacts.voidStaticMethod(builder().self(), name, p1, traits(MemberTraits.FINAL)));
    }

    // ------------------------------------------------------------------
    // Declare and define in one call
    // ------------------------------------------------------------------

    /// Declares and defines a method, see [#declareMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param body the body, given its block and `this`
    /// @param <R> the result type
    /// @return the fact of the method
    public <R> MethodRef0<Self, R> method(String name, TypeToken<R> result, InstanceBody0<Self, Body<R>, R> body) {
        MethodRef0<Self, R> method = declareMethod(name, result);
        builder().define(method, body);
        return method;
    }

    /// Declares and defines a method, see [#declareMethod(String, TypeToken, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param body the body, given its block, `this` and the parameter
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <R, A1> MethodRef1<Self, R, A1> method(
            String name, TypeToken<R> result, TypeToken<A1> p1, InstanceBody1<Self, Body<R>, R, A1> body) {
        MethodRef1<Self, R, A1> method = declareMethod(name, result, p1);
        builder().define(method, body);
        return method;
    }

    /// Declares and defines a method, see [#declareMethod(String, TypeToken, TypeToken, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param body the body, given its block, `this` and the parameters
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> method(
            String name,
            TypeToken<R> result,
            TypeToken<A1> p1,
            TypeToken<A2> p2,
            InstanceBody2<Self, Body<R>, R, A1, A2> body) {
        MethodRef2<Self, R, A1, A2> method = declareMethod(name, result, p1, p2);
        builder().define(method, body);
        return method;
    }

    /// Declares and defines a `void` method, see [#declareVoidMethod(String)].
    ///
    /// @param name the method name
    /// @param body the body, given its block and `this`
    /// @return the fact of the method
    public VoidMethodRef0<Self> voidMethod(String name, InstanceBody0<Self, VoidBody, Void> body) {
        VoidMethodRef0<Self> method = declareVoidMethod(name);
        builder().define(method, body);
        return method;
    }

    /// Declares and defines a `void` method, see [#declareVoidMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param body the body, given its block, `this` and the parameter
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <A1> VoidMethodRef1<Self, A1> voidMethod(
            String name, TypeToken<A1> p1, InstanceBody1<Self, VoidBody, Void, A1> body) {
        VoidMethodRef1<Self, A1> method = declareVoidMethod(name, p1);
        builder().define(method, body);
        return method;
    }

    /// Declares and defines a `void` method, see [#declareVoidMethod(String, TypeToken, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the method
    public <A1, A2> VoidMethodRef2<Self, A1, A2> voidMethod(
            String name, TypeToken<A1> p1, TypeToken<A2> p2, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        VoidMethodRef2<Self, A1, A2> method = declareVoidMethod(name, p1, p2);
        builder().define(method, body);
        return method;
    }

    /// Declares and defines a static method, see [#declareStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param body the body, given its block
    /// @param <R> the result type
    /// @return the fact of the method
    public <R> StaticMethodRef0<R> staticMethod(
            String name, TypeToken<R> result, Function<Body<R>, Terminated<R>> body) {
        StaticMethodRef0<R> method = declareStaticMethod(name, result);
        builder().define(method, body);
        return method;
    }

    /// Declares and defines a static method, see [#declareStaticMethod(String, TypeToken, TypeToken)].
    ///
    /// @param name the method name
    /// @param result the result type
    /// @param p1 the parameter type
    /// @param body the body, given its block and the parameter
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <R, A1> StaticMethodRef1<R, A1> staticMethod(
            String name, TypeToken<R> result, TypeToken<A1> p1, BiFunction<Body<R>, Var<A1>, Terminated<R>> body) {
        StaticMethodRef1<R, A1> method = declareStaticMethod(name, result, p1);
        builder().define(method, body);
        return method;
    }

    /// Declares and defines a static `void` method, see [#declareVoidStaticMethod(String)].
    ///
    /// @param name the method name
    /// @param body the body, given its block
    /// @return the fact of the method
    public VoidStaticMethodRef0 voidStaticMethod(String name, Function<VoidBody, Terminated<Void>> body) {
        VoidStaticMethodRef0 method = declareVoidStaticMethod(name);
        builder().define(method, body);
        return method;
    }

    /// Declares and defines a static `void` method, see [#declareVoidStaticMethod(String, TypeToken)].
    ///
    /// @param name the method name
    /// @param p1 the parameter type
    /// @param body the body, given its block and the parameter
    /// @param <A1> the parameter type
    /// @return the fact of the method
    public <A1> VoidStaticMethodRef1<A1> voidStaticMethod(
            String name, TypeToken<A1> p1, BiFunction<VoidBody, Var<A1>, Terminated<Void>> body) {
        VoidStaticMethodRef1<A1> method = declareVoidStaticMethod(name, p1);
        builder().define(method, body);
        return method;
    }

    // ------------------------------------------------------------------
    // Overrides: a method the class inherits, named by its fact
    // ------------------------------------------------------------------

    /// Overrides a method of a supertype the class has by the witness `up` — one that has a body
    /// there, a default method: an abstract one is implemented by the proof the supertype is
    /// declared with.
    ///
    /// @param up the witness that the class is of a type that has the method
    /// @param method the fact of the overridden method
    /// @param body the body, given its block and `this`
    /// @param <O> the type that declares the method
    /// @param <R> the result type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    public <O, R> MethodRef0<Self, R> override(
            Upcast<Self, ? extends O> up, MethodRef0<O, R> method, InstanceBody0<Self, Body<R>, R> body) {
        return overridden(method, body);
    }

    /// Overrides a method with one parameter of a supertype the class has by the witness `up` — one that has a body
    /// there, a default method: an abstract one is implemented by the proof the supertype is
    /// declared with.
    ///
    /// @param up the witness that the class is of a type that has the method
    /// @param method the fact of the overridden method
    /// @param body the body, given its block, `this` and the parameter
    /// @param <O> the type that declares the method
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    public <O, R, A1> MethodRef1<Self, R, A1> override(
            Upcast<Self, ? extends O> up, MethodRef1<O, R, A1> method, InstanceBody1<Self, Body<R>, R, A1> body) {
        return overridden(method, body);
    }

    /// Overrides a method with two parameters of a supertype the class has by the witness `up` — one that has a body
    /// there, a default method: an abstract one is implemented by the proof the supertype is
    /// declared with.
    ///
    /// @param up the witness that the class is of a type that has the method
    /// @param method the fact of the overridden method
    /// @param body the body, given its block, `this` and the parameters
    /// @param <O> the type that declares the method
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    public <O, R, A1, A2> MethodRef2<Self, R, A1, A2> override(
            Upcast<Self, ? extends O> up,
            MethodRef2<O, R, A1, A2> method,
            InstanceBody2<Self, Body<R>, R, A1, A2> body) {
        return overridden(method, body);
    }

    /// Overrides a `void` method of a supertype the class has by the witness `up` — one that has a body
    /// there, a default method: an abstract one is implemented by the proof the supertype is
    /// declared with.
    ///
    /// @param up the witness that the class is of a type that has the method
    /// @param method the fact of the overridden method
    /// @param body the body, given its block and `this`
    /// @param <O> the type that declares the method
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    public <O> VoidMethodRef0<Self> override(
            Upcast<Self, ? extends O> up, VoidMethodRef0<O> method, InstanceBody0<Self, VoidBody, Void> body) {
        return overridden(method, body);
    }

    /// Overrides a `void` method with one parameter of a supertype the class has by the witness `up` — one that has a
    /// body
    /// there, a default method: an abstract one is implemented by the proof the supertype is
    /// declared with.
    ///
    /// @param up the witness that the class is of a type that has the method
    /// @param method the fact of the overridden method
    /// @param body the body, given its block, `this` and the parameter
    /// @param <O> the type that declares the method
    /// @param <A1> the parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    public <O, A1> VoidMethodRef1<Self, A1> override(
            Upcast<Self, ? extends O> up, VoidMethodRef1<O, A1> method, InstanceBody1<Self, VoidBody, Void, A1> body) {
        return overridden(method, body);
    }

    /// Overrides a `void` method with two parameters of a supertype the class has by the witness `up` — one that has a
    /// body
    /// there, a default method: an abstract one is implemented by the proof the supertype is
    /// declared with.
    ///
    /// @param up the witness that the class is of a type that has the method
    /// @param method the fact of the overridden method
    /// @param body the body, given its block, `this` and the parameters
    /// @param <O> the type that declares the method
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the overriding method
    /// @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
    public <O, A1, A2> VoidMethodRef2<Self, A1, A2> override(
            Upcast<Self, ? extends O> up,
            VoidMethodRef2<O, A1, A2> method,
            InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        return overridden(method, body);
    }

    final <R> MethodRef0<Self, R> overridden(MethodRef0<?, R> method, InstanceBody0<Self, Body<R>, R> body) {
        MethodRef0<Self, R> own =
                builder().declareOverride(Overrides.own(builder().self(), method, thrown()), thrown());
        builder().define(own, body);
        return own;
    }

    final <R, A1> MethodRef1<Self, R, A1> overridden(
            MethodRef1<?, R, A1> method, InstanceBody1<Self, Body<R>, R, A1> body) {
        MethodRef1<Self, R, A1> own =
                builder().declareOverride(Overrides.own(builder().self(), method, thrown()), thrown());
        builder().define(own, body);
        return own;
    }

    final <R, A1, A2> MethodRef2<Self, R, A1, A2> overridden(
            MethodRef2<?, R, A1, A2> method, InstanceBody2<Self, Body<R>, R, A1, A2> body) {
        MethodRef2<Self, R, A1, A2> own =
                builder().declareOverride(Overrides.own(builder().self(), method, thrown()), thrown());
        builder().define(own, body);
        return own;
    }

    final VoidMethodRef0<Self> overridden(VoidMethodRef0<?> method, InstanceBody0<Self, VoidBody, Void> body) {
        VoidMethodRef0<Self> own =
                builder().declareOverride(Overrides.own(builder().self(), method, thrown()), thrown());
        builder().define(own, body);
        return own;
    }

    final <A1> VoidMethodRef1<Self, A1> overridden(
            VoidMethodRef1<?, A1> method, InstanceBody1<Self, VoidBody, Void, A1> body) {
        VoidMethodRef1<Self, A1> own =
                builder().declareOverride(Overrides.own(builder().self(), method, thrown()), thrown());
        builder().define(own, body);
        return own;
    }

    final <A1, A2> VoidMethodRef2<Self, A1, A2> overridden(
            VoidMethodRef2<?, A1, A2> method, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        VoidMethodRef2<Self, A1, A2> own =
                builder().declareOverride(Overrides.own(builder().self(), method, thrown()), thrown());
        builder().define(own, body);
        return own;
    }
}
