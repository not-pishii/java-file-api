package me.supcheg.javafile.annotation;

import me.supcheg.javafile.code.BooleanLiteral;
import me.supcheg.javafile.code.DoubleLiteral;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.code.LongLiteral;
import me.supcheg.javafile.code.StringLiteral;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AnnotationModelTest {

    private static final ClassDesc META = ClassDesc.of("me.supcheg.meta", "MessageMeta");
    private static final ClassDesc LEVEL = ClassDesc.of("me.supcheg.meta", "Level");

    @Test
    void literalFactoriesCoverEachConstantType() {
        assertThat(AnnotationValues.literal("key")).isEqualTo(new LiteralValue(new StringLiteral("key")));
        assertThat(AnnotationValues.literal(1)).isEqualTo(new LiteralValue(new IntLiteral(1)));
        assertThat(AnnotationValues.literal(1L)).isEqualTo(new LiteralValue(new LongLiteral(1L)));
        assertThat(AnnotationValues.literal(1.5)).isEqualTo(new LiteralValue(new DoubleLiteral(1.5)));
        assertThat(AnnotationValues.literal(true)).isEqualTo(new LiteralValue(new BooleanLiteral(true)));
    }

    @Test
    void classEnumNestedAndArrayFactoriesCreateMatchingNodes() {
        AnnotationUse nested = new AnnotationUse(META, List.of());

        assertThat(AnnotationValues.classValue(META)).isEqualTo(new ClassValue(META));
        assertThat(AnnotationValues.enumValue(LEVEL, "HIGH")).isEqualTo(new EnumValue(LEVEL, "HIGH"));
        assertThat(AnnotationValues.nested(nested)).isEqualTo(new NestedAnnotationValue(nested));
        assertThat(AnnotationValues.array(AnnotationValues.classValue(META)))
                .isEqualTo(new ArrayValue(List.of(new ClassValue(META))));
    }

    @Test
    void builderCollectsMembersInOrder() {
        AnnotationUse use = new AnnotationBuilder(META)
                .withMember("key", AnnotationValues.literal("greeting"))
                .withMember("count", AnnotationValues.literal(1))
                .build();

        assertThat(use.type()).isEqualTo(META);
        assertThat(use.members())
                .containsExactly(
                        new AnnotationMember("key", AnnotationValues.literal("greeting")),
                        new AnnotationMember("count", AnnotationValues.literal(1)));
    }

    @Test
    void arrayValueAndAnnotationUseCopyTheirListsDefensively() {
        var elements = new java.util.ArrayList<SingleAnnotationValue>(List.of(new ClassValue(META)));
        ArrayValue array = new ArrayValue(elements);
        elements.clear();

        var members = new java.util.ArrayList<AnnotationMember>(
                List.of(new AnnotationMember("value", AnnotationValues.literal(1))));
        AnnotationUse use = new AnnotationUse(META, members);
        members.clear();

        assertThat(array.elements()).hasSize(1);
        assertThat(use.members()).hasSize(1);
    }

    @Test
    void memberAcceptsValidName() {
        AnnotationMember member = new AnnotationMember("value", AnnotationValues.literal(1));

        assertThat(member.name()).isEqualTo("value");
    }

    @Test
    void memberRejectsReservedKeywordAsName() {
        assertThatThrownBy(() -> new AnnotationMember("class", AnnotationValues.literal(1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void memberRejectsLeadingDigitInName() {
        assertThatThrownBy(() -> new AnnotationMember("1value", AnnotationValues.literal(1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void enumValueAcceptsValidConstantName() {
        EnumValue value = new EnumValue(LEVEL, "HIGH");

        assertThat(value.constant()).isEqualTo("HIGH");
    }

    @Test
    void enumValueRejectsReservedKeywordAsConstantName() {
        assertThatThrownBy(() -> new EnumValue(LEVEL, "class")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void enumValueRejectsLeadingDigitInConstantName() {
        assertThatThrownBy(() -> new EnumValue(LEVEL, "1HIGH")).isInstanceOf(IllegalArgumentException.class);
    }
}
