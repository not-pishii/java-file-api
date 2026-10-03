import gen.facts.p.Greeter_;
import gen.facts.p.Iface_;
import me.supcheg.javafile.facts.FieldRef;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import p.Greeter;

import static org.assertj.core.api.Assertions.assertThat;

/// The facts of the fields: the family of a fact tells whether the field
/// is `static` and whether it is `final`, and javac checks it here.
public final class Fields {
    private Fields() {}

    public static void aStaticFinalFieldThatIsNoConstantHasNoValue() {
        StaticFieldRef<String> notConstant = Greeter_.NOT_CONSTANT;
        assertThat(notConstant.constantValue()).isEmpty();
        assertThat(notConstant.name()).isEqualTo("NOT_CONSTANT");
        assertThat(notConstant.type().typeRef()).isEqualTo(Types.STRING);

        StaticFieldRef<Object> object = Greeter_.OBJECT;
        assertThat(object.constantValue()).isEmpty();
        assertThat(object.type().typeRef()).isEqualTo(Types.OBJECT);
    }

    public static void aStaticFieldThatIsNotFinalIsMutable() {
        MutableStaticFieldRef<Prim.Long> counter = Greeter_.counter;
        assertThat(counter.name()).isEqualTo("counter");
        assertThat(counter.type()).isSameAs(PrimitiveToken.LONG);

        MutableStaticFieldRef<String> label = Greeter_.label;
        assertThat(label.name()).isEqualTo("label");
    }

    public static void aFinalFieldIsNotMutable() {
        FieldRef<Greeter, Prim.Int> count = Greeter_.count;
        assertThat(count).isNotInstanceOf(MutableFieldRef.class);
        assertThat(count.type()).isSameAs(PrimitiveToken.INT);
        assertThat(count.owner()).isEqualTo(Greeter_.TOKEN);

        FieldRef<Greeter, String> name = Greeter_.name;
        assertThat(name).isNotInstanceOf(MutableFieldRef.class);
    }

    public static void aFieldThatIsNotFinalIsMutable() {
        MutableFieldRef<Greeter, Prim.Int> mutable = Greeter_.mutable;
        MutableFieldRef<Greeter, String> text = Greeter_.text;
        MutableFieldRef<Greeter, int[]> numbers = Greeter_.numbers;
        MutableFieldRef<Greeter, String[][]> grid = Greeter_.grid;

        assertThat(mutable.name()).isEqualTo("mutable");
        assertThat(text.name()).isEqualTo("text");
        assertThat(numbers.type().typeRef()).isEqualTo(Types.array(PrimitiveTypeRef.INT));
        assertThat(grid.type().typeRef()).isEqualTo(Types.array(Types.array(Types.STRING)));
    }

    public static void aConstantHoldsItsValueExactly() {
        assertThat(Greeter_.DEFAULT.constantValue()).contains("say \"hi\"\n\t\\ é\u0001");
        assertThat(Greeter_.INT.constantValue()).contains(Integer.MIN_VALUE);
        assertThat(Greeter_.LONG.constantValue()).contains(9007199254740993L);
        assertThat(Greeter_.SHORT.constantValue()).contains((short) -3);
        assertThat(Greeter_.BYTE.constantValue()).contains((byte) 127);
        assertThat(Greeter_.CHAR.constantValue()).contains('\'');
        assertThat(Greeter_.BOOL.constantValue()).contains(true);
        assertThat(Greeter_.FLOAT.constantValue()).contains(1.1f);
        assertThat(Greeter_.DOUBLE.constantValue()).contains(0.1);
        assertThat(Greeter_.NAN.constantValue()).contains(Double.NaN);
        assertThat(Greeter_.INFINITY.constantValue()).contains(Double.POSITIVE_INFINITY);
        assertThat(Greeter_.NEGATIVE_INFINITY.constantValue()).contains(Double.NEGATIVE_INFINITY);
        assertThat(Greeter_.NEGATIVE_ZERO.constantValue()).contains(-0.0);
        assertThat(Greeter_.FLOAT_NAN.constantValue()).contains(Float.NaN);
        assertThat(Greeter_.FLOAT_INFINITY.constantValue()).contains(Float.POSITIVE_INFINITY);
    }

    public static void aConstantIsOfTheTypeOfItsField() {
        StaticFieldRef<Prim.Int> int_ = Greeter_.INT;
        StaticFieldRef<Prim.Long> long_ = Greeter_.LONG;
        StaticFieldRef<Prim.Short> short_ = Greeter_.SHORT;
        StaticFieldRef<Prim.Byte> byte_ = Greeter_.BYTE;
        StaticFieldRef<Prim.Char> char_ = Greeter_.CHAR;
        StaticFieldRef<Prim.Bool> bool = Greeter_.BOOL;
        StaticFieldRef<Prim.Float> float_ = Greeter_.FLOAT;
        StaticFieldRef<Prim.Double> double_ = Greeter_.DOUBLE;
        StaticFieldRef<String> string = Greeter_.DEFAULT;

        assertThat(int_.type()).isSameAs(PrimitiveToken.INT);
        assertThat(long_.type()).isSameAs(PrimitiveToken.LONG);
        assertThat(short_.type()).isSameAs(PrimitiveToken.SHORT);
        assertThat(byte_.type()).isSameAs(PrimitiveToken.BYTE);
        assertThat(char_.type()).isSameAs(PrimitiveToken.CHAR);
        assertThat(bool.type()).isSameAs(PrimitiveToken.BOOLEAN);
        assertThat(float_.type()).isSameAs(PrimitiveToken.FLOAT);
        assertThat(double_.type()).isSameAs(PrimitiveToken.DOUBLE);
        assertThat(string.type().typeRef()).isEqualTo(Types.STRING);
    }

    public static void theConstantsOfAnInterfaceAreStaticFields() {
        StaticFieldRef<Prim.Int> limit = Iface_.LIMIT;
        StaticFieldRef<String> name = Iface_.NAME;

        assertThat(limit.constantValue()).contains(10);
        assertThat(name.constantValue()).contains("n");
    }
}
