package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.ConstructorBody;
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
                        List.of(),
                        Optional.empty()))
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
                        List.of(),
                        Optional.empty()))
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
                        List.of(),
                        Optional.empty()))
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
                        List.of(),
                        Optional.empty()))
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
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("interface cannot be 'FINAL'");
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
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("record cannot be 'ABSTRACT'");
    }

    @Test
    void enumRejectsAbstract() {
        assertThatThrownBy(() -> new EnumDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                        List.of(),
                        List.of(),
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("enum cannot be 'ABSTRACT'");
    }

    @Test
    void enumRejectsFinal() {
        assertThatThrownBy(() -> new EnumDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.FINAL),
                        List.of(),
                        List.of(),
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("enum cannot be 'FINAL'");
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
                        List.of(),
                        Optional.empty()))
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
                        List.of(),
                        Optional.empty()))
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
                        List.of(),
                        Optional.empty()))
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
                        List.of(),
                        Optional.empty()))
                .doesNotThrowAnyException();
    }

    @Test
    void enumAllowsStaticForNestedMember() {
        assertThatCode(() -> new EnumDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.STATIC),
                        List.of(),
                        List.of(),
                        List.of(),
                        Optional.empty()))
                .doesNotThrowAnyException();
    }

    @Test
    void enumAllowsPrivateForNestedMember() {
        assertThatCode(() -> new EnumDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PRIVATE, Modifier.STATIC),
                        List.of(),
                        List.of(),
                        List.of(),
                        Optional.empty()))
                .doesNotThrowAnyException();
    }

    @Test
    void fieldRejectsAbstract() {
        assertThatThrownBy(() -> new FieldDecl(
                        "x",
                        PrimitiveTypeRef.INT,
                        List.of(),
                        Set.of(Modifier.PRIVATE, Modifier.ABSTRACT),
                        Optional.empty(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("field cannot be 'ABSTRACT'");
    }

    @Test
    void fieldRejectsMultipleAccessModifiers() {
        assertThatThrownBy(() -> new FieldDecl(
                        "x",
                        PrimitiveTypeRef.INT,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.PRIVATE),
                        Optional.empty(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("field cannot combine more than one access modifier");
    }

    @Test
    void fieldAllowsStaticFinal() {
        assertThatCode(() -> new FieldDecl(
                        "x",
                        PrimitiveTypeRef.INT,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL),
                        Optional.empty(),
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
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("method cannot be 'ABSTRACT'");
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
                        List.of(),
                        Optional.empty()))
                .doesNotThrowAnyException();
    }

    @Test
    void constructorRejectsStatic() {
        assertThatThrownBy(() -> new ConstructorDecl(
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.STATIC),
                        List.of(),
                        ConstructorBody.EMPTY,
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("constructor cannot be 'STATIC'");
    }

    @Test
    void constructorRejectsAbstract() {
        assertThatThrownBy(() -> new ConstructorDecl(
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                        List.of(),
                        ConstructorBody.EMPTY,
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("constructor cannot be 'ABSTRACT'");
    }

    @Test
    void constructorAllowsProtected() {
        assertThatCode(() -> new ConstructorDecl(
                        List.of(),
                        Set.of(Modifier.PROTECTED),
                        List.of(),
                        ConstructorBody.EMPTY,
                        List.of(),
                        Optional.empty()))
                .doesNotThrowAnyException();
    }

    @Test
    void compactConstructorRejectsStatic() {
        assertThatThrownBy(() -> new CompactConstructorDecl(
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.STATIC),
                        CodeBody.EMPTY,
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("compact constructor cannot be 'STATIC'");
    }

    @Test
    void compactConstructorAllowsPublic() {
        assertThatCode(() -> new CompactConstructorDecl(
                        List.of(), Set.of(Modifier.PUBLIC), CodeBody.EMPTY, List.of(), Optional.empty()))
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
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("abstract method cannot be 'PRIVATE'");
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
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("abstract method cannot be 'FINAL'");
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
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("abstract method cannot be 'STATIC'");
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
                        List.of(),
                        Optional.empty()))
                .doesNotThrowAnyException();
    }

    // No shipped record's allowed-set currently combines ABSTRACT with FINAL/STATIC/PRIVATE
    // (each excludes the others at the allowed-set level instead), so this branch of
    // requireValidMember is unreachable through any real declaration constructor; exercised
    // directly here since it's still part of the method's documented contract.
    @Test
    void classRejectsFinalAndNonSealedTogether() {
        assertThatThrownBy(() -> new ClassDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.FINAL, Modifier.NON_SEALED),
                        List.of(),
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be both final and non-sealed");
    }

    @Test
    void classRejectsFinalAndSealedTogether() {
        assertThatThrownBy(() -> new ClassDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.FINAL),
                        List.of(),
                        Optional.empty(),
                        List.of(),
                        List.of(ClassDesc.of("p", "Sub")),
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be both final and sealed");
    }

    @Test
    void classRejectsNonSealedAndSealedTogether() {
        assertThatThrownBy(() -> new ClassDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.NON_SEALED),
                        List.of(),
                        Optional.empty(),
                        List.of(),
                        List.of(ClassDesc.of("p", "Sub")),
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be both non-sealed and sealed");
    }

    @Test
    void classAllowsAbstractAndSealedTogether() {
        assertThatCode(() -> new ClassDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                        List.of(),
                        Optional.empty(),
                        List.of(),
                        List.of(ClassDesc.of("p", "Sub")),
                        List.of(),
                        Optional.empty()))
                .doesNotThrowAnyException();
    }

    @Test
    void classRejectsAPermittedSubtypeTwice() {
        ClassDesc subtype = ClassDesc.of("p", "Sub");
        assertThatThrownBy(() -> new ClassDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC),
                        List.of(),
                        Optional.empty(),
                        List.of(),
                        List.of(subtype, subtype),
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot permit Sub twice");
    }

    @Test
    void interfaceRejectsNonSealedAndSealedTogether() {
        assertThatThrownBy(() -> new InterfaceDecl(
                        DESC,
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.NON_SEALED),
                        List.of(),
                        List.of(),
                        List.of(ClassDesc.of("p", "Sub")),
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be both non-sealed and sealed");
    }

    @Test
    void requireValidMemberRejectsAbstractCombinedWithFinalStaticOrPrivate() {
        Set<Modifier> allowed = EnumSet.of(Modifier.ABSTRACT, Modifier.FINAL, Modifier.STATIC, Modifier.PRIVATE);

        assertThatThrownBy(() -> ModifierValidation.requireValidMember(
                        Set.of(Modifier.ABSTRACT, Modifier.FINAL), allowed, "member"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("abstract with final, static, private, synchronized, or native");
        assertThatThrownBy(() -> ModifierValidation.requireValidMember(
                        Set.of(Modifier.ABSTRACT, Modifier.STATIC), allowed, "member"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(
                        "member cannot combine abstract with final, static, private, synchronized, or native");
        assertThatThrownBy(() -> ModifierValidation.requireValidMember(
                        Set.of(Modifier.ABSTRACT, Modifier.PRIVATE), allowed, "member"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(
                        "member cannot combine abstract with final, static, private, synchronized, or native");
        Set<Modifier> methods = EnumSet.of(Modifier.ABSTRACT, Modifier.SYNCHRONIZED, Modifier.NATIVE);
        assertThatThrownBy(() -> ModifierValidation.requireValidMember(
                        Set.of(Modifier.ABSTRACT, Modifier.SYNCHRONIZED), methods, "member"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("synchronized");
        assertThatThrownBy(() -> ModifierValidation.requireValidMember(
                        Set.of(Modifier.ABSTRACT, Modifier.NATIVE), methods, "member"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("native");
    }

    @Test
    void fieldAllowsTransientAndVolatile() {
        assertThatCode(() -> new FieldDecl(
                        "x",
                        PrimitiveTypeRef.INT,
                        List.of(),
                        Set.of(Modifier.PRIVATE, Modifier.FINAL, Modifier.TRANSIENT),
                        Optional.empty(),
                        Optional.empty()))
                .doesNotThrowAnyException();
        assertThatCode(() -> new FieldDecl(
                        "x",
                        PrimitiveTypeRef.INT,
                        List.of(),
                        Set.of(Modifier.PRIVATE, Modifier.VOLATILE),
                        Optional.empty(),
                        Optional.empty()))
                .doesNotThrowAnyException();
    }

    @Test
    void fieldRejectsFinalVolatile() {
        assertThatThrownBy(() -> new FieldDecl(
                        "x",
                        PrimitiveTypeRef.INT,
                        List.of(),
                        Set.of(Modifier.PRIVATE, Modifier.FINAL, Modifier.VOLATILE),
                        Optional.empty(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("volatile with final or static");
    }

    @Test
    void fieldRejectsStaticVolatile() {
        assertThatThrownBy(() -> new FieldDecl(
                        "x",
                        PrimitiveTypeRef.INT,
                        List.of(),
                        Set.of(Modifier.PRIVATE, Modifier.STATIC, Modifier.VOLATILE),
                        Optional.empty(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("volatile with final or static");
    }

    @Test
    void fieldRejectsStaticTransient() {
        assertThatThrownBy(() -> new FieldDecl(
                        "x",
                        PrimitiveTypeRef.INT,
                        List.of(),
                        Set.of(Modifier.PRIVATE, Modifier.STATIC, Modifier.TRANSIENT),
                        Optional.empty(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("transient with static");
    }

    @Test
    void methodAllowsSynchronizedAndStaticSynchronized() {
        assertThatCode(() -> new MethodDecl(
                        "m",
                        Optional.empty(),
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.SYNCHRONIZED),
                        List.of(),
                        List.of(),
                        CodeBody.EMPTY,
                        List.of(),
                        Optional.empty()))
                .doesNotThrowAnyException();
        assertThatCode(() -> new MethodDecl(
                        "m",
                        Optional.empty(),
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.SYNCHRONIZED),
                        List.of(),
                        List.of(),
                        CodeBody.EMPTY,
                        List.of(),
                        Optional.empty()))
                .doesNotThrowAnyException();
    }

    @Test
    void nativeMethodAllowsNativeStaticAndRejectsAbstract() {
        assertThatCode(() -> new NativeMethodDecl(
                        "m",
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.NATIVE),
                        List.of(),
                        Optional.empty()))
                .doesNotThrowAnyException();
        assertThatThrownBy(() -> new NativeMethodDecl(
                        "m",
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of(),
                        Set.of(Modifier.PUBLIC, Modifier.NATIVE, Modifier.ABSTRACT),
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("native method cannot be 'ABSTRACT'");
    }

    @Test
    void nativeMethodRequiresTheNativeModifier() {
        assertThatThrownBy(() -> new NativeMethodDecl(
                        "m",
                        Optional.empty(),
                        List.of(),
                        List.of(),
                        List.of(),
                        Set.of(Modifier.PUBLIC),
                        List.of(),
                        Optional.empty()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("a native method is declared native");
    }
}
