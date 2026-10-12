package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArityFamilies.A1;
import me.supcheg.javafile.facts.ArityFamilies.A10;
import me.supcheg.javafile.facts.ArityFamilies.A2;
import me.supcheg.javafile.facts.ArityFamilies.A3;
import me.supcheg.javafile.facts.ArityFamilies.A4;
import me.supcheg.javafile.facts.ArityFamilies.A5;
import me.supcheg.javafile.facts.ArityFamilies.A6;
import me.supcheg.javafile.facts.ArityFamilies.A7;
import me.supcheg.javafile.facts.ArityFamilies.A8;
import me.supcheg.javafile.facts.ArityFamilies.A9;
import me.supcheg.javafile.facts.ArityFamilies.Asked;
import me.supcheg.javafile.facts.ArityFamilies.Functional;
import me.supcheg.javafile.facts.ArityFamilies.Result;
import me.supcheg.javafile.facts.ArityFamilies.Subject;
import me.supcheg.javafile.facts.Implemented.ByInheritance;
import me.supcheg.javafile.facts.Implemented.ByOverride;
import me.supcheg.javafile.facts.source.MemberQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
class Arity10FamiliesTest {
    @Test
    public void methodRef() {
        Asked asked = new Asked();
        MethodRef10<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> made = UnsafeFacts.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        MethodRef10<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> proved = asked.source().method("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10);
        for (MethodRef10<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> fact : List.of(made, proved)) {
            Assertions.assertSame(ArityFamilies.RESULT, fact.result());
            Assertions.assertSame(ArityFamilies.PARAM1, fact.param1());
            Assertions.assertSame(ArityFamilies.PARAM2, fact.param2());
            Assertions.assertSame(ArityFamilies.PARAM3, fact.param3());
            Assertions.assertSame(ArityFamilies.PARAM4, fact.param4());
            Assertions.assertSame(ArityFamilies.PARAM5, fact.param5());
            Assertions.assertSame(ArityFamilies.PARAM6, fact.param6());
            Assertions.assertSame(ArityFamilies.PARAM7, fact.param7());
            Assertions.assertSame(ArityFamilies.PARAM8, fact.param8());
            Assertions.assertSame(ArityFamilies.PARAM9, fact.param9());
            Assertions.assertSame(ArityFamilies.PARAM10, fact.param10());
            ArityFamilies.assertFact(fact, InvocableKind.INSTANCE_METHOD, ArityFamilies.OWNER, "run", Optional.of(ArityFamilies.RESULT), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10), "arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.method("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10));
    }

    @Test
    public void voidMethodRef() {
        Asked asked = new Asked();
        VoidMethodRef10<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> made = UnsafeFacts.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        VoidMethodRef10<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> proved = asked.source().voidMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10);
        for (VoidMethodRef10<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> fact : List.of(made, proved)) {
            Assertions.assertSame(ArityFamilies.PARAM1, fact.param1());
            Assertions.assertSame(ArityFamilies.PARAM2, fact.param2());
            Assertions.assertSame(ArityFamilies.PARAM3, fact.param3());
            Assertions.assertSame(ArityFamilies.PARAM4, fact.param4());
            Assertions.assertSame(ArityFamilies.PARAM5, fact.param5());
            Assertions.assertSame(ArityFamilies.PARAM6, fact.param6());
            Assertions.assertSame(ArityFamilies.PARAM7, fact.param7());
            Assertions.assertSame(ArityFamilies.PARAM8, fact.param8());
            Assertions.assertSame(ArityFamilies.PARAM9, fact.param9());
            Assertions.assertSame(ArityFamilies.PARAM10, fact.param10());
            ArityFamilies.assertFact(fact, InvocableKind.INSTANCE_METHOD, ArityFamilies.OWNER, "run", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10), "void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.voidMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10));
    }

    @Test
    public void staticMethodRef() {
        Asked asked = new Asked();
        StaticMethodRef10<Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> made = UnsafeFacts.staticMethod(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        StaticMethodRef10<Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> proved = asked.source().staticMethod("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10);
        for (StaticMethodRef10<Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> fact : List.of(made, proved)) {
            Assertions.assertSame(ArityFamilies.RESULT, fact.result());
            Assertions.assertSame(ArityFamilies.PARAM1, fact.param1());
            Assertions.assertSame(ArityFamilies.PARAM2, fact.param2());
            Assertions.assertSame(ArityFamilies.PARAM3, fact.param3());
            Assertions.assertSame(ArityFamilies.PARAM4, fact.param4());
            Assertions.assertSame(ArityFamilies.PARAM5, fact.param5());
            Assertions.assertSame(ArityFamilies.PARAM6, fact.param6());
            Assertions.assertSame(ArityFamilies.PARAM7, fact.param7());
            Assertions.assertSame(ArityFamilies.PARAM8, fact.param8());
            Assertions.assertSame(ArityFamilies.PARAM9, fact.param9());
            Assertions.assertSame(ArityFamilies.PARAM10, fact.param10());
            ArityFamilies.assertFact(fact, InvocableKind.STATIC_METHOD, ArityFamilies.OWNER, "run", Optional.of(ArityFamilies.RESULT), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10), "static arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.staticMethod("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10));
    }

    @Test
    public void voidStaticMethodRef() {
        Asked asked = new Asked();
        VoidStaticMethodRef10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> made = UnsafeFacts.voidStaticMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        VoidStaticMethodRef10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> proved = asked.source().voidStaticMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10);
        for (VoidStaticMethodRef10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> fact : List.of(made, proved)) {
            Assertions.assertSame(ArityFamilies.PARAM1, fact.param1());
            Assertions.assertSame(ArityFamilies.PARAM2, fact.param2());
            Assertions.assertSame(ArityFamilies.PARAM3, fact.param3());
            Assertions.assertSame(ArityFamilies.PARAM4, fact.param4());
            Assertions.assertSame(ArityFamilies.PARAM5, fact.param5());
            Assertions.assertSame(ArityFamilies.PARAM6, fact.param6());
            Assertions.assertSame(ArityFamilies.PARAM7, fact.param7());
            Assertions.assertSame(ArityFamilies.PARAM8, fact.param8());
            Assertions.assertSame(ArityFamilies.PARAM9, fact.param9());
            Assertions.assertSame(ArityFamilies.PARAM10, fact.param10());
            ArityFamilies.assertFact(fact, InvocableKind.STATIC_METHOD, ArityFamilies.OWNER, "run", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10), "static void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.voidStaticMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10));
    }

    @Test
    public void ctorRef() {
        Asked asked = new Asked();
        CtorRef10<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> made = UnsafeFacts.ctor(ArityFamilies.OWNER, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        CtorRef10<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> proved = asked.source().ctor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10);
        for (CtorRef10<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> fact : List.of(made, proved)) {
            Assertions.assertSame(ArityFamilies.PARAM1, fact.param1());
            Assertions.assertSame(ArityFamilies.PARAM2, fact.param2());
            Assertions.assertSame(ArityFamilies.PARAM3, fact.param3());
            Assertions.assertSame(ArityFamilies.PARAM4, fact.param4());
            Assertions.assertSame(ArityFamilies.PARAM5, fact.param5());
            Assertions.assertSame(ArityFamilies.PARAM6, fact.param6());
            Assertions.assertSame(ArityFamilies.PARAM7, fact.param7());
            Assertions.assertSame(ArityFamilies.PARAM8, fact.param8());
            Assertions.assertSame(ArityFamilies.PARAM9, fact.param9());
            Assertions.assertSame(ArityFamilies.PARAM10, fact.param10());
            ArityFamilies.assertFact(fact, InvocableKind.CONSTRUCTOR, ArityFamilies.OWNER, "Subject", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10), "new arity.Subject(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.constructor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10));
    }

    @Test
    public void superCtorRef() {
        Asked asked = new Asked();
        SuperCtorRef10<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> made = UnsafeFacts.superCtor(ArityFamilies.OWNER, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        SuperCtorRef10<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> proved = asked.source().superCtor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10);
        for (SuperCtorRef10<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> fact : List.of(made, proved)) {
            Assertions.assertSame(ArityFamilies.PARAM1, fact.param1());
            Assertions.assertSame(ArityFamilies.PARAM2, fact.param2());
            Assertions.assertSame(ArityFamilies.PARAM3, fact.param3());
            Assertions.assertSame(ArityFamilies.PARAM4, fact.param4());
            Assertions.assertSame(ArityFamilies.PARAM5, fact.param5());
            Assertions.assertSame(ArityFamilies.PARAM6, fact.param6());
            Assertions.assertSame(ArityFamilies.PARAM7, fact.param7());
            Assertions.assertSame(ArityFamilies.PARAM8, fact.param8());
            Assertions.assertSame(ArityFamilies.PARAM9, fact.param9());
            Assertions.assertSame(ArityFamilies.PARAM10, fact.param10());
            ArityFamilies.assertFact(fact, InvocableKind.CONSTRUCTOR, ArityFamilies.OWNER, "Subject", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10), "super arity.Subject(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.constructor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10));
    }

    @Test
    public void sam() {
        InterfaceToken<Functional> owner = ArityFamilies.functional(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10);
        MethodRef10<Functional, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method = UnsafeFacts.method(owner, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        Sam10<Functional, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> sam = UnsafeFacts.sam(method);
        Assertions.assertSame(owner, sam.owner());
        Assertions.assertSame(method, sam.method());
        Assertions.assertSame(ArityFamilies.RESULT, sam.result());
        Assertions.assertSame(ArityFamilies.PARAM1, sam.param1());
        Assertions.assertSame(ArityFamilies.PARAM2, sam.param2());
        Assertions.assertSame(ArityFamilies.PARAM3, sam.param3());
        Assertions.assertSame(ArityFamilies.PARAM4, sam.param4());
        Assertions.assertSame(ArityFamilies.PARAM5, sam.param5());
        Assertions.assertSame(ArityFamilies.PARAM6, sam.param6());
        Assertions.assertSame(ArityFamilies.PARAM7, sam.param7());
        Assertions.assertSame(ArityFamilies.PARAM8, sam.param8());
        Assertions.assertSame(ArityFamilies.PARAM9, sam.param9());
        Assertions.assertSame(ArityFamilies.PARAM10, sam.param10());
        org.assertj.core.api.Assertions.assertThat(sam).hasToString("arity.Result arity.Functional.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10)");
    }

    @Test
    public void voidSam() {
        InterfaceToken<Functional> owner = ArityFamilies.functional(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10);
        VoidMethodRef10<Functional, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> method = UnsafeFacts.voidMethod(owner, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        VoidSam10<Functional, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> sam = UnsafeFacts.voidSam(method);
        Assertions.assertSame(owner, sam.owner());
        Assertions.assertSame(method, sam.method());
        Assertions.assertSame(ArityFamilies.PARAM1, sam.param1());
        Assertions.assertSame(ArityFamilies.PARAM2, sam.param2());
        Assertions.assertSame(ArityFamilies.PARAM3, sam.param3());
        Assertions.assertSame(ArityFamilies.PARAM4, sam.param4());
        Assertions.assertSame(ArityFamilies.PARAM5, sam.param5());
        Assertions.assertSame(ArityFamilies.PARAM6, sam.param6());
        Assertions.assertSame(ArityFamilies.PARAM7, sam.param7());
        Assertions.assertSame(ArityFamilies.PARAM8, sam.param8());
        Assertions.assertSame(ArityFamilies.PARAM9, sam.param9());
        Assertions.assertSame(ArityFamilies.PARAM10, sam.param10());
        org.assertj.core.api.Assertions.assertThat(sam).hasToString("void arity.Functional.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10)");
    }

    @Test
    public void overriding() {
        MethodRef10<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ofSupertype = UnsafeFacts.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        MethodRef10<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> own = UnsafeFacts.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        Overriding10<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> implementation = UnsafeFacts.overriding10(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10) overriding arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10)");
    }

    @Test
    public void selfOverriding() {
        MethodRef10<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ofSupertype = UnsafeFacts.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        MethodRef10<Subject, Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> own = UnsafeFacts.method(ArityFamilies.OWNER, "run", ArityFamilies.OWNER, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        SelfOverriding10<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> implementation = UnsafeFacts.selfOverriding10(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("arity.Subject arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10) overriding arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10)");
    }

    @Test
    public void inherited() {
        MethodRef10<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ofSupertype = UnsafeFacts.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        ByInheritance how = new ByInheritance(ofSupertype);
        Inherited10<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> implementation = UnsafeFacts.inherited10(how);
        Assertions.assertSame(how, implementation.how());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("inherited arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10)");
    }

    @Test
    public void voidOverriding() {
        VoidMethodRef10<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ofSupertype = UnsafeFacts.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        VoidMethodRef10<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> own = UnsafeFacts.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        VoidOverriding10<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> implementation = UnsafeFacts.voidOverriding10(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10) overriding void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10)");
    }

    @Test
    public void voidInherited() {
        VoidMethodRef10<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> ofSupertype = UnsafeFacts.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.TRAITS);
        ByInheritance how = new ByInheritance(ofSupertype);
        VoidInherited10<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> implementation = UnsafeFacts.voidInherited10(how);
        Assertions.assertSame(how, implementation.how());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("inherited void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10)");
    }
}
