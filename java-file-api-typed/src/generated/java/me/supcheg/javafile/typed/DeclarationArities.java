package me.supcheg.javafile.typed;

import java.util.List;
import javax.annotation.processing.Generated;
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

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class DeclarationArities<Self, S> permits Declarations {
    abstract MemberBuilder<Self, S> builder();

    abstract List<ClassToken<? extends Throwable>> thrown();

    abstract <F extends Invocable> F declare(F member);

    abstract <F extends Invocable> F overriding(F own);

    abstract MemberTraits traits(MemberTraits traits);

    /**
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public CtorRef0<Self> declareConstructor() {
        return declare(UnsafeInvocables.ctor(builder().self(), traits(MemberTraits.DEFAULT)));
    }

    /**
     * Declares a `public` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R> MethodRef0<Self, R> declareMethod(String name, TypeToken<R> result) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public VoidMethodRef0<Self> declareVoidMethod(String name) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1> CtorRef1<Self, A1> declareConstructor(TypeToken<A1> p1) {
        return declare(UnsafeInvocables.ctor(builder().self(), p1, traits(MemberTraits.DEFAULT)));
    }

    /**
     * Declares a `public` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1> MethodRef1<Self, R, A1> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, p1, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1> VoidMethodRef1<Self, A1> declareVoidMethod(String name, TypeToken<A1> p1) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, p1, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2> CtorRef2<Self, A1, A2> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeInvocables.ctor(builder().self(), p1, p2, traits(MemberTraits.DEFAULT)));
    }

    /**
     * Declares a `public` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, p1, p2, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, p1, p2, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R> StaticMethodRef0<R> declareStaticMethod(String name, TypeToken<R> result) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public VoidStaticMethodRef0 declareVoidStaticMethod(String name) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1> StaticMethodRef1<R, A1> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, p1, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1> VoidStaticMethodRef1<A1> declareVoidStaticMethod(String name, TypeToken<A1> p1) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, p1, traits(MemberTraits.FINAL)));
    }

    /**
     * Overrides a method of 0 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <R> the result type of the overridden method
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R> MethodRef0<Self, R> declareOverride(MethodRef0<? super S, R> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 0 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R> MethodRef0<Self, R> declareOverride(Upcast<Self, ? extends O> up, MethodRef0<O, R> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 0 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, R2 extends R> MethodRef0<Self, R2> declareOverride(MethodRef0<? super S, R> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 0 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, R2 extends R> MethodRef0<Self, R2> declareOverride(Upcast<Self, ? extends O> up, MethodRef0<O, R> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 0 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <R> the result type of the overridden method
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R> MethodRef0<Self, Self> declareOverride(MethodRef0<? super S, R> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 0 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R> MethodRef0<Self, Self> declareOverride(Upcast<Self, ? extends O> up, MethodRef0<O, R> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 0 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public VoidMethodRef0<Self> declareOverride(VoidMethodRef0<? super S> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 0 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O> VoidMethodRef0<Self> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef0<O> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 1 parameter the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1> MethodRef1<Self, R, A1> declareOverride(MethodRef1<? super S, R, A1> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 1 parameter of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1> MethodRef1<Self, R, A1> declareOverride(Upcast<Self, ? extends O> up, MethodRef1<O, R, A1> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 1 parameter the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, R2 extends R, A1> MethodRef1<Self, R2, A1> declareOverride(MethodRef1<? super S, R, A1> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 1 parameter of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, R2 extends R, A1> MethodRef1<Self, R2, A1> declareOverride(Upcast<Self, ? extends O> up, MethodRef1<O, R, A1> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 1 parameter the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1> MethodRef1<Self, Self, A1> declareOverride(MethodRef1<? super S, R, A1> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 1 parameter of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1> MethodRef1<Self, Self, A1> declareOverride(Upcast<Self, ? extends O> up, MethodRef1<O, R, A1> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 1 parameter the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <A1> the type of the parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1> VoidMethodRef1<Self, A1> declareOverride(VoidMethodRef1<? super S, A1> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 1 parameter of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <A1> the type of the parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, A1> VoidMethodRef1<Self, A1> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef1<O, A1> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 2 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> declareOverride(MethodRef2<? super S, R, A1, A2> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 2 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2> MethodRef2<Self, R, A1, A2> declareOverride(Upcast<Self, ? extends O> up, MethodRef2<O, R, A1, A2> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 2 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, R2 extends R, A1, A2> MethodRef2<Self, R2, A1, A2> declareOverride(MethodRef2<? super S, R, A1, A2> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 2 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, R2 extends R, A1, A2> MethodRef2<Self, R2, A1, A2> declareOverride(Upcast<Self, ? extends O> up, MethodRef2<O, R, A1, A2> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 2 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2> MethodRef2<Self, Self, A1, A2> declareOverride(MethodRef2<? super S, R, A1, A2> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 2 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2> MethodRef2<Self, Self, A1, A2> declareOverride(Upcast<Self, ? extends O> up, MethodRef2<O, R, A1, A2> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 2 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2> VoidMethodRef2<Self, A1, A2> declareOverride(VoidMethodRef2<? super S, A1, A2> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 2 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, A1, A2> VoidMethodRef2<Self, A1, A2> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef2<O, A1, A2> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }
}
