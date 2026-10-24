package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SealedModelTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");

    @Test
    void staticMethodDeclAcceptsValidName() {
        assertThatCode(() -> new StaticMethodDecl(
                        "create",
                        Optional.of(Types.of(STRING)),
                        List.of(),
                        List.of(),
                        List.of(),
                        CodeBody.EMPTY,
                        List.of(),
                        Optional.empty()))
                .doesNotThrowAnyException();
    }

    @Test
    void staticMethodDeclRejectsInvalidName() {
        assertThatThrownBy(() -> new StaticMethodDecl(
                        "1create",
                        Optional.of(Types.of(STRING)),
                        List.of(),
                        List.of(),
                        List.of(),
                        CodeBody.EMPTY,
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a valid Java identifier: '1create'");
    }

    @Test
    void defaultMethodDeclAcceptsValidName() {
        assertThatCode(() -> new DefaultMethodDecl(
                        "describe",
                        Optional.of(Types.of(STRING)),
                        List.of(),
                        List.of(),
                        List.of(),
                        CodeBody.EMPTY,
                        List.of(),
                        Optional.empty()))
                .doesNotThrowAnyException();
    }

    @Test
    void defaultMethodDeclRejectsInvalidName() {
        assertThatThrownBy(() -> new DefaultMethodDecl(
                        "class",
                        Optional.of(Types.of(STRING)),
                        List.of(),
                        List.of(),
                        List.of(),
                        CodeBody.EMPTY,
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not a valid Java identifier: 'class'");
    }

    @Test
    void allTypeDeclVariantsImplementTheSealedInterface() {
        TypeDecl classDecl = new ClassDecl(
                ClassDesc.of("p", "C"),
                List.of(),
                Set.of(Modifier.PUBLIC),
                List.of(),
                Optional.empty(),
                List.of(),
                List.of(),
                List.of(),
                Optional.empty());
        TypeDecl interfaceDecl = new InterfaceDecl(
                ClassDesc.of("p", "I"),
                List.of(),
                Set.of(Modifier.PUBLIC),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                Optional.empty());
        TypeDecl recordDecl = new RecordDecl(
                ClassDesc.of("p", "R"),
                List.of(),
                Set.of(Modifier.PUBLIC),
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                Optional.empty());
        TypeDecl enumDecl = new EnumDecl(
                ClassDesc.of("p", "E"),
                List.of(),
                Set.of(Modifier.PUBLIC),
                List.<EnumConstant>of(),
                List.of(),
                List.of(),
                Optional.empty());

        assertThat(List.of(classDecl, interfaceDecl, recordDecl, enumDecl)).allMatch(JavaFileElement.class::isInstance);
    }
}
