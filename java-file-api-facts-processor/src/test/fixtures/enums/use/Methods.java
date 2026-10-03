import gen.facts.p.Day_;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTable;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// The methods of an enum without constant bodies, declared and implicit, and its method table.
public final class Methods {
    private Methods() {}

    public static void valuesIsAStaticMethodOfTheArrayOfTheEnum() {
        assertThat(Day_.values.resultType().orElseThrow().typeRef()).isEqualTo(Types.array(Types.of(ClassDesc.of("p.Day"))));
        assertThat(Day_.values.traits()).isEqualTo(MemberTraits.FINAL);
    }

    public static void valueOfIsAFinalStaticMethodOfAString() {
        assertThat(Day_.valueOf_String.traits()).isEqualTo(MemberTraits.FINAL);
        assertThat(Day_.valueOf_String.params().get(0).typeRef()).isEqualTo(Types.STRING);
    }

    public static void aMethodOfAnEnumWithoutConstantBodiesIsFinal() {
        assertThat(Day_.next.traits()).isEqualTo(MemberTraits.FINAL);
        assertThat(Day_.next.resultType().orElseThrow()).isEqualTo(Day_.TOKEN);
    }

    public static void aStaticMethodHasItsParametersAndItsResult() {
        assertThat(Day_.of_int.params().get(0)).isSameAs(PrimitiveToken.INT);
        assertThat(Day_.of_int.resultType().orElseThrow()).isEqualTo(Day_.TOKEN);
    }

    /// `ordinal()` is not a fact of `Day_`, but the decision between overloads needs it.
    public static void theMethodTableHasEveryMethodTheEnumInherits() {
        MethodTable methods = Day_.TOKEN.methods();

        assertThat(methods.concreteMethods())
                .contains(
                        new MethodSignature("ordinal", List.of()),
                        new MethodSignature("name", List.of()),
                        new MethodSignature("next", List.of()),
                        new MethodSignature("compareTo", List.of(ClassDesc.of("p.Day"))));
        assertThat(methods.staticMethods())
                .contains(
                        new MethodSignature("values", List.of()),
                        new MethodSignature("valueOf", List.of(ConstantDescs.CD_String)),
                        new MethodSignature("of", List.of(ConstantDescs.CD_int)),
                        new MethodSignature("valueOf", List.of(ConstantDescs.CD_Class, ConstantDescs.CD_String)));
    }
}
