package me.supcheg.javafile.render;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JavaStringsSurrogateTest {
    private static final char HIGH = (char) 0xD83D;
    private static final char LOW = (char) 0xDE00;
    private static final String BACKSLASH = String.valueOf((char) 0x5C);

    @Test
    void aLoneHighSurrogateIsAUnicodeEscape() {
        assertThat(JavaStrings.escape("a" + HIGH + "b")).isEqualTo("a" + BACKSLASH + "ud83db");
    }

    @Test
    void aLoneLowSurrogateIsAUnicodeEscape() {
        assertThat(JavaStrings.escape("" + LOW)).isEqualTo(BACKSLASH + "ude00");
    }

    @Test
    void surrogatesInTheWrongOrderAreBothEscaped() {
        assertThat(JavaStrings.escape("" + LOW + HIGH)).isEqualTo(BACKSLASH + "ude00" + BACKSLASH + "ud83d");
    }

    @Test
    void onlyTheUnpairedSurrogatesOfARunAreEscaped() {
        assertThat(JavaStrings.escape("" + HIGH + HIGH + LOW + LOW))
                .isEqualTo(BACKSLASH + "ud83d" + HIGH + LOW + BACKSLASH + "ude00");
    }

    @Test
    void aSurrogatePairIsUnchanged() {
        assertThat(JavaStrings.escape("" + HIGH + LOW)).isEqualTo("" + HIGH + LOW);
    }

    @Test
    void printableNonAsciiIsUnchanged() {
        String text = "" + (char) 0x43F + (char) 0x440 + (char) 0xE9 + (char) 0x4E2D + (char) 0x7F + (char) 0xA0;

        assertThat(JavaStrings.escape(text)).isEqualTo(text);
    }
}
