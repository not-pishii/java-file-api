package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.CtorRef10;
import me.supcheg.javafile.facts.CtorRef11;
import me.supcheg.javafile.facts.CtorRef12;
import me.supcheg.javafile.facts.CtorRef2;
import me.supcheg.javafile.facts.CtorRef3;
import me.supcheg.javafile.facts.CtorRef4;
import me.supcheg.javafile.facts.CtorRef5;
import me.supcheg.javafile.facts.CtorRef6;
import me.supcheg.javafile.facts.CtorRef7;
import me.supcheg.javafile.facts.CtorRef8;
import me.supcheg.javafile.facts.CtorRef9;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.TargetClasspath;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.facts.UnsafeInvocables;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
abstract sealed class RecordConstructorArities<Self> extends StaticFieldDeclarations<Self> permits TypedRecordBuilder {
    RecordConstructorArities(TargetClasspath target, ClassDesc desc, String kind) {
        super(target, desc, kind);
    }

    abstract FinalClassToken<Self> finalSelf();

    abstract <F extends Invocable> F declareCompact(F ctor);

    abstract <F extends Invocable> F declareCanonical(F ctor);

    abstract <F extends Invocable> F declareAdditional(F ctor);

    /**
     * Declares the compact constructor of the record, to be defined by the bodies of the record.
     *
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public CtorRef0<Self> declareCompactConstructor() {
        return declareCompact(UnsafeInvocables.ctor(finalSelf(), MemberTraits.FINAL));
    }

    /**
     * Declares the canonical constructor of the record, to be defined by the bodies of the record.
     *
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public CtorRef0<Self> declareCanonicalConstructor() {
        return declareCanonical(UnsafeInvocables.ctor(finalSelf(), MemberTraits.FINAL));
    }

    /**
     * Declares an additional constructor of the record, to be defined by the bodies of the record.
     *
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public CtorRef0<Self> declareConstructor() {
        return declareAdditional(UnsafeInvocables.ctor(finalSelf(), MemberTraits.FINAL));
    }

    /**
     * Declares the compact constructor of the record, to be defined by the bodies of the record.
     *
     * @param p1 the parameter type
     * @param <A1> the type of the parameter
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1> CtorRef1<Self, A1> declareCompactConstructor(TypeToken<A1> p1) {
        return declareCompact(UnsafeInvocables.ctor(finalSelf(), p1, MemberTraits.FINAL));
    }

    /**
     * Declares the canonical constructor of the record, to be defined by the bodies of the record.
     *
     * @param p1 the parameter type
     * @param <A1> the type of the parameter
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1> CtorRef1<Self, A1> declareCanonicalConstructor(TypeToken<A1> p1) {
        return declareCanonical(UnsafeInvocables.ctor(finalSelf(), p1, MemberTraits.FINAL));
    }

    /**
     * Declares an additional constructor of the record, to be defined by the bodies of the record.
     *
     * @param p1 the parameter type
     * @param <A1> the type of the parameter
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1> CtorRef1<Self, A1> declareConstructor(TypeToken<A1> p1) {
        return declareAdditional(UnsafeInvocables.ctor(finalSelf(), p1, MemberTraits.FINAL));
    }

    /**
     * Declares the compact constructor of the record, to be defined by the bodies of the record.
     *
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2> CtorRef2<Self, A1, A2> declareCompactConstructor(TypeToken<A1> p1, TypeToken<A2> p2) {
        return declareCompact(UnsafeInvocables.ctor(finalSelf(), p1, p2, MemberTraits.FINAL));
    }

    /**
     * Declares the canonical constructor of the record, to be defined by the bodies of the record.
     *
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2> CtorRef2<Self, A1, A2> declareCanonicalConstructor(TypeToken<A1> p1, TypeToken<A2> p2) {
        return declareCanonical(UnsafeInvocables.ctor(finalSelf(), p1, p2, MemberTraits.FINAL));
    }

    /**
     * Declares an additional constructor of the record, to be defined by the bodies of the record.
     *
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2> CtorRef2<Self, A1, A2> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2) {
        return declareAdditional(UnsafeInvocables.ctor(finalSelf(), p1, p2, MemberTraits.FINAL));
    }

    /**
     * Declares the compact constructor of the record, to be defined by the bodies of the record.
     *
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3> CtorRef3<Self, A1, A2, A3> declareCompactConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declareCompact(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, MemberTraits.FINAL));
    }

    /**
     * Declares the canonical constructor of the record, to be defined by the bodies of the record.
     *
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3> CtorRef3<Self, A1, A2, A3> declareCanonicalConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declareCanonical(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, MemberTraits.FINAL));
    }

    /**
     * Declares an additional constructor of the record, to be defined by the bodies of the record.
     *
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3> CtorRef3<Self, A1, A2, A3> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3) {
        return declareAdditional(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, MemberTraits.FINAL));
    }

    /**
     * Declares the compact constructor of the record, to be defined by the bodies of the record.
     *
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4> CtorRef4<Self, A1, A2, A3, A4> declareCompactConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declareCompact(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, MemberTraits.FINAL));
    }

    /**
     * Declares the canonical constructor of the record, to be defined by the bodies of the record.
     *
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4> CtorRef4<Self, A1, A2, A3, A4> declareCanonicalConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declareCanonical(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, MemberTraits.FINAL));
    }

    /**
     * Declares an additional constructor of the record, to be defined by the bodies of the record.
     *
     * @param p1 the first parameter type
     * @param <A1> the type of the first parameter
     * @param p2 the second parameter type
     * @param <A2> the type of the second parameter
     * @param p3 the third parameter type
     * @param <A3> the type of the third parameter
     * @param p4 the fourth parameter type
     * @param <A4> the type of the fourth parameter
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4> CtorRef4<Self, A1, A2, A3, A4> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4) {
        return declareAdditional(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, MemberTraits.FINAL));
    }

    /**
     * Declares the compact constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5> CtorRef5<Self, A1, A2, A3, A4, A5> declareCompactConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declareCompact(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, MemberTraits.FINAL));
    }

    /**
     * Declares the canonical constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5> CtorRef5<Self, A1, A2, A3, A4, A5> declareCanonicalConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declareCanonical(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, MemberTraits.FINAL));
    }

    /**
     * Declares an additional constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5> CtorRef5<Self, A1, A2, A3, A4, A5> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5) {
        return declareAdditional(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, MemberTraits.FINAL));
    }

    /**
     * Declares the compact constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6> CtorRef6<Self, A1, A2, A3, A4, A5, A6> declareCompactConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declareCompact(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, MemberTraits.FINAL));
    }

    /**
     * Declares the canonical constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6> CtorRef6<Self, A1, A2, A3, A4, A5, A6> declareCanonicalConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declareCanonical(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, MemberTraits.FINAL));
    }

    /**
     * Declares an additional constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6> CtorRef6<Self, A1, A2, A3, A4, A5, A6> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6) {
        return declareAdditional(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, MemberTraits.FINAL));
    }

    /**
     * Declares the compact constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> CtorRef7<Self, A1, A2, A3, A4, A5, A6, A7> declareCompactConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declareCompact(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, MemberTraits.FINAL));
    }

    /**
     * Declares the canonical constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> CtorRef7<Self, A1, A2, A3, A4, A5, A6, A7> declareCanonicalConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declareCanonical(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, MemberTraits.FINAL));
    }

    /**
     * Declares an additional constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7> CtorRef7<Self, A1, A2, A3, A4, A5, A6, A7> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7) {
        return declareAdditional(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, MemberTraits.FINAL));
    }

    /**
     * Declares the compact constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> CtorRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> declareCompactConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declareCompact(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, MemberTraits.FINAL));
    }

    /**
     * Declares the canonical constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> CtorRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> declareCanonicalConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declareCanonical(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, MemberTraits.FINAL));
    }

    /**
     * Declares an additional constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8> CtorRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8) {
        return declareAdditional(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, MemberTraits.FINAL));
    }

    /**
     * Declares the compact constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> CtorRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareCompactConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declareCompact(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, p9, MemberTraits.FINAL));
    }

    /**
     * Declares the canonical constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> CtorRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareCanonicalConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declareCanonical(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, p9, MemberTraits.FINAL));
    }

    /**
     * Declares an additional constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9> CtorRef9<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9) {
        return declareAdditional(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, p9, MemberTraits.FINAL));
    }

    /**
     * Declares the compact constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> CtorRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareCompactConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declareCompact(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, MemberTraits.FINAL));
    }

    /**
     * Declares the canonical constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> CtorRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareCanonicalConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declareCanonical(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, MemberTraits.FINAL));
    }

    /**
     * Declares an additional constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> CtorRef10<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10) {
        return declareAdditional(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, MemberTraits.FINAL));
    }

    /**
     * Declares the compact constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> CtorRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareCompactConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declareCompact(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, MemberTraits.FINAL));
    }

    /**
     * Declares the canonical constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> CtorRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareCanonicalConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declareCanonical(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, MemberTraits.FINAL));
    }

    /**
     * Declares an additional constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> CtorRef11<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11) {
        return declareAdditional(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, MemberTraits.FINAL));
    }

    /**
     * Declares the compact constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> CtorRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareCompactConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declareCompact(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, MemberTraits.FINAL));
    }

    /**
     * Declares the canonical constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> CtorRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareCanonicalConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declareCanonical(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, MemberTraits.FINAL));
    }

    /**
     * Declares an additional constructor of the record, to be defined by the bodies of the record.
     *
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
     * @return the fact of the constructor
     * @throws IllegalArgumentException if a canonical or compact constructor is declared already, or javac would reject the constructor
     * @throws IllegalStateException if the declarations of the record have ended
     */
    public <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> CtorRef12<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> declareConstructor(TypeToken<A1> p1, TypeToken<A2> p2, TypeToken<A3> p3, TypeToken<A4> p4, TypeToken<A5> p5, TypeToken<A6> p6, TypeToken<A7> p7, TypeToken<A8> p8, TypeToken<A9> p9, TypeToken<A10> p10, TypeToken<A11> p11, TypeToken<A12> p12) {
        return declareAdditional(UnsafeInvocables.ctor(finalSelf(), p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, MemberTraits.FINAL));
    }
}
