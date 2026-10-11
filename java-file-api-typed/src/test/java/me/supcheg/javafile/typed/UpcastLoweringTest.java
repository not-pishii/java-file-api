package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Base;
import me.supcheg.javafile.typed.fixtures.Signal;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.function.Supplier_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Base_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Op_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.castChecked;
import static me.supcheg.javafile.typed.Expressions.cond;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.lambda;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.switch_;
import static me.supcheg.javafile.typed.Subclasses.began;
import static org.assertj.core.api.Assertions.assertThat;

/// An [Upcast] witness changes the token of an expression and nothing in
/// the rendered code, so javac still types `this` by the class: these tests
/// observe that a member reached through a witness is the one its fact
/// names, and not the overload of the class javac would prefer for `this`.
///
/// The witness of a class that extends `Object` is of `Object`; the class
/// declares the overloads `Object` has no notion of. Where the supertype
/// is a superclass — the owner of a field, of an overload — the class is
/// declared from its header on; where it would have to be one no class has,
/// an enum, the rendered code is read, not compiled.
class UpcastLoweringTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Witnessed");
    private static final Supplier_<String> STRINGS = new Supplier_<>(String_.TOKEN);

    private static JavaFile file(TypedJavaFile.TypedClassSpec spec) {
        return TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    private static <Self> Upcast<Self, Object> asObject(TypedClassBuilder<Self> cb) {
        return new Upcast<>(cb.self(), Object_.TOKEN);
    }

    @Test
    void aCallThroughAWitnessDoesNotReachTheOverloadOfTheClass() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                Upcast<Self, Object> up = asObject(cb);
                // the class has equals(String) beside the equals(Object) it inherits
                cb.method("equals", PrimitiveToken.BOOLEAN, String_.TOKEN, (b, self, s) -> b.return_(literal(true)));
                cb.method(
                        "viaWitness",
                        PrimitiveToken.BOOLEAN,
                        (b, self) -> b.return_(call(up.apply(self), Object_.equals_Object, literal("x"))));
                cb.method(
                        "inLambda",
                        String_.TOKEN,
                        (b, self) -> b.let(
                                STRINGS.token,
                                lambda(
                                        STRINGS.sam,
                                        () -> cond(
                                                call(up.apply(self), Object_.equals_Object, literal("x")),
                                                literal("class"),
                                                literal("object"),
                                                String_.TOKEN)),
                                f -> b.return_(call(f, STRINGS.get))));
            }
        }));

        assertThat(compiled.source()).contains("this.equals((Object) \"x\")");
        assertThat(compiled.invoke("viaWitness")).isEqualTo(false);
        assertThat(compiled.invoke("inLambda")).isEqualTo("object");
    }

    @Test
    void anArgumentThroughAWitnessIsOfTheSupertype() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                Upcast<Self, Object> up = asObject(cb);
                cb.staticMethod("which", String_.TOKEN, cb.self(), (b, o) -> b.return_(literal("self")));
                var whichObject =
                        cb.staticMethod("which", String_.TOKEN, Object_.TOKEN, (b, o) -> b.return_(literal("object")));
                cb.method("pick", String_.TOKEN, (b, self) -> b.return_(staticCall(whichObject, up.apply(self))));
            }
        }));

        assertThat(compiled.source()).contains("which((Object) this)");
        assertThat(compiled.invoke("pick")).isEqualTo("object");
    }

    @Test
    void aWitnessedExpressionIsOfTheSupertypeWhereJavacChecksItsType() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                Upcast<Self, Object> up = asObject(cb);
                // `(String) this` and `this == "x"` are no Java for a final class that is no String
                cb.method("cast", String_.TOKEN, (b, self) -> b.return_(castChecked(String_.TOKEN, up.apply(self))));
                cb.method("same", PrimitiveToken.BOOLEAN, (b, self) -> b.return_(eqRef(up.apply(self), literal("x"))));
                cb.method(
                        "test",
                        PrimitiveToken.BOOLEAN,
                        (b, self) -> b.ifInstanceOfElse(
                                up.apply(self),
                                String_.TOKEN,
                                (t, s) -> t.return_(literal(true)),
                                e -> e.return_(literal(false))));
                // both branches are the class: javac would type the conditional by it
                cb.method(
                        "either",
                        PrimitiveToken.BOOLEAN,
                        (b, self) -> b.return_(call(
                                cond(literal(true), up.apply(self), up.apply(self), Object_.TOKEN),
                                Object_.equals_Object,
                                literal("x"))));
                cb.method("equals", PrimitiveToken.BOOLEAN, String_.TOKEN, (b, self, s) -> b.return_(literal(true)));
            }
        }));

        assertThat(compiled.source())
                .contains("(String) (Object) this")
                .contains("(Object) this == \"x\"")
                .contains("(Object) this instanceof String");
        assertThat(compiled.invoke("same")).isEqualTo(false);
        assertThat(compiled.invoke("test")).isEqualTo(false);
        assertThat(compiled.invoke("either")).isEqualTo(false);
    }

    @Test
    void aFieldAndTheSelectorOfASwitchThroughAWitnessAreThoseOfTheSuperclass() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var cb = began(
                                h.extends_(Base_.extend(h.self())),
                                ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                        Upcast<Self, Base> asBase = cb.asSuper();
                        // the class has a field `name` of its own: the one of Base is read through the witness
                        cb.field("name", String_.TOKEN, literal("own"));
                        cb.method(
                                "nameOfBase",
                                String_.TOKEN,
                                (b, self) -> b.return_(field(asBase.apply(self), Base_.name)));
                        cb.method(
                                "op",
                                String_.TOKEN,
                                (b, self) -> b.return_(switch_(
                                        Op_.TOKEN,
                                        field(asBase.apply(self), Base_.op),
                                        String_.TOKEN,
                                        c -> c.case_(Op_.SUB, literal("sub")).default_(literal("other")))));
                    }
                }));

        assertThat(compiled.source()).contains("((Base) this).name").contains("switch (((Base) this).op)");
        assertThat(compiled.invoke("nameOfBase")).isEqualTo("base");
        assertThat(compiled.invoke("op")).isEqualTo("sub");
    }

    @Test
    void aCallThroughTheWitnessOfTheSuperclassIsNotTakenByAnOverloadOfTheClass() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var cb = began(
                                h.extends_(Base_.extend(h.self())),
                                ctor -> ctor.super_(Base_.new_, (b, self) -> b.end()));
                        Upcast<Self, Base> asBase = cb.asSuper();
                        // javac would resolve this.pick("x") to the override of pick(String), or to the
                        // overload the class adds: the fact names pick(Object) of Base
                        cb.override(Base_.pick_String, (b, self, s) -> b.return_(literal("override")));
                        cb.method(
                                "pick",
                                String_.TOKEN,
                                me.supcheg.javafile.typed.testfacts.java.lang.CharSequence_.TOKEN,
                                (b, self, s) -> b.return_(literal("overload")));
                        cb.method(
                                "picked",
                                String_.TOKEN,
                                (b, self) -> b.return_(call(asBase.apply(self), Base_.pick_Object, literal("x"))));
                        cb.method(
                                "pickedBySuper",
                                String_.TOKEN,
                                (b, self) -> b.return_(cb.superCall(self, Base_.pick_Object, literal("x"))));
                    }
                }));

        assertThat(compiled.source())
                .contains("return this.pick((Object) \"x\");")
                .contains("return super.pick((Object) \"x\");");
        assertThat(compiled.invoke("picked")).isEqualTo("object");
        assertThat(compiled.invoke("pickedBySuper")).isEqualTo("object");
    }

    @Test
    void theSelectorOfASwitchThroughAWitnessGoesByTheClass() {
        // no class being declared is of an enum, so no declaration gives this witness: the rendered
        // code is read, not compiled
        String source = file(new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> void build(TypedClassBuilder<Self> cb) {
                        Upcast<Self, Signal> asSignal = new Upcast<>(cb.self(), Signal_.TOKEN);
                        cb.method(
                                "signal",
                                String_.TOKEN,
                                (b, self) -> b.return_(switch_(
                                        Signal_.TOKEN,
                                        asSignal.apply(self),
                                        String_.TOKEN,
                                        c -> c.default_(literal("none")))));
                    }
                })
                .render();

        assertThat(source).contains("switch ((Signal) this)");
    }

    @Test
    void anUnusedWitnessLeavesTheExpressionAsItIs() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassSpec() {
            @Override
            public <Self> void build(TypedClassBuilder<Self> cb) {
                Upcast<Self, Object> up = asObject(cb);
                // no overload of the class is in question: `this` stands as it is, no redundant cast
                cb.method("me", Object_.TOKEN, (b, self) -> b.return_(up.apply(self)));
                cb.method("text", String_.TOKEN, (b, self) -> b.return_(call(up.apply(self), Object_.toString)));
            }
        }));

        assertThat(compiled.source()).contains("return this;").contains("this.toString()");
        assertThat(compiled.invoke("me")).isNotNull();
    }
}
