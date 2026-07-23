package me.supcheg.javafile;

import me.supcheg.javafile.builder.ModuleBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ModuleFileTest {

    @Test
    void rendersRequiresExportsUsesAndProvides() {
        ModuleFile file = ModuleFile.of(
                "me.supcheg.example",
                mb -> mb.requires("java.base")
                        .requiresTransitive("java.sql")
                        .exports("me.supcheg.example.api")
                        .uses("me.supcheg.example.api.Plugin")
                        .provides("me.supcheg.example.api.Plugin", "me.supcheg.example.impl.DefaultPlugin"));

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
        ModuleFile file = ModuleFile.of("me.supcheg.example", ModuleBuilder::open);

        assertThat(file.render()).isEqualTo("""
                        open module me.supcheg.example {
                        }
                        """);
    }

    @Test
    void rendersRequiresStaticAndQualifiedExportsAndOpens() {
        ModuleFile file = ModuleFile.of(
                "me.supcheg.example",
                mb -> mb.requiresStatic("java.compiler")
                        .exportsTo("me.supcheg.example.spi", "me.supcheg.example.impl", "me.supcheg.other")
                        .opens("me.supcheg.example.internal")
                        .opensTo("me.supcheg.example.reflect", "me.supcheg.framework"));

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
                mb -> mb.provides(
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
        assertThatThrownBy(() ->
                        ModuleFile.of("me.supcheg.example", mb -> mb.open().opens("me.supcheg.example.internal")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("open")
                .hasMessageContaining("opens");
    }

    @Test
    void rejectsDuplicateRequiresForSameModuleName() {
        assertThatThrownBy(() -> ModuleFile.of(
                        "me.supcheg.example", mb -> mb.requires("java.sql").requires("java.sql")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("java.sql");
    }

    @Test
    void rejectsDuplicateRequiresAcrossDifferentModifierFlavors() {
        assertThatThrownBy(() -> ModuleFile.of(
                        "me.supcheg.example", mb -> mb.requires("java.sql").requiresTransitive("java.sql")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("java.sql");
    }

    @Test
    void writeToWritesModuleInfoJavaDirectlyUnderOutputDir(@TempDir Path tempDir) throws IOException {
        ModuleFile file = ModuleFile.of("me.supcheg.example", mb -> mb.requires("java.base"));

        file.writeTo(tempDir);

        Path expected = tempDir.resolve("module-info.java");
        assertThat(Files.exists(expected)).isTrue();
        assertThat(Files.readString(expected)).isEqualTo(file.render());
    }
}
