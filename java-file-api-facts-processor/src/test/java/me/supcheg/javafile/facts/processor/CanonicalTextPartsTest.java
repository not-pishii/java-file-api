package me.supcheg.javafile.facts.processor;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// The canonical form in a metamodel is cut into string constants that each
/// fit the 65535 bytes of modified UTF-8 a class file allows, even where a
/// single line of it does not. (A metamodel of a class with such a line is
/// compiled in `GeneratedCodeTest`.)
class CanonicalTextPartsTest {
    private static final String CYRILLIC = String.valueOf((char) 0x44f);
    private static final String CJK = String.valueOf((char) 0x4e2d);
    private static final String PAIR = new String(Character.toChars(0x1F600));
    private static final String NUL = String.valueOf((char) 0);

    /// The bytes a class file holds a string constant in.
    private static int modifiedUtf8(String text) {
        int total = 0;
        for (int i = 0; i < text.length(); i += 1000) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(bytes)) {
                out.writeUTF(text.substring(i, Math.min(text.length(), i + 1000)));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            // writeUTF puts two bytes of length first
            total += bytes.size() - 2;
        }
        return total;
    }

    @Test
    void shortLinesAreKeptWholeAndJoinedIntoParts() {
        assertThat(MetamodelEmitter.parts("ab\ncd\nef\n", 6)).containsExactly("ab\ncd\n", "ef\n");
        assertThat(MetamodelEmitter.parts("ab\ncd\n", 100)).containsExactly("ab\ncd\n");
        assertThat(MetamodelEmitter.parts("ab\ncd", 3)).containsExactly("ab\n", "cd");
        assertThat(MetamodelEmitter.parts("", 10)).containsExactly("");
    }

    @Test
    void aLineLongerThanAPartIsCut() {
        assertThat(MetamodelEmitter.parts("a\nbcdefgh\ni\n", 3)).containsExactly("a\n", "bcd", "efg", "h\n", "i\n");
        assertThat(MetamodelEmitter.parts("abcdefg", 3)).containsExactly("abc", "def", "g");
    }

    @Test
    void thePartsAreMeasuredInBytesOfModifiedUtf8() {
        // two bytes each, three bytes each, and the two bytes of NUL
        assertThat(MetamodelEmitter.parts(CYRILLIC.repeat(5), 6))
                .containsExactly(CYRILLIC.repeat(3), CYRILLIC.repeat(2));
        assertThat(MetamodelEmitter.parts(CJK.repeat(5), 6)).containsExactly(CJK.repeat(2), CJK.repeat(2), CJK);
        assertThat(MetamodelEmitter.parts(NUL.repeat(4), 6)).containsExactly(NUL.repeat(3), NUL);
        // a surrogate pair is six bytes and is not cut
        assertThat(MetamodelEmitter.parts("ab" + PAIR + PAIR + "c", 6)).containsExactly("ab", PAIR, PAIR, "c");
    }

    @Test
    void everyPartFitsWhateverTheText() {
        String line = ("x" + CYRILLIC + CJK + PAIR + NUL).repeat(7000);
        String text = "short\n" + line + "\n" + line + "tail\nlast\n";

        List<String> parts = MetamodelEmitter.parts(text, MetamodelEmitter.TEXT_PART);

        assertThat(String.join("", parts)).isEqualTo(text);
        assertThat(parts)
                .allSatisfy(part ->
                        assertThat(modifiedUtf8(part)).isPositive().isLessThanOrEqualTo(MetamodelEmitter.TEXT_PART));
    }
}
