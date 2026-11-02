package me.supcheg.javafile.typed;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
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
import me.supcheg.javafile.facts.MethodTypeParameter;
import me.supcheg.javafile.facts.StaticMethodRef0;
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
import me.supcheg.javafile.facts.VoidMethodRef0;
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
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
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
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member.Kind;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class TypeGenericMethodDeclarationArities<Self> permits TypeGenericDeclarations {
    abstract TypeMemberDeclarations<Self> declarations();

    abstract List<ClassToken<? extends Throwable>> thrown();

    abstract List<AnnotationRef<?>> annotations();

    abstract List<List<AnnotationRef<?>>> parameterAnnotations();

    abstract Optional<DocComment> doc();

    abstract Set<Modifier> modifiers();

    abstract List<MethodTypeParameter> typeParams();

    abstract MemberTraits traits(MemberTraits traits);

    abstract <F extends Invocable> F declared(F member, Kind kind);

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R> MethodRef0<Self, R> declareMethod(String name, TypeToken<R> result) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1> MethodRef1<Self, R, A1> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5> MethodRef5<Self, R, A1, A2, A3, A4, A5> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, R, A1, A2, A3, A4, A5, A6> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A7> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> MethodRef13<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> MethodRef14<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> MethodRef15<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @param p16 the sixteenth parameter type
     * @param <A16> the sixteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> MethodRef16<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> declareMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, TypeToken<A16> p16) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public VoidMethodRef0<Self> declareVoidMethod(String name) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1> VoidMethodRef1<Self, A1> declareVoidMethod(String name, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5> VoidMethodRef5<Self, A1, A2, A3, A4, A5> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6> VoidMethodRef6<Self, A1, A2, A3, A4, A5, A6> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A7> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> VoidMethodRef13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> VoidMethodRef14<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> VoidMethodRef15<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @param p16 the sixteenth parameter type
     * @param <A16> the sixteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> VoidMethodRef16<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> declareVoidMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, TypeToken<A16> p16) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, traits(MemberTraits.FINAL)), Kind.METHOD);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R> MethodRef0<Self, R> declareAbstractMethod(String name, TypeToken<R> result) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1> MethodRef1<Self, R, A1> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5> MethodRef5<Self, R, A1, A2, A3, A4, A5> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, R, A1, A2, A3, A4, A5, A6> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A7> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> MethodRef13<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> MethodRef14<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> MethodRef15<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @param p16 the sixteenth parameter type
     * @param <A16> the sixteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> MethodRef16<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> declareAbstractMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, TypeToken<A16> p16) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public VoidMethodRef0<Self> declareVoidAbstractMethod(String name) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1> VoidMethodRef1<Self, A1> declareVoidAbstractMethod(String name, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5> VoidMethodRef5<Self, A1, A2, A3, A4, A5> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6> VoidMethodRef6<Self, A1, A2, A3, A4, A5, A6> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A7> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> VoidMethodRef13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> VoidMethodRef14<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> VoidMethodRef15<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `public abstract void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @param p16 the sixteenth parameter type
     * @param <A16> the sixteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> VoidMethodRef16<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> declareVoidAbstractMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, TypeToken<A16> p16) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, traits(MemberTraits.ABSTRACT)), Kind.ABSTRACT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R> MethodRef0<Self, R> declareDefaultMethod(String name, TypeToken<R> result) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1> MethodRef1<Self, R, A1> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5> MethodRef5<Self, R, A1, A2, A3, A4, A5> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, R, A1, A2, A3, A4, A5, A6> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A7> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> MethodRef13<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> MethodRef14<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> MethodRef15<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @param p16 the sixteenth parameter type
     * @param <A16> the sixteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> MethodRef16<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> declareDefaultMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, TypeToken<A16> p16) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public VoidMethodRef0<Self> declareVoidDefaultMethod(String name) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1> VoidMethodRef1<Self, A1> declareVoidDefaultMethod(String name, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5> VoidMethodRef5<Self, A1, A2, A3, A4, A5> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6> VoidMethodRef6<Self, A1, A2, A3, A4, A5, A6> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A7> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> VoidMethodRef13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> VoidMethodRef14<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> VoidMethodRef15<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `default void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @param p16 the sixteenth parameter type
     * @param <A16> the sixteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> VoidMethodRef16<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> declareVoidDefaultMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, TypeToken<A16> p16) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, traits(MemberTraits.DEFAULT)), Kind.DEFAULT);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R> StaticMethodRef0<R> declareStaticMethod(String name, TypeToken<R> result) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1> StaticMethodRef1<R, A1> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2> StaticMethodRef2<R, A1, A2> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3> StaticMethodRef3<R, A1, A2, A3> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4> StaticMethodRef4<R, A1, A2, A3, A4> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5> StaticMethodRef5<R, A1, A2, A3, A4, A5> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6> StaticMethodRef6<R, A1, A2, A3, A4, A5, A6> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7> StaticMethodRef7<R, A1, A2, A3, A4, A5, A6, A7> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8> StaticMethodRef8<R, A1, A2, A3, A4, A5, A6, A7, A8> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> StaticMethodRef9<R, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> StaticMethodRef10<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> StaticMethodRef11<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> StaticMethodRef12<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> StaticMethodRef13<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> StaticMethodRef14<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> StaticMethodRef15<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @param p16 the sixteenth parameter type
     * @param <A16> the sixteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> StaticMethodRef16<R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> declareStaticMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, TypeToken<A16> p16) {
        return declared(UnsafeInvocables.staticMethod(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public VoidStaticMethodRef0 declareVoidStaticMethod(String name) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1> VoidStaticMethodRef1<A1> declareVoidStaticMethod(String name, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2> VoidStaticMethodRef2<A1, A2> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3> VoidStaticMethodRef3<A1, A2, A3> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4> VoidStaticMethodRef4<A1, A2, A3, A4> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5> VoidStaticMethodRef5<A1, A2, A3, A4, A5> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6> VoidStaticMethodRef6<A1, A2, A3, A4, A5, A6> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> VoidStaticMethodRef7<A1, A2, A3, A4, A5, A6, A7> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidStaticMethodRef8<A1, A2, A3, A4, A5, A6, A7, A8> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidStaticMethodRef9<A1, A2, A3, A4, A5, A6, A7, A8, A9> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidStaticMethodRef10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidStaticMethodRef11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidStaticMethodRef12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> VoidStaticMethodRef13<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> VoidStaticMethodRef14<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> VoidStaticMethodRef15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `public static void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @param p16 the sixteenth parameter type
     * @param <A16> the sixteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> VoidStaticMethodRef16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> declareVoidStaticMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, TypeToken<A16> p16) {
        return declared(UnsafeInvocables.voidStaticMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, traits(MemberTraits.FINAL)), Kind.STATIC);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R> MethodRef0<Self, R> declarePrivateMethod(String name, TypeToken<R> result) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1> MethodRef1<Self, R, A1> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2> MethodRef2<Self, R, A1, A2> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3> MethodRef3<Self, R, A1, A2, A3> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4> MethodRef4<Self, R, A1, A2, A3, A4> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5> MethodRef5<Self, R, A1, A2, A3, A4, A5> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6> MethodRef6<Self, R, A1, A2, A3, A4, A5, A6> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7> MethodRef7<Self, R, A1, A2, A3, A4, A5, A6, A7> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8> MethodRef8<Self, R, A1, A2, A3, A4, A5, A6, A7, A8> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9> MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> MethodRef10<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> MethodRef11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> MethodRef13<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> MethodRef14<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> MethodRef15<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private` method returning `R` with the type parameters of this member.
     *
     * @param name the method name
     * @param result the result type
     * @param <R> the result type
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
     * @param p16 the sixteenth parameter type
     * @param <A16> the sixteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> MethodRef16<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> declarePrivateMethod(String name, TypeToken<R> result, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, TypeToken<A16> p16) {
        return declared(UnsafeInvocables.method(declarations().self(), name, result, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public VoidMethodRef0<Self> declareVoidPrivateMethod(String name) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the parameter type
     * @param <A1> the parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1> VoidMethodRef1<Self, A1> declareVoidPrivateMethod(String name, TypeToken<A1> p1) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2> VoidMethodRef2<Self, A1, A2> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3> VoidMethodRef3<Self, A1, A2, A3> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
     * @param p1 the first parameter type
     * @param <A1> the first parameter type
     * @param p2 the second parameter type
     * @param <A2> the second parameter type
     * @param p3 the third parameter type
     * @param <A3> the third parameter type
     * @param p4 the fourth parameter type
     * @param <A4> the fourth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4> VoidMethodRef4<Self, A1, A2, A3, A4> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5> VoidMethodRef5<Self, A1, A2, A3, A4, A5> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6> VoidMethodRef6<Self, A1, A2, A3, A4, A5, A6> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> VoidMethodRef7<Self, A1, A2, A3, A4, A5, A6, A7> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> VoidMethodRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> VoidMethodRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> VoidMethodRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidMethodRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> VoidMethodRef13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> VoidMethodRef14<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> VoidMethodRef15<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }

    /**
     * Declares a `private void` method with the type parameters of this member.
     *
     * @param name the method name
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
     * @param p16 the sixteenth parameter type
     * @param <A16> the sixteenth parameter type
     * @return the fact of the method
     * @throws IllegalArgumentException if javac would reject the method (see [TypeSignatureRegistry]), or the modifiers the stage gives it cannot be added to it
     * @throws IllegalStateException if the declarations of the type have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> VoidMethodRef16<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> declareVoidPrivateMethod(String name, TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12, TypeToken<A13> p13, TypeToken<A14> p14, TypeToken<A15> p15, TypeToken<A16> p16) {
        return declared(UnsafeInvocables.voidMethod(declarations().self(), name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, traits(MemberTraits.FINAL)), Kind.PRIVATE);
    }
}
