package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.AnnotationValues;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.RenderedJava;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.lang.annotation.ElementType_;
import me.supcheg.javafile.typed.testfacts.java.lang.annotation.RetentionPolicy_;
import me.supcheg.javafile.typed.testfacts.java.lang.annotation.Retention_;
import me.supcheg.javafile.typed.testfacts.java.lang.annotation.Target_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Tag_;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.InputStream;
import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

/// The typed annotation type of §6.5: elements with the types JLS 9.6.1
/// allows, a default, and meta-annotations through the annotation API.
class TypedAnnotationTypeCompileTest {

    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Route");

    private static JavaFile route() {
        return TypedJavaFile.annotationType(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedAnnotationTypeSpec() {
                    @Override
                    public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                        ab.withAnnotation(Retention_.annotation()
                                .value(AnnotationValues.enum_(RetentionPolicy_.RUNTIME))
                                .build());
                        ab.withAnnotation(Target_.annotation()
                                .value(AnnotationValues.array(AnnotationValues.enum_(ElementType_.TYPE)))
                                .build());
                        ab.withAnnotation(Tag_.annotation()
                                .value(AnnotationValues.string("t"))
                                .build());
                        ab.element("value", String_.TOKEN);
                        ab.element("priority", PrimitiveToken.INT, AnnotationValues.int_(0));
                        return ab;
                    }
                });
    }

    private static JavaFileObject annotated() {
        return JavaFileObjects.forSourceString("me.supcheg.example.Endpoint", """
                package me.supcheg.example;

                @Route("endpoint")
                public final class Endpoint {
                }
                """);
    }

    @Test
    void everyElementTypeJls961AllowsRendersAndCompiles() {
        JavaFile file = TypedJavaFile.annotationType(
                UnsafeFacts.unverifiedClasspath(),
                ClassDesc.of("me.supcheg.example", "Every"),
                new TypedJavaFile.TypedAnnotationTypeSpec() {
                    @Override
                    public <Self> TypedAnnotationTypeBuilder<Self> build(TypedAnnotationTypeBuilder<Self> ab) {
                        ab.element("text", String_.TOKEN);
                        ab.element("type", me.supcheg.javafile.typed.testfacts.java.lang.Class_.ANY);
                        ab.element(
                                "signal",
                                me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_.TOKEN);
                        ab.annotationElement("tag", Tag_.ANNOTATION);
                        ab.element("texts", me.supcheg.javafile.facts.ArrayToken.of(String_.TOKEN));
                        ab.element(
                                "signals",
                                me.supcheg.javafile.facts.ArrayToken.of(
                                        me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Signal_
                                                .TOKEN));
                        ab.annotationArrayElement("tags", Tag_.ANNOTATION);
                        return ab;
                    }
                });

        assertThat(file.render()).contains("public @interface Every").contains("Class<?> type();");
        assertThat(RenderedJava.compile(file)).succeededWithoutWarnings();
    }

    @Test
    void anAnnotationTypeRendersCompilesAndIsReadAtRuntime() throws Exception {
        JavaFile file = route();

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;

                        import java.lang.annotation.ElementType;
                        import java.lang.annotation.Retention;
                        import java.lang.annotation.RetentionPolicy;
                        import java.lang.annotation.Target;
                        import me.supcheg.javafile.typed.fixtures.Tag;

                        @Retention(RetentionPolicy.RUNTIME)
                        @Target({
                            ElementType.TYPE
                        })
                        @Tag("t")
                        public @interface Route {
                            String value();
                            int priority() default 0;
                        }
                        """);

        Compilation compilation = RenderedJava.compile(file, annotated());
        assertThat(compilation).succeededWithoutWarnings();

        Loader loader = new Loader(compilation);
        Class<?> route = loader.define("me.supcheg.example.Route");
        Class<?> endpoint = loader.define("me.supcheg.example.Endpoint");
        Object annotation = endpoint.getAnnotation(route.asSubclass(java.lang.annotation.Annotation.class));
        assertThat(annotation).isNotNull();
        assertThat(route.getMethod("value").invoke(annotation)).isEqualTo("endpoint");
        assertThat(route.getMethod("priority").invoke(annotation)).isEqualTo(0);
    }

    private static final class Loader extends ClassLoader {
        private final Compilation compilation;

        Loader(Compilation compilation) {
            super(TypedAnnotationTypeCompileTest.class.getClassLoader());
            this.compilation = compilation;
        }

        Class<?> define(String binaryName) throws IOException {
            JavaFileObject classFile = compilation
                    .generatedFile(StandardLocation.CLASS_OUTPUT, binaryName.replace('.', '/') + ".class")
                    .orElseThrow();
            byte[] bytes;
            try (InputStream in = classFile.openInputStream()) {
                bytes = in.readAllBytes();
            }
            return defineClass(binaryName, bytes, 0, bytes.length);
        }
    }
}
