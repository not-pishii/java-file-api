import a.facts.p.Dep_;
import b.facts.p.Other_;

import static org.assertj.core.api.Assertions.assertThat;

/// The metamodel of `p.Dep` of module a, which matches `p.Dep` here, is the one module b refers to.
public final class Reused {
    private Reused() {}

    public static void theMetamodelOfTheOtherModuleIsTheOneUsed() {
        assertThat(Other_.dep.resultType().orElseThrow()).isEqualTo(Dep_.TOKEN);
    }
}
