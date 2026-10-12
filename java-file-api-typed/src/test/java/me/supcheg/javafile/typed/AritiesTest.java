package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Wide;
import me.supcheg.javafile.typed.fixtures.Widening;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Wide_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Widening_;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static me.supcheg.javafile.typed.Expressions.voidCall;
import static me.supcheg.javafile.typed.Expressions.voidStaticCall;
import static org.assertj.core.api.Assertions.assertThat;

/// The forms of the typed layer at the arities beyond none: a call, a
/// declaration, an override and a body of a member of one, two and three
/// parameters hand each argument to its parameter. The classes are compiled
/// under `-Xlint:all` and run, and tell what their members were called with.
///
/// The tests share the static field of [Wide], so they run one after another.
@Execution(ExecutionMode.SAME_THREAD)
class AritiesTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Test
    void theMethodsOfAnInterfaceAreImplementedAndOverriddenAtEveryArity() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                Upcast<Self, Widening> becoming = h.becoming(Widening_.TOKEN);
                // the abstract methods: with a narrower result, with the class for the result, void
                var made1 = h.overriding(Widening_.made_String, String_.TOKEN);
                var made2 = h.overriding(Widening_.made_String_String, String_.TOKEN);
                var self1 = h.overriding(Widening_.self_String, becoming);
                var self2 = h.overriding(Widening_.self_String_String, becoming);
                var take = h.overriding(Widening_.take_String_String);
                var oh = h.extendsObject();
                Upcast<Self, Widening> asWidening = oh.implements_(Widening_.implement(h.self())
                        .made_String(made1)
                        .made_String_String(made2)
                        .self_String(self1)
                        .self_String_String(self2)
                        .take_String_String(take));
                var cb = oh.members();
                // the default methods, overridden by the witness of the interface
                var given1 = cb.declareOverride(asWidening, Widening_.given_String, String_.TOKEN);
                var given2 = cb.declareOverride(asWidening, Widening_.given_String_String, String_.TOKEN);
                var held = cb.declareOverride(asWidening, Widening_.held_String_String);
                var chained0 = cb.declareOverride(asWidening, Widening_.chained, asWidening);
                var chained2 = cb.declareOverride(asWidening, Widening_.chained_String_String, asWidening);
                var fill0 = cb.declareOverride(asWidening, Widening_.fill);
                var fill1 = cb.declareOverride(asWidening, Widening_.fill_String);
                var fill2 = cb.declareOverride(asWidening, Widening_.fill_String_String);
                var bodies = cb.bodies();
                bodies.define(made1.method(), (b, _, first) -> b.return_(concat(literal("made:"), first)));
                bodies.define(made2.method(), (b, _, first, second) -> b.return_(concat(first, second)));
                bodies.define(self1.method(), (b, self, _) -> b.return_(self));
                bodies.define(self2.method(), (b, self, _, _) -> b.return_(self));
                bodies.define(
                        take.method(),
                        (b, _, first, second) -> b.exec(voidStaticCall(Wide_.note_String_String, first, second))
                                .end());
                bodies.define(given1, (b, _, first) -> b.return_(concat(literal("mine:"), first)));
                bodies.define(given2, (b, _, first, second) -> b.return_(concat(second, first)));
                bodies.define(held, (b, _, first, second) -> b.return_(concat(concat(first, literal("+")), second)));
                bodies.define(chained0, (b, self) -> b.return_(self));
                bodies.define(chained2, (b, self, _, _) -> b.return_(self));
                bodies.define(
                        fill0,
                        (b, _) -> b.exec(voidStaticCall(Wide_.note_String, literal("filled")))
                                .end());
                bodies.define(
                        fill1,
                        (b, _, first) ->
                                b.exec(voidStaticCall(Wide_.note_String, first)).end());
                bodies.define(
                        fill2,
                        (b, _, first, second) -> b.exec(
                                        voidStaticCall(Wide_.note_String_String_String, first, second, literal("!")))
                                .end());
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("public final class Probe implements Widening {")
                .contains("@Override\n    public String made(String ")
                .contains("@Override\n    public Probe self(String ")
                .contains("@Override\n    public Probe chained() {")
                .contains("@Override\n    public String given(String ");
        Widening probe = (Widening) compiled.generated().getConstructor().newInstance();
        assertThat(probe.made("a")).isEqualTo("made:a");
        assertThat(probe.made("a", "b")).isEqualTo("ab");
        assertThat(probe.self("a")).isSameAs(probe);
        assertThat(probe.self("a", "b")).isSameAs(probe);
        assertThat(probe.given("a")).isEqualTo("mine:a");
        assertThat(probe.given("a", "b")).isEqualTo("ba");
        assertThat(probe.held("a", "b")).isEqualTo("a+b");
        assertThat(probe.chained()).isSameAs(probe);
        assertThat(probe.chained("a", "b")).isSameAs(probe);
        probe.take("a", "b");
        assertThat(Wide.noted).isEqualTo("ab");
        probe.fill();
        assertThat(Wide.noted).isEqualTo("filled");
        probe.fill("a");
        assertThat(Wide.noted).isEqualTo("a");
        probe.fill("a", "b");
        assertThat(Wide.noted).isEqualTo("ab!");
    }

    @Test
    void theMethodsOfASuperclassAreOverriddenAndCalledAtEveryArity() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                var sh = h.extends_(Wide_.extend(h.self()));
                var cb = sh.members();
                Upcast<Self, Wide> asWide = cb.asSuper();
                var constructor = cb.declareConstructor(String_.TOKEN, String_.TOKEN);
                var two = cb.declareOverride(Wide_.two_String_String);
                var put1 = cb.declareOverride(Wide_.put_String);
                var put2 = cb.declareOverride(Wide_.put_String_String);
                var one = cb.declareOverride(Wide_.one_String, String_.TOKEN);
                var pair = cb.declareOverride(Wide_.pair_String_String, String_.TOKEN);
                var same1 = cb.declareOverride(Wide_.same_String, asWide);
                var same2 = cb.declareOverride(Wide_.same_String_String, asWide);
                var own = cb.declareVoidMethod("own", String_.TOKEN, String_.TOKEN);
                var joined = cb.declareMethod("joined", String_.TOKEN, String_.TOKEN, String_.TOKEN);
                var fresh = cb.declareMethod("fresh", String_.TOKEN, String_.TOKEN, String_.TOKEN);
                var bodies = cb.bodies(
                        constructor,
                        (ctor, first, second) ->
                                ctor.super_(Wide_.new_String_String, first, second, (b, _) -> b.end()));
                bodies.define(two, (b, _, first, second) -> b.return_(concat(second, first)));
                // super.put(first, first);
                bodies.define(
                        put1,
                        (b, self, first) -> b.exec(bodies.superCall(self, Wide_.put_String_String, first, first))
                                .end());
                bodies.define(
                        put2,
                        (b, self, first, second) -> b.exec(bodies.superCall(
                                        self, Wide_.put_String_String, first, concat(second, literal("!"))))
                                .end());
                bodies.define(one, (b, _, first) -> b.return_(concat(literal("mine:"), first)));
                bodies.define(pair, (b, _, first, second) -> b.return_(concat(first, second)));
                bodies.define(same1, (b, self, _) -> b.return_(self));
                bodies.define(same2, (b, self, _, _) -> b.return_(self));
                bodies.define(
                        own,
                        (b, self, first, second) -> b.exec(voidCall(
                                        asWide.apply(self),
                                        Wide_.put_String_String_String,
                                        first,
                                        second,
                                        literal("?")))
                                .exec(voidCall(asWide.apply(self), Wide_.put_String_String, first, second))
                                .end());
                bodies.define(
                        joined,
                        (b, _, first, second) ->
                                b.return_(staticCall(Wide_.join_String_String_String, first, second, literal("!"))));
                bodies.define(
                        fresh,
                        (b, _, first, second) -> b.return_(concat(
                                field(new_(Wide_.new_String_String, first, second), Wide_.told),
                                field(new_(Wide_.new_String_String_String, first, second, literal("!")), Wide_.told))));
                return bodies;
            }
        }));

        assertThat(compiled.source())
                .contains("public final class Probe extends Wide {")
                .contains("super(v0, v1);")
                .contains("@Override\n    public String one(String ")
                .contains("@Override\n    public String pair(String ")
                .contains("@Override\n    public Probe same(String ");
        Wide probe = (Wide)
                compiled.generated().getConstructor(String.class, String.class).newInstance("a", "b");
        assertThat(probe.told).isEqualTo("new:ab");
        assertThat(probe.two("a", "b")).isEqualTo("ba");
        assertThat(probe.one("a")).isEqualTo("mine:a");
        assertThat(probe.pair("a", "b")).isEqualTo("ab");
        assertThat(probe.same("a")).isSameAs(probe);
        assertThat(probe.same("a", "b")).isSameAs(probe);
        probe.told = "";
        probe.put("a");
        assertThat(probe.told).isEqualTo("put:aa");
        probe.told = "";
        probe.put("a", "b");
        assertThat(probe.told).isEqualTo("put:ab!");
        probe.told = "";
        compiled.generated().getMethod("own", String.class, String.class).invoke(probe, "a", "b");
        // put(String, String) is called as the class overrides it
        assertThat(probe.told).isEqualTo("put:ab?put:ab!");
        assertThat(compiled.generated()
                        .getMethod("joined", String.class, String.class)
                        .invoke(probe, "a", "b"))
                .isEqualTo("ab!");
        assertThat(compiled.generated()
                        .getMethod("fresh", String.class, String.class)
                        .invoke(probe, "a", "b"))
                .isEqualTo("new:abnew:ab!");
    }

    @Test
    void aConstructorOfTwoParametersIsDefinedByItsBody() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var constructor = cb.declareConstructor(String_.TOKEN, String_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.define(
                                constructor,
                                (b, _, first, second) -> b.exec(voidStaticCall(Wide_.note_String_String, second, first))
                                        .end());
                        return bodies;
                    }
                }));

        compiled.generated().getConstructor(String.class, String.class).newInstance("a", "b");

        assertThat(Wide.noted).isEqualTo("ba");
    }
}
