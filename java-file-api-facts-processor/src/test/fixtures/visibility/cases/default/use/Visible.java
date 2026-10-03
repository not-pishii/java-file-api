import gen.facts.p.Outer_Pub_;
import gen.facts.p.Vis_;
import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodSignature;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidStaticMethodRef0;
import p.Outer;
import p.Vis;

import static org.assertj.core.api.Assertions.assertThat;

/// Only `public` members get facts, and the method table has all the same the ones that are not.
public final class Visible {
    private Visible() {}

    public static void aPublicMemberHasAFact() {
        MutableFieldRef<Vis, Prim.Int> pub = Vis_.pub;
        CtorRef0<Vis> constructor = Vis_.new_;
        VoidMethodRef0<Vis> method = Vis_.pubM;
        VoidStaticMethodRef0 staticMethod = Vis_.sPub;

        assertThat(pub.name()).isEqualTo("pub");
        assertThat(constructor.owner()).isSameAs(Vis_.TOKEN);
        assertThat(method.name()).isEqualTo("pubM");
        assertThat(staticMethod.name()).isEqualTo("sPub");
    }

    public static void aPublicNestedTypeInASignatureIsOfItsMetamodel() {
        MethodRef0<Vis, Outer.Pub> ok = Vis_.ok;

        assertThat(ok.resultType().orElseThrow()).isEqualTo(Outer_Pub_.TOKEN);
    }

    public static void protectedAndPackagePrivateMembersAreInTheMethodTableAllTheSame() {
        assertThat(Vis_.TOKEN.methods().concreteMethods())
                .extracting(MethodSignature::name)
                .contains("protM", "pkgM")
                .doesNotContain("privM");
    }
}
