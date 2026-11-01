package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.Arity3FormsTest.AbsSpec;
import me.supcheg.javafile.typed.Arity3FormsTest.PlainSpec;
import me.supcheg.javafile.typed.Arity3FormsTest.SubSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedClassHeaderSpec;
import me.supcheg.javafile.typed.fixtures.Abstract3;
import me.supcheg.javafile.typed.fixtures.Make3;
import me.supcheg.javafile.typed.fixtures.Wide3;
import me.supcheg.javafile.typed.fixtures.Widening3;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Abstract3_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Do3_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Fn3_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Make3_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide3_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Widening3_;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * The forms of the typed layer of arity 3: a class that extends `Object`, one that extends a class and one that extends an abstract class, declared with them, are compiled with the classes of the other arities in one run of javac, and run.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
@Execution(ExecutionMode.SAME_THREAD)
class Arity3FormsTest {
    static final class PlainSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var becoming = h.becoming(Widening3_.TOKEN);
            var inhO = h.overriding(Widening3_.inh_String_String_String);
            var inhVoidO = h.overriding(Widening3_.inhVoid_String_String_String);
            var narrowO = h.overriding(Widening3_.narrow_String_String_String, String_.TOKEN);
            var plainO = h.overriding(Widening3_.plain_String_String_String);
            var selfO = h.overriding(Widening3_.self_String_String_String, becoming);
            var takeO = h.overriding(Widening3_.take_String_String_String);
            var oh = h.extendsObject();
            var asWidening = oh.implements_(Widening3_.implement(h.self()).inhVoid_String_String_String(inhVoidO).inh_String_String_String(inhO).narrow_String_String_String(narrowO).plain_String_String_String(plainO).self_String_String_String(selfO).take_String_String_String(takeO));
            var cb = oh.members();
            var givenM = cb.declareOverride(asWidening, Widening3_.given_String_String_String);
            var chosenM = cb.declareOverride(asWidening, Widening3_.chosen_String_String_String, String_.TOKEN);
            var chainedM = cb.declareOverride(asWidening, Widening3_.chained_String_String_String, asWidening);
            var heldM = cb.declareOverride(asWidening, Widening3_.held_String_String_String);
            var ctorM = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var applyM = cb.declareMethod("apply", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var runM = cb.declareVoidMethod("run", String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var makeM = cb.declareStaticMethod("make", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var logM = cb.declareVoidStaticMethod("log", String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var lamM = cb.declareMethod("lam", String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaCtorM = cb.declareStaticMethod("viaCtor", Make3_.TOKEN);
            var vctorM = cb.variableArity().declareConstructor(String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vapplyM = cb.variableArity().declareMethod("applyAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vrunM = cb.variableArity().declareVoidMethod("runAll", String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vmakeM = cb.variableArity().declareStaticMethod("makeAll", String_.TOKEN, String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vlogM = cb.variableArity().declareVoidStaticMethod("logAll", String_.TOKEN, String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var bodies = cb.bodies();
            bodies.define(inhO.method(), (b, self, p1, p2, p3) -> b.return_(Expressions.concat(Expressions.literal("inh:"), p3)));
            bodies.define(inhVoidO.method(), (b, self, p1, p2, p3) -> b.exec(Expressions.voidStaticCall(Wide3_.note_String_String_String, p1, p2, p3)).end());
            bodies.define(narrowO.method(), (b, self, p1, p2, p3) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p3)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p3)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3) -> b.exec(Expressions.voidStaticCall(Wide3_.note_String_String_String, p1, p2, p3)).end());
            bodies.define(givenM, (b, self, p1, p2, p3) -> b.return_(Expressions.concat(Expressions.literal("given:"), p3)));
            bodies.define(chosenM, (b, self, p1, p2, p3) -> b.return_(Expressions.concat(Expressions.literal("chosen:"), p3)));
            bodies.define(chainedM, (b, self, p1, p2, p3) -> b.return_(self));
            bodies.define(heldM, (b, self, p1, p2, p3) -> b.exec(Expressions.voidStaticCall(Wide3_.note_String_String_String, p1, p2, p3)).end());
            bodies.define(ctorM, (b, self, p1, p2, p3) -> b.exec(Expressions.voidStaticCall(Wide3_.note_String_String_String, p1, p2, p3)).end());
            bodies.define(applyM, (b, self, p1, p2, p3) -> b.let(Wide3_.TOKEN, Expressions.new_(Wide3_.new_String_String_String, p1, p2, p3), (w) -> b.exec(w.voidCall(Wide3_.put_String_String_String, p1, p2, p3)).exec(Expressions.voidStaticCall(Wide3_.note_String_String_String, p1, p2, p3)).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide3_.pick_String_String_String, p1, p2, p3), Expressions.literal("/")), Expressions.staticCall(Wide3_.join_String_String_String, p1, p2, p3)), Expressions.literal("/")), w.field(Wide3_.told)))));
            bodies.define(runM, (b, self, p1, p2, p3) -> b.exec(Expressions.voidStaticCall(Wide3_.note_String_String_String, p1, p2, p3)).end());
            bodies.define(makeM, (b, p1, p2, p3) -> b.return_(Expressions.staticCall(Wide3_.join_String_String_String, p1, p2, p3)));
            bodies.define(logM, (b, p1, p2, p3) -> b.exec(Expressions.voidStaticCall(Wide3_.note_String_String_String, p1, p2, p3)).end());
            bodies.define(lamM, (b, self, p1, p2, p3) -> b.let(Fn3_.TOKEN, Expressions.lambda(Fn3_.sam, (q1, q2, q3) -> Expressions.concat(Expressions.literal("fn:"), q3)), (f) -> b.let(Fn3_.TOKEN, Expressions.lambdaBlock(Fn3_.sam, (lb, q1, q2, q3) -> lb.return_(Expressions.concat(Expressions.literal("blk:"), q3))), (g) -> b.let(Do3_.TOKEN, Expressions.lambda(Do3_.sam, (q1, q2, q3) -> Expressions.voidStaticCall(Wide3_.note_String_String_String, q1, q2, q3)), (d) -> b.let(Do3_.TOKEN, Expressions.lambdaBlock(Do3_.sam, (lb, q1, q2, q3) -> lb.exec(Expressions.voidStaticCall(Wide3_.note_String_String_String, q1, q2, q3)).end()), (e) -> b.exec(d.voidCall(Do3_.accept_String_String_String, p1, p2, p3)).exec(e.voidCall(Do3_.accept_String_String_String, p1, p2, p3)).return_(Expressions.concat(Expressions.concat(f.call(Fn3_.apply_String_String_String, p1, p2, p3), Expressions.literal("/")), g.call(Fn3_.apply_String_String_String, p1, p2, p3))))))));
            bodies.define(viaCtorM, (b) -> b.return_(Expressions.constructorRef(Wide3_.new_String_String_String, Make3_.TOKEN, Make3_.sam.method())));
            bodies.define(vctorM, (b, self, p1, p2, rest) -> b.exec(Expressions.voidStaticCall(Wide3_.noteAll_String_String_StringArray, p1, p2, rest)).end());
            bodies.define(vapplyM, (b, self, p1, p2, rest) -> b.let(Wide3_.TOKEN, Expressions.new_(Wide3_.new_String_String_IntegerArray, p1, p2, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), Expressions.box(PrimitiveToken.INT, Expressions.literal(1)), Expressions.box(PrimitiveToken.INT, Expressions.literal(2)))), (w) -> b.exec(w.voidCall(Wide3_.putAll_String_String_StringArray, p1, p2, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).exec(Expressions.voidStaticCall(Wide3_.noteAll_String_String_StringArray, p1, p2, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide3_.pickAll_String_String_StringArray, p1, p2, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y"))), Expressions.literal("/")), Expressions.staticCall(Wide3_.joinAll_String_String_StringArray, p1, p2, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))), Expressions.literal("/")), w.field(Wide3_.told)))));
            bodies.define(vrunM, (b, self, p1, p2, rest) -> b.exec(Expressions.voidStaticCall(Wide3_.noteAll_String_String_StringArray, p1, p2, rest)).end());
            bodies.define(vmakeM, (b, p1, p2, rest) -> b.return_(Expressions.staticCall(Wide3_.joinAll_String_String_StringArray, p1, p2, rest)));
            bodies.define(vlogM, (b, p1, p2, rest) -> b.exec(Expressions.voidStaticCall(Wide3_.noteAll_String_String_StringArray, p1, p2, rest)).end());
            return bodies;
        }
    }

    static final class SubSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Wide3_.extend(h.self()));
            var becoming = sh.becoming(Widening3_.TOKEN);
            var inhI = sh.inherited(Wide3_.inh_String_String_String);
            var inhVoidI = sh.inherited(Wide3_.inhVoid_String_String_String);
            var narrowO = sh.overriding(Widening3_.narrow_String_String_String, String_.TOKEN);
            var plainO = sh.overriding(Widening3_.plain_String_String_String);
            var selfO = sh.overriding(Widening3_.self_String_String_String, becoming);
            var takeO = sh.overriding(Widening3_.take_String_String_String);
            var asWidening = sh.implements_(Widening3_.implement(h.self()).inhVoid_String_String_String(inhVoidI).inh_String_String_String(inhI).narrow_String_String_String(narrowO).plain_String_String_String(plainO).self_String_String_String(selfO).take_String_String_String(takeO));
            var cb = sh.members();
            var asWide = cb.asSuper();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var pickM = cb.declareOverride(Wide3_.pick_String_String_String);
            var csM = cb.declareOverride(Wide3_.cs_String_String_String, String_.TOKEN);
            var sameM = cb.declareOverride(Wide3_.same_String_String_String, asWide);
            var putM = cb.declareOverride(Wide3_.put_String_String_String);
            var viaAllM = cb.declareMethod("viaAll", String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var viaAllVoidM = cb.declareVoidMethod("viaAllVoid", String_.TOKEN, String_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3) -> c.super_(Wide3_.new_String_String_String, p1, p2, p3, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3) -> c.super_(Wide3_.new_String_String_IntegerArray, p1, p2, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p3, p3), (b, self) -> b.end()));
            bodies.define(narrowO.method(), (b, self, p1, p2, p3) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p3)));
            bodies.define(plainO.method(), (b, self, p1, p2, p3) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p3)));
            bodies.define(selfO.method(), (b, self, p1, p2, p3) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1, p2, p3) -> b.end());
            bodies.define(pickM, (b, self, p1, p2, p3) -> b.return_(Expressions.concat(Expressions.literal("sub:"), bodies.superCall(self, Wide3_.pick_String_String_String, p1, p2, p3))));
            bodies.define(csM, (b, self, p1, p2, p3) -> b.return_(Expressions.concat(Expressions.literal("cs:"), p3)));
            bodies.define(sameM, (b, self, p1, p2, p3) -> b.return_(self));
            bodies.define(putM, (b, self, p1, p2, p3) -> b.exec(bodies.superCall(self, Wide3_.put_String_String_String, p1, p2, p3)).end());
            bodies.define(viaAllM, (b, self, p1, p2) -> b.return_(bodies.superCall(self, Wide3_.pickAll_String_String_StringArray, p1, p2, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))));
            bodies.define(viaAllVoidM, (b, self, p1, p2) -> b.exec(bodies.superCall(self, Wide3_.putAll_String_String_StringArray, p1, p2, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).end());
            return bodies;
        }
    }

    static final class AbsSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Abstract3_.extend(h.self()));
            var cb = sh.members();
            var ctorA = cb.declareConstructor(String_.TOKEN, String_.TOKEN, String_.TOKEN);
            var ctorB = cb.declareConstructor(String_.TOKEN, String_.TOKEN, Integer_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1, p2, p3) -> c.super_(Abstract3_.super_String_String_String, p1, p2, p3, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1, p2, p3) -> c.super_(Abstract3_.super_String_String_IntegerArray, p1, p2, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p3, p3), (b, self) -> b.end()));
            return bodies;
        }
    }

    static JavaFile plain() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Plain3"), new PlainSpec());
    }

    static JavaFile sub() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Sub3"), new SubSpec());
    }

    static JavaFile abs() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Abs3"), new AbsSpec());
    }

    @Test
    public void plainForms() throws Throwable {
        Wide3.noted = "";
        Object plain = ArityForms.create("Plain3", List.of("a1", "a2", "a3"));
        Assertions.assertThat(Wide3.noted).isEqualTo("note:a1,a2,a3;");
        Wide3.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain3", plain, "apply", List.of("a1", "a2", "a3"))).isEqualTo("pick:a1,a2,a3/join:a1,a2,a3/new:a1,a2,a3put:a1,a2,a3;");
        Assertions.assertThat(Wide3.noted).isEqualTo("note:a1,a2,a3;");
        Wide3.noted = "";
        ArityForms.invoke("Plain3", plain, "run", List.of("a1", "a2", "a3"));
        Assertions.assertThat(Wide3.noted).isEqualTo("note:a1,a2,a3;");
        Wide3.noted = "";
        ArityForms.invoke("Plain3", null, "log", List.of("a1", "a2", "a3"));
        Assertions.assertThat(Wide3.noted).isEqualTo("note:a1,a2,a3;");
        Assertions.assertThat(ArityForms.invoke("Plain3", null, "make", List.of("a1", "a2", "a3"))).isEqualTo("join:a1,a2,a3");
        Wide3.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain3", plain, "lam", List.of("a1", "a2", "a3"))).isEqualTo("fn:a3/blk:a3");
        Assertions.assertThat(Wide3.noted).isEqualTo("note:a1,a2,a3;note:a1,a2,a3;");
        Make3 make = (Make3) ArityForms.invoke("Plain3", null, "viaCtor", List.of());
        Assertions.assertThat(make.make("a1", "a2", "a3").told).isEqualTo("new:a1,a2,a3");
        Widening3 widening = (Widening3) plain;
        Assertions.assertThat(widening.inh("a1", "a2", "a3")).isEqualTo("inh:a3");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3")).isEqualTo("narrow:a3");
        Assertions.assertThat(widening.plain("a1", "a2", "a3")).isEqualTo("plain:a3");
        Assertions.assertThat(widening.self("a1", "a2", "a3")).isSameAs(plain);
        Assertions.assertThat(widening.given("a1", "a2", "a3")).isEqualTo("given:a3");
        Assertions.assertThat(widening.chosen("a1", "a2", "a3")).isEqualTo("chosen:a3");
        Assertions.assertThat(widening.chained("a1", "a2", "a3")).isSameAs(plain);
        Wide3.noted = "";
        widening.inhVoid("a1", "a2", "a3");
        widening.take("a1", "a2", "a3");
        widening.held("a1", "a2", "a3");
        Assertions.assertThat(Wide3.noted).isEqualTo("note:a1,a2,a3;note:a1,a2,a3;note:a1,a2,a3;");
        Wide3.noted = "";
        Object variable = ArityForms.create("Plain3", List.of("a1", "a2", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide3.noted).isEqualTo("noteAll:a1,a2|[r0, r1];");
        Wide3.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain3", variable, "applyAll", List.of("a1", "a2", (Object) new String[] {"r0", "r1"}))).isEqualTo("pickAll:a1,a2|[x, y]/joinAll:a1,a2|[x, y]/newAll:a1,a2|[1, 2]putAll:a1,a2|[x, y];");
        Assertions.assertThat(Wide3.noted).isEqualTo("noteAll:a1,a2|[x, y];");
        Wide3.noted = "";
        ArityForms.invoke("Plain3", variable, "runAll", List.of("a1", "a2", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide3.noted).isEqualTo("noteAll:a1,a2|[r0, r1];");
        Wide3.noted = "";
        ArityForms.invoke("Plain3", null, "logAll", List.of("a1", "a2", (Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide3.noted).isEqualTo("noteAll:a1,a2|[r0, r1];");
        Assertions.assertThat(ArityForms.invoke("Plain3", null, "makeAll", List.of("a1", "a2", (Object) new String[] {"r0", "r1"}))).isEqualTo("joinAll:a1,a2|[r0, r1]");
    }

    @Test
    public void subForms() throws Throwable {
        Object sub = ArityForms.create("Sub3", List.of("a1", "a2", "a3"));
        Wide3 wide = (Wide3) sub;
        Assertions.assertThat(wide.told).isEqualTo("new:a1,a2,a3");
        Assertions.assertThat(wide.pick("a1", "a2", "a3")).isEqualTo("sub:pick:a1,a2,a3");
        Assertions.assertThat(wide.cs("a1", "a2", "a3")).isEqualTo("cs:a3");
        Assertions.assertThat(wide.same("a1", "a2", "a3")).isSameAs(sub);
        wide.told = "";
        wide.put("a1", "a2", "a3");
        Assertions.assertThat(wide.told).isEqualTo("put:a1,a2,a3;");
        Widening3 widening = (Widening3) sub;
        Assertions.assertThat(widening.inh("a1", "a2", "a3")).isEqualTo("inh:a1,a2,a3");
        wide.told = "";
        widening.inhVoid("a1", "a2", "a3");
        Assertions.assertThat(wide.told).isEqualTo("inhVoid:a1,a2,a3;");
        Assertions.assertThat(widening.narrow("a1", "a2", "a3")).isEqualTo("narrow:a3");
        Assertions.assertThat(widening.plain("a1", "a2", "a3")).isEqualTo("plain:a3");
        Assertions.assertThat(widening.self("a1", "a2", "a3")).isSameAs(sub);
        wide.told = "";
        ArityForms.invoke("Sub3", sub, "viaAllVoid", List.of("a1", "a2"));
        Assertions.assertThat(wide.told).isEqualTo("putAll:a1,a2|[x, y];");
        Assertions.assertThat(ArityForms.invoke("Sub3", sub, "viaAll", List.of("a1", "a2"))).isEqualTo("pickAll:a1,a2|[x, y]");
        Object other = ArityForms.create("Sub3", List.of("a1", "a2", 7));
        Assertions.assertThat(((Wide3) other).told).isEqualTo("newAll:a1,a2|[7, 7]");
    }

    @Test
    public void absForms() throws Throwable {
        Object abs = ArityForms.create("Abs3", List.of("a1", "a2", "a3"));
        Assertions.assertThat(((Abstract3) abs).told).isEqualTo("abs:a1,a2,a3");
        Object other = ArityForms.create("Abs3", List.of("a1", "a2", 7));
        Assertions.assertThat(((Abstract3) other).told).isEqualTo("absAll:a1,a2|[7, 7]");
    }
}
