package me.supcheg.javafile.annotation;

import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotationConsumerPathsTest {

    private static final ClassDesc CONTRACT = ClassDesc.of("com.example", "Contract");
    private static final ClassDesc CHECK = ClassDesc.of("com.example", "Check");
    private static final ClassDesc LEVEL = ClassDesc.of("com.example", "Level");

    @Test
    void nestedConsumerMatchesPrebuiltAnnotationUse() {
        SingleAnnotationValue viaConsumer =
                AnnotationValues.nested(CHECK, c -> c.withMember("name", AnnotationValues.literal("nonNull")));

        AnnotationUse prebuilt = new AnnotationBuilder(CHECK)
                .withMember("name", AnnotationValues.literal("nonNull"))
                .build();
        assertThat(viaConsumer).isEqualTo(AnnotationValues.nested(prebuilt));
    }

    @Test
    void arrayMemberBuilderCollectsElementsInOrder() {
        AnnotationUse built = new AnnotationBuilder(CONTRACT)
                .withArrayMember(
                        "checks",
                        arr -> arr.withLiteral("a")
                                .withClass(CHECK)
                                .withEnum(LEVEL, "HIGH")
                                .withNested(CHECK, c -> c.withMember("name", AnnotationValues.literal("b"))))
                .build();

        AnnotationValue expected = AnnotationValues.array(
                AnnotationValues.literal("a"),
                AnnotationValues.classValue(CHECK),
                AnnotationValues.enumValue(LEVEL, "HIGH"),
                AnnotationValues.nested(new AnnotationBuilder(CHECK)
                        .withMember("name", AnnotationValues.literal("b"))
                        .build()));
        assertThat(built.members()).containsExactly(new AnnotationMember("checks", expected));
    }

    @Test
    void arrayMemberBuilderCoversAllLiteralOverloadsAndPrebuiltValue() {
        AnnotationUse built = new AnnotationBuilder(CONTRACT)
                .withArrayMember(
                        "values",
                        arr -> arr.withLiteral(1)
                                .withLiteral(2L)
                                .withLiteral(3.5)
                                .withLiteral(true)
                                .withValue(AnnotationValues.literal("prebuilt")))
                .build();

        AnnotationValue expected = AnnotationValues.array(
                AnnotationValues.literal(1),
                AnnotationValues.literal(2L),
                AnnotationValues.literal(3.5),
                AnnotationValues.literal(true),
                AnnotationValues.literal("prebuilt"));
        assertThat(built.members()).containsExactly(new AnnotationMember("values", expected));
    }

    @Test
    void nestedMemberShortcutMatchesExplicitNesting() {
        AnnotationUse viaShortcut = new AnnotationBuilder(CONTRACT)
                .withNestedMember("check", CHECK, c -> c.withMember("name", AnnotationValues.literal("x")))
                .build();

        AnnotationUse explicit = new AnnotationBuilder(CONTRACT)
                .withMember(
                        "check",
                        AnnotationValues.nested(new AnnotationBuilder(CHECK)
                                .withMember("name", AnnotationValues.literal("x"))
                                .build()))
                .build();
        assertThat(viaShortcut).isEqualTo(explicit);
    }
}
