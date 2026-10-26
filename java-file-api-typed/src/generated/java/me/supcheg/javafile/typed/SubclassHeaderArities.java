package me.supcheg.javafile.typed;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Inherited0;
import me.supcheg.javafile.facts.Inherited1;
import me.supcheg.javafile.facts.Inherited2;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidInherited0;
import me.supcheg.javafile.facts.VoidInherited1;
import me.supcheg.javafile.facts.VoidInherited2;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class SubclassHeaderArities<Self, S> extends Overriding<Self> permits SubclassHeader {
    /**
     * Says the class has a method of 0 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <R> the result type
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <R> Inherited0<Self, R> inherited(MethodRef0<? super S, R> method) {
        return UnsafeInvocables.inherited0(declaration().inherit(method));
    }

    /**
     * Says the class has a `void` method of 0 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public VoidInherited0<Self> inherited(VoidMethodRef0<? super S> method) {
        return UnsafeInvocables.voidInherited0(declaration().inherit(method));
    }

    /**
     * Says the class has a method of 1 parameter by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <R> the result type
     * @param <A1> the type of the parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <R, A1> Inherited1<Self, R, A1> inherited(MethodRef1<? super S, R, A1> method) {
        return UnsafeInvocables.inherited1(declaration().inherit(method));
    }

    /**
     * Says the class has a `void` method of 1 parameter by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <A1> the type of the parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <A1> VoidInherited1<Self, A1> inherited(VoidMethodRef1<? super S, A1> method) {
        return UnsafeInvocables.voidInherited1(declaration().inherit(method));
    }

    /**
     * Says the class has a method of 2 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <R, A1, A2> Inherited2<Self, R, A1, A2> inherited(MethodRef2<? super S, R, A1, A2> method) {
        return UnsafeInvocables.inherited2(declaration().inherit(method));
    }

    /**
     * Says the class has a `void` method of 2 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <A1, A2> VoidInherited2<Self, A1, A2> inherited(VoidMethodRef2<? super S, A1, A2> method) {
        return UnsafeInvocables.voidInherited2(declaration().inherit(method));
    }
}
