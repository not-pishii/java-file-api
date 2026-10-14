import gen.facts.java.lang.Object_;
import gen.facts.p.PubRaw_;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.processor.harness.Typed;
import me.supcheg.javafile.type.Types;
import p.PubRaw;

import java.util.List;

import static me.supcheg.javafile.typed.Expressions.call;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// `PubRaw extends HG`, the raw type of the hidden `HG<T>`: the members it
/// adopts are those of the raw type, as javac has them — `T` is `Object`,
/// `List<T>` is the raw `List` —, and none that takes a `T`, which javac
/// warns of a call or an assignment of.
public final class Raw {
    private Raw() {}

    public static void theAdoptedMembersAreErased() {
        MethodRef0<PubRaw, Object> get = PubRaw_.get;
        var all = PubRaw_.all;

        assertThat(get.result().typeRef()).isEqualTo(Types.OBJECT);
        assertThat(all.result().typeRef()).isEqualTo(Types.of(List.class));
    }

    /// A call of `put(T)` and an assignment of `value`, of `T`, are ones javac warns of as unchecked:
    /// neither has a fact.
    public static void whatTakesAValueOfATypeVariableHasNoFact() {
        assertThat(java.util.Arrays.stream(PubRaw_.class.getFields()).map(field -> field.getName()))
                .doesNotContain("put_Object", "value");
    }

    public static void aMethodOfTheRawSupertypeIsCalled(Typed typed) {
        assertThat(typed.apply(Object_.TOKEN, PubRaw_.TOKEN, raw -> call(raw, PubRaw_.get), new PubRaw()))
                .isNull();
    }

    /// A method of the raw supertype that gives a `List` gives a raw one, which code javac warns of
    /// (`[rawtypes]`) would write: the typed layer refuses the call.
    public static void aCallOfAMethodOfARawResultIsRefused(Typed typed) {
        var all = PubRaw_.all;

        assertThatIllegalStateException()
                .isThrownBy(() -> typed.render(all.result(), PubRaw_.TOKEN, raw -> call(raw, all)))
                .withMessageContaining("is the raw type java.util.List");
    }
}
