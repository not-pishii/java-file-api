import gen.facts.p.Covariant_;
import me.supcheg.javafile.facts.MethodRef0;
import p.Covariant;

import static org.assertj.core.api.Assertions.assertThat;

/// `Covariant` declares `self()` again, with a `public` result: the
/// member is its own, with a fact and no warning, next to the adopted ones.
public final class Redeclared {
    private Redeclared() {}

    public static void aMemberTheSubtypeDeclaresAgainWithAPublicTypeIsItsOwn() {
        MethodRef0<Covariant, Covariant> self = Covariant_.self;
        MethodRef0<Covariant, String> near = Covariant_.near;
        MethodRef0<Covariant, String> get = Covariant_.get;

        assertThat(self.result()).isEqualTo(Covariant_.TOKEN);
        assertThat(near.owner()).isEqualTo(Covariant_.TOKEN);
        assertThat(get.owner()).isEqualTo(Covariant_.TOKEN);
    }
}
