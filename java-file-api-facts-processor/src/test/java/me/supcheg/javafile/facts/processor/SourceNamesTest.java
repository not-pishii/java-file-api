package me.supcheg.javafile.facts.processor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/// [SourceNames]: the names the body of a rendered class starts a name with.
class SourceNamesTest {
    private static final String SOURCE = """
            package gen.facts.p;

            import java.util.List;
            import me.supcheg.javafile.facts.UnsafeFacts;

            @Generated("x.y.Processor")
            @GeneratedMetamodel(of = Thing.class, fingerprint = "class Thing_ { hidden }", complete = true, format = 4)
            final class Thing_<E extends Bound> {
                public static final StaticFieldRef<String> NAME =
                        UnsafeFacts.constantField(TOKEN, "a \\" quoted.name \\\\", Other_.Data.SHAPE, 1.5e3, 0x1FL);

                static final char C = (char) 39 + '\\'' + '.';

                static final class Data {
                    static final Object SHAPE = java.util.List.of(() -> Canonical.TEXT, p.Data.class);
                }
            }
            """;

    @Test
    void theNamesAreThoseThatDoNotFollowADotInTheBody() {
        assertThat(SourceNames.inBodyOf("Thing_", SOURCE))
                .containsExactlyInAnyOrder(
                        "public",
                        "static",
                        "final",
                        "StaticFieldRef",
                        "String",
                        "NAME",
                        "UnsafeFacts",
                        "TOKEN",
                        "Other_",
                        "char",
                        "C",
                        "class",
                        "Data",
                        "Object",
                        "SHAPE",
                        "java",
                        "Canonical",
                        "p");
    }

    @Test
    void whatIsBeforeTheBodyDoesNotCount() {
        assertThat(SourceNames.inBodyOf("Thing_", SOURCE))
                .doesNotContain("gen", "me", "import", "of", "fingerprint", "complete", "Thing", "E", "Bound")
                .doesNotContain("quoted", "hidden", "e3", "L", "x1FL");
    }

    @Test
    void aSourceWithoutTheClassIsRejected() {
        assertThatThrownBy(() -> SourceNames.inBodyOf("Other_", SOURCE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("no body of class Other_");
    }

    /// The comments of a metamodel tell of its type in prose: a word of them is no name, and
    /// `class Thing_ {` in one does not start the body.
    @Test
    void aWordOfACommentIsNoName() {
        String source = """
                package gen.facts.p;

                /// The full metamodel of [Thing]: class Thing_ { is what follows, like String_.
                final class Thing_ {
                    /// The fact of [Thing#size()], declared in `p.Hidden`, which is not `public`.
                    static final int SIZE = 1; // trailing words
                    /* a block
                       of words */ static final int COUNT = 4 / 2;
                    /** traditional {@link Other} */
                    static final String SLASHES = "// no comment /* either */";
                }
                """;

        assertThat(SourceNames.inBodyOf("Thing_", source))
                .containsExactlyInAnyOrder("static", "final", "int", "SIZE", "COUNT", "String", "SLASHES");
    }

    /// The canonical form of a type is a text block: a word of it is no name, whatever quotes and
    /// escapes it has.
    @Test
    void aWordOfATextBlockIsNoName() {
        String source = "final class Thing_ { static final String TEXT = \"\"\"\n"
                + "        member field HID = \"near\"\n"
                + "        table near(); far() \\\"\"\" still.inside\n"
                + "        \"\"\"; int after; }";

        assertThat(SourceNames.inBodyOf("Thing_", source))
                .containsExactlyInAnyOrder("static", "final", "String", "TEXT", "int", "after");
    }

    @Test
    void aCommentThatDoesNotEndGoesOnToTheEndOfTheSource() {
        assertThat(SourceNames.inBodyOf("Thing_", "final class Thing_ { int a; /* words"))
                .containsExactlyInAnyOrder("int", "a");
        assertThat(SourceNames.inBodyOf("Thing_", "final class Thing_ { int a; // words"))
                .containsExactlyInAnyOrder("int", "a");
    }
}
