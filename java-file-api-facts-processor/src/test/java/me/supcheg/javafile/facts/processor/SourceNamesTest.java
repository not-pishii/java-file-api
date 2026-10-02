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
            @GeneratedMetamodel(of = Thing.class, fingerprint = "class Thing_ { hidden }", complete = true, format = 2)
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
}
