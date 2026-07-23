package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

/// Compiles a JLS 9.7.4 type-use annotation against a real `@interface` fixture
/// targeted `ElementType.TYPE_USE` with `javac`, proving the emitted source
/// actually type-checks in a type-use position. `javax.annotation.Nonnull` is
/// deliberately not used here: this project has no JSR-305 dependency on its
/// test classpath, and that annotation is declaration-targeted (method, field,
/// parameter, local variable) rather than `TYPE_USE` regardless, so it would
/// not be legal in a type-use position even if it were on the classpath.
class TypeUseAnnotationCompileTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc NON_NULL = ClassDesc.of("me.supcheg.meta", "NonNull");

    private static final String NON_NULL_SRC = """
            package me.supcheg.meta;

            import java.lang.annotation.ElementType;
            import java.lang.annotation.Target;

            @Target(ElementType.TYPE_USE)
            public @interface NonNull {
            }
            """;

    @Test
    void typeUseAnnotationOnAFieldTypeCompiles() {
        AnnotationUse nonNull = new AnnotationUse(NON_NULL, List.of());

        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Holder"),
                cb -> cb.withField("value", Types.of(STRING, nonNull), fb -> fb.withModifiers(Modifier.PRIVATE)));

        Compilation compilation = javac().compile(
                        JavaFileObjects.forSourceString("me.supcheg.meta.NonNull", NON_NULL_SRC),
                        JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }

    @Test
    void typeUseAnnotationOnAnArrayFieldTypeCompiles() {
        AnnotationUse nonNull = new AnnotationUse(NON_NULL, List.of());

        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "ArrayHolder"),
                cb -> cb.withField(
                        "values", Types.array(Types.of(STRING), nonNull), fb -> fb.withModifiers(Modifier.PRIVATE)));

        Compilation compilation = javac().compile(
                        JavaFileObjects.forSourceString("me.supcheg.meta.NonNull", NON_NULL_SRC),
                        JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }

    @Test
    void typeUseAnnotationOnATypeParamBoundCompiles() {
        AnnotationUse nonNull = new AnnotationUse(NON_NULL, List.of());
        ClassDesc number = ClassDesc.of("java.lang", "Number");

        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Box"),
                cb -> cb.withMethod(
                        "identity",
                        Types.typeVar("T"),
                        mb -> mb.withModifiers(Modifier.PUBLIC)
                                .withTypeParam("T", Types.of(number, nonNull))
                                .withParam("value", Types.typeVar("T"))
                                .withBody(b -> b.return_(b.field("value")))));

        Compilation compilation = javac().compile(
                        JavaFileObjects.forSourceString("me.supcheg.meta.NonNull", NON_NULL_SRC),
                        JavaFileObjects.forSourceString(file.qualifiedName(), file.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
