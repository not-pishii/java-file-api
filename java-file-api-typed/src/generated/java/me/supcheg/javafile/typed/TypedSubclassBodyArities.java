package me.supcheg.javafile.typed;

import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.typed.MemberBodies.Declared;
import me.supcheg.javafile.typed.MemberBodies.Slot.Awaiting;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody0;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody1;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody2;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class TypedSubclassBodyArities<Self, S> extends MemberBodies<Self, S> permits TypedSubclassBodies {
    TypedSubclassBodyArities(Declared<Self> declared) {
        super(declared);
    }

    /**
     * Defines a declared constructor of 0 parameters: its body begins with `super(...)`.
     *
     * @param ctor the declared constructor
     * @param body the body, given the place of its `super(...)`
     * @throws IllegalStateException if the body does not begin with `super(...)`, and as a definition of a method does ([MemberBodies])
     */
    public void define(CtorRef0<Self> ctor, CtorBody0<Self, S> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        SuperSite<Self, S> site = superSite(root);
        MemberBodies.built(root, (b) -> body.build(site));
        definedWithSuper(slot, root, site, List.of());
    }

    /**
     * The body of a constructor of 0 parameters of a class that extends `S`.
     *
     * @param <Self> the class
     * @param <S> its superclass
     */
    @FunctionalInterface
    public interface CtorBody0<Self, S> {
        /**
         * Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
         *
         * @param ctor the place of `super(...)`
         * @return the proof that the body ended, as `super_` gave it
         */
        Terminated<Void> build(SuperSite<Self, S> ctor);
    }

    /**
     * Defines a declared constructor of 1 parameter: its body begins with `super(...)`.
     *
     * @param ctor the declared constructor
     * @param body the body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the parameter
     * @throws IllegalStateException if the body does not begin with `super(...)`, and as a definition of a method does ([MemberBodies])
     */
    public <A1> void define(CtorRef1<Self, A1> ctor, CtorBody1<Self, S, A1> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        SuperSite<Self, S> site = superSite(root);
        Var<A1> p1 = MemberBodies.lastParameter(slot, ctor.param1(), root);
        MemberBodies.built(root, (b) -> body.build(site, p1));
        definedWithSuper(slot, root, site, List.of(p1));
    }

    /**
     * The body of a constructor of 1 parameter of a class that extends `S`.
     *
     * @param <Self> the class
     * @param <S> its superclass
     * @param <A1> the parameter type
     */
    @FunctionalInterface
    public interface CtorBody1<Self, S, A1> {
        /**
         * Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
         *
         * @param ctor the place of `super(...)`
         * @param p1 the parameter
         * @return the proof that the body ended, as `super_` gave it
         */
        Terminated<Void> build(SuperSite<Self, S> ctor, Var<A1> p1);
    }

    /**
     * Defines a declared constructor of 2 parameters: its body begins with `super(...)`.
     *
     * @param ctor the declared constructor
     * @param body the body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @throws IllegalStateException if the body does not begin with `super(...)`, and as a definition of a method does ([MemberBodies])
     */
    public <A1, A2> void define(CtorRef2<Self, A1, A2> ctor, CtorBody2<Self, S, A1, A2> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        SuperSite<Self, S> site = superSite(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = MemberBodies.lastParameter(slot, ctor.param2(), root);
        MemberBodies.built(root, (b) -> body.build(site, p1, p2));
        definedWithSuper(slot, root, site, List.of(p1, p2));
    }

    /**
     * The body of a constructor of 2 parameters of a class that extends `S`.
     *
     * @param <Self> the class
     * @param <S> its superclass
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     */
    @FunctionalInterface
    public interface CtorBody2<Self, S, A1, A2> {
        /**
         * Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
         *
         * @param ctor the place of `super(...)`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @return the proof that the body ended, as `super_` gave it
         */
        Terminated<Void> build(SuperSite<Self, S> ctor, Var<A1> p1, Var<A2> p2);
    }
}
