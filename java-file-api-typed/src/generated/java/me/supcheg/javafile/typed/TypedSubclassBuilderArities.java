package me.supcheg.javafile.typed;

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
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody0;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody1;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody10;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody11;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody12;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody13;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody14;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody15;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody16;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody2;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody3;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody4;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody5;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody6;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody7;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody8;
import me.supcheg.javafile.typed.TypedSubclassBodyArities.CtorBody9;

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

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 3 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2, A3> TypedSubclassBodies<Self, S> bodies(CtorRef3<Self, A1, A2, A3> ctor, CtorBody3<Self, S, A1, A2, A3> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 4 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2, A3, A4> TypedSubclassBodies<Self, S> bodies(CtorRef4<Self, A1, A2, A3, A4> ctor, CtorBody4<Self, S, A1, A2, A3, A4> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 5 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2, A3, A4, A5> TypedSubclassBodies<Self, S> bodies(CtorRef5<Self, A1, A2, A3, A4, A5> ctor, CtorBody5<Self, S, A1, A2, A3, A4, A5> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 6 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2, A3, A4, A5, A6> TypedSubclassBodies<Self, S> bodies(CtorRef6<Self, A1, A2, A3, A4, A5, A6> ctor, CtorBody6<Self, S, A1, A2, A3, A4, A5, A6> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 7 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2, A3, A4, A5, A6, A7> TypedSubclassBodies<Self, S> bodies(CtorRef7<Self, A1, A2, A3, A4, A5, A6, A7> ctor, CtorBody7<Self, S, A1, A2, A3, A4, A5, A6, A7> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 8 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> TypedSubclassBodies<Self, S> bodies(CtorRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> ctor, CtorBody8<Self, S, A1, A2, A3, A4, A5, A6, A7, A8> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 9 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> TypedSubclassBodies<Self, S> bodies(CtorRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> ctor, CtorBody9<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 10 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
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
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> TypedSubclassBodies<Self, S> bodies(CtorRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ctor, CtorBody10<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 11 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
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
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> TypedSubclassBodies<Self, S> bodies(CtorRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> ctor, CtorBody11<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 12 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
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
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> TypedSubclassBodies<Self, S> bodies(CtorRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ctor, CtorBody12<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 13 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
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
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> TypedSubclassBodies<Self, S> bodies(CtorRef13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> ctor, CtorBody13<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 14 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
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
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> TypedSubclassBodies<Self, S> bodies(CtorRef14<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> ctor, CtorBody14<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 15 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
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
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> TypedSubclassBodies<Self, S> bodies(CtorRef15<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> ctor, CtorBody15<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }

    /**
     * The declarations of the class end and the bodies of its members begin, with that of a constructor of 16 parameters.
     *
     * @param ctor a declared constructor of the class
     * @param first its body, given the place of its `super(...)` and the parameters
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
     * @return the stage that defines the other declared members
     * @throws IllegalStateException if the declarations have ended already, or leave undone what only a method of the class can do; if the constructor was not declared by this class; and as a definition of the bodies does
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> TypedSubclassBodies<Self, S> bodies(CtorRef16<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> ctor, CtorBody16<Self, S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> first) {
        TypedSubclassBodies<Self, S> bodies = beginBodies();
        bodies.define(ctor, first);
        return bodies;
    }
}
