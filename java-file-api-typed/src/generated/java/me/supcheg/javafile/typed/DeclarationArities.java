package me.supcheg.javafile.typed;

import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef10;
import me.supcheg.javafile.facts.CtorRef11;
import me.supcheg.javafile.facts.CtorRef12;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.CtorRef3;
import me.supcheg.javafile.facts.CtorRef4;
import me.supcheg.javafile.facts.CtorRef5;
import me.supcheg.javafile.facts.CtorRef6;
import me.supcheg.javafile.facts.CtorRef7;
import me.supcheg.javafile.facts.CtorRef8;
import me.supcheg.javafile.facts.CtorRef9;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef10;
import me.supcheg.javafile.facts.MethodRef11;
import me.supcheg.javafile.facts.MethodRef12;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodRef4;
import me.supcheg.javafile.facts.MethodRef5;
import me.supcheg.javafile.facts.MethodRef6;
import me.supcheg.javafile.facts.MethodRef7;
import me.supcheg.javafile.facts.MethodRef8;
import me.supcheg.javafile.facts.MethodRef9;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef10;
import me.supcheg.javafile.facts.StaticMethodRef11;
import me.supcheg.javafile.facts.StaticMethodRef12;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef3;
import me.supcheg.javafile.facts.StaticMethodRef4;
import me.supcheg.javafile.facts.StaticMethodRef5;
import me.supcheg.javafile.facts.StaticMethodRef6;
import me.supcheg.javafile.facts.StaticMethodRef7;
import me.supcheg.javafile.facts.StaticMethodRef8;
import me.supcheg.javafile.facts.StaticMethodRef9;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef10;
import me.supcheg.javafile.facts.VoidMethodRef11;
import me.supcheg.javafile.facts.VoidMethodRef12;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidMethodRef4;
import me.supcheg.javafile.facts.VoidMethodRef5;
import me.supcheg.javafile.facts.VoidMethodRef6;
import me.supcheg.javafile.facts.VoidMethodRef7;
import me.supcheg.javafile.facts.VoidMethodRef8;
import me.supcheg.javafile.facts.VoidMethodRef9;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef10;
import me.supcheg.javafile.facts.VoidStaticMethodRef11;
import me.supcheg.javafile.facts.VoidStaticMethodRef12;
import me.supcheg.javafile.facts.VoidStaticMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef3;
import me.supcheg.javafile.facts.VoidStaticMethodRef4;
import me.supcheg.javafile.facts.VoidStaticMethodRef5;
import me.supcheg.javafile.facts.VoidStaticMethodRef6;
import me.supcheg.javafile.facts.VoidStaticMethodRef7;
import me.supcheg.javafile.facts.VoidStaticMethodRef8;
import me.supcheg.javafile.facts.VoidStaticMethodRef9;

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
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3> CtorRef3<Self, A1, A2, A3> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declare(UnsafeInvocables.ctor(builder().self(), p1, p2, p3, traits(MemberTraits.DEFAULT)));
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
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, p1, p2, p3, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, p1, p2, p3, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4> CtorRef4<Self, A1, A2, A3, A4> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declare(UnsafeInvocables.ctor(builder().self(), p1, p2, p3, p4, traits(MemberTraits.DEFAULT)));
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
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, p1, p2, p3, p4, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, p1, p2, p3, p4, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5> CtorRef5<Self, A1, A2, A3, A4, A5> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declare(UnsafeInvocables.ctor(builder().self(), p1, p2, p3, p4, p5, traits(MemberTraits.DEFAULT)));
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
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5> MethodRef5<Self, R, A1, A2, A3, A4, A5> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, p1, p2, p3, p4, p5, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5> VoidMethodRef5<Self, A1, A2, A3, A4, A5> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, p1, p2, p3, p4, p5, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6> CtorRef6<Self, A1, A2, A3, A4, A5, A6> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declare(UnsafeInvocables.ctor(builder().self(), p1, p2, p3, p4, p5, p6, traits(MemberTraits.DEFAULT)));
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
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, R, A1, A2, A3, A4, A5, A6> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, p1, p2, p3, p4, p5, p6, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6> VoidMethodRef6<Self, A1, A2, A3, A4, A5, A6> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, p1, p2, p3, p4, p5, p6, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> CtorRef7<Self, A1, A2, A3, A4, A5, A6, A7> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declare(UnsafeInvocables.ctor(builder().self(), p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.DEFAULT)));
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
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A7> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A7> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> CtorRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declare(UnsafeInvocables.ctor(builder().self(), p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.DEFAULT)));
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
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> CtorRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declare(UnsafeInvocables.ctor(builder().self(), p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.DEFAULT)));
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
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> CtorRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declare(UnsafeInvocables.ctor(builder().self(), p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.DEFAULT)));
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
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> CtorRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declare(UnsafeInvocables.ctor(builder().self(), p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.DEFAULT)));
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
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> CtorRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declare(UnsafeInvocables.ctor(builder().self(), p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.DEFAULT)));
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
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declare(UnsafeInvocables.method(builder().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declare(UnsafeInvocables.voidMethod(builder().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.FINAL)));
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
     * Declares a `public static` method returning `R`, to be defined by the bodies of the class.
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
    public <R, A1, A2> StaticMethodRef2<R, A1, A2> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, p1, p2, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2> VoidStaticMethodRef2<A1, A2> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, p1, p2, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3> StaticMethodRef3<R, A1, A2, A3> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, p1, p2, p3, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3> VoidStaticMethodRef3<A1, A2, A3> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, p1, p2, p3, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4> StaticMethodRef4<R, A1, A2, A3, A4> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, p1, p2, p3, p4, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4> VoidStaticMethodRef4<A1, A2, A3, A4> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, p1, p2, p3, p4, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5> StaticMethodRef5<R, A1, A2, A3, A4, A5> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, p1, p2, p3, p4, p5, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5> VoidStaticMethodRef5<A1, A2, A3, A4, A5> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, p1, p2, p3, p4, p5, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6> StaticMethodRef6<R, A1, A2, A3, A4, A5, A6> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, p1, p2, p3, p4, p5, p6, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6> VoidStaticMethodRef6<A1, A2, A3, A4, A5, A6> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, p1, p2, p3, p4, p5, p6, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7> StaticMethodRef7<R, A1, A2, A3, A4, A5, A6, A7> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> VoidStaticMethodRef7<A1, A2, A3, A4, A5, A6, A7> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8> StaticMethodRef8<R, A1, A2, A3, A4, A5, A6, A7, A8> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidStaticMethodRef8<A1, A2, A3, A4, A5, A6, A7, A8> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> StaticMethodRef9<R, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidStaticMethodRef9<A1, A2, A3, A4, A5, A6, A7, A8, A9> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> StaticMethodRef10<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidStaticMethodRef10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> StaticMethodRef11<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidStaticMethodRef11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> StaticMethodRef12<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declare(UnsafeInvocables.staticMethod(builder().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.FINAL)));
    }

    /**
     * Declares a `public static void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [SignatureRegistry])
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidStaticMethodRef12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declare(UnsafeInvocables.voidStaticMethod(builder().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.FINAL)));
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

    /**
     * Overrides a method of 3 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareOverride(MethodRef3<? super S, R, A1, A2, A3> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 3 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareOverride(Upcast<Self, ? extends O> up, MethodRef3<O, R, A1, A2, A3> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 3 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, R2 extends R, A1, A2, A3> MethodRef3<Self, R2, A1, A2, A3> declareOverride(MethodRef3<? super S, R, A1, A2, A3> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 3 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, R2 extends R, A1, A2, A3> MethodRef3<Self, R2, A1, A2, A3> declareOverride(Upcast<Self, ? extends O> up, MethodRef3<O, R, A1, A2, A3> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 3 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3> MethodRef3<Self, Self, A1, A2, A3> declareOverride(MethodRef3<? super S, R, A1, A2, A3> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 3 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3> MethodRef3<Self, Self, A1, A2, A3> declareOverride(Upcast<Self, ? extends O> up, MethodRef3<O, R, A1, A2, A3> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 3 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareOverride(VoidMethodRef3<? super S, A1, A2, A3> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 3 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef3<O, A1, A2, A3> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 4 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareOverride(MethodRef4<? super S, R, A1, A2, A3, A4> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 4 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareOverride(Upcast<Self, ? extends O> up, MethodRef4<O, R, A1, A2, A3, A4> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 4 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, R2 extends R, A1, A2, A3, A4> MethodRef4<Self, R2, A1, A2, A3, A4> declareOverride(MethodRef4<? super S, R, A1, A2, A3, A4> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 4 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4> MethodRef4<Self, R2, A1, A2, A3, A4> declareOverride(Upcast<Self, ? extends O> up, MethodRef4<O, R, A1, A2, A3, A4> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 4 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4> MethodRef4<Self, Self, A1, A2, A3, A4> declareOverride(MethodRef4<? super S, R, A1, A2, A3, A4> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 4 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4> MethodRef4<Self, Self, A1, A2, A3, A4> declareOverride(Upcast<Self, ? extends O> up, MethodRef4<O, R, A1, A2, A3, A4> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 4 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareOverride(VoidMethodRef4<? super S, A1, A2, A3, A4> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 4 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef4<O, A1, A2, A3, A4> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 5 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5> MethodRef5<Self, R, A1, A2, A3, A4, A5> declareOverride(MethodRef5<? super S, R, A1, A2, A3, A4, A5> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 5 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5> MethodRef5<Self, R, A1, A2, A3, A4, A5> declareOverride(Upcast<Self, ? extends O> up, MethodRef5<O, R, A1, A2, A3, A4, A5> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 5 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, R2 extends R, A1, A2, A3, A4, A5> MethodRef5<Self, R2, A1, A2, A3, A4, A5> declareOverride(MethodRef5<? super S, R, A1, A2, A3, A4, A5> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 5 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5> MethodRef5<Self, R2, A1, A2, A3, A4, A5> declareOverride(Upcast<Self, ? extends O> up, MethodRef5<O, R, A1, A2, A3, A4, A5> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 5 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5> MethodRef5<Self, Self, A1, A2, A3, A4, A5> declareOverride(MethodRef5<? super S, R, A1, A2, A3, A4, A5> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 5 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5> MethodRef5<Self, Self, A1, A2, A3, A4, A5> declareOverride(Upcast<Self, ? extends O> up, MethodRef5<O, R, A1, A2, A3, A4, A5> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 5 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5> VoidMethodRef5<Self, A1, A2, A3, A4, A5> declareOverride(VoidMethodRef5<? super S, A1, A2, A3, A4, A5> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 5 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, A1, A2, A3, A4, A5> VoidMethodRef5<Self, A1, A2, A3, A4, A5> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef5<O, A1, A2, A3, A4, A5> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 6 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, R, A1, A2, A3, A4, A5, A6> declareOverride(MethodRef6<? super S, R, A1, A2, A3, A4, A5, A6> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 6 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, R, A1, A2, A3, A4, A5, A6> declareOverride(Upcast<Self, ? extends O> up, MethodRef6<O, R, A1, A2, A3, A4, A5, A6> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 6 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, R2 extends R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, R2, A1, A2, A3, A4, A5, A6> declareOverride(MethodRef6<? super S, R, A1, A2, A3, A4, A5, A6> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 6 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, R2, A1, A2, A3, A4, A5, A6> declareOverride(Upcast<Self, ? extends O> up, MethodRef6<O, R, A1, A2, A3, A4, A5, A6> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 6 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, Self, A1, A2, A3, A4, A5, A6> declareOverride(MethodRef6<? super S, R, A1, A2, A3, A4, A5, A6> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 6 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, Self, A1, A2, A3, A4, A5, A6> declareOverride(Upcast<Self, ? extends O> up, MethodRef6<O, R, A1, A2, A3, A4, A5, A6> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 6 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6> VoidMethodRef6<Self, A1, A2, A3, A4, A5, A6> declareOverride(VoidMethodRef6<? super S, A1, A2, A3, A4, A5, A6> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 6 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, A1, A2, A3, A4, A5, A6> VoidMethodRef6<Self, A1, A2, A3, A4, A5, A6> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef6<O, A1, A2, A3, A4, A5, A6> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 7 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A7> declareOverride(MethodRef7<? super S, R, A1, A2, A3, A4, A5, A6, A7> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 7 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A7> declareOverride(Upcast<Self, ? extends O> up, MethodRef7<O, R, A1, A2, A3, A4, A5, A6, A7> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 7 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, R2 extends R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, R2, A1, A2, A3, A4, A5, A6, A7> declareOverride(MethodRef7<? super S, R, A1, A2, A3, A4, A5, A6, A7> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 7 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, R2, A1, A2, A3, A4, A5, A6, A7> declareOverride(Upcast<Self, ? extends O> up, MethodRef7<O, R, A1, A2, A3, A4, A5, A6, A7> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 7 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, Self, A1, A2, A3, A4, A5, A6, A7> declareOverride(MethodRef7<? super S, R, A1, A2, A3, A4, A5, A6, A7> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 7 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, Self, A1, A2, A3, A4, A5, A6, A7> declareOverride(Upcast<Self, ? extends O> up, MethodRef7<O, R, A1, A2, A3, A4, A5, A6, A7> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 7 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A7> declareOverride(VoidMethodRef7<? super S, A1, A2, A3, A4, A5, A6, A7> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 7 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7> VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A7> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef7<O, A1, A2, A3, A4, A5, A6, A7> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 8 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> declareOverride(MethodRef8<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 8 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> declareOverride(Upcast<Self, ? extends O> up, MethodRef8<O, R, A1, A2, A3, A4, A5, A6, A7, A8> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 8 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8> declareOverride(MethodRef8<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 8 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8> declareOverride(Upcast<Self, ? extends O> up, MethodRef8<O, R, A1, A2, A3, A4, A5, A6, A7, A8> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 8 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8> declareOverride(MethodRef8<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 8 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8> declareOverride(Upcast<Self, ? extends O> up, MethodRef8<O, R, A1, A2, A3, A4, A5, A6, A7, A8> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 8 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> declareOverride(VoidMethodRef8<? super S, A1, A2, A3, A4, A5, A6, A7, A8> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 8 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7, A8> VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef8<O, A1, A2, A3, A4, A5, A6, A7, A8> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 9 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareOverride(MethodRef9<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 9 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareOverride(Upcast<Self, ? extends O> up, MethodRef9<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 9 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareOverride(MethodRef9<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 9 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareOverride(Upcast<Self, ? extends O> up, MethodRef9<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 9 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareOverride(MethodRef9<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 9 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareOverride(Upcast<Self, ? extends O> up, MethodRef9<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 9 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareOverride(VoidMethodRef9<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 9 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef9<O, A1, A2, A3, A4, A5, A6, A7, A8, A9> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 10 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareOverride(MethodRef10<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 10 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareOverride(Upcast<Self, ? extends O> up, MethodRef10<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 10 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareOverride(MethodRef10<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 10 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareOverride(Upcast<Self, ? extends O> up, MethodRef10<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 10 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareOverride(MethodRef10<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 10 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareOverride(Upcast<Self, ? extends O> up, MethodRef10<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 10 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareOverride(VoidMethodRef10<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 10 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef10<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 11 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareOverride(MethodRef11<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 11 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareOverride(Upcast<Self, ? extends O> up, MethodRef11<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 11 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareOverride(MethodRef11<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 11 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareOverride(Upcast<Self, ? extends O> up, MethodRef11<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 11 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareOverride(MethodRef11<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 11 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareOverride(Upcast<Self, ? extends O> up, MethodRef11<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 11 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareOverride(VoidMethodRef11<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 11 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef11<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 12 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareOverride(MethodRef12<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method of 12 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareOverride(Upcast<Self, ? extends O> up, MethodRef12<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 12 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the result type of the method of the class
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareOverride(MethodRef12<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method of a narrower result (JLS 8.4.5) of 12 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the result type of the method of the class
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <R2> the result type of the method of the class
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, R2 extends R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, R2, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareOverride(Upcast<Self, ? extends O> up, MethodRef12<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, RefToken<R2> result) {
        return overriding(Overrides.own(builder().self(), method, result, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 12 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param result the witness that the class is of the result type
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareOverride(MethodRef12<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a method with a method whose result is the class itself (JLS 8.4.5) of 12 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param result the witness that the class is of the result type
     * @param <O> the type that has the method
     * @param <R> the result type of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareOverride(Upcast<Self, ? extends O> up, MethodRef12<O, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Upcast<Self, ? extends R> result) {
        return overriding(Overrides.ownOfSelf(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 12 parameters the class inherits from its superclass: declares it anew, `@Override public`.
     *
     * @param method the fact of the overridden method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareOverride(VoidMethodRef12<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }

    /**
     * Overrides a `void` method of 12 parameters of a supertype the class has by the witness `up`.
     *
     * @param method the fact of the overridden method
     * @param up the witness that the class is of the supertype
     * @param <O> the type that has the method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @param <A10> the type of the tenth parameter
     * @param <A11> the type of the eleventh parameter
     * @param <A12> the type of the twelfth parameter
     * @return the fact of the overriding method
     * @throws IllegalArgumentException if the method cannot be overridden, see [SignatureRegistry]
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareOverride(Upcast<Self, ? extends O> up, VoidMethodRef12<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method) {
        return overriding(Overrides.own(builder().self(), method, thrown()));
    }
}
