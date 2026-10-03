import gen.facts.p.Disjoint_;
import gen.facts.p.Nested_;
import gen.facts.p.OneWithout_;
import gen.facts.p.Same_;
import me.supcheg.javafile.facts.Overridability;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.VoidSam0;
import me.supcheg.javafile.type.TypeRef;
import me.supcheg.javafile.type.Types;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// JLS 9.9: a lambda of the interface may throw only what each of the methods the `sam` stands for may.
public final class Throwing {
    private Throwing() {}

    private static List<TypeRef> throwsOf(VoidSam0<?> sam) {
        assertThat(sam.method().traits().overridability()).isEqualTo(Overridability.ABSTRACT);
        return sam.method().traits().throwsTypes().stream()
                .<TypeRef>map(RefToken::typeRef)
                .toList();
    }

    public static void noExceptionIsAllowedWhenTheMethodsThrowUnrelatedOnes() {
        assertThat(throwsOf(Disjoint_.sam)).isEmpty();
    }

    public static void theSubclassIsAllowedWhenOneMethodThrowsASubclassOfWhatTheOtherThrows() {
        assertThat(throwsOf(Nested_.sam)).containsExactly(Types.of(ClassDesc.of("java.io.FileNotFoundException")));
    }

    public static void theSameExceptionIsAllowedWhenBothThrowIt() {
        assertThat(throwsOf(Same_.sam)).containsExactly(Types.of(ClassDesc.of("java.io.IOException")));
    }

    public static void noExceptionIsAllowedWhenOneOfTheMethodsThrowsNone() {
        assertThat(throwsOf(OneWithout_.sam)).isEmpty();
    }
}
