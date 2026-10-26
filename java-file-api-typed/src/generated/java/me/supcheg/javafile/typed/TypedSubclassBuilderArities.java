package me.supcheg.javafile.typed;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody0;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody1;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody2;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class TypedSubclassBuilderArities<Self, S> extends MemberBuilder<Self, S> permits TypedSubclassBuilder {
    TypedSubclassBuilderArities(ClassDeclaration<Self> declared, Inheritance inherited, Superclass superclass) {
        super(declared, inherited, superclass);
    }

    abstract TypedSubclassBodies<Self, S> beginBodies();

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 0 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)`
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public TypedSubclassBodies<Self, S> bodies(CtorRef0<Self> ctor, CtorBody0<Self, S> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 1 parameter.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the parameter
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1> TypedSubclassBodies<Self, S> bodies(CtorRef1<Self, A1> ctor, CtorBody1<Self, S, A1> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 2 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2> TypedSubclassBodies<Self, S> bodies(CtorRef2<Self, A1, A2> ctor, CtorBody2<Self, S, A1, A2> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }
}
