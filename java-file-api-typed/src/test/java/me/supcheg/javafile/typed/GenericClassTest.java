package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.TypeVarToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Carrier;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.typed.testfacts.java.lang.Number_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Carrier_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Shape_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.function.Supplier;

import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.literalNull;
import static me.supcheg.javafile.typed.Expressions.ltInt;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static me.supcheg.javafile.typed.TypedJavaFile.typeParam;
import static org.assertj.core.api.Assertions.assertThat;

/// A generic class (§8g): its type parameters are tokens the specification
/// is given, each branded with a type variable of the generator that has the
/// leftmost bound for its own; `Self` is the class with its type parameters
/// for its type arguments. The classes are rendered, compiled under
/// `-Xlint:all` and run.
class GenericClassTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Box");

    private static JavaFile box(TypedJavaFile.TypedGenericClassSpec1<Object> spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, typeParam("T"), spec);
    }

    @Test
    void aContainerHoldsAValueOfItsTypeParameter() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(box(new TypedJavaFile.TypedGenericClassSpec1<>() {
            @Override
            public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                var cb = h.extendsObject().members();
                var item = cb.declareMutableField("item", t);
                var ctor = cb.declareConstructor();
                var put = cb.declareVoidMethod("put", t);
                var get = cb.declareMethod("get", t);
                // the class is made in its own body with its type parameter for the type argument
                var member1 = cb.declareMethod("copy", cb.self());
                var bodies = cb.bodies();
                bodies.initialize(item, literalNull(t));
                bodies.define(ctor, (b, self) -> b.end());
                bodies.define(
                        put,
                        (b, self, value) ->
                                b.exec(assignField(self, item, value)).end());
                bodies.define(get, (b, self) -> b.return_(field(self, item)));
                bodies.define(
                        member1,
                        (b, self) -> b.let(
                                cb.self(),
                                new_(ctor),
                                copy -> b.exec(voidCall(copy, put, call(self, get)))
                                        .return_(copy)));
            }
        }));

        assertThat(compiled.source())
                .contains("public final class Box<T> {")
                .contains("public T item = (T) null;")
                .contains("public void put(T v0) {")
                .contains("public T get() {")
                .contains("public Box<T> copy() {")
                .contains("Box<T> v0 = new Box<>();");
        Object box = compiled.generated().getConstructor().newInstance();
        compiled.generated().getMethod("put", Object.class).invoke(box, "held");
        Object copy = compiled.generated().getMethod("copy").invoke(box);
        assertThat(copy).isNotSameAs(box);
        assertThat(compiled.generated().getMethod("get").invoke(copy)).isEqualTo("held");
    }

    @Test
    void aValueOfATypeParameterHasTheMethodsOfItsLeftmostBound() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("N", Number_.TOKEN),
                new TypedJavaFile.TypedGenericClassSpec1<>() {
                    @Override
                    public <Self, N extends Number> void build(ClassHeader<Self> h, TypeVarToken<N> n) {
                        var cb = h.extendsObject().members();
                        var item = cb.declareMethod("item", n, n);
                        // an expression of N is one of Number to the compiler of the generator
                        var asInt = cb.declareMethod("asInt", PrimitiveToken.INT, n);
                        var bodies = cb.bodies();
                        bodies.define(item, (b, self, value) -> b.return_(value));
                        bodies.define(
                                asInt, (b, self, value) -> b.return_(call(call(self, item, value), Number_.intValue)));
                    }
                }));

        assertThat(compiled.source())
                .contains("public final class Box<N extends Number> {")
                .contains("public N item(N v0) {")
                .contains("return this.item(v0).intValue();");
        Object box = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("asInt", Number.class).invoke(box, 41L))
                .isEqualTo(41);
    }

    @Test
    void aGenericClassExtendsAndImplementsTypesOfItsTypeParameterAndOfItself() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(box(new TypedJavaFile.TypedGenericClassSpec1<>() {
            @Override
            public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                var carrier = new Carrier_<>(t);
                var cmp = new Comparable_<>(h.self());
                var compareTo = h.overriding(cmp.compareTo_T);
                var sh = h.extends_(carrier.extend(h.self()));
                Upcast<Self, Comparable<Self>> asComparable =
                        sh.implements_(cmp.implement(h.self()).compareTo_T(compareTo));
                var cb = sh.members();
                var first = cb.declareConstructor(t);
                // what the class has from Carrier<T> is in terms of its own T
                var mine = cb.declareMethod("mine", t);
                var same = cb.declareMethod("same", PrimitiveToken.INT);
                var described = cb.declareOverride(carrier.described_T);
                var bodies = cb.bodies(first, (ctor, v) -> ctor.super_(carrier.new_T, v, (b, self) -> b.end()));
                bodies.define(compareTo.method(), (b, self, other) -> b.return_(literal(7)));
                bodies.define(
                        described, (b, self, other) -> b.return_(bodies.superCall(self, carrier.described_T, other)));
                bodies.define(mine, (b, self) -> b.return_(call(sh.asSuper().apply(self), carrier.carried)));
                bodies.define(same, (b, self) -> b.return_(call(asComparable.apply(self), cmp.compareTo_T, self)));
            }
        }));

        assertThat(compiled.source())
                .contains("public final class Box<T> extends Carrier<T> implements Comparable<Box<T>> {")
                .contains("public Box(T v0) {\n        super(v0);")
                .contains("@Override\n    public int compareTo(Box<T> v0) {")
                .contains("@Override\n    public String described(T v0) {")
                .contains("return super.described(v0);");
        Object box = compiled.generated().getConstructor(Object.class).newInstance("carried");
        assertThat(((Carrier<?>) box).carried()).isEqualTo("carried");
        assertThat(compiled.generated().getMethod("mine").invoke(box)).isEqualTo("carried");
        assertThat(compiled.generated().getMethod("same").invoke(box)).isEqualTo(7);
        @SuppressWarnings("unchecked")
        Comparable<Object> comparable = (Comparable<Object>) box;
        assertThat(comparable.compareTo(box)).isEqualTo(7);
    }

    @Test
    void aFurtherBoundIsAWitnessOfTheHeader() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("T", Number_.TOKEN),
                new TypedJavaFile.TypedGenericClassSpec1<>() {
                    @Override
                    public <Self, T extends Number> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                        var comparable = new Comparable_<>(t);
                        Upcast<T, Comparable<T>> asComparable = h.bound(t, comparable.token);
                        var cb = h.extendsObject().members();
                        var less = cb.declareMethod("less", PrimitiveToken.BOOLEAN, t, t);
                        // the leftmost bound is the type of the generator, the further one a witness
                        var asInt = cb.declareMethod("asInt", PrimitiveToken.INT, t);
                        var bodies = cb.bodies();
                        bodies.define(
                                less,
                                (b, self, one, other) -> b.return_(ltInt(
                                        call(asComparable.apply(one), comparable.compareTo_T, other), literal(0))));
                        bodies.define(asInt, (b, self, one) -> b.return_(call(one, Number_.intValue)));
                    }
                }));

        assertThat(compiled.source())
                .contains("public final class Box<T extends Number & Comparable<T>> {")
                .contains("public boolean less(T v0, T v1) {");
        Object box = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated()
                        .getMethod("less", Number.class, Number.class)
                        .invoke(box, 1, 2))
                .isEqualTo(true);
        assertThat(compiled.generated()
                        .getMethod("less", Number.class, Number.class)
                        .invoke(box, 2, 1))
                .isEqualTo(false);
    }

    @Test
    void aBoundThatMentionsTheTypeParameterItselfIsAWitnessBesideObject() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(box(new TypedJavaFile.TypedGenericClassSpec1<>() {
            @Override
            public <Self, T> void build(ClassHeader<Self> h, TypeVarToken<T> t) {
                var comparable = new Comparable_<>(t);
                var asComparable = h.bound(t, comparable.token);
                var cb = h.extendsObject().members();
                var compare = cb.declareMethod("compare", PrimitiveToken.INT, t, t);
                // the erasure of T is that of its leftmost bound, Object: the two methods are two to javac too
                var show = cb.declareMethod("show", String_.TOKEN, t);
                var member9 = cb.declareMethod("show", String_.TOKEN, new Comparable_<>(String_.TOKEN).token);
                var bodies = cb.bodies();
                bodies.define(
                        compare,
                        (b, self, one, other) ->
                                b.return_(call(asComparable.apply(one), comparable.compareTo_T, other)));
                bodies.define(show, (b, self, one) -> b.return_(literal("T")));
                bodies.define(member9, (b, self, one) -> b.return_(literal("Comparable")));
            }
        }));

        assertThat(compiled.source()).contains("public final class Box<T extends Object & Comparable<T>> {");
        Object box = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated()
                        .getMethod("compare", Object.class, Object.class)
                        .invoke(box, "a", "b"))
                .isEqualTo(-1);
        assertThat(compiled.generated().getMethod("show", Object.class).invoke(box, "a"))
                .isEqualTo("T");
    }

    @Test
    void theTypeParameterIsTheResultOfAnOverrideByItsBound() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("N", Number_.TOKEN),
                new TypedJavaFile.TypedGenericClassSpec1<>() {
                    @Override
                    public <Self, N extends Number> void build(ClassHeader<Self> h, TypeVarToken<N> n) {
                        // Number get() of Supplier<Number>, answered by N get(): N is a Number by its bound
                        var numbers = new Supplier_<>(Number_.TOKEN);
                        var get = h.overriding(numbers.get, n);
                        var oh = h.extendsObject();
                        oh.implements_(numbers.implement(h.self()).get(get));
                        var cb = oh.members();
                        var bodies = cb.bodies();
                        bodies.define(get.method(), (b, self) -> b.return_(literalNull(n)));
                    }
                }));

        assertThat(compiled.source())
                .contains("public final class Box<N extends Number> implements Supplier<Number> {")
                .contains("@Override\n    public N get() {");
        Object box = compiled.generated().getConstructor().newInstance();
        assertThat(((Supplier<?>) box).get()).isNull();
    }

    @Test
    void aClassHasUpToThreeTypeParametersEachWithItsBound() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("A"),
                typeParam("B", Number_.TOKEN),
                typeParam("C", new Comparable_<>(String_.TOKEN).token),
                new TypedJavaFile.TypedGenericClassSpec3<>() {
                    @Override
                    public <Self, A, B extends Number, C extends Comparable<String>> void build(
                            ClassHeader<Self> h, TypeVarToken<A> a, TypeVarToken<B> b, TypeVarToken<C> c) {
                        var cb = h.extendsObject().members();
                        var first = cb.declareMethod("first", a, a, b);
                        var second = cb.declareMethod("second", PrimitiveToken.INT, b, c);
                        var third = cb.declareMethod("third", PrimitiveToken.INT, c);
                        var bodies = cb.bodies();
                        bodies.define(first, (body, self, one, two) -> body.return_(one));
                        bodies.define(second, (body, self, two, three) -> body.return_(call(two, Number_.intValue)));
                        bodies.define(
                                third,
                                (body, self, three) -> body.return_(
                                        call(three, new Comparable_<>(String_.TOKEN).compareTo_T, literal("b"))));
                    }
                }));

        assertThat(compiled.source())
                .contains("public final class Box<A, B extends Number, C extends Comparable<String>> {");
        Object box = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("third", Comparable.class).invoke(box, "a"))
                .isEqualTo(-1);
    }

    @Test
    void aTypeOfTheNameOfATypeParameterIsWrittenQualifiedInTheClass() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(),
                DESC,
                typeParam("String"),
                typeParam("Shape"),
                new TypedJavaFile.TypedGenericClassSpec2<>() {
                    @Override
                    public <Self, T1, T2> void build(ClassHeader<Self> h, TypeVarToken<T1> t1, TypeVarToken<T2> t2) {
                        var cb = h.extendsObject().members();
                        var text = cb.declareMethod("text", String_.TOKEN, t1);
                        var shape = cb.declareMethod("shape", Shape_.TOKEN, t2);
                        var own = cb.declareMethod("own", Object_.TOKEN, t1);
                        var bodies = cb.bodies();
                        bodies.define(text, (b, self, one) -> b.return_(literal("text")));
                        bodies.define(shape, (b, self, two) -> b.return_(literalNull(Shape_.TOKEN)));
                        bodies.define(own, (b, self, one) -> b.return_(one));
                    }
                }));

        assertThat(compiled.source())
                .contains("public final class Box<String, Shape> {")
                .contains("public java.lang.String text(String v0) {")
                .contains("public me.supcheg.javafile.typed.fixtures.Shape shape(Shape v0) {")
                .contains("public Object own(String v0) {");
        Object box = compiled.generated().getConstructor().newInstance();
        assertThat(compiled.generated().getMethod("text", Object.class).invoke(box, 1))
                .isEqualTo("text");
    }
}
