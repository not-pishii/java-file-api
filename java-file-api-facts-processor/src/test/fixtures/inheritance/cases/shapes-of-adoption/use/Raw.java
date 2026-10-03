import gen.facts.java.lang.Object_;
import gen.facts.p.PubRaw_;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.processor.harness.Typed;
import me.supcheg.javafile.type.Types;
import p.PubRaw;

import java.util.List;

import static me.supcheg.javafile.typed.Expressions.call;
import static org.assertj.core.api.Assertions.assertThat;

/// `PubRaw extends HG`, the raw type of the hidden `HG<T>`: the members it
/// adopts are those of the raw type, as javac has them — `T` is `Object`,
/// `List<T>` is the raw `List`.
public final class Raw {
    private Raw() {}

    public static void theAdoptedMembersAreErased() {
        MethodRef0<PubRaw, Object> get = PubRaw_.get;
        VoidMethodRef1<PubRaw, Object> put = PubRaw_.put_Object;
        MutableFieldRef<PubRaw, Object> value = PubRaw_.value;
        var all = PubRaw_.all;

        assertThat(get.result().typeRef()).isEqualTo(Types.OBJECT);
        assertThat(put.params()).hasSize(1);
        assertThat(value.type().typeRef()).isEqualTo(Types.OBJECT);
        assertThat(all.result().typeRef()).isEqualTo(Types.of(List.class));
    }

    public static void aMethodOfTheRawSupertypeIsCalled(Typed typed) {
        assertThat(typed.apply(Object_.TOKEN, PubRaw_.TOKEN, raw -> call(raw, PubRaw_.get), new PubRaw()))
                .isNull();
    }

    /// What is rendered for a method that takes or gives a `T` of the raw supertype is code javac warns of
    /// under `-Xlint` — `[unchecked]` for a call of `put(T)`, `[rawtypes]` for a `List` — so it is not compiled here.
    public static void aCallThatJavacWarnsOfIsRenderedAllTheSame(Typed typed) {
        var all = PubRaw_.all;

        assertThat(typed.render(all.result(), PubRaw_.TOKEN, raw -> call(raw, all)))
                .contains("return v0.all();");
    }
}
