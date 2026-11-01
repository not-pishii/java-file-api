package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.Arity11FormsTest.AbsSpec;
import me.supcheg.javafile.typed.Arity11FormsTest.PlainSpec;
import me.supcheg.javafile.typed.Arity11FormsTest.SubSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedClassHeaderSpec;
import me.supcheg.javafile.typed.fixtures.Abstract11;
import me.supcheg.javafile.typed.fixtures.Make11;
import me.supcheg.javafile.typed.fixtures.Wide11;
import me.supcheg.javafile.typed.fixtures.Widening11;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Abstract11_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Do11_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Fn11_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Make11_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide11_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Widening11_;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * The forms of the typed layer of arity 11: a class that extends `Object`, one that extends a class and one that extends an abstract class, declared with them, are compiled with the classes of the other arities in one run of javac, and run.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
@Execution(ExecutionMode.SAME_THREAD)
class Arity11FormsTest {
    static final class PlainSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var becoming = h.becoming(Widening11_.TOKEN);
            var inhO = h.overriding(Widening11_.inh_String_String_String_String_String_String_String_String_String_String_String);
            var inhVoidO = h.overriding(Widening11_.inhVoid_String_String_String_String_String_String_String_String_String_String_String);
            var narrowO = h.overriding(Widening11_.narrow_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var plainO = h.overriding(Widening11_.plain_String_String_String_String_String_String_String_String_String_String_String);
            var selfO = h.overriding(Widening11_.self_String_String_String_String_String_String_String_String_String_String_String, becoming);
            var takeO = h.overriding(Widening11_.take_String_String_String_String_String_String_String_String_String_String_String);
            var oh = h.extendsObject();
            var asWidening = oh.implements_(Widening11_.implement(h.self()).inhVoid_String_String_String_String_String_String_String_String_String_String_String(inhVoidO).inh_String_String_String_String_String_String_String_String_String_String_String(inhO).narrow_String_String_String_String_String_String_String_String_String_String_String(narrowO).plain_String_String_String_String_String_String_String_String_String_String_String(plainO).self_String_String_String_String_String_String_String_String_String_String_String(selfO).take_String_String_String_String_String_String_String_String_String_String_String(takeO));
            var cb = oh.members();
            var givenM = cb.declareOverride(asWidening, Widening11_.given_String_String_String_String_String_String_String_String_String_String_String);
            var chosenM = cb.declareOverride(asWidening, Widening11_.chosen_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var chainedM = cb.declareOverride(asWidening, Widening11_.chained_String_String_String_String_String_String_String_String_String_String_String, asWidening);
            var heldM = cb.declareOverride(asWidening, Widening11_.held_String_String_String_String_String_String_String_String_String_String_String);
            var ctorM = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var applyM = cb.declareMethod("apply", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var runM = cb.declareVoidMethod("run", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var makeM = cb.declareStaticMethod("make", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var logM = cb.declareVoidStaticMethod("log", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var lamM = cb.declareMethod("lam", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaCtorM = cb.declareStaticMethod("viaCtor", Make11_.TOKEN);
            var vctorM = cb.variableArity().declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vapplyM = cb.variableArity().declareMethod("applyAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vrunM = cb.variableArity().declareVoidMethod("runAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vmakeM = cb.variableArity().declareStaticMethod("makeAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vlogM = cb.variableArity().declareVoidStaticMethod("logAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var bodies = cb.bodies();
            bodies.define(inhO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(Expressions.concat(Expressions.literal("inh:"), p11)));
            bodies.define(inhVoidO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.exec(Expressions.voidStaticCall(Wide11_.note_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)).end());
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p11)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p11)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.exec(Expressions.voidStaticCall(Wide11_.note_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)).end());
            bodies.define(givenM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(Expressions.concat(Expressions.literal("given:"), p11)));
            bodies.define(chosenM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(Expressions.concat(Expressions.literal("chosen:"), p11)));
            bodies.define(chainedM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(self));
            bodies.define(heldM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.exec(Expressions.voidStaticCall(Wide11_.note_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)).end());
            bodies.define(ctorM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.exec(Expressions.voidStaticCall(Wide11_.note_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)).end());
            bodies.define(applyM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.let(Wide11_.TOKEN, Expressions.new_(Wide11_.new_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), (w) -> b.exec(w.voidCall(Wide11_.put_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)).exec(Expressions.voidStaticCall(Wide11_.note_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide11_.pick_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), Expressions.literal("/")), Expressions.staticCall(Wide11_.join_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)), Expressions.literal("/")), w.field(Wide11_.told)))));
            bodies.define(runM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.exec(Expressions.voidStaticCall(Wide11_.note_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)).end());
            bodies.define(makeM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(Expressions.staticCall(Wide11_.join_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)));
            bodies.define(logM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.exec(Expressions.voidStaticCall(Wide11_.note_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)).end());
            bodies.define(lamM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.let(Fn11_.TOKEN, Expressions.lambda(Fn11_.sam, (q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11) -> Expressions.concat(Expressions.literal("fn:"), q11)), (f) -> b.let(Fn11_.TOKEN, Expressions.lambdaBlock(Fn11_.sam, (lb, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11) -> lb.return_(Expressions.concat(Expressions.literal("blk:"), q11))), (g) -> b.let(Do11_.TOKEN, Expressions.lambda(Do11_.sam, (q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11) -> Expressions.voidStaticCall(Wide11_.note_String_String_String_String_String_String_String_String_String_String_String, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11)), (d) -> b.let(Do11_.TOKEN, Expressions.lambdaBlock(Do11_.sam, (lb, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11) -> lb.exec(Expressions.voidStaticCall(Wide11_.note_String_String_String_String_String_String_String_String_String_String_String, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11)).end()), (e) -> b.exec(d.voidCall(Do11_.accept_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)).exec(e.voidCall(Do11_.accept_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)).return_(Expressions.concat(Expressions.concat(f.call(Fn11_.apply_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11), Expressions.literal("/")), g.call(Fn11_.apply_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11))))))));
            bodies.define(viaCtorM, (b) -> b.return_(Expressions.constructorRef(Wide11_.new_String_String_String_String_String_String_String_String_String_String_String, Make11_.TOKEN, Make11_.sam.method())));
            bodies.define(vctorM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, rest) -> b.exec(Expressions.voidStaticCall(Wide11_.noteAll_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, rest)).end());
            bodies.define(vapplyM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, rest) -> b.let(Wide11_.TOKEN, Expressions.new_(Wide11_.new_String_String_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), Expressions.box(PrimitiveToken.INT, Expressions.literal(1)), Expressions.box(PrimitiveToken.INT, Expressions.literal(2)))), (w) -> b.exec(w.voidCall(Wide11_.putAll_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).exec(Expressions.voidStaticCall(Wide11_.noteAll_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide11_.pickAll_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y"))), Expressions.literal("/")), Expressions.staticCall(Wide11_.joinAll_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))), Expressions.literal("/")), w.field(Wide11_.told)))));
            bodies.define(vrunM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, rest) -> b.exec(Expressions.voidStaticCall(Wide11_.noteAll_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, rest)).end());
            bodies.define(vmakeM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, rest) -> b.return_(Expressions.staticCall(Wide11_.joinAll_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, rest)));
            bodies.define(vlogM, (b, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, rest) -> b.exec(Expressions.voidStaticCall(Wide11_.noteAll_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, rest)).end());
            return bodies;
        }
    }

    static final class SubSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Wide11_.extend(h.self()));
            var becoming = sh.becoming(Widening11_.TOKEN);
            var inhI = sh.inherited(Wide11_.inh_String_String_String_String_String_String_String_String_String_String_String);
            var inhVoidI = sh.inherited(Wide11_.inhVoid_String_String_String_String_String_String_String_String_String_String_String);
            var narrowO = sh.overriding(Widening11_.narrow_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var plainO = sh.overriding(Widening11_.plain_String_String_String_String_String_String_String_String_String_String_String);
            var selfO = sh.overriding(Widening11_.self_String_String_String_String_String_String_String_String_String_String_String, becoming);
            var takeO = sh.overriding(Widening11_.take_String_String_String_String_String_String_String_String_String_String_String);
            var asWidening = sh.implements_(Widening11_.implement(h.self()).inhVoid_String_String_String_String_String_String_String_String_String_String_String(inhVoidI).inh_String_String_String_String_String_String_String_String_String_String_String(inhI).narrow_String_String_String_String_String_String_String_String_String_String_String(narrowO).plain_String_String_String_String_String_String_String_String_String_String_String(plainO).self_String_String_String_String_String_String_String_String_String_String_String(selfO).take_String_String_String_String_String_String_String_String_String_String_String(takeO));
            var cb = sh.members();
            var asWide = cb.asSuper();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var pickM = cb.declareOverride(Wide11_.pick_String_String_String_String_String_String_String_String_String_String_String);
            var csM = cb.declareOverride(Wide11_.cs_String_String_String_String_String_String_String_String_String_String_String, String_.TOKEN);
            var sameM = cb.declareOverride(Wide11_.same_String_String_String_String_String_String_String_String_String_String_String, asWide);
            var putM = cb.declareOverride(Wide11_.put_String_String_String_String_String_String_String_String_String_String_String);
            var viaAllM = cb.declareMethod("viaAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaAllVoidM = cb.declareVoidMethod("viaAllVoid", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> c.super_(Wide11_.new_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> c.super_(Wide11_.new_String_String_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p11, p11), (b, self) -> b.end()));
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p11)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p11)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.end());
            bodies.define(pickM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(Expressions.concat(Expressions.literal("sub:"), bodies.superCall(self, Wide11_.pick_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11))));
            bodies.define(csM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(Expressions.concat(Expressions.literal("cs:"), p11)));
            bodies.define(sameM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.return_(self));
            bodies.define(putM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> b.exec(bodies.superCall(self, Wide11_.put_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)).end());
            bodies.define(viaAllM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10) -> b.return_(bodies.superCall(self, Wide11_.pickAll_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))));
            bodies.define(viaAllVoidM, (b, self, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10) -> b.exec(bodies.superCall(self, Wide11_.putAll_String_String_String_String_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).end());
            return bodies;
        }
    }

    static final class AbsSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Abstract11_.extend(h.self()));
            var cb = sh.members();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> c.super_(Abstract11_.super_String_String_String_String_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) -> c.super_(Abstract11_.super_String_String_String_String_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p11, p11), (b, self) -> b.end()));
            return bodies;
        }
    }

    static JavaFile plain() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Plain11"), new PlainSpec());
    }

    static JavaFile sub() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Sub11"), new SubSpec());
    }

    static JavaFile abs() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Abs11"), new AbsSpec());
    }

    @Test
    public void plainForms() throws Throwable {
        Wide11.noted = "";
        Object plain = ArityForms.create("Plain11", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11"));
        Assertions.assertThat(Wide11.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11;");
        Wide11.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain11", plain, "apply", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11"))).isEqualTo("pick:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11/join:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11/new:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11put:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11;");
        Assertions.assertThat(Wide11.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11;");
        Wide11.noted = "";
        ArityForms.invoke("Plain11", plain, "run", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11"));
        Assertions.assertThat(Wide11.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11;");
        Wide11.noted = "";
        ArityForms.invoke("Plain11", null, "log", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11"));
        Assertions.assertThat(Wide11.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11;");
        Assertions.assertThat(ArityForms.invoke("Plain11", null, "make", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11"))).isEqualTo("join:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11");
        Wide11.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain11", plain, "lam", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11"))).isEqualTo("fn:a11/blk:a11");
        Assertions.assertThat(Wide11.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11;note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11;");
        Make11 make = (Make11) ArityForms.invoke("Plain11", null, "viaCtor", List.of());
        Assertions.assertThat(make.make("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11").told).isEqualTo("new:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11");
        Widening11 widening = (Widening11) plain;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11")).isEqualTo("inh:a11");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11")).isEqualTo("narrow:a11");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11")).isEqualTo("plain:a11");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11")).isSameAs(plain);
        Assertions.assertThat(widening.given("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11")).isEqualTo("given:a11");
        Assertions.assertThat(widening.chosen("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11")).isEqualTo("chosen:a11");
        Assertions.assertThat(widening.chained("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11")).isSameAs(plain);
        Wide11.noted = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11");
        widening.take("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11");
        widening.held("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11");
        Assertions.assertThat(Wide11.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11;note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11;note:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11;");
        Wide11.noted = "";
        Object variable = ArityForms.create("Plain11", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide11.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10|[r0, r1];");
        Wide11.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain11", variable, "applyAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", (Object) new String[] {"r0", "r1"}))).isEqualTo("pickAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10|[x, y]/joinAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10|[x, y]/newAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10|[1, 2]putAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10|[x, y];");
        Assertions.assertThat(Wide11.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10|[x, y];");
        Wide11.noted = "";
        ArityForms.invoke("Plain11", variable, "runAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide11.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10|[r0, r1];");
        Wide11.noted = "";
        ArityForms.invoke("Plain11", null, "logAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide11.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10|[r0, r1];");
        Assertions.assertThat(ArityForms.invoke("Plain11", null, "makeAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", (Object) new String[] {"r0", "r1"}))).isEqualTo("joinAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10|[r0, r1]");
    }

    @Test
    public void subForms() throws Throwable {
        Object sub = ArityForms.create("Sub11", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11"));
        Wide11 wide = (Wide11) sub;
        Assertions.assertThat(wide.told).isEqualTo("new:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11");
        Assertions.assertThat(wide.pick("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11")).isEqualTo("sub:pick:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11");
        Assertions.assertThat(wide.cs("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11")).isEqualTo("cs:a11");
        Assertions.assertThat(wide.same("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11")).isSameAs(sub);
        wide.told = "";
        wide.put("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11");
        Assertions.assertThat(wide.told).isEqualTo("put:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11;");
        Widening11 widening = (Widening11) sub;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11")).isEqualTo("inh:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11");
        wide.told = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11");
        Assertions.assertThat(wide.told).isEqualTo("inhVoid:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11;");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11")).isEqualTo("narrow:a11");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11")).isEqualTo("plain:a11");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11")).isSameAs(sub);
        wide.told = "";
        ArityForms.invoke("Sub11", sub, "viaAllVoid", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10"));
        Assertions.assertThat(wide.told).isEqualTo("putAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10|[x, y];");
        Assertions.assertThat(ArityForms.invoke("Sub11", sub, "viaAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10"))).isEqualTo("pickAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10|[x, y]");
        Object other = ArityForms.create("Sub11", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", 7));
        Assertions.assertThat(((Wide11) other).told).isEqualTo("newAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10|[7, 7]");
    }

    @Test
    public void absForms() throws Throwable {
        Object abs = ArityForms.create("Abs11", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", "a11"));
        Assertions.assertThat(((Abstract11) abs).told).isEqualTo("abs:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11");
        Object other = ArityForms.create("Abs11", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "a10", 7));
        Assertions.assertThat(((Abstract11) other).told).isEqualTo("absAll:a1,a2,a3,a4,a5,a6,a7,a8,a9,a10|[7, 7]");
    }
}
