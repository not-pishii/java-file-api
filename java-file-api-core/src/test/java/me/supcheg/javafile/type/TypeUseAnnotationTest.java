package me.supcheg.javafile.type;

import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TypeUseAnnotationTest {

    private static final ClassDesc NON_NULL = ClassDesc.of("javax.annotation", "Nonnull");

    @Test
    void classTypeRefDefaultsToNoAnnotations() {
        assertThat(Types.of(ClassDesc.of("java.lang", "String")).annotations()).isEmpty();
    }

    @Test
    void parameterizedTypeRefDefaultsToNoAnnotations() {
        ParameterizedTypeRef ref = Types.parameterized(ClassDesc.of("java.util", "List"), List.of());
        assertThat(ref.annotations()).isEmpty();
    }

    @Test
    void arrayTypeRefDefaultsToNoAnnotations() {
        assertThat(Types.array(PrimitiveTypeRef.INT).annotations()).isEmpty();
    }

    @Test
    void typeVarRefDefaultsToNoAnnotations() {
        assertThat(Types.typeVar("T").annotations()).isEmpty();
    }

    @Test
    void typeParamDefaultsToNoAnnotations() {
        assertThat(new TypeParam("T", List.of()).annotations()).isEmpty();
    }
}
