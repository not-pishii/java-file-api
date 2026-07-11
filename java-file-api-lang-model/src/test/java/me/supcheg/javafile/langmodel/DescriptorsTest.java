package me.supcheg.javafile.langmodel;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import java.lang.constant.ClassDesc;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DescriptorsTest {

    @Test
    void mapsPrimitiveArrayAndDeclaredFieldTypesToTypeRefs() {
        Map<String, TypeMirror> captured = capture("""
                package test;
                import java.util.List;
                public class Fixture {
                    String name;
                    int count;
                    List<String> items;
                    String[] tags;
                }
                """);

        assertThat(Descriptors.toTypeRef(captured.get("name")))
                .isEqualTo(Types.of(ClassDesc.of("java.lang", "String")));
        assertThat(Descriptors.toTypeRef(captured.get("count"))).isEqualTo(PrimitiveTypeRef.INT);
        assertThat(Descriptors.toTypeRef(captured.get("items")))
                .isEqualTo(Types.parameterized(
                        ClassDesc.of("java.util", "List"), Types.exact(Types.of(ClassDesc.of("java.lang", "String")))));
        assertThat(Descriptors.toTypeRef(captured.get("tags")))
                .isEqualTo(Types.array(Types.of(ClassDesc.of("java.lang", "String"))));
    }

    @Test
    void mapsAllPrimitiveKindsToTypeRefs() {
        Map<String, TypeMirror> captured = capture("""
                package test;
                public class Fixture {
                    boolean f1;
                    byte f2;
                    short f3;
                    int f4;
                    long f5;
                    char f6;
                    float f7;
                    double f8;
                }
                """);

        assertThat(Descriptors.toTypeRef(captured.get("f1"))).isEqualTo(PrimitiveTypeRef.BOOLEAN);
        assertThat(Descriptors.toTypeRef(captured.get("f2"))).isEqualTo(PrimitiveTypeRef.BYTE);
        assertThat(Descriptors.toTypeRef(captured.get("f3"))).isEqualTo(PrimitiveTypeRef.SHORT);
        assertThat(Descriptors.toTypeRef(captured.get("f4"))).isEqualTo(PrimitiveTypeRef.INT);
        assertThat(Descriptors.toTypeRef(captured.get("f5"))).isEqualTo(PrimitiveTypeRef.LONG);
        assertThat(Descriptors.toTypeRef(captured.get("f6"))).isEqualTo(PrimitiveTypeRef.CHAR);
        assertThat(Descriptors.toTypeRef(captured.get("f7"))).isEqualTo(PrimitiveTypeRef.FLOAT);
        assertThat(Descriptors.toTypeRef(captured.get("f8"))).isEqualTo(PrimitiveTypeRef.DOUBLE);
    }

    @Test
    void mapsWildcardTypeArgumentsToTypeArgs() {
        Map<String, TypeMirror> captured = capture("""
                package test;
                import java.util.List;
                public class Fixture {
                    List<? extends Number> upper;
                    List<? super Number> lower;
                    List<?> unbounded;
                }
                """);

        assertThat(Descriptors.toTypeRef(captured.get("upper")))
                .isEqualTo(Types.parameterized(
                        ClassDesc.of("java.util", "List"),
                        Types.extendsBound(Types.of(ClassDesc.of("java.lang", "Number")))));
        assertThat(Descriptors.toTypeRef(captured.get("lower")))
                .isEqualTo(Types.parameterized(
                        ClassDesc.of("java.util", "List"),
                        Types.superBound(Types.of(ClassDesc.of("java.lang", "Number")))));
        assertThat(Descriptors.toTypeRef(captured.get("unbounded")))
                .isEqualTo(Types.parameterized(ClassDesc.of("java.util", "List"), Types.unbounded()));
    }

    @Test
    void throwsIllegalArgumentExceptionForUnsupportedTypeMirrorKind() {
        Map<String, TypeMirror> captured = capture("""
                package test;
                public class Fixture<T> {
                    T value;
                }
                """);

        assertThatThrownBy(() -> Descriptors.toTypeRef(captured.get("value")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("unsupported type mirror kind");
    }

    @Test
    void mapsTopLevelTypeInDefaultPackageToClassDesc() {
        Map<String, TypeMirror> captured = capture("""
                public class Fixture {
                    Fixture self;
                }
                """);

        assertThat(Descriptors.toClassDesc((DeclaredType) captured.get("self"))).isEqualTo(ClassDesc.of("", "Fixture"));
    }

    @Test
    void throwsUnsupportedOperationExceptionForNestedTypeElement() {
        Map<String, TypeMirror> captured = capture("""
                package test;
                public class Fixture {
                    Nested nested;
                    public static class Nested {}
                }
                """);

        TypeMirror nestedMirror = captured.get("nested");
        assertThatThrownBy(() -> Descriptors.toTypeRef(nestedMirror))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("nested types are not supported");
    }

    private static Map<String, TypeMirror> capture(String source) {
        Map<String, TypeMirror> captured = new LinkedHashMap<>();

        Compilation compilation = javac().withProcessors(new CapturingProcessor(captured))
                .compile(JavaFileObjects.forSourceString("test.Fixture", source));

        assertThat(compilation).succeededWithoutWarnings();
        return captured;
    }

    private static final class CapturingProcessor extends AbstractProcessor {
        private final Map<String, TypeMirror> captured;

        CapturingProcessor(Map<String, TypeMirror> captured) {
            this.captured = captured;
        }

        @Override
        public Set<String> getSupportedAnnotationTypes() {
            return Set.of("*");
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latestSupported();
        }

        @Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
            for (Element root : roundEnv.getRootElements()) {
                if (root.getKind() == ElementKind.CLASS || root.getKind() == ElementKind.INTERFACE) {
                    for (VariableElement field : ElementFilter.fieldsIn(root.getEnclosedElements())) {
                        captured.put(field.getSimpleName().toString(), field.asType());
                    }
                }
            }
            return false;
        }
    }
}
