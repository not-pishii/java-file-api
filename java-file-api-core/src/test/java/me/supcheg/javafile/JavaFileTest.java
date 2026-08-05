package me.supcheg.javafile;

import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.nio.file.Files;
import java.nio.file.Path;

import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.literal;
import static org.assertj.core.api.Assertions.assertThat;

class JavaFileTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc BUNDLE = ClassDesc.of("java.util", "ResourceBundle");

    @Test
    void ofExposesPackageSimpleAndQualifiedName() {
        JavaFile file = JavaFile.class_(ClassDesc.of("me.supcheg.example", "Messages"), cb -> {});

        assertThat(file.packageName()).isEqualTo("me.supcheg.example");
        assertThat(file.simpleName()).isEqualTo("Messages");
        assertThat(file.qualifiedName()).isEqualTo("me.supcheg.example.Messages");
    }

    @Test
    void renderProducesTheExpectedGreetingClass() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Messages"),
                cb -> cb.withModifiers(Modifier.FINAL)
                        .withField("bundle", Types.of(BUNDLE), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL))
                        .withMethod(
                                "greeting",
                                Types.of(STRING),
                                mb -> mb.withParam("name", Types.of(STRING))
                                        .withBody(b ->
                                                b.return_(field("bundle").call("getString", literal("greeting"))))));

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;

                        import java.util.ResourceBundle;

                        public final class Messages {
                            private final ResourceBundle bundle;

                            public String greeting(String name) {
                                return bundle.getString("greeting");
                            }
                        }
                        """);
    }

    @Test
    void writeToCreatesThePackageDirectoryAndTheJavaFile(@TempDir Path tempDir) throws IOException {
        JavaFile file = JavaFile.class_(ClassDesc.of("me.supcheg.example", "Empty"), cb -> {});

        file.writeTo(tempDir);

        Path expected = tempDir.resolve("me/supcheg/example/Empty.java");
        assertThat(Files.exists(expected)).isTrue();
        assertThat(Files.readString(expected)).isEqualTo(file.render());
    }

    @Test
    void transformClassRewritesTheWrappedClassDecl() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Config"),
                cb -> cb.withField("count", me.supcheg.javafile.type.PrimitiveTypeRef.INT, fb -> {}));

        JavaFile transformed = file.transformClass((builder, member) -> {
            if (member instanceof me.supcheg.javafile.model.FieldDecl f) {
                java.util.Set<Modifier> mods = new java.util.LinkedHashSet<>(f.modifiers());
                mods.add(Modifier.FINAL);
                builder.accept(new me.supcheg.javafile.model.FieldDecl(
                        f.name(), f.type(), f.annotations(), mods, f.initializer()));
            } else {
                builder.accept(member);
            }
        });

        assertThat(transformed.render()).contains("public final int count;");
    }

    @Test
    void transformEnumRewritesTheWrappedEnumDecl() {
        JavaFile file = JavaFile.enum_(
                ClassDesc.of("me.supcheg.example", "Suit"),
                eb -> eb.withConstant("HEARTS").withConstant("SPADES"));

        JavaFile transformed = file.transformEnum((builder, member) -> builder.accept(member));

        assertThat(transformed.render()).contains("HEARTS, SPADES;");
    }

    @Test
    void transformEnumOnAClassShapedFileThrows() {
        JavaFile file = JavaFile.class_(ClassDesc.of("me.supcheg.example", "Config"), cb -> {});

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> file.transformEnum((builder, member) -> {}))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void transformInterfaceOnAClassShapedFileThrows() {
        JavaFile file = JavaFile.class_(ClassDesc.of("me.supcheg.example", "Config"), cb -> {});

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> file.transformInterface((builder, member) -> {}))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void transformInterfaceRewritesTheWrappedInterfaceDecl() {
        JavaFile file = JavaFile.interface_(
                ClassDesc.of("me.supcheg.example", "Greeter"), ib -> ib.withAbstractMethod("greet", Types.of(STRING)));

        JavaFile transformed = file.transformInterface((builder, member) -> builder.accept(member));

        assertThat(transformed.render()).contains("String greet();");
    }

    @Test
    void transformClassOnAnInterfaceShapedFileThrows() {
        JavaFile file = JavaFile.interface_(ClassDesc.of("me.supcheg.example", "Greeter"), ib -> {});

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> file.transformClass((builder, member) -> {}))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void transformRecordRewritesTheWrappedRecordDecl() {
        JavaFile file = JavaFile.record(
                ClassDesc.of("me.supcheg.example", "Point"),
                rb -> rb.withComponent("x", me.supcheg.javafile.type.PrimitiveTypeRef.INT));

        JavaFile transformed = file.transformRecord((builder, member) -> builder.accept(member));

        assertThat(transformed.render()).contains("record Point(int x)");
    }

    @Test
    void transformRecordOnAClassShapedFileThrows() {
        JavaFile file = JavaFile.class_(ClassDesc.of("me.supcheg.example", "Config"), cb -> {});

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> file.transformRecord((builder, member) -> {}))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void qualifiedNameOmitsTheDotForTheUnnamedPackage() {
        JavaFile file = JavaFile.class_(ClassDesc.of("Empty"), cb -> {});

        assertThat(file.qualifiedName()).isEqualTo("Empty");
    }

    @Test
    void writeToWritesDirectlyIntoTheOutputDirForTheUnnamedPackage(@TempDir Path tempDir) throws IOException {
        JavaFile file = JavaFile.class_(ClassDesc.of("Empty"), cb -> {});

        file.writeTo(tempDir);

        Path expected = tempDir.resolve("Empty.java");
        assertThat(Files.exists(expected)).isTrue();
        assertThat(Files.readString(expected)).isEqualTo(file.render());
    }

    @Test
    void annotationTypeCanBeTransformed() {
        ClassDesc anno = ClassDesc.of("com.example", "Marker");
        ClassDesc string = ClassDesc.of("java.lang", "String");

        JavaFile file = JavaFile.annotationType(anno, ab -> ab.withElement("value", Types.of(string)));
        JavaFile transformed = file.transformAnnotationType((builder, element) -> builder.accept(element));

        assertThat(transformed.render()).isEqualTo(file.render());
    }

    @Test
    void transformAnnotationTypeIdentityTransformPreservesAnnotationsModifiersAndElementDefaults() {
        ClassDesc marker = ClassDesc.of("com.example", "Marker");
        ClassDesc documented = ClassDesc.of("com.example", "Documented");
        ClassDesc string = ClassDesc.of("java.lang", "String");

        JavaFile file = JavaFile.annotationType(
                marker,
                ab -> ab.withAnnotation(documented)
                        .withExactModifiers(java.util.Set.of(Modifier.PUBLIC))
                        .withElement(
                                "value",
                                Types.of(string),
                                me.supcheg.javafile.annotation.AnnotationValues.literal("default")));

        JavaFile transformed = file.transformAnnotationType((builder, element) -> builder.accept(element));

        String rendered = transformed.render();
        assertThat(rendered).contains("@Documented");
        assertThat(rendered).contains("public @interface Marker");
        assertThat(rendered).contains("String value() default \"default\";");
        assertThat(rendered).isEqualTo(file.render());
    }

    @Test
    void transformAnnotationTypeOnAClassShapedFileThrows() {
        JavaFile file = JavaFile.class_(ClassDesc.of("me.supcheg.example", "Config"), cb -> {});

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> file.transformAnnotationType((builder, element) -> {}))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void classFactoryIsNamedAfterTheDeclarationKind() {
        ClassDesc holder = ClassDesc.of("com.example", "Holder");
        assertThat(JavaFile.class_(holder, cb -> {}).qualifiedName()).isEqualTo("com.example.Holder");
    }
}
