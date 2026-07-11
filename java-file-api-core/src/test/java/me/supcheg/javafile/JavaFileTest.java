package me.supcheg.javafile;

import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class JavaFileTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc BUNDLE = ClassDesc.of("java.util", "ResourceBundle");

    @Test
    void ofExposesPackageSimpleAndQualifiedName() {
        JavaFile file = JavaFile.of(ClassDesc.of("me.supcheg.example", "Messages"), cb -> {});

        assertThat(file.packageName()).isEqualTo("me.supcheg.example");
        assertThat(file.simpleName()).isEqualTo("Messages");
        assertThat(file.qualifiedName()).isEqualTo("me.supcheg.example.Messages");
    }

    @Test
    void renderProducesTheExpectedGreetingClass() {
        JavaFile file = JavaFile.of(ClassDesc.of("me.supcheg.example", "Messages"), cb -> cb.withModifiers(
                        Modifier.FINAL)
                .withField("bundle", Types.of(BUNDLE), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL))
                .withMethod("greeting", Types.of(STRING), mb -> mb.withParam("name", Types.of(STRING))
                        .withBody(b -> b.return_(b.call(b.field("bundle"), "getString", b.literal("greeting"))))));

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
        JavaFile file = JavaFile.of(ClassDesc.of("me.supcheg.example", "Empty"), cb -> {});

        file.writeTo(tempDir);

        Path expected = tempDir.resolve("me/supcheg/example/Empty.java");
        assertThat(Files.exists(expected)).isTrue();
        assertThat(Files.readString(expected)).isEqualTo(file.render());
    }

    @Test
    void transformClassRewritesTheWrappedClassDecl() {
        JavaFile file = JavaFile.of(
                ClassDesc.of("me.supcheg.example", "Config"),
                cb -> cb.withField("count", me.supcheg.javafile.type.PrimitiveTypeRef.INT, fb -> {}));

        JavaFile transformed = file.transformClass((builder, member) -> {
            if (member instanceof me.supcheg.javafile.model.FieldDecl f) {
                java.util.Set<Modifier> mods = new java.util.LinkedHashSet<>(f.modifiers());
                mods.add(Modifier.FINAL);
                builder.accept(new me.supcheg.javafile.model.FieldDecl(f.name(), f.type(), mods, f.initializer()));
            } else {
                builder.accept(member);
            }
        });

        assertThat(transformed.render()).contains("public final int count;");
    }

    @Test
    void transformEnumRewritesTheWrappedEnumDecl() {
        JavaFile file = JavaFile.enum_(ClassDesc.of("me.supcheg.example", "Suit"), eb -> eb.withConstant("HEARTS")
                .withConstant("SPADES"));

        JavaFile transformed = file.transformEnum((builder, member) -> builder.accept(member));

        assertThat(transformed.render()).contains("HEARTS, SPADES;");
    }

    @Test
    void transformEnumOnAClassShapedFileThrows() {
        JavaFile file = JavaFile.of(ClassDesc.of("me.supcheg.example", "Config"), cb -> {});

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> file.transformEnum((builder, member) -> {}))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void transformInterfaceOnAClassShapedFileThrows() {
        JavaFile file = JavaFile.of(ClassDesc.of("me.supcheg.example", "Config"), cb -> {});

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> file.transformInterface((builder, member) -> {}))
                .isInstanceOf(IllegalStateException.class);
    }
}
