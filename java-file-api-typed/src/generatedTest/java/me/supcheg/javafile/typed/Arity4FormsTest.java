package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.Arity4FormsTest.AbsSpec;
import me.supcheg.javafile.typed.Arity4FormsTest.PlainSpec;
import me.supcheg.javafile.typed.Arity4FormsTest.SubSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedClassHeaderSpec;
import me.supcheg.javafile.typed.fixtures.Abstract4;
import me.supcheg.javafile.typed.fixtures.Wide4;
import me.supcheg.javafile.typed.fixtures.Widening4;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Abstract4_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Do4_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Fn4_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide4_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Widening4_;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * The forms of the typed layer of arity 4: a class that extends `Object`, one that extends a class and one that extends an abstract class, declared with them, are compiled with the classes of the other arities in one run of javac, and run.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
@Execution(ExecutionMode.SAME_THREAD)
class Arity4FormsTest {
    static final class PlainSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var becoming = h.becoming(Widening4_.TOKEN);
            var inhO = h.overriding(Widening4_.inh_String_String_String_String);
            var inhVoidO = h.overriding(Widening4_.inhVoid_String_String_String_String);
            var narrowO = h.overriding(Widening4_.narrow_String_String_String_String, String_.TOKEN);
            var plainO = h.overriding(Widening4_.plain_String_String_String_String);
            var selfO = h.overriding(Widening4_.self_String_String_String_String, becoming);
            var takeO = h.overriding(Widening4_.take_String_String_String_String);
            var oh = h.extendsObject();
            var asWidening = oh.implements_(Widening4_.implement(h.self()).inhVoid_String_String_String_String(inhVoidO).inh_String_String_String_String(inhO).narrow_String_String_String_String(narrowO).plain_String_String_String_String(plainO).self_String_String_String_String(selfO).take_String_String_String_String(takeO));
            var cb = oh.members();
            var givenM = cb.declareOverride(asWidening, Widening4_.given_String_String_String_String);
            var chosenM = cb.declareOverride(asWidening, Widening4_.chosen_String_String_String_String, String_.TOKEN);
            var chainedM = cb.declareOverride(asWidening, Widening4_.chained_String_String_String_String, asWidening);
            var heldM = cb.declareOverride(asWidening, Widening4_.held_String_String_String_String);
            var ctorM = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var applyM = cb.declareMethod("apply", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var runM = cb.declareVoidMethod("run", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var makeM = cb.declareStaticMethod("make", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var logM = cb.declareVoidStaticMethod("log", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var lamM = cb.declareMethod("lam", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var vctorM = cb.variableArity().declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vapplyM = cb.variableArity().declareMethod("applyAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vrunM = cb.variableArity().declareVoidMethod("runAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vmakeM = cb.variableArity().declareStaticMethod("makeAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vlogM = cb.variableArity().declareVoidStaticMethod("logAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var bodies = cb.bodies();
            bodies.define(inhO.method(), (b, self, p1, p2, p3, p4) -> b.return_(Expressions.concat(Expressions.literal("inh:"), p4)));
            bodies.define(inhVoidO.method(), (b, self, p1, p2, p3, p4) -> b.exec(Expressions.voidStaticCall(Wide4_.note_String_String_String_String, p1, p2, p3, p4)).end());
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p4)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p4)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4) -> b.exec(Expressions.voidStaticCall(Wide4_.note_String_String_String_String, p1, p2, p3, p4)).end());
            bodies.define(givenM, (b, self, p1, p2, p3, p4) -> b.return_(Expressions.concat(Expressions.literal("given:"), p4)));
            bodies.define(chosenM, (b, self, p1, p2, p3, p4) -> b.return_(Expressions.concat(Expressions.literal("chosen:"), p4)));
            bodies.define(chainedM, (b, self, p1, p2, p3, p4) -> b.return_(self));
            bodies.define(heldM, (b, self, p1, p2, p3, p4) -> b.exec(Expressions.voidStaticCall(Wide4_.note_String_String_String_String, p1, p2, p3, p4)).end());
            bodies.define(ctorM, (b, self, p1, p2, p3, p4) -> b.exec(Expressions.voidStaticCall(Wide4_.note_String_String_String_String, p1, p2, p3, p4)).end());
            bodies.define(applyM, (b, self, p1, p2, p3, p4) -> b.let(Wide4_.TOKEN, Expressions.new_(Wide4_.new_String_String_String_String, p1, p2, p3, p4), (w) -> b.exec(w.voidCall(Wide4_.put_String_String_String_String, p1, p2, p3, p4)).exec(Expressions.voidStaticCall(Wide4_.note_String_String_String_String, p1, p2, p3, p4)).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide4_.pick_String_String_String_String, p1, p2, p3, p4), Expressions.literal("/")), Expressions.staticCall(Wide4_.join_String_String_String_String, p1, p2, p3, p4)), Expressions.literal("/")), w.field(Wide4_.told)))));
            bodies.define(runM, (b, self, p1, p2, p3, p4) -> b.exec(Expressions.voidStaticCall(Wide4_.note_String_String_String_String, p1, p2, p3, p4)).end());
            bodies.define(makeM, (b, p1, p2, p3, p4) -> b.return_(Expressions.staticCall(Wide4_.join_String_String_String_String, p1, p2, p3, p4)));
            bodies.define(logM, (b, p1, p2, p3, p4) -> b.exec(Expressions.voidStaticCall(Wide4_.note_String_String_String_String, p1, p2, p3, p4)).end());
            bodies.define(lamM, (b, self, p1, p2, p3, p4) -> b.let(Fn4_.TOKEN, Expressions.lambda(Fn4_.sam, (q1, q2, q3, q4) -> Expressions.concat(Expressions.literal("fn:"), q4)), (f) -> b.let(Fn4_.TOKEN, Expressions.lambdaBlock(Fn4_.sam, (lb, q1, q2, q3, q4) -> lb.return_(Expressions.concat(Expressions.literal("blk:"), q4))), (g) -> b.let(Do4_.TOKEN, Expressions.lambda(Do4_.sam, (q1, q2, q3, q4) -> Expressions.voidStaticCall(Wide4_.note_String_String_String_String, q1, q2, q3, q4)), (d) -> b.let(Do4_.TOKEN, Expressions.lambdaBlock(Do4_.sam, (lb, q1, q2, q3, q4) -> lb.exec(Expressions.voidStaticCall(Wide4_.note_String_String_String_String, q1, q2, q3, q4)).end()), (e) -> b.exec(d.voidCall(Do4_.accept_String_String_String_String, p1, p2, p3, p4)).exec(e.voidCall(Do4_.accept_String_String_String_String, p1, p2, p3, p4)).return_(Expressions.concat(Expressions.concat(f.call(Fn4_.apply_String_String_String_String, p1, p2, p3, p4), Expressions.literal("/")), g.call(Fn4_.apply_String_String_String_String, p1, p2, p3, p4))))))));
            bodies.define(vctorM, (b, self, p1, p2, p3, rest) -> b.exec(Expressions.voidStaticCall(Wide4_.noteAll_String_String_String_StringArray, p1, p2, p3, rest)).end());
            bodies.define(vapplyM, (b, self, p1, p2, p3, rest) -> b.let(Wide4_.TOKEN, Expressions.new_(Wide4_.new_String_String_String_IntegerArray, p1, p2, p3, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), Expressions.box(PrimitiveToken.INT, Expressions.literal(1)), Expressions.box(PrimitiveToken.INT, Expressions.literal(2)))), (w) -> b.exec(w.voidCall(Wide4_.putAll_String_String_String_StringArray, p1, p2, p3, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).exec(Expressions.voidStaticCall(Wide4_.noteAll_String_String_String_StringArray, p1, p2, p3, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide4_.pickAll_String_String_String_StringArray, p1, p2, p3, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y"))), Expressions.literal("/")), Expressions.staticCall(Wide4_.joinAll_String_String_String_StringArray, p1, p2, p3, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))), Expressions.literal("/")), w.field(Wide4_.told)))));
            bodies.define(vrunM, (b, self, p1, p2, p3, rest) -> b.exec(Expressions.voidStaticCall(Wide4_.noteAll_String_String_String_StringArray, p1, p2, p3, rest)).end());
            bodies.define(vmakeM, (b, p1, p2, p3, rest) -> b.return_(Expressions.staticCall(Wide4_.joinAll_String_String_String_StringArray, p1, p2, p3, rest)));
            bodies.define(vlogM, (b, p1, p2, p3, rest) -> b.exec(Expressions.voidStaticCall(Wide4_.noteAll_String_String_String_StringArray, p1, p2, p3, rest)).end());
            return bodies;
        }
    }

    static final class SubSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Wide4_.extend(h.self()));
            var becoming = sh.becoming(Widening4_.TOKEN);
            var inhI = sh.inherited(Wide4_.inh_String_String_String_String);
            var inhVoidI = sh.inherited(Wide4_.inhVoid_String_String_String_String);
            var narrowO = sh.overriding(Widening4_.narrow_String_String_String_String, String_.TOKEN);
            var plainO = sh.overriding(Widening4_.plain_String_String_String_String);
            var selfO = sh.overriding(Widening4_.self_String_String_String_String, becoming);
            var takeO = sh.overriding(Widening4_.take_String_String_String_String);
            var asWidening = sh.implements_(Widening4_.implement(h.self()).inhVoid_String_String_String_String(inhVoidI).inh_String_String_String_String(inhI).narrow_String_String_String_String(narrowO).plain_String_String_String_String(plainO).self_String_String_String_String(selfO).take_String_String_String_String(takeO));
            var cb = sh.members();
            var asWide = cb.asSuper();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var pickM = cb.declareOverride(Wide4_.pick_String_String_String_String);
            var csM = cb.declareOverride(Wide4_.cs_String_String_String_String, String_.TOKEN);
            var sameM = cb.declareOverride(Wide4_.same_String_String_String_String, asWide);
            var putM = cb.declareOverride(Wide4_.put_String_String_String_String);
            var viaAllM = cb.declareMethod("viaAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaAllVoidM = cb.declareVoidMethod("viaAllVoid", String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4) -> c.super_(Wide4_.new_String_String_String_String, p1, p2, p3, p4, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4) -> c.super_(Wide4_.new_String_String_String_IntegerArray, p1, p2, p3, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p4, p4), (b, self) -> b.end()));
            bodies.define(narrowO.method(), (b, self, p1, p2, p3, p4) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p4)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3, p4) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p4)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3, p4) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3, p4) -> b.end());
            bodies.define(pickM, (b, self, p1, p2, p3, p4) -> b.return_(Expressions.concat(Expressions.literal("sub:"), bodies.superCall(self, Wide4_.pick_String_String_String_String, p1, p2, p3, p4))));
            bodies.define(csM, (b, self, p1, p2, p3, p4) -> b.return_(Expressions.concat(Expressions.literal("cs:"), p4)));
            bodies.define(sameM, (b, self, p1, p2, p3, p4) -> b.return_(self));
            bodies.define(putM, (b, self, p1, p2, p3, p4) -> b.exec(bodies.superCall(self, Wide4_.put_String_String_String_String, p1, p2, p3, p4)).end());
            bodies.define(viaAllM, (b, self, p1, p2, p3) -> b.return_(bodies.superCall(self, Wide4_.pickAll_String_String_String_StringArray, p1, p2, p3, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))));
            bodies.define(viaAllVoidM, (b, self, p1, p2, p3) -> b.exec(bodies.superCall(self, Wide4_.putAll_String_String_String_StringArray, p1, p2, p3, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).end());
            return bodies;
        }
    }

    static final class AbsSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Abstract4_.extend(h.self()));
            var cb = sh.members();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3, p4) -> c.super_(Abstract4_.super_String_String_String_String, p1, p2, p3, p4, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3, p4) -> c.super_(Abstract4_.super_String_String_String_IntegerArray, p1, p2, p3, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p4, p4), (b, self) -> b.end()));
            return bodies;
        }
    }

    static JavaFile plain() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Plain4"), new PlainSpec());
    }

    static JavaFile sub() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Sub4"), new SubSpec());
    }

    static JavaFile abs() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Abs4"), new AbsSpec());
    }

    @Test
    public void plainForms() throws Throwable {
        Wide4.noted = "";
        Object plain = ArityForms.create("Plain4", List.of("a1", "a2", "a3", "a4"));
        Assertions.assertThat(Wide4.noted).isEqualTo("note:a1,a2,a3,a4;");
        Wide4.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain4", plain, "apply", List.of("a1", "a2", "a3", "a4"))).isEqualTo("pick:a1,a2,a3,a4/join:a1,a2,a3,a4/new:a1,a2,a3,a4put:a1,a2,a3,a4;");
        Assertions.assertThat(Wide4.noted).isEqualTo("note:a1,a2,a3,a4;");
        Wide4.noted = "";
        ArityForms.invoke("Plain4", plain, "run", List.of("a1", "a2", "a3", "a4"));
        Assertions.assertThat(Wide4.noted).isEqualTo("note:a1,a2,a3,a4;");
        Wide4.noted = "";
        ArityForms.invoke("Plain4", null, "log", List.of("a1", "a2", "a3", "a4"));
        Assertions.assertThat(Wide4.noted).isEqualTo("note:a1,a2,a3,a4;");
        Assertions.assertThat(ArityForms.invoke("Plain4", null, "make", List.of("a1", "a2", "a3", "a4"))).isEqualTo("join:a1,a2,a3,a4");
        Wide4.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain4", plain, "lam", List.of("a1", "a2", "a3", "a4"))).isEqualTo("fn:a4/blk:a4");
        Assertions.assertThat(Wide4.noted).isEqualTo("note:a1,a2,a3,a4;note:a1,a2,a3,a4;");
        Widening4 widening = (Widening4) plain;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4")).isEqualTo("inh:a4");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4")).isEqualTo("narrow:a4");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4")).isEqualTo("plain:a4");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4")).isSameAs(plain);
        Assertions.assertThat(widening.given("a1", "a2", "a3", "a4")).isEqualTo("given:a4");
        Assertions.assertThat(widening.chosen("a1", "a2", "a3", "a4")).isEqualTo("chosen:a4");
        Assertions.assertThat(widening.chained("a1", "a2", "a3", "a4")).isSameAs(plain);
        Wide4.noted = "";
        widening.inhVoid("a1", "a2", "a3", "a4");
        widening.take("a1", "a2", "a3", "a4");
        widening.held("a1", "a2", "a3", "a4");
        Assertions.assertThat(Wide4.noted).isEqualTo("note:a1,a2,a3,a4;note:a1,a2,a3,a4;note:a1,a2,a3,a4;");
        Wide4.noted = "";
        Object variable = ArityForms.create("Plain4", List.of("a1", "a2", "a3", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide4.noted).isEqualTo("noteAll:a1,a2,a3|[r0, r1];");
        Wide4.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain4", variable, "applyAll", List.of("a1", "a2", "a3", (Object) new String[] {"r0", "r1"}))).isEqualTo("pickAll:a1,a2,a3|[x, y]/joinAll:a1,a2,a3|[x, y]/newAll:a1,a2,a3|[1, 2]putAll:a1,a2,a3|[x, y];");
        Assertions.assertThat(Wide4.noted).isEqualTo("noteAll:a1,a2,a3|[x, y];");
        Wide4.noted = "";
        ArityForms.invoke("Plain4", variable, "runAll", List.of("a1", "a2", "a3", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide4.noted).isEqualTo("noteAll:a1,a2,a3|[r0, r1];");
        Wide4.noted = "";
        ArityForms.invoke("Plain4", null, "logAll", List.of("a1", "a2", "a3", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide4.noted).isEqualTo("noteAll:a1,a2,a3|[r0, r1];");
        Assertions.assertThat(ArityForms.invoke("Plain4", null, "makeAll", List.of("a1", "a2", "a3", (Object) new String[] {"r0", "r1"}))).isEqualTo("joinAll:a1,a2,a3|[r0, r1]");
    }

    @Test
    public void subForms() throws Throwable {
        Object sub = ArityForms.create("Sub4", List.of("a1", "a2", "a3", "a4"));
        Wide4 wide = (Wide4) sub;
        Assertions.assertThat(wide.told).isEqualTo("new:a1,a2,a3,a4");
        Assertions.assertThat(wide.pick("a1", "a2", "a3", "a4")).isEqualTo("sub:pick:a1,a2,a3,a4");
        Assertions.assertThat(wide.cs("a1", "a2", "a3", "a4")).isEqualTo("cs:a4");
        Assertions.assertThat(wide.same("a1", "a2", "a3", "a4")).isSameAs(sub);
        wide.told = "";
        wide.put("a1", "a2", "a3", "a4");
        Assertions.assertThat(wide.told).isEqualTo("put:a1,a2,a3,a4;");
        Widening4 widening = (Widening4) sub;
        Assertions.assertThat(widening.inh("a1", "a2", "a3", "a4")).isEqualTo("inh:a1,a2,a3,a4");
        wide.told = "";
        widening.inhVoid("a1", "a2", "a3", "a4");
        Assertions.assertThat(wide.told).isEqualTo("inhVoid:a1,a2,a3,a4;");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3", "a4")).isEqualTo("narrow:a4");
        Assertions.assertThat(widening.plain("a1", "a2", "a3", "a4")).isEqualTo("plain:a4");
        Assertions.assertThat(widening.self("a1", "a2", "a3", "a4")).isSameAs(sub);
        wide.told = "";
        ArityForms.invoke("Sub4", sub, "viaAllVoid", List.of("a1", "a2", "a3"));
        Assertions.assertThat(wide.told).isEqualTo("putAll:a1,a2,a3|[x, y];");
        Assertions.assertThat(ArityForms.invoke("Sub4", sub, "viaAll", List.of("a1", "a2", "a3"))).isEqualTo("pickAll:a1,a2,a3|[x, y]");
        Object other = ArityForms.create("Sub4", List.of("a1", "a2", "a3", 7));
        Assertions.assertThat(((Wide4) other).told).isEqualTo("newAll:a1,a2,a3|[7, 7]");
    }

    @Test
    public void absForms() throws Throwable {
        Object abs = ArityForms.create("Abs4", List.of("a1", "a2", "a3", "a4"));
        Assertions.assertThat(((Abstract4) abs).told).isEqualTo("abs:a1,a2,a3,a4");
        Object other = ArityForms.create("Abs4", List.of("a1", "a2", "a3", 7));
        Assertions.assertThat(((Abstract4) other).told).isEqualTo("absAll:a1,a2,a3|[7, 7]");
    }
}
