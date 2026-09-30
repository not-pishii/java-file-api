package me.supcheg.javafile.example;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.Types;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.code.Exprs.textBlock;
import static org.assertj.core.api.Assertions.assertThat;

class TextBlockProperties {

    @Property(tries = 300)
    void renderedTextBlockEvaluatesToItsValue(@ForAll("textBlockValues") String value) throws Exception {
        assertThat(evaluate(value)).isEqualTo(value);
    }

    @Example
    void backslashesStayBackslashes() throws Exception {
        String value = "C:\\new\\table\\x\n\\";
        assertThat(evaluate(value)).isEqualTo(value);
    }

    @Example
    void unicodeEscapeStaysVerbatim() throws Exception {
        String value = "\\u0041 \\\\u0042 \\u000a\n";
        assertThat(evaluate(value)).isEqualTo(value);
    }

    @Example
    void tripleQuotesDoNotCloseTheBlock() throws Exception {
        String value = "\"\"\" \"\"\"\"\"\"\"\n\"\"\"";
        assertThat(evaluate(value)).isEqualTo(value);
    }

    @Example
    void trailingSpacesAndBlankLinesAreKept() throws Exception {
        String value = "a  \n   \n\n\t\nb \u3000\n";
        assertThat(evaluate(value)).isEqualTo(value);
    }

    @Example
    void valueWithoutTrailingNewlineGetsNone() throws Exception {
        assertThat(evaluate("one\ntwo")).isEqualTo("one\ntwo");
    }

    @Example
    void valueWithTrailingNewlineGetsExactlyOne() throws Exception {
        assertThat(evaluate("one\ntwo\n")).isEqualTo("one\ntwo\n");
    }

    @Example
    void emptyValueStaysEmpty() throws Exception {
        assertThat(evaluate("")).isEmpty();
    }

    @Example
    void crLfIsKept() throws Exception {
        String value = "one\r\ntwo\r\n\r";
        assertThat(evaluate(value)).isEqualTo(value);
    }

    @Example
    void controlCharactersAreKept() throws Exception {
        String value = "\u0000\u00011\b\f\u000b\u001f\u007f";
        assertThat(evaluate(value)).isEqualTo(value);
    }

    @Provide
    Arbitrary<String> textBlockValues() {
        Arbitrary<String> tricky = Arbitraries.strings()
                .withChars('\\', '"', '\n', '\r', '\t', ' ', '\f', '\b', '\u000b', '\u0000', '\u001f', '\u3000')
                .withChars('a', 'u', '0', '4', '1', 's')
                .ofMaxLength(40);
        return Arbitraries.oneOf(tricky, Arbitraries.strings().ofMaxLength(40));
    }

    private static String evaluate(String value) throws Exception {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "TextBlockProbe"),
                cb -> cb.withMethod(
                        "value",
                        Types.STRING,
                        mb -> mb.withModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .withBody(b -> b.return_(textBlock(value)))));

        Class<?> probeClass = InMemoryCompiler.compileAndLoad(file.qualifiedName(), file.render());
        return (String) probeClass.getMethod("value").invoke(null);
    }
}
