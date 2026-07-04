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
    void escapesOtherControlCharactersAsUnicode() {
        assertThat(JavaStrings.escape("")).isEqualTo("\\u0001");
    }
}
