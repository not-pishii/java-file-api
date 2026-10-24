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

    @Test
    void theFactoriesOfTypeRefsTakeATypeUseAnnotation() {
        AnnotationUse nonNull = new AnnotationUse(NON_NULL, List.of());

        assertThat(Types.of(ClassDesc.of("java.lang", "String"), nonNull).annotations())
                .containsExactly(nonNull);
        assertThat(Types.parameterized(ClassDesc.of("java.util", "List"), List.of(), nonNull)
                        .annotations())
                .containsExactly(nonNull);
        assertThat(Types.array(PrimitiveTypeRef.INT, nonNull).annotations()).containsExactly(nonNull);
        assertThat(Types.typeVar("T", nonNull).annotations()).containsExactly(nonNull);
    }
}
