package me.supcheg.javafile.render;

import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.render.SourceRenderer.standardFormat;
import static org.assertj.core.api.Assertions.assertThat;

class StandardRendererTest {

    StandardRenderer renderer;

    @BeforeEach
    void setup() {
        renderer = StandardRenderer.instance();
    }

    @Test
    void prependsPackageAndSortedImportsBeforeTheTypeBody() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Messages"));
        ClassDesc bundle = ClassDesc.of("java.util", "ResourceBundle");
        builder.withModifiers(Modifier.FINAL)
                .withField("bundle", Types.of(bundle), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL));

        String rendered = renderer.render("me.supcheg.example", builder.build(), standardFormat());

        assertThat(rendered).isEqualTo("""
                        package me.supcheg.example;

                        import java.util.ResourceBundle;

                        public final class Messages {
                            private final ResourceBundle bundle;
                        }
                        """);
    }

    @Test
    void omitsTheImportBlockWhenThereAreNoImports() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Empty"));

        String rendered = renderer.render("me.supcheg.example", builder.build(), standardFormat());

        assertThat(rendered).isEqualTo("""
                        package me.supcheg.example;

                        public class Empty {
                        }
                        """);
    }

    @Test
    void aCustomIndentUnitIsUsedForNestedBodyLines() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Messages"));
        builder.withModifiers(Modifier.FINAL)
                .withField(
                        "bundle",
                        Types.of(ClassDesc.of("java.util", "ResourceBundle")),
                        fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL));

        String rendered = renderer.render("me.supcheg.example", builder.build(), SourceRenderer.format("  ", "\n"));

        assertThat(rendered).isEqualTo("""
                        package me.supcheg.example;

                        import java.util.ResourceBundle;

                        public final class Messages {
                          private final ResourceBundle bundle;
                        }
                        """);
    }

    @Test
    void aCustomLineSeparatorIsUsedThroughoutTheOutput() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Messages"));
        builder.withModifiers(Modifier.FINAL)
                .withField(
                        "bundle",
                        Types.of(ClassDesc.of("java.util", "ResourceBundle")),
                        fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL));

        String rendered = renderer.render("me.supcheg.example", builder.build(), SourceRenderer.format("    ", "\r\n"));

        assertThat(rendered)
                .isEqualTo("package me.supcheg.example;\r\n" + "\r\n"
                        + "import java.util.ResourceBundle;\r\n"
                        + "\r\n"
                        + "public final class Messages {\r\n"
                        + "    private final ResourceBundle bundle;\r\n"
                        + "}\r\n");
    }
}
