import com.acme.gen.facts.p.Dep_;
import com.acme.gen.facts.p.Svc_;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.type.Types;
import p.Dep;
import p.Svc;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// Without the option the base is `facts` under the package of `@Facts`: `com.acme.gen.facts`.
public final class Base {
    private Base() {}

    public static void theMetamodelsAreUnderFactsInThePackageOfTheClassWithFacts() {
        OpenClassToken<Svc> svc = Svc_.TOKEN;
        OpenClassToken<Dep> dep = Dep_.TOKEN;

        assertThat(svc.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Svc")));
        assertThat(dep.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Dep")));
        assertThat(Svc_.dep.name()).isEqualTo("dep");
    }
}
