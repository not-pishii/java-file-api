package me.supcheg.javafile.typed;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Inherited0;
import me.supcheg.javafile.facts.Inherited1;
import me.supcheg.javafile.facts.Inherited10;
import me.supcheg.javafile.facts.Inherited11;
import me.supcheg.javafile.facts.Inherited12;
import me.supcheg.javafile.facts.Inherited2;
import me.supcheg.javafile.facts.Inherited3;
import me.supcheg.javafile.facts.Inherited4;
import me.supcheg.javafile.facts.Inherited5;
import me.supcheg.javafile.facts.Inherited6;
import me.supcheg.javafile.facts.Inherited7;
import me.supcheg.javafile.facts.Inherited8;
import me.supcheg.javafile.facts.Inherited9;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef10;
import me.supcheg.javafile.facts.MethodRef11;
import me.supcheg.javafile.facts.MethodRef12;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodRef4;
import me.supcheg.javafile.facts.MethodRef5;
import me.supcheg.javafile.facts.MethodRef6;
import me.supcheg.javafile.facts.MethodRef7;
import me.supcheg.javafile.facts.MethodRef8;
import me.supcheg.javafile.facts.MethodRef9;
import me.supcheg.javafile.facts.UnsafeInvocables;
import me.supcheg.javafile.facts.VoidInherited0;
import me.supcheg.javafile.facts.VoidInherited1;
import me.supcheg.javafile.facts.VoidInherited10;
import me.supcheg.javafile.facts.VoidInherited11;
import me.supcheg.javafile.facts.VoidInherited12;
import me.supcheg.javafile.facts.VoidInherited2;
import me.supcheg.javafile.facts.VoidInherited3;
import me.supcheg.javafile.facts.VoidInherited4;
import me.supcheg.javafile.facts.VoidInherited5;
import me.supcheg.javafile.facts.VoidInherited6;
import me.supcheg.javafile.facts.VoidInherited7;
import me.supcheg.javafile.facts.VoidInherited8;
import me.supcheg.javafile.facts.VoidInherited9;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef10;
import me.supcheg.javafile.facts.VoidMethodRef11;
import me.supcheg.javafile.facts.VoidMethodRef12;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidMethodRef4;
import me.supcheg.javafile.facts.VoidMethodRef5;
import me.supcheg.javafile.facts.VoidMethodRef6;
import me.supcheg.javafile.facts.VoidMethodRef7;
import me.supcheg.javafile.facts.VoidMethodRef8;
import me.supcheg.javafile.facts.VoidMethodRef9;

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

    /**
     * Says the class has a method of 3 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <R, A1, A2, A3> Inherited3<Self, R, A1, A2, A3> inherited(MethodRef3<? super S, R, A1, A2, A3> method) {
        return UnsafeInvocables.inherited3(declaration().inherit(method));
    }

    /**
     * Says the class has a `void` method of 3 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <A1, A2, A3> VoidInherited3<Self, A1, A2, A3> inherited(VoidMethodRef3<? super S, A1, A2, A3> method) {
        return UnsafeInvocables.voidInherited3(declaration().inherit(method));
    }

    /**
     * Says the class has a method of 4 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <R, A1, A2, A3, A4> Inherited4<Self, R, A1, A2, A3, A4> inherited(MethodRef4<? super S, R, A1, A2, A3, A4> method) {
        return UnsafeInvocables.inherited4(declaration().inherit(method));
    }

    /**
     * Says the class has a `void` method of 4 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <A1, A2, A3, A4> VoidInherited4<Self, A1, A2, A3, A4> inherited(VoidMethodRef4<? super S, A1, A2, A3, A4> method) {
        return UnsafeInvocables.voidInherited4(declaration().inherit(method));
    }

    /**
     * Says the class has a method of 5 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <R, A1, A2, A3, A4, A5> Inherited5<Self, R, A1, A2, A3, A4, A5> inherited(MethodRef5<? super S, R, A1, A2, A3, A4, A5> method) {
        return UnsafeInvocables.inherited5(declaration().inherit(method));
    }

    /**
     * Says the class has a `void` method of 5 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <A1, A2, A3, A4, A5> VoidInherited5<Self, A1, A2, A3, A4, A5> inherited(VoidMethodRef5<? super S, A1, A2, A3, A4, A5> method) {
        return UnsafeInvocables.voidInherited5(declaration().inherit(method));
    }

    /**
     * Says the class has a method of 6 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <R, A1, A2, A3, A4, A5, A6> Inherited6<Self, R, A1, A2, A3, A4, A5, A6> inherited(MethodRef6<? super S, R, A1, A2, A3, A4, A5, A6> method) {
        return UnsafeInvocables.inherited6(declaration().inherit(method));
    }

    /**
     * Says the class has a `void` method of 6 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <A1, A2, A3, A4, A5, A6> VoidInherited6<Self, A1, A2, A3, A4, A5, A6> inherited(VoidMethodRef6<? super S, A1, A2, A3, A4, A5, A6> method) {
        return UnsafeInvocables.voidInherited6(declaration().inherit(method));
    }

    /**
     * Says the class has a method of 7 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7> Inherited7<Self, R, A1, A2, A3, A4, A5, A6, A7> inherited(MethodRef7<? super S, R, A1, A2, A3, A4, A5, A6, A7> method) {
        return UnsafeInvocables.inherited7(declaration().inherit(method));
    }

    /**
     * Says the class has a `void` method of 7 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> VoidInherited7<Self, A1, A2, A3, A4, A5, A6, A7> inherited(VoidMethodRef7<? super S, A1, A2, A3, A4, A5, A6, A7> method) {
        return UnsafeInvocables.voidInherited7(declaration().inherit(method));
    }

    /**
     * Says the class has a method of 8 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8> Inherited8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> inherited(MethodRef8<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8> method) {
        return UnsafeInvocables.inherited8(declaration().inherit(method));
    }

    /**
     * Says the class has a `void` method of 8 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidInherited8<Self, A1, A2, A3, A4, A5, A6, A7, A8> inherited(VoidMethodRef8<? super S, A1, A2, A3, A4, A5, A6, A7, A8> method) {
        return UnsafeInvocables.voidInherited8(declaration().inherit(method));
    }

    /**
     * Says the class has a method of 9 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <R> the result type
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> Inherited9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> inherited(MethodRef9<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method) {
        return UnsafeInvocables.inherited9(declaration().inherit(method));
    }

    /**
     * Says the class has a `void` method of 9 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <A1> the type of the first parameter
     * @param <A2> the type of the second parameter
     * @param <A3> the type of the third parameter
     * @param <A4> the type of the fourth parameter
     * @param <A5> the type of the fifth parameter
     * @param <A6> the type of the sixth parameter
     * @param <A7> the type of the seventh parameter
     * @param <A8> the type of the eighth parameter
     * @param <A9> the type of the ninth parameter
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidInherited9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> inherited(VoidMethodRef9<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9> method) {
        return UnsafeInvocables.voidInherited9(declaration().inherit(method));
    }

    /**
     * Says the class has a method of 10 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <R> the result type
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
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> Inherited10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> inherited(MethodRef10<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method) {
        return UnsafeInvocables.inherited10(declaration().inherit(method));
    }

    /**
     * Says the class has a `void` method of 10 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
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
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidInherited10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> inherited(VoidMethodRef10<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method) {
        return UnsafeInvocables.voidInherited10(declaration().inherit(method));
    }

    /**
     * Says the class has a method of 11 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <R> the result type
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
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> Inherited11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> inherited(MethodRef11<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method) {
        return UnsafeInvocables.inherited11(declaration().inherit(method));
    }

    /**
     * Says the class has a `void` method of 11 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
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
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidInherited11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> inherited(VoidMethodRef11<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method) {
        return UnsafeInvocables.voidInherited11(declaration().inherit(method));
    }

    /**
     * Says the class has a method of 12 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
     * @param <R> the result type
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
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> Inherited12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> inherited(MethodRef12<? super S, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method) {
        return UnsafeInvocables.inherited12(declaration().inherit(method));
    }

    /**
     * Says the class has a `void` method of 12 parameters by inheriting it from its superclass: what a step of the proof that the class implements an interface takes where the superclass has the method of the interface already.
     *
     * @param method the fact of the method of the superclass
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
     * @return how the class has the method: by inheritance, with no method of its own
     * @throws IllegalArgumentException if the superclass has no such method on the target classpath, or has it abstract, `static` or not `public`, or otherwise than the fact tells
     * @throws IllegalStateException if the header of the class has ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidInherited12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> inherited(VoidMethodRef12<? super S, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method) {
        return UnsafeInvocables.voidInherited12(declaration().inherit(method));
    }
}
