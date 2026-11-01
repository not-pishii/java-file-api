package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.Arity9FormsTest.AbsSpec;
import me.supcheg.javafile.typed.Arity9FormsTest.PlainSpec;
import me.supcheg.javafile.typed.Arity9FormsTest.SubSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedClassHeaderSpec;
import me.supcheg.javafile.typed.fixtures.Abstract9;
import me.supcheg.javafile.typed.fixtures.Make9;
import me.supcheg.javafile.typed.fixtures.Wide9;
import me.supcheg.javafile.typed.fixtures.Widening9;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Abstract9_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Do9_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Fn9_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Make9_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide9_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Widening9_;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * The forms of the typed layer of arity 9: a class that extends `Object`, one that extends a class and one that extends an abstract class, declared with them, are compiled with the classes of the other arities in one run of javac, and run.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
@Execution(ExecutionMode.SAME_THREAD)
class Arity9FormsTest {
    static final class PlainSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var becoming = h.becoming(Widening9_.TOKEN);
            var inhO = h.overriding(Widening9_.inh_String_String_String_String_String_String_String_String_String);
            var inhVoidO = h.overriding(Widening9_.inhVoid_String_String_String_String_String_String_String_String_String);
            var narrowO = h.overriding(Widening9_.narrow_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var plainO = h.overriding(Widening9_.plain_String_String_String_String_String_String_String_String_String);
            var selfO = h.overriding(Widening9_.self_String_String_String_String_String_String_String_String_String, becoming);
            var takeO = h.overriding(Widening9_.take_String_String_String_String_String_String_String_String_String);
            var oh = h.extendsObject();
            var asWidening = oh.implements_(Widening9_.implement(h.self()).inhVoid_String_String_String_String_String_String_String_String_String(inhVoidO).inh_String_String_String_String_String_String_String_String_String(inhO).narrow_String_String_String_String_String_String_String_String_String(narrowO).plain_String_String_String_String_String_String_String_String_String(plainO).self_String_String_String_String_String_String_String_String_String(selfO).take_String_String_String_String_String_String_String_String_String(takeO));
            var cb = oh.members();
            var givenM = cb.declareOverride(asWidening, Widening9_.given_String_String_String_String_String_String_String_String_String);
            var chosenM = cb.declareOverride(asWidening, Widening9_.chosen_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var chainedM = cb.declareOverride(asWidening, Widening9_.chained_String_String_String_String_String_String_String_String_String, asWidening);
            var heldM = cb.declareOverride(asWidening, Widening9_.held_String_String_String_String_String_String_String_String_String);
            var ctorM = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var applyM = cb.declareMethod("apply", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var runM = cb.declareVoidMethod("run", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var makeM = cb.declareStaticMethod("make", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var logM = cb.declareVoidStaticMethod("log", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var lamM = cb.declareMethod("lam", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaCtorM = cb.declareStaticMethod("viaCtor", Make9_.TOKEN);
            var vctorM = cb.variableArity().declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vapplyM = cb.variableArity().declareMethod("applyAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vrunM = cb.variableArity().declareVoidMethod("runAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vmakeM = cb.variableArity().declareStaticMethod("makeAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vlogM = cb.variableArity().declareVoidStaticMethod("logAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var bodies = cb.bodies();
            bodies.define(inhO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(Expressions.concat(Expressions.literal("inh:"), p9)));
            bodies.define(inhVoidO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.exec(Expressions.voidStaticCall(Wide9_.note_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9)).end());
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p9)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p9)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.exec(Expressions.voidStaticCall(Wide9_.note_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9)).end());
            bodies.define(givenM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(Expressions.concat(Expressions.literal("given:"), p9)));
            bodies.define(chosenM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(Expressions.concat(Expressions.literal("chosen:"), p9)));
            bodies.define(chainedM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(self));
            bodies.define(heldM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.exec(Expressions.voidStaticCall(Wide9_.note_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9)).end());
            bodies.define(ctorM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.exec(Expressions.voidStaticCall(Wide9_.note_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9)).end());
            bodies.define(applyM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.let(Wide9_.TOKEN, Expressions.new_(Wide9_.new_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9), (w) -> b.exec(w.voidCall(Wide9_.put_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9)).exec(Expressions.voidStaticCall(Wide9_.note_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9)).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide9_.pick_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9), Expressions.literal("/")), Expressions.staticCall(Wide9_.join_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9)), Expressions.literal("/")), w.field(Wide9_.told)))));
            bodies.define(runM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.exec(Expressions.voidStaticCall(Wide9_.note_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9)).end());
            bodies.define(makeM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(Expressions.staticCall(Wide9_.join_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9)));
            bodies.define(logM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.exec(Expressions.voidStaticCall(Wide9_.note_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9)).end());
            bodies.define(lamM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.let(Fn9_.TOKEN, Expressions.lambda(Fn9_.sam, (q1, q2, q3, q4, q5, q6, q7, q8, q9) -> Expressions.concat(Expressions.literal("fn:"), q9)), (f) -> b.let(Fn9_.TOKEN, Expressions.lambdaBlock(Fn9_.sam, (lb, q1, q2, q3, q4, q5, q6, q7, q8, q9) -> lb.return_(Expressions.concat(Expressions.literal("blk:"), q9))), (g) -> b.let(Do9_.TOKEN, Expressions.lambda(Do9_.sam, (q1, q2, q3, q4, q5, q6, q7, q8, q9) -> Expressions.voidStaticCall(Wide9_.note_String_String_String_String_String_String_String_String_String, q1, q2, q3, q4, q5, q6, q7, q8, q9)), (d) -> b.let(Do9_.TOKEN, Expressions.lambdaBlock(Do9_.sam, (lb, q1, q2, q3, q4, q5, q6, q7, q8, q9) -> lb.exec(Expressions.voidStaticCall(Wide9_.note_String_String_String_String_String_String_String_String_String, q1, q2, q3, q4, q5, q6, q7, q8, q9)).end()), (e) -> b.exec(d.voidCall(Do9_.accept_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9)).exec(e.voidCall(Do9_.accept_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9)).return_(Expressions.concat(Expressions.concat(f.call(Fn9_.apply_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9), Expressions.literal("/")), g.call(Fn9_.apply_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9))))))));
            bodies.define(viaCtorM, (b) -> b.return_(Expressions.constructorRef(Wide9_.new_String_String_String_String_String_String_String_String_String, Make9_.TOKEN, Make9_.sam.method())));
            bodies.define(vctorM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, rest) -> b.exec(Expressions.voidStaticCall(Wide9_.noteAll_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, rest)).end());
            bodies.define(vapplyM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, rest) -> b.let(Wide9_.TOKEN, Expressions.new_(Wide9_.new_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), Expressions.box(PrimitiveToken.INT, Expressions.literal(1)), Expressions.box(PrimitiveToken.INT, Expressions.literal(2)))), (w) -> b.exec(w.voidCall(Wide9_.putAll_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).exec(Expressions.voidStaticCall(Wide9_.noteAll_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide9_.pickAll_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y"))), Expressions.literal("/")), Expressions.staticCall(Wide9_.joinAll_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))), Expressions.literal("/")), w.field(Wide9_.told)))));
            bodies.define(vrunM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, rest) -> b.exec(Expressions.voidStaticCall(Wide9_.noteAll_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, rest)).end());
            bodies.define(vmakeM, (b, p1, p2, p3, p4, p5, p6, p7, p8, rest) -> b.return_(Expressions.staticCall(Wide9_.joinAll_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, rest)));
            bodies.define(vlogM, (b, p1, p2, p3, p4, p5, p6, p7, p8, rest) -> b.exec(Expressions.voidStaticCall(Wide9_.noteAll_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, rest)).end());
            return bodies;
        }
    }

    static final class SubSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Wide9_.extend(h.self()));
            var becoming = sh.becoming(Widening9_.TOKEN);
            var inhI = sh.inherited(Wide9_.inh_String_String_String_String_String_String_String_String_String);
            var inhVoidI = sh.inherited(Wide9_.inhVoid_String_String_String_String_String_String_String_String_String);
            var narrowO = sh.overriding(Widening9_.narrow_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var plainO = sh.overriding(Widening9_.plain_String_String_String_String_String_String_String_String_String);
            var selfO = sh.overriding(Widening9_.self_String_String_String_String_String_String_String_String_String, becoming);
            var takeO = sh.overriding(Widening9_.take_String_String_String_String_String_String_String_String_String);
            var asWidening = sh.implements_(Widening9_.implement(h.self()).inhVoid_String_String_String_String_String_String_String_String_String(inhVoidI).inh_String_String_String_String_String_String_String_String_String(inhI).narrow_String_String_String_String_String_String_String_String_String(narrowO).plain_String_String_String_String_String_String_String_String_String(plainO).self_String_String_String_String_String_String_String_String_String(selfO).take_String_String_String_String_String_String_String_String_String(takeO));
            var cb = sh.members();
            var asWide = cb.asSuper();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var pickM = cb.declareOverride(Wide9_.pick_String_String_String_String_String_String_String_String_String);
            var csM = cb.declareOverride(Wide9_.cs_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var sameM = cb.declareOverride(Wide9_.same_String_String_String_String_String_String_String_String_String, asWide);
            var putM = cb.declareOverride(Wide9_.put_String_String_String_String_String_String_String_String_String);
            var viaAllM = cb.declareMethod("viaAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaAllVoidM = cb.declareVoidMethod("viaAllVoid", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> c.super_(Wide9_.new_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> c.super_(Wide9_.new_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p9, p9), (b, self) -> b.end()));
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p9)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p9)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.end());
            bodies.define(pickM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(Expressions.concat(Expressions.literal("sub:"), bodies.superCall(self, Wide9_.pick_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9))));
            bodies.define(csM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(Expressions.concat(Expressions.literal("cs:"), p9)));
            bodies.define(sameM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.return_(self));
            bodies.define(putM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> b.exec(bodies.superCall(self, Wide9_.put_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9)).end());
            bodies.define(viaAllM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(bodies.superCall(self, Wide9_.pickAll_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))));
            bodies.define(viaAllVoidM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.exec(bodies.superCall(self, Wide9_.putAll_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).end());
            return bodies;
        }
    }

    static final class AbsSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Abstract9_.extend(h.self()));
            var cb = sh.members();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> c.super_(Abstract9_.super_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9) -> c.super_(Abstract9_.super_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p9, p9), (b, self) -> b.end()));
            return bodies;
        }
    }

    static JavaFile plain() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Plain9"), new PlainSpec());
    }

    static JavaFile sub() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Sub9"), new SubSpec());
    }

    static JavaFile abs() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Abs9"), new AbsSpec());
    }

    @Test
    public void plainForms() throws Throwable {
        Wide9.noted = "";
        Object plain = ArityForms.create("Plain9", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"));
        Assertions.assertThat(Wide9.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9;");
        Wide9.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain9", plain, "apply", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"))).isEqualTo("pick:a1,a2,a3,a4,a5,a6,a7,a8,a9/join:a1,a2,a3,a4,a5,a6,a7,a8,a9/new:a1,a2,a3,a4,a5,a6,a7,a8,a9put:a1,a2,a3,a4,a5,a6,a7,a8,a9;");
        Assertions.assertThat(Wide9.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9;");
        Wide9.noted = "";
        ArityForms.invoke("Plain9", plain, "run", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"));
        Assertions.assertThat(Wide9.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9;");
        Wide9.noted = "";
        ArityForms.invoke("Plain9", null, "log", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"));
        Assertions.assertThat(Wide9.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9;");
        Assertions.assertThat(ArityForms.invoke("Plain9", null, "make", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"))).isEqualTo("join:a1,a2,a3,a4,a5,a6,a7,a8,a9");
        Wide9.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain9", plain, "lam", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"))).isEqualTo("fn:a9/blk:a9");
        Assertions.assertThat(Wide9.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9;note:a1,a2,a3,a4,a5,a6,a7,a8,a9;");
        Make9 make = (Make9) ArityForms.invoke("Plain9", null, "viaCtor", List.of());
        Assertions.assertThat(make.make("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9").told).isEqualTo("new:a1,a2,a3,a4,a5,a6,a7,a8,a9");
        Widening9 widening = (Widening9) plain;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9")).isEqualTo("inh:a9");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9")).isEqualTo("narrow:a9");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9")).isEqualTo("plain:a9");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9")).isSameAs(plain);
        Assertions.assertThat(widening.given("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9")).isEqualTo("given:a9");
        Assertions.assertThat(widening.chosen("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9")).isEqualTo("chosen:a9");
        Assertions.assertThat(widening.chained("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9")).isSameAs(plain);
        Wide9.noted = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9");
        widening.take("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9");
        widening.held("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9");
        Assertions.assertThat(Wide9.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9;note:a1,a2,a3,a4,a5,a6,a7,a8,a9;note:a1,a2,a3,a4,a5,a6,a7,a8,a9;");
        Wide9.noted = "";
        Object variable = ArityForms.create("Plain9", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide9.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8|[r0, r1];");
        Wide9.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain9", variable, "applyAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", (Object) new String[] {"r0", "r1"}))).isEqualTo("pickAll:a1,a2,a3,a4,a5,a6,a7,a8|[x, y]/joinAll:a1,a2,a3,a4,a5,a6,a7,a8|[x, y]/newAll:a1,a2,a3,a4,a5,a6,a7,a8|[1, 2]putAll:a1,a2,a3,a4,a5,a6,a7,a8|[x, y];");
        Assertions.assertThat(Wide9.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8|[x, y];");
        Wide9.noted = "";
        ArityForms.invoke("Plain9", variable, "runAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide9.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8|[r0, r1];");
        Wide9.noted = "";
        ArityForms.invoke("Plain9", null, "logAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide9.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8|[r0, r1];");
        Assertions.assertThat(ArityForms.invoke("Plain9", null, "makeAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", (Object) new String[] {"r0", "r1"}))).isEqualTo("joinAll:a1,a2,a3,a4,a5,a6,a7,a8|[r0, r1]");
    }

    @Test
    public void subForms() throws Throwable {
        Object sub = ArityForms.create("Sub9", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"));
        Wide9 wide = (Wide9) sub;
        Assertions.assertThat(wide.told).isEqualTo("new:a1,a2,a3,a4,a5,a6,a7,a8,a9");
        Assertions.assertThat(wide.pick("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9")).isEqualTo("sub:pick:a1,a2,a3,a4,a5,a6,a7,a8,a9");
        Assertions.assertThat(wide.cs("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9")).isEqualTo("cs:a9");
        Assertions.assertThat(wide.same("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9")).isSameAs(sub);
        wide.told = "";
        wide.put("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9");
        Assertions.assertThat(wide.told).isEqualTo("put:a1,a2,a3,a4,a5,a6,a7,a8,a9;");
        Widening9 widening = (Widening9) sub;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9")).isEqualTo("inh:a1,a2,a3,a4,a5,a6,a7,a8,a9");
        wide.told = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9");
        Assertions.assertThat(wide.told).isEqualTo("inhVoid:a1,a2,a3,a4,a5,a6,a7,a8,a9;");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9")).isEqualTo("narrow:a9");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9")).isEqualTo("plain:a9");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9")).isSameAs(sub);
        wide.told = "";
        ArityForms.invoke("Sub9", sub, "viaAllVoid", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8"));
        Assertions.assertThat(wide.told).isEqualTo("putAll:a1,a2,a3,a4,a5,a6,a7,a8|[x, y];");
        Assertions.assertThat(ArityForms.invoke("Sub9", sub, "viaAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8"))).isEqualTo("pickAll:a1,a2,a3,a4,a5,a6,a7,a8|[x, y]");
        Object other = ArityForms.create("Sub9", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", 7));
        Assertions.assertThat(((Wide9) other).told).isEqualTo("newAll:a1,a2,a3,a4,a5,a6,a7,a8|[7, 7]");
    }

    @Test
    public void absForms() throws Throwable {
        Object abs = ArityForms.create("Abs9", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"));
        Assertions.assertThat(((Abstract9) abs).told).isEqualTo("abs:a1,a2,a3,a4,a5,a6,a7,a8,a9");
        Object other = ArityForms.create("Abs9", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", 7));
        Assertions.assertThat(((Abstract9) other).told).isEqualTo("absAll:a1,a2,a3,a4,a5,a6,a7,a8|[7, 7]");
    }
}
