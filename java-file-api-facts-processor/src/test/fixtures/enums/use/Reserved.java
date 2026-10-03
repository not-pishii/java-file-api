import gen.facts.p.Tok_;
import me.supcheg.javafile.facts.EnumConstant;
import me.supcheg.javafile.facts.EnumToken;
import p.Tok;

import static org.assertj.core.api.Assertions.assertThat;

/// A constant named like a reserved name of the metamodel (`TOKEN`, `sam`, `token`) is escaped, and keeps its name.
public final class Reserved {
    private Reserved() {}

    public static void aConstantNamedLikeAReservedNameIsEscaped() {
        EnumToken<Tok> token = Tok_.TOKEN;
        EnumConstant<Tok> upper = Tok_.TOKEN_;
        EnumConstant<Tok> sam = Tok_.sam_;
        EnumConstant<Tok> lower = Tok_.token_;

        assertThat(upper.name()).isEqualTo("TOKEN");
        assertThat(sam.name()).isEqualTo("sam");
        assertThat(lower.name()).isEqualTo("token");
        assertThat(token.constants()).containsExactly("TOKEN", "sam", "token");
    }
}
