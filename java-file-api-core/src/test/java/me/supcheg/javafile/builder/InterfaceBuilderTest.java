package me.supcheg.javafile.builder;

import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.ConstantDecl;
import me.supcheg.javafile.model.DefaultMethodDecl;
import me.supcheg.javafile.model.InterfaceDecl;
import me.supcheg.javafile.model.StaticMethodDecl;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

class InterfaceBuilderTest {

    @Test
    void buildsASealedInterfaceWithMixedMemberKinds() {
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("ast", "Node"));
        ClassDesc leaf = ClassDesc.of("ast", "Leaf");
        ClassDesc branch = ClassDesc.of("ast", "Branch");

        builder.permits(leaf, branch)
                .withAbstractMethod("kind", me.supcheg.javafile.type.Types.of(ClassDesc.of("java.lang", "String")))
                .withDefaultMethod(
                        "describe",
                        me.supcheg.javafile.type.Types.of(ClassDesc.of("java.lang", "String")),
                        mb -> mb.withBody(b -> b.return_(b.literal("node"))))
                .withStaticMethod(
                        "empty",
                        me.supcheg.javafile.type.Types.of(leaf),
                        mb -> mb.withBody(b -> b.return_(b.literalNull())))
                .withConstant("MAX", PrimitiveTypeRef.INT, new me.supcheg.javafile.code.IntLiteral(10));

        InterfaceDecl decl = builder.build();

        assertThat(decl.permits()).containsExactly(leaf, branch);
        assertThat(decl.members()).hasSize(4);
        assertThat(decl.members().get(0)).isInstanceOf(AbstractMethodDecl.class);
        assertThat(decl.members().get(1)).isInstanceOf(DefaultMethodDecl.class);
        assertThat(decl.members().get(2)).isInstanceOf(StaticMethodDecl.class);
        assertThat(decl.members().get(3)).isInstanceOf(ConstantDecl.class);
    }

    @Test
    void interfaceIsAlwaysPublic() {
        InterfaceDecl decl = new InterfaceBuilder(ClassDesc.of("ast", "Empty")).build();

        assertThat(decl.modifiers()).containsExactly(me.supcheg.javafile.model.Modifier.PUBLIC);
    }

    @Test
    void typeParamIsCarriedOver() {
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("ast", "Container"));
        me.supcheg.javafile.type.ClassOrInterfaceTypeRef bound =
                me.supcheg.javafile.type.Types.of(ClassDesc.of("java.lang", "Comparable"));

        builder.withTypeParam("T", bound);

        InterfaceDecl decl = builder.build();
        assertThat(decl.typeParams()).hasSize(1);
        assertThat(decl.typeParams().get(0).name()).isEqualTo("T");
        assertThat(decl.typeParams().get(0).bounds()).containsExactly(bound);
    }

    @Test
    void extendsIsAddedViaClassDescOrTypeRef() {
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("ast", "SubNode"));
        ClassDesc parent = ClassDesc.of("ast", "Node");

        builder.withExtends(parent);

        InterfaceDecl decl = builder.build();
        assertThat(decl.extendsInterfaces()).containsExactly(me.supcheg.javafile.type.Types.of(parent));
    }

    @Test
    void abstractMethodCanDeclareAThrowsClause() {
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("ast", "Node"));
        ClassDesc ioException = ClassDesc.of("java.io", "IOException");

        builder.withAbstractMethod(
                "kind",
                me.supcheg.javafile.type.Types.of(ClassDesc.of("java.lang", "String")),
                new me.supcheg.javafile.model.Param[0],
                ioException);

        AbstractMethodDecl method =
                (AbstractMethodDecl) builder.build().members().get(0);
        assertThat(method.throwsTypes()).containsExactly(me.supcheg.javafile.type.Types.of(ioException));
    }

    @Test
    void voidAbstractMethodsAreSupportedWithAndWithoutThrows() {
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("ast", "Node"));
        ClassDesc ioException = ClassDesc.of("java.io", "IOException");

        builder.withVoidAbstractMethod("visit")
                .withVoidAbstractMethod("visitChecked", new me.supcheg.javafile.model.Param[0], ioException);

        InterfaceDecl decl = builder.build();

        AbstractMethodDecl visit = (AbstractMethodDecl) decl.members().get(0);
        assertThat(visit.returnType()).isEmpty();
        assertThat(visit.throwsTypes()).isEmpty();

        AbstractMethodDecl visitChecked = (AbstractMethodDecl) decl.members().get(1);
        assertThat(visitChecked.returnType()).isEmpty();
        assertThat(visitChecked.throwsTypes()).containsExactly(me.supcheg.javafile.type.Types.of(ioException));
    }

    @Test
    void acceptAppendsAPreBuiltMember() {
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("ast", "Node"));
        ConstantDecl member = new ConstantDecl(
                "MAX", PrimitiveTypeRef.INT, java.util.List.of(), new me.supcheg.javafile.code.IntLiteral(10));

        builder.accept(member);

        assertThat(builder.build().members()).containsExactly(member);
    }

    @Test
    void annotationsAreCarriedAllThreeWays() {
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("me.supcheg.example", "Documented"));
        ClassDesc deprecated = ClassDesc.of("java.lang", "Deprecated");
        ClassDesc since = ClassDesc.of("me.supcheg.example", "Since");
        ClassDesc preBuilt = ClassDesc.of("me.supcheg.example", "PreBuilt");

        builder.withAnnotation(deprecated)
                .withAnnotation(
                        since,
                        ab -> ab.withMember("value", me.supcheg.javafile.annotation.AnnotationValues.literal("1.0")))
                .withAnnotation(new me.supcheg.javafile.annotation.AnnotationUse(preBuilt, java.util.List.of()));

        InterfaceDecl decl = builder.build();

        assertThat(decl.annotations()).hasSize(3);
        assertThat(decl.annotations().get(0).type()).isEqualTo(deprecated);
        assertThat(decl.annotations().get(1).type()).isEqualTo(since);
        assertThat(decl.annotations().get(2).type()).isEqualTo(preBuilt);
    }
}
