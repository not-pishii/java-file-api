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
import me.supcheg.javafile.typed.MemberBodies.Declared;
import me.supcheg.javafile.typed.MemberBodies.Slot.Awaiting;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody0;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody1;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody10;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody11;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody12;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody2;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody3;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody4;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody5;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody6;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody7;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody8;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody9;

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

    /**
     * Defines a declared constructor of 3 parameters: its body begins with `super(...)`.
     *
     * @param ctor the declared constructor
     * @param body the body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @throws IllegalStateException if the body does not begin with `super(...)`, and as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3> void define(CtorRef3<Self, A1, A2, A3> ctor, CtorBody3<Self, S, A1, A2, A3> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        SuperSite<Self, S> site = superSite(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = MemberBodies.lastParameter(slot, ctor.param3(), root);
        MemberBodies.built(root, (b) -> body.build(site, p1, p2, p3));
        definedWithSuper(slot, root, site, List.of(p1, p2, p3));
    }

    /**
     * The body of a constructor of 3 parameters of a class that extends `S`.
     *
     * @param <Self> the class
     * @param <S> its superclass
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     */
    @FunctionalInterface
    public interface CtorBody3<Self, S, A1, A2, A3> {
        /**
         * Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
         *
         * @param ctor the place of `super(...)`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @return the proof that the body ended, as `super_` gave it
         */
        Terminated<Void> build(SuperSite<Self, S> ctor, Var<A1> p1, Var<A2> p2, Var<A3> p3);
    }

    /**
     * Defines a declared constructor of 4 parameters: its body begins with `super(...)`.
     *
     * @param ctor the declared constructor
     * @param body the body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @throws IllegalStateException if the body does not begin with `super(...)`, and as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4> void define(CtorRef4<Self, A1, A2, A3, A4> ctor, CtorBody4<Self, S, A1, A2, A3, A4> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        SuperSite<Self, S> site = superSite(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = MemberBodies.lastParameter(slot, ctor.param4(), root);
        MemberBodies.built(root, (b) -> body.build(site, p1, p2, p3, p4));
        definedWithSuper(slot, root, site, List.of(p1, p2, p3, p4));
    }

    /**
     * The body of a constructor of 4 parameters of a class that extends `S`.
     *
     * @param <Self> the class
     * @param <S> its superclass
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     * @param <A4> the fourth parameter type
     */
    @FunctionalInterface
    public interface CtorBody4<Self, S, A1, A2, A3, A4> {
        /**
         * Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
         *
         * @param ctor the place of `super(...)`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @return the proof that the body ended, as `super_` gave it
         */
        Terminated<Void> build(SuperSite<Self, S> ctor, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4);
    }

    /**
     * Defines a declared constructor of 5 parameters: its body begins with `super(...)`.
     *
     * @param ctor the declared constructor
     * @param body the body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @throws IllegalStateException if the body does not begin with `super(...)`, and as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5> void define(CtorRef5<Self, A1, A2, A3, A4, A5> ctor, CtorBody5<Self, S, A1, A2, A3, A4, A5> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        SuperSite<Self, S> site = superSite(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = Var.param(ctor.param4(), root);
        Var<A5> p5 = MemberBodies.lastParameter(slot, ctor.param5(), root);
        MemberBodies.built(root, (b) -> body.build(site, p1, p2, p3, p4, p5));
        definedWithSuper(slot, root, site, List.of(p1, p2, p3, p4, p5));
    }

    /**
     * The body of a constructor of 5 parameters of a class that extends `S`.
     *
     * @param <Self> the class
     * @param <S> its superclass
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     * @param <A4> the fourth parameter type
     * @param <A5> the fifth parameter type
     */
    @FunctionalInterface
    public interface CtorBody5<Self, S, A1, A2, A3, A4, A5> {
        /**
         * Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
         *
         * @param ctor the place of `super(...)`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @param p5 the fifth parameter
         * @return the proof that the body ended, as `super_` gave it
         */
        Terminated<Void> build(SuperSite<Self, S> ctor, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5);
    }

    /**
     * Defines a declared constructor of 6 parameters: its body begins with `super(...)`.
     *
     * @param ctor the declared constructor
     * @param body the body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @throws IllegalStateException if the body does not begin with `super(...)`, and as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6> void define(CtorRef6<Self, A1, A2, A3, A4, A5, A6> ctor, CtorBody6<Self, S, A1, A2, A3, A4, A5, A6> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        SuperSite<Self, S> site = superSite(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = Var.param(ctor.param4(), root);
        Var<A5> p5 = Var.param(ctor.param5(), root);
        Var<A6> p6 = MemberBodies.lastParameter(slot, ctor.param6(), root);
        MemberBodies.built(root, (b) -> body.build(site, p1, p2, p3, p4, p5, p6));
        definedWithSuper(slot, root, site, List.of(p1, p2, p3, p4, p5, p6));
    }

    /**
     * The body of a constructor of 6 parameters of a class that extends `S`.
     *
     * @param <Self> the class
     * @param <S> its superclass
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     * @param <A4> the fourth parameter type
     * @param <A5> the fifth parameter type
     * @param <A6> the sixth parameter type
     */
    @FunctionalInterface
    public interface CtorBody6<Self, S, A1, A2, A3, A4, A5, A6> {
        /**
         * Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
         *
         * @param ctor the place of `super(...)`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @param p5 the fifth parameter
         * @param p6 the sixth parameter
         * @return the proof that the body ended, as `super_` gave it
         */
        Terminated<Void> build(SuperSite<Self, S> ctor, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5, Var<A6> p6);
    }

    /**
     * Defines a declared constructor of 7 parameters: its body begins with `super(...)`.
     *
     * @param ctor the declared constructor
     * @param body the body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @throws IllegalStateException if the body does not begin with `super(...)`, and as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7> void define(CtorRef7<Self, A1, A2, A3, A4, A5, A6, A7> ctor, CtorBody7<Self, S, A1, A2, A3, A4, A5, A6, A7> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        SuperSite<Self, S> site = superSite(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = Var.param(ctor.param4(), root);
        Var<A5> p5 = Var.param(ctor.param5(), root);
        Var<A6> p6 = Var.param(ctor.param6(), root);
        Var<A7> p7 = MemberBodies.lastParameter(slot, ctor.param7(), root);
        MemberBodies.built(root, (b) -> body.build(site, p1, p2, p3, p4, p5, p6, p7));
        definedWithSuper(slot, root, site, List.of(p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * The body of a constructor of 7 parameters of a class that extends `S`.
     *
     * @param <Self> the class
     * @param <S> its superclass
     * @param <A1> the first parameter type
     * @param <A2> the second parameter type
     * @param <A3> the third parameter type
     * @param <A4> the fourth parameter type
     * @param <A5> the fifth parameter type
     * @param <A6> the sixth parameter type
     * @param <A7> the seventh parameter type
     */
    @FunctionalInterface
    public interface CtorBody7<Self, S, A1, A2, A3, A4, A5, A6, A7> {
        /**
         * Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
         *
         * @param ctor the place of `super(...)`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @param p5 the fifth parameter
         * @param p6 the sixth parameter
         * @param p7 the seventh parameter
         * @return the proof that the body ended, as `super_` gave it
         */
        Terminated<Void> build(SuperSite<Self, S> ctor, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5, Var<A6> p6, Var<A7> p7);
    }

    /**
     * Defines a declared constructor of 8 parameters: its body begins with `super(...)`.
     *
     * @param ctor the declared constructor
     * @param body the body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @throws IllegalStateException if the body does not begin with `super(...)`, and as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> void define(CtorRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> ctor, CtorBody8<Self, S, A1, A2, A3, A4, A5, A6, A7, A8> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        SuperSite<Self, S> site = superSite(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = Var.param(ctor.param4(), root);
        Var<A5> p5 = Var.param(ctor.param5(), root);
        Var<A6> p6 = Var.param(ctor.param6(), root);
        Var<A7> p7 = Var.param(ctor.param7(), root);
        Var<A8> p8 = MemberBodies.lastParameter(slot, ctor.param8(), root);
        MemberBodies.built(root, (b) -> body.build(site, p1, p2, p3, p4, p5, p6, p7, p8));
        definedWithSuper(slot, root, site, List.of(p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * The body of a constructor of 8 parameters of a class that extends `S`.
     *
     * @param <Self> the class
     * @param <S> its superclass
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
    public interface CtorBody8<Self, S, A1, A2, A3, A4, A5, A6, A7, A8> {
        /**
         * Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
         *
         * @param ctor the place of `super(...)`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @param p5 the fifth parameter
         * @param p6 the sixth parameter
         * @param p7 the seventh parameter
         * @param p8 the eighth parameter
         * @return the proof that the body ended, as `super_` gave it
         */
        Terminated<Void> build(SuperSite<Self, S> ctor, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5, Var<A6> p6, Var<A7> p7, Var<A8> p8);
    }

    /**
     * Defines a declared constructor of 9 parameters: its body begins with `super(...)`.
     *
     * @param ctor the declared constructor
     * @param body the body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @throws IllegalStateException if the body does not begin with `super(...)`, and as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> void define(CtorRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> ctor, CtorBody9<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        SuperSite<Self, S> site = superSite(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = Var.param(ctor.param4(), root);
        Var<A5> p5 = Var.param(ctor.param5(), root);
        Var<A6> p6 = Var.param(ctor.param6(), root);
        Var<A7> p7 = Var.param(ctor.param7(), root);
        Var<A8> p8 = Var.param(ctor.param8(), root);
        Var<A9> p9 = MemberBodies.lastParameter(slot, ctor.param9(), root);
        MemberBodies.built(root, (b) -> body.build(site, p1, p2, p3, p4, p5, p6, p7, p8, p9));
        definedWithSuper(slot, root, site, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * The body of a constructor of 9 parameters of a class that extends `S`.
     *
     * @param <Self> the class
     * @param <S> its superclass
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
    public interface CtorBody9<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9> {
        /**
         * Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
         *
         * @param ctor the place of `super(...)`
         * @param p1 the first parameter
         * @param p2 the second parameter
         * @param p3 the third parameter
         * @param p4 the fourth parameter
         * @param p5 the fifth parameter
         * @param p6 the sixth parameter
         * @param p7 the seventh parameter
         * @param p8 the eighth parameter
         * @param p9 the ninth parameter
         * @return the proof that the body ended, as `super_` gave it
         */
        Terminated<Void> build(SuperSite<Self, S> ctor, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5, Var<A6> p6, Var<A7> p7, Var<A8> p8, Var<A9> p9);
    }

    /**
     * Defines a declared constructor of 10 parameters: its body begins with `super(...)`.
     *
     * @param ctor the declared constructor
     * @param body the body, given the place of its `super(...)` and the parameters
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
     * @throws IllegalStateException if the body does not begin with `super(...)`, and as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> void define(CtorRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ctor, CtorBody10<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        SuperSite<Self, S> site = superSite(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = Var.param(ctor.param4(), root);
        Var<A5> p5 = Var.param(ctor.param5(), root);
        Var<A6> p6 = Var.param(ctor.param6(), root);
        Var<A7> p7 = Var.param(ctor.param7(), root);
        Var<A8> p8 = Var.param(ctor.param8(), root);
        Var<A9> p9 = Var.param(ctor.param9(), root);
        Var<A10> p10 = MemberBodies.lastParameter(slot, ctor.param10(), root);
        MemberBodies.built(root, (b) -> body.build(site, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
        definedWithSuper(slot, root, site, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    /**
     * The body of a constructor of 10 parameters of a class that extends `S`.
     *
     * @param <Self> the class
     * @param <S> its superclass
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
    public interface CtorBody10<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> {
        /**
         * Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
         *
         * @param ctor the place of `super(...)`
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
         * @return the proof that the body ended, as `super_` gave it
         */
        Terminated<Void> build(SuperSite<Self, S> ctor, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5, Var<A6> p6, Var<A7> p7, Var<A8> p8, Var<A9> p9, Var<A10> p10);
    }

    /**
     * Defines a declared constructor of 11 parameters: its body begins with `super(...)`.
     *
     * @param ctor the declared constructor
     * @param body the body, given the place of its `super(...)` and the parameters
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
     * @throws IllegalStateException if the body does not begin with `super(...)`, and as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> void define(CtorRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> ctor, CtorBody11<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        SuperSite<Self, S> site = superSite(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = Var.param(ctor.param4(), root);
        Var<A5> p5 = Var.param(ctor.param5(), root);
        Var<A6> p6 = Var.param(ctor.param6(), root);
        Var<A7> p7 = Var.param(ctor.param7(), root);
        Var<A8> p8 = Var.param(ctor.param8(), root);
        Var<A9> p9 = Var.param(ctor.param9(), root);
        Var<A10> p10 = Var.param(ctor.param10(), root);
        Var<A11> p11 = MemberBodies.lastParameter(slot, ctor.param11(), root);
        MemberBodies.built(root, (b) -> body.build(site, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
        definedWithSuper(slot, root, site, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
    }

    /**
     * The body of a constructor of 11 parameters of a class that extends `S`.
     *
     * @param <Self> the class
     * @param <S> its superclass
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
    public interface CtorBody11<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> {
        /**
         * Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
         *
         * @param ctor the place of `super(...)`
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
         * @return the proof that the body ended, as `super_` gave it
         */
        Terminated<Void> build(SuperSite<Self, S> ctor, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5, Var<A6> p6, Var<A7> p7, Var<A8> p8, Var<A9> p9, Var<A10> p10, Var<A11> p11);
    }

    /**
     * Defines a declared constructor of 12 parameters: its body begins with `super(...)`.
     *
     * @param ctor the declared constructor
     * @param body the body, given the place of its `super(...)` and the parameters
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
     * @throws IllegalStateException if the body does not begin with `super(...)`, and as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> void define(CtorRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ctor, CtorBody12<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        SuperSite<Self, S> site = superSite(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = Var.param(ctor.param4(), root);
        Var<A5> p5 = Var.param(ctor.param5(), root);
        Var<A6> p6 = Var.param(ctor.param6(), root);
        Var<A7> p7 = Var.param(ctor.param7(), root);
        Var<A8> p8 = Var.param(ctor.param8(), root);
        Var<A9> p9 = Var.param(ctor.param9(), root);
        Var<A10> p10 = Var.param(ctor.param10(), root);
        Var<A11> p11 = Var.param(ctor.param11(), root);
        Var<A12> p12 = MemberBodies.lastParameter(slot, ctor.param12(), root);
        MemberBodies.built(root, (b) -> body.build(site, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
        definedWithSuper(slot, root, site, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    }

    /**
     * The body of a constructor of 12 parameters of a class that extends `S`.
     *
     * @param <Self> the class
     * @param <S> its superclass
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
    public interface CtorBody12<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> {
        /**
         * Builds the body: `ctor.super_(...)`, whose last argument builds what follows it.
         *
         * @param ctor the place of `super(...)`
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
         * @return the proof that the body ended, as `super_` gave it
         */
        Terminated<Void> build(SuperSite<Self, S> ctor, Var<A1> p1, Var<A2> p2, Var<A3> p3, Var<A4> p4, Var<A5> p5, Var<A6> p6, Var<A7> p7, Var<A8> p8, Var<A9> p9, Var<A10> p10, Var<A11> p11, Var<A12> p12);
    }
}
