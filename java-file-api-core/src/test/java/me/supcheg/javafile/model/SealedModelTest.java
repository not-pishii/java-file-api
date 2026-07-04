package me.supcheg.javafile.model;

import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SealedModelTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");

    @Test
    void classDeclHoldsItsMembersInInsertionOrder() {
        FieldDecl field =
                new FieldDecl("bundle", Types.of(STRING), Set.of(Modifier.PRIVATE, Modifier.FINAL), Optional.empty());
        MethodDecl method = new MethodDecl(
                "greeting",
                Optional.of(Types.of(STRING)),
                Set.of(Modifier.PUBLIC),
                List.of(),
                CodeBody.EMPTY,
                List.of());

        ClassDecl decl = new ClassDecl(
                ClassDesc.of("me.supcheg.example", "Messages"),
                Set.of(Modifier.PUBLIC, Modifier.FINAL),
                Optional.empty(),
                List.of(),
                List.of(),
                List.of(field, method));

        assertThat(decl.members()).containsExactly(field, method);
    }

    @Test
    void interfaceMemberSealedHierarchyDistinguishesMethodKinds() {
        AbstractMethodDecl abstractMethod = new AbstractMethodDecl(
                "kind",
                Optional.of(Types.of(STRING)),
                List.of(),
                Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                List.of());
        DefaultMethodDecl defaultMethod =
                new DefaultMethodDecl("describe", Optional.of(Types.of(STRING)), List.of(), CodeBody.EMPTY, List.of());
        StaticMethodDecl staticMethod =
                new StaticMethodDecl("create", Optional.of(Types.of(STRING)), List.of(), CodeBody.EMPTY, List.of());
        ConstantDecl constant =
                new ConstantDecl("MAX", Types.of(STRING), new me.supcheg.javafile.code.StringLiteral("x"));

        InterfaceDecl decl = new InterfaceDecl(
                ClassDesc.of("me.supcheg.example", "Node"),
                Set.of(Modifier.PUBLIC),
                List.of(),
                List.of(),
                List.of(abstractMethod, defaultMethod, staticMethod, constant));

        assertThat(decl.members()).hasSize(4);
    }

    @Test
    void constantDeclRejectsNullInitializer() {
        assertThatThrownBy(() -> new ConstantDecl("MAX", Types.of(STRING), null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void recordDeclHoldsComponentsAndMembers() {
        RecordComponent x = new RecordComponent("x", me.supcheg.javafile.type.PrimitiveTypeRef.INT);
        RecordDecl decl = new RecordDecl(
                ClassDesc.of("geom", "Point"), Set.of(Modifier.PUBLIC), List.of(x), List.of(), List.of());

        assertThat(decl.components()).containsExactly(x);
    }

    @Test
    void enumDeclHoldsConstantsInOrder() {
        EnumDecl decl = new EnumDecl(
                ClassDesc.of("me.supcheg.example", "Suit"),
                Set.of(Modifier.PUBLIC),
                List.of(
                        new EnumConstant("HEARTS", List.of(), List.of()),
                        new EnumConstant("SPADES", List.of(), List.of())),
                List.of(),
                List.of());

        assertThat(decl.constants()).extracting(EnumConstant::name).containsExactly("HEARTS", "SPADES");
    }

    @Test
    void allTypeDeclVariantsImplementTheSealedInterface() {
        TypeDecl classDecl = new ClassDecl(
                ClassDesc.of("p", "C"), Set.of(Modifier.PUBLIC), Optional.empty(), List.of(), List.of(), List.of());
        TypeDecl interfaceDecl =
                new InterfaceDecl(ClassDesc.of("p", "I"), Set.of(Modifier.PUBLIC), List.of(), List.of(), List.of());
        TypeDecl recordDecl =
                new RecordDecl(ClassDesc.of("p", "R"), Set.of(Modifier.PUBLIC), List.of(), List.of(), List.of());
        TypeDecl enumDecl = new EnumDecl(
                ClassDesc.of("p", "E"), Set.of(Modifier.PUBLIC), List.<EnumConstant>of(), List.of(), List.of());

        assertThat(List.of(classDecl, interfaceDecl, recordDecl, enumDecl)).allMatch(JavaFileElement.class::isInstance);
    }
}
