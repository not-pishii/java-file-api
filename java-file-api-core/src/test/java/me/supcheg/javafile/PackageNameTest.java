package me.supcheg.javafile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.constant.ClassDesc;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackageNameTest {

    @Test
    void aNamedPackageIsItsIdentifiers() {
        PackageName.Named name = new PackageName.Named("com.example.api");

        assertThat(name.name()).isEqualTo("com.example.api");
        assertThat(name.identifiers()).containsExactly("com", "example", "api");
        assertThat(name.directory()).isEqualTo(Path.of("com", "example", "api"));
        assertThat(name).hasToString("com.example.api");
    }

    @Test
    void theEmptyNameIsTheUnnamedPackage() {
        PackageName unnamed = PackageName.of("");

        assertThat(unnamed).isEqualTo(new PackageName.Unnamed());
        assertThat(unnamed.name()).isEmpty();
        assertThat(unnamed.directory()).isEqualTo(Path.of(""));
        assertThat(unnamed).hasToString("the unnamed package");
    }

    @Test
    void thePackageOfADescriptorIsItsPackage() {
        assertThat(PackageName.of(ClassDesc.of("java.util", "Map$Entry")))
                .isEqualTo(new PackageName.Named("java.util"));
        assertThat(PackageName.of(ClassDesc.of("Top"))).isEqualTo(new PackageName.Unnamed());
        assertThat(PackageName.of("p")).isEqualTo(new PackageName.Named("p"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a.int.b", "a..b", "a.b.", ".a", "a.1b", "a b", "a.b-c", "a._"})
    void aNameWithAPartThatIsNoIdentifierIsRefused(String name) {
        assertThatThrownBy(() -> new PackageName.Named(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith(
                        "not a valid Java package name: '" + name + "': not a valid Java identifier: '");
    }

    @Test
    void aNamedPackageHasAName() {
        assertThatThrownBy(() -> new PackageName.Named(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("not a valid Java package name: '': not a valid Java identifier: ''");
    }
}
