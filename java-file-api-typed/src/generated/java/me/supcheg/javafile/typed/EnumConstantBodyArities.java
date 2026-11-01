package me.supcheg.javafile.typed;

import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
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
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef10;
import me.supcheg.javafile.facts.StaticMethodRef11;
import me.supcheg.javafile.facts.StaticMethodRef12;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.StaticMethodRef3;
import me.supcheg.javafile.facts.StaticMethodRef4;
import me.supcheg.javafile.facts.StaticMethodRef5;
import me.supcheg.javafile.facts.StaticMethodRef6;
import me.supcheg.javafile.facts.StaticMethodRef7;
import me.supcheg.javafile.facts.StaticMethodRef8;
import me.supcheg.javafile.facts.StaticMethodRef9;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeInvocables;
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
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef10;
import me.supcheg.javafile.facts.VoidStaticMethodRef11;
import me.supcheg.javafile.facts.VoidStaticMethodRef12;
import me.supcheg.javafile.facts.VoidStaticMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef3;
import me.supcheg.javafile.facts.VoidStaticMethodRef4;
import me.supcheg.javafile.facts.VoidStaticMethodRef5;
import me.supcheg.javafile.facts.VoidStaticMethodRef6;
import me.supcheg.javafile.facts.VoidStaticMethodRef7;
import me.supcheg.javafile.facts.VoidStaticMethodRef8;
import me.supcheg.javafile.facts.VoidStaticMethodRef9;
import me.supcheg.javafile.model.EnumConstantMember;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody10;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody11;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody12;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody5;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody6;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody7;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody8;
import me.supcheg.javafile.typed.MemberBodyArities.InstanceBody9;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member.Kind;
import me.supcheg.javafile.typed.TypedTypeBodies.Slot.Awaiting;
import me.supcheg.javafile.typed.TypedTypeBodies.Start;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class EnumConstantBodyArities<Self> extends TypedTypeBodies<Self, EnumConstantMember> permits TypedEnumConstantBodies {
    EnumConstantBodyArities(TypedEnumBuilder<Self> owner, List<Invocable> abstractMethods) {
        super(owner, TypedEnumConstantBodies.declared(owner, abstractMethods));
    }

    abstract <F extends Invocable> F declaredHelper(F member, Kind kind);

    /**
     * Declares a `public` helper method of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R> MethodRef0<Self, R> declareMethod(String name, TypeToken<R> result) {
        return declaredHelper(UnsafeInvocables.method(self(), name, result, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `void` helper method of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public VoidMethodRef0<Self> declareVoidMethod(String name) {
        return declaredHelper(UnsafeInvocables.voidMethod(self(), name, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `static` helper method of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R> StaticMethodRef0<R> declareStaticMethod(String name, TypeToken<R> result) {
        return declaredHelper(UnsafeInvocables.staticMethod(self(), name, result, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` `static` `void` helper method of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public VoidStaticMethodRef0 declareVoidStaticMethod(String name) {
        return declaredHelper(UnsafeInvocables.voidStaticMethod(self(), name, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` helper method of 1 parameter of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the parameter type
     * @param <A1> the parameter type of the parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1> MethodRef1<Self, R, A1> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declaredHelper(UnsafeInvocables.method(self(), name, result, p1, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `void` helper method of 1 parameter of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the parameter type
     * @param <A1> the parameter type of the parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1> VoidMethodRef1<Self, A1> declareVoidMethod(String name, TypeToken<A1> p1) {
        return declaredHelper(UnsafeInvocables.voidMethod(self(), name, p1, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `static` helper method of 1 parameter of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the parameter type
     * @param <A1> the parameter type of the parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1> StaticMethodRef1<R, A1> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declaredHelper(UnsafeInvocables.staticMethod(self(), name, result, p1, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` `static` `void` helper method of 1 parameter of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the parameter type
     * @param <A1> the parameter type of the parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1> VoidStaticMethodRef1<A1> declareVoidStaticMethod(String name, TypeToken<A1> p1) {
        return declaredHelper(UnsafeInvocables.voidStaticMethod(self(), name, p1, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` helper method of 2 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declaredHelper(UnsafeInvocables.method(self(), name, result, p1, p2, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `void` helper method of 2 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declaredHelper(UnsafeInvocables.voidMethod(self(), name, p1, p2, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `static` helper method of 2 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2> StaticMethodRef2<R, A1, A2> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declaredHelper(UnsafeInvocables.staticMethod(self(), name, result, p1, p2, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` `static` `void` helper method of 2 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2> VoidStaticMethodRef2<A1, A2> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declaredHelper(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` helper method of 3 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declaredHelper(UnsafeInvocables.method(self(), name, result, p1, p2, p3, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `void` helper method of 3 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declaredHelper(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `static` helper method of 3 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3> StaticMethodRef3<R, A1, A2, A3> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declaredHelper(UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` `static` `void` helper method of 3 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3> VoidStaticMethodRef3<A1, A2, A3> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declaredHelper(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` helper method of 4 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declaredHelper(UnsafeInvocables.method(self(), name, result, p1, p2, p3, p4, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `void` helper method of 4 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declaredHelper(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, p4, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `static` helper method of 4 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4> StaticMethodRef4<R, A1, A2, A3, A4> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declaredHelper(UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, p4, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` `static` `void` helper method of 4 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4> VoidStaticMethodRef4<A1, A2, A3, A4> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declaredHelper(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, p4, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` helper method of 5 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5> MethodRef5<Self, R, A1, A2, A3, A4, A5> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declaredHelper(UnsafeInvocables.method(self(), name, result, p1, p2, p3, p4, p5, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `void` helper method of 5 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5> VoidMethodRef5<Self, A1, A2, A3, A4, A5> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declaredHelper(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, p4, p5, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `static` helper method of 5 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5> StaticMethodRef5<R, A1, A2, A3, A4, A5> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declaredHelper(UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, p4, p5, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` `static` `void` helper method of 5 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5> VoidStaticMethodRef5<A1, A2, A3, A4, A5> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declaredHelper(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, p4, p5, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` helper method of 6 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, R, A1, A2, A3, A4, A5, A6> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declaredHelper(UnsafeInvocables.method(self(), name, result, p1, p2, p3, p4, p5, p6, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `void` helper method of 6 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5, A6> VoidMethodRef6<Self, A1, A2, A3, A4, A5, A6> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declaredHelper(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, p4, p5, p6, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `static` helper method of 6 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5, A6> StaticMethodRef6<R, A1, A2, A3, A4, A5, A6> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declaredHelper(UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, p4, p5, p6, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` `static` `void` helper method of 6 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5, A6> VoidStaticMethodRef6<A1, A2, A3, A4, A5, A6> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declaredHelper(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, p4, p5, p6, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` helper method of 7 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A7> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declaredHelper(UnsafeInvocables.method(self(), name, result, p1, p2, p3, p4, p5, p6, p7, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `void` helper method of 7 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A7> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declaredHelper(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, p4, p5, p6, p7, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `static` helper method of 7 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7> StaticMethodRef7<R, A1, A2, A3, A4, A5, A6, A7> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declaredHelper(UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, p4, p5, p6, p7, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` `static` `void` helper method of 7 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> VoidStaticMethodRef7<A1, A2, A3, A4, A5, A6, A7> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declaredHelper(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, p4, p5, p6, p7, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` helper method of 8 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declaredHelper(UnsafeInvocables.method(self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `void` helper method of 8 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declaredHelper(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, p4, p5, p6, p7, p8, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `static` helper method of 8 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8> StaticMethodRef8<R, A1, A2, A3, A4, A5, A6, A7, A8> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declaredHelper(UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` `static` `void` helper method of 8 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidStaticMethodRef8<A1, A2, A3, A4, A5, A6, A7, A8> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declaredHelper(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, p4, p5, p6, p7, p8, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` helper method of 9 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declaredHelper(UnsafeInvocables.method(self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `void` helper method of 9 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declaredHelper(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `static` helper method of 9 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> StaticMethodRef9<R, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declaredHelper(UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` `static` `void` helper method of 9 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidStaticMethodRef9<A1, A2, A3, A4, A5, A6, A7, A8, A9> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declaredHelper(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` helper method of 10 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @param <A10> the parameter type of the tenth parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declaredHelper(UnsafeInvocables.method(self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `void` helper method of 10 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @param <A10> the parameter type of the tenth parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declaredHelper(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `static` helper method of 10 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @param <A10> the parameter type of the tenth parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> StaticMethodRef10<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declaredHelper(UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` `static` `void` helper method of 10 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @param <A10> the parameter type of the tenth parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidStaticMethodRef10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declaredHelper(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` helper method of 11 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @param <A10> the parameter type of the tenth parameter
     * @param <A11> the parameter type of the eleventh parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declaredHelper(UnsafeInvocables.method(self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `void` helper method of 11 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @param <A10> the parameter type of the tenth parameter
     * @param <A11> the parameter type of the eleventh parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declaredHelper(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `static` helper method of 11 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @param <A10> the parameter type of the tenth parameter
     * @param <A11> the parameter type of the eleventh parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> StaticMethodRef11<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declaredHelper(UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` `static` `void` helper method of 11 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @param <A10> the parameter type of the tenth parameter
     * @param <A11> the parameter type of the eleventh parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidStaticMethodRef11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declaredHelper(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` helper method of 12 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @param <A10> the parameter type of the tenth parameter
     * @param <A11> the parameter type of the eleventh parameter
     * @param <A12> the parameter type of the twelfth parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declaredHelper(UnsafeInvocables.method(self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `void` helper method of 12 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @param <A10> the parameter type of the tenth parameter
     * @param <A11> the parameter type of the eleventh parameter
     * @param <A12> the parameter type of the twelfth parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declaredHelper(UnsafeInvocables.voidMethod(self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, MemberTraits.DEFAULT), Kind.METHOD);
    }

    /**
     * Declares a `public` `static` helper method of 12 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @param <A10> the parameter type of the tenth parameter
     * @param <A11> the parameter type of the eleventh parameter
     * @param <A12> the parameter type of the twelfth parameter
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> StaticMethodRef12<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declaredHelper(UnsafeInvocables.staticMethod(self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Declares a `public` `static` `void` helper method of 12 parameters of the constant's body, which [#define] gives its body.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A1> the parameter type of the first parameter
     * @param <A2> the parameter type of the second parameter
     * @param <A3> the parameter type of the third parameter
     * @param <A4> the parameter type of the fourth parameter
     * @param <A5> the parameter type of the fifth parameter
     * @param <A6> the parameter type of the sixth parameter
     * @param <A7> the parameter type of the seventh parameter
     * @param <A8> the parameter type of the eighth parameter
     * @param <A9> the parameter type of the ninth parameter
     * @param <A10> the parameter type of the tenth parameter
     * @param <A11> the parameter type of the eleventh parameter
     * @param <A12> the parameter type of the twelfth parameter
     * @return the fact of the method
     * @throws IllegalArgumentException if the method has the signature of a method the constant overrides
     * @throws IllegalStateException if the declarations of the body have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidStaticMethodRef12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declaredHelper(UnsafeInvocables.voidStaticMethod(self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, MemberTraits.FINAL), Kind.STATIC);
    }

    /**
     * Defines the declared helper method of 5 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5> void define(MethodRef5<Self, R, A1, A2, A3, A4, A5> method, InstanceBody5<Self, Body<R>, R, A1, A2, A3, A4, A5> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5));
    }

    /**
     * Defines the declared `void` helper method of 5 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5> void define(VoidMethodRef5<Self, A1, A2, A3, A4, A5> method, InstanceBody5<Self, VoidBody, Void, A1, A2, A3, A4, A5> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5));
    }

    /**
     * Defines the declared static helper method of 5 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5> void define(StaticMethodRef5<R, A1, A2, A3, A4, A5> method, Function6<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5));
    }

    /**
     * Defines the declared static `void` helper method of 5 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5> void define(VoidStaticMethodRef5<A1, A2, A3, A4, A5> method, Function6<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5));
    }

    /**
     * Defines the declared helper method of 6 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5, A6> void define(MethodRef6<Self, R, A1, A2, A3, A4, A5, A6> method, InstanceBody6<Self, Body<R>, R, A1, A2, A3, A4, A5, A6> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6));
    }

    /**
     * Defines the declared `void` helper method of 6 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5, A6> void define(VoidMethodRef6<Self, A1, A2, A3, A4, A5, A6> method, InstanceBody6<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6));
    }

    /**
     * Defines the declared static helper method of 6 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5, A6> void define(StaticMethodRef6<R, A1, A2, A3, A4, A5, A6> method, Function7<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5, p6));
    }

    /**
     * Defines the declared static `void` helper method of 6 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5, A6> void define(VoidStaticMethodRef6<A1, A2, A3, A4, A5, A6> method, Function7<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5, p6));
    }

    /**
     * Defines the declared helper method of 7 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7> void define(MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A7> method, InstanceBody7<Self, Body<R>, R, A1, A2, A3, A4, A5, A6, A7> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * Defines the declared `void` helper method of 7 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5, A6, A7> void define(VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A7> method, InstanceBody7<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * Defines the declared static helper method of 7 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7> void define(StaticMethodRef7<R, A1, A2, A3, A4, A5, A6, A7> method, Function8<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * Defines the declared static `void` helper method of 7 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5, A6, A7> void define(VoidStaticMethodRef7<A1, A2, A3, A4, A5, A6, A7> method, Function8<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7));
    }

    /**
     * Defines the declared helper method of 8 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8> void define(MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> method, InstanceBody8<Self, Body<R>, R, A1, A2, A3, A4, A5, A6, A7, A8> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * Defines the declared `void` helper method of 8 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8> void define(VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> method, InstanceBody8<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * Defines the declared static helper method of 8 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8> void define(StaticMethodRef8<R, A1, A2, A3, A4, A5, A6, A7, A8> method, Function9<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * Defines the declared static `void` helper method of 8 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8> void define(VoidStaticMethodRef8<A1, A2, A3, A4, A5, A6, A7, A8> method, Function9<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8));
    }

    /**
     * Defines the declared helper method of 9 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> void define(MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, InstanceBody9<Self, Body<R>, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * Defines the declared `void` helper method of 9 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9> void define(VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, InstanceBody9<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * Defines the declared static helper method of 9 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> void define(StaticMethodRef9<R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Function10<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * Defines the declared static `void` helper method of 9 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9> void define(VoidStaticMethodRef9<A1, A2, A3, A4, A5, A6, A7, A8, A9> method, Function10<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }

    /**
     * Defines the declared helper method of 10 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @param <A10> the type parameter of the tenth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> void define(MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, InstanceBody10<Self, Body<R>, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        Var<A10> p10 = lastParameter(method.param10(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    /**
     * Defines the declared `void` helper method of 10 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @param <A10> the type parameter of the tenth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> void define(VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, InstanceBody10<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        Var<A10> p10 = lastParameter(method.param10(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    /**
     * Defines the declared static helper method of 10 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @param <A10> the type parameter of the tenth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> void define(StaticMethodRef10<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Function11<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Var<A10>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        Var<A10> p10 = lastParameter(method.param10(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    /**
     * Defines the declared static `void` helper method of 10 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @param <A10> the type parameter of the tenth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> void define(VoidStaticMethodRef10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method, Function11<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Var<A10>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        Var<A10> p10 = lastParameter(method.param10(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
    }

    /**
     * Defines the declared helper method of 11 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @param <A10> the type parameter of the tenth parameter
     * @param <A11> the type parameter of the eleventh parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> void define(MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, InstanceBody11<Self, Body<R>, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        Var<A10> p10 = lastParameter(method.param10(), root);
        Var<A11> p11 = lastParameter(method.param11(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
    }

    /**
     * Defines the declared `void` helper method of 11 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @param <A10> the type parameter of the tenth parameter
     * @param <A11> the type parameter of the eleventh parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> void define(VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, InstanceBody11<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        Var<A10> p10 = lastParameter(method.param10(), root);
        Var<A11> p11 = lastParameter(method.param11(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
    }

    /**
     * Defines the declared static helper method of 11 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @param <A10> the type parameter of the tenth parameter
     * @param <A11> the type parameter of the eleventh parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> void define(StaticMethodRef11<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Function12<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Var<A10>, Var<A11>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        Var<A10> p10 = lastParameter(method.param10(), root);
        Var<A11> p11 = lastParameter(method.param11(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
    }

    /**
     * Defines the declared static `void` helper method of 11 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @param <A10> the type parameter of the tenth parameter
     * @param <A11> the type parameter of the eleventh parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> void define(VoidStaticMethodRef11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> method, Function12<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Var<A10>, Var<A11>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        Var<A10> p10 = lastParameter(method.param10(), root);
        Var<A11> p11 = lastParameter(method.param11(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
    }

    /**
     * Defines the declared helper method of 12 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @param <A10> the type parameter of the tenth parameter
     * @param <A11> the type parameter of the eleventh parameter
     * @param <A12> the type parameter of the twelfth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> void define(MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, InstanceBody12<Self, Body<R>, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        Var<A10> p10 = lastParameter(method.param10(), root);
        Var<A11> p11 = lastParameter(method.param11(), root);
        Var<A12> p12 = lastParameter(method.param12(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    }

    /**
     * Defines the declared `void` helper method of 12 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block, `this` and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @param <A10> the type parameter of the tenth parameter
     * @param <A11> the type parameter of the eleventh parameter
     * @param <A12> the type parameter of the twelfth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> void define(VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, InstanceBody12<Self, VoidBody, Void, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        This<Self> self = thisOf(root);
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        Var<A10> p10 = lastParameter(method.param10(), root);
        Var<A11> p11 = lastParameter(method.param11(), root);
        Var<A12> p12 = lastParameter(method.param12(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), Start.IMPLICIT, (b) -> body.build(b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    }

    /**
     * Defines the declared static helper method of 12 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @param <A10> the type parameter of the tenth parameter
     * @param <A11> the type parameter of the eleventh parameter
     * @param <A12> the type parameter of the twelfth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> void define(StaticMethodRef12<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Function13<Body<R>, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Var<A10>, Var<A11>, Var<A12>, Terminated<R>> body) {
        Awaiting slot = definable(method);
        Body<R> root = Body.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        Var<A10> p10 = lastParameter(method.param10(), root);
        Var<A11> p11 = lastParameter(method.param11(), root);
        Var<A12> p12 = lastParameter(method.param12(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    }

    /**
     * Defines the declared static `void` helper method of 12 parameters of the constant's body.
     *
     * @param method the declared method
     * @param body the body, given its block and the parameters
     * @param p1 the first parameter type
     * @param p2 the second parameter type
     * @param p3 the third parameter type
     * @param p4 the fourth parameter type
     * @param p5 the fifth parameter type
     * @param p6 the sixth parameter type
     * @param p7 the seventh parameter type
     * @param p8 the eighth parameter type
     * @param p9 the ninth parameter type
     * @param p10 the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A1> the type parameter of the first parameter
     * @param <A2> the type parameter of the second parameter
     * @param <A3> the type parameter of the third parameter
     * @param <A4> the type parameter of the fourth parameter
     * @param <A5> the type parameter of the fifth parameter
     * @param <A6> the type parameter of the sixth parameter
     * @param <A7> the type parameter of the seventh parameter
     * @param <A8> the type parameter of the eighth parameter
     * @param <A9> the type parameter of the ninth parameter
     * @param <A10> the type parameter of the tenth parameter
     * @param <A11> the type parameter of the eleventh parameter
     * @param <A12> the type parameter of the twelfth parameter
     * @throws IllegalStateException as a definition of a method of a class does ([MemberBodies])
     */
    public final <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> void define(VoidStaticMethodRef12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method, Function13<VoidBody, Var<A1>, Var<A2>, Var<A3>, Var<A4>, Var<A5>, Var<A6>, Var<A7>, Var<A8>, Var<A9>, Var<A10>, Var<A11>, Var<A12>, Terminated<Void>> body) {
        Awaiting slot = definable(method);
        VoidBody root = VoidBody.root("body of method " + method.name(), MemberBodies.declares(method));
        Var<A1> p1 = lastParameter(method.param1(), root);
        Var<A2> p2 = lastParameter(method.param2(), root);
        Var<A3> p3 = lastParameter(method.param3(), root);
        Var<A4> p4 = lastParameter(method.param4(), root);
        Var<A5> p5 = lastParameter(method.param5(), root);
        Var<A6> p6 = lastParameter(method.param6(), root);
        Var<A7> p7 = lastParameter(method.param7(), root);
        Var<A8> p8 = lastParameter(method.param8(), root);
        Var<A9> p9 = lastParameter(method.param9(), root);
        Var<A10> p10 = lastParameter(method.param10(), root);
        Var<A11> p11 = lastParameter(method.param11(), root);
        Var<A12> p12 = lastParameter(method.param12(), root);
        defineIn(slot, root, List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), Start.IMPLICIT, (b) -> body.apply(b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    }
}
