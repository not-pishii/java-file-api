package me.supcheg.javafile.transform;

import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.builder.EnumBuilder;
import me.supcheg.javafile.builder.InterfaceBuilder;
import me.supcheg.javafile.builder.RecordBuilder;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.ExprStmt;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.code.ReturnStmt;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.ConstantDecl;
import me.supcheg.javafile.model.EnumDecl;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.InterfaceDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.RecordDecl;
import me.supcheg.javafile.model.StaticFieldDecl;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
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
    void enumTransformAndThenInvokesBothTransformsInOrder() {
        EnumDecl original = new EnumBuilder(ClassDesc.of("p", "Suit"))
                .withField("symbol", PrimitiveTypeRef.INT, fb -> {})
                .build();

        java.util.List<String> callOrder = new java.util.ArrayList<>();
        EnumTransform first = (builder, member) -> {
            callOrder.add("first");
            builder.accept(member);
        };
        EnumTransform second = (builder, member) -> callOrder.add("second");

        EnumDecl result = Transforms.transform(original, first.andThen(second));

        assertThat(callOrder).containsExactly("first", "second");
        assertThat(result.members()).hasSize(1);
    }

    @Test
    void classTransformCarriesTypeParamsSuperclassInterfacesAndPermitsThrough() {
        ClassOrInterfaceTypeRef comparable = Types.of(ClassDesc.of("java.lang", "Comparable"));
        ClassDecl original = new ClassBuilder(ClassDesc.of("p", "C"))
                .withTypeParam("T", comparable)
                .withSuperclass(ClassDesc.of("p", "Base"))
                .withInterface(ClassDesc.of("p", "Iface"))
                .permits(ClassDesc.of("p", "Sub"))
                .withField("count", PrimitiveTypeRef.INT, fb -> {})
                .build();

        ClassDecl result = Transforms.transform(original, (builder, member) -> builder.accept(member));

        assertThat(result.typeParams()).isEqualTo(original.typeParams());
        assertThat(result.superclass()).isEqualTo(original.superclass());
        assertThat(result.interfaces()).isEqualTo(original.interfaces());
        assertThat(result.permits()).isEqualTo(original.permits());
        assertThat(result.members()).isEqualTo(original.members());
    }

    @Test
    void interfaceTransformCarriesTypeParamsExtendsPermitsAndMembersThrough() {
        ClassOrInterfaceTypeRef comparable = Types.of(ClassDesc.of("java.lang", "Comparable"));
        InterfaceDecl original = new InterfaceBuilder(ClassDesc.of("p", "I"))
                .withTypeParam("T", comparable)
                .withExtends(ClassDesc.of("p", "Super"))
                .permits(ClassDesc.of("p", "Impl"))
                .withConstant("MAX", PrimitiveTypeRef.INT, new IntLiteral(1))
                .build();

        InterfaceDecl result = Transforms.transform(original, (builder, member) -> builder.accept(member));

        assertThat(result.typeParams()).isEqualTo(original.typeParams());
        assertThat(result.extendsInterfaces()).isEqualTo(original.extendsInterfaces());
        assertThat(result.permits()).isEqualTo(original.permits());
        assertThat(result.members()).isEqualTo(original.members());
        assertThat(result.members()).containsExactly(new ConstantDecl("MAX", PrimitiveTypeRef.INT, new IntLiteral(1)));
    }

    @Test
    void interfaceTransformAndThenInvokesBothTransformsInOrder() {
        InterfaceDecl original = new InterfaceBuilder(ClassDesc.of("p", "I"))
                .withConstant("MAX", PrimitiveTypeRef.INT, new IntLiteral(1))
                .build();

        java.util.List<String> callOrder = new java.util.ArrayList<>();
        InterfaceTransform first = (builder, member) -> {
            callOrder.add("first");
            builder.accept(member);
        };
        InterfaceTransform second = (builder, member) -> callOrder.add("second");

        InterfaceDecl result = Transforms.transform(original, first.andThen(second));

        assertThat(callOrder).containsExactly("first", "second");
        assertThat(result.members()).hasSize(1);
    }

    @Test
    void recordTransformCarriesTypeParamsComponentsInterfacesAndMembersThrough() {
        ClassOrInterfaceTypeRef comparable = Types.of(ClassDesc.of("java.lang", "Comparable"));
        RecordDecl original = new RecordBuilder(ClassDesc.of("p", "R"))
                .withTypeParam("T", comparable)
                .withComponent("value", PrimitiveTypeRef.INT)
                .withInterface(ClassDesc.of("p", "Iface"))
                .withStaticField("MAX", PrimitiveTypeRef.INT, new IntLiteral(1))
                .build();

        RecordDecl result = Transforms.transform(original, (builder, member) -> builder.accept(member));

        assertThat(result.typeParams()).isEqualTo(original.typeParams());
        assertThat(result.components()).isEqualTo(original.components());
        assertThat(result.interfaces()).isEqualTo(original.interfaces());
        assertThat(result.members()).isEqualTo(original.members());
        assertThat(result.members())
                .containsExactly(new StaticFieldDecl("MAX", PrimitiveTypeRef.INT, new IntLiteral(1)));
    }

    @Test
    void recordTransformAndThenInvokesBothTransformsInOrder() {
        RecordDecl original = new RecordBuilder(ClassDesc.of("p", "R"))
                .withStaticField("MAX", PrimitiveTypeRef.INT, new IntLiteral(1))
                .build();

        java.util.List<String> callOrder = new java.util.ArrayList<>();
        RecordTransform first = (builder, member) -> {
            callOrder.add("first");
            builder.accept(member);
        };
        RecordTransform second = (builder, member) -> callOrder.add("second");

        RecordDecl result = Transforms.transform(original, first.andThen(second));

        assertThat(callOrder).containsExactly("first", "second");
        assertThat(result.members()).hasSize(1);
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
