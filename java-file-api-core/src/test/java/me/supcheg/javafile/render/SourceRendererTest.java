package me.supcheg.javafile.render;

import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

class SourceRendererTest {

    @Test
    void prependsPackageAndSortedImportsBeforeTheTypeBody() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Messages"));
        ClassDesc bundle = ClassDesc.of("java.util", "ResourceBundle");
        builder.withModifiers(Modifier.FINAL)
                .withField("bundle", Types.of(bundle), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL));

        String rendered = SourceRenderer.render("me.supcheg.example", builder.build());

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

        String rendered = SourceRenderer.render("me.supcheg.example", builder.build());

        assertThat(rendered).isEqualTo("""
                        package me.supcheg.example;

                        public class Empty {
                        }
                        """);
    }
}
