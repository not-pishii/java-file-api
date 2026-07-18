package me.supcheg.javafile.builder;

import me.supcheg.javafile.code.MethodCallExpr;
import me.supcheg.javafile.code.ReturnStmt;
import me.supcheg.javafile.model.AbstractMethodDecl;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.ConstructorDecl;
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
                .withBody(b -> b.exprStatement(b.call(b.field(b.this_(), "bundle"), "equals", b.field("bundle")))));

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

    @Test
    void classCanDeclareAVoidAbstractMethod() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Shape"));
        builder.withVoidAbstractMethod("reset", new me.supcheg.javafile.model.Param("x", Types.of(STRING)));

        ClassDecl decl = builder.build();

        AbstractMethodDecl method = (AbstractMethodDecl) decl.members().get(0);
        assertThat(method.name()).isEqualTo("reset");
        assertThat(method.returnType()).isEmpty();
        assertThat(method.params()).hasSize(1);
    }

    @Test
    void typeParamIsCarriedOver() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Box"));
        ClassOrInterfaceTypeRefHolder boundHolder = new ClassOrInterfaceTypeRefHolder();
        builder.withTypeParam("T", boundHolder.ref());

        ClassDecl decl = builder.build();

        assertThat(decl.typeParams()).hasSize(1);
        assertThat(decl.typeParams().get(0).name()).isEqualTo("T");
        assertThat(decl.typeParams().get(0).bounds()).containsExactly(boundHolder.ref());
    }

    @Test
    void acceptAppendsAPreBuiltMember() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Sink"));
        AbstractMethodDecl member = new AbstractMethodDecl(
                "op",
                java.util.Optional.empty(),
                java.util.List.of(),
                java.util.List.of(),
                java.util.List.of(),
                java.util.Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                java.util.List.of());

        builder.accept(member);

        ClassDecl decl = builder.build();
        assertThat(decl.members()).containsExactly(member);
    }

    @Test
    void constructorThrowsClauseIsCarriedOver() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Risky"));
        ClassDesc ioException = ClassDesc.of("java.io", "IOException");
        builder.withConstructor(cb -> cb.withThrows(ioException).withThrows(Types.of(BUNDLE)));

        ClassDecl decl = builder.build();

        ConstructorDecl ctor = (ConstructorDecl) decl.members().get(0);
        assertThat(ctor.throwsTypes()).containsExactly(Types.of(ioException), Types.of(BUNDLE));
    }

    @Test
    void annotationsAreCarriedAllThreeWays() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Documented"));
        ClassDesc deprecated = ClassDesc.of("java.lang", "Deprecated");
        ClassDesc since = ClassDesc.of("me.supcheg.example", "Since");
        ClassDesc preBuilt = ClassDesc.of("me.supcheg.example", "PreBuilt");

        builder.withAnnotation(deprecated)
                .withAnnotation(
                        since,
                        ab -> ab.withMember("value", me.supcheg.javafile.annotation.AnnotationValues.literal("1.0")))
                .withAnnotation(new me.supcheg.javafile.annotation.AnnotationUse(preBuilt, java.util.List.of()));

        ClassDecl decl = builder.build();

        assertThat(decl.annotations()).hasSize(3);
        assertThat(decl.annotations().get(0).type()).isEqualTo(deprecated);
        assertThat(decl.annotations().get(1).type()).isEqualTo(since);
        assertThat(decl.annotations().get(2).type()).isEqualTo(preBuilt);
    }

    @Test
    void fieldMethodAndConstructorBuildersCarryAnnotationsAllThreeWays() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Annotated"));
        ClassDesc marker = ClassDesc.of("me.supcheg.example", "Marker");
        ClassDesc withSpec = ClassDesc.of("me.supcheg.example", "WithSpec");
        ClassDesc preBuilt = ClassDesc.of("me.supcheg.example", "PreBuilt");
        me.supcheg.javafile.annotation.AnnotationUse preBuiltUse =
                new me.supcheg.javafile.annotation.AnnotationUse(preBuilt, java.util.List.of());

        builder.withField("bundle", Types.of(BUNDLE), fb -> fb.withAnnotation(marker)
                        .withAnnotation(withSpec, ab -> {})
                        .withAnnotation(preBuiltUse))
                .withMethod("greeting", Types.of(STRING), mb -> mb.withAnnotation(marker)
                        .withAnnotation(withSpec, ab -> {})
                        .withAnnotation(preBuiltUse)
                        .withParam(new me.supcheg.javafile.model.Param("name", Types.of(STRING)))
                        .withBody(b -> b.return_(b.literal("hi"))))
                .withConstructor(cb -> cb.withAnnotation(marker)
                        .withAnnotation(withSpec, ab -> {})
                        .withAnnotation(preBuiltUse)
                        .withParam(new me.supcheg.javafile.model.Param("name", Types.of(STRING))));

        ClassDecl decl = builder.build();

        FieldDecl field = (FieldDecl) decl.members().get(0);
        assertThat(field.annotations()).hasSize(3);

        MethodDecl method = (MethodDecl) decl.members().get(1);
        assertThat(method.annotations()).hasSize(3);
        assertThat(method.params()).hasSize(1);

        ConstructorDecl ctor = (ConstructorDecl) decl.members().get(2);
        assertThat(ctor.annotations()).hasSize(3);
        assertThat(ctor.params()).hasSize(1);
    }

    private static final class ClassOrInterfaceTypeRefHolder {
        private final me.supcheg.javafile.type.ClassOrInterfaceTypeRef ref = Types.of(STRING);

        me.supcheg.javafile.type.ClassOrInterfaceTypeRef ref() {
            return ref;
        }
    }
}
