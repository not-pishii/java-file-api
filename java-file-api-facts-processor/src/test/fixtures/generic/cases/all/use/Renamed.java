import gen.facts.java.lang.Integer_;
import gen.facts.java.lang.Object_;
import gen.facts.java.lang.String_;
import gen.facts.p.Names_;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import p.Names;

import java.lang.constant.ConstantDescs;

import static org.assertj.core.api.Assertions.assertThat;

/// `Names<List, UnsafeFacts, Token>` names its type parameters like what
/// its metamodel uses: the metamodel renames them — see `Names_.java` in
/// `expected/` — and the facts are of the members all the same.
public final class Renamed {
    private Renamed() {}

    public static void theShapeKeepsTheNamesTheTypeDeclares() {
        assertThat(Names_.Data.SHAPE.typeParameters())
                .extracting(TypeParam::name)
                .containsExactly("List", "UnsafeFacts", "Token");
    }

    /// The field `token` is the fact `token_`, as `token` is the token of the instance.
    public static void theFactsOfTheRenamedParametersAreInTermsOfTheTokens() {
        Names_<String, Integer, Object> names = new Names_<>(String_.TOKEN, Integer_.TOKEN, Object_.TOKEN);
        MutableFieldRef<Names<String, Integer, Object>, String> first = names.first;
        MutableFieldRef<Names<String, Integer, Object>, Object> token = names.token_;

        assertThat(first.type()).isSameAs(String_.TOKEN);
        assertThat(token.name()).isEqualTo("token");
        assertThat(token.type()).isSameAs(Object_.TOKEN);
        assertThat(names.all.result().typeRef())
                .isEqualTo(Types.parameterized(ConstantDescs.CD_List, Types.exact(Integer_.TOKEN.typeRef())));
    }

    /// `<Data> Data data(Data data)`: a method of the metamodel, that takes the token of `Data`.
    public static void theFactOfAGenericMethodIsMadeWithATokenOfItsTypeParameter() {
        Names_<String, Integer, Object> names = new Names_<>(String_.TOKEN, Integer_.TOKEN, Object_.TOKEN);
        MethodRef1<Names<String, Integer, Object>, String, String> data = names.data_Data(String_.TOKEN);

        assertThat(data.params()).containsExactly(String_.TOKEN);
        assertThat(data.traits().typeArgs()).containsExactly(String_.TOKEN);
    }
}
