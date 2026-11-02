package me.supcheg.javafile.facts;

import me.supcheg.javafile.type.ClassTypeRef;
import me.supcheg.javafile.type.ExactTypeArg;
import me.supcheg.javafile.type.ParameterizedTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// A type parameter a method declares ([MethodTypeParameter]): the name and the bounds the declaration
/// writes, and the token the body of the member uses as its type.
class MethodTypeParameterTest {
    private static final TypeVarToken<Object> TOKEN =
            UnsafeFacts.typeVarToken(Types.typeVar("T"), ConstantDescs.CD_Object);

    @Test
    void aTypeParameterIsWrittenWithItsBounds() {
        assertThat(new MethodTypeParameter(TOKEN, List.of(new ClassTypeRef(ConstantDescs.CD_Object))))
                .hasToString("T extends java.lang.Object")
                .extracting(MethodTypeParameter::name)
                .isEqualTo("T");
        assertThat(new MethodTypeParameter(
                        TOKEN,
                        List.of(
                                new ClassTypeRef(ClassDesc.of("java.lang", "Number")),
                                new ParameterizedTypeRef(
                                        ClassDesc.of("java.lang", "Comparable"),
                                        List.of(new ExactTypeArg(Types.typeVar("T")))))))
                .hasToString("T extends java.lang.Number & java.lang.Comparable<T>");
    }

    @Test
    void aTypeParameterHasALeftmostBound() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new MethodTypeParameter(TOKEN, List.of()))
                .withMessageContaining("has a leftmost bound");
    }
}
