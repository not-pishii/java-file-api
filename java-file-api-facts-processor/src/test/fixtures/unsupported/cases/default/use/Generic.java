import gen.facts.java.lang.String_;
import gen.facts.java.util.List_;
import gen.facts.java.util.Map_;
import gen.facts.p.Mix_;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.VoidMethodRef1;
import p.Mix;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/// A generic signature is no reason to leave a member out: the fact has the type with its type arguments.
public final class Generic {
    private Generic() {}

    public static void aMemberWithAGenericSignatureHasAFact() {
        MethodRef0<Mix, List<String>> names = Mix_.names;
        MethodRef0<Mix, List<String>[]> array = Mix_.array;
        VoidMethodRef1<Mix, Map<?, ? extends Number>> wild = Mix_.wild_Map;
        CtorRef1<Mix, Set<String>> constructor = Mix_.new_Set;
        MutableFieldRef<Mix, Function<String, String>> field = Mix_.field;
        MethodRef0<Mix, String> plain = Mix_.plain;

        assertThat(names.name()).isEqualTo("names");
        assertThat(array.name()).isEqualTo("array");
        assertThat(wild.name()).isEqualTo("wild");
        assertThat(constructor.owner()).isSameAs(Mix_.TOKEN);
        assertThat(field.name()).isEqualTo("field");
        assertThat(plain.name()).isEqualTo("plain");
    }

    public static void aMemberOfARawTypeHasAFact() {
        var raw = Mix_.raw;

        assertThat(raw.name()).isEqualTo("raw");
    }

    public static void aGenericMethodHasAFactMadeFromAWitness() {
        MethodRef1<Mix, String, String> id = Mix_.id_T(String_.TOKEN);

        assertThat(id.name()).isEqualTo("id");
    }

    public static void theTypesTheSignaturesMentionHaveTokenOnlyMetamodels() {
        assertThat(String_.TOKEN).isNotNull();
        assertThat(new List_<>(String_.TOKEN).token).isNotNull();
        assertThat(new Map_<>(String_.TOKEN, String_.TOKEN).token).isNotNull();
    }
}
