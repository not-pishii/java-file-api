package me.supcheg.javafile.render;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import static org.assertj.core.api.Assertions.assertThat;

class JavaStringsProperties {

    @Property
    void escapedOutputContainsNoBareBackslashOrQuote(@ForAll String raw) {
        String escaped = JavaStrings.escape(raw);

        String withoutEscapedPairs = escaped.replace("\\\\", "").replace("\\\"", "");
        assertThat(withoutEscapedPairs).doesNotContain("\"");
    }

    @Property
    void textBlockLinesHaveNoUnescapedTripleQuoteNoRawLineTerminatorAndNoTrailingWhiteSpace(@ForAll String value) {
        for (String line : JavaStrings.textBlockLines(value)) {
            assertThat(line).doesNotContain("\n", "\r");
            assertThat(line.replace("\\\\", "").replace("\\\"", "")).doesNotContain("\"\"\"");
            if (!line.isEmpty()) {
                assertThat(Character.isWhitespace(line.charAt(line.length() - 1)))
                        .isFalse();
            }
        }
    }
}
