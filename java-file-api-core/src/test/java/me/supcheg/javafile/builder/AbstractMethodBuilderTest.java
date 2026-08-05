package me.supcheg.javafile.builder;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static me.supcheg.javafile.annotation.AnnotationValues.literal;
import static org.assertj.core.api.Assertions.assertThat;

class AbstractMethodBuilderTest {

    private static final ClassDesc HOLDER = ClassDesc.of("com.example", "Holder");
    private static final ClassDesc IO_EXCEPTION = ClassDesc.of("java.io", "IOException");

    @Test
    void classAbstractMethodCanDeclareThrows() {
        AbstractMethodDecl decl = (AbstractMethodDecl) new ClassBuilder(HOLDER)
                .withModifiers(me.supcheg.javafile.model.Modifier.ABSTRACT)
                .withAbstractMethod("read", PrimitiveTypeRef.INT, mb -> mb.withThrows(IO_EXCEPTION))
                .build()
                .members()
                .get(0);

        assertThat(decl.throwsTypes()).containsExactly(Types.of(IO_EXCEPTION));
    }

    @Test
    void interfaceAbstractMethodCollectsParamsAndThrows() {
        AbstractMethodDecl decl = (AbstractMethodDecl) new InterfaceBuilder(HOLDER)
                .withAbstractMethod(
                        "write",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withParam("value", PrimitiveTypeRef.INT).withThrows(IO_EXCEPTION))
                .build()
                .members()
                .get(0);

        assertThat(decl.params()).hasSize(1);
        assertThat(decl.throwsTypes()).containsExactly(Types.of(IO_EXCEPTION));
    }

    @Test
    void enumAbstractMethodCanDeclareThrows() {
        AbstractMethodDecl decl = (AbstractMethodDecl) new EnumBuilder(HOLDER)
                .withAbstractMethod("apply", PrimitiveTypeRef.INT, mb -> mb.withThrows(IO_EXCEPTION))
                .build()
                .members()
                .get(0);

        assertThat(decl.throwsTypes()).containsExactly(Types.of(IO_EXCEPTION));
    }

    @Test
    void classVoidAbstractMethodIsPopulatedViaTheBuilder() {
        AbstractMethodDecl decl = (AbstractMethodDecl) new ClassBuilder(HOLDER)
                .withModifiers(Modifier.ABSTRACT)
                .withVoidAbstractMethod("close", mb -> mb.withThrows(IO_EXCEPTION))
                .build()
                .members()
                .get(0);

        assertThat(decl.returnType()).isEmpty();
        assertThat(decl.throwsTypes()).containsExactly(Types.of(IO_EXCEPTION));
    }

    @Test
    void enumVoidAbstractMethodIsPopulatedViaTheBuilder() {
        AbstractMethodDecl decl = (AbstractMethodDecl) new EnumBuilder(HOLDER)
                .withVoidAbstractMethod("reset", mb -> mb.withThrows(IO_EXCEPTION))
                .build()
                .members()
                .get(0);

        assertThat(decl.returnType()).isEmpty();
        assertThat(decl.throwsTypes()).containsExactly(Types.of(IO_EXCEPTION));
    }

    @Test
    void annotationsAreCarriedAllThreeWays() {
        ClassDesc deprecated = ClassDesc.of("java.lang", "Deprecated");
        ClassDesc since = ClassDesc.of("com.example", "Since");
        ClassDesc preBuilt = ClassDesc.of("com.example", "PreBuilt");

        AbstractMethodDecl decl = (AbstractMethodDecl) new InterfaceBuilder(HOLDER)
                .withAbstractMethod(
                        "read",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withAnnotation(deprecated)
                                .withAnnotation(since, ab -> ab.withMember("value", literal("1.0")))
                                .withAnnotation(new AnnotationUse(preBuilt, List.of())))
                .build()
                .members()
                .get(0);

        assertThat(decl.annotations()).hasSize(3);
        assertThat(decl.annotations().get(0).type()).isEqualTo(deprecated);
        assertThat(decl.annotations().get(1).type()).isEqualTo(since);
        assertThat(decl.annotations().get(2).type()).isEqualTo(preBuilt);
    }

    @Test
    void modifiersDefaultToPublicAbstract() {
        AbstractMethodDecl defaults = (AbstractMethodDecl) new InterfaceBuilder(HOLDER)
                .withAbstractMethod("a", PrimitiveTypeRef.INT, mb -> {})
                .build()
                .members()
                .get(0);
        assertThat(defaults.modifiers()).containsExactlyInAnyOrder(Modifier.PUBLIC, Modifier.ABSTRACT);
    }

    @Test
    void withModifiersAddsToTheDefaultSeedRatherThanReplacingIt() {
        AbstractMethodDecl decl = (AbstractMethodDecl) new InterfaceBuilder(HOLDER)
                .withAbstractMethod("b", PrimitiveTypeRef.INT, mb -> mb.withModifiers(Modifier.PUBLIC))
                .build()
                .members()
                .get(0);

        // Before the fix, calling withModifiers at all replaced the default seed,
        // so ABSTRACT would be silently dropped here even though only PUBLIC was
        // explicitly requested (a no-op modifier that was already present).
        assertThat(decl.modifiers()).containsExactlyInAnyOrder(Modifier.PUBLIC, Modifier.ABSTRACT);
    }

    @Test
    void withModifiersAddingAConflictingAccessModifierFailsAtBuild() {
        // withModifiers can only add to the public/abstract seed - it cannot remove
        // public, so combining it with a different access modifier is rejected by
        // the model, mirroring ClassBuilder's withModifiers/withExactModifiers split.
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> new InterfaceBuilder(HOLDER)
                        .withAbstractMethod("c", PrimitiveTypeRef.INT, mb -> mb.withModifiers(Modifier.PROTECTED)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void withExactModifiersReplacesTheDefaultSeedButAbstractAlwaysSurvives() {
        AbstractMethodDecl decl = (AbstractMethodDecl) new InterfaceBuilder(HOLDER)
                .withAbstractMethod(
                        "d", PrimitiveTypeRef.INT, mb -> mb.withExactModifiers(java.util.Set.of(Modifier.PROTECTED)))
                .build()
                .members()
                .get(0);

        assertThat(decl.modifiers()).containsExactlyInAnyOrder(Modifier.PROTECTED, Modifier.ABSTRACT);
    }

    @Test
    void withExactModifiersWithAnEmptySetStillYieldsAbstract() {
        AbstractMethodDecl decl = (AbstractMethodDecl) new InterfaceBuilder(HOLDER)
                .withAbstractMethod("e", PrimitiveTypeRef.INT, mb -> mb.withExactModifiers(java.util.Set.of()))
                .build()
                .members()
                .get(0);

        assertThat(decl.modifiers()).containsExactly(Modifier.ABSTRACT);
    }

    @Test
    void aLaterWithModifiersCallStillAddsToTheSetInstalledByWithExactModifiers() {
        AbstractMethodDecl decl = (AbstractMethodDecl) new InterfaceBuilder(HOLDER)
                .withAbstractMethod(
                        "f",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withExactModifiers(java.util.Set.of(Modifier.PROTECTED))
                                .withModifiers(Modifier.ABSTRACT))
                .build()
                .members()
                .get(0);

        assertThat(decl.modifiers()).containsExactlyInAnyOrder(Modifier.PROTECTED, Modifier.ABSTRACT);
    }

    @Test
    void renderedAbstractMethodInAClassBodyKeepsAbstractAfterWithExactModifiers() {
        me.supcheg.javafile.JavaFile file = me.supcheg.javafile.JavaFile.class_(
                HOLDER,
                cb -> cb.withModifiers(Modifier.ABSTRACT)
                        .withAbstractMethod(
                                "read",
                                PrimitiveTypeRef.INT,
                                mb -> mb.withExactModifiers(java.util.Set.of(Modifier.PROTECTED))));

        assertThat(file.render()).contains("protected abstract int read();");
    }

    @Test
    void typeParamsCanBeAddedByNameOrPreBuilt() {
        TypeParam preBuilt = new TypeParam("U", List.of());

        AbstractMethodDecl decl = (AbstractMethodDecl) new InterfaceBuilder(HOLDER)
                .withAbstractMethod(
                        "convert",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withTypeParam("T").withTypeParam(preBuilt))
                .build()
                .members()
                .get(0);

        assertThat(decl.typeParams()).hasSize(2);
        assertThat(decl.typeParams().get(0).name()).isEqualTo("T");
        assertThat(decl.typeParams().get(1)).isEqualTo(preBuilt);
    }

    @Test
    void paramsCanBeAddedByNameOrPreBuiltOrVarargs() {
        Param preBuilt = new Param("y", PrimitiveTypeRef.INT);

        AbstractMethodDecl decl = (AbstractMethodDecl) new InterfaceBuilder(HOLDER)
                .withAbstractMethod(
                        "sum",
                        PrimitiveTypeRef.INT,
                        mb -> mb.withParam("x", PrimitiveTypeRef.INT)
                                .withParam(preBuilt)
                                .withVarargsParam("rest", PrimitiveTypeRef.INT))
                .build()
                .members()
                .get(0);

        assertThat(decl.params()).hasSize(3);
        assertThat(decl.params().get(0).name()).isEqualTo("x");
        assertThat(decl.params().get(1)).isEqualTo(preBuilt);
        assertThat(decl.params().get(2).varargs()).isTrue();
    }

    @Test
    void throwsAcceptsClassOrInterfaceTypeRefsDirectly() {
        ClassOrInterfaceTypeRef ioExceptionRef = Types.of(IO_EXCEPTION);

        AbstractMethodDecl decl = (AbstractMethodDecl) new InterfaceBuilder(HOLDER)
                .withAbstractMethod("read", PrimitiveTypeRef.INT, mb -> mb.withThrows(ioExceptionRef))
                .build()
                .members()
                .get(0);

        assertThat(decl.throwsTypes()).containsExactly(ioExceptionRef);
    }
}
