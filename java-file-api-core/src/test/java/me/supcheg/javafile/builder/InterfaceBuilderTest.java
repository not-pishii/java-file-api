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
}
