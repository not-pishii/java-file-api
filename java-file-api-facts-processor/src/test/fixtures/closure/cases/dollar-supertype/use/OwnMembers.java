import gen.facts.p.OverDollar_;
import me.supcheg.javafile.facts.VoidMethodRef0;
import p.OverDollar;

import static org.assertj.core.api.Assertions.assertThat;

/// `OverDollar extends Dol$lar`, of which no metamodel can be made: the
/// requested type is generated all the same, with its own members.
public final class OwnMembers {
    private OwnMembers() {}

    public static void theSubtypeOfATypeWithoutAMetamodelHasItsOwnFacts() {
        VoidMethodRef0<OverDollar> own = OverDollar_.own;

        assertThat(own.owner()).isSameAs(OverDollar_.TOKEN);
    }
}
