package me.supcheg.javafile.model;

import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ModifierValidationTest {

    private static final ClassDesc DESC = ClassDesc.of("p", "C");

    @Test
    void classRejectsAbstractAndFinalTogether() {
        assertThatThrownBy(() -> new ClassDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.ABSTRACT, Modifier.FINAL),
                        List.of(),
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("abstract");
    }

    @Test
    void classRejectsProtectedAtTopLevel() {
        assertThatThrownBy(() -> new ClassDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PROTECTED),
                        List.of(),
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void interfaceRejectsFinal() {
        assertThatThrownBy(() -> new InterfaceDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.FINAL),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void recordRejectsAbstract() {
        assertThatThrownBy(() -> new RecordDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void enumRejectsAbstract() {
        assertThatThrownBy(() -> new EnumDecl(
                        DESC, List.of(), Set.of(Modifier.PUBLIC, Modifier.ABSTRACT), List.of(), List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void enumRejectsFinal() {
        assertThatThrownBy(() -> new EnumDecl(
                        DESC, List.of(), Set.of(Modifier.PUBLIC, Modifier.FINAL), List.of(), List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void interfaceAllowsStaticForNestedMember() {
        assertThatCode(() -> new InterfaceDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.STATIC),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()))
                .doesNotThrowAnyException();
    }

    @Test
    void recordAllowsStaticForNestedMember() {
        assertThatCode(() -> new RecordDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.STATIC),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of()))
                .doesNotThrowAnyException();
    }

    @Test
    void enumAllowsStaticForNestedMember() {
        assertThatCode(() -> new EnumDecl(
                        DESC, List.of(), Set.of(Modifier.PUBLIC, Modifier.STATIC), List.of(), List.of(), List.of()))
                .doesNotThrowAnyException();
    }
}
