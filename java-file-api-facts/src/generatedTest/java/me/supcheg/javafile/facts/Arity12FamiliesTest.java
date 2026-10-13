package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArityFamilies.A1;
import me.supcheg.javafile.facts.ArityFamilies.A10;
import me.supcheg.javafile.facts.ArityFamilies.A11;
import me.supcheg.javafile.facts.ArityFamilies.A12;
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
class Arity12FamiliesTest {
    @Test
    public void methodRef() {
        Asked asked = new Asked();
        MethodRef12<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> made = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        MethodRef12<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> proved = asked.source().method("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12);
        for (MethodRef12<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> fact : List.of(made, proved)) {
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
            Assertions.assertSame(ArityFamilies.PARAM11, fact.param11());
            Assertions.assertSame(ArityFamilies.PARAM12, fact.param12());
            ArityFamilies.assertFact(fact, InvocableKind.INSTANCE_METHOD, ArityFamilies.OWNER, "run", Optional.of(ArityFamilies.RESULT), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12), "arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.method("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12));
    }

    @Test
    public void voidMethodRef() {
        Asked asked = new Asked();
        VoidMethodRef12<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> made = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        VoidMethodRef12<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> proved = asked.source().voidMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12);
        for (VoidMethodRef12<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> fact : List.of(made, proved)) {
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
            Assertions.assertSame(ArityFamilies.PARAM11, fact.param11());
            Assertions.assertSame(ArityFamilies.PARAM12, fact.param12());
            ArityFamilies.assertFact(fact, InvocableKind.INSTANCE_METHOD, ArityFamilies.OWNER, "run", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12), "void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.voidMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12));
    }

    @Test
    public void staticMethodRef() {
        Asked asked = new Asked();
        StaticMethodRef12<Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> made = UnsafeInvocables.staticMethod(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        StaticMethodRef12<Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> proved = asked.source().staticMethod("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12);
        for (StaticMethodRef12<Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> fact : List.of(made, proved)) {
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
            Assertions.assertSame(ArityFamilies.PARAM11, fact.param11());
            Assertions.assertSame(ArityFamilies.PARAM12, fact.param12());
            ArityFamilies.assertFact(fact, InvocableKind.STATIC_METHOD, ArityFamilies.OWNER, "run", Optional.of(ArityFamilies.RESULT), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12), "static arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.staticMethod("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12));
    }

    @Test
    public void voidStaticMethodRef() {
        Asked asked = new Asked();
        VoidStaticMethodRef12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> made = UnsafeInvocables.voidStaticMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        VoidStaticMethodRef12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> proved = asked.source().voidStaticMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12);
        for (VoidStaticMethodRef12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> fact : List.of(made, proved)) {
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
            Assertions.assertSame(ArityFamilies.PARAM11, fact.param11());
            Assertions.assertSame(ArityFamilies.PARAM12, fact.param12());
            ArityFamilies.assertFact(fact, InvocableKind.STATIC_METHOD, ArityFamilies.OWNER, "run", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12), "static void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.voidStaticMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12));
    }

    @Test
    public void ctorRef() {
        Asked asked = new Asked();
        CtorRef12<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> made = UnsafeInvocables.ctor(ArityFamilies.OWNER, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        CtorRef12<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> proved = asked.source().ctor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12);
        for (CtorRef12<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> fact : List.of(made, proved)) {
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
            Assertions.assertSame(ArityFamilies.PARAM11, fact.param11());
            Assertions.assertSame(ArityFamilies.PARAM12, fact.param12());
            ArityFamilies.assertFact(fact, InvocableKind.CONSTRUCTOR, ArityFamilies.OWNER, "Subject", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12), "new arity.Subject(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.constructor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12));
    }

    @Test
    public void superCtorRef() {
        Asked asked = new Asked();
        SuperCtorRef12<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> made = UnsafeInvocables.superCtor(ArityFamilies.OWNER, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        SuperCtorRef12<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> proved = asked.source().superCtor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12);
        for (SuperCtorRef12<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> fact : List.of(made, proved)) {
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
            Assertions.assertSame(ArityFamilies.PARAM11, fact.param11());
            Assertions.assertSame(ArityFamilies.PARAM12, fact.param12());
            ArityFamilies.assertFact(fact, InvocableKind.CONSTRUCTOR, ArityFamilies.OWNER, "Subject", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12), "super arity.Subject(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.constructor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12));
    }

    @Test
    public void sam() {
        InterfaceToken<Functional> owner = ArityFamilies.functional(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12);
        MethodRef12<Functional, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method = UnsafeInvocables.method(owner, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        Sam12<Functional, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> sam = UnsafeInvocables.sam(method);
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
        Assertions.assertSame(ArityFamilies.PARAM11, sam.param11());
        Assertions.assertSame(ArityFamilies.PARAM12, sam.param12());
        org.assertj.core.api.Assertions.assertThat(sam).hasToString("arity.Result arity.Functional.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12)");
    }

    @Test
    public void voidSam() {
        InterfaceToken<Functional> owner = ArityFamilies.functional(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12);
        VoidMethodRef12<Functional, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method = UnsafeInvocables.voidMethod(owner, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        VoidSam12<Functional, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> sam = UnsafeInvocables.voidSam(method);
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
        Assertions.assertSame(ArityFamilies.PARAM11, sam.param11());
        Assertions.assertSame(ArityFamilies.PARAM12, sam.param12());
        org.assertj.core.api.Assertions.assertThat(sam).hasToString("void arity.Functional.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12)");
    }

    @Test
    public void overriding() {
        MethodRef12<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ofSupertype = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        MethodRef12<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> own = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        Overriding12<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> implementation = UnsafeInvocables.overriding12(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12) overriding arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12)");
    }

    @Test
    public void selfOverriding() {
        MethodRef12<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ofSupertype = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        MethodRef12<Subject, Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> own = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.OWNER, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        SelfOverriding12<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> implementation = UnsafeInvocables.selfOverriding12(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("arity.Subject arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12) overriding arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12)");
    }

    @Test
    public void inherited() {
        MethodRef12<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ofSupertype = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        ByInheritance how = new ByInheritance(ofSupertype);
        Inherited12<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> implementation = UnsafeInvocables.inherited12(how);
        Assertions.assertSame(how, implementation.how());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("inherited arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12)");
    }

    @Test
    public void voidOverriding() {
        VoidMethodRef12<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ofSupertype = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        VoidMethodRef12<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> own = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        VoidOverriding12<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> implementation = UnsafeInvocables.voidOverriding12(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12) overriding void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12)");
    }

    @Test
    public void voidInherited() {
        VoidMethodRef12<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> ofSupertype = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.TRAITS);
        ByInheritance how = new ByInheritance(ofSupertype);
        VoidInherited12<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> implementation = UnsafeInvocables.voidInherited12(how);
        Assertions.assertSame(how, implementation.how());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("inherited void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12)");
    }
}
