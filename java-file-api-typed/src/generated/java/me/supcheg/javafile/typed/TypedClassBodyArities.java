package me.supcheg.javafile.typed;

import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.typed.MemberBodies.Declared;
import me.supcheg.javafile.typed.MemberBodies.Slot.Awaiting;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody0;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody1;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody2;

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
}
