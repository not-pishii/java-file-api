package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.Arity7FormsTest.AbsSpec;
import me.supcheg.javafile.typed.Arity7FormsTest.PlainSpec;
import me.supcheg.javafile.typed.Arity7FormsTest.SubSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedClassHeaderSpec;
import me.supcheg.javafile.typed.fixtures.Abstract7;
import me.supcheg.javafile.typed.fixtures.Wide7;
import me.supcheg.javafile.typed.fixtures.Widening7;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Abstract7_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Do7_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Fn7_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide7_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Widening7_;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * The forms of the typed layer of arity 7: a class that extends `Object`, one that extends a class and one that extends an abstract class, declared with them, are compiled with the classes of the other arities in one run of javac, and run.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
@Execution(ExecutionMode.SAME_THREAD)
class Arity7FormsTest {
    static final class PlainSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var becoming = h.becoming(Widening7_.TOKEN);
            var inhO = h.overriding(Widening7_.inh_String_String_String_String_String_String_String);
            var inhVoidO = h.overriding(Widening7_.inhVoid_String_String_String_String_String_String_String);
            var narrowO = h.overriding(Widening7_.narrow_String_String_String_String_String_String_String, String_.TOKEN);
            var plainO = h.overriding(Widening7_.plain_String_String_String_String_String_String_String);
            var selfO = h.overriding(Widening7_.self_String_String_String_String_String_String_String, becoming);
            var takeO = h.overriding(Widening7_.take_String_String_String_String_String_String_String);
            var oh = h.extendsObject();
            var asWidening = oh.implements_(Widening7_.implement(h.self()).inhVoid_String_String_String_String_String_String_String(inhVoidO).inh_String_String_String_String_String_String_String(inhO).narrow_String_String_String_String_String_String_String(narrowO).plain_String_String_String_String_String_String_String(plainO).self_String_String_String_String_String_String_String(selfO).take_String_String_String_String_String_String_String(takeO));
            var cb = oh.members();
            var givenM = cb.declareOverride(asWidening, Widening7_.given_String_String_String_String_String_String_String);
            var chosenM = cb.declareOverride(asWidening, Widening7_.chosen_String_String_String_String_String_String_String, String_.TOKEN);
            var chainedM = cb.declareOverride(asWidening, Widening7_.chained_String_String_String_String_String_String_String, asWidening);
            var heldM = cb.declareOverride(asWidening, Widening7_.held_String_String_String_String_String_String_String);
            var ctorM = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var applyM = cb.declareMethod("apply", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var runM = cb.declareVoidMethod("run", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var makeM = cb.declareStaticMethod("make", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var logM = cb.declareVoidStaticMethod("log", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var lamM = cb.declareMethod("lam", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var vctorM = cb.variableArity().declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vapplyM = cb.variableArity().declareMethod("applyAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vrunM = cb.variableArity().declareVoidMethod("runAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vmakeM = cb.variableArity().declareStaticMethod("makeAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vlogM = cb.variableArity().declareVoidStaticMethod("logAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var bodies = cb.bodies();
            bodies.define(inhO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(Expressions.concat(Expressions.literal("inh:"), p7)));
            bodies.define(inhVoidO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.exec(Expressions.voidStaticCall(Wide7_.note_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7)).end());
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p7)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p7)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.exec(Expressions.voidStaticCall(Wide7_.note_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7)).end());
            bodies.define(givenM, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(Expressions.concat(Expressions.literal("given:"), p7)));
            bodies.define(chosenM, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(Expressions.concat(Expressions.literal("chosen:"), p7)));
            bodies.define(chainedM, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(self));
            bodies.define(heldM, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.exec(Expressions.voidStaticCall(Wide7_.note_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7)).end());
            bodies.define(ctorM, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.exec(Expressions.voidStaticCall(Wide7_.note_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7)).end());
            bodies.define(applyM, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.let(Wide7_.TOKEN, Expressions.new_(Wide7_.new_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7), (w) -> b.exec(Expressions.voidCall(w, Wide7_.put_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7)).exec(Expressions.voidStaticCall(Wide7_.note_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7)).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.call(w, Wide7_.pick_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7), Expressions.literal("/")), Expressions.staticCall(Wide7_.join_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7)), Expressions.literal("/")), Expressions.field(w, Wide7_.told)))));
            bodies.define(runM, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.exec(Expressions.voidStaticCall(Wide7_.note_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7)).end());
            bodies.define(makeM, (b, p1, p2, p3, p4, p5, p6, p7) -> b.return_(Expressions.staticCall(Wide7_.join_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7)));
            bodies.define(logM, (b, p1, p2, p3, p4, p5, p6, p7) -> b.exec(Expressions.voidStaticCall(Wide7_.note_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7)).end());
            bodies.define(lamM, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.let(Fn7_.TOKEN, Expressions.lambda(Fn7_.sam, (q1, q2, q3, q4, q5, q6, q7) -> Expressions.concat(Expressions.literal("fn:"), q7)), (f) -> b.let(Fn7_.TOKEN, Expressions.lambdaBlock(Fn7_.sam, (lb, q1, q2, q3, q4, q5, q6, q7) -> lb.return_(Expressions.concat(Expressions.literal("blk:"), q7))), (g) -> b.let(Do7_.TOKEN, Expressions.lambda(Do7_.sam, (q1, q2, q3, q4, q5, q6, q7) -> Expressions.voidStaticCall(Wide7_.note_String_String_String_String_String_String_String, q1, q2, q3, q4, q5, q6, q7)), (d) -> b.let(Do7_.TOKEN, Expressions.lambdaBlock(Do7_.sam, (lb, q1, q2, q3, q4, q5, q6, q7) -> lb.exec(Expressions.voidStaticCall(Wide7_.note_String_String_String_String_String_String_String, q1, q2, q3, q4, q5, q6, q7)).end()), (e) -> b.exec(Expressions.voidCall(d, Do7_.accept_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7)).exec(Expressions.voidCall(e, Do7_.accept_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7)).return_(Expressions.concat(Expressions.concat(Expressions.call(f, Fn7_.apply_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7), Expressions.literal("/")), Expressions.call(g, Fn7_.apply_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7))))))));
            bodies.define(vctorM, (b, self, p1, p2, p3, p4, p5, p6, rest) -> b.exec(Expressions.voidStaticCall(Wide7_.noteAll_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, rest)).end());
            bodies.define(vapplyM, (b, self, p1, p2, p3, p4, p5, p6, rest) -> b.let(Wide7_.TOKEN, Expressions.new_(Wide7_.new_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), Expressions.box(PrimitiveToken.INT, Expressions.literal(1)), Expressions.box(PrimitiveToken.INT, Expressions.literal(2)))), (w) -> b.exec(Expressions.voidCall(w, Wide7_.putAll_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).exec(Expressions.voidStaticCall(Wide7_.noteAll_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.call(w, Wide7_.pickAll_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y"))), Expressions.literal("/")), Expressions.staticCall(Wide7_.joinAll_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))), Expressions.literal("/")), Expressions.field(w, Wide7_.told)))));
            bodies.define(vrunM, (b, self, p1, p2, p3, p4, p5, p6, rest) -> b.exec(Expressions.voidStaticCall(Wide7_.noteAll_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, rest)).end());
            bodies.define(vmakeM, (b, p1, p2, p3, p4, p5, p6, rest) -> b.return_(Expressions.staticCall(Wide7_.joinAll_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, rest)));
            bodies.define(vlogM, (b, p1, p2, p3, p4, p5, p6, rest) -> b.exec(Expressions.voidStaticCall(Wide7_.noteAll_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, rest)).end());
            return bodies;
        }
    }

    static final class SubSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Wide7_.extend(h.self()));
            var becoming = sh.becoming(Widening7_.TOKEN);
            var inhI = sh.inherited(Wide7_.inh_String_String_String_String_String_String_String);
            var inhVoidI = sh.inherited(Wide7_.inhVoid_String_String_String_String_String_String_String);
            var narrowO = sh.overriding(Widening7_.narrow_String_String_String_String_String_String_String, String_.TOKEN);
            var plainO = sh.overriding(Widening7_.plain_String_String_String_String_String_String_String);
            var selfO = sh.overriding(Widening7_.self_String_String_String_String_String_String_String, becoming);
            var takeO = sh.overriding(Widening7_.take_String_String_String_String_String_String_String);
            var asWidening = sh.implements_(Widening7_.implement(h.self()).inhVoid_String_String_String_String_String_String_String(inhVoidI).inh_String_String_String_String_String_String_String(inhI).narrow_String_String_String_String_String_String_String(narrowO).plain_String_String_String_String_String_String_String(plainO).self_String_String_String_String_String_String_String(selfO).take_String_String_String_String_String_String_String(takeO));
            var cb = sh.members();
            var asWide = cb.asSuper();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var pickM = cb.declareOverride(Wide7_.pick_String_String_String_String_String_String_String);
            var csM = cb.declareOverride(Wide7_.cs_String_String_String_String_String_String_String, String_.TOKEN);
            var sameM = cb.declareOverride(Wide7_.same_String_String_String_String_String_String_String, asWide);
            var putM = cb.declareOverride(Wide7_.put_String_String_String_String_String_String_String);
            var viaAllM = cb.declareMethod("viaAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaAllVoidM = cb.declareVoidMethod("viaAllVoid", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5, p6, p7) -> c.super_(Wide7_.new_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5, p6, p7) -> c.super_(Wide7_.new_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p7, p7), (b, self) -> b.end()));
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p7)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p7)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.end());
            bodies.define(pickM, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(Expressions.concat(Expressions.literal("sub:"), bodies.superCall(self, Wide7_.pick_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7))));
            bodies.define(csM, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(Expressions.concat(Expressions.literal("cs:"), p7)));
            bodies.define(sameM, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.return_(self));
            bodies.define(putM, (b, self, p1, p2, p3, p4, p5, p6, p7) -> b.exec(bodies.superCall(self, Wide7_.put_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7)).end());
            bodies.define(viaAllM, (b, self, p1, p2, p3, p4, p5, p6) -> b.return_(bodies.superCall(self, Wide7_.pickAll_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))));
            bodies.define(viaAllVoidM, (b, self, p1, p2, p3, p4, p5, p6) -> b.exec(bodies.superCall(self, Wide7_.putAll_String_String_String_String_String_String_StringArray, p1, p2, p3, p4, p5, p6, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).end());
            return bodies;
        }
    }

    static final class AbsSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Abstract7_.extend(h.self()));
            var cb = sh.members();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5, p6, p7) -> c.super_(Abstract7_.super_String_String_String_String_String_String_String, p1, p2, p3, p4, p5, p6, p7, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5, p6, p7) -> c.super_(Abstract7_.super_String_String_String_String_String_String_IntegerArray, p1, p2, p3, p4, p5, p6, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p7, p7), (b, self) -> b.end()));
            return bodies;
        }
    }

    static JavaFile plain() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Plain7"), new PlainSpec());
    }

    static JavaFile sub() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Sub7"), new SubSpec());
    }

    static JavaFile abs() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Abs7"), new AbsSpec());
    }

    @Test
    public void plainForms() throws Throwable {
        Wide7.noted = "";
        Object plain = ArityForms.create("Plain7", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7"));
        Assertions.assertThat(Wide7.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7;");
        Wide7.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain7", plain, "apply", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7"))).isEqualTo("pick:a1,a2,a3,a4,a5,a6,a7/join:a1,a2,a3,a4,a5,a6,a7/new:a1,a2,a3,a4,a5,a6,a7put:a1,a2,a3,a4,a5,a6,a7;");
        Assertions.assertThat(Wide7.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7;");
        Wide7.noted = "";
        ArityForms.invoke("Plain7", plain, "run", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7"));
        Assertions.assertThat(Wide7.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7;");
        Wide7.noted = "";
        ArityForms.invoke("Plain7", null, "log", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7"));
        Assertions.assertThat(Wide7.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7;");
        Assertions.assertThat(ArityForms.invoke("Plain7", null, "make", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7"))).isEqualTo("join:a1,a2,a3,a4,a5,a6,a7");
        Wide7.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain7", plain, "lam", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7"))).isEqualTo("fn:a7/blk:a7");
        Assertions.assertThat(Wide7.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7;note:a1,a2,a3,a4,a5,a6,a7;");
        Widening7 widening = (Widening7) plain;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5", "a6", "a7")).isEqualTo("inh:a7");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5", "a6", "a7")).isEqualTo("narrow:a7");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5", "a6", "a7")).isEqualTo("plain:a7");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5", "a6", "a7")).isSameAs(plain);
        Assertions.assertThat(widening.given("a1", "a2", "a3", "a4", "a5", "a6", "a7")).isEqualTo("given:a7");
        Assertions.assertThat(widening.chosen("a1", "a2", "a3", "a4", "a5", "a6", "a7")).isEqualTo("chosen:a7");
        Assertions.assertThat(widening.chained("a1", "a2", "a3", "a4", "a5", "a6", "a7")).isSameAs(plain);
        Wide7.noted = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5", "a6", "a7");
        widening.take("a1", "a2", "a3", "a4", "a5", "a6", "a7");
        widening.held("a1", "a2", "a3", "a4", "a5", "a6", "a7");
        Assertions.assertThat(Wide7.noted).isEqualTo("note:a1,a2,a3,a4,a5,a6,a7;note:a1,a2,a3,a4,a5,a6,a7;note:a1,a2,a3,a4,a5,a6,a7;");
        Wide7.noted = "";
        Object variable = ArityForms.create("Plain7", List.of("a1", "a2", "a3", "a4", "a5", "a6", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide7.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6|[r0, r1];");
        Wide7.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain7", variable, "applyAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", (Object) new String[] {"r0", "r1"}))).isEqualTo("pickAll:a1,a2,a3,a4,a5,a6|[x, y]/joinAll:a1,a2,a3,a4,a5,a6|[x, y]/newAll:a1,a2,a3,a4,a5,a6|[1, 2]putAll:a1,a2,a3,a4,a5,a6|[x, y];");
        Assertions.assertThat(Wide7.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6|[x, y];");
        Wide7.noted = "";
        ArityForms.invoke("Plain7", variable, "runAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide7.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6|[r0, r1];");
        Wide7.noted = "";
        ArityForms.invoke("Plain7", null, "logAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide7.noted).isEqualTo("noteAll:a1,a2,a3,a4,a5,a6|[r0, r1];");
        Assertions.assertThat(ArityForms.invoke("Plain7", null, "makeAll", List.of("a1", "a2", "a3", "a4", "a5", "a6", (Object) new String[] {"r0", "r1"}))).isEqualTo("joinAll:a1,a2,a3,a4,a5,a6|[r0, r1]");
    }

    @Test
    public void subForms() throws Throwable {
        Object sub = ArityForms.create("Sub7", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7"));
        Wide7 wide = (Wide7) sub;
        Assertions.assertThat(wide.told).isEqualTo("new:a1,a2,a3,a4,a5,a6,a7");
        Assertions.assertThat(wide.pick("a1", "a2", "a3", "a4", "a5", "a6", "a7")).isEqualTo("sub:pick:a1,a2,a3,a4,a5,a6,a7");
        Assertions.assertThat(wide.cs("a1", "a2", "a3", "a4", "a5", "a6", "a7")).isEqualTo("cs:a7");
        Assertions.assertThat(wide.same("a1", "a2", "a3", "a4", "a5", "a6", "a7")).isSameAs(sub);
        wide.told = "";
        wide.put("a1", "a2", "a3", "a4", "a5", "a6", "a7");
        Assertions.assertThat(wide.told).isEqualTo("put:a1,a2,a3,a4,a5,a6,a7;");
        Widening7 widening = (Widening7) sub;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5", "a6", "a7")).isEqualTo("inh:a1,a2,a3,a4,a5,a6,a7");
        wide.told = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5", "a6", "a7");
        Assertions.assertThat(wide.told).isEqualTo("inhVoid:a1,a2,a3,a4,a5,a6,a7;");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5", "a6", "a7")).isEqualTo("narrow:a7");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5", "a6", "a7")).isEqualTo("plain:a7");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5", "a6", "a7")).isSameAs(sub);
        wide.told = "";
        ArityForms.invoke("Sub7", sub, "viaAllVoid", List.of("a1", "a2", "a3", "a4", "a5", "a6"));
        Assertions.assertThat(wide.told).isEqualTo("putAll:a1,a2,a3,a4,a5,a6|[x, y];");
        Assertions.assertThat(ArityForms.invoke("Sub7", sub, "viaAll", List.of("a1", "a2", "a3", "a4", "a5", "a6"))).isEqualTo("pickAll:a1,a2,a3,a4,a5,a6|[x, y]");
        Object other = ArityForms.create("Sub7", List.of("a1", "a2", "a3", "a4", "a5", "a6", 7));
        Assertions.assertThat(((Wide7) other).told).isEqualTo("newAll:a1,a2,a3,a4,a5,a6|[7, 7]");
    }

    @Test
    public void absForms() throws Throwable {
        Object abs = ArityForms.create("Abs7", List.of("a1", "a2", "a3", "a4", "a5", "a6", "a7"));
        Assertions.assertThat(((Abstract7) abs).told).isEqualTo("abs:a1,a2,a3,a4,a5,a6,a7");
        Object other = ArityForms.create("Abs7", List.of("a1", "a2", "a3", "a4", "a5", "a6", 7));
        Assertions.assertThat(((Abstract7) other).told).isEqualTo("absAll:a1,a2,a3,a4,a5,a6|[7, 7]");
    }
}
