import gen.facts.java.lang.Comparable_;
import gen.facts.p.Box_;
import gen.facts.p.Canonical_;
import gen.facts.p.Data_;
import gen.facts.p.Named_;
import gen.facts.p.Raw_;
import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Box;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// The type parameters of a type, their bounds and their names, in the shape and the tokens of its metamodel (the
/// declarations of the metamodel are in `expected/`).
public final class TypeParameters {
    private TypeParameters() {}

    public static void theBoundsOfTypeParametersAreInTheShape() {
        assertThat(Box_.Data.SHAPE.typeParameters())
                .containsExactly(new TypeParam(
                        "T", List.of(Types.parameterized(ClassDesc.of("java.lang.Comparable"), Types.typeVar("T")))));
    }

    public static void aTokenOfAParameterizationIsMadeFromTokensOfTheTypeArguments() {
        Box_<Integer> ofIntegers = new Box_<>(PrimitiveToken.INT.boxed());

        assertThat(ofIntegers.token.typeRef())
                .isEqualTo(Types.parameterized(ClassDesc.of("p.Box"), Types.of(ConstantDescs.CD_Integer)));
    }

    public static void aBoundThatIsAMentionedTypeHasItsMetamodel() {
        InterfaceToken<Comparable<?>> any = Comparable_.ANY;

        assertThat(any).isNotNull();
    }

    /// `Raw<T extends Hidden>`, `Hidden` not public: the metamodel is not generic, and the shape has the bound.
    public static void aBoundMentioningATypeThatIsNotPublicMakesTheTokenRaw() {
        assertThat(Raw_.TOKEN.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Raw")));
        assertThat(Raw_.Data.SHAPE.typeParameters())
                .containsExactly(new TypeParam("T", List.of(Types.of(ClassDesc.of("p.Hidden")))));
    }

    public static void aTypeParameterNamedLikeAClassTheMetamodelUsesKeepsItsNameInTheShape() {
        assertThat(Named_.Data.SHAPE.typeParameters())
                .extracting(TypeParam::name)
                .containsExactly("Data", "String", "Int");
    }

    public static void aTypeNamedLikeANestedClassOfTheMetamodelIsTheTypeOfItsMetamodel() {
        assertThat(Data_.TOKEN.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Data")));
        assertThat(Canonical_.Data.SHAPE.typeParameters()).extracting(TypeParam::name).containsExactly("Data");
    }
}
