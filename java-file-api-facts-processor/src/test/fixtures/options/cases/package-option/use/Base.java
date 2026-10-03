import com.acme.metamodel.p.Dep_;
import com.acme.metamodel.p.Svc_;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.type.Types;
import p.Dep;
import p.Svc;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// `-Ajavafile.facts.package` names the base: the classes with `@Facts` of several packages share it.
public final class Base {
    private Base() {}

    public static void theMetamodelsAreUnderTheBaseTheOptionNames() {
        OpenClassToken<Svc> svc = Svc_.TOKEN;
        OpenClassToken<Dep> dep = Dep_.TOKEN;

        assertThat(svc.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Svc")));
        assertThat(dep.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Dep")));
    }
}
