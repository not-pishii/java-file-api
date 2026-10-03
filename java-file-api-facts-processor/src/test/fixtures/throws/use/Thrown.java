import gen.facts.p.Failure_;
import gen.facts.p.Io_;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;
import p.Io;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// The `throws` clause of a member is a token per exception type, in declaration order.
public final class Thrown {
    private Thrown() {}

    private static List<TypeRef> thrown(Invocable fact) {
        return fact.traits().throwsTypes().stream().<TypeRef>map(RefToken::typeRef).toList();
    }

    private static TypeRef type(String name) {
        return Types.of(ClassDesc.of(name));
    }

    public static void theThrowsClauseOfAConstructorIsATokenPerException() {
        assertThat(thrown(Io_.new_)).containsExactly(type("java.io.IOException"));
        assertThat(thrown(Io_.new_int)).isEmpty();
    }

    public static void theThrowsClauseOfAMethodIsATokenPerExceptionInDeclarationOrder() {
        assertThat(thrown(Io_.read)).containsExactly(type("java.io.IOException"));
        assertThat(thrown(Io_.multi))
                .containsExactly(
                        type("java.io.IOException"),
                        type("java.lang.InterruptedException"),
                        type("java.lang.IllegalStateException"));
        assertThat(thrown(Io_.unchecked)).containsExactly(type("java.lang.IllegalArgumentException"));
        assertThat(thrown(Io_.util)).containsExactly(type("java.lang.Exception"));
        assertThat(thrown(Io_.custom)).containsExactly(type("p.Failure"));
    }

    public static void theThrowsClauseKeepsTheOverridabilityOfTheMember() {
        assertThat(Io_.custom.traits().overridability()).isEqualTo(MemberTraits.OVERRIDABLE.overridability());
        assertThat(Io_.locked.traits().overridability()).isEqualTo(MemberTraits.FINAL.overridability());
        assertThat(Io_.util.traits().overridability()).isEqualTo(MemberTraits.FINAL.overridability());
        assertThat(Io_.new_.traits().overridability()).isEqualTo(MemberTraits.FINAL.overridability());
        assertThat(Io_.read.traits().overridability()).isEqualTo(Overridability.OVERRIDABLE);
    }

    public static void aTypeVariableInAThrowsClauseIsTheTokenGivenForIt() {
        VoidMethodRef0<Io> generic = Io_.generic(Failure_.TOKEN);

        assertThat(List.<Object>copyOf(generic.traits().throwsTypes())).containsExactly(Failure_.TOKEN);
        assertThat(generic.traits().typeArgs()).containsExactly(Failure_.TOKEN);
    }
}
