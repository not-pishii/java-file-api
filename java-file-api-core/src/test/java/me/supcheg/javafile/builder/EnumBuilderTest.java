package me.supcheg.javafile.builder;

import me.supcheg.javafile.model.EnumDecl;
import me.supcheg.javafile.model.FieldDecl;
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

    private static final class CodeBuilderExprHolder {
        private final me.supcheg.javafile.code.Expr expr = new me.supcheg.javafile.code.IntLiteral(1);

        me.supcheg.javafile.code.Expr expr() {
            return expr;
        }
    }
}
