package me.supcheg.javafile.typed;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.CtorRef3;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.Sam2;
import me.supcheg.javafile.facts.Sam3;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef3;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.VoidSam1;
import me.supcheg.javafile.facts.VoidSam2;
import me.supcheg.javafile.facts.VoidSam3;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef3;
import me.supcheg.javafile.typed.Expressions.LambdaValue;
import me.supcheg.javafile.typed.Node.Call;
import me.supcheg.javafile.typed.Node.Passing;
import me.supcheg.javafile.typed.Node.StaticCall;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class ExpressionArities permits Expressions {
    /**
     * Calls an instance method of 0 parameters.
     *
     * @param method the fact of the method
     * @param target the receiver
     * @return the call
     */
    public static <O, R> Invocation<R> call(Expr<? extends O> target, MethodRef0<O, R> method) {
        return new Invocation<>(new Call(Expressions.receiver(target, method), method, Expressions.arguments(), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 1 parameter.
     *
     * @param method the fact of the method
     * @param target the receiver
     * @param a1 the argument
     * @param <A1> the type of the parameter
     * @return the call
     */
    public static <O, R, A1> Invocation<R> call(Expr<? extends O> target, MethodRef1<O, R, A1> method, Expr<? extends A1> a1) {
        return new Invocation<>(new Call(Expressions.receiver(target, method), method, Expressions.arguments(a1), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 1 parameter, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param target the receiver
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the parameter
     * @return the call
     */
    public static <O, R, A1> Invocation<R> call(Expr<? extends O> target, MethodRef1<O, R, A1> method, Varargs<A1> rest) {
        return new Invocation<>(Expressions.callNode(target, method, rest, Expressions.arguments()), method.result());
    }

    /**
     * Calls an instance method of 2 parameters.
     *
     * @param method the fact of the method
     * @param target the receiver
     * @param a1 the first argument
     * @param a2 the second argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     */
    public static <O, R, A1, A2> Invocation<R> call(Expr<? extends O> target, MethodRef2<O, R, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new Invocation<>(new Call(Expressions.receiver(target, method), method, Expressions.arguments(a1, a2), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 2 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param target the receiver
     * @param a1 the argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     */
    public static <O, R, A1, A2> Invocation<R> call(Expr<? extends O> target, MethodRef2<O, R, A1, A2> method, Expr<? extends A1> a1, Varargs<A2> rest) {
        return new Invocation<>(Expressions.callNode(target, method, rest, Expressions.arguments(a1)), method.result());
    }

    /**
     * Calls an instance method of 3 parameters.
     *
     * @param method the fact of the method
     * @param target the receiver
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the call
     */
    public static <O, R, A1, A2, A3> Invocation<R> call(Expr<? extends O> target, MethodRef3<O, R, A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3) {
        return new Invocation<>(new Call(Expressions.receiver(target, method), method, Expressions.arguments(a1, a2, a3), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 3 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param target the receiver
     * @param a1 the first argument
     * @param a2 the second argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the call
     */
    public static <O, R, A1, A2, A3> Invocation<R> call(Expr<? extends O> target, MethodRef3<O, R, A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Varargs<A3> rest) {
        return new Invocation<>(Expressions.callNode(target, method, rest, Expressions.arguments(a1, a2)), method.result());
    }

    /**
     * Calls an instance `void` method of 0 parameters.
     *
     * @param method the fact of the method
     * @param target the receiver
     * @return the call
     */
    public static <O> VoidInvocation voidCall(Expr<? extends O> target, VoidMethodRef0<O> method) {
        return new VoidInvocation(new Call(Expressions.receiver(target, method), method, Expressions.arguments(), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 1 parameter.
     *
     * @param method the fact of the method
     * @param target the receiver
     * @param a1 the argument
     * @param <A1> the type of the parameter
     * @return the call
     */
    public static <O, A1> VoidInvocation voidCall(Expr<? extends O> target, VoidMethodRef1<O, A1> method, Expr<? extends A1> a1) {
        return new VoidInvocation(new Call(Expressions.receiver(target, method), method, Expressions.arguments(a1), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 1 parameter, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param target the receiver
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the parameter
     * @return the call
     */
    public static <O, A1> VoidInvocation voidCall(Expr<? extends O> target, VoidMethodRef1<O, A1> method, Varargs<A1> rest) {
        return new VoidInvocation(Expressions.callNode(target, method, rest, Expressions.arguments()));
    }

    /**
     * Calls an instance `void` method of 2 parameters.
     *
     * @param method the fact of the method
     * @param target the receiver
     * @param a1 the first argument
     * @param a2 the second argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     */
    public static <O, A1, A2> VoidInvocation voidCall(Expr<? extends O> target, VoidMethodRef2<O, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new VoidInvocation(new Call(Expressions.receiver(target, method), method, Expressions.arguments(a1, a2), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 2 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param target the receiver
     * @param a1 the argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     */
    public static <O, A1, A2> VoidInvocation voidCall(Expr<? extends O> target, VoidMethodRef2<O, A1, A2> method, Expr<? extends A1> a1, Varargs<A2> rest) {
        return new VoidInvocation(Expressions.callNode(target, method, rest, Expressions.arguments(a1)));
    }

    /**
     * Calls an instance `void` method of 3 parameters.
     *
     * @param method the fact of the method
     * @param target the receiver
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the call
     */
    public static <O, A1, A2, A3> VoidInvocation voidCall(Expr<? extends O> target, VoidMethodRef3<O, A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3) {
        return new VoidInvocation(new Call(Expressions.receiver(target, method), method, Expressions.arguments(a1, a2, a3), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 3 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param target the receiver
     * @param a1 the first argument
     * @param a2 the second argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the call
     */
    public static <O, A1, A2, A3> VoidInvocation voidCall(Expr<? extends O> target, VoidMethodRef3<O, A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Varargs<A3> rest) {
        return new VoidInvocation(Expressions.callNode(target, method, rest, Expressions.arguments(a1, a2)));
    }

    /**
     * Calls a static method of 0 parameters.
     *
     * @param method the fact of the method
     * @return the call
     */
    public static <R> Invocation<R> staticCall(StaticMethodRef0<R> method) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 1 parameter.
     *
     * @param method the fact of the method
     * @param a1 the argument
     * @param <A1> the type of the parameter
     * @return the call
     */
    public static <R, A1> Invocation<R> staticCall(StaticMethodRef1<R, A1> method, Expr<? extends A1> a1) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 1 parameter, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the parameter
     * @return the call
     */
    public static <R, A1> Invocation<R> staticCall(StaticMethodRef1<R, A1> method, Varargs<A1> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments()), method.result());
    }

    /**
     * Calls a static method of 2 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     */
    public static <R, A1, A2> Invocation<R> staticCall(StaticMethodRef2<R, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 2 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     */
    public static <R, A1, A2> Invocation<R> staticCall(StaticMethodRef2<R, A1, A2> method, Expr<? extends A1> a1, Varargs<A2> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1)), method.result());
    }

    /**
     * Calls a static method of 3 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the call
     */
    public static <R, A1, A2, A3> Invocation<R> staticCall(StaticMethodRef3<R, A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2, a3), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 3 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the call
     */
    public static <R, A1, A2, A3> Invocation<R> staticCall(StaticMethodRef3<R, A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Varargs<A3> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2)), method.result());
    }

    /**
     * Calls a static `void` method of 0 parameters.
     *
     * @param method the fact of the method
     * @return the call
     */
    public static VoidInvocation voidStaticCall(VoidStaticMethodRef0 method) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 1 parameter.
     *
     * @param method the fact of the method
     * @param a1 the argument
     * @param <A1> the type of the parameter
     * @return the call
     */
    public static <A1> VoidInvocation voidStaticCall(VoidStaticMethodRef1<A1> method, Expr<? extends A1> a1) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 1 parameter, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the parameter
     * @return the call
     */
    public static <A1> VoidInvocation voidStaticCall(VoidStaticMethodRef1<A1> method, Varargs<A1> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments()));
    }

    /**
     * Calls a static `void` method of 2 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     */
    public static <A1, A2> VoidInvocation voidStaticCall(VoidStaticMethodRef2<A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 2 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     */
    public static <A1, A2> VoidInvocation voidStaticCall(VoidStaticMethodRef2<A1, A2> method, Expr<? extends A1> a1, Varargs<A2> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1)));
    }

    /**
     * Calls a static `void` method of 3 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the call
     */
    public static <A1, A2, A3> VoidInvocation voidStaticCall(VoidStaticMethodRef3<A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2, a3), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 3 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the call
     */
    public static <A1, A2, A3> VoidInvocation voidStaticCall(VoidStaticMethodRef3<A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Varargs<A3> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2)));
    }

    /**
     * Calls a constructor of 0 parameters.
     *
     * @param ctor the fact of the constructor
     * @return the call
     */
    public static <O> Invocation<O> new_(CtorRef0<O> ctor) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 1 parameter.
     *
     * @param ctor the fact of the constructor
     * @param a1 the argument
     * @param <A1> the type of the parameter
     * @return the call
     */
    public static <O, A1> Invocation<O> new_(CtorRef1<O, A1> ctor, Expr<? extends A1> a1) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 1 parameter, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the parameter
     * @return the call
     */
    public static <O, A1> Invocation<O> new_(CtorRef1<O, A1> ctor, Varargs<A1> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments()), ctor.owner());
    }

    /**
     * Calls a constructor of 2 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     */
    public static <O, A1, A2> Invocation<O> new_(CtorRef2<O, A1, A2> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 2 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     */
    public static <O, A1, A2> Invocation<O> new_(CtorRef2<O, A1, A2> ctor, Expr<? extends A1> a1, Varargs<A2> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1)), ctor.owner());
    }

    /**
     * Calls a constructor of 3 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the call
     */
    public static <O, A1, A2, A3> Invocation<O> new_(CtorRef3<O, A1, A2, A3> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2, a3), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 3 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the call
     */
    public static <O, A1, A2, A3> Invocation<O> new_(CtorRef3<O, A1, A2, A3> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Varargs<A3> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1, a2)), ctor.owner());
    }

    /**
     * A lambda with an expression body, `() -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R> Expr<F> lambda(Sam0<F, R> sam, Supplier<? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            return new LambdaValue(List.of(), Expressions.returned(body.get(), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `() -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect
     * @param <F> the functional interface
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F> Expr<F> lambda(VoidSam0<F> sam, Supplier<? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            return new LambdaValue(List.of(), Assignment.nodeOf(body.get()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1> Expr<F> lambda(Sam1<F, R, A1> sam, Function<? super Var<A1>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            return new LambdaValue(List.of(p1), Expressions.returned(body.apply(p1), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1> Expr<F> lambda(VoidSam1<F, A1> sam, Function<? super Var<A1>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            return new LambdaValue(List.of(p1), Assignment.nodeOf(body.apply(p1)));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2> Expr<F> lambda(Sam2<F, R, A1, A2> sam, BiFunction<? super Var<A1>, ? super Var<A2>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            return new LambdaValue(List.of(p1, p2), Expressions.returned(body.apply(p1, p2), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2> Expr<F> lambda(VoidSam2<F, A1, A2> sam, BiFunction<? super Var<A1>, ? super Var<A2>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            return new LambdaValue(List.of(p1, p2), Assignment.nodeOf(body.apply(p1, p2)));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2, A3> Expr<F> lambda(Sam3<F, R, A1, A2, A3> sam, Function3<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            return new LambdaValue(List.of(p1, p2, p3), Expressions.returned(body.apply(p1, p2, p3), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2, A3> Expr<F> lambda(VoidSam3<F, A1, A2, A3> sam, Function3<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            return new LambdaValue(List.of(p1, p2, p3), Assignment.nodeOf(body.apply(p1, p2, p3)));
        });
    }

    /**
     * A lambda with a block body, `() -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R> Expr<F> lambdaBlock(Sam0<F, R> sam, Function<? super Body<R>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(), (b) -> body.apply(b));
    }

    /**
     * A lambda with a block body, `() -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block
     * @param <F> the functional interface
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F> Expr<F> lambdaBlock(VoidSam0<F> sam, Function<? super VoidBody, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(), (b) -> body.apply(b));
    }

    /**
     * A lambda with a block body, `(A1 a1) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1> Expr<F> lambdaBlock(Sam1<F, R, A1> sam, BiFunction<? super Body<R>, ? super Var<A1>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1), (b) -> body.apply(b, p1));
    }

    /**
     * A lambda with a block body, `(A1 a1) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1> Expr<F> lambdaBlock(VoidSam1<F, A1> sam, BiFunction<? super VoidBody, ? super Var<A1>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1), (b) -> body.apply(b, p1));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2> Expr<F> lambdaBlock(Sam2<F, R, A1, A2> sam, Function3<? super Body<R>, ? super Var<A1>, ? super Var<A2>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2), (b) -> body.apply(b, p1, p2));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2> Expr<F> lambdaBlock(VoidSam2<F, A1, A2> sam, Function3<? super VoidBody, ? super Var<A1>, ? super Var<A2>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2), (b) -> body.apply(b, p1, p2));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2, A3> Expr<F> lambdaBlock(Sam3<F, R, A1, A2, A3> sam, Function4<? super Body<R>, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3), (b) -> body.apply(b, p1, p2, p3));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2, A3> Expr<F> lambdaBlock(VoidSam3<F, A1, A2, A3> sam, Function4<? super VoidBody, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3), (b) -> body.apply(b, p1, p2, p3));
    }
}
