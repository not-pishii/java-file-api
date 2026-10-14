package me.supcheg.javafile;

import me.supcheg.javafile.annotation.AnnotationUse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackageInfoFileTest {

    @Test
    void rendersAnnotationBeforePackageDeclaration() {
        AnnotationUse nonNullByDefault =
                new AnnotationUse(ClassDesc.of("javax.annotation", "ParametersAreNonnullByDefault"), List.of());
        PackageInfoFile file = PackageInfoFile.of("me.supcheg.example", nonNullByDefault);

        assertThat(file.render()).isEqualTo("""
                        @ParametersAreNonnullByDefault
                        package me.supcheg.example;

                        import javax.annotation.ParametersAreNonnullByDefault;
                        """);
    }

    @Test
    void rendersPackageDeclarationOnlyWhenThereAreNoAnnotations() {
        PackageInfoFile file = PackageInfoFile.of("me.supcheg.example");

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;
                        """);
    }

    @Test
    void writeToCreatesThePackageDirectoryAndThePackageInfoJavaFile(@TempDir Path tempDir) throws IOException {
        PackageInfoFile file = PackageInfoFile.of("me.supcheg.example");

        file.writeTo(tempDir);

        Path expected = tempDir.resolve("me/supcheg/example/package-info.java");
        assertThat(Files.exists(expected)).isTrue();
        assertThat(Files.readString(expected)).isEqualTo(file.render());
    }

    @Test
    void aPackageInfoIsOfAPackageNameThatIsOne() {
        // `package a.int.b;` is no Java (JLS 7.4.1)
        assertThatThrownBy(() -> PackageInfoFile.of("a.int.b"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("not a valid Java package name: 'a.int.b': not a valid Java identifier: 'int'");
        assertThatThrownBy(() -> PackageInfoFile.of("not a package"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("not a valid Java package name: 'not a package'");

        PackageInfoFile file = PackageInfoFile.of(new PackageName.Named("com.example"));

        assertThat(file.packageName()).isEqualTo(new PackageName.Named("com.example"));
        assertThat(file.render()).isEqualTo("package com.example;\n");
    }

    @Test
    void theUnnamedPackageHasNoPackageInfo() {
        // a package declaration names its package (JLS 7.4.1): `package ;` is no Java
        assertThatThrownBy(() -> PackageInfoFile.of(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("the unnamed package has no package declaration, so no package-info.java");
    }
}
