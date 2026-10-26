package me.supcheg.javafile.typed;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class VariableArityDeclarationArities<Self, S> permits VariableArityDeclarations {
    abstract <F extends Invocable> F declare(F member);

    abstract FinalClassToken<Self> self();

    abstract MemberTraits traits(MemberTraits traits, ArrayToken<?, ?> last);

    /**
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A> CtorRef1<Self, A> declareConstructor(ArrayToken<A, ?> last) {
        return declare(UnsafeInvocables.ctor(self(), last, traits(MemberTraits.DEFAULT, last)));
    }

    /**
     * Declares a `public` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A> MethodRef1<Self, R, A> declareMethod(String name, TypeToken<R> result, ArrayToken<A, ?> last) {
        return declare(UnsafeInvocables.method(self(), name, result, last, traits(MemberTraits.FINAL, last)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A> VoidMethodRef1<Self, A> declareVoidMethod(String name, ArrayToken<A, ?> last) {
        return declare(UnsafeInvocables.voidMethod(self(), name, last, traits(MemberTraits.FINAL, last)));
    }

    /**
     * Declares a `public` constructor, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the constructor
     * @throws IllegalArgumentException if javac would reject the constructor
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A> CtorRef2<Self, A1, A> declareConstructor(TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declare(UnsafeInvocables.ctor(self(), p1, last, traits(MemberTraits.DEFAULT, last)));
    }

    /**
     * Declares a `public` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A1, A> MethodRef2<Self, R, A1, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declare(UnsafeInvocables.method(self(), name, result, p1, last, traits(MemberTraits.FINAL, last)));
    }

    /**
     * Declares a `public void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A1, A> VoidMethodRef2<Self, A1, A> declareVoidMethod(String name, TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declare(UnsafeInvocables.voidMethod(self(), name, p1, last, traits(MemberTraits.FINAL, last)));
    }

    /**
     * Declares a `public static` method returning `R`, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <R, A> StaticMethodRef1<R, A> declareStaticMethod(String name, TypeToken<R> result, ArrayToken<A, ?> last) {
        return declare(UnsafeInvocables.staticMethod(self(), name, result, last, traits(MemberTraits.FINAL, last)));
    }

    /**
     * Declares a `public static void` method, to be defined by the bodies of the class.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method
     * @throws IllegalStateException if the declarations of the class have ended
     */
    public <A> VoidStaticMethodRef1<A> declareVoidStaticMethod(String name, ArrayToken<A, ?> last) {
        return declare(UnsafeInvocables.voidStaticMethod(self(), name, last, traits(MemberTraits.FINAL, last)));
    }
}
