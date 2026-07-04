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
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import java.lang.constant.ClassDesc;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static org.assertj.core.api.Assertions.assertThat;

class DescriptorsTest {

    @Test
    void mapsPrimitiveArrayAndDeclaredFieldTypesToTypeRefs() {
        Map<String, TypeMirror> captured = new LinkedHashMap<>();

        Compilation compilation = javac().withProcessors(new CapturingProcessor(captured))
                .compile(JavaFileObjects.forSourceString("test.Fixture", """
                        package test;
                        import java.util.List;
                        public class Fixture {
                            String name;
                            int count;
                            List<String> items;
                            String[] tags;
                        }
                        """));

        assertThat(compilation).succeededWithoutWarnings();
        assertThat(Descriptors.toTypeRef(captured.get("name")))
                .isEqualTo(Types.of(ClassDesc.of("java.lang", "String")));
        assertThat(Descriptors.toTypeRef(captured.get("count"))).isEqualTo(PrimitiveTypeRef.INT);
        assertThat(Descriptors.toTypeRef(captured.get("items")))
                .isEqualTo(Types.parameterized(
                        ClassDesc.of("java.util", "List"), Types.exact(Types.of(ClassDesc.of("java.lang", "String")))));
        assertThat(Descriptors.toTypeRef(captured.get("tags")))
                .isEqualTo(Types.array(Types.of(ClassDesc.of("java.lang", "String"))));
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
                if (root.getKind() == ElementKind.CLASS) {
                    for (VariableElement field : ElementFilter.fieldsIn(root.getEnclosedElements())) {
                        captured.put(field.getSimpleName().toString(), field.asType());
                    }
                }
            }
            return false;
        }
    }
}
