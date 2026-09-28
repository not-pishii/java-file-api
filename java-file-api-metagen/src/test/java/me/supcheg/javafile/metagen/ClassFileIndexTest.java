package me.supcheg.javafile.metagen;

import me.supcheg.javafile.facts.FactLookupException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.lang.constant.ConstantDescs.CD_Class;
import static java.lang.constant.ConstantDescs.CD_List;
import static java.lang.constant.ConstantDescs.CD_String;
import static java.lang.constant.ConstantDescs.CD_byte;
import static java.lang.constant.ConstantDescs.CD_char;
import static java.lang.constant.ConstantDescs.CD_double;
import static java.lang.constant.ConstantDescs.CD_int;
import static java.lang.constant.ConstantDescs.CD_long;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClassFileIndexTest {
    private static final ClassDesc STRING_BUILDER = ClassDesc.of("java.lang.StringBuilder");
    private static final ClassDesc CHAR_SEQUENCE = ClassDesc.of("java.lang.CharSequence");
    private static final ClassDesc ARRAY_LIST = ClassDesc.of("java.util.ArrayList");
    private static final ClassDesc MISSING = ClassDesc.of("com.example", "Missing");

    private final ClassFileIndex index =
            new ClassFileIndex(ClassSource.of(getClass().getClassLoader()));

    @Test
    void findsDeclaredMembers() {
        IndexedType string = index.type(CD_String);

        assertThat(string.method("charAt", MethodTypeDesc.of(CD_char, CD_int)))
                .hasToString("char java.lang.String.charAt(int)");
        assertThat(string.staticMethod("valueOf", MethodTypeDesc.of(CD_String, CD_int)))
                .hasToString("static java.lang.String java.lang.String.valueOf(int)");
        assertThat(index.type(ClassDesc.of("java.lang.Math")).staticField("PI", CD_double))
                .hasToString("static double java.lang.Math.PI");
        assertThat(index.type(STRING_BUILDER).ctor(CD_String))
                .hasToString("new java.lang.StringBuilder(java.lang.String)");
        assertThat(string.desc()).isEqualTo(CD_String);
        assertThat(string).hasToString("java.lang.String");
    }

    @Test
    void findsInheritedMembers() {
        assertThat(index.type(CHAR_SEQUENCE).isInterface()).isTrue();
        assertThat(index.type(CD_String).isInterface()).isFalse();
        assertThat(index.type(CD_String).method("getClass", MethodTypeDesc.of(CD_Class)))
                .hasToString("java.lang.Class java.lang.String.getClass()");
        assertThat(index.type(STRING_BUILDER).method("capacity", MethodTypeDesc.of(CD_int)))
                .isNotNull();
        assertThat(index.type(CD_List).staticMethod("of", MethodTypeDesc.of(CD_List)))
                .isNotNull();
        assertThat(index.type(ClassDesc.of("java.awt.Point")).field("x", CD_int))
                .isNotNull();
    }

    @Test
    void readsJdkClassesFromJrt() {
        ClassFileIndex jrt = new ClassFileIndex(ClassSource.jrt());

        assertThat(jrt.type(CD_String).method("charAt", MethodTypeDesc.of(CD_char, CD_int)))
                .isNotNull();
        assertThatThrownBy(() -> jrt.type(MISSING)).isInstanceOf(FactLookupException.class);
        assertThatThrownBy(() -> jrt.type(ClassDesc.of("Unnamed"))).isInstanceOf(FactLookupException.class);
    }

    @Test
    void readsFromADirectoryAndFallsBack(@TempDir Path root) throws IOException {
        Path file = root.resolve("com/example/Missing.class");
        Files.createDirectories(file.getParent());
        Files.write(file, new byte[] {1, 2, 3});
        ClassSource source = ClassSource.of(root).or(ClassSource.jrt());

        assertThat(source.read(MISSING)).contains(new byte[] {1, 2, 3});
        assertThat(source.read(CD_String)).isPresent();
        assertThat(ClassSource.of(root).read(CD_String)).isEmpty();
    }

    @Test
    void wrapsReadFailures(@TempDir Path root) throws IOException {
        Files.createDirectories(root.resolve("com/example/Missing.class/nested"));
        ClassSource failing = type -> {
            throw new UncheckedIOException(new IOException("boom"));
        };

        assertThat(ClassSource.of(root).read(MISSING)).isEqualTo(Optional.empty());
        assertThatThrownBy(() -> failing.or(ClassSource.jrt()).read(CD_String))
                .isInstanceOf(UncheckedIOException.class);
    }

    @Test
    void missingMethodThrows() {
        assertThatThrownBy(() -> index.type(CD_String).method("frobnicate", MethodTypeDesc.of(CD_int)))
                .isInstanceOf(FactLookupException.class)
                .hasMessage("no int java.lang.String.frobnicate() in the class path");
    }

    @Test
    void wrongParamsListSimilar() {
        assertThatThrownBy(() -> index.type(CD_String).method("charAt", MethodTypeDesc.of(CD_char, CD_long)))
                .isInstanceOf(FactLookupException.class)
                .hasMessageStartingWith("no char java.lang.String.charAt(long) in the class path;"
                        + " similar: char java.lang.String.charAt(int)");
    }

    @Test
    void staticMismatchListsTheOtherOne() {
        assertThatThrownBy(() -> index.type(CD_String).method("valueOf", MethodTypeDesc.of(CD_String, CD_int)))
                .isInstanceOf(FactLookupException.class)
                .hasMessageContaining("similar: ")
                .hasMessageContaining("static java.lang.String java.lang.String.valueOf(int)");
    }

    @Test
    void staticInterfaceMethodsAreNotInherited() {
        assertThatThrownBy(() -> index.type(ARRAY_LIST).staticMethod("of", MethodTypeDesc.of(CD_List)))
                .isInstanceOf(FactLookupException.class)
                .hasMessage("no static java.util.List java.util.ArrayList.of() in the class path");
    }

    @Test
    void unknownClassThrows() {
        assertThatThrownBy(() -> index.type(MISSING))
                .isInstanceOf(FactLookupException.class)
                .hasMessage("no type com.example.Missing in the class path");
        assertThatThrownBy(() -> index.type(CD_int)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void privateFieldIsInvisible() {
        assertThatThrownBy(() -> index.type(CD_String).field("value", CD_byte.arrayType()))
                .isInstanceOf(FactLookupException.class)
                .hasMessage("no byte[] java.lang.String.value in the class path");
    }

    @Test
    void missingCtorThrows() {
        assertThatThrownBy(() -> index.type(CD_String).ctor(CD_double))
                .isInstanceOf(FactLookupException.class)
                .hasMessageStartingWith(
                        "no new java.lang.String(double) in the class path; similar: new java.lang.String(");
    }
}
