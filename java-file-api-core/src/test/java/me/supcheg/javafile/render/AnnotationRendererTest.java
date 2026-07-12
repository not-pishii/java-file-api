package me.supcheg.javafile.render;

import me.supcheg.javafile.annotation.AnnotationBuilder;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.annotation.AnnotationValues;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static me.supcheg.javafile.render.SourceRenderer.standardFormat;
import static org.assertj.core.api.Assertions.assertThat;

class AnnotationRendererTest {

    private static final ClassDesc CONTRACT_META = ClassDesc.of("me.supcheg.meta", "ContractMeta");
    private static final ClassDesc MESSAGE_META = ClassDesc.of("me.supcheg.meta", "MessageMeta");
    private static final ClassDesc PARAM_META = ClassDesc.of("me.supcheg.meta", "ParamMeta");
    private static final ClassDesc LEVEL = ClassDesc.of("me.supcheg.meta", "Level");
    private static final ClassDesc NULLABLE = ClassDesc.of("me.supcheg.meta", "Nullable");

    @Test
    void markerAnnotationHasNoParens() {
        AnnotationUse use = new AnnotationUse(NULLABLE, List.of());
        Context ctx = Context.of(standardFormat(), new ImportManager("p"));

        assertThat(AnnotationRenderer.renderAnnotations(List.of(use), ctx)).isEqualTo("@Nullable\n");
    }

    @Test
    void emptyAnnotationListRendersEmptyString() {
        Context ctx = Context.of(standardFormat(), new ImportManager("p"));

        assertThat(AnnotationRenderer.renderAnnotations(List.of(), ctx)).isEmpty();
    }

    @Test
    void multipleNamedMembersRenderCommaSeparatedAssignments() {
        AnnotationUse use = new AnnotationBuilder(MESSAGE_META)
                .withMember("key", AnnotationValues.literal("greeting"))
                .withMember("count", AnnotationValues.literal(1))
                .build();
        Context ctx = Context.of(standardFormat(), new ImportManager("p"));

        assertThat(AnnotationRenderer.renderAnnotations(List.of(use), ctx))
                .isEqualTo("@MessageMeta(key = \"greeting\", count = 1)\n");
    }

    @Test
    void singleValueMemberUsesShorthand() {
        AnnotationUse use = new AnnotationBuilder(MESSAGE_META)
                .withMember("value", AnnotationValues.literal("greeting"))
                .build();
        Context ctx = Context.of(standardFormat(), new ImportManager("p"));

        assertThat(AnnotationRenderer.renderAnnotations(List.of(use), ctx)).isEqualTo("@MessageMeta(\"greeting\")\n");
    }

    @Test
    void classAndEnumValuesReferenceTheirTypesAndRegisterImports() {
        ImportManager imports = new ImportManager("p");
        AnnotationUse use = new AnnotationBuilder(MESSAGE_META)
                .withMember("type", AnnotationValues.classValue(PARAM_META))
                .withMember("level", AnnotationValues.enumValue(LEVEL, "HIGH"))
                .build();
        Context ctx = Context.of(standardFormat(), imports);

        assertThat(AnnotationRenderer.renderAnnotations(List.of(use), ctx))
                .isEqualTo("@MessageMeta(type = ParamMeta.class, level = Level.HIGH)\n");
        assertThat(imports.sortedImports())
                .containsExactly("me.supcheg.meta.Level", "me.supcheg.meta.MessageMeta", "me.supcheg.meta.ParamMeta");
    }

    @Test
    void emptyArrayValueRendersEmptyBraces() {
        AnnotationUse use = new AnnotationBuilder(MESSAGE_META)
                .withMember("params", AnnotationValues.array())
                .build();
        Context ctx = Context.of(standardFormat(), new ImportManager("p"));

        assertThat(AnnotationRenderer.renderAnnotations(List.of(use), ctx)).isEqualTo("@MessageMeta(params = {})\n");
    }

    @Test
    void multiElementArrayValueSeparatesElementsWithCommas() {
        AnnotationUse use = new AnnotationBuilder(MESSAGE_META)
                .withMember(
                        "levels",
                        AnnotationValues.array(
                                AnnotationValues.enumValue(LEVEL, "HIGH"), AnnotationValues.enumValue(LEVEL, "LOW")))
                .build();
        Context ctx = Context.of(standardFormat(), new ImportManager("p"));

        assertThat(AnnotationRenderer.renderAnnotations(List.of(use), ctx)).isEqualTo("""
                        @MessageMeta(levels = {
                            Level.HIGH,
                            Level.LOW
                        })
                        """);
    }

    @Test
    void nestedAnnotationArrayRendersMultilineWithIncreasedPad() {
        AnnotationUse paramMeta = new AnnotationBuilder(PARAM_META)
                .withMember("name", AnnotationValues.literal("who"))
                .build();
        AnnotationUse messageMeta = new AnnotationBuilder(MESSAGE_META)
                .withMember("key", AnnotationValues.literal("greeting"))
                .withMember("params", AnnotationValues.array(AnnotationValues.nested(paramMeta)))
                .build();
        AnnotationUse contractMeta = new AnnotationBuilder(CONTRACT_META)
                .withMember("value", AnnotationValues.array(AnnotationValues.nested(messageMeta)))
                .build();
        Context ctx = Context.of(standardFormat(), new ImportManager("p"));

        assertThat(AnnotationRenderer.renderAnnotations(List.of(contractMeta), ctx))
                .isEqualTo("""
                        @ContractMeta({
                            @MessageMeta(key = "greeting", params = {
                                @ParamMeta(name = "who")
                            })
                        })
                        """);
    }

    @Test
    void multipleAnnotationsRenderOnePerLine() {
        AnnotationUse a = new AnnotationUse(NULLABLE, List.of());
        AnnotationUse b = new AnnotationUse(LEVEL, List.of());
        Context ctx = Context.of(standardFormat(), new ImportManager("p"));

        assertThat(AnnotationRenderer.renderAnnotations(List.of(a, b), ctx)).isEqualTo("@Nullable\n@Level\n");
    }

    @Test
    void inlineAnnotationsRenderSpaceSeparatedWithTrailingSpace() {
        AnnotationUse a = new AnnotationUse(NULLABLE, List.of());
        Context ctx = Context.of(standardFormat(), new ImportManager("p"));

        assertThat(AnnotationRenderer.renderInlineAnnotations(List.of(a), ctx)).isEqualTo("@Nullable ");
    }

    @Test
    void emptyInlineAnnotationsRenderEmptyString() {
        Context ctx = Context.of(standardFormat(), new ImportManager("p"));

        assertThat(AnnotationRenderer.renderInlineAnnotations(List.of(), ctx)).isEmpty();
    }
}
