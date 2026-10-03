import gen.facts.java.lang.String_;
import gen.facts.p.Low_;
import me.supcheg.javafile.facts.MethodRef3;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.TypeToken;
import p.Low;

import java.lang.constant.ClassDesc;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/// The name of a type parameter neither hides a package the metamodel writes a qualified name by nor takes the name
/// of a fact: the type parameters `gen`, `java`, `me` and `p` of `Low` are `gen_`, `java_`, `me_` and `p_` of
/// `Low_`, and the tokens are named after them (see the source in `expected/`).
public final class Renamed {
    private Renamed() {}

    public static void aTypeParameterNamedLikeAPackageIsRenamedAndTheFactsAreOfTheTypeArguments() {
        Low_<String, String, String, String> low =
                new Low_<>(String_.TOKEN, String_.TOKEN, String_.TOKEN, String_.TOKEN);
        MethodRef3<Low<String, String, String, String>, Set<String>, String, String, String> all = low.all_java_me_p;
        MutableFieldRef<Low<String, String, String, String>, String> first = low.first;

        assertThat(all.params().stream().<ClassDesc>map(TypeToken::erasure))
                .containsExactly(
                        ClassDesc.of("java.lang.String"),
                        ClassDesc.of("java.lang.String"),
                        ClassDesc.of("java.lang.String"));
        assertThat(first.name()).isEqualTo("first");
    }
}
