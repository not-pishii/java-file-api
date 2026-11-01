package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.textBlock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// Text blocks (6.1): a `String` expression rendered with `"""` delimiters, or as an escaped string
/// literal for the three characters the white space check of a text block splits on, compiled under
/// `-Xlint:all` and read back at run time as the value given.
class TextBlocksCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Blocks");

    private static final String MULTI_LINE = "line one\nline two\n";
    private static final String QUOTED = "he said \"hi\" and \\ left  \n";
    private static final String SEPARATORS = "a\u0085b\u2028c\u2029d";

    @Test
    void aMultiLineTextBlockCompilesAndHoldsItsValue() throws Throwable {
        CompiledClasses compiled = compileTextBlock(MULTI_LINE);

        assertThat(compiled.source()).contains("return \"\"\"\n        line one\n        line two\n        \"\"\";");
        assertThat(compiled.invoke("value")).isEqualTo(MULTI_LINE);
    }

    @Test
    void quotesBackslashesAndTrailingSpacesKeepTheirValue() throws Throwable {
        CompiledClasses compiled = compileTextBlock(QUOTED);

        assertThat(compiled.source()).contains("he said \"hi\" and \\\\ left \\s");
        assertThat(compiled.invoke("value")).isEqualTo(QUOTED);
    }

    @Test
    void theLineTerminatorsOfTheWhiteSpaceCheckBecomeAnEscapedStringLiteral() throws Throwable {
        CompiledClasses compiled = compileTextBlock(SEPARATORS);

        assertThat(compiled.source()).contains("return \"a\\u0085b\\u2028c\\u2029d\";");
        assertThat(compiled.invoke("value")).isEqualTo(SEPARATORS);
    }

    @Test
    void aTextBlockIsAConstantExpressionAsJavacFoldsIt() {
        assertThatThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                var loop = cb.declareVoidStaticMethod("loop");
                                var bodies = cb.bodies();
                                bodies.define(
                                        loop,
                                        b -> b.while_(eqRef(textBlock("a"), textBlock("b")), (body, _) -> body.end())
                                                .end());
                                return bodies;
                            }
                        }))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("unreachable");
    }

    private static CompiledClasses compileTextBlock(String value) {
        return CompiledClasses.of(TypedJavaFile.class_(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        var method = cb.declareStaticMethod("value", String_.TOKEN);
                        var bodies = cb.bodies();
                        bodies.define(method, b -> b.return_(textBlock(value)));
                        return bodies;
                    }
                }));
    }
}
