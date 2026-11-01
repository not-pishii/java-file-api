package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.Arity14FormsTest.AbsSpec;
import me.supcheg.javafile.typed.Arity14FormsTest.PlainSpec;
import me.supcheg.javafile.typed.Arity14FormsTest.SubSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedClassHeaderSpec;
import me.supcheg.javafile.typed.fixtures.Abstract14;
import me.supcheg.javafile.typed.fixtures.Make14;
import me.supcheg.javafile.typed.fixtures.Wide14;
import me.supcheg.javafile.typed.fixtures.Widening14;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Abstract14_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Do14_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Fn14_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Make14_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide14_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Widening14_;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * The forms of the typed layer of arity 14: a class that extends `Object`, one that extends a class and one that extends an abstract class, declared with them, are compiled with the classes of the other arities in one run of javac, and run.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
@Execution(ExecutionMode.SAME_THREAD)
class Arity14FormsTest {
    static final class PlainSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var becoming = h.becoming(Widening14_.TOKEN);
            var inhO = h.overriding(Widening14_.inh_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var inhVoidO = h.overriding(Widening14_.inhVoid_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var narrowO = h.overriding(Widening14_.narrow_String_String_String_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var plainO = h.overriding(Widening14_.plain_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var selfO = h.overriding(Widening14_.self_String_String_String_String_String_String_String_String_String_String_String_String_String_String, becoming);
            var takeO = h.overriding(Widening14_.take_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var oh = h.extendsObject();
            var asWidening = oh.implements_(Widening14_.implement(h.self()).inhVoid_String_String_String_String_String_String_String_String_String_String_String_String_String_String(inhVoidO).inh_String_String_String_String_String_String_String_String_String_String_String_String_String_String(inhO).narrow_String_String_String_String_String_String_String_String_String_String_String_String_String_String(narrowO).plain_String_String_String_String_String_String_String_String_String_String_String_String_String_String(plainO).self_String_String_String_String_String_String_String_String_String_String_String_String_String_String(selfO).take_String_String_String_String_String_String_String_String_String_String_String_String_String_String(takeO));
            var cb = oh.members();
            var givenM = cb.declareOverride(asWidening, Widening14_.given_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var chosenM = cb.declareOverride(asWidening, Widening14_.chosen_String_String_String_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var chainedM = cb.declareOverride(asWidening, Widening14_.chained_String_String_String_String_String_String_String_String_String_String_String_String_String_String, asWidening);
            var heldM = cb.declareOverride(asWidening, Widening14_.held_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var ctorM = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var applyM = cb.declareMethod("apply", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var runM = cb.declareVoidMethod("run", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var makeM = cb.declareStaticMethod("make", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var logM = cb.declareVoidStaticMethod("log", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var lamM = cb.declareMethod("lam", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaCtorM = cb.declareStaticMethod("viaCtor", Make14_.TOKEN);
            var vctorM = cb.variableArity().declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vapplyM = cb.variableArity().declareMethod("applyAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vrunM = cb.variableArity().declareVoidMethod("runAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vmakeM = cb.variableArity().declareStaticMethod("makeAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vlogM = cb.variableArity().declareVoidStaticMethod("logAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var bodies = cb.bodies();
            bodies.define(inhO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.return_(Expressions.concat(Expressions.literal("inh:"), p14)));
            bodies.define(inhVoidO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.exec(Expressions.voidStaticCall(Wide14_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)).end());
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p14)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p14)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.exec(Expressions.voidStaticCall(Wide14_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)).end());
            bodies.define(givenM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.return_(Expressions.concat(Expressions.literal("given:"), p14)));
            bodies.define(chosenM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.return_(Expressions.concat(Expressions.literal("chosen:"), p14)));
            bodies.define(chainedM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.return_(self));
            bodies.define(heldM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.exec(Expressions.voidStaticCall(Wide14_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)).end());
            bodies.define(ctorM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.exec(Expressions.voidStaticCall(Wide14_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)).end());
            bodies.define(applyM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.let(Wide14_.TOKEN, Expressions.new_(Wide14_.new_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14), (w) -> b.exec(w.voidCall(Wide14_.put_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)).exec(Expressions.voidStaticCall(Wide14_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide14_.pick_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14), Expressions.literal("/")), Expressions.staticCall(Wide14_.join_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)), Expressions.literal("/")), w.field(Wide14_.told)))));
            bodies.define(runM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.exec(Expressions.voidStaticCall(Wide14_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)).end());
            bodies.define(makeM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.return_(Expressions.staticCall(Wide14_.join_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)));
            bodies.define(logM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.exec(Expressions.voidStaticCall(Wide14_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)).end());
            bodies.define(lamM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.let(Fn14_.TOKEN, Expressions.lambda(Fn14_.sam, (q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14) -> Expressions.concat(Expressions.literal("fn:"), q14)), (f) -> b.let(Fn14_.TOKEN, Expressions.lambdaBlock(Fn14_.sam, (lb, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14) -> lb.return_(Expressions.concat(Expressions.literal("blk:"), q14))), (g) -> b.let(Do14_.TOKEN, Expressions.lambda(Do14_.sam, (q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14) -> Expressions.voidStaticCall(Wide14_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14)), (d) -> b.let(Do14_.TOKEN, Expressions.lambdaBlock(Do14_.sam, (lb, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14) -> lb.exec(Expressions.voidStaticCall(Wide14_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14)).end()), (e) -> b.exec(d.voidCall(Do14_.accept_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)).exec(e.voidCall(Do14_.accept_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)).return_(Expressions.concat(Expressions.concat(f.call(Fn14_.apply_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14), Expressions.literal("/")), g.call(Fn14_.apply_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14))))))));
            bodies.define(viaCtorM, (b) -> b.return_(Expressions.constructorRef(Wide14_.new_String_String_String_String_String_String_String_String_String_String_String_String_String_String, Make14_.TOKEN, Make14_.sam.method())));
            bodies.define(vctorM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, rest) -> b.exec(Expressions.voidStaticCall(Wide14_.noteAll_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, rest)).end());
            bodies.define(vapplyM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, rest) -> b.let(Wide14_.TOKEN, Expressions.new_(Wide14_.new_String_String_String_String_String_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), Expressions.box(PrimitiveToken.INT, Expressions.literal(1)), Expressions.box(PrimitiveToken.INT, Expressions.literal(2)))), (w) -> b.exec(w.voidCall(Wide14_.putAll_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).exec(Expressions.voidStaticCall(Wide14_.noteAll_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide14_.pickAll_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y"))), Expressions.literal("/")), Expressions.staticCall(Wide14_.joinAll_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))), Expressions.literal("/")), w.field(Wide14_.told)))));
            bodies.define(vrunM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, rest) -> b.exec(Expressions.voidStaticCall(Wide14_.noteAll_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, rest)).end());
            bodies.define(vmakeM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, rest) -> b.return_(Expressions.staticCall(Wide14_.joinAll_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, rest)));
            bodies.define(vlogM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, rest) -> b.exec(Expressions.voidStaticCall(Wide14_.noteAll_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, rest)).end());
            return bodies;
        }
    }

    static final class SubSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Wide14_.extend(h.self()));
            var becoming = sh.becoming(Widening14_.TOKEN);
            var inhI = sh.inherited(Wide14_.inh_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var inhVoidI = sh.inherited(Wide14_.inhVoid_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var narrowO = sh.overriding(Widening14_.narrow_String_String_String_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var plainO = sh.overriding(Widening14_.plain_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var selfO = sh.overriding(Widening14_.self_String_String_String_String_String_String_String_String_String_String_String_String_String_String, becoming);
            var takeO = sh.overriding(Widening14_.take_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var asWidening = sh.implements_(Widening14_.implement(h.self()).inhVoid_String_String_String_String_String_String_String_String_String_String_String_String_String_String(inhVoidI).inh_String_String_String_String_String_String_String_String_String_String_String_String_String_String(inhI).narrow_String_String_String_String_String_String_String_String_String_String_String_String_String_String(narrowO).plain_String_String_String_String_String_String_String_String_String_String_String_String_String_String(plainO).self_String_String_String_String_String_String_String_String_String_String_String_String_String_String(selfO).take_String_String_String_String_String_String_String_String_String_String_String_String_String_String(takeO));
            var cb = sh.members();
            var asWide = cb.asSuper();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var pickM = cb.declareOverride(Wide14_.pick_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var csM = cb.declareOverride(Wide14_.cs_String_String_String_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var sameM = cb.declareOverride(Wide14_.same_String_String_String_String_String_String_String_String_String_String_String_String_String_String, asWide);
            var putM = cb.declareOverride(Wide14_.put_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var viaAllM = cb.declareMethod("viaAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaAllVoidM = cb.declareVoidMethod("viaAllVoid", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> c.super_(Wide14_.new_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> c.super_(Wide14_.new_String_String_String_String_String_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p14, p14), (b, self) -> b.end()));
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p14)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p14)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.end());
            bodies.define(pickM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.return_(Expressions.concat(Expressions.literal("sub:"), bodies.superCall(self, Wide14_.pick_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14))));
            bodies.define(csM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.return_(Expressions.concat(Expressions.literal("cs:"), p14)));
            bodies.define(sameM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.return_(self));
            bodies.define(putM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> b.exec(bodies.superCall(self, Wide14_.put_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14)).end());
            bodies.define(viaAllM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13) -> b.return_(bodies.superCall(self, Wide14_.pickAll_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))));
            bodies.define(viaAllVoidM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13) -> b.exec(bodies.superCall(self, Wide14_.putAll_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).end());
            return bodies;
        }
    }

    static final class AbsSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Abstract14_.extend(h.self()));
            var cb = sh.members();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> c.super_(Abstract14_.super_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14) -> c.super_(Abstract14_.super_String_String_String_String_String_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p14, p14), (b, self) -> b.end()));
            return bodies;
        }
    }

    static JavaFile plain() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Plain14"), new PlainSpec());
    }

    static JavaFile sub() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Sub14"), new SubSpec());
    }

    static JavaFile abs() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Abs14"), new AbsSpec());
    }

    @Test
    public void plainForms() throws Throwable {
        Wide14.noted = "";
        Object plain = ArityForms.create("Plain14", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14"));
        Assertions.assertThat(Wide14.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;");
        Wide14.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain14", plain, "apply", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14"))).isEqualTo("pick:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14/join:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14/new:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14put:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;");
        Assertions.assertThat(Wide14.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;");
        Wide14.noted = "";
        ArityForms.invoke("Plain14", plain, "run", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14"));
        Assertions.assertThat(Wide14.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;");
        Wide14.noted = "";
        ArityForms.invoke("Plain14", null, "log", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14"));
        Assertions.assertThat(Wide14.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;");
        Assertions.assertThat(ArityForms.invoke("Plain14", null, "make", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14"))).isEqualTo("join:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14");
        Wide14.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain14", plain, "lam", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14"))).isEqualTo("fn:a14/blk:a14");
        Assertions.assertThat(Wide14.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;");
        Make14 make = (Make14) ArityForms.invoke("Plain14", null, "viaCtor", List.of());
        Assertions.assertThat(make.make("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14").told).isEqualTo("new:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14");
        Widening14 widening = (Widening14) plain;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14")).isEqualTo("inh:a14");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14")).isEqualTo("narrow:a14");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14")).isEqualTo("plain:a14");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14")).isSameAs(plain);
        Assertions.assertThat(widening.given("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14")).isEqualTo("given:a14");
        Assertions.assertThat(widening.chosen("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14")).isEqualTo("chosen:a14");
        Assertions.assertThat(widening.chained("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14")).isSameAs(plain);
        Wide14.noted = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14");
        widening.take("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14");
        widening.held("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14");
        Assertions.assertThat(Wide14.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;");
        Wide14.noted = "";
        Object variable = ArityForms.create("Plain14", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide14.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13|[r0, r1];");
        Wide14.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain14", variable, "applyAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", (Object) new String[] {"r0", "r1"}))).isEqualTo("pickAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13|[x, y]/joinAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13|[x, y]/newAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13|[1, 2]putAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13|[x, y];");
        Assertions.assertThat(Wide14.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13|[x, y];");
        Wide14.noted = "";
        ArityForms.invoke("Plain14", variable, "runAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide14.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13|[r0, r1];");
        Wide14.noted = "";
        ArityForms.invoke("Plain14", null, "logAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide14.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13|[r0, r1];");
        Assertions.assertThat(ArityForms.invoke("Plain14", null, "makeAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", (Object) new String[] {"r0", "r1"}))).isEqualTo("joinAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13|[r0, r1]");
    }

    @Test
    public void subForms() throws Throwable {
        Object sub = ArityForms.create("Sub14", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14"));
        Wide14 wide = (Wide14) sub;
        Assertions.assertThat(wide.told).isEqualTo("new:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14");
        Assertions.assertThat(wide.pick("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14")).isEqualTo("sub:pick:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14");
        Assertions.assertThat(wide.cs("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14")).isEqualTo("cs:a14");
        Assertions.assertThat(wide.same("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14")).isSameAs(sub);
        wide.told = "";
        wide.put("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14");
        Assertions.assertThat(wide.told).isEqualTo("put:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;");
        Widening14 widening = (Widening14) sub;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14")).isEqualTo("inh:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14");
        wide.told = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14");
        Assertions.assertThat(wide.told).isEqualTo("inhVoid:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14;");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14")).isEqualTo("narrow:a14");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14")).isEqualTo("plain:a14");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14")).isSameAs(sub);
        wide.told = "";
        ArityForms.invoke("Sub14", sub, "viaAllVoid", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13"));
        Assertions.assertThat(wide.told).isEqualTo("putAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13|[x, y];");
        Assertions.assertThat(ArityForms.invoke("Sub14", sub, "viaAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13"))).isEqualTo("pickAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13|[x, y]");
        Object other = ArityForms.create("Sub14", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", 7));
        Assertions.assertThat(((Wide14) other).told).isEqualTo("newAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13|[7, 7]");
    }

    @Test
    public void absForms() throws Throwable {
        Object abs = ArityForms.create("Abs14", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14"));
        Assertions.assertThat(((Abstract14) abs).told).isEqualTo("abs:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14");
        Object other = ArityForms.create("Abs14", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", 7));
        Assertions.assertThat(((Abstract14) other).told).isEqualTo("absAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13|[7, 7]");
    }
}
