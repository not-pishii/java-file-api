package me.supcheg.javafile.facts;

import java.util.List;
import java.util.Optional;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.ArityFamilies.A1;
import me.supcheg.javafile.facts.ArityFamilies.A10;
import me.supcheg.javafile.facts.ArityFamilies.A11;
import me.supcheg.javafile.facts.ArityFamilies.A12;
import me.supcheg.javafile.facts.ArityFamilies.A13;
import me.supcheg.javafile.facts.ArityFamilies.A14;
import me.supcheg.javafile.facts.ArityFamilies.A15;
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
class Arity15FamiliesTest {
    @Test
    public void methodRef() {
        Asked asked = new Asked();
        MethodRef15<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> made = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        MethodRef15<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> proved = asked.source().method("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15);
        for (MethodRef15<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> fact : List.of(made, proved)) {
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
            Assertions.assertSame(ArityFamilies.PARAM13, fact.param13());
            Assertions.assertSame(ArityFamilies.PARAM14, fact.param14());
            Assertions.assertSame(ArityFamilies.PARAM15, fact.param15());
            ArityFamilies.assertFact(fact, InvocableKind.INSTANCE_METHOD, ArityFamilies.OWNER, "run", Optional.of(ArityFamilies.RESULT), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15), "arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.method("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15));
        MethodRef15<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> generic = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.GENERIC_TRAITS);
        Assertions.assertSame(ArityFamilies.WITNESS, generic.withTypeArgs(ArityFamilies.WITNESS).traits().typeArgs().getFirst());
    }

    @Test
    public void voidMethodRef() {
        Asked asked = new Asked();
        VoidMethodRef15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> made = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        VoidMethodRef15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> proved = asked.source().voidMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15);
        for (VoidMethodRef15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> fact : List.of(made, proved)) {
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
            Assertions.assertSame(ArityFamilies.PARAM13, fact.param13());
            Assertions.assertSame(ArityFamilies.PARAM14, fact.param14());
            Assertions.assertSame(ArityFamilies.PARAM15, fact.param15());
            ArityFamilies.assertFact(fact, InvocableKind.INSTANCE_METHOD, ArityFamilies.OWNER, "run", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15), "void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.voidMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15));
        VoidMethodRef15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> generic = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.GENERIC_TRAITS);
        Assertions.assertSame(ArityFamilies.WITNESS, generic.withTypeArgs(ArityFamilies.WITNESS).traits().typeArgs().getFirst());
    }

    @Test
    public void staticMethodRef() {
        Asked asked = new Asked();
        StaticMethodRef15<Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> made = UnsafeInvocables.staticMethod(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        StaticMethodRef15<Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> proved = asked.source().staticMethod("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15);
        for (StaticMethodRef15<Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> fact : List.of(made, proved)) {
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
            Assertions.assertSame(ArityFamilies.PARAM13, fact.param13());
            Assertions.assertSame(ArityFamilies.PARAM14, fact.param14());
            Assertions.assertSame(ArityFamilies.PARAM15, fact.param15());
            ArityFamilies.assertFact(fact, InvocableKind.STATIC_METHOD, ArityFamilies.OWNER, "run", Optional.of(ArityFamilies.RESULT), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15), "static arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.staticMethod("run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15));
        StaticMethodRef15<Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> generic = UnsafeInvocables.staticMethod(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.GENERIC_TRAITS);
        Assertions.assertSame(ArityFamilies.WITNESS, generic.withTypeArgs(ArityFamilies.WITNESS).traits().typeArgs().getFirst());
    }

    @Test
    public void voidStaticMethodRef() {
        Asked asked = new Asked();
        VoidStaticMethodRef15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> made = UnsafeInvocables.voidStaticMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        VoidStaticMethodRef15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> proved = asked.source().voidStaticMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15);
        for (VoidStaticMethodRef15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> fact : List.of(made, proved)) {
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
            Assertions.assertSame(ArityFamilies.PARAM13, fact.param13());
            Assertions.assertSame(ArityFamilies.PARAM14, fact.param14());
            Assertions.assertSame(ArityFamilies.PARAM15, fact.param15());
            ArityFamilies.assertFact(fact, InvocableKind.STATIC_METHOD, ArityFamilies.OWNER, "run", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15), "static void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.voidStaticMethod("run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15));
        VoidStaticMethodRef15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> generic = UnsafeInvocables.voidStaticMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.GENERIC_TRAITS);
        Assertions.assertSame(ArityFamilies.WITNESS, generic.withTypeArgs(ArityFamilies.WITNESS).traits().typeArgs().getFirst());
    }

    @Test
    public void ctorRef() {
        Asked asked = new Asked();
        CtorRef15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> made = UnsafeInvocables.ctor(ArityFamilies.OWNER, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        CtorRef15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> proved = asked.source().ctor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15);
        for (CtorRef15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> fact : List.of(made, proved)) {
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
            Assertions.assertSame(ArityFamilies.PARAM13, fact.param13());
            Assertions.assertSame(ArityFamilies.PARAM14, fact.param14());
            Assertions.assertSame(ArityFamilies.PARAM15, fact.param15());
            ArityFamilies.assertFact(fact, InvocableKind.CONSTRUCTOR, ArityFamilies.OWNER, "Subject", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15), "new arity.Subject(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.constructor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15));
    }

    @Test
    public void superCtorRef() {
        Asked asked = new Asked();
        SuperCtorRef15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> made = UnsafeInvocables.superCtor(ArityFamilies.OWNER, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        SuperCtorRef15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> proved = asked.source().superCtor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15);
        for (SuperCtorRef15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> fact : List.of(made, proved)) {
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
            Assertions.assertSame(ArityFamilies.PARAM13, fact.param13());
            Assertions.assertSame(ArityFamilies.PARAM14, fact.param14());
            Assertions.assertSame(ArityFamilies.PARAM15, fact.param15());
            ArityFamilies.assertFact(fact, InvocableKind.CONSTRUCTOR, ArityFamilies.OWNER, "Subject", Optional.empty(), List.of(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15), "super arity.Subject(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15)");
        }
        org.assertj.core.api.Assertions.assertThat(asked.queries()).containsExactly(MemberQuery.constructor(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15));
    }

    @Test
    public void sam() {
        InterfaceToken<Functional> owner = ArityFamilies.functional(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15);
        MethodRef15<Functional, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> method = UnsafeInvocables.method(owner, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        Sam15<Functional, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> sam = UnsafeInvocables.sam(method);
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
        Assertions.assertSame(ArityFamilies.PARAM13, sam.param13());
        Assertions.assertSame(ArityFamilies.PARAM14, sam.param14());
        Assertions.assertSame(ArityFamilies.PARAM15, sam.param15());
        org.assertj.core.api.Assertions.assertThat(sam).hasToString("arity.Result arity.Functional.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15)");
    }

    @Test
    public void voidSam() {
        InterfaceToken<Functional> owner = ArityFamilies.functional(ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15);
        VoidMethodRef15<Functional, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> method = UnsafeInvocables.voidMethod(owner, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        VoidSam15<Functional, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> sam = UnsafeInvocables.voidSam(method);
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
        Assertions.assertSame(ArityFamilies.PARAM13, sam.param13());
        Assertions.assertSame(ArityFamilies.PARAM14, sam.param14());
        Assertions.assertSame(ArityFamilies.PARAM15, sam.param15());
        org.assertj.core.api.Assertions.assertThat(sam).hasToString("void arity.Functional.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15)");
    }

    @Test
    public void overriding() {
        MethodRef15<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> ofSupertype = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        MethodRef15<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> own = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        Overriding15<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> implementation = UnsafeInvocables.overriding15(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15) overriding arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15)");
    }

    @Test
    public void selfOverriding() {
        MethodRef15<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> ofSupertype = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        MethodRef15<Subject, Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> own = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.OWNER, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        SelfOverriding15<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> implementation = UnsafeInvocables.selfOverriding15(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("arity.Subject arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15) overriding arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15)");
    }

    @Test
    public void inherited() {
        MethodRef15<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> ofSupertype = UnsafeInvocables.method(ArityFamilies.OWNER, "run", ArityFamilies.RESULT, ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        ByInheritance how = new ByInheritance(ofSupertype);
        Inherited15<Subject, Result, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> implementation = UnsafeInvocables.inherited15(how);
        Assertions.assertSame(how, implementation.how());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("inherited arity.Result arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15)");
    }

    @Test
    public void voidOverriding() {
        VoidMethodRef15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> ofSupertype = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        VoidMethodRef15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> own = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        ByOverride how = new ByOverride(ofSupertype, own);
        VoidOverriding15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> implementation = UnsafeInvocables.voidOverriding15(how);
        Assertions.assertSame(how, implementation.how());
        Assertions.assertSame(own, implementation.method());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15) overriding void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15)");
    }

    @Test
    public void voidInherited() {
        VoidMethodRef15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> ofSupertype = UnsafeInvocables.voidMethod(ArityFamilies.OWNER, "run", ArityFamilies.PARAM1, ArityFamilies.PARAM2, ArityFamilies.PARAM3, ArityFamilies.PARAM4, ArityFamilies.PARAM5, ArityFamilies.PARAM6, ArityFamilies.PARAM7, ArityFamilies.PARAM8, ArityFamilies.PARAM9, ArityFamilies.PARAM10, ArityFamilies.PARAM11, ArityFamilies.PARAM12, ArityFamilies.PARAM13, ArityFamilies.PARAM14, ArityFamilies.PARAM15, ArityFamilies.TRAITS);
        ByInheritance how = new ByInheritance(ofSupertype);
        VoidInherited15<Subject, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15> implementation = UnsafeInvocables.voidInherited15(how);
        Assertions.assertSame(how, implementation.how());
        org.assertj.core.api.Assertions.assertThat(implementation).hasToString("inherited void arity.Subject.run(arity.A1, arity.A2, arity.A3, arity.A4, arity.A5, arity.A6, arity.A7, arity.A8, arity.A9, arity.A10, arity.A11, arity.A12, arity.A13, arity.A14, arity.A15)");
    }
}
