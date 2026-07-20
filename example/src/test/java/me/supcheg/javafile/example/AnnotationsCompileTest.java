package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.annotation.AnnotationBuilder;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.model.RecordComponent;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

/// Compiles use-site annotations (this library never declares `@interface`s
/// itself) against real `@interface` fixtures with `javac`, exercising the
/// full [me.supcheg.javafile.annotation] model and [AnnotationBuilder]
/// end to end: nested annotation arrays, class/enum-shaped values are
/// covered at the unit level in `AnnotationRendererTest`; this test proves
/// the emitted source actually type-checks.
class AnnotationsCompileTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc CONTRACT_META = ClassDesc.of("me.supcheg.meta", "ContractMeta");
    private static final ClassDesc MESSAGE_META = ClassDesc.of("me.supcheg.meta", "MessageMeta");
    private static final ClassDesc PARAM_META = ClassDesc.of("me.supcheg.meta", "ParamMeta");
    private static final ClassDesc NULLABLE = ClassDesc.of("me.supcheg.meta", "Nullable");

    private static final String CONTRACT_META_SRC = """
            package me.supcheg.meta;

            public @interface ContractMeta {
                MessageMeta[] value();
            }
            """;
    private static final String MESSAGE_META_SRC = """
            package me.supcheg.meta;

            public @interface MessageMeta {
                String key();
                ParamMeta[] params() default {};
            }
            """;
    private static final String PARAM_META_SRC = """
            package me.supcheg.meta;

            public @interface ParamMeta {
                String name();
            }
            """;
    private static final String NULLABLE_SRC = """
            package me.supcheg.meta;

            public @interface Nullable {
            }
            """;

    @Test
    void classAnnotatedWithANestedAnnotationArrayCompiles() {
        AnnotationUse paramMeta = new AnnotationBuilder(PARAM_META)
                .withMember("name", AnnotationValues.literal("who"))
                .build();
        AnnotationUse messageMeta = new AnnotationBuilder(MESSAGE_META)
                .withMember("key", AnnotationValues.literal("greeting"))
                .withMember("params", AnnotationValues.array(AnnotationValues.nested(paramMeta)))
                .build();

        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Greeter"),
                cb -> cb.withAnnotation(
                                CONTRACT_META,
                                ab -> ab.withMember(
                                        "value", AnnotationValues.array(AnnotationValues.nested(messageMeta))))
                        .withMethod("greeting", Types.of(STRING), mb -> mb.withBody(b -> b.return_(b.literal("hi")))));

        Compilation compilation = javac().compile(
                        JavaFileObjects.forSourceString("me.supcheg.meta.ContractMeta", CONTRACT_META_SRC),
                        JavaFileObjects.forSourceString("me.supcheg.meta.MessageMeta", MESSAGE_META_SRC),
                        JavaFileObjects.forSourceString("me.supcheg.meta.ParamMeta", PARAM_META_SRC),
                        JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }

    @Test
    void annotatedParameterAndRecordComponentCompile() {
        AnnotationUse nullable = new AnnotationUse(NULLABLE, List.of());

        JavaFile withParam = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Renamer"),
                cb -> cb.withMethod(
                        "rename",
                        Types.of(STRING),
                        mb -> mb.withParam(new Param("name", Types.of(STRING), List.of(nullable)))
                                .withBody(b -> b.return_(b.field("name")))));

        JavaFile withComponent = JavaFile.record(
                ClassDesc.of("me.supcheg.example", "Box"),
                rb -> rb.withComponent(new RecordComponent("value", Types.of(STRING), List.of(nullable))));

        Compilation compilation = javac().compile(
                        JavaFileObjects.forSourceString("me.supcheg.meta.Nullable", NULLABLE_SRC),
                        JavaFileObjects.forSourceString(withParam.qualifiedName(), withParam.render()),
                        JavaFileObjects.forSourceString(withComponent.qualifiedName(), withComponent.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
