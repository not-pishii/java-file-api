package me.supcheg.javafile;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IdentifiersTest {

    @Test
    void acceptsValidIdentifier() {
        assertThat(Identifiers.requireValid("counter")).isEqualTo("counter");
        assertThat(Identifiers.requireValid("_x1")).isEqualTo("_x1");
        assertThat(Identifiers.requireValid("$handle")).isEqualTo("$handle");
        assertThat(Identifiers.requireValid("var")).isEqualTo("var"); // contextual keyword, not reserved
    }

    @Test
    void rejectsEmptyString() {
        assertThatThrownBy(() -> Identifiers.requireValid("")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsLeadingDigit() {
        assertThatThrownBy(() -> Identifiers.requireValid("1foo")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsEmbeddedSpace() {
        assertThatThrownBy(() -> Identifiers.requireValid("foo bar")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsReservedKeyword() {
        assertThatThrownBy(() -> Identifiers.requireValid("class")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Identifiers.requireValid("this")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Identifiers.requireValid("true")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Identifiers.requireValid("null")).isInstanceOf(IllegalArgumentException.class);
    }
}
