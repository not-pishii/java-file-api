import gen.facts.p.Day_;
import me.supcheg.javafile.facts.EnumConstant;
import me.supcheg.javafile.facts.EnumToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import p.Day;

import static org.assertj.core.api.Assertions.assertThat;

/// The constants of an enum are facts of their own, and the enum is an `EnumToken`.
public final class Constants {
    private Constants() {}

    public static void anEnumTokenAndItsShapeNameTheConstantsInOrder() {
        EnumToken<Day> token = Day_.TOKEN;

        assertThat(token.constants()).containsExactly("MON", "TUE", "WED");
        assertThat(Day_.Data.SHAPE.enumConstants()).containsExactly("MON", "TUE", "WED");
    }

    public static void aConstantIsAFactOfItsOwner() {
        EnumConstant<Day> mon = Day_.MON;
        EnumConstant<Day> tue = Day_.TUE;
        EnumConstant<Day> wed = Day_.WED;

        assertThat(mon.name()).isEqualTo("MON");
        assertThat(tue.name()).isEqualTo("TUE");
        assertThat(wed.name()).isEqualTo("WED");
        assertThat(mon.owner()).isSameAs(Day_.TOKEN);
        assertThat(tue.owner()).isSameAs(Day_.TOKEN);
        assertThat(wed.owner()).isSameAs(Day_.TOKEN);
    }

    public static void aStaticFinalFieldOfAnEnumIsAConstantFact() {
        StaticFieldRef<Prim.Int> count = Day_.COUNT;

        assertThat(count.constantValue()).contains(3);
        assertThat(count.type()).isSameAs(PrimitiveToken.INT);
    }
}
