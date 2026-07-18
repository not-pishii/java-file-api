package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.EnumSet;
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
    void classAllowsProtectedForNestedMember() {
        assertThatCode(() -> new ClassDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PROTECTED),
                        List.of(),
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of()))
                .doesNotThrowAnyException();
    }

    @Test
    void classRejectsMultipleAccessModifiers() {
        assertThatThrownBy(() -> new ClassDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.PROTECTED),
                        List.of(),
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("more than one access modifier");
    }

    @Test
    void classAllowsPrivateForNestedMember() {
        assertThatCode(() -> new ClassDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PRIVATE, Modifier.STATIC),
                        List.of(),
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of()))
                .doesNotThrowAnyException();
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
    void interfaceAllowsPrivateForNestedMember() {
        assertThatCode(() -> new InterfaceDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PRIVATE, Modifier.STATIC),
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
    void recordAllowsPrivateForNestedMember() {
        assertThatCode(() -> new RecordDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PRIVATE, Modifier.STATIC),
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

    @Test
    void enumAllowsPrivateForNestedMember() {
        assertThatCode(() -> new EnumDecl(
                        DESC, List.of(), Set.of(Modifier.PRIVATE, Modifier.STATIC), List.of(), List.of(), List.of()))
                .doesNotThrowAnyException();
    }

    @Test
    void fieldRejectsAbstract() {
        assertThatThrownBy(() -> new FieldDecl(
                        "x",
                        PrimitiveTypeRef.INT,
                        List.of(),
                        Set.of(Modifier.PRIVATE, Modifier.ABSTRACT),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fieldRejectsMultipleAccessModifiers() {
        assertThatThrownBy(() -> new FieldDecl(
                        "x",
                        PrimitiveTypeRef.INT,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.PRIVATE),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fieldAllowsStaticFinal() {
        assertThatCode(() -> new FieldDecl(
                        "x",
                        PrimitiveTypeRef.INT,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL),
                        Optional.empty()))
                .doesNotThrowAnyException();
    }

    @Test
    void methodRejectsAbstract() {
        assertThatThrownBy(() -> new MethodDecl(
                        "m",
                        Optional.empty(),
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                        List.of(),
                        List.of(),
                        CodeBody.EMPTY,
                        List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void methodAllowsPrivateFinal() {
        assertThatCode(() -> new MethodDecl(
                        "m",
                        Optional.empty(),
                        List.of(),
                        Set.of(Modifier.PRIVATE, Modifier.FINAL),
                        List.of(),
                        List.of(),
                        CodeBody.EMPTY,
                        List.of()))
                .doesNotThrowAnyException();
    }

    @Test
    void constructorRejectsStatic() {
        assertThatThrownBy(() -> new ConstructorDecl(
                        List.of(), Set.of(Modifier.PUBLIC, Modifier.STATIC), List.of(), CodeBody.EMPTY, List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructorRejectsAbstract() {
        assertThatThrownBy(() -> new ConstructorDecl(
                        List.of(), Set.of(Modifier.PUBLIC, Modifier.ABSTRACT), List.of(), CodeBody.EMPTY, List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructorAllowsProtected() {
        assertThatCode(() -> new ConstructorDecl(
                        List.of(), Set.of(Modifier.PROTECTED), List.of(), CodeBody.EMPTY, List.of()))
                .doesNotThrowAnyException();
    }

    @Test
    void compactConstructorRejectsStatic() {
        assertThatThrownBy(() -> new CompactConstructorDecl(
                        List.of(), Set.of(Modifier.PUBLIC, Modifier.STATIC), CodeBody.EMPTY, List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void compactConstructorAllowsPublic() {
        assertThatCode(() -> new CompactConstructorDecl(List.of(), Set.of(Modifier.PUBLIC), CodeBody.EMPTY, List.of()))
                .doesNotThrowAnyException();
    }

    @Test
    void abstractMethodRejectsAbstractAndPrivateTogether() {
        assertThatThrownBy(() -> new AbstractMethodDecl(
                        "m",
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of(),
                        Set.of(Modifier.PRIVATE, Modifier.ABSTRACT),
                        List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void abstractMethodRejectsFinal() {
        assertThatThrownBy(() -> new AbstractMethodDecl(
                        "m",
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.FINAL),
                        List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void abstractMethodRejectsStatic() {
        assertThatThrownBy(() -> new AbstractMethodDecl(
                        "m",
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.STATIC),
                        List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void abstractMethodAllowsPublicAbstract() {
        assertThatCode(() -> new AbstractMethodDecl(
                        "m",
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                        List.of()))
                .doesNotThrowAnyException();
    }

    // No shipped record's allowed-set currently combines ABSTRACT with FINAL/STATIC/PRIVATE
    // (each excludes the others at the allowed-set level instead), so this branch of
    // requireValidMember is unreachable through any real declaration constructor; exercised
    // directly here since it's still part of the method's documented contract.
    @Test
    void requireValidMemberRejectsAbstractCombinedWithFinalStaticOrPrivate() {
        Set<Modifier> allowed = EnumSet.of(Modifier.ABSTRACT, Modifier.FINAL, Modifier.STATIC, Modifier.PRIVATE);

        assertThatThrownBy(() -> ModifierValidation.requireValidMember(
                        Set.of(Modifier.ABSTRACT, Modifier.FINAL), allowed, "member"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("abstract with final, static, or private");
        assertThatThrownBy(() -> ModifierValidation.requireValidMember(
                        Set.of(Modifier.ABSTRACT, Modifier.STATIC), allowed, "member"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ModifierValidation.requireValidMember(
                        Set.of(Modifier.ABSTRACT, Modifier.PRIVATE), allowed, "member"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
