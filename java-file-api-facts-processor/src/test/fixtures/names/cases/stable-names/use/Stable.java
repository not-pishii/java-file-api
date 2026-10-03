import gen.facts.java.lang.String_;
import gen.facts.p.Ov_;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.VoidMethodRef1;
import p.Ov;

import static org.assertj.core.api.Assertions.assertThat;

/// The name of a fact does not depend on which other members get a fact: `m(java.util.List<Hidden>)` has none, and
/// still `m(java.awt.List)` is told from it.
public final class Stable {
    private Stable() {}

    public static void anOverloadIsToldFromAnotherThatHasNoFactByItsParameters() {
        VoidMethodRef1<Ov, java.awt.List> m = Ov_.m_java_awt_List;
        CtorRef1<Ov, java.awt.List> constructor = Ov_.new_java_awt_List;

        assertThat(m.name()).isEqualTo("m");
        assertThat(constructor.owner()).isSameAs(Ov_.TOKEN);
    }

    public static void aMethodWithoutAnOverloadHasItsParameterInItsName() {
        VoidMethodRef1<Ov, java.awt.List> n = Ov_.n_List;

        assertThat(n.name()).isEqualTo("n");
    }

    public static void aGenericOverloadIsToldByItsTypeParameter() {
        VoidMethodRef1<Ov, Prim.Int> plain = Ov_.k_int;
        VoidMethodRef1<Ov, String> generic = Ov_.k_T(String_.TOKEN);

        assertThat(plain.name()).isEqualTo("k");
        assertThat(generic.name()).isEqualTo("k");
    }

    public static void aFieldAndAGenericMethodOfTheSameNameAreToldByAnEscape() {
        MutableFieldRef<Ov, Prim.Int> field = Ov_.size;
        MethodRef0<Ov, String> method = Ov_.size_(String_.TOKEN);

        assertThat(field.name()).isEqualTo("size");
        assertThat(method.name()).isEqualTo("size");
    }
}
