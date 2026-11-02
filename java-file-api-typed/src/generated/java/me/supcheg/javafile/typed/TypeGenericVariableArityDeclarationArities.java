package me.supcheg.javafile.typed;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef10;
import me.supcheg.javafile.facts.MethodRef11;
import me.supcheg.javafile.facts.MethodRef12;
import me.supcheg.javafile.facts.MethodRef13;
import me.supcheg.javafile.facts.MethodRef14;
import me.supcheg.javafile.facts.MethodRef15;
import me.supcheg.javafile.facts.MethodRef16;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MethodRef4;
import me.supcheg.javafile.facts.MethodRef5;
import me.supcheg.javafile.facts.MethodRef6;
import me.supcheg.javafile.facts.MethodRef7;
import me.supcheg.javafile.facts.MethodRef8;
import me.supcheg.javafile.facts.MethodRef9;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef10;
import me.supcheg.javafile.facts.StaticMethodRef11;
import me.supcheg.javafile.facts.StaticMethodRef12;
import me.supcheg.javafile.facts.StaticMethodRef13;
import me.supcheg.javafile.facts.StaticMethodRef14;
import me.supcheg.javafile.facts.StaticMethodRef15;
import me.supcheg.javafile.facts.StaticMethodRef16;
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
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef10;
import me.supcheg.javafile.facts.VoidMethodRef11;
import me.supcheg.javafile.facts.VoidMethodRef12;
import me.supcheg.javafile.facts.VoidMethodRef13;
import me.supcheg.javafile.facts.VoidMethodRef14;
import me.supcheg.javafile.facts.VoidMethodRef15;
import me.supcheg.javafile.facts.VoidMethodRef16;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidMethodRef3;
import me.supcheg.javafile.facts.VoidMethodRef4;
import me.supcheg.javafile.facts.VoidMethodRef5;
import me.supcheg.javafile.facts.VoidMethodRef6;
import me.supcheg.javafile.facts.VoidMethodRef7;
import me.supcheg.javafile.facts.VoidMethodRef8;
import me.supcheg.javafile.facts.VoidMethodRef9;
import me.supcheg.javafile.facts.VoidStaticMethodRef1;
import me.supcheg.javafile.facts.VoidStaticMethodRef10;
import me.supcheg.javafile.facts.VoidStaticMethodRef11;
import me.supcheg.javafile.facts.VoidStaticMethodRef12;
import me.supcheg.javafile.facts.VoidStaticMethodRef13;
import me.supcheg.javafile.facts.VoidStaticMethodRef14;
import me.supcheg.javafile.facts.VoidStaticMethodRef15;
import me.supcheg.javafile.facts.VoidStaticMethodRef16;
import me.supcheg.javafile.facts.VoidStaticMethodRef2;
import me.supcheg.javafile.facts.VoidStaticMethodRef3;
import me.supcheg.javafile.facts.VoidStaticMethodRef4;
import me.supcheg.javafile.facts.VoidStaticMethodRef5;
import me.supcheg.javafile.facts.VoidStaticMethodRef6;
import me.supcheg.javafile.facts.VoidStaticMethodRef7;
import me.supcheg.javafile.facts.VoidStaticMethodRef8;
import me.supcheg.javafile.facts.VoidStaticMethodRef9;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member.Kind;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class TypeGenericVariableArityDeclarationArities<Self> permits TypeGenericVariableArityDeclarations {
    abstract TypeMemberDeclarations<Self> declarations();

    abstract <F extends Invocable> F declared(F member, Kind kind);

    abstract MemberTraits traits(MemberTraits traits, ArrayToken<?, ?> last);

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A> MethodRef1<Self, R, A> declareMethod(String name, TypeToken<R> result, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A> MethodRef2<Self, R, A1, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A> MethodRef3<Self, R, A1, A2, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A> MethodRef4<Self, R, A1, A2, A3, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A> MethodRef5<Self, R, A1, A2, A3, A4, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A> MethodRef6<Self, R, A1, A2, A3, A4, A5, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A> MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A> MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A> MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> MethodRef13<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> MethodRef14<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> MethodRef15<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @param p15 the fifteenth parameter type
     * @param <A15> the fifteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> MethodRef16<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A> VoidMethodRef1<Self, A> declareVoidMethod(String name, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A> VoidMethodRef2<Self, A1, A> declareVoidMethod(String name, TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A> VoidMethodRef3<Self, A1, A2, A> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A> VoidMethodRef4<Self, A1, A2, A3, A> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A> VoidMethodRef5<Self, A1, A2, A3, A4, A> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A> VoidMethodRef6<Self, A1, A2, A3, A4, A5, A> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A> VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A> VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A> VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A> VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> VoidMethodRef13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> VoidMethodRef14<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> VoidMethodRef15<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @param p15 the fifteenth parameter type
     * @param <A15> the fifteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> VoidMethodRef16<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, last, traits(MemberTraits.FINAL, last)), Kind.METHOD);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A> MethodRef1<Self, R, A> declareAbstractMethod(String name, TypeToken<R> result, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A> MethodRef2<Self, R, A1, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A> MethodRef3<Self, R, A1, A2, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A> MethodRef4<Self, R, A1, A2, A3, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A> MethodRef5<Self, R, A1, A2, A3, A4, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A> MethodRef6<Self, R, A1, A2, A3, A4, A5, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A> MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A> MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A> MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> MethodRef13<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> MethodRef14<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> MethodRef15<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @param p15 the fifteenth parameter type
     * @param <A15> the fifteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> MethodRef16<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A> VoidMethodRef1<Self, A> declareVoidAbstractMethod(String name, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A> VoidMethodRef2<Self, A1, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A> VoidMethodRef3<Self, A1, A2, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A> VoidMethodRef4<Self, A1, A2, A3, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A> VoidMethodRef5<Self, A1, A2, A3, A4, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A> VoidMethodRef6<Self, A1, A2, A3, A4, A5, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A> VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A> VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A> VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A> VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> VoidMethodRef13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> VoidMethodRef14<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> VoidMethodRef15<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @param p15 the fifteenth parameter type
     * @param <A15> the fifteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> VoidMethodRef16<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, last, traits(MemberTraits.ABSTRACT, last)), Kind.ABSTRACT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A> MethodRef1<Self, R, A> declareDefaultMethod(String name, TypeToken<R> result, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A> MethodRef2<Self, R, A1, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A> MethodRef3<Self, R, A1, A2, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A> MethodRef4<Self, R, A1, A2, A3, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A> MethodRef5<Self, R, A1, A2, A3, A4, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A> MethodRef6<Self, R, A1, A2, A3, A4, A5, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A> MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A> MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A> MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> MethodRef13<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> MethodRef14<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> MethodRef15<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @param p15 the fifteenth parameter type
     * @param <A15> the fifteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> MethodRef16<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A> VoidMethodRef1<Self, A> declareVoidDefaultMethod(String name, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A> VoidMethodRef2<Self, A1, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A> VoidMethodRef3<Self, A1, A2, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A> VoidMethodRef4<Self, A1, A2, A3, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A> VoidMethodRef5<Self, A1, A2, A3, A4, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A> VoidMethodRef6<Self, A1, A2, A3, A4, A5, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A> VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A> VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A> VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A> VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> VoidMethodRef13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> VoidMethodRef14<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> VoidMethodRef15<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @param p15 the fifteenth parameter type
     * @param <A15> the fifteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> VoidMethodRef16<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, last, traits(MemberTraits.DEFAULT, last)), Kind.DEFAULT);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A> StaticMethodRef1<R, A> declareStaticMethod(String name, TypeToken<R> result, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A> StaticMethodRef2<R, A1, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A> StaticMethodRef3<R, A1, A2, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A> StaticMethodRef4<R, A1, A2, A3, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A> StaticMethodRef5<R, A1, A2, A3, A4, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A> StaticMethodRef6<R, A1, A2, A3, A4, A5, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A> StaticMethodRef7<R, A1, A2, A3, A4, A5, A6, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A> StaticMethodRef8<R, A1, A2, A3, A4, A5, A6, A7, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A> StaticMethodRef9<R, A1, A2, A3, A4, A5, A6, A7, A8, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A> StaticMethodRef10<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> StaticMethodRef11<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> StaticMethodRef12<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> StaticMethodRef13<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> StaticMethodRef14<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> StaticMethodRef15<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @param p15 the fifteenth parameter type
     * @param <A15> the fifteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> StaticMethodRef16<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A> VoidStaticMethodRef1<A> declareVoidStaticMethod(String name, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A> VoidStaticMethodRef2<A1, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A> VoidStaticMethodRef3<A1, A2, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A> VoidStaticMethodRef4<A1, A2, A3, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A> VoidStaticMethodRef5<A1, A2, A3, A4, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A> VoidStaticMethodRef6<A1, A2, A3, A4, A5, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A> VoidStaticMethodRef7<A1, A2, A3, A4, A5, A6, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A> VoidStaticMethodRef8<A1, A2, A3, A4, A5, A6, A7, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A> VoidStaticMethodRef9<A1, A2, A3, A4, A5, A6, A7, A8, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A> VoidStaticMethodRef10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> VoidStaticMethodRef11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> VoidStaticMethodRef12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> VoidStaticMethodRef13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> VoidStaticMethodRef14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> VoidStaticMethodRef15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @param p15 the fifteenth parameter type
     * @param <A15> the fifteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> VoidStaticMethodRef16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, last, traits(MemberTraits.FINAL, last)), Kind.STATIC);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A> MethodRef1<Self, R, A> declarePrivateMethod(String name, TypeToken<R> result, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A> MethodRef2<Self, R, A1, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A> MethodRef3<Self, R, A1, A2, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A> MethodRef4<Self, R, A1, A2, A3, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A> MethodRef5<Self, R, A1, A2, A3, A4, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A> MethodRef6<Self, R, A1, A2, A3, A4, A5, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A> MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A> MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A> MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> MethodRef13<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> MethodRef14<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> MethodRef15<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @param p15 the fifteenth parameter type
     * @param <A15> the fifteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> MethodRef16<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A> VoidMethodRef1<Self, A> declareVoidPrivateMethod(String name, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A> VoidMethodRef2<Self, A1, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A> VoidMethodRef3<Self, A1, A2, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A> VoidMethodRef4<Self, A1, A2, A3, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A> VoidMethodRef5<Self, A1, A2, A3, A4, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A> VoidMethodRef6<Self, A1, A2, A3, A4, A5, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A> VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A> VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A> VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A> VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> VoidMethodRef13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> VoidMethodRef14<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> VoidMethodRef15<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param last the array type of the last parameter, which is of variable arity
     * @param <A> the array type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @param p5 the fifth parameter type
     * @param <A5> the fifth parameter type
     * @param p6 the sixth parameter type
     * @param <A6> the sixth parameter type
     * @param p7 the seventh parameter type
     * @param <A7> the seventh parameter type
     * @param p8 the eighth parameter type
     * @param <A8> the eighth parameter type
     * @param p9 the ninth parameter type
     * @param <A9> the ninth parameter type
     * @param p10 the tenth parameter type
     * @param <A10> the tenth parameter type
     * @param p11 the eleventh parameter type
     * @param <A11> the eleventh parameter type
     * @param p12 the twelfth parameter type
     * @param <A12> the twelfth parameter type
     * @param p13 the thirteenth parameter type
     * @param <A13> the thirteenth parameter type
     * @param p14 the fourteenth parameter type
     * @param <A14> the fourteenth parameter type
     * @param p15 the fifteenth parameter type
     * @param <A15> the fifteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the elements of the last parameter cannot be vouched for by `@SafeVarargs` where the member cannot carry it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> VoidMethodRef16<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, ArrayToken<A, ?> last) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, last, traits(MemberTraits.FINAL, last)), Kind.PRIVATE);
    }
}
