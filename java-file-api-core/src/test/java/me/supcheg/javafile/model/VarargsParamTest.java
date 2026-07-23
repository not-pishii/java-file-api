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

    @Test
    void defaultMethodDeclRejectsNonLastVarargsParam() {
        Param varargsFirst = new Param("rest", PrimitiveTypeRef.INT, List.of(), true);
        Param normalSecond = new Param("count", PrimitiveTypeRef.INT);

        assertThatThrownBy(() -> new DefaultMethodDecl(
                        "m",
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of(varargsFirst, normalSecond),
                        me.supcheg.javafile.code.CodeBody.EMPTY,
                        List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void staticMethodDeclRejectsNonLastVarargsParam() {
        Param varargsFirst = new Param("rest", PrimitiveTypeRef.INT, List.of(), true);
        Param normalSecond = new Param("count", PrimitiveTypeRef.INT);

        assertThatThrownBy(() -> new StaticMethodDecl(
                        "m",
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of(varargsFirst, normalSecond),
                        me.supcheg.javafile.code.CodeBody.EMPTY,
                        List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void enumConstructorDeclRejectsNonLastVarargsParam() {
        Param varargsFirst = new Param("rest", PrimitiveTypeRef.INT, List.of(), true);
        Param normalSecond = new Param("count", PrimitiveTypeRef.INT);

        assertThatThrownBy(() -> new EnumConstructorDecl(
                        List.of(),
                        List.of(varargsFirst, normalSecond),
                        me.supcheg.javafile.code.CodeBody.EMPTY,
                        List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void enumConstructorDeclAcceptsVarargsParamInLastPosition() {
        Param normalFirst = new Param("count", PrimitiveTypeRef.INT);
        Param varargsLast = new Param("rest", PrimitiveTypeRef.INT, List.of(), true);

        EnumConstructorDecl decl = new EnumConstructorDecl(
                List.of(), List.of(normalFirst, varargsLast), me.supcheg.javafile.code.CodeBody.EMPTY, List.of());

        assertThat(decl.params()).containsExactly(normalFirst, varargsLast);
    }
}
