package me.supcheg.javafile.facts.processor;

import com.google.testing.compile.Compilation;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.StaticFieldRef;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/// The source of a metamodel is ASCII, so the constants and names of a type
/// are what they are whatever `-encoding` the metamodel is compiled with,
/// and a surrogate without its pair, which no encoding writes, is kept.
class ConstantsEncodingTest extends FixtureSupport {
    private static final String LIBRARY = """
            package p;
            public class Text {
                public static final String LONE_HIGH = "a\\uD800b";
                public static final String LONE_LOW = "\\uDC00";
                public static final String PAIR = "\\uD83D\\uDE00";
                public static final String CYRILLIC = "\\u043f\\u0440\\u0438\\u0432\\u0435\\u0442 \\u00e9\\u007f";
                public static final String ESCAPES = "\\\\u0041 \\\\\\u00e9 \\"\\u00e9\\"";
                public static final char CHAR = '\\u044f';
                public static final char LONE_CHAR = '\\uD800';
                public static final char DELETE = '\\u007f';
                public int \\u0447\\u0438\\u0441\\u043b\\u043e;
            }
            """;

    private static final char HIGH = (char) 0xD800;
    private static final char LOW = (char) 0xDC00;

    private static Object constant(ClassLoader loader, String field) throws ReflectiveOperationException {
        return ((StaticFieldRef<?>) fact(loader, "gen.facts.p.Text_", field))
                .constantValue()
                .orElseThrow();
    }

    @Test
    void theSourceOfAMetamodelIsAscii() {
        Compilation compilation = generate("p.Text.class", LIBRARY);

        assertThat(sources(compilation))
                .allSatisfy((name, source) -> assertThat(
                                source.chars().filter(c -> c >= 0x7f).count())
                        .as(name)
                        .isZero());
    }

    @Test
    void aStringConstantIsExactlyItsValue() throws Exception {
        ClassLoader loader = load(generate("p.Text.class", LIBRARY));

        assertThat(constant(loader, "LONE_HIGH")).isEqualTo("a" + HIGH + "b");
        assertThat(constant(loader, "LONE_LOW")).isEqualTo("" + LOW);
        assertThat(constant(loader, "PAIR")).isEqualTo(new String(Character.toChars(0x1F600)));
        assertThat(constant(loader, "CYRILLIC"))
                .isEqualTo("" + (char) 0x43f + (char) 0x440 + (char) 0x438 + (char) 0x432 + (char) 0x435 + (char) 0x442
                        + " " + (char) 0xe9 + (char) 0x7f);
        assertThat(constant(loader, "ESCAPES"))
                .isEqualTo((char) 0x5c + "u0041 " + (char) 0x5c + (char) 0xe9 + " \"" + (char) 0xe9 + "\"");
    }

    @Test
    void aCharConstantIsExactlyItsValue() throws Exception {
        ClassLoader loader = load(generate("p.Text.class", LIBRARY));

        assertThat(constant(loader, "CHAR")).isEqualTo((char) 0x44f);
        assertThat(constant(loader, "LONE_CHAR")).isEqualTo(HIGH);
        assertThat(constant(loader, "DELETE")).isEqualTo((char) 0x7f);
    }

    @Test
    void aNameThatIsNotAsciiIsKept() throws Exception {
        ClassLoader loader = load(generate("p.Text.class", LIBRARY));
        String name = "" + (char) 0x447 + (char) 0x438 + (char) 0x441 + (char) 0x43b + (char) 0x43e;

        assertThat(((FieldRef<?, ?>) fact(loader, "gen.facts.p.Text_", name)).name())
                .isEqualTo(name);
    }
}
