package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.Arity8FormsTest.AbsSpec;
import me.supcheg.javafile.typed.Arity8FormsTest.PlainSpec;
import me.supcheg.javafile.typed.Arity8FormsTest.SubSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedClassHeaderSpec;
import me.supcheg.javafile.typed.fixtures.Abstract8;
import me.supcheg.javafile.typed.fixtures.Make8;
import me.supcheg.javafile.typed.fixtures.Wide8;
import me.supcheg.javafile.typed.fixtures.Widening8;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Abstract8_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Do8_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Fn8_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Make8_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide8_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Widening8_;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * The forms of the typed layer of arity 8: a class that extends `Object`, one that extends a class and one that extends an abstract class, declared with them, are compiled with the classes of the other arities in one run of javac, and run.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
@Execution(ExecutionMode.SAME_THREAD)
class Arity8FormsTest {
    static final class PlainSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var becoming = h.becoming(Widening8_.TOKEN);
            var inhO = h.overriding(Widening8_.inh_String_String_String_String_String_String_String_String);
            var inhVoidO = h.overriding(Widening8_.inhVoid_String_String_String_String_String_String_String_String);
            var narrowO = h.overriding(Widening8_.narrow_String_String_String_String_String_String_String_String, String_.TOKEN);
            var plainO = h.overriding(Widening8_.plain_String_String_String_String_String_String_String_String);
            var selfO = h.overriding(Widening8_.self_String_String_String_String_String_String_String_String, becoming);
            var takeO = h.overriding(Widening8_.take_String_String_String_String_String_String_String_String);
            var oh = h.extendsObject();
            var asWidening = oh.implements_(Widening8_.implement(h.self()).inhVoid_String_String_String_String_String_String_String_String(inhVoidO).inh_String_String_String_String_String_String_String_String(inhO).narrow_String_String_String_String_String_String_String_String(narrowO).plain_String_String_String_String_String_String_String_String(plainO).self_String_String_String_String_String_String_String_String(selfO).take_String_String_String_String_String_String_String_String(takeO));
            var cb = oh.members();
            var givenM = cb.declareOverride(asWidening, Widening8_.given_String_String_String_String_String_String_String_String);
            var chosenM = cb.declareOverride(asWidening, Widening8_.chosen_String_String_String_String_String_String_String_String, String_.TOKEN);
            var chainedM = cb.declareOverride(asWidening, Widening8_.chained_String_String_String_String_String_String_String_String, asWidening);
            var heldM = cb.declareOverride(asWidening, Widening8_.held_String_String_String_String_String_String_String_String);
            var ctorM = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var applyM = cb.declareMethod("apply", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var runM = cb.declareVoidMethod("run", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var makeM = cb.declareStaticMethod("make", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var logM = cb.declareVoidStaticMethod("log", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var lamM = cb.declareMethod("lam", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaCtorM = cb.declareStaticMethod("viaCtor", Make8_.TOKEN);
            var vctorM = cb.variableArity().declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vapplyM = cb.variableArity().declareMethod("applyAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vrunM = cb.variableArity().declareVoidMethod("runAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vmakeM = cb.variableArity().declareStaticMethod("makeAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vlogM = cb.variableArity().declareVoidStaticMethod("logAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var bodies = cb.bodies();
            bodies.define(inhO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(Expressions.concat(Expressions.literal("inh:"), p8)));
            bodies.define(inhVoidO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.exec(Expressions.voidStaticCall(Wide8_.note_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8)).end());
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p8)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p8)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.exec(Expressions.voidStaticCall(Wide8_.note_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8)).end());
            bodies.define(givenM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(Expressions.concat(Expressions.literal("given:"), p8)));
            bodies.define(chosenM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(Expressions.concat(Expressions.literal("chosen:"), p8)));
            bodies.define(chainedM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(self));
            bodies.define(heldM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.exec(Expressions.voidStaticCall(Wide8_.note_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8)).end());
            bodies.define(ctorM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.exec(Expressions.voidStaticCall(Wide8_.note_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8)).end());
            bodies.define(applyM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.let(Wide8_.TOKEN, Expressions.new_(Wide8_.new_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8), (w) -> b.exec(w.voidCall(Wide8_.put_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8)).exec(Expressions.voidStaticCall(Wide8_.note_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8)).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide8_.pick_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8), Expressions.literal("/")), Expressions.staticCall(Wide8_.join_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8)), Expressions.literal("/")), w.field(Wide8_.told)))));
            bodies.define(runM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.exec(Expressions.voidStaticCall(Wide8_.note_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8)).end());
            bodies.define(makeM, (b, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(Expressions.staticCall(Wide8_.join_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8)));
            bodies.define(logM, (b, p1, p2, p3, p4, p5, p6, p7, p8) -> b.exec(Expressions.voidStaticCall(Wide8_.note_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8)).end());
            bodies.define(lamM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.let(Fn8_.TOKEN, Expressions.lambda(Fn8_.sam, (q1, q2, q3, q4, q5, q6, q7, q8) -> Expressions.concat(Expressions.literal("fn:"), q8)), (f) -> b.let(Fn8_.TOKEN, Expressions.lambdaBlock(Fn8_.sam, (lb, q1, q2, q3, q4, q5, q6, q7, q8) -> lb.return_(Expressions.concat(Expressions.literal("blk:"), q8))), (g) -> b.let(Do8_.TOKEN, Expressions.lambda(Do8_.sam, (q1, q2, q3, q4, q5, q6, q7, q8) -> Expressions.voidStaticCall(Wide8_.note_String_String_String_String_String_String_String_String, q1, q2, q3, q4, q5, q6, q7, q8)), (d) -> b.let(Do8_.TOKEN, Expressions.lambdaBlock(Do8_.sam, (lb, q1, q2, q3, q4, q5, q6, q7, q8) -> lb.exec(Expressions.voidStaticCall(Wide8_.note_String_String_String_String_String_String_String_String, q1, q2, q3, q4, q5, q6, q7, q8)).end()), (e) -> b.exec(d.voidCall(Do8_.accept_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8)).exec(e.voidCall(Do8_.accept_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8)).return_(Expressions.concat(Expressions.concat(f.call(Fn8_.apply_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8), Expressions.literal("/")), g.call(Fn8_.apply_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8))))))));
            bodies.define(viaCtorM, (b) -> b.return_(Expressions.constructorRef(Wide8_.new_String_String_String_String_String_String_String_String, Make8_.TOKEN, Make8_.sam.method())));
            bodies.define(vctorM, (b, self, p1, p2, p3, p4, p5, p6, p7, rest) -> b.exec(Expressions.voidStaticCall(Wide8_.noteAll_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, rest)).end());
            bodies.define(vapplyM, (b, self, p1, p2, p3, p4, p5, p6, p7, rest) -> b.let(Wide8_.TOKEN, Expressions.new_(Wide8_.new_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), Expressions.box(PrimitiveToken.INT, Expressions.literal(1)), Expressions.box(PrimitiveToken.INT, Expressions.literal(2)))), (w) -> b.exec(w.voidCall(Wide8_.putAll_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).exec(Expressions.voidStaticCall(Wide8_.noteAll_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide8_.pickAll_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y"))), Expressions.literal("/")), Expressions.staticCall(Wide8_.joinAll_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))), Expressions.literal("/")), w.field(Wide8_.told)))));
            bodies.define(vrunM, (b, self, p1, p2, p3, p4, p5, p6, p7, rest) -> b.exec(Expressions.voidStaticCall(Wide8_.noteAll_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, rest)).end());
            bodies.define(vmakeM, (b, p1, p2, p3, p4, p5, p6, p7, rest) -> b.return_(Expressions.staticCall(Wide8_.joinAll_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, rest)));
            bodies.define(vlogM, (b, p1, p2, p3, p4, p5, p6, p7, rest) -> b.exec(Expressions.voidStaticCall(Wide8_.noteAll_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, rest)).end());
            return bodies;
        }
    }

    static final class SubSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Wide8_.extend(h.self()));
            var becoming = sh.becoming(Widening8_.TOKEN);
            var inhI = sh.inherited(Wide8_.inh_String_String_String_String_String_String_String_String);
            var inhVoidI = sh.inherited(Wide8_.inhVoid_String_String_String_String_String_String_String_String);
            var narrowO = sh.overriding(Widening8_.narrow_String_String_String_String_String_String_String_String, String_.TOKEN);
            var plainO = sh.overriding(Widening8_.plain_String_String_String_String_String_String_String_String);
            var selfO = sh.overriding(Widening8_.self_String_String_String_String_String_String_String_String, becoming);
            var takeO = sh.overriding(Widening8_.take_String_String_String_String_String_String_String_String);
            var asWidening = sh.implements_(Widening8_.implement(h.self()).inhVoid_String_String_String_String_String_String_String_String(inhVoidI).inh_String_String_String_String_String_String_String_String(inhI).narrow_String_String_String_String_String_String_String_String(narrowO).plain_String_String_String_String_String_String_String_String(plainO).self_String_String_String_String_String_String_String_String(selfO).take_String_String_String_String_String_String_String_String(takeO));
            var cb = sh.members();
            var asWide = cb.asSuper();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var pickM = cb.declareOverride(Wide8_.pick_String_String_String_String_String_String_String_String);
            var csM = cb.declareOverride(Wide8_.cs_String_String_String_String_String_String_String_String, String_.TOKEN);
            var sameM = cb.declareOverride(Wide8_.same_String_String_String_String_String_String_String_String, asWide);
            var putM = cb.declareOverride(Wide8_.put_String_String_String_String_String_String_String_String);
            var viaAllM = cb.declareMethod("viaAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaAllVoidM = cb.declareVoidMethod("viaAllVoid", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5, p6, p7, p8) -> c.super_(Wide8_.new_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5, p6, p7, p8) -> c.super_(Wide8_.new_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p8, p8), (b, self) -> b.end()));
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p8)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p8)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.end());
            bodies.define(pickM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(Expressions.concat(Expressions.literal("sub:"), bodies.superCall(self, Wide8_.pick_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8))));
            bodies.define(csM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(Expressions.concat(Expressions.literal("cs:"), p8)));
            bodies.define(sameM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.return_(self));
            bodies.define(putM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8) -> b.exec(bodies.superCall(self, Wide8_.put_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8)).end());
            bodies.define(viaAllM, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(bodies.superCall(self, Wide8_.pickAll_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))));
            bodies.define(viaAllVoidM, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.exec(bodies.superCall(self, Wide8_.putAll_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).end());
            return bodies;
        }
    }

    static final class AbsSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Abstract8_.extend(h.self()));
            var cb = sh.members();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5, p6, p7, p8) -> c.super_(Abstract8_.super_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5, p6, p7, p8) -> c.super_(Abstract8_.super_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p8, p8), (b, self) -> b.end()));
            return bodies;
        }
    }

    static JavaFile plain() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Plain8"), new PlainSpec());
    }

    static JavaFile sub() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Sub8"), new SubSpec());
    }

    static JavaFile abs() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Abs8"), new AbsSpec());
    }

    @Test
    public void plainForms() throws Throwable {
        Wide8.noted = "";
        Object plain = ArityForms.create("Plain8", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8"));
        Assertions.assertThat(Wide8.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8;");
        Wide8.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain8", plain, "apply", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8"))).isEqualTo("pick:a1,a2,a3,a4,a5,a6,a7,a8/join:a1,a2,a3,a4,a5,a6,a7,a8/new:a1,a2,a3,a4,a5,a6,a7,a8put:a1,a2,a3,a4,a5,a6,a7,a8;");
        Assertions.assertThat(Wide8.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8;");
        Wide8.noted = "";
        ArityForms.invoke("Plain8", plain, "run", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8"));
        Assertions.assertThat(Wide8.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8;");
        Wide8.noted = "";
        ArityForms.invoke("Plain8", null, "log", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8"));
        Assertions.assertThat(Wide8.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8;");
        Assertions.assertThat(ArityForms.invoke("Plain8", null, "make", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8"))).isEqualTo("join:a1,a2,a3,a4,a5,a6,a7,a8");
        Wide8.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain8", plain, "lam", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8"))).isEqualTo("fn:a8/blk:a8");
        Assertions.assertThat(Wide8.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8;note:a1,a2,a3,a4,a5,a6,a7,a8;");
        Make8 make = (Make8) ArityForms.invoke("Plain8", null, "viaCtor", List.of());
        Assertions.assertThat(make.make("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8").told).isEqualTo("new:a1,a2,a3,a4,a5,a6,a7,a8");
        Widening8 widening = (Widening8) plain;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8")).isEqualTo("inh:a8");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8")).isEqualTo("narrow:a8");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8")).isEqualTo("plain:a8");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8")).isSameAs(plain);
        Assertions.assertThat(widening.given("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8")).isEqualTo("given:a8");
        Assertions.assertThat(widening.chosen("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8")).isEqualTo("chosen:a8");
        Assertions.assertThat(widening.chained("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8")).isSameAs(plain);
        Wide8.noted = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8");
        widening.take("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8");
        widening.held("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8");
        Assertions.assertThat(Wide8.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8;note:a1,a2,a3,a4,a5,a6,a7,a8;note:a1,a2,a3,a4,a5,a6,a7,a8;");
        Wide8.noted = "";
        Object variable = ArityForms.create("Plain8", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide8.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7|[r0, r1];");
        Wide8.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain8", variable, "applyAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", (Object) new String[] {"r0", "r1"}))).isEqualTo("pickAll:a1,a2,a3,a4,a5,a6,a7|[x, y]/joinAll:a1,a2,a3,a4,a5,a6,a7|[x, y]/newAll:a1,a2,a3,a4,a5,a6,a7|[1, 2]putAll:a1,a2,a3,a4,a5,a6,a7|[x, y];");
        Assertions.assertThat(Wide8.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7|[x, y];");
        Wide8.noted = "";
        ArityForms.invoke("Plain8", variable, "runAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide8.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7|[r0, r1];");
        Wide8.noted = "";
        ArityForms.invoke("Plain8", null, "logAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide8.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7|[r0, r1];");
        Assertions.assertThat(ArityForms.invoke("Plain8", null, "makeAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", (Object) new String[] {"r0", "r1"}))).isEqualTo("joinAll:a1,a2,a3,a4,a5,a6,a7|[r0, r1]");
    }

    @Test
    public void subForms() throws Throwable {
        Object sub = ArityForms.create("Sub8", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8"));
        Wide8 wide = (Wide8) sub;
        Assertions.assertThat(wide.told).isEqualTo("new:a1,a2,a3,a4,a5,a6,a7,a8");
        Assertions.assertThat(wide.pick("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8")).isEqualTo("sub:pick:a1,a2,a3,a4,a5,a6,a7,a8");
        Assertions.assertThat(wide.cs("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8")).isEqualTo("cs:a8");
        Assertions.assertThat(wide.same("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8")).isSameAs(sub);
        wide.told = "";
        wide.put("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8");
        Assertions.assertThat(wide.told).isEqualTo("put:a1,a2,a3,a4,a5,a6,a7,a8;");
        Widening8 widening = (Widening8) sub;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8")).isEqualTo("inh:a1,a2,a3,a4,a5,a6,a7,a8");
        wide.told = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8");
        Assertions.assertThat(wide.told).isEqualTo("inhVoid:a1,a2,a3,a4,a5,a6,a7,a8;");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8")).isEqualTo("narrow:a8");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8")).isEqualTo("plain:a8");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8")).isSameAs(sub);
        wide.told = "";
        ArityForms.invoke("Sub8", sub, "viaAllVoid", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7"));
        Assertions.assertThat(wide.told).isEqualTo("putAll:a1,a2,a3,a4,a5,a6,a7|[x, y];");
        Assertions.assertThat(ArityForms.invoke("Sub8", sub, "viaAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7"))).isEqualTo("pickAll:a1,a2,a3,a4,a5,a6,a7|[x, y]");
        Object other = ArityForms.create("Sub8", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", 7));
        Assertions.assertThat(((Wide8) other).told).isEqualTo("newAll:a1,a2,a3,a4,a5,a6,a7|[7, 7]");
    }

    @Test
    public void absForms() throws Throwable {
        Object abs = ArityForms.create("Abs8", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8"));
        Assertions.assertThat(((Abstract8) abs).told).isEqualTo("abs:a1,a2,a3,a4,a5,a6,a7,a8");
        Object other = ArityForms.create("Abs8", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", 7));
        Assertions.assertThat(((Abstract8) other).told).isEqualTo("absAll:a1,a2,a3,a4,a5,a6,a7|[7, 7]");
    }
}
