import b.facts.p.OnHid_;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.Prim;
import p.OnHid;

import static org.assertj.core.api.Assertions.assertThat;

/// `OnHid extends HidBase`, which is not `public`: the metamodel of the
/// classpath was generated when `HidBase` had `old()`, and has `renamed()`
/// now. The fingerprint of `OnHid` tells the members it adopts, so the
/// metamodel is stale and generated anew.
public final class Regenerated {
    private Regenerated() {}

    public static void theMetamodelHasTheMemberOfTheHiddenSupertypeAsItIsNow() {
        MethodRef0<OnHid, Prim.Int> renamed = OnHid_.renamed;

        assertThat(renamed.owner()).isSameAs(OnHid_.TOKEN);
    }
}
