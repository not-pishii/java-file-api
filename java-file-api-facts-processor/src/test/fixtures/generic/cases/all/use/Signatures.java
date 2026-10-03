import gen.facts.java.lang.String_;
import gen.facts.java.util.List_;
import gen.facts.p.Box_;
import gen.facts.p.Uses_;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import p.Box;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// A parameterized or a raw type in a signature is a token made from the
/// shape of its type — `List_.Data.SHAPE` — applied to the tokens of its
/// arguments.
public final class Signatures {
    private static final ClassDesc BOX = ClassDesc.of("p.Box");
    private static final TypeRef INTEGER = Types.of(ClassDesc.of("java.lang.Integer"));

    private Signatures() {}

    public static void aParameterizedTypeIsATokenOfItsShape() {
        Box_<String> box = new Box_<>(String_.TOKEN);
        DeclaredToken<List<String>> list = (DeclaredToken<List<String>>) box.asList.result();

        assertThat(list.typeRef()).isEqualTo(Types.parameterized(ConstantDescs.CD_List, Types.exact(Types.STRING)));
        assertThat(list.shape()).isSameAs(List_.Data.SHAPE);
        assertThat(list.methods().abstractMethods())
                .contains(new MethodSignature("add", List.of(ConstantDescs.CD_String)));
    }

    public static void wildcardsOfEveryKindAreArgumentsOfTheToken() {
        Box_<String> box = new Box_<>(String_.TOKEN);

        assertThat(box.addAll_Collection.params())
                .extracting(TypeToken::typeRef)
                .containsExactly(Types.parameterized(ConstantDescs.CD_Collection, Types.extendsBound(Types.STRING)));
        assertThat(box.drainTo_Collection.params())
                .extracting(TypeToken::typeRef)
                .containsExactly(Types.parameterized(ConstantDescs.CD_Collection, Types.superBound(Types.STRING)));
        assertThat(box.sameAs_Box.params())
                .extracting(TypeToken::typeRef)
                .containsExactly(Types.parameterized(BOX, Types.unbounded()));
    }

    /// `List raw(Map raw)`: `var`, as the types of these facts are raw.
    public static void aGenericTypeWithoutTypeArgumentsIsARawToken() {
        Box_<String> box = new Box_<>(String_.TOKEN);
        var raw = box.raw_Map;
        var rawSelf = box.rawSelf;

        assertThat(raw.result().typeRef()).isEqualTo(Types.of(ConstantDescs.CD_List));
        assertThat(raw.result()).isInstanceOfSatisfying(DeclaredToken.class, list -> assertThat(list.shape())
                .isSameAs(List_.Data.SHAPE));
        assertThat(raw.params()).extracting(TypeToken::typeRef).containsExactly(Types.of(ConstantDescs.CD_Map));
        // the raw type of the type itself is not the token of the instance
        assertThat(rawSelf.result().typeRef()).isEqualTo(Types.of(BOX));
        assertThat(Uses_.raw.result().typeRef()).isEqualTo(Types.of(BOX));
    }

    public static void aTypeThatIsNotGenericMentionsParameterizedTypesByTheirShapes() {
        DeclaredToken<Box<String>> strings = (DeclaredToken<Box<String>>) Uses_.strings.result();

        assertThat(strings.typeRef()).isEqualTo(Types.parameterized(BOX, Types.exact(Types.STRING)));
        assertThat(strings.shape()).isSameAs(Box_.Data.SHAPE);
        assertThat(Uses_.take_Sorted.params())
                .extracting(TypeToken::typeRef)
                .containsExactly(Types.parameterized(ClassDesc.of("p.Sorted"), Types.exact(INTEGER)));
        assertThat(Uses_.wild.type().typeRef())
                .isEqualTo(Types.parameterized(
                        ConstantDescs.CD_Map,
                        Types.exact(Types.STRING),
                        Types.extendsBound(Types.of(ClassDesc.of("java.lang.Number")))));
        assertThat(Uses_.lists.result().typeRef())
                .isEqualTo(Types.array(Types.parameterized(ConstantDescs.CD_List, Types.superBound(INTEGER))));
        assertThat(Uses_.arrays.result().typeRef())
                .isEqualTo(Types.parameterized(
                        ClassDesc.of("p.Pair"),
                        Types.exact(Types.array(Types.INT)),
                        Types.exact(Types.array(Types.STRING))));
        assertThat(Uses_.any.result().typeRef())
                .isEqualTo(Types.parameterized(ConstantDescs.CD_List, Types.unbounded()));
    }
}
