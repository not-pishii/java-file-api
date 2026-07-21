package me.supcheg.javafile.model;

import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VarargsParamTest {

    @Test
    void nonVarargsParamDefaultsToFalse() {
        assertThat(new Param("x", PrimitiveTypeRef.INT).varargs()).isFalse();
    }

    @Test
    void varargsParamMustBeLast() {
        Param varargsFirst = new Param("rest", PrimitiveTypeRef.INT, List.of(), true);
        Param normalSecond = new Param("count", PrimitiveTypeRef.INT);

        assertThatThrownBy(() -> new MethodDecl(
                        "m",
                        Optional.empty(),
                        List.of(),
                        java.util.Set.of(Modifier.PUBLIC),
                        List.of(),
                        List.of(varargsFirst, normalSecond),
                        me.supcheg.javafile.code.CodeBody.EMPTY,
                        List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
