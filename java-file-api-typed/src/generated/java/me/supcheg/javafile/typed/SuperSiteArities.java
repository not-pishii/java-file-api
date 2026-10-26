package me.supcheg.javafile.typed;

import java.util.List;
import javax.annotation.processing.Generated;
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
import me.supcheg.javafile.facts.SuperCtorRef0;
import me.supcheg.javafile.facts.SuperCtorRef1;
import me.supcheg.javafile.facts.SuperCtorRef10;
import me.supcheg.javafile.facts.SuperCtorRef11;
import me.supcheg.javafile.facts.SuperCtorRef12;
import me.supcheg.javafile.facts.SuperCtorRef2;
import me.supcheg.javafile.facts.SuperCtorRef3;
import me.supcheg.javafile.facts.SuperCtorRef4;
import me.supcheg.javafile.facts.SuperCtorRef5;
import me.supcheg.javafile.facts.SuperCtorRef6;
import me.supcheg.javafile.facts.SuperCtorRef7;
import me.supcheg.javafile.facts.SuperCtorRef8;
import me.supcheg.javafile.facts.SuperCtorRef9;
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
     * `super(a1, a2, a3, );` by a constructor a subclass alone calls, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3> Terminated<Void> super_(SuperCtorRef3<S, A1, A2, A3> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, ...);` by a constructor a subclass alone calls, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3> Terminated<Void> super_(SuperCtorRef3<S, A1, A2, A3> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Varargs<A3> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, );` by a constructor a subclass alone calls, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4> Terminated<Void> super_(SuperCtorRef4<S, A1, A2, A3, A4> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, ...);` by a constructor a subclass alone calls, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4> Terminated<Void> super_(SuperCtorRef4<S, A1, A2, A3, A4> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Varargs<A4> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, );` by a constructor a subclass alone calls, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5> Terminated<Void> super_(SuperCtorRef5<S, A1, A2, A3, A4, A5> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, ...);` by a constructor a subclass alone calls, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5> Terminated<Void> super_(SuperCtorRef5<S, A1, A2, A3, A4, A5> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Varargs<A5> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, );` by a constructor a subclass alone calls, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6> Terminated<Void> super_(SuperCtorRef6<S, A1, A2, A3, A4, A5, A6> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, a5, ...);` by a constructor a subclass alone calls, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6> Terminated<Void> super_(SuperCtorRef6<S, A1, A2, A3, A4, A5, A6> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Varargs<A6> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, );` by a constructor a subclass alone calls, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7> Terminated<Void> super_(SuperCtorRef7<S, A1, A2, A3, A4, A5, A6, A7> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, ...);` by a constructor a subclass alone calls, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7> Terminated<Void> super_(SuperCtorRef7<S, A1, A2, A3, A4, A5, A6, A7> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Varargs<A7> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, );` by a constructor a subclass alone calls, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> Terminated<Void> super_(SuperCtorRef8<S, A1, A2, A3, A4, A5, A6, A7, A8> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, ...);` by a constructor a subclass alone calls, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> Terminated<Void> super_(SuperCtorRef8<S, A1, A2, A3, A4, A5, A6, A7, A8> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Varargs<A8> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, a9, );` by a constructor a subclass alone calls, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> Terminated<Void> super_(SuperCtorRef9<S, A1, A2, A3, A4, A5, A6, A7, A8, A9> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, ...);` by a constructor a subclass alone calls, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> Terminated<Void> super_(SuperCtorRef9<S, A1, A2, A3, A4, A5, A6, A7, A8, A9> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Varargs<A9> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, );` by a constructor a subclass alone calls, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
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
     * @param rest what follows the call, given its block and `this`
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
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Terminated<Void> super_(SuperCtorRef10<S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, a9, ...);` by a constructor a subclass alone calls, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
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
     * @param body what follows the call, given its block and `this`
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
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Terminated<Void> super_(SuperCtorRef10<S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Varargs<A10> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, );` by a constructor a subclass alone calls, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
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
     * @param rest what follows the call, given its block and `this`
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
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Terminated<Void> super_(SuperCtorRef11<S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, ...);` by a constructor a subclass alone calls, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
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
     * @param body what follows the call, given its block and `this`
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
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Terminated<Void> super_(SuperCtorRef11<S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Varargs<A11> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, );` by a constructor a subclass alone calls, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
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
     * @param rest what follows the call, given its block and `this`
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
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Terminated<Void> super_(SuperCtorRef12<S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, ...);` by a constructor a subclass alone calls, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
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
     * @param body what follows the call, given its block and `this`
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
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Terminated<Void> super_(SuperCtorRef12<S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Varargs<A12> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)), Expressions.spreading(rest), body);
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

    /**
     * `super(a1, a2, a3, );` by a `public` constructor of a superclass that is not abstract, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3> Terminated<Void> super_(CtorRef3<S, A1, A2, A3> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, ...);` by a `public` constructor of a superclass that is not abstract, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3> Terminated<Void> super_(CtorRef3<S, A1, A2, A3> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Varargs<A3> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, );` by a `public` constructor of a superclass that is not abstract, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4> Terminated<Void> super_(CtorRef4<S, A1, A2, A3, A4> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, ...);` by a `public` constructor of a superclass that is not abstract, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4> Terminated<Void> super_(CtorRef4<S, A1, A2, A3, A4> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Varargs<A4> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, );` by a `public` constructor of a superclass that is not abstract, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5> Terminated<Void> super_(CtorRef5<S, A1, A2, A3, A4, A5> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, ...);` by a `public` constructor of a superclass that is not abstract, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5> Terminated<Void> super_(CtorRef5<S, A1, A2, A3, A4, A5> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Varargs<A5> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, );` by a `public` constructor of a superclass that is not abstract, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6> Terminated<Void> super_(CtorRef6<S, A1, A2, A3, A4, A5, A6> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, a5, ...);` by a `public` constructor of a superclass that is not abstract, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6> Terminated<Void> super_(CtorRef6<S, A1, A2, A3, A4, A5, A6> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Varargs<A6> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, );` by a `public` constructor of a superclass that is not abstract, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7> Terminated<Void> super_(CtorRef7<S, A1, A2, A3, A4, A5, A6, A7> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, ...);` by a `public` constructor of a superclass that is not abstract, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7> Terminated<Void> super_(CtorRef7<S, A1, A2, A3, A4, A5, A6, A7> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Varargs<A7> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, );` by a `public` constructor of a superclass that is not abstract, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> Terminated<Void> super_(CtorRef8<S, A1, A2, A3, A4, A5, A6, A7, A8> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, ...);` by a `public` constructor of a superclass that is not abstract, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> Terminated<Void> super_(CtorRef8<S, A1, A2, A3, A4, A5, A6, A7, A8> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Varargs<A8> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, a9, );` by a `public` constructor of a superclass that is not abstract, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param a9 the ninth argument
     * @param rest what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> Terminated<Void> super_(CtorRef9<S, A1, A2, A3, A4, A5, A6, A7, A8, A9> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, ...);` by a `public` constructor of a superclass that is not abstract, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
     * @param a1 the first argument
     * @param a2 the second argument
     * @param a3 the third argument
     * @param a4 the fourth argument
     * @param a5 the fifth argument
     * @param a6 the sixth argument
     * @param a7 the seventh argument
     * @param a8 the eighth argument
     * @param rest the arguments of the last parameter
     * @param body what follows the call, given its block and `this`
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> Terminated<Void> super_(CtorRef9<S, A1, A2, A3, A4, A5, A6, A7, A8, A9> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Varargs<A9> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, );` by a `public` constructor of a superclass that is not abstract, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
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
     * @param rest what follows the call, given its block and `this`
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
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Terminated<Void> super_(CtorRef10<S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, a9, ...);` by a `public` constructor of a superclass that is not abstract, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
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
     * @param body what follows the call, given its block and `this`
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
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Terminated<Void> super_(CtorRef10<S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Varargs<A10> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, );` by a `public` constructor of a superclass that is not abstract, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
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
     * @param rest what follows the call, given its block and `this`
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
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Terminated<Void> super_(CtorRef11<S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, ...);` by a `public` constructor of a superclass that is not abstract, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
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
     * @param body what follows the call, given its block and `this`
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
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Terminated<Void> super_(CtorRef11<S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Varargs<A11> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)), Expressions.spreading(rest), body);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, );` by a `public` constructor of a superclass that is not abstract, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
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
     * @param rest what follows the call, given its block and `this`
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
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Terminated<Void> super_(CtorRef12<S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Expr<? extends A12> a12, InstanceBody0<Self, VoidBody, Void> rest) {
        return call(ctor, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12), Passing.EACH, rest);
    }

    /**
     * `super(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, ...);` by a `public` constructor of a superclass that is not abstract, the arguments of its last parameter, which is of variable arity, one by one, then the rest of the body.
     *
     * @param ctor the constructor of the superclass
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
     * @param body what follows the call, given its block and `this`
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
     * @return the proof that the body ended
     * @throws IllegalStateException if `super_` was called already, the constructor of the superclass throws a checked exception the constructor does not declare, or an argument uses a variable out of scope or throws one
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Terminated<Void> super_(CtorRef12<S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ctor, Expr<? extends A1> a1, Expr<? extends A2> a2, Expr<? extends A3> a3, Expr<? extends A4> a4, Expr<? extends A5> a5, Expr<? extends A6> a6, Expr<? extends A7> a7, Expr<? extends A8> a8, Expr<? extends A9> a9, Expr<? extends A10> a10, Expr<? extends A11> a11, Varargs<A12> rest, InstanceBody0<Self, VoidBody, Void> body) {
        return call(ctor, Expressions.spread(ctor, rest, Expressions.arguments(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)), Expressions.spreading(rest), body);
    }
}
