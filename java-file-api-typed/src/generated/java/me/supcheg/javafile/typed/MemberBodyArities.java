package me.supcheg.javafile.typed;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Invocable;
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
import me.supcheg.javafile.typed.MemberBodies.Slot.Awaiting;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody0;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody1;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody10;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody11;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody12;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody2;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody3;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody4;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody5;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody6;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody7;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody8;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody9;
import me.supcheg.javafile.typed.Node.Operand;
import me.supcheg.javafile.typed.Node.Passing;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class MemberBodyArities<Self, S> permits MemberBodies {
    abstract Awaiting definable(Invocable member);

    abstract This<Self> thisOf(Block<?, ?> root);

    abstract Node superCallNode(This<Self> receiver, Invocable method, List<Operand> args, Passing passing);

    abstract <R, B extends Block<R, B>> void defineIn(Awaiting slot, B root, List<Var<?>> params, Function<? super B, Terminated<R>> bodyFn);

    /**
     * Defines a declared method; the body gets `this`.
     *
     * @param method the declared method
     * @param body the body, given its block, `this`
     * @param <R> the result type
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <R> void define(MethodRef0<Self, R> method, InstanceBody0<Self, Body<R>, R> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        defineIn(slot, root, List.of(), (b) -> body.build(b, self));
    }

    /**
     * Defines a declared `void` method; the body gets `this`.
     *
     * @param method the declared method
     * @param body the body, given its block, `this`
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final void define(VoidMethodRef0<Self> method, InstanceBody0<Self, VoidBody, Void> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        defineIn(slot, root, List.of(), (b) -> body.build(b, self));
    }

    /**
     * `super.method()`: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
     * @param method the fact of the method
     * @return the call
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R> Invocation<R> superCall(This<Self> self, MethodRef0<? super S, R> method) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(), Passing.EACH), method.result());
    }

    /**
     * `super.method()` of a `void` method: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
     * @param method the fact of the method
     * @return the call
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final VoidInvocation superCall(This<Self> self, VoidMethodRef0<? super S> method) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(), Passing.EACH));
    }

    /**
     * The body of an instance method or constructor of 0 parameters.
     *
     * @param <Self> the class
     * @param <B> the body block
     * @param <R> the result type, [Void] for `void`
     */
    @FunctionalInterface
    public interface InstanceBody0<Self, B, R> {
        /**
         * Builds the body.
         *
         * @param body the body block
         * @param self `this`
         * @return the proof that the body ended
         */
        Terminated<R> build(B body, This<Self> self);
    }

    /**
     * Defines a declared method; the body gets `this` and the parameter.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <R> the result type
     * @param <A1> the type of the parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <R, A1> void define(MethodRef1<Self, R, A1> method, InstanceBody1<Self, Body<R>, R, A1> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = MemberBodies.lastParameter(slot, method.param1(), root);
        defineIn(slot, root, List.of(p1), (b) -> body.build(b, self, p1));
    }

    /**
     * Defines a declared `void` method; the body gets `this` and the parameter.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <A1> void define(VoidMethodRef1<Self, A1> method, InstanceBody1<Self, VoidBody, Void, A1> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = MemberBodies.lastParameter(slot, method.param1(), root);
        defineIn(slot, root, List.of(p1), (b) -> body.build(b, self, p1));
    }

    /**
     * `super.method(a1, )`: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
     * @param method the fact of the method
     * @param a1 the argument
     * @param <A1> the type of the parameter
     * @return the call
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1> Invocation<R> superCall(This<Self> self, MethodRef1<? super S, R, A1> method, Expr<? extends A1> a1) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(a1), Passing.EACH), method.result());
    }

    /**
     * `super.method(a1, )` of a `void` method: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
     * @param method the fact of the method
     * @param a1 the argument
     * @param <A1> the type of the parameter
     * @return the call
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1> VoidInvocation superCall(This<Self> self, VoidMethodRef1<? super S, A1> method, Expr<? extends A1> a1) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(a1), Passing.EACH));
    }

    /**
     * `super.method(...)`, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
     * @param method the fact of the method
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the parameter
     * @return the call
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1> Invocation<R> superCall(This<Self> self, MethodRef1<? super S, R, A1> method, Varargs<A1> rest) {
        return new Invocation<>(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments()), Expressions.spreading(rest)), method.result());
    }

    /**
     * `super.method(...)` of a `void` method, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
     * @param method the fact of the method
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the parameter
     * @return the call
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1> VoidInvocation superCall(This<Self> self, VoidMethodRef1<? super S, A1> method, Varargs<A1> rest) {
        return new VoidInvocation(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments()), Expressions.spreading(rest)));
    }

    /**
     * The body of an instance method or constructor of 1 parameter.
     *
     * @param <Self> the class
     * @param <B> the body block
     * @param <R> the result type, [Void] for `void`
     * @param <A1> the parameter type
     */
    @FunctionalInterface
    public interface InstanceBody1<Self, B, R, A1> {
        /**
         * Builds the body.
         *
         * @param body the body block
         * @param self `this`
         * @param p1 the parameter
         * @return the proof that the body ended
         */
        Terminated<R> build(B body, This<Self> self, Var<A1> p1);
    }

    /**
     * Defines a declared method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <R, A1, A2> void define(MethodRef2<Self, R, A1, A2> method, InstanceBody2<Self, Body<R>, R, A1, A2> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = MemberBodies.lastParameter(slot, method.param2(), root);
        defineIn(slot, root, List.of(p1, p2), (b) -> body.build(b, self, p1, p2));
    }

    /**
     * Defines a declared `void` method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <A1, A2> void define(VoidMethodRef2<Self, A1, A2> method, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = MemberBodies.lastParameter(slot, method.param2(), root);
        defineIn(slot, root, List.of(p1, p2), (b) -> body.build(b, self, p1, p2));
    }

    /**
     * `super.method(a1, a2, )`: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2> Invocation<R> superCall(This<Self> self, MethodRef2<? super S, R, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(a1, a2), Passing.EACH), method.result());
    }

    /**
     * `super.method(a1, a2, )` of a `void` method: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2> VoidInvocation superCall(This<Self> self, VoidMethodRef2<? super S, A1, A2> method, Expr<? extends A1> a1, Expr<? extends A2> a2) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(a1, a2), Passing.EACH));
    }

    /**
     * `super.method(a1, ...)`, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
     * @param method the fact of the method
     * @param a1 the argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2> Invocation<R> superCall(This<Self> self, MethodRef2<? super S, R, A1, A2> method, Expr<? extends A1> a1, Varargs<A2> rest) {
        return new Invocation<>(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1)), Expressions.spreading(rest)), method.result());
    }

    /**
     * `super.method(a1, ...)` of a `void` method, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
     * @param method the fact of the method
     * @param a1 the argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the call
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2> VoidInvocation superCall(This<Self> self, VoidMethodRef2<? super S, A1, A2> method, Expr<? extends A1> a1, Varargs<A2> rest) {
        return new VoidInvocation(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1)), Expressions.spreading(rest)));
    }

    /**
     * The body of an instance method or constructor of 2 parameters.
     *
     * @param <Self> the class
     * @param <B> the body block
     * @param <R> the result type, [Void] for `void`
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     */
    @FunctionalInterface
    public interface InstanceBody2<Self, B, R, A1, A2> {
        /**
         * Builds the body.
         *
         * @param body the body block
         * @param self `this`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @return the proof that the body ended
         */
        Terminated<R> build(B body, This<Self> self, Var<A1> p1, Var<A2> p2);
    }

    /**
     * Defines a declared method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <R, A1, A2, A3> void define(MethodRef3<Self, R, A1, A2, A3> method, InstanceBody3<Self, Body<R>, R, A1, A2, A3> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = MemberBodies.lastParameter(slot, method.param3(), root);
        defineIn(slot, root, List.of(p1, p2, p3), (b) -> body.build(b, self, p1, p2, p3));
    }

    /**
     * Defines a declared `void` method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <A1, A2, A3> void define(VoidMethodRef3<Self, A1, A2, A3> method, InstanceBody3<Self, VoidBody, Void, A1, A2, A3> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = MemberBodies.lastParameter(slot, method.param3(), root);
        defineIn(slot, root, List.of(p1, p2, p3), (b) -> body.build(b, self, p1, p2, p3));
    }

    /**
     * `super.method(a1, a2, a3, )`: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the call
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3> Invocation<R> superCall(This<Self> self, MethodRef3<? super S, R, A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(a1, a2, a3), Passing.EACH), method.result());
    }

    /**
     * `super.method(a1, a2, a3, )` of a `void` method: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the call
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3> VoidInvocation superCall(This<Self> self, VoidMethodRef3<? super S, A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(a1, a2, a3), Passing.EACH));
    }

    /**
     * `super.method(a1, a2, ...)`, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the call
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3> Invocation<R> superCall(This<Self> self, MethodRef3<? super S, R, A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Varargs<A3> rest) {
        return new Invocation<>(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2)), Expressions.spreading(rest)), method.result());
    }

    /**
     * `super.method(a1, a2, ...)` of a `void` method, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
     * @param method the fact of the method
     * @param a1 the first argument
     * @param a2 the second argument
     * @param rest the arguments of the last parameter
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the call
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3> VoidInvocation superCall(This<Self> self, VoidMethodRef3<? super S, A1, A2, A3> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Varargs<A3> rest) {
        return new VoidInvocation(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2)), Expressions.spreading(rest)));
    }

    /**
     * The body of an instance method or constructor of 3 parameters.
     *
     * @param <Self> the class
     * @param <B> the body block
     * @param <R> the result type, [Void] for `void`
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     */
    @FunctionalInterface
    public interface InstanceBody3<Self, B, R, A1, A2, A3> {
        /**
         * Builds the body.
         *
         * @param body the body block
         * @param self `this`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @return the proof that the body ended
         */
        Terminated<R> build(B body, This<Self> self, Var<A1> p1, Var<A2> p2, Var<A3> p3);
    }

    /**
     * Defines a declared method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <R, A1, A2, A3, A4> void define(MethodRef4<Self, R, A1, A2, A3, A4> method, InstanceBody4<Self, Body<R>, R, A1, A2, A3, A4> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = MemberBodies.lastParameter(slot, method.param4(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4), (b) -> body.build(b, self, p1, p2, p3, p4));
    }

    /**
     * Defines a declared `void` method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <A1, A2, A3, A4> void define(VoidMethodRef4<Self, A1, A2, A3, A4> method, InstanceBody4<Self, VoidBody, Void, A1, A2, A3, A4> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = MemberBodies.lastParameter(slot, method.param4(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4), (b) -> body.build(b, self, p1, p2, p3, p4));
    }

    /**
     * `super.method(a1, a2, a3, a4, )`: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4> Invocation<R> superCall(This<Self> self, MethodRef4<? super S, R, A1, A2, A3, A4> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4), Passing.EACH), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, )` of a `void` method: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4> VoidInvocation superCall(This<Self> self, VoidMethodRef4<? super S, A1, A2, A3, A4> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4), Passing.EACH));
    }

    /**
     * `super.method(a1, a2, a3, ...)`, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4> Invocation<R> superCall(This<Self> self, MethodRef4<? super S, R, A1, A2, A3, A4> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Varargs<A4> rest) {
        return new Invocation<>(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3)), Expressions.spreading(rest)), method.result());
    }

    /**
     * `super.method(a1, a2, a3, ...)` of a `void` method, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4> VoidInvocation superCall(This<Self> self, VoidMethodRef4<? super S, A1, A2, A3, A4> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Varargs<A4> rest) {
        return new VoidInvocation(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3)), Expressions.spreading(rest)));
    }

    /**
     * The body of an instance method or constructor of 4 parameters.
     *
     * @param <Self> the class
     * @param <B> the body block
     * @param <R> the result type, [Void] for `void`
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     * @param <A4> the fourth parameter type
     */
    @FunctionalInterface
    public interface InstanceBody4<Self, B, R, A1, A2, A3, A4> {
        /**
         * Builds the body.
         *
         * @param body the body block
         * @param self `this`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @return the proof that the body ended
         */
        Terminated<R> build(B body, This<Self> self, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4);
    }

    /**
     * Defines a declared method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <R, A1, A2, A3, A4, A5> void define(MethodRef5<Self, R, A1, A2, A3, A4, A5> method, InstanceBody5<Self, Body<R>, R, A1, A2, A3, A4, A5> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = MemberBodies.lastParameter(slot, method.param5(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5), (b) -> body.build(b, self, p1, p2, p3, p4, p5));
    }

    /**
     * Defines a declared `void` method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <A1, A2, A3, A4, A5> void define(VoidMethodRef5<Self, A1, A2, A3, A4, A5> method, InstanceBody5<Self, VoidBody, Void, A1, A2, A3, A4, A5> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = MemberBodies.lastParameter(slot, method.param5(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5), (b) -> body.build(b, self, p1, p2, p3, p4, p5));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, )`: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5> Invocation<R> superCall(This<Self> self, MethodRef5<? super S, R, A1, A2, A3, A4, A5> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5), Passing.EACH), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, )` of a `void` method: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5> VoidInvocation superCall(This<Self> self, VoidMethodRef5<? super S, A1, A2, A3, A4, A5> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5), Passing.EACH));
    }

    /**
     * `super.method(a1, a2, a3, a4, ...)`, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5> Invocation<R> superCall(This<Self> self, MethodRef5<? super S, R, A1, A2, A3, A4, A5> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Varargs<A5> rest) {
        return new Invocation<>(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4)), Expressions.spreading(rest)), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, ...)` of a `void` method, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5> VoidInvocation superCall(This<Self> self, VoidMethodRef5<? super S, A1, A2, A3, A4, A5> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Varargs<A5> rest) {
        return new VoidInvocation(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4)), Expressions.spreading(rest)));
    }

    /**
     * The body of an instance method or constructor of 5 parameters.
     *
     * @param <Self> the class
     * @param <B> the body block
     * @param <R> the result type, [Void] for `void`
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     * @param <A4> the fourth parameter type
     * @param <A5> the fifth parameter type
     */
    @FunctionalInterface
    public interface InstanceBody5<Self, B, R, A1, A2, A3, A4, A5> {
        /**
         * Builds the body.
         *
         * @param body the body block
         * @param self `this`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @param p5 the fifth parameter
         * @return the proof that the body ended
         */
        Terminated<R> build(B body, This<Self> self, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5);
    }

    /**
     * Defines a declared method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <R, A1, A2, A3, A4, A5, A6> void define(MethodRef6<Self, R, A1, A2, A3, A4, A5, A6> method, InstanceBody6<Self, Body<R>, R, A1, A2, A3, A4, A5, A6> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = MemberBodies.lastParameter(slot, method.param6(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6));
    }

    /**
     * Defines a declared `void` method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <A1, A2, A3, A4, A5, A6> void define(VoidMethodRef6<Self, A1, A2, A3, A4, A5, A6> method, InstanceBody6<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = MemberBodies.lastParameter(slot, method.param6(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, )`: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5, A6> Invocation<R> superCall(This<Self> self, MethodRef6<? super S, R, A1, A2, A3, A4, A5, A6> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5, a6), Passing.EACH), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, )` of a `void` method: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5, A6> VoidInvocation superCall(This<Self> self, VoidMethodRef6<? super S, A1, A2, A3, A4, A5, A6> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5, a6), Passing.EACH));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, ...)`, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5, A6> Invocation<R> superCall(This<Self> self, MethodRef6<? super S, R, A1, A2, A3, A4, A5, A6> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Varargs<A6> rest) {
        return new Invocation<>(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4, a5)), Expressions.spreading(rest)), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, ...)` of a `void` method, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5, A6> VoidInvocation superCall(This<Self> self, VoidMethodRef6<? super S, A1, A2, A3, A4, A5, A6> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Varargs<A6> rest) {
        return new VoidInvocation(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4, a5)), Expressions.spreading(rest)));
    }

    /**
     * The body of an instance method or constructor of 6 parameters.
     *
     * @param <Self> the class
     * @param <B> the body block
     * @param <R> the result type, [Void] for `void`
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     * @param <A4> the fourth parameter type
     * @param <A5> the fifth parameter type
     * @param <A6> the sixth parameter type
     */
    @FunctionalInterface
    public interface InstanceBody6<Self, B, R, A1, A2, A3, A4, A5, A6> {
        /**
         * Builds the body.
         *
         * @param body the body block
         * @param self `this`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @param p5 the fifth parameter
         * @param p6 the sixth parameter
         * @return the proof that the body ended
         */
        Terminated<R> build(B body, This<Self> self, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5, Var<A6> p6);
    }

    /**
     * Defines a declared method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7> void define(MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A7> method, InstanceBody7<Self, Body<R>, R, A1, A2, A3, A4, A5, A6, A7> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = MemberBodies.lastParameter(slot, method.param7(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * Defines a declared `void` method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <A1, A2, A3, A4, A5, A6, A7> void define(VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A7> method, InstanceBody7<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = MemberBodies.lastParameter(slot, method.param7(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, )`: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7> Invocation<R> superCall(This<Self> self, MethodRef7<? super S, R, A1, A2, A3, A4, A5, A6, A7> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7), Passing.EACH), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, )` of a `void` method: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5, A6, A7> VoidInvocation superCall(This<Self> self, VoidMethodRef7<? super S, A1, A2, A3, A4, A5, A6, A7> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7), Passing.EACH));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, ...)`, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7> Invocation<R> superCall(This<Self> self, MethodRef7<? super S, R, A1, A2, A3, A4, A5, A6, A7> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Varargs<A7> rest) {
        return new Invocation<>(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6)), Expressions.spreading(rest)), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, ...)` of a `void` method, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5, A6, A7> VoidInvocation superCall(This<Self> self, VoidMethodRef7<? super S, A1, A2, A3, A4, A5, A6, A7> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Varargs<A7> rest) {
        return new VoidInvocation(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6)), Expressions.spreading(rest)));
    }

    /**
     * The body of an instance method or constructor of 7 parameters.
     *
     * @param <Self> the class
     * @param <B> the body block
     * @param <R> the result type, [Void] for `void`
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     * @param <A4> the fourth parameter type
     * @param <A5> the fifth parameter type
     * @param <A6> the sixth parameter type
     * @param <A7> the seventh parameter type
     */
    @FunctionalInterface
    public interface InstanceBody7<Self, B, R, A1, A2, A3, A4, A5, A6, A7> {
        /**
         * Builds the body.
         *
         * @param body the body block
         * @param self `this`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @param p5 the fifth parameter
         * @param p6 the sixth parameter
         * @param p7 the seventh parameter
         * @return the proof that the body ended
         */
        Terminated<R> build(B body, This<Self> self, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5, Var<A6> p6, Var<A7> p7);
    }

    /**
     * Defines a declared method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8> void define(MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> method, InstanceBody8<Self, Body<R>, R, A1, A2, A3, A4, A5, A6, A7, A8> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = MemberBodies.lastParameter(slot, method.param8(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * Defines a declared `void` method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8> void define(VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> method, InstanceBody8<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = MemberBodies.lastParameter(slot, method.param8(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, )`: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8> Invocation<R> superCall(This<Self> self, MethodRef8<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8), Passing.EACH), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, )` of a `void` method: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8> VoidInvocation superCall(This<Self> self, VoidMethodRef8<? super S, A1, A2, A3, A4, A5, A6, A7, A8> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8), Passing.EACH));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, ...)`, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8> Invocation<R> superCall(This<Self> self, MethodRef8<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Varargs<A8> rest) {
        return new Invocation<>(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7)), Expressions.spreading(rest)), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, ...)` of a `void` method, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8> VoidInvocation superCall(This<Self> self, VoidMethodRef8<? super S, A1, A2, A3, A4, A5, A6, A7, A8> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Varargs<A8> rest) {
        return new VoidInvocation(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7)), Expressions.spreading(rest)));
    }

    /**
     * The body of an instance method or constructor of 8 parameters.
     *
     * @param <Self> the class
     * @param <B> the body block
     * @param <R> the result type, [Void] for `void`
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     * @param <A4> the fourth parameter type
     * @param <A5> the fifth parameter type
     * @param <A6> the sixth parameter type
     * @param <A7> the seventh parameter type
     * @param <A8> the eighth parameter type
     */
    @FunctionalInterface
    public interface InstanceBody8<Self, B, R, A1, A2, A3, A4, A5, A6, A7, A8> {
        /**
         * Builds the body.
         *
         * @param body the body block
         * @param self `this`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @param p5 the fifth parameter
         * @param p6 the sixth parameter
         * @param p7 the seventh parameter
         * @param p8 the eighth parameter
         * @return the proof that the body ended
         */
        Terminated<R> build(B body, This<Self> self, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5, Var<A6> p6, Var<A7> p7, Var<A8> p8);
    }

    /**
     * Defines a declared method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> void define(MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, InstanceBody9<Self, Body<R>, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = MemberBodies.lastParameter(slot, method.param9(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * Defines a declared `void` method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9> void define(VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, InstanceBody9<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = MemberBodies.lastParameter(slot, method.param9(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, a9, )`: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> Invocation<R> superCall(This<Self> self, MethodRef9<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9), Passing.EACH), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, a9, )` of a `void` method: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidInvocation superCall(This<Self> self, VoidMethodRef9<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9), Passing.EACH));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, ...)`, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> Invocation<R> superCall(This<Self> self, MethodRef9<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Varargs<A9> rest) {
        return new Invocation<>(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8)), Expressions.spreading(rest)), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, ...)` of a `void` method, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidInvocation superCall(This<Self> self, VoidMethodRef9<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Varargs<A9> rest) {
        return new VoidInvocation(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8)), Expressions.spreading(rest)));
    }

    /**
     * The body of an instance method or constructor of 9 parameters.
     *
     * @param <Self> the class
     * @param <B> the body block
     * @param <R> the result type, [Void] for `void`
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     * @param <A4> the fourth parameter type
     * @param <A5> the fifth parameter type
     * @param <A6> the sixth parameter type
     * @param <A7> the seventh parameter type
     * @param <A8> the eighth parameter type
     * @param <A9> the ninth parameter type
     */
    @FunctionalInterface
    public interface InstanceBody9<Self, B, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> {
        /**
         * Builds the body.
         *
         * @param body the body block
         * @param self `this`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @param p5 the fifth parameter
         * @param p6 the sixth parameter
         * @param p7 the seventh parameter
         * @param p8 the eighth parameter
         * @param p9 the ninth parameter
         * @return the proof that the body ended
         */
        Terminated<R> build(B body, This<Self> self, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5, Var<A6> p6, Var<A7> p7, Var<A8> p8, Var<A9> p9);
    }

    /**
     * Defines a declared method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <R> the result type
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
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> void define(MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, InstanceBody10<Self, Body<R>, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = Var.param(method.param9(), root);
        Var<A10> p10 = MemberBodies.lastParameter(slot, method.param10(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    /**
     * Defines a declared `void` method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
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
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> void define(VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, InstanceBody10<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = Var.param(method.param9(), root);
        Var<A10> p10 = MemberBodies.lastParameter(slot, method.param10(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, )`: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Invocation<R> superCall(This<Self> self, MethodRef10<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10), Passing.EACH), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, )` of a `void` method: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidInvocation superCall(This<Self> self, VoidMethodRef10<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10), Passing.EACH));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, a9, ...)`, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Invocation<R> superCall(This<Self> self, MethodRef10<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Varargs<A10> rest) {
        return new Invocation<>(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9)), Expressions.spreading(rest)), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, a9, ...)` of a `void` method, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidInvocation superCall(This<Self> self, VoidMethodRef10<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Varargs<A10> rest) {
        return new VoidInvocation(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9)), Expressions.spreading(rest)));
    }

    /**
     * The body of an instance method or constructor of 10 parameters.
     *
     * @param <Self> the class
     * @param <B> the body block
     * @param <R> the result type, [Void] for `void`
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     * @param <A4> the fourth parameter type
     * @param <A5> the fifth parameter type
     * @param <A6> the sixth parameter type
     * @param <A7> the seventh parameter type
     * @param <A8> the eighth parameter type
     * @param <A9> the ninth parameter type
     * @param <A10> the tenth parameter type
     */
    @FunctionalInterface
    public interface InstanceBody10<Self, B, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> {
        /**
         * Builds the body.
         *
         * @param body the body block
         * @param self `this`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @param p5 the fifth parameter
         * @param p6 the sixth parameter
         * @param p7 the seventh parameter
         * @param p8 the eighth parameter
         * @param p9 the ninth parameter
         * @param p10 the tenth parameter
         * @return the proof that the body ended
         */
        Terminated<R> build(B body, This<Self> self, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5, Var<A6> p6, Var<A7> p7, Var<A8> p8, Var<A9> p9, Var<A10> p10);
    }

    /**
     * Defines a declared method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <R> the result type
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
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> void define(MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, InstanceBody11<Self, Body<R>, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = Var.param(method.param9(), root);
        Var<A10> p10 = Var.param(method.param10(), root);
        Var<A11> p11 = MemberBodies.lastParameter(slot, method.param11(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
    }

    /**
     * Defines a declared `void` method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
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
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> void define(VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, InstanceBody11<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = Var.param(method.param9(), root);
        Var<A10> p10 = Var.param(method.param10(), root);
        Var<A11> p11 = MemberBodies.lastParameter(slot, method.param11(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, )`: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Invocation<R> superCall(This<Self> self, MethodRef11<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11), Passing.EACH), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, )` of a `void` method: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidInvocation superCall(This<Self> self, VoidMethodRef11<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11), Passing.EACH));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, ...)`, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Invocation<R> superCall(This<Self> self, MethodRef11<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Varargs<A11> rest) {
        return new Invocation<>(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)), Expressions.spreading(rest)), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, ...)` of a `void` method, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidInvocation superCall(This<Self> self, VoidMethodRef11<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Varargs<A11> rest) {
        return new VoidInvocation(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)), Expressions.spreading(rest)));
    }

    /**
     * The body of an instance method or constructor of 11 parameters.
     *
     * @param <Self> the class
     * @param <B> the body block
     * @param <R> the result type, [Void] for `void`
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     * @param <A4> the fourth parameter type
     * @param <A5> the fifth parameter type
     * @param <A6> the sixth parameter type
     * @param <A7> the seventh parameter type
     * @param <A8> the eighth parameter type
     * @param <A9> the ninth parameter type
     * @param <A10> the tenth parameter type
     * @param <A11> the eleventh parameter type
     */
    @FunctionalInterface
    public interface InstanceBody11<Self, B, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> {
        /**
         * Builds the body.
         *
         * @param body the body block
         * @param self `this`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @param p5 the fifth parameter
         * @param p6 the sixth parameter
         * @param p7 the seventh parameter
         * @param p8 the eighth parameter
         * @param p9 the ninth parameter
         * @param p10 the tenth parameter
         * @param p11 the eleventh parameter
         * @return the proof that the body ended
         */
        Terminated<R> build(B body, This<Self> self, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5, Var<A6> p6, Var<A7> p7, Var<A8> p8, Var<A9> p9, Var<A10> p10, Var<A11> p11);
    }

    /**
     * Defines a declared method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param <R> the result type
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
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> void define(MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, InstanceBody12<Self, Body<R>, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = Var.param(method.param9(), root);
        Var<A10> p10 = Var.param(method.param10(), root);
        Var<A11> p11 = Var.param(method.param11(), root);
        Var<A12> p12 = MemberBodies.lastParameter(slot, method.param12(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    }

    /**
     * Defines a declared `void` method; the body gets `this` and the parameters.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
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
     * @throws IllegalStateException if the method is defined already or was not declared by this class; if the body reaches a `protected` member where it cannot be reached (JLS 6.6.2), names a type parameter of the class out of its scope, or a type the file of the class cannot write
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> void define(VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, InstanceBody12<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = Var.param(method.param9(), root);
        Var<A10> p10 = Var.param(method.param10(), root);
        Var<A11> p11 = Var.param(method.param11(), root);
        Var<A12> p12 = MemberBodies.lastParameter(slot, method.param12(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, )`: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Invocation<R> superCall(This<Self> self, MethodRef12<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12) {
        return new Invocation<>(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12), Passing.EACH), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, )` of a `void` method: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidInvocation superCall(This<Self> self, VoidMethodRef12<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12) {
        return new VoidInvocation(superCallNode(self, method, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12), Passing.EACH));
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, ...)`, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     * @param <R> the result type
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Invocation<R> superCall(This<Self> self, MethodRef12<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Varargs<A12> rest) {
        return new Invocation<>(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)), Expressions.spreading(rest)), method.result());
    }

    /**
     * `super.method(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, ...)` of a `void` method, the arguments of the last parameter, which is of variable arity, one by one: the method as the superclass has it, whatever the class being declared overrides it with.
     *
     * @param self `this`
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
     * @throws IllegalArgumentException if `self` is not `this` of this class, or the superclass has no such method with a body on the target classpath
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidInvocation superCall(This<Self> self, VoidMethodRef12<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Varargs<A12> rest) {
        return new VoidInvocation(superCallNode(self, method, Expressions.spread(method, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)), Expressions.spreading(rest)));
    }

    /**
     * The body of an instance method or constructor of 12 parameters.
     *
     * @param <Self> the class
     * @param <B> the body block
     * @param <R> the result type, [Void] for `void`
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     * @param <A4> the fourth parameter type
     * @param <A5> the fifth parameter type
     * @param <A6> the sixth parameter type
     * @param <A7> the seventh parameter type
     * @param <A8> the eighth parameter type
     * @param <A9> the ninth parameter type
     * @param <A10> the tenth parameter type
     * @param <A11> the eleventh parameter type
     * @param <A12> the twelfth parameter type
     */
    @FunctionalInterface
    public interface InstanceBody12<Self, B, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> {
        /**
         * Builds the body.
         *
         * @param body the body block
         * @param self `this`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @param p5 the fifth parameter
         * @param p6 the sixth parameter
         * @param p7 the seventh parameter
         * @param p8 the eighth parameter
         * @param p9 the ninth parameter
         * @param p10 the tenth parameter
         * @param p11 the eleventh parameter
         * @param p12 the twelfth parameter
         * @return the proof that the body ended
         */
        Terminated<R> build(B body, This<Self> self, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5, Var<A6> p6, Var<A7> p7, Var<A8> p8, Var<A9> p9, Var<A10> p10, Var<A11> p11, Var<A12> p12);
    }

    /**
     * Defines a declared static method; the body gets no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block
     * @param <R> the result type
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <R> void define(StaticMethodRef0<R> method, Function<Body<R>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        defineIn(slot, root, List.of(), (b) -> body.apply(b));
    }

    /**
     * Defines a declared static `void` method; the body gets no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block
     * @throws IllegalStateException as the definition of an instance method
     */
    public final void define(VoidStaticMethodRef0 method, Function<VoidBody, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        defineIn(slot, root, List.of(), (b) -> body.apply(b));
    }

    /**
     * Defines a declared static method; the body gets the parameter, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <R> the result type
     * @param <A1> the type of the parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <R, A1> void define(StaticMethodRef1<R, A1> method, BiFunction<Body<R>, Var<A1>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = MemberBodies.lastParameter(slot, method.param1(), root);
        defineIn(slot, root, List.of(p1), (b) -> body.apply(b, p1));
    }

    /**
     * Defines a declared static `void` method; the body gets the parameter, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <A1> the type of the parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <A1> void define(VoidStaticMethodRef1<A1> method, BiFunction<VoidBody, Var<A1>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = MemberBodies.lastParameter(slot, method.param1(), root);
        defineIn(slot, root, List.of(p1), (b) -> body.apply(b, p1));
    }

    /**
     * Defines a declared static method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <R, A1, A2> void define(StaticMethodRef2<R, A1, A2> method, Function3<Body<R>, Var<A1>, Var<A2>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = MemberBodies.lastParameter(slot, method.param2(), root);
        defineIn(slot, root, List.of(p1, p2), (b) -> body.apply(b, p1, p2));
    }

    /**
     * Defines a declared static `void` method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <A1, A2> void define(VoidStaticMethodRef2<A1, A2> method, Function3<VoidBody, Var<A1>, Var<A2>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = MemberBodies.lastParameter(slot, method.param2(), root);
        defineIn(slot, root, List.of(p1, p2), (b) -> body.apply(b, p1, p2));
    }

    /**
     * Defines a declared static method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <R, A1, A2, A3> void define(StaticMethodRef3<R, A1, A2, A3> method, Function4<Body<R>, Var<A1>, Var<A2>, Var<A3>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = MemberBodies.lastParameter(slot, method.param3(), root);
        defineIn(slot, root, List.of(p1, p2, p3), (b) -> body.apply(b, p1, p2, p3));
    }

    /**
     * Defines a declared static `void` method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <A1, A2, A3> void define(VoidStaticMethodRef3<A1, A2, A3> method, Function4<VoidBody, Var<A1>, Var<A2>, Var<A3>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = MemberBodies.lastParameter(slot, method.param3(), root);
        defineIn(slot, root, List.of(p1, p2, p3), (b) -> body.apply(b, p1, p2, p3));
    }

    /**
     * Defines a declared static method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <R, A1, A2, A3, A4> void define(StaticMethodRef4<R, A1, A2, A3, A4> method, Function5<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = MemberBodies.lastParameter(slot, method.param4(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4), (b) -> body.apply(b, p1, p2, p3, p4));
    }

    /**
     * Defines a declared static `void` method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <A1, A2, A3, A4> void define(VoidStaticMethodRef4<A1, A2, A3, A4> method, Function5<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = MemberBodies.lastParameter(slot, method.param4(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4), (b) -> body.apply(b, p1, p2, p3, p4));
    }

    /**
     * Defines a declared static method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <R, A1, A2, A3, A4, A5> void define(StaticMethodRef5<R, A1, A2, A3, A4, A5> method, Function6<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = MemberBodies.lastParameter(slot, method.param5(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5), (b) -> body.apply(b, p1, p2, p3, p4, p5));
    }

    /**
     * Defines a declared static `void` method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <A1, A2, A3, A4, A5> void define(VoidStaticMethodRef5<A1, A2, A3, A4, A5> method, Function6<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = MemberBodies.lastParameter(slot, method.param5(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5), (b) -> body.apply(b, p1, p2, p3, p4, p5));
    }

    /**
     * Defines a declared static method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <R, A1, A2, A3, A4, A5, A6> void define(StaticMethodRef6<R, A1, A2, A3, A4, A5, A6> method, Function7<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = MemberBodies.lastParameter(slot, method.param6(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6));
    }

    /**
     * Defines a declared static `void` method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <A1, A2, A3, A4, A5, A6> void define(VoidStaticMethodRef6<A1, A2, A3, A4, A5, A6> method, Function7<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = MemberBodies.lastParameter(slot, method.param6(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6));
    }

    /**
     * Defines a declared static method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7> void define(StaticMethodRef7<R, A1, A2, A3, A4, A5, A6, A7> method, Function8<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = MemberBodies.lastParameter(slot, method.param7(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * Defines a declared static `void` method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <A1, A2, A3, A4, A5, A6, A7> void define(VoidStaticMethodRef7<A1, A2, A3, A4, A5, A6, A7> method, Function8<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = MemberBodies.lastParameter(slot, method.param7(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * Defines a declared static method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8> void define(StaticMethodRef8<R, A1, A2, A3, A4, A5, A6, A7, A8> method, Function9<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = MemberBodies.lastParameter(slot, method.param8(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * Defines a declared static `void` method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8> void define(VoidStaticMethodRef8<A1, A2, A3, A4, A5, A6, A7, A8> method, Function9<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = MemberBodies.lastParameter(slot, method.param8(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * Defines a declared static method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> void define(StaticMethodRef9<R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Function10<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = MemberBodies.lastParameter(slot, method.param9(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * Defines a declared static `void` method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9> void define(VoidStaticMethodRef9<A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Function10<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = MemberBodies.lastParameter(slot, method.param9(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * Defines a declared static method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <R> the result type
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
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> void define(StaticMethodRef10<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Function11<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Var<A10>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = Var.param(method.param9(), root);
        Var<A10> p10 = MemberBodies.lastParameter(slot, method.param10(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    /**
     * Defines a declared static `void` method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
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
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> void define(VoidStaticMethodRef10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Function11<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Var<A10>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = Var.param(method.param9(), root);
        Var<A10> p10 = MemberBodies.lastParameter(slot, method.param10(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    /**
     * Defines a declared static method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <R> the result type
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
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> void define(StaticMethodRef11<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Function12<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Var<A10>, Var<A11>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = Var.param(method.param9(), root);
        Var<A10> p10 = Var.param(method.param10(), root);
        Var<A11> p11 = MemberBodies.lastParameter(slot, method.param11(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
    }

    /**
     * Defines a declared static `void` method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
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
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> void define(VoidStaticMethodRef11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Function12<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Var<A10>, Var<A11>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = Var.param(method.param9(), root);
        Var<A10> p10 = Var.param(method.param10(), root);
        Var<A11> p11 = MemberBodies.lastParameter(slot, method.param11(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
    }

    /**
     * Defines a declared static method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param <R> the result type
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
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> void define(StaticMethodRef12<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Function13<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Var<A10>, Var<A11>, Var<A12>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = Var.param(method.param9(), root);
        Var<A10> p10 = Var.param(method.param10(), root);
        Var<A11> p11 = Var.param(method.param11(), root);
        Var<A12> p12 = MemberBodies.lastParameter(slot, method.param12(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    }

    /**
     * Defines a declared static `void` method; the body gets the parameters, no `this`.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
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
     * @throws IllegalStateException as the definition of an instance method
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> void define(VoidStaticMethodRef12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Function13<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Var<A10>, Var<A11>, Var<A12>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of static method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = Var.param(method.param1(), root);
        Var<A2> p2 = Var.param(method.param2(), root);
        Var<A3> p3 = Var.param(method.param3(), root);
        Var<A4> p4 = Var.param(method.param4(), root);
        Var<A5> p5 = Var.param(method.param5(), root);
        Var<A6> p6 = Var.param(method.param6(), root);
        Var<A7> p7 = Var.param(method.param7(), root);
        Var<A8> p8 = Var.param(method.param8(), root);
        Var<A9> p9 = Var.param(method.param9(), root);
        Var<A10> p10 = Var.param(method.param10(), root);
        Var<A11> p11 = Var.param(method.param11(), root);
        Var<A12> p12 = MemberBodies.lastParameter(slot, method.param12(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    }
}
