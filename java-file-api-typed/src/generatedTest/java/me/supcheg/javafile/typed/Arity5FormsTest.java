package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.Arity5FormsTest.AbsSpec;
import me.supcheg.javafile.typed.Arity5FormsTest.PlainSpec;
import me.supcheg.javafile.typed.Arity5FormsTest.SubSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedClassHeaderSpec;
import me.supcheg.javafile.typed.fixtures.Abstract5;
import me.supcheg.javafile.typed.fixtures.Make5;
import me.supcheg.javafile.typed.fixtures.Wide5;
import me.supcheg.javafile.typed.fixtures.Widening5;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Abstract5_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Do5_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Fn5_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Make5_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide5_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Widening5_;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * The forms of the typed layer of arity 5: a class that extends `Object`, one that extends a class and one that extends an abstract class, declared with them, are compiled with the classes of the other arities in one run of javac, and run.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
@Execution(ExecutionMode.SAME_THREAD)
class Arity5FormsTest {
    static final class PlainSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var becoming = h.becoming(Widening5_.TOKEN);
            var inhO = h.overriding(Widening5_.inh_String_String_String_String_String);
            var inhVoidO = h.overriding(Widening5_.inhVoid_String_String_String_String_String);
            var narrowO = h.overriding(Widening5_.narrow_String_String_String_String_String, String_.TOKEN);
            var plainO = h.overriding(Widening5_.plain_String_String_String_String_String);
            var selfO = h.overriding(Widening5_.self_String_String_String_String_String, becoming);
            var takeO = h.overriding(Widening5_.take_String_String_String_String_String);
            var oh = h.extendsObject();
            var asWidening = oh.implements_(Widening5_.implement(h.self()).inhVoid_String_String_String_String_String(inhVoidO).inh_String_String_String_String_String(inhO).narrow_String_String_String_String_String(narrowO).plain_String_String_String_String_String(plainO).self_String_String_String_String_String(selfO).take_String_String_String_String_String(takeO));
            var cb = oh.members();
            var givenM = cb.declareOverride(asWidening, Widening5_.given_String_String_String_String_String);
            var chosenM = cb.declareOverride(asWidening, Widening5_.chosen_String_String_String_String_String, String_.TOKEN);
            var chainedM = cb.declareOverride(asWidening, Widening5_.chained_String_String_String_String_String, asWidening);
            var heldM = cb.declareOverride(asWidening, Widening5_.held_String_String_String_String_String);
            var ctorM = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var applyM = cb.declareMethod("apply", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var runM = cb.declareVoidMethod("run", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var makeM = cb.declareStaticMethod("make", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var logM = cb.declareVoidStaticMethod("log", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var lamM = cb.declareMethod("lam", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaCtorM = cb.declareStaticMethod("viaCtor", Make5_.TOKEN);
            var vctorM = cb.variableArity().declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vapplyM = cb.variableArity().declareMethod("applyAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vrunM = cb.variableArity().declareVoidMethod("runAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vmakeM = cb.variableArity().declareStaticMethod("makeAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vlogM = cb.variableArity().declareVoidStaticMethod("logAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var bodies = cb.bodies();
            bodies.define(inhO.method(), (b, self, p1, p2, p3, p4, p5) -> b.return_(Expressions.concat(Expressions.literal("inh:"), p5)));
            bodies.define(inhVoidO.method(), (b, self, p1, p2, p3, p4, p5) -> b.exec(Expressions.voidStaticCall(Wide5_.note_String_String_String_String_String, p1, p2, p3, p4, p5)).end());
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p5)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p5)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5) -> b.exec(Expressions.voidStaticCall(Wide5_.note_String_String_String_String_String, p1, p2, p3, p4, p5)).end());
            bodies.define(givenM, (b, self, p1, p2, p3, p4, p5) -> b.return_(Expressions.concat(Expressions.literal("given:"), p5)));
            bodies.define(chosenM, (b, self, p1, p2, p3, p4, p5) -> b.return_(Expressions.concat(Expressions.literal("chosen:"), p5)));
            bodies.define(chainedM, (b, self, p1, p2, p3, p4, p5) -> b.return_(self));
            bodies.define(heldM, (b, self, p1, p2, p3, p4, p5) -> b.exec(Expressions.voidStaticCall(Wide5_.note_String_String_String_String_String, p1, p2, p3, p4, p5)).end());
            bodies.define(ctorM, (b, self, p1, p2, p3, p4, p5) -> b.exec(Expressions.voidStaticCall(Wide5_.note_String_String_String_String_String, p1, p2, p3, p4, p5)).end());
            bodies.define(applyM, (b, self, p1, p2, p3, p4, p5) -> b.let(Wide5_.TOKEN, Expressions.new_(Wide5_.new_String_String_String_String_String, p1, p2, p3, p4, p5), (w) -> b.exec(w.voidCall(Wide5_.put_String_String_String_String_String, p1, p2, p3, p4, p5)).exec(Expressions.voidStaticCall(Wide5_.note_String_String_String_String_String, p1, p2, p3, p4, p5)).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide5_.pick_String_String_String_String_String, p1, p2, p3, p4, p5), Expressions.literal("/")), Expressions.staticCall(Wide5_.join_String_String_String_String_String, p1, p2, p3, p4, p5)), Expressions.literal("/")), w.field(Wide5_.told)))));
            bodies.define(runM, (b, self, p1, p2, p3, p4, p5) -> b.exec(Expressions.voidStaticCall(Wide5_.note_String_String_String_String_String, p1, p2, p3, p4, p5)).end());
            bodies.define(makeM, (b, p1, p2, p3, p4, p5) -> b.return_(Expressions.staticCall(Wide5_.join_String_String_String_String_String, p1, p2, p3, p4, p5)));
            bodies.define(logM, (b, p1, p2, p3, p4, p5) -> b.exec(Expressions.voidStaticCall(Wide5_.note_String_String_String_String_String, p1, p2, p3, p4, p5)).end());
            bodies.define(lamM, (b, self, p1, p2, p3, p4, p5) -> b.let(Fn5_.TOKEN, Expressions.lambda(Fn5_.sam, (q1, q2, q3, q4, q5) -> Expressions.concat(Expressions.literal("fn:"), q5)), (f) -> b.let(Fn5_.TOKEN, Expressions.lambdaBlock(Fn5_.sam, (lb, q1, q2, q3, q4, q5) -> lb.return_(Expressions.concat(Expressions.literal("blk:"), q5))), (g) -> b.let(Do5_.TOKEN, Expressions.lambda(Do5_.sam, (q1, q2, q3, q4, q5) -> Expressions.voidStaticCall(Wide5_.note_String_String_String_String_String, q1, q2, q3, q4, q5)), (d) -> b.let(Do5_.TOKEN, Expressions.lambdaBlock(Do5_.sam, (lb, q1, q2, q3, q4, q5) -> lb.exec(Expressions.voidStaticCall(Wide5_.note_String_String_String_String_String, q1, q2, q3, q4, q5)).end()), (e) -> b.exec(d.voidCall(Do5_.accept_String_String_String_String_String, p1, p2, p3, p4, p5)).exec(e.voidCall(Do5_.accept_String_String_String_String_String, p1, p2, p3, p4, p5)).return_(Expressions.concat(Expressions.concat(f.call(Fn5_.apply_String_String_String_String_String, p1, p2, p3, p4, p5), Expressions.literal("/")), g.call(Fn5_.apply_String_String_String_String_String, p1, p2, p3, p4, p5))))))));
            bodies.define(viaCtorM, (b) -> b.return_(Expressions.constructorRef(Wide5_.new_String_String_String_String_String, Make5_.TOKEN, Make5_.sam.method())));
            bodies.define(vctorM, (b, self, p1, p2, p3, p4, rest) -> b.exec(Expressions.voidStaticCall(Wide5_.noteAll_String_String_String_String_StringArray, p1, p2, p3, p4, rest)).end());
            bodies.define(vapplyM, (b, self, p1, p2, p3, p4, rest) -> b.let(Wide5_.TOKEN, Expressions.new_(Wide5_.new_String_String_String_String_IntegerArray, p1, p2, p3, p4, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), Expressions.box(PrimitiveToken.INT, Expressions.literal(1)), Expressions.box(PrimitiveToken.INT, Expressions.literal(2)))), (w) -> b.exec(w.voidCall(Wide5_.putAll_String_String_String_String_StringArray, p1, p2, p3, p4, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).exec(Expressions.voidStaticCall(Wide5_.noteAll_String_String_String_String_StringArray, p1, p2, p3, p4, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide5_.pickAll_String_String_String_String_StringArray, p1, p2, p3, p4, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y"))), Expressions.literal("/")), Expressions.staticCall(Wide5_.joinAll_String_String_String_String_StringArray, p1, p2, p3, p4, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))), Expressions.literal("/")), w.field(Wide5_.told)))));
            bodies.define(vrunM, (b, self, p1, p2, p3, p4, rest) -> b.exec(Expressions.voidStaticCall(Wide5_.noteAll_String_String_String_String_StringArray, p1, p2, p3, p4, rest)).end());
            bodies.define(vmakeM, (b, p1, p2, p3, p4, rest) -> b.return_(Expressions.staticCall(Wide5_.joinAll_String_String_String_String_StringArray, p1, p2, p3, p4, rest)));
            bodies.define(vlogM, (b, p1, p2, p3, p4, rest) -> b.exec(Expressions.voidStaticCall(Wide5_.noteAll_String_String_String_String_StringArray, p1, p2, p3, p4, rest)).end());
            return bodies;
        }
    }

    static final class SubSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Wide5_.extend(h.self()));
            var becoming = sh.becoming(Widening5_.TOKEN);
            var inhI = sh.inherited(Wide5_.inh_String_String_String_String_String);
            var inhVoidI = sh.inherited(Wide5_.inhVoid_String_String_String_String_String);
            var narrowO = sh.overriding(Widening5_.narrow_String_String_String_String_String, String_.TOKEN);
            var plainO = sh.overriding(Widening5_.plain_String_String_String_String_String);
            var selfO = sh.overriding(Widening5_.self_String_String_String_String_String, becoming);
            var takeO = sh.overriding(Widening5_.take_String_String_String_String_String);
            var asWidening = sh.implements_(Widening5_.implement(h.self()).inhVoid_String_String_String_String_String(inhVoidI).inh_String_String_String_String_String(inhI).narrow_String_String_String_String_String(narrowO).plain_String_String_String_String_String(plainO).self_String_String_String_String_String(selfO).take_String_String_String_String_String(takeO));
            var cb = sh.members();
            var asWide = cb.asSuper();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var pickM = cb.declareOverride(Wide5_.pick_String_String_String_String_String);
            var csM = cb.declareOverride(Wide5_.cs_String_String_String_String_String, String_.TOKEN);
            var sameM = cb.declareOverride(Wide5_.same_String_String_String_String_String, asWide);
            var putM = cb.declareOverride(Wide5_.put_String_String_String_String_String);
            var viaAllM = cb.declareMethod("viaAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaAllVoidM = cb.declareVoidMethod("viaAllVoid", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5) -> c.super_(Wide5_.new_String_String_String_String_String, p1, p2, p3, p4, p5, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5) -> c.super_(Wide5_.new_String_String_String_String_IntegerArray, p1, p2, p3, p4, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p5, p5), (b, self) -> b.end()));
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4, p5) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p5)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4, p5) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p5)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4, p5) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4, p5) -> b.end());
            bodies.define(pickM, (b, self, p1, p2, p3, p4, p5) -> b.return_(Expressions.concat(Expressions.literal("sub:"), bodies.superCall(self, Wide5_.pick_String_String_String_String_String, p1, p2, p3, p4, p5))));
            bodies.define(csM, (b, self, p1, p2, p3, p4, p5) -> b.return_(Expressions.concat(Expressions.literal("cs:"), p5)));
            bodies.define(sameM, (b, self, p1, p2, p3, p4, p5) -> b.return_(self));
            bodies.define(putM, (b, self, p1, p2, p3, p4, p5) -> b.exec(bodies.superCall(self, Wide5_.put_String_String_String_String_String, p1, p2, p3, p4, p5)).end());
            bodies.define(viaAllM, (b, self, p1, p2, p3, p4) -> b.return_(bodies.superCall(self, Wide5_.pickAll_String_String_String_String_StringArray, p1, p2, p3, p4, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))));
            bodies.define(viaAllVoidM, (b, self, p1, p2, p3, p4) -> b.exec(bodies.superCall(self, Wide5_.putAll_String_String_String_String_StringArray, p1, p2, p3, p4, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).end());
            return bodies;
        }
    }

    static final class AbsSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Abstract5_.extend(h.self()));
            var cb = sh.members();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4, p5) -> c.super_(Abstract5_.super_String_String_String_String_String, p1, p2, p3, p4, p5, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4, p5) -> c.super_(Abstract5_.super_String_String_String_String_IntegerArray, p1, p2, p3, p4, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p5, p5), (b, self) -> b.end()));
            return bodies;
        }
    }

    static JavaFile plain() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Plain5"), new PlainSpec());
    }

    static JavaFile sub() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Sub5"), new SubSpec());
    }

    static JavaFile abs() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Abs5"), new AbsSpec());
    }

    @Test
    public void plainForms() throws Throwable {
        Wide5.noted = "";
        Object plain = ArityForms.create("Plain5", List.of("a1", "a2", "a3", "a4", "a5"));
        Assertions.assertThat(Wide5.noted).isEqualTo("note:a1,a2,a3,a4,a5;");
        Wide5.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain5", plain, "apply", List.of("a1", "a2", "a3", "a4", "a5"))).isEqualTo("pick:a1,a2,a3,a4,a5/join:a1,a2,a3,a4,a5/new:a1,a2,a3,a4,a5put:a1,a2,a3,a4,a5;");
        Assertions.assertThat(Wide5.noted).isEqualTo("note:a1,a2,a3,a4,a5;");
        Wide5.noted = "";
        ArityForms.invoke("Plain5", plain, "run", List.of("a1", "a2", "a3", "a4", "a5"));
        Assertions.assertThat(Wide5.noted).isEqualTo("note:a1,a2,a3,a4,a5;");
        Wide5.noted = "";
        ArityForms.invoke("Plain5", null, "log", List.of("a1", "a2", "a3", "a4", "a5"));
        Assertions.assertThat(Wide5.noted).isEqualTo("note:a1,a2,a3,a4,a5;");
        Assertions.assertThat(ArityForms.invoke("Plain5", null, "make", List.of("a1", "a2", "a3", "a4", "a5"))).isEqualTo("join:a1,a2,a3,a4,a5");
        Wide5.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain5", plain, "lam", List.of("a1", "a2", "a3", "a4", "a5"))).isEqualTo("fn:a5/blk:a5");
        Assertions.assertThat(Wide5.noted).isEqualTo("note:a1,a2,a3,a4,a5;note:a1,a2,a3,a4,a5;");
        Make5 make = (Make5) ArityForms.invoke("Plain5", null, "viaCtor", List.of());
        Assertions.assertThat(make.make("a1", "a2", "a3", "a4", "a5").told).isEqualTo("new:a1,a2,a3,a4,a5");
        Widening5 widening = (Widening5) plain;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5")).isEqualTo("inh:a5");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5")).isEqualTo("narrow:a5");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5")).isEqualTo("plain:a5");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5")).isSameAs(plain);
        Assertions.assertThat(widening.given("a1", "a2", "a3", "a4", "a5")).isEqualTo("given:a5");
        Assertions.assertThat(widening.chosen("a1", "a2", "a3", "a4", "a5")).isEqualTo("chosen:a5");
        Assertions.assertThat(widening.chained("a1", "a2", "a3", "a4", "a5")).isSameAs(plain);
        Wide5.noted = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5");
        widening.take("a1", "a2", "a3", "a4", "a5");
        widening.held("a1", "a2", "a3", "a4", "a5");
        Assertions.assertThat(Wide5.noted).isEqualTo("note:a1,a2,a3,a4,a5;note:a1,a2,a3,a4,a5;note:a1,a2,a3,a4,a5;");
        Wide5.noted = "";
        Object variable = ArityForms.create("Plain5", List.of("a1", "a2", "a3", "a4", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide5.noted).isEqualTo("noteAll:a1,a2,a3,a4|[r0, r1];");
        Wide5.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain5", variable, "applyAll", List.of("a1", "a2", "a3", "a4", (Object) new String[] {"r0", "r1"}))).isEqualTo("pickAll:a1,a2,a3,a4|[x, y]/joinAll:a1,a2,a3,a4|[x, y]/newAll:a1,a2,a3,a4|[1, 2]putAll:a1,a2,a3,a4|[x, y];");
        Assertions.assertThat(Wide5.noted).isEqualTo("noteAll:a1,a2,a3,a4|[x, y];");
        Wide5.noted = "";
        ArityForms.invoke("Plain5", variable, "runAll", List.of("a1", "a2", "a3", "a4", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide5.noted).isEqualTo("noteAll:a1,a2,a3,a4|[r0, r1];");
        Wide5.noted = "";
        ArityForms.invoke("Plain5", null, "logAll", List.of("a1", "a2", "a3", "a4", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide5.noted).isEqualTo("noteAll:a1,a2,a3,a4|[r0, r1];");
        Assertions.assertThat(ArityForms.invoke("Plain5", null, "makeAll", List.of("a1", "a2", "a3", "a4", (Object) new String[] {"r0", "r1"}))).isEqualTo("joinAll:a1,a2,a3,a4|[r0, r1]");
    }

    @Test
    public void subForms() throws Throwable {
        Object sub = ArityForms.create("Sub5", List.of("a1", "a2", "a3", "a4", "a5"));
        Wide5 wide = (Wide5) sub;
        Assertions.assertThat(wide.told).isEqualTo("new:a1,a2,a3,a4,a5");
        Assertions.assertThat(wide.pick("a1", "a2", "a3", "a4", "a5")).isEqualTo("sub:pick:a1,a2,a3,a4,a5");
        Assertions.assertThat(wide.cs("a1", "a2", "a3", "a4", "a5")).isEqualTo("cs:a5");
        Assertions.assertThat(wide.same("a1", "a2", "a3", "a4", "a5")).isSameAs(sub);
        wide.told = "";
        wide.put("a1", "a2", "a3", "a4", "a5");
        Assertions.assertThat(wide.told).isEqualTo("put:a1,a2,a3,a4,a5;");
        Widening5 widening = (Widening5) sub;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4", "a5")).isEqualTo("inh:a1,a2,a3,a4,a5");
        wide.told = "";
        widening.inhVoid("a1", "a2", "a3", "a4", "a5");
        Assertions.assertThat(wide.told).isEqualTo("inhVoid:a1,a2,a3,a4,a5;");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4", "a5")).isEqualTo("narrow:a5");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4", "a5")).isEqualTo("plain:a5");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4", "a5")).isSameAs(sub);
        wide.told = "";
        ArityForms.invoke("Sub5", sub, "viaAllVoid", List.of("a1", "a2", "a3", "a4"));
        Assertions.assertThat(wide.told).isEqualTo("putAll:a1,a2,a3,a4|[x, y];");
        Assertions.assertThat(ArityForms.invoke("Sub5", sub, "viaAll", List.of("a1", "a2", "a3", "a4"))).isEqualTo("pickAll:a1,a2,a3,a4|[x, y]");
        Object other = ArityForms.create("Sub5", List.of("a1", "a2", "a3", "a4", 7));
        Assertions.assertThat(((Wide5) other).told).isEqualTo("newAll:a1,a2,a3,a4|[7, 7]");
    }

    @Test
    public void absForms() throws Throwable {
        Object abs = ArityForms.create("Abs5", List.of("a1", "a2", "a3", "a4", "a5"));
        Assertions.assertThat(((Abstract5) abs).told).isEqualTo("abs:a1,a2,a3,a4,a5");
        Object other = ArityForms.create("Abs5", List.of("a1", "a2", "a3", "a4", 7));
        Assertions.assertThat(((Abstract5) other).told).isEqualTo("absAll:a1,a2,a3,a4|[7, 7]");
    }
}
