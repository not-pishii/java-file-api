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

/// The declarations of the methods and constructors of a class being
/// declared (§6.5): what a [TypedClassBuilder] declares of them itself, and
/// what [TypedClassBuilder#throwing] declares with a `throws` clause
/// ([ThrowingDeclarations]). The two differ in the clause alone.
///
/// A method or constructor is declared, which gives its fact, and defined
/// with a body by the builder of the class
/// ([TypedClassBuilder#define(MethodRef0, InstanceBody0)] and its
/// overloads); `method`, `voidMethod`, `staticMethod`, `voidStaticMethod` and
/// `constructor` do both in one call.
///
/// @param <Self> the brand of the class being declared
public abstract sealed class Declarations<Self> permits TypedClassBuilder, ThrowingDeclarations {

    Declarations() {}

    /// The builder of the class the members are declared in and defined by.
    abstract TypedClassBuilder<Self> builder();

    /// The exception classes of the `throws` clause of every member declared here.
    abstract List<ClassToken<? extends Throwable>> thrown();

    private <F extends Invocable> F declare(F member) {
        return builder().declare(member, thrown());
    }

    /// The traits of a member declared here: `traits`, with the `throws` clause.
    private MemberTraits traits(MemberTraits traits) {
        return new MemberTraits(List.copyOf(thrown()), traits.overridability(), traits.typeArgs());
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

    /// Declares a `public` constructor, to be defined with [TypedClassBuilder#define(CtorRef0, InstanceBody0)].
    ///
    /// @return the fact of the constructor, which `new_` accepts
    /// @throws IllegalArgumentException if a constructor with the same erased parameters is declared already
    public CtorRef0<Self> declareConstructor() {
        return declare(UnsafeFacts.ctor(builder().self(), traits(MemberTraits.DEFAULT)));
    }

    /// Declares a `public` constructor, to be defined with [TypedClassBuilder#define(CtorRef1, InstanceBody1)].
    ///
    /// @param p1 the parameter type
    /// @param <A1> the parameter type
    /// @return the fact of the constructor
    /// @throws IllegalArgumentException if a constructor with the same erased parameters is declared already
    public <A1> CtorRef1<Self, A1> declareConstructor(TypeToken<A1> p1) {
        return declare(UnsafeFacts.ctor(builder().self(), p1, traits(MemberTraits.DEFAULT)));
    }

    /// Declares a `public` constructor, to be defined with [TypedClassBuilder#define(CtorRef2, InstanceBody2)].
    ///
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the constructor
    /// @throws IllegalArgumentException if a constructor with the same erased parameters is declared already
    public <A1, A2> CtorRef2<Self, A1, A2> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeFacts.ctor(builder().self(), p1, p2, traits(MemberTraits.DEFAULT)));
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

    /// Declares and defines a constructor, see [#declareConstructor()].
    ///
    /// @param body the body, given its block and `this`
    /// @return the fact of the constructor
    public CtorRef0<Self> constructor(InstanceBody0<Self, VoidBody, Void> body) {
        CtorRef0<Self> ctor = declareConstructor();
        builder().define(ctor, body);
        return ctor;
    }

    /// Declares and defines a constructor, see [#declareConstructor(TypeToken)].
    ///
    /// @param p1 the parameter type
    /// @param body the body, given its block, `this` and the parameter
    /// @param <A1> the parameter type
    /// @return the fact of the constructor
    public <A1> CtorRef1<Self, A1> constructor(TypeToken<A1> p1, InstanceBody1<Self, VoidBody, Void, A1> body) {
        CtorRef1<Self, A1> ctor = declareConstructor(p1);
        builder().define(ctor, body);
        return ctor;
    }

    /// Declares and defines a constructor, see [#declareConstructor(TypeToken, TypeToken)].
    ///
    /// @param p1 the first parameter type
    /// @param p2 the second parameter type
    /// @param body the body, given its block, `this` and the parameters
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return the fact of the constructor
    public <A1, A2> CtorRef2<Self, A1, A2> constructor(
            TypeToken<A1> p1, TypeToken<A2> p2, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        CtorRef2<Self, A1, A2> ctor = declareConstructor(p1, p2);
        builder().define(ctor, body);
        return ctor;
    }
}
