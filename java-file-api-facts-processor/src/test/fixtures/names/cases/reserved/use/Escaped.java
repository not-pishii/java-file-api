import gen.facts.p.Res_;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MutableFieldRef;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.type.Types;
import p.Res;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

/// A member named like a name of the metamodel (`sam`, `TOKEN`, `token`, `ANY`, `Data`, `Canonical`) or like
/// another fact (`count`) is escaped with `_`, and the fact keeps the name of the member.
public final class Escaped {
    private Escaped() {}

    public static void aFieldNamedLikeAReservedNameIsEscaped() {
        OpenClassToken<Res> token = Res_.TOKEN;
        MutableFieldRef<Res, Prim.Int> upper = Res_.TOKEN_;
        MutableFieldRef<Res, Prim.Int> lower = Res_.token_;
        MutableFieldRef<Res, Prim.Int> sam = Res_.sam_;
        MutableFieldRef<Res, Prim.Int> any = Res_.ANY_;
        MutableFieldRef<Res, Prim.Int> data = Res_.Data_;
        MutableFieldRef<Res, Prim.Int> canonical = Res_.Canonical_;
        MutableFieldRef<Res, Prim.Int> keyword = Res_.switch__;

        assertThat(token.typeRef()).isEqualTo(Types.of(ClassDesc.of("p.Res")));
        assertThat(upper.name()).isEqualTo("TOKEN");
        assertThat(lower.name()).isEqualTo("token");
        assertThat(sam.name()).isEqualTo("sam");
        assertThat(any.name()).isEqualTo("ANY");
        assertThat(data.name()).isEqualTo("Data");
        assertThat(canonical.name()).isEqualTo("Canonical");
        assertThat(keyword.name()).isEqualTo("switch_");
    }

    public static void aMethodNamedLikeAFieldIsEscapedAndAFieldIsNot() {
        MutableFieldRef<Res, Prim.Int> field = Res_.count;
        MethodRef0<Res, Prim.Int> method = Res_.count_;
        VoidMethodRef1<Res, Prim.Int> overload = Res_.sam_int;

        assertThat(field.name()).isEqualTo("count");
        assertThat(method.name()).isEqualTo("count");
        assertThat(overload.name()).isEqualTo("sam");
    }
}
