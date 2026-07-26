package me.supcheg.javafile.render;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.ModuleFile;
import me.supcheg.javafile.PackageInfoFile;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.RequiresDirective;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

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

        String rendered = renderer.render(new JavaFile.Meta("me.supcheg.example", builder.build()), standardFormat());

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

        String rendered = renderer.render(new JavaFile.Meta("me.supcheg.example", builder.build()), standardFormat());

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

        String rendered = renderer.render(
                new JavaFile.Meta("me.supcheg.example", builder.build()), SourceRenderer.format("  ", "\n"));

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

        String rendered = renderer.render(
                new JavaFile.Meta("me.supcheg.example", builder.build()), SourceRenderer.format("    ", "\r\n"));

        assertThat(rendered)
                .isEqualTo("package me.supcheg.example;\r\n" + "\r\n"
                        + "import java.util.ResourceBundle;\r\n"
                        + "\r\n"
                        + "public final class Messages {\r\n"
                        + "    private final ResourceBundle bundle;\r\n"
                        + "}\r\n");
    }

    @Test
    void dispatchesToModuleDirectiveRenderingForAModuleMeta() {
        var meta = new ModuleFile.Meta(
                false, "me.supcheg.example", List.of(new RequiresDirective("java.base", false, false)));

        String rendered = renderer.render(meta, standardFormat());

        assertThat(rendered).isEqualTo("""
                        module me.supcheg.example {
                            requires java.base;
                        }
                        """);
    }

    @Test
    void aCustomFormatIsHonoredWhenRenderingAModuleMeta() {
        var meta = new ModuleFile.Meta(
                true, "me.supcheg.example", List.of(new RequiresDirective("java.base", false, false)));

        String rendered = renderer.render(meta, SourceRenderer.format("  ", "\r\n"));

        assertThat(rendered).isEqualTo("open module me.supcheg.example {\r\n" + "  requires java.base;\r\n" + "}\r\n");
    }

    @Test
    void dispatchesToPackageAnnotationRenderingForAPackageInfoMeta() {
        var nonNullByDefault =
                new AnnotationUse(ClassDesc.of("javax.annotation", "ParametersAreNonnullByDefault"), List.of());
        var meta = new PackageInfoFile.Meta("me.supcheg.example", List.of(nonNullByDefault));

        String rendered = renderer.render(meta, standardFormat());

        assertThat(rendered).isEqualTo("""
                        @ParametersAreNonnullByDefault
                        package me.supcheg.example;

                        import javax.annotation.ParametersAreNonnullByDefault;
                        """);
    }
}
