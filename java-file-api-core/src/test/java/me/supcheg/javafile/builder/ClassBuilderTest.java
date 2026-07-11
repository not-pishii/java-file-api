package me.supcheg.javafile.builder;

import me.supcheg.javafile.code.MethodCallExpr;
import me.supcheg.javafile.code.ReturnStmt;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.FieldDecl;
import me.supcheg.javafile.model.MethodDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;

class ClassBuilderTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc BUNDLE = ClassDesc.of("java.util", "ResourceBundle");

    @Test
    void buildsAClassWithAFieldAndAMethod() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Messages"));
        builder.withModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .withField("bundle", Types.of(BUNDLE), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL))
                .withMethod("greeting", Types.of(STRING), mb -> mb.withParam("name", Types.of(STRING))
                        .withBody(body ->
                                body.return_(body.call(body.field("bundle"), "getString", body.literal("greeting")))));

        ClassDecl decl = builder.build();

        assertThat(decl.members()).hasSize(2);
        FieldDecl field = (FieldDecl) decl.members().get(0);
        assertThat(field.name()).isEqualTo("bundle");
        assertThat(field.modifiers()).containsExactlyInAnyOrder(Modifier.PRIVATE, Modifier.FINAL);

        MethodDecl method = (MethodDecl) decl.members().get(1);
        assertThat(method.name()).isEqualTo("greeting");
        assertThat(method.params()).hasSize(1);
        assertThat(method.body().statements()).hasSize(1);
        ReturnStmt returnStmt = (ReturnStmt) method.body().statements().get(0);
        assertThat(returnStmt.value()).isPresent();
        assertThat(returnStmt.value().get()).isInstanceOf(MethodCallExpr.class);
    }

    @Test
    void classModifiersDefaultToPublicEvenWithoutExplicitWithModifiers() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Empty"));

        ClassDecl decl = builder.build();

        assertThat(decl.modifiers()).contains(Modifier.PUBLIC);
    }

    @Test
    void voidMethodHasNoReturnType() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Greeter"));
        builder.withVoidMethod("greet", mb -> mb.withBody(b -> b.return_()));

        ClassDecl decl = builder.build();

        MethodDecl method = (MethodDecl) decl.members().get(0);
        assertThat(method.returnType()).isEmpty();
    }

    @Test
    void constructorIsAddedAsAClassMember() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Greeter"));
        builder.withField("bundle", Types.of(BUNDLE), fb -> {}).withConstructor(cb -> cb.withModifiers(Modifier.PUBLIC)
                .withParam("bundle", Types.of(BUNDLE))
                .withBody(
                        b -> b.exprStatement(b.call(b.field(b.field("this"), "bundle"), "equals", b.field("bundle")))));

        ClassDecl decl = builder.build();

        assertThat(decl.members().get(1)).isInstanceOf(me.supcheg.javafile.model.ConstructorDecl.class);
    }

    @Test
    void superclassAndInterfacesAreCarriedOver() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Impl"));
        ClassDesc iface = ClassDesc.of("me.supcheg.example", "Greeter");
        builder.withSuperclass(ClassDesc.of("java.lang", "Object")).withInterface(iface);

        ClassDecl decl = builder.build();

        assertThat(decl.superclass()).contains(Types.of(ClassDesc.of("java.lang", "Object")));
        assertThat(decl.interfaces()).containsExactly(Types.of(iface));
    }

    @Test
    void sealedClassRendersPermitsAndSealedKeyword() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Shape"));
        ClassDesc circle = ClassDesc.of("me.supcheg.example", "Circle");
        builder.permits(circle);

        ClassDecl decl = builder.build();

        assertThat(decl.permits()).containsExactly(circle);
    }

    @Test
    void classCanDeclareAnAbstractMethod() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Shape"));
        builder.withAbstractMethod("area", Types.of(ClassDesc.of("java.lang", "Double")));

        ClassDecl decl = builder.build();

        me.supcheg.javafile.model.AbstractMethodDecl method =
                (me.supcheg.javafile.model.AbstractMethodDecl) decl.members().get(0);
        assertThat(method.name()).isEqualTo("area");
        assertThat(method.modifiers()).containsExactlyInAnyOrder(Modifier.PUBLIC, Modifier.ABSTRACT);
    }
}
