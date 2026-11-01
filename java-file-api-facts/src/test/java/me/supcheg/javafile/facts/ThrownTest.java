package me.supcheg.javafile.facts;

import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ThrownTest {

    private static final ClassDesc IO_EXCEPTION = ClassDesc.of("java.io", "IOException");

    @Test
    void aClassIsItsErasureAndChainAndIsNoVariable() {
        Thrown thrown = new Thrown(
                IO_EXCEPTION,
                List.of(ClassDesc.of("java.lang", "Exception"), ConstantDescs.CD_Throwable, ConstantDescs.CD_Object),
                Optional.empty());

        assertThat(thrown.erasure()).isEqualTo(IO_EXCEPTION);
        assertThat(thrown.superclasses())
                .containsExactly(
                        ClassDesc.of("java.lang", "Exception"), ConstantDescs.CD_Throwable, ConstantDescs.CD_Object);
        assertThat(thrown.isVariable()).isFalse();
    }

    @Test
    void aTypeVariableCarriesItsName() {
        Thrown thrown = new Thrown(ConstantDescs.CD_Throwable, List.of(ConstantDescs.CD_Object), Optional.of("X"));

        assertThat(thrown.isVariable()).isTrue();
        assertThat(thrown.variable()).contains("X");
    }

    @Test
    void objectHasNoSuperclass() {
        assertThat(new Thrown(ConstantDescs.CD_Object, List.of(), Optional.empty()).superclasses())
                .isEmpty();
    }

    @Test
    void aPrimitiveIsNoThrownType() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Thrown(ConstantDescs.CD_int, List.of(), Optional.empty()))
                .withMessageContaining("a thrown type is a class");
    }

    @Test
    void aClassThatIsNotObjectHasASuperclassChainThatEndsInObject() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Thrown(IO_EXCEPTION, List.of(), Optional.empty()))
                .withMessageContaining("must end in java.lang.Object");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Thrown(IO_EXCEPTION, List.of(ConstantDescs.CD_Throwable), Optional.empty()))
                .withMessageContaining("must end in java.lang.Object");
    }
}
