import gen.facts.p.Box_;
import me.supcheg.javafile.facts.MutableStaticFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.type.Types;
import p.Box;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// The static members of a generic type know nothing of its type
/// parameters: their facts are `static` fields of the metamodel, owned by
/// `ANY`, the token of the type applied to wildcards.
public final class Statics {
    private static final ClassDesc BOX = ClassDesc.of("p.Box");

    private Statics() {}

    public static void theStaticMembersOfAGenericTypeAreFactsOfTheClassOwnedByAny() {
        OpenClassToken<Box<?>> any = Box_.ANY;
        StaticFieldRef<String> name = Box_.NAME;
        MutableStaticFieldRef<Prim.Int> count = Box_.count;
        StaticMethodRef0<Box<String>> ofString = Box_.ofString;
        StaticMethodRef1<Prim.Int, Box<?>> size = Box_.size_Box;

        assertThat(name.constantValue()).contains("box");
        assertThat(name.owner()).isSameAs(any);
        assertThat(count.owner()).isSameAs(any);
        assertThat(ofString.owner()).isSameAs(any);
        assertThat(ofString.result().typeRef()).isEqualTo(Types.parameterized(BOX, Types.exact(Types.STRING)));
        assertThat(size.params())
                .extracting(TypeToken::typeRef)
                .containsExactly(Types.parameterized(BOX, Types.unbounded()));
    }
}
