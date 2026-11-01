package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.fixtures.Closing;
import me.supcheg.javafile.typed.testfacts.java.lang.IllegalStateException_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.lang.Throwable_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Closing_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.length;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.Expressions.staticCall;
import static org.assertj.core.api.Assertions.assertThat;

/// Try-with-resources (6.3): a declared and an existing resource are closed on every way out, and a
/// close that throws while the body throws leaves the body's exception primary with the close's
/// suppressed, as javac makes it.
class TryResourcesCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Resources");

    @Test
    void resourcesAreClosedAndSuppressAsJavacDoes() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var ok = cb.declareStaticMethod("ok", String_.TOKEN, PrimitiveToken.BOOLEAN);
                        var failed = cb.declareStaticMethod("failed", String_.TOKEN, PrimitiveToken.BOOLEAN);
                        var existing = cb.declareStaticMethod("existing", String_.TOKEN, Closing_.TOKEN);
                        var statement = cb.declareVoidStaticMethod("statement", PrimitiveToken.BOOLEAN);
                        var bodies = cb.bodies();
                        bodies.define(
                                ok,
                                (b, failing) -> b.tryWithResourcesTerminated(
                                        h -> h.catch_(
                                                IllegalStateException_.TOKEN,
                                                (c, e) -> c.return_(call(e, Throwable_.getMessage))),
                                        (t, resources) -> {
                                            var resource = resources.resource_(
                                                    Closing_.TOKEN, new_(Closing_.new_boolean, failing));
                                            return t.if_(eqRef(resource, resource), _ -> {})
                                                    .return_(literal("ok"));
                                        }));
                        bodies.define(
                                failed,
                                (b, failing) -> b.tryWithResourcesTerminated(
                                        h -> h.catch_(
                                                IllegalStateException_.TOKEN,
                                                (c, e) -> c.return_(concat(
                                                        concat(call(e, Throwable_.getMessage), literal(":")),
                                                        staticCall(
                                                                String_.valueOf_int,
                                                                length(
                                                                        ArrayToken.of(Throwable_.TOKEN),
                                                                        call(e, Throwable_.getSuppressed)))))),
                                        (t, resources) -> {
                                            var resource = resources.resource_(
                                                    Closing_.TOKEN, new_(Closing_.new_boolean, failing));
                                            return t.if_(eqRef(resource, resource), _ -> {})
                                                    .throw_(new_(IllegalStateException_.new_String, literal("body")));
                                        }));
                        bodies.define(
                                existing,
                                (b, first) -> b.tryWithResourcesTerminated(
                                        h -> h.catch_(
                                                IllegalStateException_.TOKEN, (c, _) -> c.return_(literal("close"))),
                                        (t, resources) -> {
                                            var one = resources.resource_(first);
                                            var two = resources.resource_(
                                                    Closing_.TOKEN, new_(Closing_.new_boolean, literal(false)));
                                            return t.if_(eqRef(one, one), _ -> {})
                                                    .if_(eqRef(two, two), _ -> {})
                                                    .return_(literal("ok"));
                                        }));
                        bodies.define(
                                statement,
                                (b, failing) -> b.tryWithResources(
                                                h -> h.finally_(f -> f.exec(staticCall(Closing_.closes))),
                                                (t, resources) -> {
                                                    var resource = resources.resource_(
                                                            Closing_.TOKEN, new_(Closing_.new_boolean, failing));
                                                    t.if_(eqRef(resource, resource), _ -> {})
                                                            .exec(staticCall(Closing_.closes));
                                                })
                                        .end());
                        return bodies;
                    }
                }));

        assertThat(compiled.source())
                .contains("try (Closing v1 = new Closing(v0)) {")
                .contains("try (v0; Closing v1 = new Closing(false)) {")
                .contains("catch (IllegalStateException v");

        Closing.reset();
        assertThat(compiled.invoke("ok", false)).isEqualTo("ok");
        assertThat(Closing.closes()).isEqualTo(1);

        Closing.reset();
        assertThat(compiled.invoke("ok", true)).isEqualTo("close");

        Closing.reset();
        assertThat(compiled.invoke("failed", true)).isEqualTo("body:1");
        Closing.reset();
        assertThat(compiled.invoke("failed", false)).isEqualTo("body:0");

        Closing.reset();
        assertThat(compiled.invoke("existing", new Closing(false))).isEqualTo("ok");
        assertThat(Closing.closes()).isEqualTo(2);

        Closing.reset();
        compiled.invoke("statement", false);
        assertThat(Closing.closes()).isEqualTo(1);
    }
}
