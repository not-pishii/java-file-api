package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArityFamilies.A1;
import me.supcheg.javafile.facts.ArityFamilies.A2;
import me.supcheg.javafile.facts.ArityFamilies.A3;
import me.supcheg.javafile.facts.ArityFamilies.A4;
import me.supcheg.javafile.facts.ArityFamilies.A5;
import me.supcheg.javafile.facts.ArityFamilies.A6;
import me.supcheg.javafile.facts.ArityFamilies.A7;
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
class Arity7FamiliesTest {
    @Test
    public void methodRef() {
        Asked asked = new Asked();
        MethodRef7<Subject, Result, A1, A2, A3, A4, A5, A6, A7> made = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        MethodRef7<Subject, Result, A1, A2, A3, A4, A5, A6, A7> proved = asked.source().method("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7);
        for (MethodRef7<Subject, Result, A1, A2, A3, A4, A5, A6, A7> fact : List.of(made, proved)) {
            Assertions.assertSame(ArityFamilies.RESULT, fact.result());
            Assertions.assertSame(ArityFamilies.PARAM1, fact.param1());
            Assertions.assertSame(ArityFamilies.PARAM2, fact.param2());
            Assertions.assertSame(ArityFamilies.PARAM3, fact.param3());
            Assertions.assertSame(ArityFamilies.PARAM4, fact.param4());
            Assertions.assertSame(ArityFamilies.PARAM5, fact.param5());
            Assertions.assertSame(ArityFamilies.PARAM6, fact.param6());
            Assertions.assertSame(ArityFamilies.PARAM7, fact.param7());
            ArityFamilies.assertFact(fact, InvocableKind.INSTANCE_METHOD, ArityFamilies.OWNER, "run", Optional.of(ArityFamilies.RESULT), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7), "arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.method("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7));
    }

    @Test
    public void voidMethodRef() {
        Asked asked = new Asked();
        VoidMethodRef7<Subject, A1, A2, A3, A4, A5, A6, A7> made = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        VoidMethodRef7<Subject, A1, A2, A3, A4, A5, A6, A7> proved = asked.source().voidMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7);
        for (VoidMethodRef7<Subject, A1, A2, A3, A4, A5, A6, A7> fact : List.of(made, proved)) {
            Assertions.assertSame(ArityFamilies.PARAM1, fact.param1());
            Assertions.assertSame(ArityFamilies.PARAM2, fact.param2());
            Assertions.assertSame(ArityFamilies.PARAM3, fact.param3());
            Assertions.assertSame(ArityFamilies.PARAM4, fact.param4());
            Assertions.assertSame(ArityFamilies.PARAM5, fact.param5());
            Assertions.assertSame(ArityFamilies.PARAM6, fact.param6());
            Assertions.assertSame(ArityFamilies.PARAM7, fact.param7());
            ArityFamilies.assertFact(fact, InvocableKind.INSTANCE_METHOD, ArityFamilies.OWNER, "run", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7), "void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.voidMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7));
    }

    @Test
    public void staticMethodRef() {
        Asked asked = new Asked();
        StaticMethodRef7<Result, A1, A2, A3, A4, A5, A6, A7> made = UnsafeInvocables.staticMethod(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        StaticMethodRef7<Result, A1, A2, A3, A4, A5, A6, A7> proved = asked.source().staticMethod("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7);
        for (StaticMethodRef7<Result, A1, A2, A3, A4, A5, A6, A7> fact : List.of(made, proved)) {
            Assertions.assertSame(ArityFamilies.RESULT, fact.result());
            Assertions.assertSame(ArityFamilies.PARAM1, fact.param1());
            Assertions.assertSame(ArityFamilies.PARAM2, fact.param2());
            Assertions.assertSame(ArityFamilies.PARAM3, fact.param3());
            Assertions.assertSame(ArityFamilies.PARAM4, fact.param4());
            Assertions.assertSame(ArityFamilies.PARAM5, fact.param5());
            Assertions.assertSame(ArityFamilies.PARAM6, fact.param6());
            Assertions.assertSame(ArityFamilies.PARAM7, fact.param7());
            ArityFamilies.assertFact(fact, InvocableKind.STATIC_METHOD, ArityFamilies.OWNER, "run", Optional.of(ArityFamilies.RESULT), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7), "static arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.staticMethod("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7));
    }

    @Test
    public void voidStaticMethodRef() {
        Asked asked = new Asked();
        VoidStaticMethodRef7<A1, A2, A3, A4, A5, A6, A7> made = UnsafeInvocables.voidStaticMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        VoidStaticMethodRef7<A1, A2, A3, A4, A5, A6, A7> proved = asked.source().voidStaticMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7);
        for (VoidStaticMethodRef7<A1, A2, A3, A4, A5, A6, A7> fact : List.of(made, proved)) {
            Assertions.assertSame(ArityFamilies.PARAM1, fact.param1());
            Assertions.assertSame(ArityFamilies.PARAM2, fact.param2());
            Assertions.assertSame(ArityFamilies.PARAM3, fact.param3());
            Assertions.assertSame(ArityFamilies.PARAM4, fact.param4());
            Assertions.assertSame(ArityFamilies.PARAM5, fact.param5());
            Assertions.assertSame(ArityFamilies.PARAM6, fact.param6());
            Assertions.assertSame(ArityFamilies.PARAM7, fact.param7());
            ArityFamilies.assertFact(fact, InvocableKind.STATIC_METHOD, ArityFamilies.OWNER, "run", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7), "static void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.voidStaticMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7));
    }

    @Test
    public void ctorRef() {
        Asked asked = new Asked();
        CtorRef7<Subject, A1, A2, A3, A4, A5, A6, A7> made = UnsafeInvocables.ctor(ArityFamilies.OWNER, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        CtorRef7<Subject, A1, A2, A3, A4, A5, A6, A7> proved = asked.source().ctor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7);
        for (CtorRef7<Subject, A1, A2, A3, A4, A5, A6, A7> fact : List.of(made, proved)) {
            Assertions.assertSame(ArityFamilies.PARAM1, fact.param1());
            Assertions.assertSame(ArityFamilies.PARAM2, fact.param2());
            Assertions.assertSame(ArityFamilies.PARAM3, fact.param3());
            Assertions.assertSame(ArityFamilies.PARAM4, fact.param4());
            Assertions.assertSame(ArityFamilies.PARAM5, fact.param5());
            Assertions.assertSame(ArityFamilies.PARAM6, fact.param6());
            Assertions.assertSame(ArityFamilies.PARAM7, fact.param7());
            ArityFamilies.assertFact(fact, InvocableKind.CONSTRUCTOR, ArityFamilies.OWNER, "Subject", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7), "new arity.Subject(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.constructor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7));
    }

    @Test
    public void superCtorRef() {
        Asked asked = new Asked();
        SuperCtorRef7<Subject, A1, A2, A3, A4, A5, A6, A7> made = UnsafeInvocables.superCtor(ArityFamilies.OWNER, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        SuperCtorRef7<Subject, A1, A2, A3, A4, A5, A6, A7> proved = asked.source().superCtor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7);
        for (SuperCtorRef7<Subject, A1, A2, A3, A4, A5, A6, A7> fact : List.of(made, proved)) {
            Assertions.assertSame(ArityFamilies.PARAM1, fact.param1());
            Assertions.assertSame(ArityFamilies.PARAM2, fact.param2());
            Assertions.assertSame(ArityFamilies.PARAM3, fact.param3());
            Assertions.assertSame(ArityFamilies.PARAM4, fact.param4());
            Assertions.assertSame(ArityFamilies.PARAM5, fact.param5());
            Assertions.assertSame(ArityFamilies.PARAM6, fact.param6());
            Assertions.assertSame(ArityFamilies.PARAM7, fact.param7());
            ArityFamilies.assertFact(fact, InvocableKind.CONSTRUCTOR, ArityFamilies.OWNER, "Subject", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7), "super arity.Subject(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.constructor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7));
    }

    @Test
    public void sam() {
        InterfaceToken<Functional> owner = ArityFamilies.functional(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7);
        MethodRef7<Functional, Result, A1, A2, A3, A4, A5, A6, A7> method = UnsafeInvocables.method(owner, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        Sam7<Functional, Result, A1, A2, A3, A4, A5, A6, A7> sam = UnsafeInvocables.sam(method);
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
        org.assertj.core.api.Assertions.assertThat(sam).hasToString("arity.Result arity.Functional.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7)");
    }

    @Test
    public void voidSam() {
        InterfaceToken<Functional> owner = ArityFamilies.functional(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7);
        VoidMethodRef7<Functional, A1, A2, A3, A4, A5, A6, A7> method = UnsafeInvocables.voidMethod(owner, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        VoidSam7<Functional, A1, A2, A3, A4, A5, A6, A7> sam = UnsafeInvocables.voidSam(method);
        Assertions.assertSame(owner, sam.owner());
        Assertions.assertSame(method, sam.method());
        Assertions.assertSame(ArityFamilies.PARAM1, sam.param1());
        Assertions.assertSame(ArityFamilies.PARAM2, sam.param2());
        Assertions.assertSame(ArityFamilies.PARAM3, sam.param3());
        Assertions.assertSame(ArityFamilies.PARAM4, sam.param4());
        Assertions.assertSame(ArityFamilies.PARAM5, sam.param5());
        Assertions.assertSame(ArityFamilies.PARAM6, sam.param6());
        Assertions.assertSame(ArityFamilies.PARAM7, sam.param7());
        org.assertj.core.api.Assertions.assertThat(sam).hasToString("void arity.Functional.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7)");
    }

    @Test
    public void overriding() {
        MethodRef7<Subject, Result, A1, A2, A3, A4, A5, A6, A7> ofSupertype = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        MethodRef7<Subject, Result, A1, A2, A3, A4, A5, A6, A7> own = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        Overriding7<Subject, Result, A1, A2, A3, A4, A5, A6, A7> implementation = UnsafeInvocables.overriding7(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7) overriding arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7)");
    }

    @Test
    public void selfOverriding() {
        MethodRef7<Subject, Result, A1, A2, A3, A4, A5, A6, A7> ofSupertype = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        MethodRef7<Subject, Subject, A1, A2, A3, A4, A5, A6, A7> own = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.OWNER, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        SelfOverriding7<Subject, Result, A1, A2, A3, A4, A5, A6, A7> implementation = UnsafeInvocables.selfOverriding7(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("arity.Subject arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7) overriding arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7)");
    }

    @Test
    public void inherited() {
        MethodRef7<Subject, Result, A1, A2, A3, A4, A5, A6, A7> ofSupertype = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        ByInheritance how = new ByInheritance(ofSupertype);
        Inherited7<Subject, Result, A1, A2, A3, A4, A5, A6, A7> implementation = UnsafeInvocables.inherited7(how);
        Assertions.assertSame(how, implementation.how());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("inherited arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7)");
    }

    @Test
    public void voidOverriding() {
        VoidMethodRef7<Subject, A1, A2, A3, A4, A5, A6, A7> ofSupertype = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        VoidMethodRef7<Subject, A1, A2, A3, A4, A5, A6, A7> own = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        VoidOverriding7<Subject, A1, A2, A3, A4, A5, A6, A7> implementation = UnsafeInvocables.voidOverriding7(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7) overriding void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7)");
    }

    @Test
    public void voidInherited() {
        VoidMethodRef7<Subject, A1, A2, A3, A4, A5, A6, A7> ofSupertype = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.TRAITS);
        ByInheritance how = new ByInheritance(ofSupertype);
        VoidInherited7<Subject, A1, A2, A3, A4, A5, A6, A7> implementation = UnsafeInvocables.voidInherited7(how);
        Assertions.assertSame(how, implementation.how());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("inherited void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7)");
    }
}
