package me.supcheg.javafile;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.Chars;
import net.jqwik.api.constraints.StringLength;

import static org.assertj.core.api.Assertions.assertThat;

class IdentifiersProperties {

    @Property
    void anyStringOfLettersStartingWithLetterIsAccepted(
            @ForAll @StringLength(min = 1, max = 20) @Chars({'a', 'b', 'c', 'x', 'y', 'z'}) String suffix) {
        String candidate = "a" + suffix;
        assertThat(Identifiers.requireValid(candidate)).isEqualTo(candidate);
    }
}
