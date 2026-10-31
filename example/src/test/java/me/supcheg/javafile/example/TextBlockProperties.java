package me.supcheg.javafile.example;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.type.Types;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.List;

import static me.supcheg.javafile.code.Exprs.textBlock;
import static org.assertj.core.api.Assertions.assertThat;

class TextBlockProperties {

    /// How many values one class of the property holds: javac runs once for all of them.
    private static final int VALUES_PER_CLASS = 20;

    @Property(tries = 300 / VALUES_PER_CLASS)
    void renderedTextBlocksEvaluateToTheirValues(@ForAll("batchesOfTextBlockValues") List<String> values)
            throws Exception {
        assertThat(evaluateAll(values)).isEqualTo(values);
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

    @Example
    void lineTerminatorsOfTheWhiteSpaceCheckAreEscapedInAStringLiteral() throws Throwable {
        List<String> values = List.of("\u2028", "\u2029", "\u0085", "one\u2028two");
        JavaFile file = JavaFile.class_(ClassDesc.of("me.supcheg.example", "LineTerminatorProbe"), cb -> {
            for (int i = 0; i < values.size(); i++) {
                String value = values.get(i);
                cb.withMethod(
                        "value" + i,
                        Types.STRING,
                        mb -> mb.withModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .withBody(b -> b.return_(textBlock(value))));
            }
        });

        CompiledClasses compiled = CompiledClasses.of(file);
        assertThat(compiled.source())
                .contains(
                        "return \"\\u2028\";",
                        "return \"\\u2029\";",
                        "return \"\\u0085\";",
                        "return \"one\\u2028two\";");
        for (int i = 0; i < values.size(); i++) {
            assertThat(compiled.invoke("value" + i)).isEqualTo(values.get(i));
        }
    }

    @Provide
    Arbitrary<String> textBlockValues() {
        Arbitrary<String> tricky = Arbitraries.strings()
                .withChars(
                        '\\', '"', '\n', '\r', '\t', ' ', '\f', '\b', '\u000b', '\u0000', '\u001f', '\u0085', '\u3000',
                        '\u2028', '\u2029')
                .withChars('a', 'u', '0', '4', '1', 's')
                .ofMaxLength(40);
        return Arbitraries.oneOf(tricky, Arbitraries.strings().ofMaxLength(40));
    }

    @Provide
    Arbitrary<List<String>> batchesOfTextBlockValues() {
        return textBlockValues().list().ofSize(VALUES_PER_CLASS);
    }

    private static String evaluate(String value) throws Exception {
        return evaluateAll(List.of(value)).getFirst();
    }

    /// What the text blocks of `values` evaluate to, in a class that holds a method for each.
    private static List<String> evaluateAll(List<String> values) throws Exception {
        JavaFile file = JavaFile.class_(ClassDesc.of("me.supcheg.example", "TextBlockProbe"), cb -> {
            for (int i = 0; i < values.size(); i++) {
                String value = values.get(i);
                cb.withMethod(
                        "value" + i,
                        Types.STRING,
                        mb -> mb.withModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                .withBody(b -> b.return_(textBlock(value))));
            }
        });

        Class<?> probeClass = CompiledClasses.of(file).generated();
        List<String> evaluated = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            evaluated.add((String) probeClass.getMethod("value" + i).invoke(null));
        }
        return evaluated;
    }
}
