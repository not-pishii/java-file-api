package me.supcheg.javafile;

import me.supcheg.javafile.builder.ModuleBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ModuleFileTest {

    @Test
    void rendersRequiresExportsUsesAndProvides() {
        ModuleFile file = ModuleFile.of(
                "me.supcheg.example",
                mb -> mb.withRequires("java.base")
                        .withRequiresTransitive("java.sql")
                        .withExports("me.supcheg.example.api")
                        .withUses("me.supcheg.example.api.Plugin")
                        .withProvides("me.supcheg.example.api.Plugin", "me.supcheg.example.impl.DefaultPlugin"));

        assertThat(file.render()).isEqualTo("""
                        module me.supcheg.example {
                            requires java.base;
                            requires transitive java.sql;
                            exports me.supcheg.example.api;
                            uses me.supcheg.example.api.Plugin;
                            provides me.supcheg.example.api.Plugin with me.supcheg.example.impl.DefaultPlugin;
                        }
                        """);
    }

    @Test
    void rendersOpenModuleWithNoDirectives() {
        ModuleFile file = ModuleFile.of("me.supcheg.example", ModuleBuilder::withOpen);

        assertThat(file.render()).isEqualTo("""
                        open module me.supcheg.example {
                        }
                        """);
    }

    @Test
    void rendersRequiresStaticAndQualifiedExportsAndOpens() {
        ModuleFile file = ModuleFile.of(
                "me.supcheg.example",
                mb -> mb.withRequiresStatic("java.compiler")
                        .withExportsTo("me.supcheg.example.spi", "me.supcheg.example.impl", "me.supcheg.other")
                        .withOpens("me.supcheg.example.internal")
                        .withOpensTo("me.supcheg.example.reflect", "me.supcheg.framework"));

        assertThat(file.render()).isEqualTo("""
                        module me.supcheg.example {
                            requires static java.compiler;
                            exports me.supcheg.example.spi to me.supcheg.example.impl, me.supcheg.other;
                            opens me.supcheg.example.internal;
                            opens me.supcheg.example.reflect to me.supcheg.framework;
                        }
                        """);
    }

    @Test
    void providesRendersMultipleImplementationsInOrder() {
        ModuleFile file = ModuleFile.of(
                "me.supcheg.example",
                mb -> mb.withProvides(
                        "me.supcheg.example.api.Plugin",
                        "me.supcheg.example.impl.DefaultPlugin",
                        "me.supcheg.example.impl.OtherPlugin"));

        assertThat(file.render()).isEqualTo("""
                        module me.supcheg.example {
                            provides me.supcheg.example.api.Plugin with me.supcheg.example.impl.DefaultPlugin, me.supcheg.example.impl.OtherPlugin;
                        }
                        """);
    }

    @Test
    void rejectsOpensDirectiveInOpenModule() {
        assertThatThrownBy(() -> ModuleFile.of(
                        "me.supcheg.example", mb -> mb.withOpen().withOpens("me.supcheg.example.internal")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("open")
                .hasMessageContaining("opens");
    }

    @Test
    void rejectsDuplicateRequiresForSameModuleName() {
        assertThatThrownBy(() -> ModuleFile.of(
                        "me.supcheg.example", mb -> mb.withRequires("java.sql").withRequires("java.sql")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("java.sql");
    }

    @Test
    void rejectsDuplicateRequiresAcrossDifferentModifierFlavors() {
        assertThatThrownBy(() -> ModuleFile.of(
                        "me.supcheg.example", mb -> mb.withRequires("java.sql").withRequiresTransitive("java.sql")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("java.sql");
    }

    @Test
    void usesAndProvidesRenderUnqualifiedClassNamesWithNoLeadingDot() {
        ModuleFile file = ModuleFile.of(
                "me.supcheg.example",
                mb -> mb.withUses("TopLevelService").withProvides("TopLevelService", "TopLevelImpl"));

        assertThat(file.render()).isEqualTo("""
                        module me.supcheg.example {
                            uses TopLevelService;
                            provides TopLevelService with TopLevelImpl;
                        }
                        """);
    }

    @Test
    void usesAndProvidesRenderNestedServiceAndImplementationTypesWithDotsNotDollarSigns() {
        ClassDesc service = ClassDesc.of("me.supcheg.example.api", "Registry").nested("Plugin");
        ClassDesc impl = ClassDesc.of("me.supcheg.example.impl", "Registry").nested("DefaultPlugin");

        ModuleFile file =
                ModuleFile.of("me.supcheg.example", mb -> mb.withUses(service).withProvides(service, impl));

        assertThat(file.render()).isEqualTo("""
                        module me.supcheg.example {
                            uses me.supcheg.example.api.Registry.Plugin;
                            provides me.supcheg.example.api.Registry.Plugin with me.supcheg.example.impl.Registry.DefaultPlugin;
                        }
                        """);
    }

    @Test
    void openModuleWithNonOpensDirectivesBuildsAndRendersSuccessfully() {
        ModuleFile file = ModuleFile.of(
                "me.supcheg.example",
                mb -> mb.withOpen().withRequires("java.base").withExports("me.supcheg.example.api"));

        assertThat(file.render()).isEqualTo("""
                        open module me.supcheg.example {
                            requires java.base;
                            exports me.supcheg.example.api;
                        }
                        """);
    }

    @Test
    void writeToWritesModuleInfoJavaDirectlyUnderOutputDir(@TempDir Path tempDir) throws IOException {
        ModuleFile file = ModuleFile.of("me.supcheg.example", mb -> mb.withRequires("java.base"));

        file.writeTo(tempDir);

        Path expected = tempDir.resolve("module-info.java");
        assertThat(Files.exists(expected)).isTrue();
        assertThat(Files.readString(expected)).isEqualTo(file.render());
    }
}
