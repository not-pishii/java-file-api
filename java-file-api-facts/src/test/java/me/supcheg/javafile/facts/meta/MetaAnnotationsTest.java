package me.supcheg.javafile.facts.meta;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.classfile.Attributes;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.classfile.attribute.RuntimeInvisibleAnnotationsAttribute;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static org.assertj.core.api.Assertions.assertThat;

/// The retention and targets of `@Facts` and `@GeneratedMetamodel` are what
/// the processor relies on: `CLASS`, so that Gradle's incremental processing
/// and the classpath scan of reused metamodels can read them. Reflection
/// cannot see a `CLASS` annotation, so the retention is checked twice: as
/// declared, and in the class file javac writes for an annotated class.
class MetaAnnotationsTest {

    @Test
    void factsIsAClassRetainedAnnotationOfTypesAndPackages() {
        assertThat(Facts.class.getAnnotation(Retention.class).value()).isEqualTo(RetentionPolicy.CLASS);
        assertThat(Facts.class.getAnnotation(Target.class).value())
                .containsExactlyInAnyOrder(ElementType.TYPE, ElementType.PACKAGE);
    }

    @Test
    void generatedMetamodelIsAClassRetainedAnnotationOfTypes() {
        assertThat(GeneratedMetamodel.class.getAnnotation(Retention.class).value())
                .isEqualTo(RetentionPolicy.CLASS);
        assertThat(GeneratedMetamodel.class.getAnnotation(Target.class).value()).containsExactly(ElementType.TYPE);
    }

    @Test
    void theAnnotationsSurviveIntoTheClassFileButNotIntoTheRuntime() throws IOException {
        Compilation compilation = javac().compile(
                        JavaFileObjects.forSourceString("a.Gen", """
                        package a;

                        import me.supcheg.javafile.facts.meta.Facts;

                        @Facts({String.class, java.util.List.class})
                        final class Gen {}
                        """),
                        JavaFileObjects.forSourceString("a.Meta", """
                        package a;

                        import me.supcheg.javafile.facts.meta.GeneratedMetamodel;

                        @GeneratedMetamodel(of = String.class, fingerprint = "abc", complete = true, format = 2)
                        final class Meta {}
                        """),
                        JavaFileObjects.forSourceString("b.package-info", """
                        @me.supcheg.javafile.facts.meta.Facts(Integer.class)
                        package b;
                        """));
        assertThat(compilation).succeededWithoutWarnings();

        assertThat(invisibleAnnotations(compilation, "a/Gen.class")).containsExactly(Facts.class.descriptorString());
        assertThat(invisibleAnnotations(compilation, "a/Meta.class"))
                .containsExactly(GeneratedMetamodel.class.descriptorString());
        assertThat(invisibleAnnotations(compilation, "b/package-info.class"))
                .containsExactly(Facts.class.descriptorString());
        assertThat(ClassFile.of()
                        .parse(bytes(compilation, "a/Gen.class"))
                        .findAttribute(Attributes.runtimeVisibleAnnotations()))
                .isEmpty();
    }

    @Test
    void theFormatVersionIsACompileTimeConstant() {
        Compilation compilation = javac().compile(JavaFileObjects.forSourceString("a.Use", """
                package a;

                import me.supcheg.javafile.facts.meta.MetamodelFormat;

                final class Use {
                    static final int V = MetamodelFormat.VERSION;
                }
                """));
        assertThat(compilation).succeededWithoutWarnings();

        assertThat(MetamodelFormat.VERSION).isPositive();
    }

    private static Set<String> invisibleAnnotations(Compilation compilation, String classFile) throws IOException {
        ClassModel model = ClassFile.of().parse(bytes(compilation, classFile));
        return model
                .findAttribute(Attributes.runtimeInvisibleAnnotations())
                .map(RuntimeInvisibleAnnotationsAttribute::annotations)
                .orElse(List.of())
                .stream()
                .map(a -> a.className().stringValue())
                .collect(Collectors.toSet());
    }

    private static byte[] bytes(Compilation compilation, String classFile) throws IOException {
        var file = compilation.generatedFiles().stream()
                .filter(f -> f.toUri().getPath().endsWith("/" + classFile))
                .findFirst()
                .orElseThrow(() -> new AssertionError("no " + classFile));
        try (var in = file.openInputStream()) {
            return in.readAllBytes();
        }
    }
}
