package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Carrying;
import me.supcheg.javafile.typed.fixtures.Labelled;
import me.supcheg.javafile.typed.fixtures.Op;
import me.supcheg.javafile.typed.testfacts.java.lang.CharSequence_;
import me.supcheg.javafile.typed.testfacts.java.lang.Number_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Consumer_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.BaseTwin_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Carrier_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Carrying_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Labelled_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Op_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.function.Consumer;

import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.switch_;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;

/// What a generic class has from its supertypes in terms of its own type
/// parameters (§8g): a method had by inheritance, a `protected` one reached
/// on the class itself, an override whose parameter or result is a type
/// parameter, a `switch` over a value of one. The classes are rendered,
/// compiled under `-Xlint:all` and run.
class GenericInheritanceTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Box");

    @Test
    void aMethodOfAnInterfaceIsHadFromAGenericSuperclassByInheritance() throws Throwable {
        // T carried() of Carrier<T> for T carried() of Carrying<T>
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), DESC, typeParam("T"), new TypedJavaFile.TypedGenericClassSpec1<>() {
                    @Override
                    public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                        var carrier = new Carrier_<>(t);
                        var carrying = new Carrying_<>(t);
                        var sh = h.extends_(carrier.extend(h.self()));
                        Upcast<Self, Carrying<T>> asCarrying =
                                sh.implements_(carrying.implement(h.self()).carried(sh.inherited(carrier.carried)));
                        var first = sh.declareConstructor(t);
                        var cb = sh.members();
                        var member1 = cb.declareMethod("go", t);
                        var bodies = cb.bodies(first, (ctor, v) -> ctor.super_(carrier.new_T, v, (b, self) -> b.end()));
                        bodies.define(member1, (b, self) -> b.return_(call(asCarrying.apply(self), carrying.carried)));
                    }
                }));

        assertThat(compiled.source())
                .contains("public final class Box<T> extends Carrier<T> implements Carrying<T> {")
                .doesNotContain("public T carried()");
        Object box = compiled.generated().getConstructor(Object.class).newInstance("carried");
        assertThat(((Carrying<?>) box).carried()).isEqualTo("carried");
        assertThat(compiled.generated().getMethod("go").invoke(box)).isEqualTo("carried");
    }

    @Test
    void aProtectedMethodAndAPublicOneOfAGenericSuperclassAreCalledOnTheClassItself() throws Throwable {
        // protected String p(T) and String m(T) of BaseTwin<T>
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), DESC, typeParam("T"), new TypedJavaFile.TypedGenericClassSpec1<>() {
                    @Override
                    public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                        var base = new BaseTwin_<>(t);
                        var sh = h.extends_(base.extend(h.self()));
                        var first = sh.declareConstructor();
                        var cb = sh.members();
                        var p = cb.protected_(base.p_T);
                        var member2 = cb.declareMethod("go", String_.TOKEN, t);
                        var member3 = cb.declareMethod("pub", String_.TOKEN, t);
                        var bodies = cb.bodies(first, ctor -> ctor.super_(base.new_, (b, self) -> b.end()));
                        bodies.define(
                                member2,
                                (b, self, v) -> b.return_(call(cb.asSuper().apply(self), p, v)));
                        bodies.define(
                                member3,
                                (b, self, v) -> b.return_(call(cb.asSuper().apply(self), base.m_T, v)));
                    }
                }));

        assertThat(compiled.source())
                .contains("public final class Box<T> extends BaseTwin<T> {")
                .contains("return this.p(v0);")
                .contains("return this.m(v0);");
        Object box = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("go", Object.class).invoke(box, "x"))
                .isEqualTo("base:x");
        assertThat(compiled.generated().getMethod("pub", Object.class).invoke(box, "x"))
                .isEqualTo("base:x");
    }

    @Test
    void aSwitchIsOverAValueOfATypeParameterBoundedByAnEnum() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("E", Op_.TOKEN),
                new TypedJavaFile.TypedGenericClassSpec1<Op>() {
                    @Override
                    public <Self, E extends Op> void build(ClassHeader<Self> h, TypeVarToken<E> e) {
                        var cb = h.extendsObject().members();
                        var member4 = cb.declareConstructor();
                        var member5 = cb.declareMethod("go", PrimitiveToken.INT, e);
                        var bodies = cb.bodies();
                        bodies.define(member4, (b, self) -> b.end());
                        bodies.define(
                                member5,
                                (b, self, v) -> b.return_(switch_(
                                        Op_.TOKEN,
                                        v,
                                        PrimitiveToken.INT,
                                        c -> c.case_(Op_.ADD, literal(1)).case_(Op_.SUB, literal(2)))));
                    }
                }));

        assertThat(compiled.source())
                .contains("public final class Box<E extends Op> {")
                .contains("switch ((Op) v0) {");
        Object box = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("go", Op.class).invoke(box, Op.SUB))
                .isEqualTo(2);
    }

    @Test
    void theResultOfAnOverrideIsATypeParameterBoundedByTheResultOfTheMethod() throws Throwable {
        // CharSequence label() of Labelled, overridden with the result T extends CharSequence
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("T", CharSequence_.TOKEN),
                new TypedJavaFile.TypedGenericClassSpec1<CharSequence>() {
                    @Override
                    public <Self, T extends CharSequence> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                        var label = h.overriding(Labelled_.label, t);
                        var oh = h.extendsObject();
                        oh.implements_(Labelled_.implement(h.self()).label(label));
                        var cb = oh.members();
                        var held = cb.declareMutableField("held", t);
                        var member6 = cb.declareConstructor(t);
                        var bodies = cb.bodies();
                        bodies.initialize(held, literalNull(t));
                        bodies.define(label.method(), (b, self) -> b.return_(field(self, held)));
                        bodies.define(
                                member6,
                                (b, self, v) ->
                                        b.exec(assignField(self, held, v)).end());
                    }
                }));

        assertThat(compiled.source())
                .contains("public final class Box<T extends CharSequence> implements Labelled {")
                .contains("@Override\n    public T label() {");
        Object box = compiled.generated().getConstructor(CharSequence.class).newInstance("held");
        assertThat(((Labelled) box).label()).isEqualTo("held");
    }

    @Test
    void anOverrideTakesAParameterOfABoundedTypeParameter() throws Throwable {
        // void accept(T) of Consumer<T>, in a Box<T extends Number>: the method erases to accept(Number)
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("T", Number_.TOKEN),
                new TypedJavaFile.TypedGenericClassSpec1<Number>() {
                    @Override
                    public <Self, T extends Number> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                        var consumer = new Consumer_<>(t);
                        var accept = h.overriding(consumer.accept_T);
                        var oh = h.extendsObject();
                        oh.implements_(consumer.implement(h.self()).accept_T(accept));
                        var cb = oh.members();
                        var seen = cb.declareMutableField("seen", PrimitiveToken.INT);
                        var member7 = cb.declareConstructor();
                        var bodies = cb.bodies();
                        bodies.initialize(seen, literal(0));
                        bodies.define(
                                accept.method(),
                                (b, self, v) -> b.exec(assignField(self, seen, call(v, Number_.intValue)))
                                        .end());
                        bodies.define(member7, (b, self) -> b.end());
                    }
                }));

        assertThat(compiled.source())
                .contains("public final class Box<T extends Number> implements Consumer<T> {")
                .contains("@Override\n    public void accept(T v0) {");
        Object box = compiled.generated().getConstructor().newInstance();
        @SuppressWarnings("unchecked")
        Consumer<Object> consumer = (Consumer<Object>) box;
        consumer.accept(7);
        assertThat(compiled.generated().getField("seen").get(box)).isEqualTo(7);
    }
}
