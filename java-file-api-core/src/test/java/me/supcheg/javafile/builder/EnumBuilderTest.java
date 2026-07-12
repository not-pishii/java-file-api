package me.supcheg.javafile.builder;

import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.EnumConstant;
import me.supcheg.javafile.model.EnumConstructorDecl;
import me.supcheg.javafile.model.EnumDecl;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

class EnumBuilderTest {

    @Test
    void buildsAnEnumWithConstantsAndMembers() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Suit"));

        builder.withConstant("HEARTS")
                .withConstant("SPADES")
                .withField("symbol", Types.of(ClassDesc.of("java.lang", "String")), fb -> {});

        EnumDecl decl = builder.build();

        assertThat(decl.constants()).hasSize(2);
        assertThat(decl.constants().get(0).name()).isEqualTo("HEARTS");
        assertThat(decl.constants().get(0).args()).isEmpty();
        assertThat(decl.constants().get(1).name()).isEqualTo("SPADES");
        assertThat(decl.members()).hasSize(1);
        assertThat(decl.members().get(0)).isInstanceOf(FieldDecl.class);
    }

    @Test
    void constantsCarryConstructorArguments() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Planet"));

        builder.withConstructor(cb -> cb.withParam("mass", Types.of(ClassDesc.of("java.lang", "Double"))))
                .withConstant("MERCURY", literal -> {});

        EnumDecl decl = builder.build();

        assertThat(decl.constants().get(0).name()).isEqualTo("MERCURY");
    }

    @Test
    void constantCanHaveArgumentsPassedDirectly() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Planet"));
        CodeBuilderExprHolder holder = new CodeBuilderExprHolder();

        builder.withConstant("MERCURY", holder.expr());

        assertThat(builder.build().constants().get(0).args()).containsExactly(holder.expr());
    }

    @Test
    void constantCanOverrideAMethodWithABody() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Op"));

        builder.withConstant(
                "PLUS",
                ecb -> ecb.withMethod(
                        "apply",
                        Types.of(ClassDesc.of("java.lang", "Integer")),
                        mb -> mb.withBody(b -> b.return_(b.literal(1)))));

        EnumDecl decl = builder.build();

        assertThat(decl.constants().get(0).body()).hasSize(1);
    }

    @Test
    void preBuiltConstantIsAddedDirectly() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Suit"));
        EnumConstant constant =
                new EnumConstant("CLUBS", java.util.List.of(), java.util.List.of(), java.util.List.of());

        builder.withConstant(constant);

        assertThat(builder.build().constants()).containsExactly(constant);
    }

    @Test
    void interfaceIsAddedViaClassDescOrTypeRef() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Suit"));
        ClassDesc iface = ClassDesc.of("me.supcheg.example", "Describable");

        builder.withInterface(iface);

        assertThat(builder.build().interfaces()).containsExactly(Types.of(iface));
    }

    @Test
    void enumCanDeclareAMethodAndAVoidMethod() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Suit"));

        builder.withMethod(
                        "label",
                        Types.of(ClassDesc.of("java.lang", "String")),
                        mb -> mb.withBody(b -> b.return_(b.literal("suit"))))
                .withVoidMethod("reset", mb -> mb.withBody(b -> b.return_()));

        EnumDecl decl = builder.build();

        MethodDecl method = (MethodDecl) decl.members().get(0);
        assertThat(method.name()).isEqualTo("label");
        assertThat(method.returnType()).isPresent();

        MethodDecl voidMethod = (MethodDecl) decl.members().get(1);
        assertThat(voidMethod.name()).isEqualTo("reset");
        assertThat(voidMethod.returnType()).isEmpty();
    }

    @Test
    void enumCanDeclareAbstractMethodsImplementedPerConstant() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Op"));

        builder.withAbstractMethod(
                        "apply",
                        Types.of(ClassDesc.of("java.lang", "Integer")),
                        new me.supcheg.javafile.model.Param("x", Types.of(ClassDesc.of("java.lang", "Integer"))))
                .withVoidAbstractMethod("reset");

        EnumDecl decl = builder.build();

        AbstractMethodDecl apply = (AbstractMethodDecl) decl.members().get(0);
        assertThat(apply.name()).isEqualTo("apply");
        assertThat(apply.returnType()).isPresent();
        assertThat(apply.params()).hasSize(1);

        AbstractMethodDecl reset = (AbstractMethodDecl) decl.members().get(1);
        assertThat(reset.name()).isEqualTo("reset");
        assertThat(reset.returnType()).isEmpty();
    }

    @Test
    void acceptAppendsAPreBuiltMember() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Suit"));
        EnumConstructorDecl member = new EnumConstructorDecl(
                java.util.List.of(), java.util.List.of(), me.supcheg.javafile.code.CodeBody.EMPTY, java.util.List.of());

        builder.accept(member);

        assertThat(builder.build().members()).containsExactly(member);
    }

    @Test
    void enumConstructorThrowsClauseIsCarriedOver() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Planet"));
        ClassDesc ioException = ClassDesc.of("java.io", "IOException");

        builder.withConstructor(
                cb -> cb.withThrows(ioException).withThrows(Types.of(ClassDesc.of("java.lang", "Double"))));

        EnumConstructorDecl ctor =
                (EnumConstructorDecl) builder.build().members().get(0);
        assertThat(ctor.throwsTypes())
                .containsExactly(Types.of(ioException), Types.of(ClassDesc.of("java.lang", "Double")));
    }

    @Test
    void annotationsAreCarriedAllThreeWaysOnTheEnumConstructorAndConstant() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Documented"));
        ClassDesc deprecated = ClassDesc.of("java.lang", "Deprecated");
        ClassDesc since = ClassDesc.of("me.supcheg.example", "Since");
        ClassDesc preBuilt = ClassDesc.of("me.supcheg.example", "PreBuilt");
        me.supcheg.javafile.annotation.AnnotationUse preBuiltUse =
                new me.supcheg.javafile.annotation.AnnotationUse(preBuilt, java.util.List.of());

        builder.withAnnotation(deprecated)
                .withAnnotation(
                        since,
                        ab -> ab.withMember("value", me.supcheg.javafile.annotation.AnnotationValues.literal("1.0")))
                .withAnnotation(preBuiltUse)
                .withConstant("HEARTS", ecb -> ecb.withAnnotation(deprecated)
                        .withAnnotation(since, ab -> {})
                        .withAnnotation(preBuiltUse))
                .withConstructor(cb -> cb.withAnnotation(deprecated)
                        .withAnnotation(since, ab -> {})
                        .withAnnotation(preBuiltUse)
                        .withParam(new me.supcheg.javafile.model.Param(
                                "x", Types.of(ClassDesc.of("java.lang", "String")))));

        EnumDecl decl = builder.build();

        assertThat(decl.annotations()).hasSize(3);
        assertThat(decl.constants().get(0).annotations()).hasSize(3);
        EnumConstructorDecl ctor = (EnumConstructorDecl) decl.members().get(0);
        assertThat(ctor.annotations()).hasSize(3);
        assertThat(ctor.params()).hasSize(1);
    }

    private static final class CodeBuilderExprHolder {
        private final me.supcheg.javafile.code.Expr expr = new me.supcheg.javafile.code.IntLiteral(1);

        me.supcheg.javafile.code.Expr expr() {
            return expr;
        }
    }
}
