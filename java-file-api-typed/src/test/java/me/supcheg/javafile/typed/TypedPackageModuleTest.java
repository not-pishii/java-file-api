package me.supcheg.javafile.typed;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.ModuleFile;
import me.supcheg.javafile.PackageInfoFile;
import me.supcheg.javafile.facts.AnnotationValues;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.PackageTag_;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static org.assertj.core.api.Assertions.assertThat;

/// The typed `package-info.java` and `module-info.java` of §6.5: a package
/// annotation checked against its targets, and every module directive the
/// core supports.
class TypedPackageModuleTest {

    private static final String PACKAGE_TAG = """
            package me.supcheg.javafile.typed.fixtures;

            import java.lang.annotation.ElementType;
            import java.lang.annotation.Retention;
            import java.lang.annotation.RetentionPolicy;
            import java.lang.annotation.Target;

            @Retention(RetentionPolicy.RUNTIME)
            @Target(ElementType.PACKAGE)
            public @interface PackageTag {
                String value();
            }
            """;

    @Test
    void aPackageInfoRendersAndCompiles() {
        PackageInfoFile file = TypedJavaFile.packageInfo(
                UnsafeFacts.unverifiedClasspath(),
                "me.supcheg.example",
                pb -> pb.withAnnotation(PackageTag_.annotation()
                        .value(AnnotationValues.string("api"))
                        .build()));

        assertThat(file.render()).isEqualTo("""
                        @PackageTag("api")
                        package me.supcheg.example;

                        import me.supcheg.javafile.typed.fixtures.PackageTag;
                        """);

        Compilation compilation = javac().withOptions("-Xlint:all")
                .compile(
                        JavaFileObjects.forSourceString("me.supcheg.example.package-info", file.render()),
                        JavaFileObjects.forSourceString("me.supcheg.javafile.typed.fixtures.PackageTag", PACKAGE_TAG));
        assertThat(compilation).succeededWithoutWarnings();
    }

    @Test
    void aModuleInfoRendersAndCompiles(@TempDir Path tempDir) throws IOException {
        ModuleFile file = TypedJavaFile.module(
                UnsafeFacts.unverifiedClasspath(),
                "com.example.app",
                mb -> mb.requires("java.base")
                        .requiresTransitive("java.sql")
                        .requiresStatic("java.compiler")
                        .exports("com.example.api")
                        .exportsTo("com.example.api.internal", "com.example.client")
                        .opens("com.example.reflect")
                        .opensTo("com.example.reflect.internal", "com.example.client")
                        .uses(ClassDesc.of("com.example.api.Plugin"))
                        .provides(ClassDesc.of("com.example.api.Plugin"), ClassDesc.of("com.example.impl.PluginImpl")));

        assertThat(file.render()).isEqualTo("""
                        module com.example.app {
                            requires java.base;
                            requires transitive java.sql;
                            requires static java.compiler;
                            exports com.example.api;
                            exports com.example.api.internal to com.example.client;
                            opens com.example.reflect;
                            opens com.example.reflect.internal to com.example.client;
                            uses com.example.api.Plugin;
                            provides com.example.api.Plugin with com.example.impl.PluginImpl;
                        }
                        """);

        Path source = tempDir.resolve("src");
        write(source, "module-info.java", file.render());
        write(source, "com/example/api/Api.java", """
                package com.example.api;

                public final class Api {
                }
                """);
        write(source, "com/example/api/Plugin.java", """
                package com.example.api;

                public interface Plugin {
                }
                """);
        write(source, "com/example/api/internal/Internal.java", """
                package com.example.api.internal;

                public final class Internal {
                }
                """);
        write(source, "com/example/impl/PluginImpl.java", """
                package com.example.impl;

                public final class PluginImpl implements com.example.api.Plugin {
                }
                """);
        write(source, "com/example/reflect/Reflect.java", """
                package com.example.reflect;

                public final class Reflect {
                }
                """);
        write(source, "com/example/reflect/internal/ReflectInternal.java", """
                package com.example.reflect.internal;

                public final class ReflectInternal {
                }
                """);
        Path output = tempDir.resolve("out");
        Files.createDirectories(output);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(
                null,
                null,
                null,
                "-Xlint:all",
                "-d",
                output.toString(),
                source.resolve("module-info.java").toString(),
                source.resolve("com/example/api/Api.java").toString(),
                source.resolve("com/example/api/Plugin.java").toString(),
                source.resolve("com/example/api/internal/Internal.java").toString(),
                source.resolve("com/example/impl/PluginImpl.java").toString(),
                source.resolve("com/example/reflect/Reflect.java").toString(),
                source.resolve("com/example/reflect/internal/ReflectInternal.java")
                        .toString());
        assertThat(result).isZero();
    }

    private static void write(Path root, String path, String source) throws IOException {
        Path file = root.resolve(path);
        Files.createDirectories(file.getParent());
        Files.writeString(file, source);
    }
}
