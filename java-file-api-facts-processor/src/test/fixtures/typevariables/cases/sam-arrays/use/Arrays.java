import gen.facts.p.Arr_;
import gen.facts.p.Grid_;
import gen.facts.p.Item_;
import gen.facts.p.Spread_;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.MethodTableTemplate.Signature;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.VoidSam1;
import me.supcheg.javafile.type.Types;
import p.Arr;
import p.Grid;
import p.Item;
import p.Spread;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// An array of a type parameter in a signature is an array of the type argument in the method table of the
/// instance: the `sam` of a generic interface takes and returns arrays of its type parameters.
public final class Arrays {
    private Arrays() {}

    private static final ClassDesc CD_ITEM = ClassDesc.of("p.Item");

    /// The method of the fact is the abstract method of the table of `Arr<Item>`: `m(Item[])`.
    public static void theSamOfAGenericInterfaceTakesAndReturnsArraysOfItsTypeParameter() {
        Arr_<Item> arr = new Arr_<>(Item_.TOKEN);
        Sam1<Arr<Item>, Item[], Item[]> sam = arr.sam;

        assertThat(sam.method()).isSameAs(arr.m_TArray);
        assertThat(sam.method().declared()).isEqualTo(Signature.of("m", Param.var(0, 1)));
        assertThat(sam.method().signature()).isEqualTo(new MethodSignature("m", List.of(CD_ITEM.arrayType())));
        assertThat(sam.result().typeRef()).isEqualTo(Types.array(Types.of(CD_ITEM)));
        assertThat(sam.owner().methods().abstractMethods())
                .containsExactly(sam.method().signature());
    }

    public static void theShapeOfTheMetamodelHasTheArrayByTheTypeParametersPosition() {
        assertThat(Arr_.Data.SHAPE.methods().abstractMethods()).containsExactly(Signature.of("m", Param.var(0, 1)));
    }

    /// A variable-arity parameter is its array type.
    public static void aVariableArityParameterIsItsArray() {
        Spread_<Item> spread = new Spread_<>(Item_.TOKEN);
        VoidSam1<Spread<Item>, Item[]> accept = spread.sam;

        assertThat(accept.method().signature())
                .isEqualTo(new MethodSignature("accept", List.of(CD_ITEM.arrayType())));
    }

    /// Each type parameter by its own position, under its own dimensions.
    public static void eachTypeParameterIsByItsOwnPositionUnderItsOwnDimensions() {
        Grid_<Item, Item> grid = new Grid_<>(Item_.TOKEN, Item_.TOKEN);
        Sam1<Grid<Item, Item>, Item[], Item[][]> row = grid.sam;

        assertThat(row.method().declared()).isEqualTo(Signature.of("row", Param.var(1, 2)));
        assertThat(row.method().signature()).isEqualTo(new MethodSignature("row", List.of(CD_ITEM.arrayType(2))));
    }
}
