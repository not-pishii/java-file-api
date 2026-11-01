package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.gtInt;
import static me.supcheg.javafile.typed.Expressions.literal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// `assert` (6.3): the two forms render exactly, compile under `-Xlint:all`, and run as assertions -
/// checked with `-ea` and skipped without it.
class AssertCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Assertions");

    @Test
    void assertWithoutAndWithAMessageRendersCompilesAndRunsAsAnAssertion() throws Throwable {
        JavaFile file =
                TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var check = cb.declareVoidStaticMethod("check", PrimitiveToken.BOOLEAN);
                        var guarded = cb.declareStaticMethod(
                                "guarded", PrimitiveToken.INT, PrimitiveToken.INT, String_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.define(check, (b, flag) -> b.assert_(flag).end());
                        bodies.define(
                                guarded,
                                (b, value, message) -> b.assert_(gtInt(value, literal(0)), message)
                                        .return_(value));
                        return bodies;
                    }
                });

        CompiledClasses enabled = CompiledClasses.of(file, true);
        CompiledClasses disabled = CompiledClasses.of(file, false);

        assertThat(enabled.source()).contains("assert v0;").contains("assert v0 > 0 : v1;");

        assertThatThrownBy(() -> enabled.invoke("check", false)).isInstanceOf(AssertionError.class);
        assertThatThrownBy(() -> enabled.invoke("guarded", -1, "positive"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("positive");
        assertThat(enabled.invoke("guarded", 1, "positive")).isEqualTo(1);

        assertThatCode(() -> disabled.invoke("check", false)).doesNotThrowAnyException();
        assertThat(disabled.invoke("guarded", -1, "positive")).isEqualTo(-1);
    }
}
