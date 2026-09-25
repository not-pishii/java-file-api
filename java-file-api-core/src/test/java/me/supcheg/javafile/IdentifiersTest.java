package me.supcheg.javafile;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void acceptsListOfValidIdentifiers() {
        List<String> names = new ArrayList<>(List.of("a", "b"));

        List<String> result = Identifiers.requireValid(names);

        assertThat(result).containsExactly("a", "b");
        names.add("c");
        assertThat(result).containsExactly("a", "b");
    }

    @Test
    void rejectsListWithInvalidIdentifiersReportingEachAsSuppressed() {
        assertThatThrownBy(() -> Identifiers.requireValid(List.of("ok", "1bad", "class")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid Java identifier")
                .satisfies(e -> assertThat(e.getSuppressed()).hasSize(2));
    }
}
