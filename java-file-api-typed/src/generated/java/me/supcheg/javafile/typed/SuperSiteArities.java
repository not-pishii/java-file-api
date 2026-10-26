package me.supcheg.javafile.typed;

import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.SuperCtorRef0;
import me.supcheg.javafile.facts.SuperCtorRef1;
import me.supcheg.javafile.facts.SuperCtorRef2;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody0;
import me.supcheg.javafile.typed.Node.Operand;
import me.supcheg.javafile.typed.Node.Passing;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class SuperSiteArities<Self, S> permits SuperSite {
    abstract Terminated<Void> call(Invocable ctor, List<Operand> args, Passing passing, InstanceBody0<Self, VoidBody, Void> rest);

    /**
     * `super();` by a constructor a subclass alone calls, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param rest what follows the call, given its block and `this`
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public Terminated<Void> super_(SuperCtorRef0<S> ctor, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(), Passing.EACH, rest);
    }

    /**
     * `super(a1, );` by a constructor a subclass alone calls, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1> Terminated<Void> super_(SuperCtorRef1<S, A1> ctor, Expr<? extends A1> a1, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1), Passing.EACH, rest);
    }

    /**
     * `super(...);` by a constructor a subclass alone calls, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1> Terminated<Void> super_(SuperCtorRef1<S, A1> ctor, Varargs<A1> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments()), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, );` by a constructor a subclass alone calls, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2> Terminated<Void> super_(SuperCtorRef2<S, A1, A2> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2), Passing.EACH, rest);
    }

    /**
     * `super(a1, ...);` by a constructor a subclass alone calls, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2> Terminated<Void> super_(SuperCtorRef2<S, A1, A2> ctor, Expr<? extends A1> a1, Varargs<A2> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1)), Expressions.spreading(rest), body);
    }

    /**
     * `super();` by a `public` constructor of a superclass that is not abstract, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param rest what follows the call, given its block and `this`
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public Terminated<Void> super_(CtorRef0<S> ctor, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(), Passing.EACH, rest);
    }

    /**
     * `super(a1, );` by a `public` constructor of a superclass that is not abstract, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1> Terminated<Void> super_(CtorRef1<S, A1> ctor, Expr<? extends A1> a1, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1), Passing.EACH, rest);
    }

    /**
     * `super(...);` by a `public` constructor of a superclass that is not abstract, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1> Terminated<Void> super_(CtorRef1<S, A1> ctor, Varargs<A1> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments()), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, );` by a `public` constructor of a superclass that is not abstract, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2> Terminated<Void> super_(CtorRef2<S, A1, A2> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2), Passing.EACH, rest);
    }

    /**
     * `super(a1, ...);` by a `public` constructor of a superclass that is not abstract, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2> Terminated<Void> super_(CtorRef2<S, A1, A2> ctor, Expr<? extends A1> a1, Varargs<A2> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1)), Expressions.spreading(rest), body);
    }
}
