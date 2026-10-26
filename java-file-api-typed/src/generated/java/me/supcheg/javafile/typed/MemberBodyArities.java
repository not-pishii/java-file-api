package me.supcheg.javafile.typed;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.typed.MemberBodies.Slot.Awaiting;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody0;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody1;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody2;
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
}
