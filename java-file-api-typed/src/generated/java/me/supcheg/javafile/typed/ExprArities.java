package me.supcheg.javafile.typed;

import javax.annotation.processing.Generated;
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
import me.supcheg.javafile.typed.Node.Call;
import me.supcheg.javafile.typed.Node.Passing;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class ExprArities<T> permits Expr {
    abstract Expr<T> self();

    /**
     * Calls an instance method of 0 parameters.
     *
     * @param method the fact of the method
     * @return the call
     */
    public <R> Invocation<R> call(MethodRef0<? super T, R> method) {
        return new Invocation<>(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 1 parameter.
     *
     * @param method the fact of the method
     * @param a1 the argument
     * @param <A1> the type of the parameter
     * @return the call
     */
    public <R, A1> Invocation<R> call(MethodRef1<? super T, R, A1> method, Expr<? extends A1> a1) {
        return new Invocation<>(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 1 parameter, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the parameter
     * @return the call
     */
    public <R, A1> Invocation<R> call(MethodRef1<? super T, R, A1> method, Varargs<A1> rest) {
        return new Invocation<>(Expressions.callNode(self(), method, rest, Expressions.arguments()), method.result());
    }

    /**
     * Calls an instance method of 2 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     */
    public <R, A1, A2> Invocation<R> call(MethodRef2<? super T, R, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new Invocation<>(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 2 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     */
    public <R, A1, A2> Invocation<R> call(MethodRef2<? super T, R, A1, A2> method, Expr<? extends A1> a1, Varargs<A2> rest) {
        return new Invocation<>(Expressions.callNode(self(), method, rest, Expressions.arguments(a1)), method.result());
    }

    /**
     * Calls an instance method of 3 parameters.
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
    public <R, A1, A2, A3> Invocation<R> call(MethodRef3<? super T, R, A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3) {
        return new Invocation<>(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 3 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <R, A1, A2, A3> Invocation<R> call(MethodRef3<? super T, R, A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Varargs<A3> rest) {
        return new Invocation<>(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2)), method.result());
    }

    /**
     * Calls an instance method of 4 parameters.
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
    public <R, A1, A2, A3, A4> Invocation<R> call(MethodRef4<? super T, R, A1, A2, A3, A4> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4) {
        return new Invocation<>(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 4 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <R, A1, A2, A3, A4> Invocation<R> call(MethodRef4<? super T, R, A1, A2, A3, A4> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Varargs<A4> rest) {
        return new Invocation<>(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3)), method.result());
    }

    /**
     * Calls an instance method of 5 parameters.
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
    public <R, A1, A2, A3, A4, A5> Invocation<R> call(MethodRef5<? super T, R, A1, A2, A3, A4, A5> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5) {
        return new Invocation<>(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 5 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <R, A1, A2, A3, A4, A5> Invocation<R> call(MethodRef5<? super T, R, A1, A2, A3, A4, A5> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Varargs<A5> rest) {
        return new Invocation<>(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4)), method.result());
    }

    /**
     * Calls an instance method of 6 parameters.
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
    public <R, A1, A2, A3, A4, A5, A6> Invocation<R> call(MethodRef6<? super T, R, A1, A2, A3, A4, A5, A6> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6) {
        return new Invocation<>(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5, a6), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 6 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <R, A1, A2, A3, A4, A5, A6> Invocation<R> call(MethodRef6<? super T, R, A1, A2, A3, A4, A5, A6> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Varargs<A6> rest) {
        return new Invocation<>(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4, a5)), method.result());
    }

    /**
     * Calls an instance method of 7 parameters.
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
    public <R, A1, A2, A3, A4, A5, A6, A7> Invocation<R> call(MethodRef7<? super T, R, A1, A2, A3, A4, A5, A6, A7> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7) {
        return new Invocation<>(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 7 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <R, A1, A2, A3, A4, A5, A6, A7> Invocation<R> call(MethodRef7<? super T, R, A1, A2, A3, A4, A5, A6, A7> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Varargs<A7> rest) {
        return new Invocation<>(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6)), method.result());
    }

    /**
     * Calls an instance method of 8 parameters.
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
    public <R, A1, A2, A3, A4, A5, A6, A7, A8> Invocation<R> call(MethodRef8<? super T, R, A1, A2, A3, A4, A5, A6, A7, A8> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8) {
        return new Invocation<>(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 8 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <R, A1, A2, A3, A4, A5, A6, A7, A8> Invocation<R> call(MethodRef8<? super T, R, A1, A2, A3, A4, A5, A6, A7, A8> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Varargs<A8> rest) {
        return new Invocation<>(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7)), method.result());
    }

    /**
     * Calls an instance method of 9 parameters.
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
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> Invocation<R> call(MethodRef9<? super T, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9) {
        return new Invocation<>(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 9 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> Invocation<R> call(MethodRef9<? super T, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Varargs<A9> rest) {
        return new Invocation<>(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8)), method.result());
    }

    /**
     * Calls an instance method of 10 parameters.
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
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Invocation<R> call(MethodRef10<? super T, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10) {
        return new Invocation<>(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 10 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Invocation<R> call(MethodRef10<? super T, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Varargs<A10> rest) {
        return new Invocation<>(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9)), method.result());
    }

    /**
     * Calls an instance method of 11 parameters.
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
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Invocation<R> call(MethodRef11<? super T, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11) {
        return new Invocation<>(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 11 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Invocation<R> call(MethodRef11<? super T, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Varargs<A11> rest) {
        return new Invocation<>(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)), method.result());
    }

    /**
     * Calls an instance method of 12 parameters.
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
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Invocation<R> call(MethodRef12<? super T, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12) {
        return new Invocation<>(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12), Passing.EACH), method.result());
    }

    /**
     * Calls an instance method of 12 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Invocation<R> call(MethodRef12<? super T, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Varargs<A12> rest) {
        return new Invocation<>(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)), method.result());
    }

    /**
     * Calls an instance `void` method of 0 parameters.
     *
     * @param method the fact of the method
     * @return the call
     */
    public VoidInvocation voidCall(VoidMethodRef0<? super T> method) {
        return new VoidInvocation(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 1 parameter.
     *
     * @param method the fact of the method
     * @param a1 the argument
     * @param <A1> the type of the parameter
     * @return the call
     */
    public <A1> VoidInvocation voidCall(VoidMethodRef1<? super T, A1> method, Expr<? extends A1> a1) {
        return new VoidInvocation(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 1 parameter, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the parameter
     * @return the call
     */
    public <A1> VoidInvocation voidCall(VoidMethodRef1<? super T, A1> method, Varargs<A1> rest) {
        return new VoidInvocation(Expressions.callNode(self(), method, rest, Expressions.arguments()));
    }

    /**
     * Calls an instance `void` method of 2 parameters.
     *
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     */
    public <A1, A2> VoidInvocation voidCall(VoidMethodRef2<? super T, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new VoidInvocation(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 2 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
     *
     * @param method the fact of the method
     * @param a1 the argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     */
    public <A1, A2> VoidInvocation voidCall(VoidMethodRef2<? super T, A1, A2> method, Expr<? extends A1> a1, Varargs<A2> rest) {
        return new VoidInvocation(Expressions.callNode(self(), method, rest, Expressions.arguments(a1)));
    }

    /**
     * Calls an instance `void` method of 3 parameters.
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
    public <A1, A2, A3> VoidInvocation voidCall(VoidMethodRef3<? super T, A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3) {
        return new VoidInvocation(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 3 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <A1, A2, A3> VoidInvocation voidCall(VoidMethodRef3<? super T, A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Varargs<A3> rest) {
        return new VoidInvocation(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2)));
    }

    /**
     * Calls an instance `void` method of 4 parameters.
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
    public <A1, A2, A3, A4> VoidInvocation voidCall(VoidMethodRef4<? super T, A1, A2, A3, A4> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4) {
        return new VoidInvocation(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 4 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <A1, A2, A3, A4> VoidInvocation voidCall(VoidMethodRef4<? super T, A1, A2, A3, A4> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Varargs<A4> rest) {
        return new VoidInvocation(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3)));
    }

    /**
     * Calls an instance `void` method of 5 parameters.
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
    public <A1, A2, A3, A4, A5> VoidInvocation voidCall(VoidMethodRef5<? super T, A1, A2, A3, A4, A5> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5) {
        return new VoidInvocation(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 5 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <A1, A2, A3, A4, A5> VoidInvocation voidCall(VoidMethodRef5<? super T, A1, A2, A3, A4, A5> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Varargs<A5> rest) {
        return new VoidInvocation(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4)));
    }

    /**
     * Calls an instance `void` method of 6 parameters.
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
    public <A1, A2, A3, A4, A5, A6> VoidInvocation voidCall(VoidMethodRef6<? super T, A1, A2, A3, A4, A5, A6> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6) {
        return new VoidInvocation(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5, a6), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 6 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <A1, A2, A3, A4, A5, A6> VoidInvocation voidCall(VoidMethodRef6<? super T, A1, A2, A3, A4, A5, A6> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Varargs<A6> rest) {
        return new VoidInvocation(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4, a5)));
    }

    /**
     * Calls an instance `void` method of 7 parameters.
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
    public <A1, A2, A3, A4, A5, A6, A7> VoidInvocation voidCall(VoidMethodRef7<? super T, A1, A2, A3, A4, A5, A6, A7> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7) {
        return new VoidInvocation(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 7 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <A1, A2, A3, A4, A5, A6, A7> VoidInvocation voidCall(VoidMethodRef7<? super T, A1, A2, A3, A4, A5, A6, A7> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Varargs<A7> rest) {
        return new VoidInvocation(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6)));
    }

    /**
     * Calls an instance `void` method of 8 parameters.
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
    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidInvocation voidCall(VoidMethodRef8<? super T, A1, A2, A3, A4, A5, A6, A7, A8> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8) {
        return new VoidInvocation(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 8 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidInvocation voidCall(VoidMethodRef8<? super T, A1, A2, A3, A4, A5, A6, A7, A8> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Varargs<A8> rest) {
        return new VoidInvocation(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7)));
    }

    /**
     * Calls an instance `void` method of 9 parameters.
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
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidInvocation voidCall(VoidMethodRef9<? super T, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9) {
        return new VoidInvocation(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 9 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidInvocation voidCall(VoidMethodRef9<? super T, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Varargs<A9> rest) {
        return new VoidInvocation(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8)));
    }

    /**
     * Calls an instance `void` method of 10 parameters.
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
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidInvocation voidCall(VoidMethodRef10<? super T, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10) {
        return new VoidInvocation(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 10 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidInvocation voidCall(VoidMethodRef10<? super T, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Varargs<A10> rest) {
        return new VoidInvocation(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9)));
    }

    /**
     * Calls an instance `void` method of 11 parameters.
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
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidInvocation voidCall(VoidMethodRef11<? super T, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11) {
        return new VoidInvocation(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 11 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidInvocation voidCall(VoidMethodRef11<? super T, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Varargs<A11> rest) {
        return new VoidInvocation(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)));
    }

    /**
     * Calls an instance `void` method of 12 parameters.
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
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidInvocation voidCall(VoidMethodRef12<? super T, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12) {
        return new VoidInvocation(new Call(Expressions.receiver(self(), method), method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12), Passing.EACH));
    }

    /**
     * Calls an instance `void` method of 12 parameters, the arguments of its last parameter, which is of variable arity, one by one (see [#varargs]).
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
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidInvocation voidCall(VoidMethodRef12<? super T, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Varargs<A12> rest) {
        return new VoidInvocation(Expressions.callNode(self(), method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)));
    }
}
