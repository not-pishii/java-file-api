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
    void rejectsACharacterTheCompilerIgnoresInAnIdentifier() {
        // javac drops an ignorable character (`Character.isIdentifierIgnorable`): `a\u0000b` is the name `ab`
        // in the class file, so two such names of one type are one name
        assertThatThrownBy(() -> Identifiers.requireValid("a\u0000b"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("not a valid Java identifier: 'a\u0000b'");
        assertThatThrownBy(() -> Identifiers.requireValid("a\u200db"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a valid Java identifier");
        assertThatThrownBy(() -> Identifiers.requireValid("ab\u00ad"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a valid Java identifier");
        assertThatThrownBy(() -> Identifiers.requireValid("\u0000a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a valid Java identifier");
    }

    @Test
    void acceptsAndRejectsCharactersOutsideTheBasicPlaneAsWhole() {
        // U+1D4D0, a letter of two chars, and U+1F600, which is none
        assertThat(Identifiers.requireValid("\ud835\udcd0x")).isEqualTo("\ud835\udcd0x");
        assertThat(Identifiers.requireValid("x\ud835\udcd0")).isEqualTo("x\ud835\udcd0");
        assertThatThrownBy(() -> Identifiers.requireValid("x\ud83d\ude00"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a valid Java identifier");
        assertThatThrownBy(() -> Identifiers.requireValid("x\ud835"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a valid Java identifier");
    }

    @Test
    void aTypeIdentifierIsAnIdentifierThatIsNoContextualKeywordOfTypes() {
        assertThat(Identifiers.requireTypeIdentifier("Counter")).isEqualTo("Counter");
        // contextual keywords that do name a type (JLS 3.8 excludes five and no more)
        assertThat(Identifiers.requireTypeIdentifier("module")).isEqualTo("module");
        assertThat(Identifiers.requireTypeIdentifier("when")).isEqualTo("when");
        for (String name : List.of("permits", "record", "sealed", "var", "yield")) {
            assertThatThrownBy(() -> Identifiers.requireTypeIdentifier(name))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("not a valid Java type identifier: '" + name + "'");
        }
        assertThatThrownBy(() -> Identifiers.requireTypeIdentifier("class"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("not a valid Java identifier: 'class'");
    }

    @Test
    void tellsWhetherANameIsAnIdentifier() {
        assertThat(Identifiers.isValid("counter")).isTrue();
        assertThat(Identifiers.isValid("toString-impl")).isFalse();
        assertThat(Identifiers.isValid("")).isFalse();
        assertThat(Identifiers.isValid("class")).isFalse();
        assertThat(Identifiers.isValid("1a")).isFalse();
    }

    @Test
    void rejectsEmptyString() {
        assertThatThrownBy(() -> Identifiers.requireValid(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a valid Java identifier: ''");
    }

    @Test
    void rejectsLeadingDigit() {
        assertThatThrownBy(() -> Identifiers.requireValid("1foo"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a valid Java identifier: '1foo'");
    }

    @Test
    void rejectsEmbeddedSpace() {
        assertThatThrownBy(() -> Identifiers.requireValid("foo bar"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a valid Java identifier: 'foo bar'");
    }

    @Test
    void rejectsReservedKeyword() {
        assertThatThrownBy(() -> Identifiers.requireValid("class"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a valid Java identifier: 'class'");
        assertThatThrownBy(() -> Identifiers.requireValid("this"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a valid Java identifier");
        assertThatThrownBy(() -> Identifiers.requireValid("true"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a valid Java identifier");
        assertThatThrownBy(() -> Identifiers.requireValid("null"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a valid Java identifier");
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
