package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.Arity12FormsTest.AbsSpec;
import me.supcheg.javafile.typed.Arity12FormsTest.PlainSpec;
import me.supcheg.javafile.typed.Arity12FormsTest.SubSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedClassHeaderSpec;
import me.supcheg.javafile.typed.fixtures.Abstract12;
import me.supcheg.javafile.typed.fixtures.Wide12;
import me.supcheg.javafile.typed.fixtures.Widening12;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Abstract12_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Do12_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Fn12_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide12_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Widening12_;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * The forms of the typed layer of arity 12: a class that extends `Object`, one that extends a class and one that extends an abstract class, declared with them, are compiled with the classes of the other arities in one run of javac, and run.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
@Execution(ExecutionMode.SAME_THREAD)
class Arity12FormsTest {
    static final class PlainSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var becoming = h.becoming(Widening12_.TOKEN);
            var inhO = h.overriding(Widening12_.inh_String_String_String_String_String_String_String_String_String_String_String_String);
            var inhVoidO = h.overriding(Widening12_.inhVoid_String_String_String_String_String_String_String_String_String_String_String_String);
            var narrowO = h.overriding(Widening12_.narrow_String_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var plainO = h.overriding(Widening12_.plain_String_String_String_String_String_String_String_String_String_String_String_String);
            var selfO = h.overriding(Widening12_.self_String_String_String_String_String_String_String_String_String_String_String_String, becoming);
            var takeO = h.overriding(Widening12_.take_String_String_String_String_String_String_String_String_String_String_String_String);
            var oh = h.extendsObject();
            var asWidening = oh.implements_(Widening12_.implement(h.self()).inhVoid_String_String_String_String_String_String_String_String_String_String_String_String(inhVoidO).inh_String_String_String_String_String_String_String_String_String_String_String_String(inhO).narrow_String_String_String_String_String_String_String_String_String_String_String_String(narrowO).plain_String_String_String_String_String_String_String_String_String_String_String_String(plainO).self_String_String_String_String_String_String_String_String_String_String_String_String(selfO).take_String_String_String_String_String_String_String_String_String_String_String_String(takeO));
            var cb = oh.members();
            var givenM = cb.declareOverride(asWidening, Widening12_.given_String_String_String_String_String_String_String_String_String_String_String_String);
            var chosenM = cb.declareOverride(asWidening, Widening12_.chosen_String_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var chainedM = cb.declareOverride(asWidening, Widening12_.chained_String_String_String_String_String_String_String_String_String_String_String_String, asWidening);
            var heldM = cb.declareOverride(asWidening, Widening12_.held_String_String_String_String_String_String_String_String_String_String_String_String);
            var ctorM = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var applyM = cb.declareMethod("apply", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var runM = cb.declareVoidMethod("run", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var makeM = cb.declareStaticMethod("make", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var logM = cb.declareVoidStaticMethod("log", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var lamM = cb.declareMethod("lam", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var vctorM = cb.variableArity().declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vapplyM = cb.variableArity().declareMethod("applyAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vrunM = cb.variableArity().declareVoidMethod("runAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vmakeM = cb.variableArity().declareStaticMethod("makeAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vlogM = cb.variableArity().declareVoidStaticMethod("logAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var bodies = cb.bodies();
            bodies.define(inhO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(Expressions.concat(Expressions.literal("inh:"), p12)));
            bodies.define(inhVoidO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.exec(Expressions.voidStaticCall(Wide12_.note_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)).end());
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p12)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p12)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.exec(Expressions.voidStaticCall(Wide12_.note_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)).end());
            bodies.define(givenM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(Expressions.concat(Expressions.literal("given:"), p12)));
            bodies.define(chosenM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(Expressions.concat(Expressions.literal("chosen:"), p12)));
            bodies.define(chainedM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(self));
            bodies.define(heldM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.exec(Expressions.voidStaticCall(Wide12_.note_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)).end());
            bodies.define(ctorM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.exec(Expressions.voidStaticCall(Wide12_.note_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)).end());
            bodies.define(applyM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.let(Wide12_.TOKEN, Expressions.new_(Wide12_.new_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), (w) -> b.exec(w.voidCall(Wide12_.put_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)).exec(Expressions.voidStaticCall(Wide12_.note_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide12_.pick_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), Expressions.literal("/")), Expressions.staticCall(Wide12_.join_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)), Expressions.literal("/")), w.field(Wide12_.told)))));
            bodies.define(runM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.exec(Expressions.voidStaticCall(Wide12_.note_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)).end());
            bodies.define(makeM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(Expressions.staticCall(Wide12_.join_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)));
            bodies.define(logM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.exec(Expressions.voidStaticCall(Wide12_.note_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)).end());
            bodies.define(lamM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.let(Fn12_.TOKEN, Expressions.lambda(Fn12_.sam, (q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12) -> Expressions.concat(Expressions.literal("fn:"), q12)), (f) -> b.let(Fn12_.TOKEN, Expressions.lambdaBlock(Fn12_.sam, (lb, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12) -> lb.return_(Expressions.concat(Expressions.literal("blk:"), q12))), (g) -> b.let(Do12_.TOKEN, Expressions.lambda(Do12_.sam, (q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12) -> Expressions.voidStaticCall(Wide12_.note_String_String_String_String_String_String_String_String_String_String_String_String, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12)), (d) -> b.let(Do12_.TOKEN, Expressions.lambdaBlock(Do12_.sam, (lb, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12) -> lb.exec(Expressions.voidStaticCall(Wide12_.note_String_String_String_String_String_String_String_String_String_String_String_String, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12)).end()), (e) -> b.exec(d.voidCall(Do12_.accept_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)).exec(e.voidCall(Do12_.accept_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)).return_(Expressions.concat(Expressions.concat(f.call(Fn12_.apply_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12), Expressions.literal("/")), g.call(Fn12_.apply_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12))))))));
            bodies.define(vctorM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, rest) -> b.exec(Expressions.voidStaticCall(Wide12_.noteAll_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, rest)).end());
            bodies.define(vapplyM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, rest) -> b.let(Wide12_.TOKEN, Expressions.new_(Wide12_.new_String_String_String_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), Expressions.box(PrimitiveToken.INT, Expressions.literal(1)), Expressions.box(PrimitiveToken.INT, Expressions.literal(2)))), (w) -> b.exec(w.voidCall(Wide12_.putAll_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).exec(Expressions.voidStaticCall(Wide12_.noteAll_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide12_.pickAll_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y"))), Expressions.literal("/")), Expressions.staticCall(Wide12_.joinAll_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))), Expressions.literal("/")), w.field(Wide12_.told)))));
            bodies.define(vrunM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, rest) -> b.exec(Expressions.voidStaticCall(Wide12_.noteAll_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, rest)).end());
            bodies.define(vmakeM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, rest) -> b.return_(Expressions.staticCall(Wide12_.joinAll_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, rest)));
            bodies.define(vlogM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, rest) -> b.exec(Expressions.voidStaticCall(Wide12_.noteAll_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, rest)).end());
            return bodies;
        }
    }

    static final class SubSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Wide12_.extend(h.self()));
            var becoming = sh.becoming(Widening12_.TOKEN);
            var inhI = sh.inherited(Wide12_.inh_String_String_String_String_String_String_String_String_String_String_String_String);
            var inhVoidI = sh.inherited(Wide12_.inhVoid_String_String_String_String_String_String_String_String_String_String_String_String);
            var narrowO = sh.overriding(Widening12_.narrow_String_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var plainO = sh.overriding(Widening12_.plain_String_String_String_String_String_String_String_String_String_String_String_String);
            var selfO = sh.overriding(Widening12_.self_String_String_String_String_String_String_String_String_String_String_String_String, becoming);
            var takeO = sh.overriding(Widening12_.take_String_String_String_String_String_String_String_String_String_String_String_String);
            var asWidening = sh.implements_(Widening12_.implement(h.self()).inhVoid_String_String_String_String_String_String_String_String_String_String_String_String(inhVoidI).inh_String_String_String_String_String_String_String_String_String_String_String_String(inhI).narrow_String_String_String_String_String_String_String_String_String_String_String_String(narrowO).plain_String_String_String_String_String_String_String_String_String_String_String_String(plainO).self_String_String_String_String_String_String_String_String_String_String_String_String(selfO).take_String_String_String_String_String_String_String_String_String_String_String_String(takeO));
            var cb = sh.members();
            var asWide = cb.asSuper();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var pickM = cb.declareOverride(Wide12_.pick_String_String_String_String_String_String_String_String_String_String_String_String);
            var csM = cb.declareOverride(Wide12_.cs_String_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var sameM = cb.declareOverride(Wide12_.same_String_String_String_String_String_String_String_String_String_String_String_String, asWide);
            var putM = cb.declareOverride(Wide12_.put_String_String_String_String_String_String_String_String_String_String_String_String);
            var viaAllM = cb.declareMethod("viaAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaAllVoidM = cb.declareVoidMethod("viaAllVoid", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> c.super_(Wide12_.new_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> c.super_(Wide12_.new_String_String_String_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p12, p12), (b, self) -> b.end()));
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p12)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p12)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.end());
            bodies.define(pickM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(Expressions.concat(Expressions.literal("sub:"), bodies.superCall(self, Wide12_.pick_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12))));
            bodies.define(csM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(Expressions.concat(Expressions.literal("cs:"), p12)));
            bodies.define(sameM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.return_(self));
            bodies.define(putM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> b.exec(bodies.superCall(self, Wide12_.put_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12)).end());
            bodies.define(viaAllM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(bodies.superCall(self, Wide12_.pickAll_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))));
            bodies.define(viaAllVoidM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.exec(bodies.superCall(self, Wide12_.putAll_String_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).end());
            return bodies;
        }
    }

    static final class AbsSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Abstract12_.extend(h.self()));
            var cb = sh.members();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> c.super_(Abstract12_.super_String_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) -> c.super_(Abstract12_.super_String_String_String_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p12, p12), (b, self) -> b.end()));
            return bodies;
        }
    }

    static JavaFile plain() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Plain12"), new PlainSpec());
    }

    static JavaFile sub() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Sub12"), new SubSpec());
    }

    static JavaFile abs() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Abs12"), new AbsSpec());
    }

    @Test
    public void plainForms() throws Throwable {
        Wide12.noted = "";
        Object plain = ArityForms.create("Plain12", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12"));
        Assertions.assertThat(Wide12.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12;");
        Wide12.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain12", plain, "apply", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12"))).isEqualTo("pick:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12/join:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12/new:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12put:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12;");
        Assertions.assertThat(Wide12.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12;");
        Wide12.noted = "";
        ArityForms.invoke("Plain12", plain, "run", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12"));
        Assertions.assertThat(Wide12.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12;");
        Wide12.noted = "";
        ArityForms.invoke("Plain12", null, "log", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12"));
        Assertions.assertThat(Wide12.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12;");
        Assertions.assertThat(ArityForms.invoke("Plain12", null, "make", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12"))).isEqualTo("join:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12");
        Wide12.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain12", plain, "lam", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12"))).isEqualTo("fn:a12/blk:a12");
        Assertions.assertThat(Wide12.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12;note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12;");
        Widening12 widening = (Widening12) plain;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12")).isEqualTo("inh:a12");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12")).isEqualTo("narrow:a12");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12")).isEqualTo("plain:a12");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12")).isSameAs(plain);
        Assertions.assertThat(widening.given("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12")).isEqualTo("given:a12");
        Assertions.assertThat(widening.chosen("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12")).isEqualTo("chosen:a12");
        Assertions.assertThat(widening.chained("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12")).isSameAs(plain);
        Wide12.noted = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12");
        widening.take("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12");
        widening.held("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12");
        Assertions.assertThat(Wide12.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12;note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12;note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12;");
        Wide12.noted = "";
        Object variable = ArityForms.create("Plain12", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide12.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11|[r0, r1];");
        Wide12.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain12", variable, "applyAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", (Object) new String[] {"r0", "r1"}))).isEqualTo("pickAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11|[x, y]/joinAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11|[x, y]/newAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11|[1, 2]putAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11|[x, y];");
        Assertions.assertThat(Wide12.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11|[x, y];");
        Wide12.noted = "";
        ArityForms.invoke("Plain12", variable, "runAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide12.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11|[r0, r1];");
        Wide12.noted = "";
        ArityForms.invoke("Plain12", null, "logAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide12.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11|[r0, r1];");
        Assertions.assertThat(ArityForms.invoke("Plain12", null, "makeAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", (Object) new String[] {"r0", "r1"}))).isEqualTo("joinAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11|[r0, r1]");
    }

    @Test
    public void subForms() throws Throwable {
        Object sub = ArityForms.create("Sub12", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12"));
        Wide12 wide = (Wide12) sub;
        Assertions.assertThat(wide.told).isEqualTo("new:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12");
        Assertions.assertThat(wide.pick("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12")).isEqualTo("sub:pick:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12");
        Assertions.assertThat(wide.cs("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12")).isEqualTo("cs:a12");
        Assertions.assertThat(wide.same("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12")).isSameAs(sub);
        wide.told = "";
        wide.put("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12");
        Assertions.assertThat(wide.told).isEqualTo("put:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12;");
        Widening12 widening = (Widening12) sub;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12")).isEqualTo("inh:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12");
        wide.told = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12");
        Assertions.assertThat(wide.told).isEqualTo("inhVoid:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12;");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12")).isEqualTo("narrow:a12");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12")).isEqualTo("plain:a12");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12")).isSameAs(sub);
        wide.told = "";
        ArityForms.invoke("Sub12", sub, "viaAllVoid", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11"));
        Assertions.assertThat(wide.told).isEqualTo("putAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11|[x, y];");
        Assertions.assertThat(ArityForms.invoke("Sub12", sub, "viaAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11"))).isEqualTo("pickAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11|[x, y]");
        Object other = ArityForms.create("Sub12", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", 7));
        Assertions.assertThat(((Wide12) other).told).isEqualTo("newAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11|[7, 7]");
    }

    @Test
    public void absForms() throws Throwable {
        Object abs = ArityForms.create("Abs12", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", "a12"));
        Assertions.assertThat(((Abstract12) abs).told).isEqualTo("abs:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12");
        Object other = ArityForms.create("Abs12", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11", 7));
        Assertions.assertThat(((Abstract12) other).told).isEqualTo("absAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11|[7, 7]");
    }
}
