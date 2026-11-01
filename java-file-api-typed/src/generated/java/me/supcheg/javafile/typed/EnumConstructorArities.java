package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeToken;
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

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class EnumConstructorArities<Self> extends StaticFieldDeclarations<Self> permits TypedEnumBuilder {
    EnumConstructorArities(TargetClasspath target, ClassDesc desc, String kind) {
        super(target, desc, kind);
    }

    abstract VoidBody constructorRoot();

    abstract void declaredConstructor(List<?> params, VoidBody body);

    abstract void declaredConstructor(List<?> params, VoidBody body, DocComment doc);

    /**
     * Declares a `private` constructor of the enum, of 0 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @throws IllegalStateException if the members of the enum have begun
     */
    public void constructor(InstanceBody0<Self, VoidBody, Void> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        MemberBodies.built(root, (b) -> body.build(b, self));
        declaredConstructor(List.of(), root);
    }

    /**
     * Declares a `private` constructor of the enum, of 0 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param doc the documentation comment
     * @throws IllegalStateException if the members of the enum have begun
     */
    public void constructor(DocComment doc, InstanceBody0<Self, VoidBody, Void> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        MemberBodies.built(root, (b) -> body.build(b, self));
        declaredConstructor(List.of(), root, doc);
    }

    /**
     * Declares a `private` constructor of the enum, of 1 parameter.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the parameter type
     * @param <A1> the type of the parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1> void constructor(TypeToken<A1> p1, InstanceBody1<Self, VoidBody, Void, A1> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1));
        declaredConstructor(List.of(v1), root);
    }

    /**
     * Declares a `private` constructor of the enum, of 1 parameter.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param doc the documentation comment
     * @param p1 the parameter type
     * @param <A1> the type of the parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1> void constructor(DocComment doc, TypeToken<A1> p1, InstanceBody1<Self, VoidBody, Void, A1> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1));
        declaredConstructor(List.of(v1), root, doc);
    }

    /**
     * Declares a `private` constructor of the enum, of 2 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2> void constructor(TypeToken<A1> p1, TypeToken<A2> p2, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2));
        declaredConstructor(List.of(v1, v2), root);
    }

    /**
     * Declares a `private` constructor of the enum, of 2 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param doc the documentation comment
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2> void constructor(DocComment doc, TypeToken<A1> p1, TypeToken<A2> p2, InstanceBody2<Self, VoidBody, Void, A1, A2> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2));
        declaredConstructor(List.of(v1, v2), root, doc);
    }

    /**
     * Declares a `private` constructor of the enum, of 3 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3> void constructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, InstanceBody3<Self, VoidBody, Void, A1, A2, A3> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3));
        declaredConstructor(List.of(v1, v2, v3), root);
    }

    /**
     * Declares a `private` constructor of the enum, of 3 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param doc the documentation comment
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3> void constructor(DocComment doc, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, InstanceBody3<Self, VoidBody, Void, A1, A2, A3> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3));
        declaredConstructor(List.of(v1, v2, v3), root, doc);
    }

    /**
     * Declares a `private` constructor of the enum, of 4 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4> void constructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, InstanceBody4<Self, VoidBody, Void, A1, A2, A3, A4> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4));
        declaredConstructor(List.of(v1, v2, v3, v4), root);
    }

    /**
     * Declares a `private` constructor of the enum, of 4 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param doc the documentation comment
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4> void constructor(DocComment doc, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, InstanceBody4<Self, VoidBody, Void, A1, A2, A3, A4> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4));
        declaredConstructor(List.of(v1, v2, v3, v4), root, doc);
    }

    /**
     * Declares a `private` constructor of the enum, of 5 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5> void constructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, InstanceBody5<Self, VoidBody, Void, A1, A2, A3, A4, A5> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5));
        declaredConstructor(List.of(v1, v2, v3, v4, v5), root);
    }

    /**
     * Declares a `private` constructor of the enum, of 5 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param doc the documentation comment
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5> void constructor(DocComment doc, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, InstanceBody5<Self, VoidBody, Void, A1, A2, A3, A4, A5> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5));
        declaredConstructor(List.of(v1, v2, v3, v4, v5), root, doc);
    }

    /**
     * Declares a `private` constructor of the enum, of 6 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @param p6 the sixth parameter type
     * @param <A6> the type of the sixth parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5, A6> void constructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, InstanceBody6<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        Var<A6> v6 = Var.param(p6, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5, v6));
        declaredConstructor(List.of(v1, v2, v3, v4, v5, v6), root);
    }

    /**
     * Declares a `private` constructor of the enum, of 6 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param doc the documentation comment
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @param p6 the sixth parameter type
     * @param <A6> the type of the sixth parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5, A6> void constructor(DocComment doc, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, InstanceBody6<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        Var<A6> v6 = Var.param(p6, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5, v6));
        declaredConstructor(List.of(v1, v2, v3, v4, v5, v6), root, doc);
    }

    /**
     * Declares a `private` constructor of the enum, of 7 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @param p6 the sixth parameter type
     * @param <A6> the type of the sixth parameter
     * @param p7 the seventh parameter type
     * @param <A7> the type of the seventh parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5, A6, A7> void constructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, InstanceBody7<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        Var<A6> v6 = Var.param(p6, root);
        Var<A7> v7 = Var.param(p7, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5, v6, v7));
        declaredConstructor(List.of(v1, v2, v3, v4, v5, v6, v7), root);
    }

    /**
     * Declares a `private` constructor of the enum, of 7 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param doc the documentation comment
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @param p6 the sixth parameter type
     * @param <A6> the type of the sixth parameter
     * @param p7 the seventh parameter type
     * @param <A7> the type of the seventh parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5, A6, A7> void constructor(DocComment doc, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, InstanceBody7<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        Var<A6> v6 = Var.param(p6, root);
        Var<A7> v7 = Var.param(p7, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5, v6, v7));
        declaredConstructor(List.of(v1, v2, v3, v4, v5, v6, v7), root, doc);
    }

    /**
     * Declares a `private` constructor of the enum, of 8 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @param p6 the sixth parameter type
     * @param <A6> the type of the sixth parameter
     * @param p7 the seventh parameter type
     * @param <A7> the type of the seventh parameter
     * @param p8 the eighth parameter type
     * @param <A8> the type of the eighth parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> void constructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, InstanceBody8<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        Var<A6> v6 = Var.param(p6, root);
        Var<A7> v7 = Var.param(p7, root);
        Var<A8> v8 = Var.param(p8, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5, v6, v7, v8));
        declaredConstructor(List.of(v1, v2, v3, v4, v5, v6, v7, v8), root);
    }

    /**
     * Declares a `private` constructor of the enum, of 8 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param doc the documentation comment
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @param p6 the sixth parameter type
     * @param <A6> the type of the sixth parameter
     * @param p7 the seventh parameter type
     * @param <A7> the type of the seventh parameter
     * @param p8 the eighth parameter type
     * @param <A8> the type of the eighth parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> void constructor(DocComment doc, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, InstanceBody8<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        Var<A6> v6 = Var.param(p6, root);
        Var<A7> v7 = Var.param(p7, root);
        Var<A8> v8 = Var.param(p8, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5, v6, v7, v8));
        declaredConstructor(List.of(v1, v2, v3, v4, v5, v6, v7, v8), root, doc);
    }

    /**
     * Declares a `private` constructor of the enum, of 9 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @param p6 the sixth parameter type
     * @param <A6> the type of the sixth parameter
     * @param p7 the seventh parameter type
     * @param <A7> the type of the seventh parameter
     * @param p8 the eighth parameter type
     * @param <A8> the type of the eighth parameter
     * @param p9 the ninth parameter type
     * @param <A9> the type of the ninth parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> void constructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, InstanceBody9<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        Var<A6> v6 = Var.param(p6, root);
        Var<A7> v7 = Var.param(p7, root);
        Var<A8> v8 = Var.param(p8, root);
        Var<A9> v9 = Var.param(p9, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5, v6, v7, v8, v9));
        declaredConstructor(List.of(v1, v2, v3, v4, v5, v6, v7, v8, v9), root);
    }

    /**
     * Declares a `private` constructor of the enum, of 9 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param doc the documentation comment
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @param p6 the sixth parameter type
     * @param <A6> the type of the sixth parameter
     * @param p7 the seventh parameter type
     * @param <A7> the type of the seventh parameter
     * @param p8 the eighth parameter type
     * @param <A8> the type of the eighth parameter
     * @param p9 the ninth parameter type
     * @param <A9> the type of the ninth parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> void constructor(DocComment doc, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, InstanceBody9<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        Var<A6> v6 = Var.param(p6, root);
        Var<A7> v7 = Var.param(p7, root);
        Var<A8> v8 = Var.param(p8, root);
        Var<A9> v9 = Var.param(p9, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5, v6, v7, v8, v9));
        declaredConstructor(List.of(v1, v2, v3, v4, v5, v6, v7, v8, v9), root, doc);
    }

    /**
     * Declares a `private` constructor of the enum, of 10 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @param p6 the sixth parameter type
     * @param <A6> the type of the sixth parameter
     * @param p7 the seventh parameter type
     * @param <A7> the type of the seventh parameter
     * @param p8 the eighth parameter type
     * @param <A8> the type of the eighth parameter
     * @param p9 the ninth parameter type
     * @param <A9> the type of the ninth parameter
     * @param p10 the tenth parameter type
     * @param <A10> the type of the tenth parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> void constructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, InstanceBody10<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        Var<A6> v6 = Var.param(p6, root);
        Var<A7> v7 = Var.param(p7, root);
        Var<A8> v8 = Var.param(p8, root);
        Var<A9> v9 = Var.param(p9, root);
        Var<A10> v10 = Var.param(p10, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5, v6, v7, v8, v9, v10));
        declaredConstructor(List.of(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10), root);
    }

    /**
     * Declares a `private` constructor of the enum, of 10 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param doc the documentation comment
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @param p6 the sixth parameter type
     * @param <A6> the type of the sixth parameter
     * @param p7 the seventh parameter type
     * @param <A7> the type of the seventh parameter
     * @param p8 the eighth parameter type
     * @param <A8> the type of the eighth parameter
     * @param p9 the ninth parameter type
     * @param <A9> the type of the ninth parameter
     * @param p10 the tenth parameter type
     * @param <A10> the type of the tenth parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> void constructor(DocComment doc, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, InstanceBody10<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        Var<A6> v6 = Var.param(p6, root);
        Var<A7> v7 = Var.param(p7, root);
        Var<A8> v8 = Var.param(p8, root);
        Var<A9> v9 = Var.param(p9, root);
        Var<A10> v10 = Var.param(p10, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5, v6, v7, v8, v9, v10));
        declaredConstructor(List.of(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10), root, doc);
    }

    /**
     * Declares a `private` constructor of the enum, of 11 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @param p6 the sixth parameter type
     * @param <A6> the type of the sixth parameter
     * @param p7 the seventh parameter type
     * @param <A7> the type of the seventh parameter
     * @param p8 the eighth parameter type
     * @param <A8> the type of the eighth parameter
     * @param p9 the ninth parameter type
     * @param <A9> the type of the ninth parameter
     * @param p10 the tenth parameter type
     * @param <A10> the type of the tenth parameter
     * @param p11 the eleventh parameter type
     * @param <A11> the type of the eleventh parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> void constructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, InstanceBody11<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        Var<A6> v6 = Var.param(p6, root);
        Var<A7> v7 = Var.param(p7, root);
        Var<A8> v8 = Var.param(p8, root);
        Var<A9> v9 = Var.param(p9, root);
        Var<A10> v10 = Var.param(p10, root);
        Var<A11> v11 = Var.param(p11, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11));
        declaredConstructor(List.of(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11), root);
    }

    /**
     * Declares a `private` constructor of the enum, of 11 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param doc the documentation comment
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @param p6 the sixth parameter type
     * @param <A6> the type of the sixth parameter
     * @param p7 the seventh parameter type
     * @param <A7> the type of the seventh parameter
     * @param p8 the eighth parameter type
     * @param <A8> the type of the eighth parameter
     * @param p9 the ninth parameter type
     * @param <A9> the type of the ninth parameter
     * @param p10 the tenth parameter type
     * @param <A10> the type of the tenth parameter
     * @param p11 the eleventh parameter type
     * @param <A11> the type of the eleventh parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> void constructor(DocComment doc, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, InstanceBody11<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        Var<A6> v6 = Var.param(p6, root);
        Var<A7> v7 = Var.param(p7, root);
        Var<A8> v8 = Var.param(p8, root);
        Var<A9> v9 = Var.param(p9, root);
        Var<A10> v10 = Var.param(p10, root);
        Var<A11> v11 = Var.param(p11, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11));
        declaredConstructor(List.of(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11), root, doc);
    }

    /**
     * Declares a `private` constructor of the enum, of 12 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @param p6 the sixth parameter type
     * @param <A6> the type of the sixth parameter
     * @param p7 the seventh parameter type
     * @param <A7> the type of the seventh parameter
     * @param p8 the eighth parameter type
     * @param <A8> the type of the eighth parameter
     * @param p9 the ninth parameter type
     * @param <A9> the type of the ninth parameter
     * @param p10 the tenth parameter type
     * @param <A10> the type of the tenth parameter
     * @param p11 the eleventh parameter type
     * @param <A11> the type of the eleventh parameter
     * @param p12 the twelfth parameter type
     * @param <A12> the type of the twelfth parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> void constructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, InstanceBody12<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        Var<A6> v6 = Var.param(p6, root);
        Var<A7> v7 = Var.param(p7, root);
        Var<A8> v8 = Var.param(p8, root);
        Var<A9> v9 = Var.param(p9, root);
        Var<A10> v10 = Var.param(p10, root);
        Var<A11> v11 = Var.param(p11, root);
        Var<A12> v12 = Var.param(p12, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12));
        declaredConstructor(List.of(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12), root);
    }

    /**
     * Declares a `private` constructor of the enum, of 12 parameters.
     *
     * @param body the body, given its block, `this` and the parameters
     * @param doc the documentation comment
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @param p5 the fifth parameter type
     * @param <A5> the type of the fifth parameter
     * @param p6 the sixth parameter type
     * @param <A6> the type of the sixth parameter
     * @param p7 the seventh parameter type
     * @param <A7> the type of the seventh parameter
     * @param p8 the eighth parameter type
     * @param <A8> the type of the eighth parameter
     * @param p9 the ninth parameter type
     * @param <A9> the type of the ninth parameter
     * @param p10 the tenth parameter type
     * @param <A10> the type of the tenth parameter
     * @param p11 the eleventh parameter type
     * @param <A11> the type of the eleventh parameter
     * @param p12 the twelfth parameter type
     * @param <A12> the type of the twelfth parameter
     * @throws IllegalStateException if the members of the enum have begun
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> void constructor(DocComment doc, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, InstanceBody12<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> body) {
        requireHeader("a constructor of " + described());
        VoidBody root = constructorRoot();
        This<Self> self = new This(root, self());
        Var<A1> v1 = Var.param(p1, root);
        Var<A2> v2 = Var.param(p2, root);
        Var<A3> v3 = Var.param(p3, root);
        Var<A4> v4 = Var.param(p4, root);
        Var<A5> v5 = Var.param(p5, root);
        Var<A6> v6 = Var.param(p6, root);
        Var<A7> v7 = Var.param(p7, root);
        Var<A8> v8 = Var.param(p8, root);
        Var<A9> v9 = Var.param(p9, root);
        Var<A10> v10 = Var.param(p10, root);
        Var<A11> v11 = Var.param(p11, root);
        Var<A12> v12 = Var.param(p12, root);
        MemberBodies.built(root, (b) -> body.build(b, self, v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12));
        declaredConstructor(List.of(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12), root, doc);
    }
}
