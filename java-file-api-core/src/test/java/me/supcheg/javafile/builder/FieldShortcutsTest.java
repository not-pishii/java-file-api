package me.supcheg.javafile.builder;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.model.ConstantDecl;
import me.supcheg.javafile.model.EnumConstantMember;
import me.supcheg.javafile.model.EnumMember;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.StaticFieldDecl;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FieldShortcutsTest {

    private static final ClassDesc HOLDER = ClassDesc.of("com.example", "Holder");
    private static final ClassDesc DEPRECATED = ClassDesc.of("java.lang", "Deprecated");

    @Test
    void twoArgWithFieldMatchesEmptyConsumerForm() {
        assertThat(new ClassBuilder(HOLDER)
                        .withField("count", PrimitiveTypeRef.INT)
                        .build())
                .isEqualTo(new ClassBuilder(HOLDER)
                        .withField("count", PrimitiveTypeRef.INT, fb -> {})
                        .build());
    }

    @Test
    void threeArgWithFieldSetsTheInitializer() {
        FieldDecl field = (FieldDecl) new ClassBuilder(HOLDER)
                .withField("count", PrimitiveTypeRef.INT, Exprs.literal(0))
                .build()
                .members()
                .get(0);

        assertThat(field.initializer()).contains(Exprs.literal(0));
    }

    @Test
    void enumTwoArgWithFieldMatchesEmptyConsumerForm() {
        assertThat(new EnumBuilder(HOLDER)
                        .withField("count", PrimitiveTypeRef.INT)
                        .build())
                .isEqualTo(new EnumBuilder(HOLDER)
                        .withField("count", PrimitiveTypeRef.INT, fb -> {})
                        .build());
    }

    @Test
    void enumThreeArgWithFieldSetsTheInitializer() {
        EnumMember member = new EnumBuilder(HOLDER)
                .withField("count", PrimitiveTypeRef.INT, Exprs.literal(0))
                .build()
                .members()
                .get(0);

        assertThat(((FieldDecl) member).initializer()).contains(Exprs.literal(0));
    }

    @Test
    void anonymousTwoArgWithFieldMatchesEmptyConsumerForm() {
        assertThat(new AnonymousClassBuilder()
                        .withField("count", PrimitiveTypeRef.INT)
                        .build())
                .isEqualTo(new AnonymousClassBuilder()
                        .withField("count", PrimitiveTypeRef.INT, fb -> {})
                        .build());
    }

    @Test
    void anonymousThreeArgWithFieldSetsTheInitializer() {
        EnumConstantMember member = new AnonymousClassBuilder()
                .withField("count", PrimitiveTypeRef.INT, Exprs.literal(0))
                .build()
                .get(0);

        assertThat(((FieldDecl) member).initializer()).contains(Exprs.literal(0));
    }

    @Test
    void constantBuilderCarriesAnnotations() {
        ConstantDecl constant = (ConstantDecl) new InterfaceBuilder(HOLDER)
                .withConstant(
                        "MAX",
                        PrimitiveTypeRef.INT,
                        cb -> cb.withAnnotation(DEPRECATED).withInitializer(Exprs.literal(10)))
                .build()
                .members()
                .get(0);

        assertThat(constant.annotations()).containsExactly(new AnnotationUse(DEPRECATED, List.of()));
        assertThat(constant.initializer()).isEqualTo(Exprs.literal(10));
    }

    @Test
    void constantBuilderSupportsAnnotationBuilderSpec() {
        ConstantDecl constant = (ConstantDecl) new InterfaceBuilder(HOLDER)
                .withConstant(
                        "MAX",
                        PrimitiveTypeRef.INT,
                        cb -> cb.withAnnotation(DEPRECATED, ab -> {}).withInitializer(Exprs.literal(10)))
                .build()
                .members()
                .get(0);

        assertThat(constant.annotations()).containsExactly(new AnnotationUse(DEPRECATED, List.of()));
    }

    @Test
    void constantBuilderSupportsPreBuiltAnnotation() {
        AnnotationUse annotation = new AnnotationUse(DEPRECATED, List.of());
        ConstantDecl constant = (ConstantDecl) new InterfaceBuilder(HOLDER)
                .withConstant(
                        "MAX",
                        PrimitiveTypeRef.INT,
                        cb -> cb.withAnnotation(annotation).withInitializer(Exprs.literal(10)))
                .build()
                .members()
                .get(0);

        assertThat(constant.annotations()).containsExactly(annotation);
    }

    @Test
    void constantBuilderRequiresAnInitializer() {
        assertThatThrownBy(() -> new InterfaceBuilder(HOLDER)
                        .withConstant("MAX", PrimitiveTypeRef.INT, cb -> {})
                        .build())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void recordStaticFieldConsumerFormMatchesExprForm() {
        assertThat(new RecordBuilder(HOLDER)
                        .withStaticField("ORIGIN", PrimitiveTypeRef.INT, Exprs.literal(0))
                        .build())
                .isEqualTo(new RecordBuilder(HOLDER)
                        .withStaticField("ORIGIN", PrimitiveTypeRef.INT, fb -> fb.withInitializer(Exprs.literal(0)))
                        .build());
    }

    @Test
    void recordStaticFieldConsumerFormCarriesAnnotations() {
        StaticFieldDecl field = (StaticFieldDecl) new RecordBuilder(HOLDER)
                .withStaticField(
                        "ORIGIN",
                        PrimitiveTypeRef.INT,
                        fb -> fb.withAnnotation(DEPRECATED).withInitializer(Exprs.literal(0)))
                .build()
                .members()
                .get(0);

        assertThat(field.annotations()).containsExactly(new AnnotationUse(DEPRECATED, List.of()));
    }

    @Test
    void recordStaticFieldConsumerFormRequiresAnInitializer() {
        assertThatThrownBy(() -> new RecordBuilder(HOLDER)
                        .withStaticField("ORIGIN", PrimitiveTypeRef.INT, fb -> {})
                        .build())
                .isInstanceOf(IllegalStateException.class);
    }
}
