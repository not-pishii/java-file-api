package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.Arity2FormsTest.AbsSpec;
import me.supcheg.javafile.typed.Arity2FormsTest.PlainSpec;
import me.supcheg.javafile.typed.Arity2FormsTest.SubSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedClassHeaderSpec;
import me.supcheg.javafile.typed.fixtures.Abstract2;
import me.supcheg.javafile.typed.fixtures.Wide2;
import me.supcheg.javafile.typed.fixtures.Widening2;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Abstract2_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Do2_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Fn2_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide2_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Widening2_;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * The forms of the typed layer of arity 2: a class that extends `Object`, one that extends a class and one that extends an abstract class, declared with them, are compiled with the classes of the other arities in one run of javac, and run.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
@Execution(ExecutionMode.SAME_THREAD)
class Arity2FormsTest {
    static final class PlainSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var becoming = h.becoming(Widening2_.TOKEN);
            var inhO = h.overriding(Widening2_.inh_String_String);
            var inhVoidO = h.overriding(Widening2_.inhVoid_String_String);
            var narrowO = h.overriding(Widening2_.narrow_String_String, String_.TOKEN);
            var plainO = h.overriding(Widening2_.plain_String_String);
            var selfO = h.overriding(Widening2_.self_String_String, becoming);
            var takeO = h.overriding(Widening2_.take_String_String);
            var oh = h.extendsObject();
            var asWidening = oh.implements_(Widening2_.implement(h.self()).inhVoid_String_String(inhVoidO).inh_String_String(inhO).narrow_String_String(narrowO).plain_String_String(plainO).self_String_String(selfO).take_String_String(takeO));
            var cb = oh.members();
            var givenM = cb.declareOverride(asWidening, Widening2_.given_String_String);
            var chosenM = cb.declareOverride(asWidening, Widening2_.chosen_String_String, String_.TOKEN);
            var chainedM = cb.declareOverride(asWidening, Widening2_.chained_String_String, asWidening);
            var heldM = cb.declareOverride(asWidening, Widening2_.held_String_String);
            var ctorM = cb.declareConstructor(String_.TOKEN, String_.TOKEN);
            var applyM = cb.declareMethod("apply", String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var runM = cb.declareVoidMethod("run", String_.TOKEN, String_.TOKEN);
            var makeM = cb.declareStaticMethod("make", String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var logM = cb.declareVoidStaticMethod("log", String_.TOKEN, String_.TOKEN);
            var lamM = cb.declareMethod("lam", String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var vctorM = cb.variableArity().declareConstructor(String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vapplyM = cb.variableArity().declareMethod("applyAll", String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vrunM = cb.variableArity().declareVoidMethod("runAll", String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vmakeM = cb.variableArity().declareStaticMethod("makeAll", String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vlogM = cb.variableArity().declareVoidStaticMethod("logAll", String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var bodies = cb.bodies();
            bodies.define(inhO.method(), (b, self, p1, p2) -> b.return_(Expressions.concat(Expressions.literal("inh:"), p2)));
            bodies.define(inhVoidO.method(), (b, self, p1, p2) -> b.exec(Expressions.voidStaticCall(Wide2_.note_String_String, p1, p2)).end());
            bodies.define(narrowO.method(), (b, self, p1, p2) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p2)));
            bodies.define(plainO.method(), (b, self, p1, p2) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p2)));
            bodies.define(selfO.method(), (b, self, p1, p2) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2) -> b.exec(Expressions.voidStaticCall(Wide2_.note_String_String, p1, p2)).end());
            bodies.define(givenM, (b, self, p1, p2) -> b.return_(Expressions.concat(Expressions.literal("given:"), p2)));
            bodies.define(chosenM, (b, self, p1, p2) -> b.return_(Expressions.concat(Expressions.literal("chosen:"), p2)));
            bodies.define(chainedM, (b, self, p1, p2) -> b.return_(self));
            bodies.define(heldM, (b, self, p1, p2) -> b.exec(Expressions.voidStaticCall(Wide2_.note_String_String, p1, p2)).end());
            bodies.define(ctorM, (b, self, p1, p2) -> b.exec(Expressions.voidStaticCall(Wide2_.note_String_String, p1, p2)).end());
            bodies.define(applyM, (b, self, p1, p2) -> b.let(Wide2_.TOKEN, Expressions.new_(Wide2_.new_String_String, p1, p2), (w) -> b.exec(Expressions.voidCall(w, Wide2_.put_String_String, p1, p2)).exec(Expressions.voidStaticCall(Wide2_.note_String_String, p1, p2)).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.call(w, Wide2_.pick_String_String, p1, p2), Expressions.literal("/")), Expressions.staticCall(Wide2_.join_String_String, p1, p2)), Expressions.literal("/")), Expressions.field(w, Wide2_.told)))));
            bodies.define(runM, (b, self, p1, p2) -> b.exec(Expressions.voidStaticCall(Wide2_.note_String_String, p1, p2)).end());
            bodies.define(makeM, (b, p1, p2) -> b.return_(Expressions.staticCall(Wide2_.join_String_String, p1, p2)));
            bodies.define(logM, (b, p1, p2) -> b.exec(Expressions.voidStaticCall(Wide2_.note_String_String, p1, p2)).end());
            bodies.define(lamM, (b, self, p1, p2) -> b.let(Fn2_.TOKEN, Expressions.lambda(Fn2_.sam, (q1, q2) -> Expressions.concat(Expressions.literal("fn:"), q2)), (f) -> b.let(Fn2_.TOKEN, Expressions.lambdaBlock(Fn2_.sam, (lb, q1, q2) -> lb.return_(Expressions.concat(Expressions.literal("blk:"), q2))), (g) -> b.let(Do2_.TOKEN, Expressions.lambda(Do2_.sam, (q1, q2) -> Expressions.voidStaticCall(Wide2_.note_String_String, q1, q2)), (d) -> b.let(Do2_.TOKEN, Expressions.lambdaBlock(Do2_.sam, (lb, q1, q2) -> lb.exec(Expressions.voidStaticCall(Wide2_.note_String_String, q1, q2)).end()), (e) -> b.exec(Expressions.voidCall(d, Do2_.accept_String_String, p1, p2)).exec(Expressions.voidCall(e, Do2_.accept_String_String, p1, p2)).return_(Expressions.concat(Expressions.concat(Expressions.call(f, Fn2_.apply_String_String, p1, p2), Expressions.literal("/")), Expressions.call(g, Fn2_.apply_String_String, p1, p2))))))));
            bodies.define(vctorM, (b, self, p1, rest) -> b.exec(Expressions.voidStaticCall(Wide2_.noteAll_String_StringArray, p1, rest)).end());
            bodies.define(vapplyM, (b, self, p1, rest) -> b.let(Wide2_.TOKEN, Expressions.new_(Wide2_.new_String_IntegerArray, p1, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), Expressions.box(PrimitiveToken.INT, Expressions.literal(1)), Expressions.box(PrimitiveToken.INT, Expressions.literal(2)))), (w) -> b.exec(Expressions.voidCall(w, Wide2_.putAll_String_StringArray, p1, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).exec(Expressions.voidStaticCall(Wide2_.noteAll_String_StringArray, p1, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.call(w, Wide2_.pickAll_String_StringArray, p1, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y"))), Expressions.literal("/")), Expressions.staticCall(Wide2_.joinAll_String_StringArray, p1, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))), Expressions.literal("/")), Expressions.field(w, Wide2_.told)))));
            bodies.define(vrunM, (b, self, p1, rest) -> b.exec(Expressions.voidStaticCall(Wide2_.noteAll_String_StringArray, p1, rest)).end());
            bodies.define(vmakeM, (b, p1, rest) -> b.return_(Expressions.staticCall(Wide2_.joinAll_String_StringArray, p1, rest)));
            bodies.define(vlogM, (b, p1, rest) -> b.exec(Expressions.voidStaticCall(Wide2_.noteAll_String_StringArray, p1, rest)).end());
            return bodies;
        }
    }

    static final class SubSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Wide2_.extend(h.self()));
            var becoming = sh.becoming(Widening2_.TOKEN);
            var inhI = sh.inherited(Wide2_.inh_String_String);
            var inhVoidI = sh.inherited(Wide2_.inhVoid_String_String);
            var narrowO = sh.overriding(Widening2_.narrow_String_String, String_.TOKEN);
            var plainO = sh.overriding(Widening2_.plain_String_String);
            var selfO = sh.overriding(Widening2_.self_String_String, becoming);
            var takeO = sh.overriding(Widening2_.take_String_String);
            var asWidening = sh.implements_(Widening2_.implement(h.self()).inhVoid_String_String(inhVoidI).inh_String_String(inhI).narrow_String_String(narrowO).plain_String_String(plainO).self_String_String(selfO).take_String_String(takeO));
            var cb = sh.members();
            var asWide = cb.asSuper();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, Integer_.TOKEN);
            var pickM = cb.declareOverride(Wide2_.pick_String_String);
            var csM = cb.declareOverride(Wide2_.cs_String_String, String_.TOKEN);
            var sameM = cb.declareOverride(Wide2_.same_String_String, asWide);
            var putM = cb.declareOverride(Wide2_.put_String_String);
            var viaAllM = cb.declareMethod("viaAll", String_.TOKEN, String_.TOKEN);
            var viaAllVoidM = cb.declareVoidMethod("viaAllVoid", String_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2) -> c.super_(Wide2_.new_String_String, p1, p2, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2) -> c.super_(Wide2_.new_String_IntegerArray, p1, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p2, p2), (b, self) -> b.end()));
            bodies.define(narrowO.method(), (b, self, p1, p2) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p2)));
            bodies.define(plainO.method(), (b, self, p1, p2) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p2)));
            bodies.define(selfO.method(), (b, self, p1, p2) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2) -> b.end());
            bodies.define(pickM, (b, self, p1, p2) -> b.return_(Expressions.concat(Expressions.literal("sub:"), bodies.superCall(self, Wide2_.pick_String_String, p1, p2))));
            bodies.define(csM, (b, self, p1, p2) -> b.return_(Expressions.concat(Expressions.literal("cs:"), p2)));
            bodies.define(sameM, (b, self, p1, p2) -> b.return_(self));
            bodies.define(putM, (b, self, p1, p2) -> b.exec(bodies.superCall(self, Wide2_.put_String_String, p1, p2)).end());
            bodies.define(viaAllM, (b, self, p1) -> b.return_(bodies.superCall(self, Wide2_.pickAll_String_StringArray, p1, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))));
            bodies.define(viaAllVoidM, (b, self, p1) -> b.exec(bodies.superCall(self, Wide2_.putAll_String_StringArray, p1, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).end());
            return bodies;
        }
    }

    static final class AbsSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Abstract2_.extend(h.self()));
            var cb = sh.members();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, Integer_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2) -> c.super_(Abstract2_.super_String_String, p1, p2, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2) -> c.super_(Abstract2_.super_String_IntegerArray, p1, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p2, p2), (b, self) -> b.end()));
            return bodies;
        }
    }

    static JavaFile plain() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Plain2"), new PlainSpec());
    }

    static JavaFile sub() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Sub2"), new SubSpec());
    }

    static JavaFile abs() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Abs2"), new AbsSpec());
    }

    @Test
    public void plainForms() throws Throwable {
        Wide2.noted = "";
        Object plain = ArityForms.create("Plain2", List.of("a1", "a2"));
        Assertions.assertThat(Wide2.noted).isEqualTo("note:a1,a2;");
        Wide2.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain2", plain, "apply", List.of("a1", "a2"))).isEqualTo("pick:a1,a2/join:a1,a2/new:a1,a2put:a1,a2;");
        Assertions.assertThat(Wide2.noted).isEqualTo("note:a1,a2;");
        Wide2.noted = "";
        ArityForms.invoke("Plain2", plain, "run", List.of("a1", "a2"));
        Assertions.assertThat(Wide2.noted).isEqualTo("note:a1,a2;");
        Wide2.noted = "";
        ArityForms.invoke("Plain2", null, "log", List.of("a1", "a2"));
        Assertions.assertThat(Wide2.noted).isEqualTo("note:a1,a2;");
        Assertions.assertThat(ArityForms.invoke("Plain2", null, "make", List.of("a1", "a2"))).isEqualTo("join:a1,a2");
        Wide2.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain2", plain, "lam", List.of("a1", "a2"))).isEqualTo("fn:a2/blk:a2");
        Assertions.assertThat(Wide2.noted).isEqualTo("note:a1,a2;note:a1,a2;");
        Widening2 widening = (Widening2) plain;
        Assertions.assertThat(widening.inh("a1", "a2")).isEqualTo("inh:a2");
        Assertions.assertThat(widening.narrow("a1", "a2")).isEqualTo("narrow:a2");
        Assertions.assertThat(widening.plain("a1", "a2")).isEqualTo("plain:a2");
        Assertions.assertThat(widening.self("a1", "a2")).isSameAs(plain);
        Assertions.assertThat(widening.given("a1", "a2")).isEqualTo("given:a2");
        Assertions.assertThat(widening.chosen("a1", "a2")).isEqualTo("chosen:a2");
        Assertions.assertThat(widening.chained("a1", "a2")).isSameAs(plain);
        Wide2.noted = "";
        widening.inhVoid("a1", "a2");
        widening.take("a1", "a2");
        widening.held("a1", "a2");
        Assertions.assertThat(Wide2.noted).isEqualTo("note:a1,a2;note:a1,a2;note:a1,a2;");
        Wide2.noted = "";
        Object variable = ArityForms.create("Plain2", List.of("a1", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide2.noted).isEqualTo("noteAll:a1|[r0, r1];");
        Wide2.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain2", variable, "applyAll", List.of("a1", (Object) new String[] {"r0", "r1"}))).isEqualTo("pickAll:a1|[x, y]/joinAll:a1|[x, y]/newAll:a1|[1, 2]putAll:a1|[x, y];");
        Assertions.assertThat(Wide2.noted).isEqualTo("noteAll:a1|[x, y];");
        Wide2.noted = "";
        ArityForms.invoke("Plain2", variable, "runAll", List.of("a1", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide2.noted).isEqualTo("noteAll:a1|[r0, r1];");
        Wide2.noted = "";
        ArityForms.invoke("Plain2", null, "logAll", List.of("a1", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide2.noted).isEqualTo("noteAll:a1|[r0, r1];");
        Assertions.assertThat(ArityForms.invoke("Plain2", null, "makeAll", List.of("a1", (Object) new String[] {"r0", "r1"}))).isEqualTo("joinAll:a1|[r0, r1]");
    }

    @Test
    public void subForms() throws Throwable {
        Object sub = ArityForms.create("Sub2", List.of("a1", "a2"));
        Wide2 wide = (Wide2) sub;
        Assertions.assertThat(wide.told).isEqualTo("new:a1,a2");
        Assertions.assertThat(wide.pick("a1", "a2")).isEqualTo("sub:pick:a1,a2");
        Assertions.assertThat(wide.cs("a1", "a2")).isEqualTo("cs:a2");
        Assertions.assertThat(wide.same("a1", "a2")).isSameAs(sub);
        wide.told = "";
        wide.put("a1", "a2");
        Assertions.assertThat(wide.told).isEqualTo("put:a1,a2;");
        Widening2 widening = (Widening2) sub;
        Assertions.assertThat(widening.inh("a1", "a2")).isEqualTo("inh:a1,a2");
        wide.told = "";
        widening.inhVoid("a1", "a2");
        Assertions.assertThat(wide.told).isEqualTo("inhVoid:a1,a2;");
        Assertions.assertThat(widening.narrow("a1", "a2")).isEqualTo("narrow:a2");
        Assertions.assertThat(widening.plain("a1", "a2")).isEqualTo("plain:a2");
        Assertions.assertThat(widening.self("a1", "a2")).isSameAs(sub);
        wide.told = "";
        ArityForms.invoke("Sub2", sub, "viaAllVoid", List.of("a1"));
        Assertions.assertThat(wide.told).isEqualTo("putAll:a1|[x, y];");
        Assertions.assertThat(ArityForms.invoke("Sub2", sub, "viaAll", List.of("a1"))).isEqualTo("pickAll:a1|[x, y]");
        Object other = ArityForms.create("Sub2", List.of("a1", 7));
        Assertions.assertThat(((Wide2) other).told).isEqualTo("newAll:a1|[7, 7]");
    }

    @Test
    public void absForms() throws Throwable {
        Object abs = ArityForms.create("Abs2", List.of("a1", "a2"));
        Assertions.assertThat(((Abstract2) abs).told).isEqualTo("abs:a1,a2");
        Object other = ArityForms.create("Abs2", List.of("a1", 7));
        Assertions.assertThat(((Abstract2) other).told).isEqualTo("absAll:a1|[7, 7]");
    }
}
