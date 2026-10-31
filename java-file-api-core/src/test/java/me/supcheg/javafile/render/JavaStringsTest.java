package me.supcheg.javafile.render;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JavaStringsTest {

    @Test
    void plainTextIsUnchanged() {
        assertThat(JavaStrings.escape("hello")).isEqualTo("hello");
    }

    @Test
    void escapesBackslashAndQuote() {
        assertThat(JavaStrings.escape("a\\b\"c")).isEqualTo("a\\\\b\\\"c");
    }

    @Test
    void escapesControlCharacters() {
        assertThat(JavaStrings.escape("a\nb\rc\td")).isEqualTo("a\\nb\\rc\\td");
    }

    @Test
    void escapesNextLineAndLineAndParagraphSeparatorsAsUnicode() {
        assertThat(JavaStrings.escape("a\u0085b\u2028c\u2029d")).isEqualTo("a\\u0085b\\u2028c\\u2029d");
    }

    @Test
    void textBlockFitsValuesWithoutTheLineTerminatorsOfTheWhiteSpaceCheck() {
        assertThat(JavaStrings.fitsTextBlock("")).isTrue();
        assertThat(JavaStrings.fitsTextBlock("a\nb\r\tc\u3000")).isTrue();
        assertThat(JavaStrings.fitsTextBlock("a\u0085b")).isFalse();
        assertThat(JavaStrings.fitsTextBlock("a\u2028b")).isFalse();
        assertThat(JavaStrings.fitsTextBlock("a\u2029b")).isFalse();
    }

    @Test
    void escapesOtherControlCharactersAsUnicode() {
        assertThat(JavaStrings.escape("")).isEqualTo("\\u0001");
    }

    @Test
    void textBlockLinesKeepPlainLinesAsIs() {
        assertThat(JavaStrings.textBlockLines("a\nb\n")).containsExactly("a", "b");
    }

    @Test
    void textBlockLinesEndWithLineContinuationWhenValueHasNoTrailingNewline() {
        assertThat(JavaStrings.textBlockLines("a\nb")).containsExactly("a", "b\\");
    }

    @Test
    void textBlockLinesOfEmptyValueAreASingleLineContinuation() {
        assertThat(JavaStrings.textBlockLines("")).containsExactly("\\");
    }

    @Test
    void textBlockLinesOfNewlineAreASingleEmptyLine() {
        assertThat(JavaStrings.textBlockLines("\n")).containsExactly("");
    }

    @Test
    void textBlockLinesEscapeBackslash() {
        assertThat(JavaStrings.textBlockLines("a\\b\n")).containsExactly("a\\\\b");
    }

    @Test
    void textBlockLinesEscapeBackslashOfUnicodeEscape() {
        assertThat(JavaStrings.textBlockLines("\\u0041\n")).containsExactly("\\\\u0041");
    }

    @Test
    void textBlockLinesEscapeEveryThirdQuoteOfARun() {
        assertThat(JavaStrings.textBlockLines("\"\" \"\"\" \"\"\"\"\"\"\"\n"))
                .containsExactly("\"\" \"\"\\\" \"\"\\\"\"\"\\\"\"");
    }

    @Test
    void textBlockLinesKeepTrailingSpacesWithSpaceEscape() {
        assertThat(JavaStrings.textBlockLines("a  \n   \n")).containsExactly("a \\s", "  \\s");
    }

    @Test
    void textBlockLinesKeepTrailingSpacesOfLastLineBeforeLineContinuation() {
        assertThat(JavaStrings.textBlockLines("a  ")).containsExactly("a  \\");
    }

    @Test
    void textBlockLinesKeepOtherTrailingWhiteSpaceWithNewlineEscapeAndLineContinuation() {
        assertThat(JavaStrings.textBlockLines("a\u3000\nb\n")).containsExactly("a\u3000\\n\\", "b");
    }

    @Test
    void textBlockLinesEscapeCarriageReturnOfCrLf() {
        assertThat(JavaStrings.textBlockLines("a\r\nb\r\n")).containsExactly("a\\r", "b\\r");
    }

    @Test
    void textBlockLinesEscapeControlCharactersAsThreeDigitOctal() {
        assertThat(JavaStrings.textBlockLines("\t\b\f\u0000\u001f1\n")).containsExactly("\\t\\b\\f\\000\\0371");
    }
}
