package me.supcheg.javafile.typed;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef10;
import me.supcheg.javafile.facts.CtorRef11;
import me.supcheg.javafile.facts.CtorRef12;
import me.supcheg.javafile.facts.CtorRef13;
import me.supcheg.javafile.facts.CtorRef14;
import me.supcheg.javafile.facts.CtorRef15;
import me.supcheg.javafile.facts.CtorRef16;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.CtorRef3;
import me.supcheg.javafile.facts.CtorRef4;
import me.supcheg.javafile.facts.CtorRef5;
import me.supcheg.javafile.facts.CtorRef6;
import me.supcheg.javafile.facts.CtorRef7;
import me.supcheg.javafile.facts.CtorRef8;
import me.supcheg.javafile.facts.CtorRef9;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.Sam10;
import me.supcheg.javafile.facts.Sam11;
import me.supcheg.javafile.facts.Sam12;
import me.supcheg.javafile.facts.Sam13;
import me.supcheg.javafile.facts.Sam14;
import me.supcheg.javafile.facts.Sam15;
import me.supcheg.javafile.facts.Sam16;
import me.supcheg.javafile.facts.Sam2;
import me.supcheg.javafile.facts.Sam3;
import me.supcheg.javafile.facts.Sam4;
import me.supcheg.javafile.facts.Sam5;
import me.supcheg.javafile.facts.Sam6;
import me.supcheg.javafile.facts.Sam7;
import me.supcheg.javafile.facts.Sam8;
import me.supcheg.javafile.facts.Sam9;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef10;
import me.supcheg.javafile.facts.StaticMethodRef11;
import me.supcheg.javafile.facts.StaticMethodRef12;
import me.supcheg.javafile.facts.StaticMethodRef13;
import me.supcheg.javafile.facts.StaticMethodRef14;
import me.supcheg.javafile.facts.StaticMethodRef15;
import me.supcheg.javafile.facts.StaticMethodRef16;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef3;
import me.supcheg.javafile.facts.StaticMethodRef4;
import me.supcheg.javafile.facts.StaticMethodRef5;
import me.supcheg.javafile.facts.StaticMethodRef6;
import me.supcheg.javafile.facts.StaticMethodRef7;
import me.supcheg.javafile.facts.StaticMethodRef8;
import me.supcheg.javafile.facts.StaticMethodRef9;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.facts.VoidSam1;
import me.supcheg.javafile.facts.VoidSam10;
import me.supcheg.javafile.facts.VoidSam11;
import me.supcheg.javafile.facts.VoidSam12;
import me.supcheg.javafile.facts.VoidSam13;
import me.supcheg.javafile.facts.VoidSam14;
import me.supcheg.javafile.facts.VoidSam15;
import me.supcheg.javafile.facts.VoidSam16;
import me.supcheg.javafile.facts.VoidSam2;
import me.supcheg.javafile.facts.VoidSam3;
import me.supcheg.javafile.facts.VoidSam4;
import me.supcheg.javafile.facts.VoidSam5;
import me.supcheg.javafile.facts.VoidSam6;
import me.supcheg.javafile.facts.VoidSam7;
import me.supcheg.javafile.facts.VoidSam8;
import me.supcheg.javafile.facts.VoidSam9;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef10;
import me.supcheg.javafile.facts.VoidStaticMethodRef11;
import me.supcheg.javafile.facts.VoidStaticMethodRef12;
import me.supcheg.javafile.facts.VoidStaticMethodRef13;
import me.supcheg.javafile.facts.VoidStaticMethodRef14;
import me.supcheg.javafile.facts.VoidStaticMethodRef15;
import me.supcheg.javafile.facts.VoidStaticMethodRef16;
import me.supcheg.javafile.facts.VoidStaticMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef3;
import me.supcheg.javafile.facts.VoidStaticMethodRef4;
import me.supcheg.javafile.facts.VoidStaticMethodRef5;
import me.supcheg.javafile.facts.VoidStaticMethodRef6;
import me.supcheg.javafile.facts.VoidStaticMethodRef7;
import me.supcheg.javafile.facts.VoidStaticMethodRef8;
import me.supcheg.javafile.facts.VoidStaticMethodRef9;
import me.supcheg.javafile.typed.Expressions.LambdaValue;
import me.supcheg.javafile.typed.Node.Passing;
import me.supcheg.javafile.typed.Node.StaticCall;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class ExpressionArities permits Expressions {
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
     * Calls a static method of 4 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4> Invocation<R> staticCall(StaticMethodRef4<R, A1, A2, A3, A4> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 4 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4> Invocation<R> staticCall(StaticMethodRef4<R, A1, A2, A3, A4> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Varargs<A4> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3)), method.result());
    }

    /**
     * Calls a static method of 5 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5> Invocation<R> staticCall(StaticMethodRef5<R, A1, A2, A3, A4, A5> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 5 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5> Invocation<R> staticCall(StaticMethodRef5<R, A1, A2, A3, A4, A5> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Varargs<A5> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4)), method.result());
    }

    /**
     * Calls a static method of 6 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6> Invocation<R> staticCall(StaticMethodRef6<R, A1, A2, A3, A4, A5, A6> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 6 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6> Invocation<R> staticCall(StaticMethodRef6<R, A1, A2, A3, A4, A5, A6> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Varargs<A6> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5)), method.result());
    }

    /**
     * Calls a static method of 7 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7> Invocation<R> staticCall(StaticMethodRef7<R, A1, A2, A3, A4, A5, A6, A7> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 7 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7> Invocation<R> staticCall(StaticMethodRef7<R, A1, A2, A3, A4, A5, A6, A7> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Varargs<A7> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6)), method.result());
    }

    /**
     * Calls a static method of 8 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8> Invocation<R> staticCall(StaticMethodRef8<R, A1, A2, A3, A4, A5, A6, A7, A8> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 8 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8> Invocation<R> staticCall(StaticMethodRef8<R, A1, A2, A3, A4, A5, A6, A7, A8> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Varargs<A8> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7)), method.result());
    }

    /**
     * Calls a static method of 9 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> Invocation<R> staticCall(StaticMethodRef9<R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 9 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> Invocation<R> staticCall(StaticMethodRef9<R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Varargs<A9> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8)), method.result());
    }

    /**
     * Calls a static method of 10 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
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
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Invocation<R> staticCall(StaticMethodRef10<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 10 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param rest the arguments of the last parameter
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
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Invocation<R> staticCall(StaticMethodRef10<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Varargs<A10> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9)), method.result());
    }

    /**
     * Calls a static method of 11 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
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
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Invocation<R> staticCall(StaticMethodRef11<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 11 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param rest the arguments of the last parameter
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
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Invocation<R> staticCall(StaticMethodRef11<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Varargs<A11> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)), method.result());
    }

    /**
     * Calls a static method of 12 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
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
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Invocation<R> staticCall(StaticMethodRef12<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 12 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param rest the arguments of the last parameter
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
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Invocation<R> staticCall(StaticMethodRef12<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Varargs<A12> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)), method.result());
    }

    /**
     * Calls a static method of 13 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
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
     * @param <A13> the type of the thirteenth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> Invocation<R> staticCall(StaticMethodRef13<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 13 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param rest the arguments of the last parameter
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
     * @param <A13> the type of the thirteenth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> Invocation<R> staticCall(StaticMethodRef13<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Varargs<A13> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12)), method.result());
    }

    /**
     * Calls a static method of 14 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> Invocation<R> staticCall(StaticMethodRef14<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 14 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param rest the arguments of the last parameter
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> Invocation<R> staticCall(StaticMethodRef14<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Varargs<A14> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13)), method.result());
    }

    /**
     * Calls a static method of 15 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
     * @param a15 the fifteenth argument
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> Invocation<R> staticCall(StaticMethodRef15<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14, Expr<? extends A15> a15) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 15 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
     * @param rest the arguments of the last parameter
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> Invocation<R> staticCall(StaticMethodRef15<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14, Varargs<A15> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14)), method.result());
    }

    /**
     * Calls a static method of 16 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
     * @param a15 the fifteenth argument
     * @param a16 the sixteenth argument
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> Invocation<R> staticCall(StaticMethodRef16<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14, Expr<? extends A15> a15, Expr<? extends A16> a16) {
        return new Invocation<>(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16), Passing.EACH), method.result());
    }

    /**
     * Calls a static method of 16 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
     * @param a15 the fifteenth argument
     * @param rest the arguments of the last parameter
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return the call
     */
    public static <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> Invocation<R> staticCall(StaticMethodRef16<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14, Expr<? extends A15> a15, Varargs<A16> rest) {
        return new Invocation<>(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)), method.result());
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
     * Calls a static `void` method of 4 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4> VoidInvocation voidStaticCall(VoidStaticMethodRef4<A1, A2, A3, A4> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 4 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4> VoidInvocation voidStaticCall(VoidStaticMethodRef4<A1, A2, A3, A4> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Varargs<A4> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3)));
    }

    /**
     * Calls a static `void` method of 5 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5> VoidInvocation voidStaticCall(VoidStaticMethodRef5<A1, A2, A3, A4, A5> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 5 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5> VoidInvocation voidStaticCall(VoidStaticMethodRef5<A1, A2, A3, A4, A5> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Varargs<A5> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4)));
    }

    /**
     * Calls a static `void` method of 6 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6> VoidInvocation voidStaticCall(VoidStaticMethodRef6<A1, A2, A3, A4, A5, A6> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 6 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6> VoidInvocation voidStaticCall(VoidStaticMethodRef6<A1, A2, A3, A4, A5, A6> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Varargs<A6> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5)));
    }

    /**
     * Calls a static `void` method of 7 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7> VoidInvocation voidStaticCall(VoidStaticMethodRef7<A1, A2, A3, A4, A5, A6, A7> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 7 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7> VoidInvocation voidStaticCall(VoidStaticMethodRef7<A1, A2, A3, A4, A5, A6, A7> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Varargs<A7> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6)));
    }

    /**
     * Calls a static `void` method of 8 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8> VoidInvocation voidStaticCall(VoidStaticMethodRef8<A1, A2, A3, A4, A5, A6, A7, A8> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 8 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8> VoidInvocation voidStaticCall(VoidStaticMethodRef8<A1, A2, A3, A4, A5, A6, A7, A8> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Varargs<A8> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7)));
    }

    /**
     * Calls a static `void` method of 9 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidInvocation voidStaticCall(VoidStaticMethodRef9<A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 9 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidInvocation voidStaticCall(VoidStaticMethodRef9<A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Varargs<A9> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8)));
    }

    /**
     * Calls a static `void` method of 10 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
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
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidInvocation voidStaticCall(VoidStaticMethodRef10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 10 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param rest the arguments of the last parameter
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
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidInvocation voidStaticCall(VoidStaticMethodRef10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Varargs<A10> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9)));
    }

    /**
     * Calls a static `void` method of 11 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
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
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidInvocation voidStaticCall(VoidStaticMethodRef11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 11 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param rest the arguments of the last parameter
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
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidInvocation voidStaticCall(VoidStaticMethodRef11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Varargs<A11> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)));
    }

    /**
     * Calls a static `void` method of 12 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
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
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidInvocation voidStaticCall(VoidStaticMethodRef12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 12 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param rest the arguments of the last parameter
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
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidInvocation voidStaticCall(VoidStaticMethodRef12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Varargs<A12> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)));
    }

    /**
     * Calls a static `void` method of 13 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
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
     * @param <A13> the type of the thirteenth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> VoidInvocation voidStaticCall(VoidStaticMethodRef13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 13 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param rest the arguments of the last parameter
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
     * @param <A13> the type of the thirteenth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> VoidInvocation voidStaticCall(VoidStaticMethodRef13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Varargs<A13> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12)));
    }

    /**
     * Calls a static `void` method of 14 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> VoidInvocation voidStaticCall(VoidStaticMethodRef14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 14 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param rest the arguments of the last parameter
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> VoidInvocation voidStaticCall(VoidStaticMethodRef14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Varargs<A14> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13)));
    }

    /**
     * Calls a static `void` method of 15 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
     * @param a15 the fifteenth argument
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> VoidInvocation voidStaticCall(VoidStaticMethodRef15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14, Expr<? extends A15> a15) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 15 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
     * @param rest the arguments of the last parameter
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> VoidInvocation voidStaticCall(VoidStaticMethodRef15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14, Varargs<A15> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14)));
    }

    /**
     * Calls a static `void` method of 16 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
     * @param a15 the fifteenth argument
     * @param a16 the sixteenth argument
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> VoidInvocation voidStaticCall(VoidStaticMethodRef16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14, Expr<? extends A15> a15, Expr<? extends A16> a16) {
        return new VoidInvocation(new StaticCall(method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16), Passing.EACH));
    }

    /**
     * Calls a static `void` method of 16 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
     * @param a15 the fifteenth argument
     * @param rest the arguments of the last parameter
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return the call
     */
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> VoidInvocation voidStaticCall(VoidStaticMethodRef16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14, Expr<? extends A15> a15, Varargs<A16> rest) {
        return new VoidInvocation(Expressions.staticCallNode(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)));
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
     * Calls a constructor of 4 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4> Invocation<O> new_(CtorRef4<O, A1, A2, A3, A4> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2, a3, a4), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 4 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4> Invocation<O> new_(CtorRef4<O, A1, A2, A3, A4> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Varargs<A4> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1, a2, a3)), ctor.owner());
    }

    /**
     * Calls a constructor of 5 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5> Invocation<O> new_(CtorRef5<O, A1, A2, A3, A4, A5> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2, a3, a4, a5), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 5 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5> Invocation<O> new_(CtorRef5<O, A1, A2, A3, A4, A5> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Varargs<A5> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1, a2, a3, a4)), ctor.owner());
    }

    /**
     * Calls a constructor of 6 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6> Invocation<O> new_(CtorRef6<O, A1, A2, A3, A4, A5, A6> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 6 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6> Invocation<O> new_(CtorRef6<O, A1, A2, A3, A4, A5, A6> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Varargs<A6> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5)), ctor.owner());
    }

    /**
     * Calls a constructor of 7 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7> Invocation<O> new_(CtorRef7<O, A1, A2, A3, A4, A5, A6, A7> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 7 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7> Invocation<O> new_(CtorRef7<O, A1, A2, A3, A4, A5, A6, A7> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Varargs<A7> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6)), ctor.owner());
    }

    /**
     * Calls a constructor of 8 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8> Invocation<O> new_(CtorRef8<O, A1, A2, A3, A4, A5, A6, A7, A8> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 8 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8> Invocation<O> new_(CtorRef8<O, A1, A2, A3, A4, A5, A6, A7, A8> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Varargs<A8> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7)), ctor.owner());
    }

    /**
     * Calls a constructor of 9 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9> Invocation<O> new_(CtorRef9<O, A1, A2, A3, A4, A5, A6, A7, A8, A9> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 9 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9> Invocation<O> new_(CtorRef9<O, A1, A2, A3, A4, A5, A6, A7, A8, A9> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Varargs<A9> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8)), ctor.owner());
    }

    /**
     * Calls a constructor of 10 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
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
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Invocation<O> new_(CtorRef10<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 10 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param rest the arguments of the last parameter
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
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Invocation<O> new_(CtorRef10<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Varargs<A10> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9)), ctor.owner());
    }

    /**
     * Calls a constructor of 11 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
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
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Invocation<O> new_(CtorRef11<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 11 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param rest the arguments of the last parameter
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
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Invocation<O> new_(CtorRef11<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Varargs<A11> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)), ctor.owner());
    }

    /**
     * Calls a constructor of 12 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
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
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Invocation<O> new_(CtorRef12<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 12 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param rest the arguments of the last parameter
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
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Invocation<O> new_(CtorRef12<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Varargs<A12> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)), ctor.owner());
    }

    /**
     * Calls a constructor of 13 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
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
     * @param <A13> the type of the thirteenth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> Invocation<O> new_(CtorRef13<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 13 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param rest the arguments of the last parameter
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
     * @param <A13> the type of the thirteenth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> Invocation<O> new_(CtorRef13<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Varargs<A13> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12)), ctor.owner());
    }

    /**
     * Calls a constructor of 14 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> Invocation<O> new_(CtorRef14<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 14 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param rest the arguments of the last parameter
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> Invocation<O> new_(CtorRef14<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Varargs<A14> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13)), ctor.owner());
    }

    /**
     * Calls a constructor of 15 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
     * @param a15 the fifteenth argument
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> Invocation<O> new_(CtorRef15<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14, Expr<? extends A15> a15) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 15 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
     * @param rest the arguments of the last parameter
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> Invocation<O> new_(CtorRef15<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14, Varargs<A15> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14)), ctor.owner());
    }

    /**
     * Calls a constructor of 16 parameters.
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
     * @param a15 the fifteenth argument
     * @param a16 the sixteenth argument
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> Invocation<O> new_(CtorRef16<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14, Expr<? extends A15> a15, Expr<? extends A16> a16) {
        return new Invocation<>(Expressions.instantiate(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16), Passing.EACH), ctor.owner());
    }

    /**
     * Calls a constructor of 16 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param ctor the fact of the constructor
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param a10 the tenth argument
     * @param a11 the eleventh argument
     * @param a12 the twelfth argument
     * @param a13 the thirteenth argument
     * @param a14 the fourteenth argument
     * @param a15 the fifteenth argument
     * @param rest the arguments of the last parameter
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return the call
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> Invocation<O> new_(CtorRef16<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, Expr<? extends A13> a13, Expr<? extends A14> a14, Expr<? extends A15> a15, Varargs<A16> rest) {
        return new Invocation<>(Expressions.newNode(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)), ctor.owner());
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 0 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
     * @return the constructor reference
     */
    public static <O, F> Expr<F> constructorRef(CtorRef0<O> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 1 parameter, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
     * @param <A1> the type of the parameter
     * @return the constructor reference
     */
    public static <O, A1, F> Expr<F> constructorRef(CtorRef1<O, A1> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 2 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the constructor reference
     */
    public static <O, A1, A2, F> Expr<F> constructorRef(CtorRef2<O, A1, A2> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 3 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the constructor reference
     */
    public static <O, A1, A2, A3, F> Expr<F> constructorRef(CtorRef3<O, A1, A2, A3> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 4 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the constructor reference
     */
    public static <O, A1, A2, A3, A4, F> Expr<F> constructorRef(CtorRef4<O, A1, A2, A3, A4> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 5 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the constructor reference
     */
    public static <O, A1, A2, A3, A4, A5, F> Expr<F> constructorRef(CtorRef5<O, A1, A2, A3, A4, A5> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 6 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the constructor reference
     */
    public static <O, A1, A2, A3, A4, A5, A6, F> Expr<F> constructorRef(CtorRef6<O, A1, A2, A3, A4, A5, A6> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 7 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the constructor reference
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, F> Expr<F> constructorRef(CtorRef7<O, A1, A2, A3, A4, A5, A6, A7> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 8 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the constructor reference
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, F> Expr<F> constructorRef(CtorRef8<O, A1, A2, A3, A4, A5, A6, A7, A8> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 9 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the constructor reference
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, F> Expr<F> constructorRef(CtorRef9<O, A1, A2, A3, A4, A5, A6, A7, A8, A9> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 10 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
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
     * @return the constructor reference
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, F> Expr<F> constructorRef(CtorRef10<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 11 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
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
     * @return the constructor reference
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, F> Expr<F> constructorRef(CtorRef11<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 12 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
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
     * @return the constructor reference
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, F> Expr<F> constructorRef(CtorRef12<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 13 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
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
     * @param <A13> the type of the thirteenth parameter
     * @return the constructor reference
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, F> Expr<F> constructorRef(CtorRef13<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 14 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return the constructor reference
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, F> Expr<F> constructorRef(CtorRef14<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 15 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return the constructor reference
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, F> Expr<F> constructorRef(CtorRef15<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
    }

    /**
     * A constructor reference, `Type::new`, of a constructor of 16 parameters, of the functional interface `samType` whose method `sam` takes the parameters of the constructor and has the constructed class as its result.
     *
     * @param ctor the fact of the constructor
     * @param samType the functional interface the reference implements
     * @param sam the single abstract method of `samType`
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return the constructor reference
     */
    public static <O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, F> Expr<F> constructorRef(CtorRef16<O, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> ctor, InterfaceToken<F> samType, Invocable sam) {
        return Expressions.constructorRef(ctor.owner(), ctor, samType, sam);
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
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2, A3, A4> Expr<F> lambda(Sam4<F, R, A1, A2, A3, A4> sam, Function4<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4), Expressions.returned(body.apply(p1, p2, p3, p4), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2, A3, A4> Expr<F> lambda(VoidSam4<F, A1, A2, A3, A4> sam, Function4<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4), Assignment.nodeOf(body.apply(p1, p2, p3, p4)));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2, A3, A4, A5> Expr<F> lambda(Sam5<F, R, A1, A2, A3, A4, A5> sam, Function5<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5), Expressions.returned(body.apply(p1, p2, p3, p4, p5), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2, A3, A4, A5> Expr<F> lambda(VoidSam5<F, A1, A2, A3, A4, A5> sam, Function5<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5), Assignment.nodeOf(body.apply(p1, p2, p3, p4, p5)));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2, A3, A4, A5, A6> Expr<F> lambda(Sam6<F, R, A1, A2, A3, A4, A5, A6> sam, Function6<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6), Expressions.returned(body.apply(p1, p2, p3, p4, p5, p6), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2, A3, A4, A5, A6> Expr<F> lambda(VoidSam6<F, A1, A2, A3, A4, A5, A6> sam, Function6<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6), Assignment.nodeOf(body.apply(p1, p2, p3, p4, p5, p6)));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7> Expr<F> lambda(Sam7<F, R, A1, A2, A3, A4, A5, A6, A7> sam, Function7<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7), Expressions.returned(body.apply(p1, p2, p3, p4, p5, p6, p7), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7> Expr<F> lambda(VoidSam7<F, A1, A2, A3, A4, A5, A6, A7> sam, Function7<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7), Assignment.nodeOf(body.apply(p1, p2, p3, p4, p5, p6, p7)));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8> Expr<F> lambda(Sam8<F, R, A1, A2, A3, A4, A5, A6, A7, A8> sam, Function8<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8), Expressions.returned(body.apply(p1, p2, p3, p4, p5, p6, p7, p8), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8> Expr<F> lambda(VoidSam8<F, A1, A2, A3, A4, A5, A6, A7, A8> sam, Function8<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8), Assignment.nodeOf(body.apply(p1, p2, p3, p4, p5, p6, p7, p8)));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> Expr<F> lambda(Sam9<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> sam, Function9<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), Expressions.returned(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9> Expr<F> lambda(VoidSam9<F, A1, A2, A3, A4, A5, A6, A7, A8, A9> sam, Function9<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), Assignment.nodeOf(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9)));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
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
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Expr<F> lambda(Sam10<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> sam, Function10<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), Expressions.returned(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
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
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Expr<F> lambda(VoidSam10<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> sam, Function10<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), Assignment.nodeOf(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
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
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Expr<F> lambda(Sam11<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> sam, Function11<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), scope);
            Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), Expressions.returned(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
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
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Expr<F> lambda(VoidSam11<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> sam, Function11<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), scope);
            Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), Assignment.nodeOf(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
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
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Expr<F> lambda(Sam12<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> sam, Function12<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), scope);
            Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), scope);
            Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), Expressions.returned(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
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
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Expr<F> lambda(VoidSam12<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> sam, Function12<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), scope);
            Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), scope);
            Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), Assignment.nodeOf(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
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
     * @param <A13> the type of the thirteenth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> Expr<F> lambda(Sam13<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> sam, Function13<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), scope);
            Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), scope);
            Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), scope);
            Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13), Expressions.returned(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
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
     * @param <A13> the type of the thirteenth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> Expr<F> lambda(VoidSam13<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> sam, Function13<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), scope);
            Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), scope);
            Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), scope);
            Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13), Assignment.nodeOf(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13)));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13, A14 a14) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> Expr<F> lambda(Sam14<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> sam, Function14<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, ? super Var<A14>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), scope);
            Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), scope);
            Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), scope);
            Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), scope);
            Var<A14> p14 = Expressions.lambdaParameter(sam.param14(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14), Expressions.returned(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13, A14 a14) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> Expr<F> lambda(VoidSam14<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> sam, Function14<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, ? super Var<A14>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), scope);
            Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), scope);
            Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), scope);
            Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), scope);
            Var<A14> p14 = Expressions.lambdaParameter(sam.param14(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14), Assignment.nodeOf(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13, A14 a14, A15 a15) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> Expr<F> lambda(Sam15<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> sam, Function15<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, ? super Var<A14>, ? super Var<A15>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), scope);
            Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), scope);
            Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), scope);
            Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), scope);
            Var<A14> p14 = Expressions.lambdaParameter(sam.param14(), scope);
            Var<A15> p15 = Expressions.lambdaParameter(sam.param15(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15), Expressions.returned(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13, A14 a14, A15 a15) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> Expr<F> lambda(VoidSam15<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> sam, Function15<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, ? super Var<A14>, ? super Var<A15>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), scope);
            Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), scope);
            Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), scope);
            Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), scope);
            Var<A14> p14 = Expressions.lambdaParameter(sam.param14(), scope);
            Var<A15> p15 = Expressions.lambdaParameter(sam.param15(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15), Assignment.nodeOf(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15)));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13, A14 a14, A15 a15, A16 a16) -&gt; value`, of the functional interface `sam` is the method of.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the value, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> Expr<F> lambda(Sam16<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> sam, Function16<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, ? super Var<A14>, ? super Var<A15>, ? super Var<A16>, ? extends Expr<? extends R>> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), scope);
            Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), scope);
            Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), scope);
            Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), scope);
            Var<A14> p14 = Expressions.lambdaParameter(sam.param14(), scope);
            Var<A15> p15 = Expressions.lambdaParameter(sam.param15(), scope);
            Var<A16> p16 = Expressions.lambdaParameter(sam.param16(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16), Expressions.returned(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16), sam.method()));
        });
    }

    /**
     * A lambda with an expression body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13, A14 a14, A15 a15, A16 a16) -&gt; effect`, of the functional interface whose `void` method `sam` is.
     *
     * @param sam the single abstract method of the functional interface
     * @param body gives the effect, given the parameters
     * @param <F> the functional interface
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return the lambda
     * @throws IllegalStateException if the body uses a variable a lambda cannot capture, or can throw a checked exception the method does not declare
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> Expr<F> lambda(VoidSam16<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> sam, Function16<? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, ? super Var<A14>, ? super Var<A15>, ? super Var<A16>, ? extends Effect> body) {
        return Expressions.expressionLambda(sam.owner(), sam.method(), (scope) -> {
            Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), scope);
            Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), scope);
            Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), scope);
            Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), scope);
            Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), scope);
            Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), scope);
            Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), scope);
            Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), scope);
            Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), scope);
            Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), scope);
            Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), scope);
            Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), scope);
            Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), scope);
            Var<A14> p14 = Expressions.lambdaParameter(sam.param14(), scope);
            Var<A15> p15 = Expressions.lambdaParameter(sam.param15(), scope);
            Var<A16> p16 = Expressions.lambdaParameter(sam.param16(), scope);
            return new LambdaValue(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16), Assignment.nodeOf(body.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)));
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

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2, A3, A4> Expr<F> lambdaBlock(Sam4<F, R, A1, A2, A3, A4> sam, Function5<? super Body<R>, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4), (b) -> body.apply(b, p1, p2, p3, p4));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2, A3, A4> Expr<F> lambdaBlock(VoidSam4<F, A1, A2, A3, A4> sam, Function5<? super VoidBody, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4), (b) -> body.apply(b, p1, p2, p3, p4));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2, A3, A4, A5> Expr<F> lambdaBlock(Sam5<F, R, A1, A2, A3, A4, A5> sam, Function6<? super Body<R>, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5), (b) -> body.apply(b, p1, p2, p3, p4, p5));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2, A3, A4, A5> Expr<F> lambdaBlock(VoidSam5<F, A1, A2, A3, A4, A5> sam, Function6<? super VoidBody, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5), (b) -> body.apply(b, p1, p2, p3, p4, p5));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2, A3, A4, A5, A6> Expr<F> lambdaBlock(Sam6<F, R, A1, A2, A3, A4, A5, A6> sam, Function7<? super Body<R>, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2, A3, A4, A5, A6> Expr<F> lambdaBlock(VoidSam6<F, A1, A2, A3, A4, A5, A6> sam, Function7<? super VoidBody, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7> Expr<F> lambdaBlock(Sam7<F, R, A1, A2, A3, A4, A5, A6, A7> sam, Function8<? super Body<R>, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7> Expr<F> lambdaBlock(VoidSam7<F, A1, A2, A3, A4, A5, A6, A7> sam, Function8<? super VoidBody, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8> Expr<F> lambdaBlock(Sam8<F, R, A1, A2, A3, A4, A5, A6, A7, A8> sam, Function9<? super Body<R>, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8> Expr<F> lambdaBlock(VoidSam8<F, A1, A2, A3, A4, A5, A6, A7, A8> sam, Function9<? super VoidBody, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> Expr<F> lambdaBlock(Sam9<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> sam, Function10<? super Body<R>, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9> Expr<F> lambdaBlock(VoidSam9<F, A1, A2, A3, A4, A5, A6, A7, A8, A9> sam, Function10<? super VoidBody, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
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
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Expr<F> lambdaBlock(Sam10<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> sam, Function11<? super Body<R>, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
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
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Expr<F> lambdaBlock(VoidSam10<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> sam, Function11<? super VoidBody, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
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
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Expr<F> lambdaBlock(Sam11<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> sam, Function12<? super Body<R>, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), block);
        Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
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
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Expr<F> lambdaBlock(VoidSam11<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> sam, Function12<? super VoidBody, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), block);
        Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
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
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Expr<F> lambdaBlock(Sam12<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> sam, Function13<? super Body<R>, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), block);
        Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), block);
        Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
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
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Expr<F> lambdaBlock(VoidSam12<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> sam, Function13<? super VoidBody, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), block);
        Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), block);
        Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
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
     * @param <A13> the type of the thirteenth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> Expr<F> lambdaBlock(Sam13<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> sam, Function14<? super Body<R>, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), block);
        Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), block);
        Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), block);
        Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
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
     * @param <A13> the type of the thirteenth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> Expr<F> lambdaBlock(VoidSam13<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> sam, Function14<? super VoidBody, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), block);
        Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), block);
        Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), block);
        Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13, A14 a14) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> Expr<F> lambdaBlock(Sam14<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> sam, Function15<? super Body<R>, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, ? super Var<A14>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), block);
        Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), block);
        Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), block);
        Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), block);
        Var<A14> p14 = Expressions.lambdaParameter(sam.param14(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13, A14 a14) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> Expr<F> lambdaBlock(VoidSam14<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> sam, Function15<? super VoidBody, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, ? super Var<A14>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), block);
        Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), block);
        Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), block);
        Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), block);
        Var<A14> p14 = Expressions.lambdaParameter(sam.param14(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13, A14 a14, A15 a15) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> Expr<F> lambdaBlock(Sam15<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> sam, Function16<? super Body<R>, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, ? super Var<A14>, ? super Var<A15>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), block);
        Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), block);
        Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), block);
        Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), block);
        Var<A14> p14 = Expressions.lambdaParameter(sam.param14(), block);
        Var<A15> p15 = Expressions.lambdaParameter(sam.param15(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13, A14 a14, A15 a15) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> Expr<F> lambdaBlock(VoidSam15<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> sam, Function16<? super VoidBody, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, ? super Var<A14>, ? super Var<A15>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), block);
        Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), block);
        Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), block);
        Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), block);
        Var<A14> p14 = Expressions.lambdaParameter(sam.param14(), block);
        Var<A15> p15 = Expressions.lambdaParameter(sam.param15(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13, A14 a14, A15 a15, A16 a16) -&gt; { ... }`, of the functional interface `sam` is the method of. The block ends as the body of a method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
     * @param <R> the result type of its method
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> Expr<F> lambdaBlock(Sam16<F, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> sam, Function17<? super Body<R>, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, ? super Var<A14>, ? super Var<A15>, ? super Var<A16>, Terminated<R>> body) {
        Body<R> block = Body.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), block);
        Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), block);
        Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), block);
        Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), block);
        Var<A14> p14 = Expressions.lambdaParameter(sam.param14(), block);
        Var<A15> p15 = Expressions.lambdaParameter(sam.param15(), block);
        Var<A16> p16 = Expressions.lambdaParameter(sam.param16(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16));
    }

    /**
     * A lambda with a block body, `(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5, A6 a6, A7 a7, A8 a8, A9 a9, A10 a10, A11 a11, A12 a12, A13 a13, A14 a14, A15 a15, A16 a16) -&gt; { ... }`, of the functional interface whose `void` method `sam` is. The block ends as the body of a `void` method does.
     *
     * @param sam the single abstract method of the functional interface
     * @param body builds the block, given the parameters
     * @param <F> the functional interface
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @return the lambda
     * @throws IllegalStateException if a statement of the block uses a variable a lambda cannot capture or can throw a checked exception the method does not declare, where it is built; if the block ends with the token of another block
     */
    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> Expr<F> lambdaBlock(VoidSam16<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> sam, Function17<? super VoidBody, ? super Var<A1>, ? super Var<A2>, ? super Var<A3>, ? super Var<A4>, ? super Var<A5>, ? super Var<A6>, ? super Var<A7>, ? super Var<A8>, ? super Var<A9>, ? super Var<A10>, ? super Var<A11>, ? super Var<A12>, ? super Var<A13>, ? super Var<A14>, ? super Var<A15>, ? super Var<A16>, Terminated<Void>> body) {
        VoidBody block = VoidBody.lambdaBody(Scopes.enclosing(), sam.method().traits().throwsTypes());
        Var<A1> p1 = Expressions.lambdaParameter(sam.param1(), block);
        Var<A2> p2 = Expressions.lambdaParameter(sam.param2(), block);
        Var<A3> p3 = Expressions.lambdaParameter(sam.param3(), block);
        Var<A4> p4 = Expressions.lambdaParameter(sam.param4(), block);
        Var<A5> p5 = Expressions.lambdaParameter(sam.param5(), block);
        Var<A6> p6 = Expressions.lambdaParameter(sam.param6(), block);
        Var<A7> p7 = Expressions.lambdaParameter(sam.param7(), block);
        Var<A8> p8 = Expressions.lambdaParameter(sam.param8(), block);
        Var<A9> p9 = Expressions.lambdaParameter(sam.param9(), block);
        Var<A10> p10 = Expressions.lambdaParameter(sam.param10(), block);
        Var<A11> p11 = Expressions.lambdaParameter(sam.param11(), block);
        Var<A12> p12 = Expressions.lambdaParameter(sam.param12(), block);
        Var<A13> p13 = Expressions.lambdaParameter(sam.param13(), block);
        Var<A14> p14 = Expressions.lambdaParameter(sam.param14(), block);
        Var<A15> p15 = Expressions.lambdaParameter(sam.param15(), block);
        Var<A16> p16 = Expressions.lambdaParameter(sam.param16(), block);
        return Expressions.blockLambda(sam.owner(), sam.method(), block, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16));
    }
}
