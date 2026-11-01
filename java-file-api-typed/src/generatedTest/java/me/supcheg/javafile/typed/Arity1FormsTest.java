package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.Arity1FormsTest.AbsSpec;
import me.supcheg.javafile.typed.Arity1FormsTest.PlainSpec;
import me.supcheg.javafile.typed.Arity1FormsTest.SubSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedClassHeaderSpec;
import me.supcheg.javafile.typed.fixtures.Abstract1;
import me.supcheg.javafile.typed.fixtures.Make1;
import me.supcheg.javafile.typed.fixtures.Wide1;
import me.supcheg.javafile.typed.fixtures.Widening1;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Abstract1_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Do1_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Fn1_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Make1_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide1_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Widening1_;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * The forms of the typed layer of arity 1: a class that extends `Object`, one that extends a class and one that extends an abstract class, declared with them, are compiled with the classes of the other arities in one run of javac, and run.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
@Execution(ExecutionMode.SAME_THREAD)
class Arity1FormsTest {
    static final class PlainSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var becoming = h.becoming(Widening1_.TOKEN);
            var inhO = h.overriding(Widening1_.inh_String);
            var inhVoidO = h.overriding(Widening1_.inhVoid_String);
            var narrowO = h.overriding(Widening1_.narrow_String, String_.TOKEN);
            var plainO = h.overriding(Widening1_.plain_String);
            var selfO = h.overriding(Widening1_.self_String, becoming);
            var takeO = h.overriding(Widening1_.take_String);
            var oh = h.extendsObject();
            var asWidening = oh.implements_(Widening1_.implement(h.self()).inhVoid_String(inhVoidO).inh_String(inhO).narrow_String(narrowO).plain_String(plainO).self_String(selfO).take_String(takeO));
            var cb = oh.members();
            var givenM = cb.declareOverride(asWidening, Widening1_.given_String);
            var chosenM = cb.declareOverride(asWidening, Widening1_.chosen_String, String_.TOKEN);
            var chainedM = cb.declareOverride(asWidening, Widening1_.chained_String, asWidening);
            var heldM = cb.declareOverride(asWidening, Widening1_.held_String);
            var ctorM = cb.declareConstructor(String_.TOKEN);
            var applyM = cb.declareMethod("apply", String_.TOKEN, String_.TOKEN);
            var runM = cb.declareVoidMethod("run", String_.TOKEN);
            var makeM = cb.declareStaticMethod("make", String_.TOKEN, String_.TOKEN);
            var logM = cb.declareVoidStaticMethod("log", String_.TOKEN);
            var lamM = cb.declareMethod("lam", String_.TOKEN, String_.TOKEN);
            var viaCtorM = cb.declareStaticMethod("viaCtor", Make1_.TOKEN);
            var vctorM = cb.variableArity().declareConstructor(ArrayToken.of(String_.TOKEN));
            var vapplyM = cb.variableArity().declareMethod("applyAll", String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vrunM = cb.variableArity().declareVoidMethod("runAll", ArrayToken.of(String_.TOKEN));
            var vmakeM = cb.variableArity().declareStaticMethod("makeAll", String_.TOKEN, ArrayToken.of(String_.TOKEN));
            var vlogM = cb.variableArity().declareVoidStaticMethod("logAll", ArrayToken.of(String_.TOKEN));
            var bodies = cb.bodies();
            bodies.define(inhO.method(), (b, self, p1) -> b.return_(Expressions.concat(Expressions.literal("inh:"), p1)));
            bodies.define(inhVoidO.method(), (b, self, p1) -> b.exec(Expressions.voidStaticCall(Wide1_.note_String, p1)).end());
            bodies.define(narrowO.method(), (b, self, p1) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p1)));
            bodies.define(plainO.method(), (b, self, p1) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p1)));
            bodies.define(selfO.method(), (b, self, p1) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1) -> b.exec(Expressions.voidStaticCall(Wide1_.note_String, p1)).end());
            bodies.define(givenM, (b, self, p1) -> b.return_(Expressions.concat(Expressions.literal("given:"), p1)));
            bodies.define(chosenM, (b, self, p1) -> b.return_(Expressions.concat(Expressions.literal("chosen:"), p1)));
            bodies.define(chainedM, (b, self, p1) -> b.return_(self));
            bodies.define(heldM, (b, self, p1) -> b.exec(Expressions.voidStaticCall(Wide1_.note_String, p1)).end());
            bodies.define(ctorM, (b, self, p1) -> b.exec(Expressions.voidStaticCall(Wide1_.note_String, p1)).end());
            bodies.define(applyM, (b, self, p1) -> b.let(Wide1_.TOKEN, Expressions.new_(Wide1_.new_String, p1), (w) -> b.exec(w.voidCall(Wide1_.put_String, p1)).exec(Expressions.voidStaticCall(Wide1_.note_String, p1)).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide1_.pick_String, p1), Expressions.literal("/")), Expressions.staticCall(Wide1_.join_String, p1)), Expressions.literal("/")), w.field(Wide1_.told)))));
            bodies.define(runM, (b, self, p1) -> b.exec(Expressions.voidStaticCall(Wide1_.note_String, p1)).end());
            bodies.define(makeM, (b, p1) -> b.return_(Expressions.staticCall(Wide1_.join_String, p1)));
            bodies.define(logM, (b, p1) -> b.exec(Expressions.voidStaticCall(Wide1_.note_String, p1)).end());
            bodies.define(lamM, (b, self, p1) -> b.let(Fn1_.TOKEN, Expressions.lambda(Fn1_.sam, (q1) -> Expressions.concat(Expressions.literal("fn:"), q1)), (f) -> b.let(Fn1_.TOKEN, Expressions.lambdaBlock(Fn1_.sam, (lb, q1) -> lb.return_(Expressions.concat(Expressions.literal("blk:"), q1))), (g) -> b.let(Do1_.TOKEN, Expressions.lambda(Do1_.sam, (q1) -> Expressions.voidStaticCall(Wide1_.note_String, q1)), (d) -> b.let(Do1_.TOKEN, Expressions.lambdaBlock(Do1_.sam, (lb, q1) -> lb.exec(Expressions.voidStaticCall(Wide1_.note_String, q1)).end()), (e) -> b.exec(d.voidCall(Do1_.accept_String, p1)).exec(e.voidCall(Do1_.accept_String, p1)).return_(Expressions.concat(Expressions.concat(f.call(Fn1_.apply_String, p1), Expressions.literal("/")), g.call(Fn1_.apply_String, p1))))))));
            bodies.define(viaCtorM, (b) -> b.return_(Expressions.constructorRef(Wide1_.new_String, Make1_.TOKEN, Make1_.sam.method())));
            bodies.define(vctorM, (b, self, rest) -> b.exec(Expressions.voidStaticCall(Wide1_.noteAll_StringArray, rest)).end());
            bodies.define(vapplyM, (b, self, rest) -> b.let(Wide1_.TOKEN, Expressions.new_(Wide1_.new_IntegerArray, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), Expressions.box(PrimitiveToken.INT, Expressions.literal(1)), Expressions.box(PrimitiveToken.INT, Expressions.literal(2)))), (w) -> b.exec(w.voidCall(Wide1_.putAll_StringArray, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).exec(Expressions.voidStaticCall(Wide1_.noteAll_StringArray, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide1_.pickAll_StringArray, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y"))), Expressions.literal("/")), Expressions.staticCall(Wide1_.joinAll_StringArray, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))), Expressions.literal("/")), w.field(Wide1_.told)))));
            bodies.define(vrunM, (b, self, rest) -> b.exec(Expressions.voidStaticCall(Wide1_.noteAll_StringArray, rest)).end());
            bodies.define(vmakeM, (b, rest) -> b.return_(Expressions.staticCall(Wide1_.joinAll_StringArray, rest)));
            bodies.define(vlogM, (b, rest) -> b.exec(Expressions.voidStaticCall(Wide1_.noteAll_StringArray, rest)).end());
            return bodies;
        }
    }

    static final class SubSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Wide1_.extend(h.self()));
            var becoming = sh.becoming(Widening1_.TOKEN);
            var inhI = sh.inherited(Wide1_.inh_String);
            var inhVoidI = sh.inherited(Wide1_.inhVoid_String);
            var narrowO = sh.overriding(Widening1_.narrow_String, String_.TOKEN);
            var plainO = sh.overriding(Widening1_.plain_String);
            var selfO = sh.overriding(Widening1_.self_String, becoming);
            var takeO = sh.overriding(Widening1_.take_String);
            var asWidening = sh.implements_(Widening1_.implement(h.self()).inhVoid_String(inhVoidI).inh_String(inhI).narrow_String(narrowO).plain_String(plainO).self_String(selfO).take_String(takeO));
            var cb = sh.members();
            var asWide = cb.asSuper();
            var ctorA = cb.declareConstructor(String_.TOKEN);
            var ctorB = cb.declareConstructor(Integer_.TOKEN);
            var pickM = cb.declareOverride(Wide1_.pick_String);
            var csM = cb.declareOverride(Wide1_.cs_String, String_.TOKEN);
            var sameM = cb.declareOverride(Wide1_.same_String, asWide);
            var putM = cb.declareOverride(Wide1_.put_String);
            var viaAllM = cb.declareMethod("viaAll", String_.TOKEN);
            var viaAllVoidM = cb.declareVoidMethod("viaAllVoid");
            var bodies = cb.bodies(ctorA, (c, p1) -> c.super_(Wide1_.new_String, p1, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1) -> c.super_(Wide1_.new_IntegerArray, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p1, p1), (b, self) -> b.end()));
            bodies.define(narrowO.method(), (b, self, p1) -> b.return_(Expressions.concat(Expressions.literal("narrow:"), p1)));
            bodies.define(plainO.method(), (b, self, p1) -> b.return_(Expressions.concat(Expressions.literal("plain:"), p1)));
            bodies.define(selfO.method(), (b, self, p1) -> b.return_(self));
            bodies.define(takeO.method(), (b, self, p1) -> b.end());
            bodies.define(pickM, (b, self, p1) -> b.return_(Expressions.concat(Expressions.literal("sub:"), bodies.superCall(self, Wide1_.pick_String, p1))));
            bodies.define(csM, (b, self, p1) -> b.return_(Expressions.concat(Expressions.literal("cs:"), p1)));
            bodies.define(sameM, (b, self, p1) -> b.return_(self));
            bodies.define(putM, (b, self, p1) -> b.exec(bodies.superCall(self, Wide1_.put_String, p1)).end());
            bodies.define(viaAllM, (b, self) -> b.return_(bodies.superCall(self, Wide1_.pickAll_StringArray, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))));
            bodies.define(viaAllVoidM, (b, self) -> b.exec(bodies.superCall(self, Wide1_.putAll_StringArray, Expressions.varargs(ArrayToken.of(String_.TOKEN), Expressions.literal("x"), Expressions.literal("y")))).end());
            return bodies;
        }
    }

    static final class AbsSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Abstract1_.extend(h.self()));
            var cb = sh.members();
            var ctorA = cb.declareConstructor(String_.TOKEN);
            var ctorB = cb.declareConstructor(Integer_.TOKEN);
            var bodies = cb.bodies(ctorA, (c, p1) -> c.super_(Abstract1_.super_String, p1, (b, self) -> b.end()));
            bodies.define(ctorB, (c, p1) -> c.super_(Abstract1_.super_IntegerArray, Expressions.varargs(ArrayToken.of(Integer_.TOKEN), p1, p1), (b, self) -> b.end()));
            return bodies;
        }
    }

    static JavaFile plain() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Plain1"), new PlainSpec());
    }

    static JavaFile sub() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Sub1"), new SubSpec());
    }

    static JavaFile abs() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Abs1"), new AbsSpec());
    }

    @Test
    public void plainForms() throws Throwable {
        Wide1.noted = "";
        Object plain = ArityForms.create("Plain1", List.of("a1"));
        Assertions.assertThat(Wide1.noted).isEqualTo("note:a1;");
        Wide1.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain1", plain, "apply", List.of("a1"))).isEqualTo("pick:a1/join:a1/new:a1put:a1;");
        Assertions.assertThat(Wide1.noted).isEqualTo("note:a1;");
        Wide1.noted = "";
        ArityForms.invoke("Plain1", plain, "run", List.of("a1"));
        Assertions.assertThat(Wide1.noted).isEqualTo("note:a1;");
        Wide1.noted = "";
        ArityForms.invoke("Plain1", null, "log", List.of("a1"));
        Assertions.assertThat(Wide1.noted).isEqualTo("note:a1;");
        Assertions.assertThat(ArityForms.invoke("Plain1", null, "make", List.of("a1"))).isEqualTo("join:a1");
        Wide1.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain1", plain, "lam", List.of("a1"))).isEqualTo("fn:a1/blk:a1");
        Assertions.assertThat(Wide1.noted).isEqualTo("note:a1;note:a1;");
        Make1 make = (Make1) ArityForms.invoke("Plain1", null, "viaCtor", List.of());
        Assertions.assertThat(make.make("a1").told).isEqualTo("new:a1");
        Widening1 widening = (Widening1) plain;
        Assertions.assertThat(widening.inh("a1")).isEqualTo("inh:a1");
        Assertions.assertThat(widening.narrow("a1")).isEqualTo("narrow:a1");
        Assertions.assertThat(widening.plain("a1")).isEqualTo("plain:a1");
        Assertions.assertThat(widening.self("a1")).isSameAs(plain);
        Assertions.assertThat(widening.given("a1")).isEqualTo("given:a1");
        Assertions.assertThat(widening.chosen("a1")).isEqualTo("chosen:a1");
        Assertions.assertThat(widening.chained("a1")).isSameAs(plain);
        Wide1.noted = "";
        widening.inhVoid("a1");
        widening.take("a1");
        widening.held("a1");
        Assertions.assertThat(Wide1.noted).isEqualTo("note:a1;note:a1;note:a1;");
        Wide1.noted = "";
        Object variable = ArityForms.create("Plain1", List.of((Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide1.noted).isEqualTo("noteAll:|[r0, r1];");
        Wide1.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain1", variable, "applyAll", List.of((Object) new String[] {"r0", "r1"}))).isEqualTo("pickAll:|[x, y]/joinAll:|[x, y]/newAll:|[1, 2]putAll:|[x, y];");
        Assertions.assertThat(Wide1.noted).isEqualTo("noteAll:|[x, y];");
        Wide1.noted = "";
        ArityForms.invoke("Plain1", variable, "runAll", List.of((Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide1.noted).isEqualTo("noteAll:|[r0, r1];");
        Wide1.noted = "";
        ArityForms.invoke("Plain1", null, "logAll", List.of((Object) new String[] {"r0", "r1"}));
        Assertions.assertThat(Wide1.noted).isEqualTo("noteAll:|[r0, r1];");
        Assertions.assertThat(ArityForms.invoke("Plain1", null, "makeAll", List.of((Object) new String[] {"r0", "r1"}))).isEqualTo("joinAll:|[r0, r1]");
    }

    @Test
    public void subForms() throws Throwable {
        Object sub = ArityForms.create("Sub1", List.of("a1"));
        Wide1 wide = (Wide1) sub;
        Assertions.assertThat(wide.told).isEqualTo("new:a1");
        Assertions.assertThat(wide.pick("a1")).isEqualTo("sub:pick:a1");
        Assertions.assertThat(wide.cs("a1")).isEqualTo("cs:a1");
        Assertions.assertThat(wide.same("a1")).isSameAs(sub);
        wide.told = "";
        wide.put("a1");
        Assertions.assertThat(wide.told).isEqualTo("put:a1;");
        Widening1 widening = (Widening1) sub;
        Assertions.assertThat(widening.inh("a1")).isEqualTo("inh:a1");
        wide.told = "";
        widening.inhVoid("a1");
        Assertions.assertThat(wide.told).isEqualTo("inhVoid:a1;");
        Assertions.assertThat(widening.narrow("a1")).isEqualTo("narrow:a1");
        Assertions.assertThat(widening.plain("a1")).isEqualTo("plain:a1");
        Assertions.assertThat(widening.self("a1")).isSameAs(sub);
        wide.told = "";
        ArityForms.invoke("Sub1", sub, "viaAllVoid", List.of());
        Assertions.assertThat(wide.told).isEqualTo("putAll:|[x, y];");
        Assertions.assertThat(ArityForms.invoke("Sub1", sub, "viaAll", List.of())).isEqualTo("pickAll:|[x, y]");
        Object other = ArityForms.create("Sub1", List.of(7));
        Assertions.assertThat(((Wide1) other).told).isEqualTo("newAll:|[7, 7]");
    }

    @Test
    public void absForms() throws Throwable {
        Object abs = ArityForms.create("Abs1", List.of("a1"));
        Assertions.assertThat(((Abstract1) abs).told).isEqualTo("abs:a1");
        Object other = ArityForms.create("Abs1", List.of(7));
        Assertions.assertThat(((Abstract1) other).told).isEqualTo("absAll:|[7, 7]");
    }
}
