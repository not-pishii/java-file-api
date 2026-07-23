package me.supcheg.javafile.type;

import me.supcheg.javafile.annotation.AnnotationUse;
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
    void classTypeRefCanCarryATypeUseAnnotation() {
        AnnotationUse nonNull = new AnnotationUse(NON_NULL, List.of());
        ClassTypeRef ref = Types.of(ClassDesc.of("java.lang", "String"), nonNull);
        assertThat(ref.annotations()).containsExactly(nonNull);
    }

    @Test
    void parameterizedTypeRefDefaultsToNoAnnotations() {
        ParameterizedTypeRef ref = Types.parameterized(ClassDesc.of("java.util", "List"), List.of());
        assertThat(ref.annotations()).isEmpty();
    }

    @Test
    void parameterizedTypeRefCanCarryATypeUseAnnotation() {
        AnnotationUse nonNull = new AnnotationUse(NON_NULL, List.of());
        ParameterizedTypeRef ref = Types.parameterized(ClassDesc.of("java.util", "List"), List.of(), nonNull);
        assertThat(ref.annotations()).containsExactly(nonNull);
    }

    @Test
    void arrayTypeRefDefaultsToNoAnnotations() {
        assertThat(Types.array(PrimitiveTypeRef.INT).annotations()).isEmpty();
    }

    @Test
    void arrayTypeRefCanCarryATypeUseAnnotation() {
        AnnotationUse nonNull = new AnnotationUse(NON_NULL, List.of());
        ArrayTypeRef ref = Types.array(PrimitiveTypeRef.INT, nonNull);
        assertThat(ref.annotations()).containsExactly(nonNull);
    }

    @Test
    void typeVarRefDefaultsToNoAnnotations() {
        assertThat(Types.typeVar("T").annotations()).isEmpty();
    }

    @Test
    void typeVarRefCanCarryATypeUseAnnotation() {
        AnnotationUse nonNull = new AnnotationUse(NON_NULL, List.of());
        TypeVarRef ref = Types.typeVar("T", nonNull);
        assertThat(ref.annotations()).containsExactly(nonNull);
    }

    @Test
    void typeParamDefaultsToNoAnnotations() {
        assertThat(new TypeParam("T", List.of()).annotations()).isEmpty();
    }

    @Test
    void typeParamCanCarryATypeUseAnnotation() {
        AnnotationUse nonNull = new AnnotationUse(NON_NULL, List.of());
        TypeParam param = new TypeParam("T", List.of(), List.of(nonNull));
        assertThat(param.annotations()).containsExactly(nonNull);
    }
}
