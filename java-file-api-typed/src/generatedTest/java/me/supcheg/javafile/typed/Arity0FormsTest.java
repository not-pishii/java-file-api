package me.supcheg.javafile.typed;

import java.lang.constant.ClassDesc;
import java.util.List;
import javax.annotation.processing.Generated;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.Arity0FormsTest.AbsSpec;
import me.supcheg.javafile.typed.Arity0FormsTest.PlainSpec;
import me.supcheg.javafile.typed.Arity0FormsTest.SubSpec;
import me.supcheg.javafile.typed.TypedJavaFile.TypedClassHeaderSpec;
import me.supcheg.javafile.typed.fixtures.Abstract0;
import me.supcheg.javafile.typed.fixtures.Make0;
import me.supcheg.javafile.typed.fixtures.Wide0;
import me.supcheg.javafile.typed.fixtures.Widening0;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Abstract0_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Do0_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Fn0_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Make0_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide0_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Widening0_;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

/**
 * The forms of the typed layer of arity 0: a class that extends `Object`, one that extends a class and one that extends an abstract class, declared with them, are compiled with the classes of the other arities in one run of javac, and run.
 */
@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
@Execution(ExecutionMode.SAME_THREAD)
class Arity0FormsTest {
    static final class PlainSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var becoming = h.becoming(Widening0_.TOKEN);
            var inhO = h.overriding(Widening0_.inh);
            var inhVoidO = h.overriding(Widening0_.inhVoid);
            var narrowO = h.overriding(Widening0_.narrow, String_.TOKEN);
            var plainO = h.overriding(Widening0_.plain);
            var selfO = h.overriding(Widening0_.self, becoming);
            var takeO = h.overriding(Widening0_.take);
            var oh = h.extendsObject();
            var asWidening = oh.implements_(Widening0_.implement(h.self()).inh(inhO).inhVoid(inhVoidO).narrow(narrowO).plain(plainO).self(selfO).take(takeO));
            var cb = oh.members();
            var givenM = cb.declareOverride(asWidening, Widening0_.given);
            var chosenM = cb.declareOverride(asWidening, Widening0_.chosen, String_.TOKEN);
            var chainedM = cb.declareOverride(asWidening, Widening0_.chained, asWidening);
            var heldM = cb.declareOverride(asWidening, Widening0_.held);
            var ctorM = cb.declareConstructor();
            var applyM = cb.declareMethod("apply", String_.TOKEN);
            var runM = cb.declareVoidMethod("run");
            var makeM = cb.declareStaticMethod("make", String_.TOKEN);
            var logM = cb.declareVoidStaticMethod("log");
            var lamM = cb.declareMethod("lam", String_.TOKEN);
            var viaCtorM = cb.declareStaticMethod("viaCtor", Make0_.TOKEN);
            var bodies = cb.bodies();
            bodies.define(inhO.method(), (b, self) -> b.return_(Expressions.literal("inh")));
            bodies.define(inhVoidO.method(), (b, self) -> b.exec(Expressions.voidStaticCall(Wide0_.note)).end());
            bodies.define(narrowO.method(), (b, self) -> b.return_(Expressions.literal("narrow")));
            bodies.define(plainO.method(), (b, self) -> b.return_(Expressions.literal("plain")));
            bodies.define(selfO.method(), (b, self) -> b.return_(self));
            bodies.define(takeO.method(), (b, self) -> b.exec(Expressions.voidStaticCall(Wide0_.note)).end());
            bodies.define(givenM, (b, self) -> b.return_(Expressions.literal("given")));
            bodies.define(chosenM, (b, self) -> b.return_(Expressions.literal("chosen")));
            bodies.define(chainedM, (b, self) -> b.return_(self));
            bodies.define(heldM, (b, self) -> b.exec(Expressions.voidStaticCall(Wide0_.note)).end());
            bodies.define(ctorM, (b, self) -> b.exec(Expressions.voidStaticCall(Wide0_.note)).end());
            bodies.define(applyM, (b, self) -> b.let(Wide0_.TOKEN, Expressions.new_(Wide0_.new_), (w) -> b.exec(w.voidCall(Wide0_.put)).exec(Expressions.voidStaticCall(Wide0_.note)).return_(Expressions.concat(Expressions.concat(Expressions.concat(Expressions.concat(w.call(Wide0_.pick), Expressions.literal("/")), Expressions.staticCall(Wide0_.join)), Expressions.literal("/")), w.field(Wide0_.told)))));
            bodies.define(runM, (b, self) -> b.exec(Expressions.voidStaticCall(Wide0_.note)).end());
            bodies.define(makeM, (b) -> b.return_(Expressions.staticCall(Wide0_.join)));
            bodies.define(logM, (b) -> b.exec(Expressions.voidStaticCall(Wide0_.note)).end());
            bodies.define(lamM, (b, self) -> b.let(Fn0_.TOKEN, Expressions.lambda(Fn0_.sam, () -> Expressions.literal("fn")), (f) -> b.let(Fn0_.TOKEN, Expressions.lambdaBlock(Fn0_.sam, (lb) -> lb.return_(Expressions.literal("blk"))), (g) -> b.let(Do0_.TOKEN, Expressions.lambda(Do0_.sam, () -> Expressions.voidStaticCall(Wide0_.note)), (d) -> b.let(Do0_.TOKEN, Expressions.lambdaBlock(Do0_.sam, (lb) -> lb.exec(Expressions.voidStaticCall(Wide0_.note)).end()), (e) -> b.exec(d.voidCall(Do0_.accept)).exec(e.voidCall(Do0_.accept)).return_(Expressions.concat(Expressions.concat(f.call(Fn0_.apply), Expressions.literal("/")), g.call(Fn0_.apply))))))));
            bodies.define(viaCtorM, (b) -> b.return_(Expressions.constructorRef(Wide0_.new_, Make0_.TOKEN, Make0_.sam.method())));
            return bodies;
        }
    }

    static final class SubSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Wide0_.extend(h.self()));
            var becoming = sh.becoming(Widening0_.TOKEN);
            var inhI = sh.inherited(Wide0_.inh);
            var inhVoidI = sh.inherited(Wide0_.inhVoid);
            var narrowO = sh.overriding(Widening0_.narrow, String_.TOKEN);
            var plainO = sh.overriding(Widening0_.plain);
            var selfO = sh.overriding(Widening0_.self, becoming);
            var takeO = sh.overriding(Widening0_.take);
            var asWidening = sh.implements_(Widening0_.implement(h.self()).inh(inhI).inhVoid(inhVoidI).narrow(narrowO).plain(plainO).self(selfO).take(takeO));
            var cb = sh.members();
            var asWide = cb.asSuper();
            var ctorA = cb.declareConstructor();
            var pickM = cb.declareOverride(Wide0_.pick);
            var csM = cb.declareOverride(Wide0_.cs, String_.TOKEN);
            var sameM = cb.declareOverride(Wide0_.same, asWide);
            var putM = cb.declareOverride(Wide0_.put);
            var bodies = cb.bodies(ctorA, (c) -> c.super_(Wide0_.new_, (b, self) -> b.end()));
            bodies.define(narrowO.method(), (b, self) -> b.return_(Expressions.literal("narrow")));
            bodies.define(plainO.method(), (b, self) -> b.return_(Expressions.literal("plain")));
            bodies.define(selfO.method(), (b, self) -> b.return_(self));
            bodies.define(takeO.method(), (b, self) -> b.end());
            bodies.define(pickM, (b, self) -> b.return_(Expressions.concat(Expressions.literal("sub:"), bodies.superCall(self, Wide0_.pick))));
            bodies.define(csM, (b, self) -> b.return_(Expressions.literal("cs")));
            bodies.define(sameM, (b, self) -> b.return_(self));
            bodies.define(putM, (b, self) -> b.exec(bodies.superCall(self, Wide0_.put)).end());
            return bodies;
        }
    }

    static final class AbsSpec implements TypedClassHeaderSpec {
        @Override
        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
            var sh = h.extends_(Abstract0_.extend(h.self()));
            var cb = sh.members();
            var ctorA = cb.declareConstructor();
            var bodies = cb.bodies(ctorA, (c) -> c.super_(Abstract0_.super_, (b, self) -> b.end()));
            return bodies;
        }
    }

    static JavaFile plain() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Plain0"), new PlainSpec());
    }

    static JavaFile sub() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Sub0"), new SubSpec());
    }

    static JavaFile abs() {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), ClassDesc.of("me.supcheg.example.arity", "Abs0"), new AbsSpec());
    }

    @Test
    public void plainForms() throws Throwable {
        Wide0.noted = "";
        Object plain = ArityForms.create("Plain0", List.of());
        Assertions.assertThat(Wide0.noted).isEqualTo("note:;");
        Wide0.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain0", plain, "apply", List.of())).isEqualTo("pick:/join:/new:put:;");
        Assertions.assertThat(Wide0.noted).isEqualTo("note:;");
        Wide0.noted = "";
        ArityForms.invoke("Plain0", plain, "run", List.of());
        Assertions.assertThat(Wide0.noted).isEqualTo("note:;");
        Wide0.noted = "";
        ArityForms.invoke("Plain0", null, "log", List.of());
        Assertions.assertThat(Wide0.noted).isEqualTo("note:;");
        Assertions.assertThat(ArityForms.invoke("Plain0", null, "make", List.of())).isEqualTo("join:");
        Wide0.noted = "";
        Assertions.assertThat(ArityForms.invoke("Plain0", plain, "lam", List.of())).isEqualTo("fn/blk");
        Assertions.assertThat(Wide0.noted).isEqualTo("note:;note:;");
        Make0 make = (Make0) ArityForms.invoke("Plain0", null, "viaCtor", List.of());
        Assertions.assertThat(make.make().told).isEqualTo("new:");
        Widening0 widening = (Widening0) plain;
        Assertions.assertThat(widening.inh()).isEqualTo("inh");
        Assertions.assertThat(widening.narrow()).isEqualTo("narrow");
        Assertions.assertThat(widening.plain()).isEqualTo("plain");
        Assertions.assertThat(widening.self()).isSameAs(plain);
        Assertions.assertThat(widening.given()).isEqualTo("given");
        Assertions.assertThat(widening.chosen()).isEqualTo("chosen");
        Assertions.assertThat(widening.chained()).isSameAs(plain);
        Wide0.noted = "";
        widening.inhVoid();
        widening.take();
        widening.held();
        Assertions.assertThat(Wide0.noted).isEqualTo("note:;note:;note:;");
    }

    @Test
    public void subForms() throws Throwable {
        Object sub = ArityForms.create("Sub0", List.of());
        Wide0 wide = (Wide0) sub;
        Assertions.assertThat(wide.told).isEqualTo("new:");
        Assertions.assertThat(wide.pick()).isEqualTo("sub:pick:");
        Assertions.assertThat(wide.cs()).isEqualTo("cs");
        Assertions.assertThat(wide.same()).isSameAs(sub);
        wide.told = "";
        wide.put();
        Assertions.assertThat(wide.told).isEqualTo("put:;");
        Widening0 widening = (Widening0) sub;
        Assertions.assertThat(widening.inh()).isEqualTo("inh:");
        wide.told = "";
        widening.inhVoid();
        Assertions.assertThat(wide.told).isEqualTo("inhVoid:;");
        Assertions.assertThat(widening.narrow()).isEqualTo("narrow");
        Assertions.assertThat(widening.plain()).isEqualTo("plain");
        Assertions.assertThat(widening.self()).isSameAs(sub);
    }

    @Test
    public void absForms() throws Throwable {
        Object abs = ArityForms.create("Abs0", List.of());
        Assertions.assertThat(((Abstract0) abs).told).isEqualTo("abs:");
    }
}
