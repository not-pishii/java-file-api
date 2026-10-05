import com.acme.gen.facts.p.Svc_;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.type.Types;
import p.Svc;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// With `-Ajavafile.facts.index=false` the metamodels are generated as usual, and none is listed (`index.txt` of
/// the case is absent): they are for this module alone.
public final class Unlisted {
    private Unlisted() {}

    public static void theMetamodelsAreGeneratedAsUsual() {
        OpenClassToken<Svc> svc = Svc_.TOKEN;

        assertThat(svc.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Svc")));
    }
}
