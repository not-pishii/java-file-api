import gen.facts.java.lang.String_;
import gen.facts.p.Color_;
import gen.facts.p.Point_;
import me.supcheg.javafile.facts.processor.harness.Typed;
import p.Color;
import p.Point;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.staticField;
import static org.assertj.core.api.Assertions.assertThat;

/// An enum and a record that implement the hidden `HLabel` adopt its
/// default method and its constant, each for itself.
public final class EnumAndRecord {
    private EnumAndRecord() {}

    public static void anEnumAdoptsTheMembersOfAHiddenInterface(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Color_.TOKEN, color -> call(color, Color_.label), Color.RED))
                .isEqualTo("label");
        assertThat(typed.apply(String_.TOKEN, Color_.TOKEN, color -> staticField(Color_.NAME), Color.RED))
                .isEqualTo("name");
    }

    public static void aRecordAdoptsTheMembersOfAHiddenInterface(Typed typed) {
        assertThat(typed.apply(String_.TOKEN, Point_.TOKEN, point -> call(point, Point_.label), new Point(1, 2)))
                .isEqualTo("label");
        assertThat(typed.apply(String_.TOKEN, Point_.TOKEN, point -> staticField(Point_.NAME), new Point(1, 2)))
                .isEqualTo("name");
    }
}
