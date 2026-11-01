package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.Arity16FormsTest.AbsSpec;
import me.supcheg.javafile.typed.Arity16FormsTest.PlainSpec;
import me.supcheg.javafile.typed.Arity16FormsTest.SubSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedClassHeaderSpec;
import me.supcheg.javafile.typed.fixtures.Abstract16;
import me.supcheg.javafile.typed.fixtures.Make16;
import me.supcheg.javafile.typed.fixtures.Wide16;
import me.supcheg.javafile.typed.fixtures.Widening16;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Abstract16_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Do16_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Fn16_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Make16_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide16_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Widening16_;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * The forms of the typed layer of arity 16: a class that extends `Object`, one that extends a class and one that extends an abstract class, declared with them, are compiled with the classes of the other arities in one run of javac, and run.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
@Execution(ExecutionMode.SAME_THREAD)
class Arity16FormsTest {
    static final class PlainSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var becoming = h.becoming(Widening16_.TOKEN);
            var inhO = h.overriding(Widening16_.inh_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var inhVoidO = h.overriding(Widening16_.inhVoid_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var narrowO = h.overriding(Widening16_.narrow_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var plainO = h.overriding(Widening16_.plain_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var selfO = h.overriding(Widening16_.self_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, becoming);
            var takeO = h.overriding(Widening16_.take_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var oh = h.extendsObject();
            var asWidening = oh.implements_(Widening16_.implement(h.self()).inhVoid_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String(inhVoidO).inh_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String(inhO).narrow_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String(narrowO).plain_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String(plainO).self_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String(selfO).take_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String(takeO));
            var cb = oh.members();
            var givenM = cb.declareOverride(asWidening, Widening16_.given_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var chosenM = cb.declareOverride(asWidening, Widening16_.chosen_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var chainedM = cb.declareOverride(asWidening, Widening16_.chained_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, asWidening);
            var heldM = cb.declareOverride(asWidening, Widening16_.held_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var ctorM = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var applyM = cb.declareMethod("apply", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var runM = cb.declareVoidMethod("run", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var makeM = cb.declareStaticMethod("make", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var logM = cb.declareVoidStaticMethod("log", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var lamM = cb.declareMethod("lam", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaCtorM = cb.declareStaticMethod("viaCtor", Make16_.TOKEN);
            var vctorM = cb.variableArity().declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vapplyM = cb.variableArity().declareMethod("applyAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vrunM = cb.variableArity().declareVoidMethod("runAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vmakeM = cb.variableArity().declareStaticMethod("makeAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vlogM = cb.variableArity().declareVoidStaticMethod("logAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var bodies = cb.bodies();
            bodies.define(inhO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.return_(Expressions.concat(Expressions.literal("inh:"), p16)));
            bodies.define(inhVoidO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.exec(Expressions.voidStaticCall(Wide16_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)).end());
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p16)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p16)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.exec(Expressions.voidStaticCall(Wide16_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)).end());
            bodies.define(givenM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.return_(Expressions.concat(Expressions.literal("given:"), p16)));
            bodies.define(chosenM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.return_(Expressions.concat(Expressions.literal("chosen:"), p16)));
            bodies.define(chainedM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.return_(self));
            bodies.define(heldM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.exec(Expressions.voidStaticCall(Wide16_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)).end());
            bodies.define(ctorM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.exec(Expressions.voidStaticCall(Wide16_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)).end());
            bodies.define(applyM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.let(Wide16_.TOKEN, Expressions.new_(Wide16_.new_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16), (w) -> b.exec(w.voidCall(Wide16_.put_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)).exec(Expressions.voidStaticCall(Wide16_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide16_.pick_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16), Expressions.literal("/")), Expressions.staticCall(Wide16_.join_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)), Expressions.literal("/")), w.field(Wide16_.told)))));
            bodies.define(runM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.exec(Expressions.voidStaticCall(Wide16_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)).end());
            bodies.define(makeM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.return_(Expressions.staticCall(Wide16_.join_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)));
            bodies.define(logM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.exec(Expressions.voidStaticCall(Wide16_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)).end());
            bodies.define(lamM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.let(Fn16_.TOKEN, Expressions.lambda(Fn16_.sam, (q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16) -> Expressions.concat(Expressions.literal("fn:"), q16)), (f) -> b.let(Fn16_.TOKEN, Expressions.lambdaBlock(Fn16_.sam, (lb, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16) -> lb.return_(Expressions.concat(Expressions.literal("blk:"), q16))), (g) -> b.let(Do16_.TOKEN, Expressions.lambda(Do16_.sam, (q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16) -> Expressions.voidStaticCall(Wide16_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16)), (d) -> b.let(Do16_.TOKEN, Expressions.lambdaBlock(Do16_.sam, (lb, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16) -> lb.exec(Expressions.voidStaticCall(Wide16_.note_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16)).end()), (e) -> b.exec(d.voidCall(Do16_.accept_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)).exec(e.voidCall(Do16_.accept_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)).return_(Expressions.concat(Expressions.concat(f.call(Fn16_.apply_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16), Expressions.literal("/")), g.call(Fn16_.apply_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16))))))));
            bodies.define(viaCtorM, (b) -> b.return_(Expressions.constructorRef(Wide16_.new_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, Make16_.TOKEN, Make16_.sam.method())));
            bodies.define(vctorM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, rest) -> b.exec(Expressions.voidStaticCall(Wide16_.noteAll_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, rest)).end());
            bodies.define(vapplyM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, rest) -> b.let(Wide16_.TOKEN, Expressions.new_(Wide16_.new_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), Expressions.box(PrimitiveToken.INT, Expressions.literal(1)), Expressions.box(PrimitiveToken.INT, Expressions.literal(2)))), (w) -> b.exec(w.voidCall(Wide16_.putAll_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).exec(Expressions.voidStaticCall(Wide16_.noteAll_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide16_.pickAll_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y"))), Expressions.literal("/")), Expressions.staticCall(Wide16_.joinAll_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))), Expressions.literal("/")), w.field(Wide16_.told)))));
            bodies.define(vrunM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, rest) -> b.exec(Expressions.voidStaticCall(Wide16_.noteAll_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, rest)).end());
            bodies.define(vmakeM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, rest) -> b.return_(Expressions.staticCall(Wide16_.joinAll_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, rest)));
            bodies.define(vlogM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, rest) -> b.exec(Expressions.voidStaticCall(Wide16_.noteAll_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, rest)).end());
            return bodies;
        }
    }

    static final class SubSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Wide16_.extend(h.self()));
            var becoming = sh.becoming(Widening16_.TOKEN);
            var inhI = sh.inherited(Wide16_.inh_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var inhVoidI = sh.inherited(Wide16_.inhVoid_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var narrowO = sh.overriding(Widening16_.narrow_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var plainO = sh.overriding(Widening16_.plain_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var selfO = sh.overriding(Widening16_.self_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, becoming);
            var takeO = sh.overriding(Widening16_.take_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var asWidening = sh.implements_(Widening16_.implement(h.self()).inhVoid_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String(inhVoidI).inh_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String(inhI).narrow_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String(narrowO).plain_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String(plainO).self_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String(selfO).take_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String(takeO));
            var cb = sh.members();
            var asWide = cb.asSuper();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var pickM = cb.declareOverride(Wide16_.pick_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var csM = cb.declareOverride(Wide16_.cs_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var sameM = cb.declareOverride(Wide16_.same_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, asWide);
            var putM = cb.declareOverride(Wide16_.put_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String);
            var viaAllM = cb.declareMethod("viaAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaAllVoidM = cb.declareVoidMethod("viaAllVoid", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> c.super_(Wide16_.new_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> c.super_(Wide16_.new_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p16, p16), (b, self) -> b.end()));
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p16)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p16)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.end());
            bodies.define(pickM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.return_(Expressions.concat(Expressions.literal("sub:"), bodies.superCall(self, Wide16_.pick_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16))));
            bodies.define(csM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.return_(Expressions.concat(Expressions.literal("cs:"), p16)));
            bodies.define(sameM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.return_(self));
            bodies.define(putM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> b.exec(bodies.superCall(self, Wide16_.put_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16)).end());
            bodies.define(viaAllM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15) -> b.return_(bodies.superCall(self, Wide16_.pickAll_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))));
            bodies.define(viaAllVoidM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15) -> b.exec(bodies.superCall(self, Wide16_.putAll_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).end());
            return bodies;
        }
    }

    static final class AbsSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Abstract16_.extend(h.self()));
            var cb = sh.members();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> c.super_(Abstract16_.super_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) -> c.super_(Abstract16_.super_String_String_String_String_String_String_String_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p16, p16), (b, self) -> b.end()));
            return bodies;
        }
    }

    static JavaFile plain() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Plain16"), new PlainSpec());
    }

    static JavaFile sub() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Sub16"), new SubSpec());
    }

    static JavaFile abs() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Abs16"), new AbsSpec());
    }

    @Test
    public void plainForms() throws Throwable {
        Wide16.noted = "";
        Object plain = ArityForms.create("Plain16", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16"));
        Assertions.assertThat(Wide16.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16;");
        Wide16.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain16", plain, "apply", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16"))).isEqualTo("pick:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16/join:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16/new:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16put:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16;");
        Assertions.assertThat(Wide16.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16;");
        Wide16.noted = "";
        ArityForms.invoke("Plain16", plain, "run", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16"));
        Assertions.assertThat(Wide16.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16;");
        Wide16.noted = "";
        ArityForms.invoke("Plain16", null, "log", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16"));
        Assertions.assertThat(Wide16.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16;");
        Assertions.assertThat(ArityForms.invoke("Plain16", null, "make", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16"))).isEqualTo("join:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16");
        Wide16.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain16", plain, "lam", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16"))).isEqualTo("fn:a16/blk:a16");
        Assertions.assertThat(Wide16.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16;note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16;");
        Make16 make = (Make16) ArityForms.invoke("Plain16", null, "viaCtor", List.of());
        Assertions.assertThat(make.make("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16").told).isEqualTo("new:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16");
        Widening16 widening = (Widening16) plain;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16")).isEqualTo("inh:a16");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16")).isEqualTo("narrow:a16");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16")).isEqualTo("plain:a16");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16")).isSameAs(plain);
        Assertions.assertThat(widening.given("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16")).isEqualTo("given:a16");
        Assertions.assertThat(widening.chosen("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16")).isEqualTo("chosen:a16");
        Assertions.assertThat(widening.chained("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16")).isSameAs(plain);
        Wide16.noted = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16");
        widening.take("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16");
        widening.held("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16");
        Assertions.assertThat(Wide16.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16;note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16;note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16;");
        Wide16.noted = "";
        Object variable = ArityForms.create("Plain16", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide16.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15|[r0, r1];");
        Wide16.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain16", variable, "applyAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", (Object) new String[] {"r0", "r1"}))).isEqualTo("pickAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15|[x, y]/joinAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15|[x, y]/newAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15|[1, 2]putAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15|[x, y];");
        Assertions.assertThat(Wide16.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15|[x, y];");
        Wide16.noted = "";
        ArityForms.invoke("Plain16", variable, "runAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide16.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15|[r0, r1];");
        Wide16.noted = "";
        ArityForms.invoke("Plain16", null, "logAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide16.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15|[r0, r1];");
        Assertions.assertThat(ArityForms.invoke("Plain16", null, "makeAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", (Object) new String[] {"r0", "r1"}))).isEqualTo("joinAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15|[r0, r1]");
    }

    @Test
    public void subForms() throws Throwable {
        Object sub = ArityForms.create("Sub16", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16"));
        Wide16 wide = (Wide16) sub;
        Assertions.assertThat(wide.told).isEqualTo("new:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16");
        Assertions.assertThat(wide.pick("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16")).isEqualTo("sub:pick:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16");
        Assertions.assertThat(wide.cs("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16")).isEqualTo("cs:a16");
        Assertions.assertThat(wide.same("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16")).isSameAs(sub);
        wide.told = "";
        wide.put("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16");
        Assertions.assertThat(wide.told).isEqualTo("put:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16;");
        Widening16 widening = (Widening16) sub;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16")).isEqualTo("inh:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16");
        wide.told = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16");
        Assertions.assertThat(wide.told).isEqualTo("inhVoid:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16;");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16")).isEqualTo("narrow:a16");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16")).isEqualTo("plain:a16");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16")).isSameAs(sub);
        wide.told = "";
        ArityForms.invoke("Sub16", sub, "viaAllVoid", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15"));
        Assertions.assertThat(wide.told).isEqualTo("putAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15|[x, y];");
        Assertions.assertThat(ArityForms.invoke("Sub16", sub, "viaAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15"))).isEqualTo("pickAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15|[x, y]");
        Object other = ArityForms.create("Sub16", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", 7));
        Assertions.assertThat(((Wide16) other).told).isEqualTo("newAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15|[7, 7]");
    }

    @Test
    public void absForms() throws Throwable {
        Object abs = ArityForms.create("Abs16", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", "a16"));
        Assertions.assertThat(((Abstract16) abs).told).isEqualTo("abs:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16");
        Object other = ArityForms.create("Abs16", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12", "a13", "a14", "a15", 7));
        Assertions.assertThat(((Abstract16) other).told).isEqualTo("absAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15|[7, 7]");
    }
}
