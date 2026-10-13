package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;
import javax.annotation.processing.Generated;
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
class Arity0FamiliesTest {
    @Test
    public void methodRef() {
        Asked asked = new Asked();
        MethodRef0<Subject, Result> made = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.TRAITS);
        MethodRef0<Subject, Result> proved = asked.source().method("run", ArityFamilies.RESULT);
        for (MethodRef0<Subject, Result> fact : List.of(made, proved)) {
            Assertions.assertSame(ArityFamilies.RESULT, fact.result());
            ArityFamilies.assertFact(fact, InvocableKind.INSTANCE_METHOD, ArityFamilies.OWNER, "run", Optional.of(ArityFamilies.RESULT), List.of(), "arity.Result arity.Subject.run()");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.method("run", ArityFamilies.RESULT));
    }

    @Test
    public void voidMethodRef() {
        Asked asked = new Asked();
        VoidMethodRef0<Subject> made = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.TRAITS);
        VoidMethodRef0<Subject> proved = asked.source().voidMethod("run");
        for (VoidMethodRef0<Subject> fact : List.of(made, proved)) {
            ArityFamilies.assertFact(fact, InvocableKind.INSTANCE_METHOD, ArityFamilies.OWNER, "run", Optional.empty(), List.of(), "void arity.Subject.run()");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.voidMethod("run"));
    }

    @Test
    public void staticMethodRef() {
        Asked asked = new Asked();
        StaticMethodRef0<Result> made = UnsafeInvocables.staticMethod(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.TRAITS);
        StaticMethodRef0<Result> proved = asked.source().staticMethod("run", ArityFamilies.RESULT);
        for (StaticMethodRef0<Result> fact : List.of(made, proved)) {
            Assertions.assertSame(ArityFamilies.RESULT, fact.result());
            ArityFamilies.assertFact(fact, InvocableKind.STATIC_METHOD, ArityFamilies.OWNER, "run", Optional.of(ArityFamilies.RESULT), List.of(), "static arity.Result arity.Subject.run()");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.staticMethod("run", ArityFamilies.RESULT));
    }

    @Test
    public void voidStaticMethodRef() {
        Asked asked = new Asked();
        VoidStaticMethodRef0 made = UnsafeInvocables.voidStaticMethod(ArityFamilies.OWNER, "run", ArityFamilies.TRAITS);
        VoidStaticMethodRef0 proved = asked.source().voidStaticMethod("run");
        for (VoidStaticMethodRef0 fact : List.of(made, proved)) {
            ArityFamilies.assertFact(fact, InvocableKind.STATIC_METHOD, ArityFamilies.OWNER, "run", Optional.empty(), List.of(), "static void arity.Subject.run()");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.voidStaticMethod("run"));
    }

    @Test
    public void ctorRef() {
        Asked asked = new Asked();
        CtorRef0<Subject> made = UnsafeInvocables.ctor(ArityFamilies.OWNER, ArityFamilies.TRAITS);
        CtorRef0<Subject> proved = asked.source().ctor();
        for (CtorRef0<Subject> fact : List.of(made, proved)) {
            ArityFamilies.assertFact(fact, InvocableKind.CONSTRUCTOR, ArityFamilies.OWNER, "Subject", Optional.empty(), List.of(), "new arity.Subject()");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.constructor());
    }

    @Test
    public void superCtorRef() {
        Asked asked = new Asked();
        SuperCtorRef0<Subject> made = UnsafeInvocables.superCtor(ArityFamilies.OWNER, ArityFamilies.TRAITS);
        SuperCtorRef0<Subject> proved = asked.source().superCtor();
        for (SuperCtorRef0<Subject> fact : List.of(made, proved)) {
            ArityFamilies.assertFact(fact, InvocableKind.CONSTRUCTOR, ArityFamilies.OWNER, "Subject", Optional.empty(), List.of(), "super arity.Subject()");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.constructor());
    }

    @Test
    public void sam() {
        InterfaceToken<Functional> owner = ArityFamilies.functional();
        MethodRef0<Functional, Result> method = UnsafeInvocables.method(owner, "run", ArityFamilies.RESULT, ArityFamilies.TRAITS);
        Sam0<Functional, Result> sam = UnsafeInvocables.sam(method);
        Assertions.assertSame(owner, sam.owner());
        Assertions.assertSame(method, sam.method());
        Assertions.assertSame(ArityFamilies.RESULT, sam.result());
        org.assertj.core.api.Assertions.assertThat(sam).hasToString("arity.Result arity.Functional.run()");
    }

    @Test
    public void voidSam() {
        InterfaceToken<Functional> owner = ArityFamilies.functional();
        VoidMethodRef0<Functional> method = UnsafeInvocables.voidMethod(owner, "run", ArityFamilies.TRAITS);
        VoidSam0<Functional> sam = UnsafeInvocables.voidSam(method);
        Assertions.assertSame(owner, sam.owner());
        Assertions.assertSame(method, sam.method());
        org.assertj.core.api.Assertions.assertThat(sam).hasToString("void arity.Functional.run()");
    }

    @Test
    public void overriding() {
        MethodRef0<Subject, Result> ofSupertype = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.TRAITS);
        MethodRef0<Subject, Result> own = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        Overriding0<Subject, Result> implementation = UnsafeInvocables.overriding0(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("arity.Result arity.Subject.run() overriding arity.Result arity.Subject.run()");
    }

    @Test
    public void selfOverriding() {
        MethodRef0<Subject, Result> ofSupertype = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.TRAITS);
        MethodRef0<Subject, Subject> own = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.OWNER, ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        SelfOverriding0<Subject, Result> implementation = UnsafeInvocables.selfOverriding0(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("arity.Subject arity.Subject.run() overriding arity.Result arity.Subject.run()");
    }

    @Test
    public void inherited() {
        MethodRef0<Subject, Result> ofSupertype = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.TRAITS);
        ByInheritance how = new ByInheritance(ofSupertype);
        Inherited0<Subject, Result> implementation = UnsafeInvocables.inherited0(how);
        Assertions.assertSame(how, implementation.how());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("inherited arity.Result arity.Subject.run()");
    }

    @Test
    public void voidOverriding() {
        VoidMethodRef0<Subject> ofSupertype = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.TRAITS);
        VoidMethodRef0<Subject> own = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        VoidOverriding0<Subject> implementation = UnsafeInvocables.voidOverriding0(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("void arity.Subject.run() overriding void arity.Subject.run()");
    }

    @Test
    public void voidInherited() {
        VoidMethodRef0<Subject> ofSupertype = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.TRAITS);
        ByInheritance how = new ByInheritance(ofSupertype);
        VoidInherited0<Subject> implementation = UnsafeInvocables.voidInherited0(how);
        Assertions.assertSame(how, implementation.how());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("inherited void arity.Subject.run()");
    }
}
