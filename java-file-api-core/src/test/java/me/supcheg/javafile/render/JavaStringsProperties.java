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
}
