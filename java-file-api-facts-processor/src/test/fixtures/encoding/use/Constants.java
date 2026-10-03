import gen.facts.p.Text_;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim;
import p.Text;

import static org.assertj.core.api.Assertions.assertThat;

/// The source of a metamodel is ASCII (`expected/` is, and `SnapshotsTest` holds every generated source to it), so
/// the constants and names of a type are what they are whatever `-encoding` the metamodel is compiled with, and a
/// surrogate without its pair, which no encoding writes, is kept.
public final class Constants {
    private Constants() {}

    private static final char HIGH = (char) 0xD800;
    private static final char LOW = (char) 0xDC00;

    public static void aStringConstantIsExactlyItsValue() {
        assertThat(Text_.LONE_HIGH.constantValue()).contains("a" + HIGH + "b");
        assertThat(Text_.LONE_LOW.constantValue()).contains("" + LOW);
        assertThat(Text_.PAIR.constantValue()).contains(new String(Character.toChars(0x1F600)));
        assertThat(Text_.CYRILLIC.constantValue())
                .contains("" + (char) 0x43f + (char) 0x440 + (char) 0x438 + (char) 0x432 + (char) 0x435 + (char) 0x442
                        + " " + (char) 0xe9 + (char) 0x7f);
        assertThat(Text_.ESCAPES.constantValue())
                .contains((char) 0x5c + "u0041 " + (char) 0x5c + (char) 0xe9 + " \"" + (char) 0xe9 + "\"");
    }

    public static void aCharConstantIsExactlyItsValue() {
        assertThat(Text_.CHAR.constantValue()).contains((char) 0x44f);
        assertThat(Text_.LONE_CHAR.constantValue()).contains(HIGH);
        assertThat(Text_.DELETE.constantValue()).contains((char) 0x7f);
    }

    /// The name is the one of the member, whatever the name of the fact is written as.
    public static void aNameThatIsNotAsciiIsKept() {
        MutableFieldRef<Text, Prim.Int> number = Text_.число;
        String name = "" + (char) 0x447 + (char) 0x438 + (char) 0x441 + (char) 0x43b + (char) 0x43e;

        assertThat(number.name()).isEqualTo(name);
    }
}
