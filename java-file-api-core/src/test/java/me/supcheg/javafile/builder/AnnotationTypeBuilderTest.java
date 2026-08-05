package me.supcheg.javafile.builder;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.model.AnnotationElementDecl;
import me.supcheg.javafile.model.AnnotationTypeDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotationTypeBuilderTest {

    @Test
    void modifiersDefaultToPublicEvenWithoutExplicitWithModifiers() {
        AnnotationTypeBuilder builder = new AnnotationTypeBuilder(ClassDesc.of("ast", "Empty"));

        AnnotationTypeDecl decl = builder.build();

        assertThat(decl.modifiers()).containsExactly(Modifier.PUBLIC);
    }

    @Test
    void withExactModifiersReplacesTheDefaultPublicSeed() {
        AnnotationTypeBuilder builder = new AnnotationTypeBuilder(ClassDesc.of("ast", "Nested"));
        builder.withExactModifiers(Set.of(Modifier.PRIVATE, Modifier.STATIC));

        AnnotationTypeDecl decl = builder.build();

        assertThat(decl.modifiers()).containsExactlyInAnyOrder(Modifier.PRIVATE, Modifier.STATIC);
    }

    @Test
    void withModifiersAddsToThePublicSeed() {
        AnnotationTypeBuilder builder = new AnnotationTypeBuilder(ClassDesc.of("ast", "Nested"));
        builder.withModifiers(Modifier.STATIC);

        AnnotationTypeDecl decl = builder.build();

        assertThat(decl.modifiers()).containsExactlyInAnyOrder(Modifier.PUBLIC, Modifier.STATIC);
    }

    @Test
    void annotationsAreCarriedAllThreeWays() {
        AnnotationTypeBuilder builder = new AnnotationTypeBuilder(ClassDesc.of("me.supcheg.example", "Documented"));
        ClassDesc deprecated = ClassDesc.of("java.lang", "Deprecated");
        ClassDesc since = ClassDesc.of("me.supcheg.example", "Since");
        ClassDesc preBuilt = ClassDesc.of("me.supcheg.example", "PreBuilt");

        builder.withAnnotation(deprecated)
                .withAnnotation(since, ab -> ab.withMember("value", AnnotationValues.literal("1.0")))
                .withAnnotation(new AnnotationUse(preBuilt, List.of()));

        AnnotationTypeDecl decl = builder.build();

        assertThat(decl.annotations()).hasSize(3);
        assertThat(decl.annotations().get(0).type()).isEqualTo(deprecated);
        assertThat(decl.annotations().get(1).type()).isEqualTo(since);
        assertThat(decl.annotations().get(2).type()).isEqualTo(preBuilt);
    }

    @Test
    void elementsAreAddedWithAndWithoutADefaultValue() {
        AnnotationTypeBuilder builder = new AnnotationTypeBuilder(ClassDesc.of("ast", "Marker"));

        builder.withElement("plain", PrimitiveTypeRef.INT)
                .withElement("withDefault", PrimitiveTypeRef.INT, AnnotationValues.literal(1));

        AnnotationTypeDecl decl = builder.build();

        assertThat(decl.elements())
                .containsExactly(
                        new AnnotationElementDecl("plain", PrimitiveTypeRef.INT, Optional.empty()),
                        new AnnotationElementDecl(
                                "withDefault", PrimitiveTypeRef.INT, Optional.of(AnnotationValues.literal(1))));
    }

    @Test
    void acceptAppendsAPreBuiltElement() {
        AnnotationTypeBuilder builder = new AnnotationTypeBuilder(ClassDesc.of("ast", "Marker"));
        AnnotationElementDecl element = new AnnotationElementDecl("value", PrimitiveTypeRef.INT, Optional.empty());

        builder.accept(element);

        assertThat(builder.build().elements()).containsExactly(element);
    }
}
