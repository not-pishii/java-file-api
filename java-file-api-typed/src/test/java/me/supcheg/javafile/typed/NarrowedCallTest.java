package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Origin;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.RuntimeException_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Chained_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Linked_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Maker_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Origin_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Picked_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.addInt;
import static me.supcheg.javafile.typed.Expressions.assign;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.castChecked;
import static me.supcheg.javafile.typed.Expressions.cond;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.JavacVerdict.classUnlikeJavac;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// A call javac types narrower than its fact (§8f) — `this.get()` of a class
/// that has `String get()` for `Object get()` of [Origin], called by the fact
/// of the interface — in the positions where the static type decides what
/// javac takes or does: each is compiled under `-Xlint:all` and run. With
/// them, what the class itself being the result asks of the order of its
/// header, and a `protected` call as the value of a `throw`.
class NarrowedCallTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Test
    void theTypeOfTheFactIsKeptWhereItDecides() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var get = h.overriding(Origin_.get, String_.TOKEN);
                var oh = h.extendsObject();
                Upcast<Self, Origin> asOrigin =
                        oh.implements_(Origin_.implement(h.self()).get(get));
                var cb = oh.members();
                var objects = new List_<>(Object_.TOKEN).token;
                var each = cb.declareOverride(asOrigin, Origin_.each, objects);
                var whichObject = cb.declareMethod("which", String_.TOKEN, Object_.TOKEN);
                var which = cb.declareMethod("which", String_.TOKEN, String_.TOKEN);
                // (Integer) of a String does not compile, of an Object fails when run
                var cast = cb.declareMethod("cast", Integer_.TOKEN);
                // instanceof Integer of a String does not compile
                var tested = cb.declareMethod("tested", String_.TOKEN);
                // a conditional of two Strings is a String, and which(String) its overload
                var chosen = cb.declareMethod("chosen", String_.TOKEN, PrimitiveToken.BOOLEAN);
                // new Picked(String) beside new Picked(Object)
                var made = cb.declareMethod("made", String_.TOKEN);
                // the iterated expression is of whatever Iterable the class has it for
                var counted = cb.declareMethod("counted", PrimitiveToken.INT);
                var bodies = cb.bodies();
                bodies.define(get.method(), (b, self) -> b.return_(literal("mine")));
                bodies.define(each, (b, self) -> b.return_(literalNull(objects)));
                bodies.define(whichObject, (b, self, v) -> b.return_(literal("object")));
                bodies.define(which, (b, self, v) -> b.return_(literal("string")));
                bodies.define(
                        cast,
                        (b, self) -> b.return_(castChecked(Integer_.TOKEN, call(asOrigin.apply(self), Origin_.get))));
                bodies.define(
                        tested,
                        (b, self) -> b.ifInstanceOfElse(
                                call(asOrigin.apply(self), Origin_.get),
                                Integer_.TOKEN,
                                (t, _) -> t.return_(literal("integer")),
                                e -> e.return_(literal("other"))));
                bodies.define(
                        chosen,
                        (b, self, flag) -> b.return_(call(
                                self,
                                whichObject,
                                cond(
                                        flag,
                                        call(asOrigin.apply(self), Origin_.get),
                                        call(asOrigin.apply(self), Origin_.get),
                                        Object_.TOKEN))));
                bodies.define(
                        made,
                        (b, self) -> b.return_(field(
                                new_(Picked_.new_Object, call(asOrigin.apply(self), Origin_.get)), Picked_.which)));
                bodies.define(
                        counted,
                        (b, self) -> b.letVar(
                                PrimitiveToken.INT,
                                literal(0),
                                n -> b.forEach(
                                                Object_.TOKEN,
                                                call(asOrigin.apply(self), Origin_.each),
                                                (loop, _, _) -> loop.exec(assign(n, addInt(n, literal(1)))))
                                        .return_(n)));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("return (Integer) ((Origin) this).get();")
                .contains("if (((Origin) this).get() instanceof Integer v0) {")
                .contains("return this.which(v0 ? ((Origin) this).get() : ((Origin) this).get());")
                .contains("new Picked(((Origin) this).get()).which;")
                .contains("for (Object v1 : this.each()) {");
        assertThatExceptionOfType(ClassCastException.class).isThrownBy(() -> compiled.invoke("cast"));
        assertThat(compiled.invoke("tested")).isEqualTo("other");
        assertThat(compiled.invoke("chosen", true)).isEqualTo("object");
        assertThat(compiled.invoke("made")).isEqualTo("object");
    }

    @Test
    void aProtectedCallIsNoValueOfAThrowUntilItIsBound() throws Throwable {
        // stricter than javac: the value of a throw is of exactly its token, which a receiver of the
        // type of the owner alone makes a protected call, and that the class has no access through
        classUnlikeJavac(
                IllegalStateException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var sh = h.extends_(Maker_.extend(h.self()));
                        var cb = sh.members();
                        var constructor = cb.declareConstructor();
                        var fault = cb.protected_(Maker_.fault);
                        var f = cb.declareMethod("f", String_.TOKEN);
                        var bodies = cb.bodies(constructor, ctor -> ctor.super_(Maker_.new_, (b, self) -> b.end()));
                        bodies.define(f, (b, self) -> b.throw_(call(cb.asSuper().apply(self), fault)));
                        return bodies;
                    }
                }),
                "bind the result to a variable with let first",
                "extends Maker",
                "public String f() { throw this.fault(); }");

        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var sh = h.extends_(Maker_.extend(h.self()));
                var cb = sh.members();
                var constructor = cb.declareConstructor();
                var fault = cb.protected_(Maker_.fault);
                var f = cb.declareMethod("f", String_.TOKEN);
                var bodies = cb.bodies(constructor, ctor -> ctor.super_(Maker_.new_, (b, self) -> b.end()));
                bodies.define(
                        f,
                        (b, self) -> b.let(
                                RuntimeException_.TOKEN, call(cb.asSuper().apply(self), fault), e -> b.throw_(e)));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("RuntimeException v0 = this.fault();")
                .contains("throw v0;");
        assertThatIllegalStateException().isThrownBy(() -> compiled.invoke("f")).withMessage("maker");
    }

    @Test
    void theClassIsTheResultOnceTheSupertypeThatMakesItOneIsNamed() {
        // Object next() of Linked, answered by Probe next(); Linked next() of Chained has a body
        String source = file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        var next = h.overriding(Linked_.next, h.self());
                        var oh = h.extendsObject();
                        oh.implements_(Linked_.implement(h.self()).next(next));
                        oh.implements_(Chained_.implement(h.self()));
                        var cb = oh.members();
                        var bodies = cb.bodies();
                        bodies.define(next.method(), (b, self) -> b.return_(self));
                        return bodies;
                    }
                })
                .render();

        assertThat(source)
                .contains("public final class Probe implements Linked, Chained {")
                .contains("@Override\n    public Probe next() {");
        com.google.testing.compile.CompilationSubject.assertThat(com.google.testing.compile.Compiler.javac()
                        .withOptions("-Xlint:all")
                        .compile(com.google.testing.compile.JavaFileObjects.forSourceString(
                                "me.supcheg.example.Probe", source)))
                .succeededWithoutWarnings();

        // stricter than javac: named before Linked, Chained asks for a Linked the class is not yet
        classUnlikeJavac(
                IllegalArgumentException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.overriding(Linked_.next, h.self());
                        var oh = h.extendsObject();
                        oh.implements_(Chained_.implement(h.self()));
                        return Unreached.here();
                    }
                }),
                "cannot override next() of me.supcheg.javafile.typed.fixtures.Chained: its result is"
                        + " me.supcheg.javafile.typed.fixtures.Linked, and that of the method"
                        + " me.supcheg.example.Probe: the return types are incompatible (JLS 8.4.8.3) — the result"
                        + " of an override is that of the method or a subtype of it the typed layer can prove one,"
                        + " and no supertype the class has named so far is known to be one — a supertype that makes"
                        + " the class of it is named before the one that asks for it, or said first by"
                        + " becoming(token)",
                "implements Chained, Linked",
                "@Override public Probe next() { return this; }");
    }
}
