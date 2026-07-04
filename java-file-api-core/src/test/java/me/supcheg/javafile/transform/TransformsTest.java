package me.supcheg.javafile.transform;

import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.builder.EnumBuilder;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.ExprStmt;
import me.supcheg.javafile.code.ReturnStmt;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.EnumDecl;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TransformsTest {

    @Test
    void classTransformCanRewriteAMemberBeforeItReachesTheNewBuilder() {
        ClassDecl original = new ClassBuilder(ClassDesc.of("p", "C"))
                .withField("count", PrimitiveTypeRef.INT, fb -> {})
                .build();

        ClassTransform makeFieldsFinal = (builder, member) -> {
            if (member instanceof FieldDecl f && !f.modifiers().contains(Modifier.FINAL)) {
                Set<Modifier> mods = new LinkedHashSet<>(f.modifiers());
                mods.add(Modifier.FINAL);
                builder.accept(new FieldDecl(f.name(), f.type(), mods, f.initializer()));
            } else {
                builder.accept(member);
            }
        };

        ClassDecl result = Transforms.transform(original, makeFieldsFinal);

        FieldDecl field = (FieldDecl) result.members().get(0);
        assertThat(field.modifiers()).contains(Modifier.FINAL);
    }

    @Test
    void enumTransformCarriesConstantsOverVerbatim() {
        EnumDecl original = new EnumBuilder(ClassDesc.of("p", "Suit"))
                .withConstant("HEARTS")
                .build();

        EnumDecl result = Transforms.transform(original, (builder, member) -> builder.accept(member));

        assertThat(result.constants()).isEqualTo(original.constants());
    }

    @Test
    void codeTransformCanDropAStatement() {
        CodeBody original = new CodeBody(java.util.List.of(
                new ExprStmt(new me.supcheg.javafile.code.IntLiteral(1)), new ReturnStmt(java.util.Optional.empty())));

        CodeTransform dropExprStatements = (builder, stmt) -> {
            if (!(stmt instanceof ExprStmt)) {
                builder.accept(stmt);
            }
        };

        CodeBody result = Transforms.transform(original, dropExprStatements);

        assertThat(result.statements()).containsExactly(new ReturnStmt(java.util.Optional.empty()));
    }
}
