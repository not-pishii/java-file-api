package me.supcheg.javafile.typed;

import java.util.List;
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
import me.supcheg.javafile.typed.MemberBodies.Declared;
import me.supcheg.javafile.typed.MemberBodies.Slot.Awaiting;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody0;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody1;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody10;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody11;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody12;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody13;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody14;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody15;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody16;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody2;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody3;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody4;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody5;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody6;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody7;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody8;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody9;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class TypedClassBodyArities<Self> extends MemberBodies<Self, Object> permits TypedClassBodies {
    TypedClassBodyArities(Declared<Self> declared) {
        super(declared);
    }

    /**
     * Defines a declared constructor; the body gets `this`.
     *
     * @param ctor the declared constructor
     * @param body the body, given its block, `this`
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public void define(CtorRef0<Self> ctor, InstanceBody0<Self, VoidBody, Void> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        defineIn(slot, root, List.of(), (b) -> body.build(b, self));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameter.
     *
     * @param ctor the declared constructor
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the parameter
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1> void define(CtorRef1<Self, A1> ctor, InstanceBody1<Self, VoidBody, Void, A1> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = MemberBodies.lastParameter(slot, ctor.param1(), root);
        defineIn(slot, root, List.of(p1), (b) -> body.build(b, self, p1));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2> void define(CtorRef2<Self, A1, A2> ctor, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = MemberBodies.lastParameter(slot, ctor.param2(), root);
        defineIn(slot, root, List.of(p1, p2), (b) -> body.build(b, self, p1, p2));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3> void define(CtorRef3<Self, A1, A2, A3> ctor, InstanceBody3<Self, VoidBody, Void, A1, A2, A3> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = MemberBodies.lastParameter(slot, ctor.param3(), root);
        defineIn(slot, root, List.of(p1, p2, p3), (b) -> body.build(b, self, p1, p2, p3));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4> void define(CtorRef4<Self, A1, A2, A3, A4> ctor, InstanceBody4<Self, VoidBody, Void, A1, A2, A3, A4> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = MemberBodies.lastParameter(slot, ctor.param4(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4), (b) -> body.build(b, self, p1, p2, p3, p4));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5> void define(CtorRef5<Self, A1, A2, A3, A4, A5> ctor, InstanceBody5<Self, VoidBody, Void, A1, A2, A3, A4, A5> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = Var.param(ctor.param4(), root);
        Var<A5> p5 = MemberBodies.lastParameter(slot, ctor.param5(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5), (b) -> body.build(b, self, p1, p2, p3, p4, p5));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6> void define(CtorRef6<Self, A1, A2, A3, A4, A5, A6> ctor, InstanceBody6<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = Var.param(ctor.param4(), root);
        Var<A5> p5 = Var.param(ctor.param5(), root);
        Var<A6> p6 = MemberBodies.lastParameter(slot, ctor.param6(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7> void define(CtorRef7<Self, A1, A2, A3, A4, A5, A6, A7> ctor, InstanceBody7<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = Var.param(ctor.param4(), root);
        Var<A5> p5 = Var.param(ctor.param5(), root);
        Var<A6> p6 = Var.param(ctor.param6(), root);
        Var<A7> p7 = MemberBodies.lastParameter(slot, ctor.param7(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
     * @param body the body, given its block, `this` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> void define(CtorRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> ctor, InstanceBody8<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = Var.param(ctor.param4(), root);
        Var<A5> p5 = Var.param(ctor.param5(), root);
        Var<A6> p6 = Var.param(ctor.param6(), root);
        Var<A7> p7 = Var.param(ctor.param7(), root);
        Var<A8> p8 = MemberBodies.lastParameter(slot, ctor.param8(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
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
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> void define(CtorRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> ctor, InstanceBody9<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
        Var<A1> p1 = Var.param(ctor.param1(), root);
        Var<A2> p2 = Var.param(ctor.param2(), root);
        Var<A3> p3 = Var.param(ctor.param3(), root);
        Var<A4> p4 = Var.param(ctor.param4(), root);
        Var<A5> p5 = Var.param(ctor.param5(), root);
        Var<A6> p6 = Var.param(ctor.param6(), root);
        Var<A7> p7 = Var.param(ctor.param7(), root);
        Var<A8> p8 = Var.param(ctor.param8(), root);
        Var<A9> p9 = MemberBodies.lastParameter(slot, ctor.param9(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
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
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> void define(CtorRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ctor, InstanceBody10<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
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
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
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
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> void define(CtorRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> ctor, InstanceBody11<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
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
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
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
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> void define(CtorRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ctor, InstanceBody12<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
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
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
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
     * @param <A13> the type of the thirteenth parameter
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> void define(CtorRef13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> ctor, InstanceBody13<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
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
        Var<A12> p12 = Var.param(ctor.param12(), root);
        Var<A13> p13 = MemberBodies.lastParameter(slot, ctor.param13(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> void define(CtorRef14<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> ctor, InstanceBody14<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
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
        Var<A12> p12 = Var.param(ctor.param12(), root);
        Var<A13> p13 = Var.param(ctor.param13(), root);
        Var<A14> p14 = MemberBodies.lastParameter(slot, ctor.param14(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> void define(CtorRef15<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> ctor, InstanceBody15<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
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
        Var<A12> p12 = Var.param(ctor.param12(), root);
        Var<A13> p13 = Var.param(ctor.param13(), root);
        Var<A14> p14 = Var.param(ctor.param14(), root);
        Var<A15> p15 = MemberBodies.lastParameter(slot, ctor.param15(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15));
    }

    /**
     * Defines a declared constructor; the body gets `this` and the parameters.
     *
     * @param ctor the declared constructor
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
     * @param <A13> the type of the thirteenth parameter
     * @param <A14> the type of the fourteenth parameter
     * @param <A15> the type of the fifteenth parameter
     * @param <A16> the type of the sixteenth parameter
     * @throws IllegalStateException as a definition of a method does ([MemberBodies])
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> void define(CtorRef16<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> ctor, InstanceBody16<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> body) {
        Awaiting slot = definable(ctor);
        VoidBody root = VoidBody.root("body of constructor", MemberBodies.declares(ctor));
        This<Self> self = thisOf(root);
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
        Var<A12> p12 = Var.param(ctor.param12(), root);
        Var<A13> p13 = Var.param(ctor.param13(), root);
        Var<A14> p14 = Var.param(ctor.param14(), root);
        Var<A15> p15 = Var.param(ctor.param15(), root);
        Var<A16> p16 = MemberBodies.lastParameter(slot, ctor.param16(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16), (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16));
    }
}
