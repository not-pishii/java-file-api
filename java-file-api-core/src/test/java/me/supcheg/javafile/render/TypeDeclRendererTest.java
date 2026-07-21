package me.supcheg.javafile.render;

import me.supcheg.javafile.annotation.LiteralValue;
import me.supcheg.javafile.builder.AnnotationTypeBuilder;
import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.builder.EnumBuilder;
import me.supcheg.javafile.builder.InterfaceBuilder;
import me.supcheg.javafile.builder.RecordBuilder;
import me.supcheg.javafile.code.IntLiteral;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static me.supcheg.javafile.render.SourceRenderer.standardFormat;
import static org.assertj.core.api.Assertions.assertThat;

class TypeDeclRendererTest {

    @Test
    void rendersAClassWithFieldAndMethod() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Messages"));
        ClassDesc bundle = ClassDesc.of("java.util", "ResourceBundle");
        ClassDesc string = ClassDesc.of("java.lang", "String");
        builder.withModifiers(Modifier.FINAL)
                .withField("bundle", Types.of(bundle), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL))
                .withMethod(
                        "greeting",
                        Types.of(string),
                        mb -> mb.withParam("name", Types.of(string))
                                .withBody(
                                        b -> b.return_(b.call(b.field("bundle"), "getString", b.literal("greeting")))));

        ImportManager imports = new ImportManager("me.supcheg.example");
        String rendered = TypeDeclRenderer.renderTypeDecl(builder.build(), Context.of(standardFormat(), imports));

        assertThat(rendered).isEqualTo("""
                        public final class Messages {
                            private final ResourceBundle bundle;

                            public String greeting(String name) {
                                return bundle.getString("greeting");
                            }
                        }
                        """);
        assertThat(imports.sortedImports()).containsExactly("java.util.ResourceBundle");
    }

    @Test
    void rendersASealedInterfaceWithPermitsAndMixedMembers() {
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("ast", "Node"));
        ClassDesc leaf = ClassDesc.of("ast", "Leaf");
        builder.permits(leaf).withAbstractMethod("kind", Types.of(ClassDesc.of("java.lang", "String")));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("ast")));

        assertThat(rendered).isEqualTo("""
                        public sealed interface Node permits Leaf {
                            String kind();
                        }
                        """);
    }

    @Test
    void rendersARecordWithACompactConstructorClosingBraceIndentedNotContextToString() {
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("geom", "Point"));
        builder.withComponent("x", PrimitiveTypeRef.INT)
                .withComponent("y", PrimitiveTypeRef.INT)
                .withCompactConstructor(b -> b.exprStatement(b.call("requireValid")));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("geom")));

        assertThat(rendered).isEqualTo("""
                        public record Point(int x, int y) {
                            public Point {
                                requireValid();
                            }
                        }
                        """);
    }

    @Test
    void rendersARecordWithAnExplicitCanonicalConstructor() {
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("geom", "Range"));
        builder.withComponent("low", PrimitiveTypeRef.INT)
                .withComponent("high", PrimitiveTypeRef.INT)
                .withCanonicalConstructor(
                        List.of(new Param("low", PrimitiveTypeRef.INT), new Param("high", PrimitiveTypeRef.INT)),
                        b -> b.if_(
                                        b.gt(b.field("low"), b.field("high")),
                                        ib -> ib.then(t -> t.exprStatement(t.call("fail"))))
                                .assign(b.field(b.this_(), "low"), b.field("low"))
                                .assign(b.field(b.this_(), "high"), b.field("high")));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("geom")));

        assertThat(rendered).isEqualTo("""
                        public record Range(int low, int high) {
                            public Range(int low, int high) {
                                if (low > high) {
                                    fail();
                                }
                                this.low = low;
                                this.high = high;
                            }
                        }
                        """);
    }

    @Test
    void rejectsAnExplicitCanonicalConstructorWhoseParamsDontMatchComponents() {
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("geom", "Range"));
        builder.withComponent("low", PrimitiveTypeRef.INT)
                .withComponent("high", PrimitiveTypeRef.INT)
                .withCanonicalConstructor(
                        List.of(new Param("lo", PrimitiveTypeRef.INT), new Param("hi", PrimitiveTypeRef.INT)), b -> {});

        var decl = builder.build();
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                        TypeDeclRenderer.renderTypeDecl(decl, Context.of(standardFormat(), new ImportManager("geom"))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("low");
    }

    @Test
    void rendersARecordWithComponentsAndAStaticField() {
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("geom", "Point"));
        builder.withComponent("x", PrimitiveTypeRef.INT)
                .withComponent("y", PrimitiveTypeRef.INT)
                .withStaticField("ORIGIN", PrimitiveTypeRef.INT, new me.supcheg.javafile.code.IntLiteral(0));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("geom")));

        assertThat(rendered).isEqualTo("""
                        public record Point(int x, int y) {
                            public static final int ORIGIN = 0;
                        }
                        """);
    }

    @Test
    void rendersAnEnumWithConstantsAndAField() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Suit"));
        builder.withConstant("HEARTS").withConstant("SPADES");

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public enum Suit {
                            HEARTS, SPADES;
                        }
                        """);
    }

    @Test
    void rendersAFullyEmptyEnumWithoutStraySemicolon() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Empty"));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public enum Empty {
                        }
                        """);
    }

    @Test
    void rendersEnumConstantsWithConstructorArgumentsAndAConstructor() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Planet"));
        ClassDesc doubleType = ClassDesc.of("java.lang", "Double");
        builder.withConstructor(cb -> cb.withParam("mass", Types.of(doubleType)))
                .withConstant("MERCURY", new me.supcheg.javafile.code.DoubleLiteral(3.3e23))
                .withConstant("VENUS", new me.supcheg.javafile.code.DoubleLiteral(4.8e24));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public enum Planet {
                            MERCURY(3.3E23), VENUS(4.8E24);

                            Planet(Double mass) {
                            }
                        }
                        """);
    }

    @Test
    void rendersAnEnumConstantWithAnOverriddenMethodBody() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Op"));
        builder.withVoidAbstractMethod("describe")
                .withConstant("PLUS", ecb -> ecb.withVoidMethod("describe", mb -> mb.withBody(b -> b.return_())));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public enum Op {
                            PLUS() {
                                public void describe() {
                                    return;
                                }
                            };

                            public abstract void describe();
                        }
                        """);
    }

    @Test
    void rendersAnEnumConstructorWithThrowsClause() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Currency"));
        ClassDesc parseException = ClassDesc.of("java.text", "ParseException");
        builder.withConstructor(cb -> cb.withParam("code", Types.of(ClassDesc.of("java.lang", "String")))
                        .withThrows(parseException)
                        .withBody(b -> {}))
                .withConstant("USD", new me.supcheg.javafile.code.StringLiteral("USD"));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public enum Currency {
                            USD("USD");

                            Currency(String code) throws ParseException {
                            }
                        }
                        """);
    }

    @Test
    void rendersASealedClassWithPermitsAndAnAbstractMethod() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Shape"));
        ClassDesc circle = ClassDesc.of("me.supcheg.example", "Circle");
        builder.withModifiers(Modifier.ABSTRACT)
                .permits(circle)
                .withAbstractMethod("area", Types.of(ClassDesc.of("java.lang", "Double")));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public abstract sealed class Shape permits Circle {
                            public abstract Double area();
                        }
                        """);
    }

    @Test
    void rendersAGenericAbstractMethodInAClassBody() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Container"));
        var abstractMethod = new me.supcheg.javafile.model.AbstractMethodDecl(
                "convert",
                java.util.Optional.of(Types.typeVar("T")),
                java.util.List.of(new TypeParam("T", java.util.List.of())),
                java.util.List.of(),
                java.util.List.of(),
                java.util.Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                java.util.List.of());
        builder.withModifiers(Modifier.ABSTRACT).accept(abstractMethod);

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public abstract class Container {
                            public abstract <T> T convert();
                        }
                        """);
    }

    @Test
    void rendersAGenericAbstractMethodInAnInterfaceBody() {
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("me.supcheg.example", "Converter"));
        var abstractMethod = new me.supcheg.javafile.model.AbstractMethodDecl(
                "convert",
                java.util.Optional.of(Types.typeVar("T")),
                java.util.List.of(new TypeParam("T", java.util.List.of())),
                java.util.List.of(),
                java.util.List.of(),
                java.util.Set.of(Modifier.PUBLIC, Modifier.ABSTRACT),
                java.util.List.of());
        builder.accept(abstractMethod);

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public interface Converter {
                            <T> T convert();
                        }
                        """);
    }

    @Test
    void rendersThrowsClauseOnAMethodAConstructorAndAnInterfaceMethod() {
        ClassDesc ioException = ClassDesc.of("java.io", "IOException");
        ClassBuilder classBuilder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Reader"));
        classBuilder
                .withConstructor(cb -> cb.withThrows(ioException).withBody(b -> {}))
                .withMethod(
                        "read",
                        Types.of(ClassDesc.of("java.lang", "String")),
                        mb -> mb.withThrows(ioException).withBody(b -> b.return_(b.literalNull())));

        String renderedClass = TypeDeclRenderer.renderTypeDecl(
                classBuilder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(renderedClass).isEqualTo("""
                        public class Reader {
                            public Reader() throws IOException {
                            }

                            public String read() throws IOException {
                                return null;
                            }
                        }
                        """);

        InterfaceBuilder interfaceBuilder = new InterfaceBuilder(ClassDesc.of("me.supcheg.example", "Source"));
        interfaceBuilder.withAbstractMethod(
                "read", Types.of(ClassDesc.of("java.lang", "String")), new Param[0], ioException);

        String renderedInterface = TypeDeclRenderer.renderTypeDecl(
                interfaceBuilder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(renderedInterface).isEqualTo("""
                        public interface Source {
                            String read() throws IOException;
                        }
                        """);
    }

    @Test
    void rendersAGenericClassWithBoundAndParameterizedSuperInterface() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Box"));
        ClassDesc comparable = ClassDesc.of("java.lang", "Comparable");
        builder.withTypeParam("T", Types.parameterized(comparable, Types.exact(Types.typeVar("T"))))
                .withInterface(Types.parameterized(
                        ClassDesc.of("java.util.function", "Supplier"), Types.exact(Types.typeVar("T"))));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public class Box<T extends Comparable<T>> implements Supplier<T> {
                        }
                        """);
    }

    @Test
    void rendersAGenericRecordImplementingParameterizedInterface() {
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("me.supcheg.example", "Impl"));
        builder.withTypeParam("T")
                .withComponent("value", Types.typeVar("T"))
                .withInterface(Types.parameterized(
                        ClassDesc.of("me.supcheg.example", "Contract"), Types.exact(Types.typeVar("T"))));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public record Impl<T>(T value) implements Contract<T> {
                        }
                        """);
    }

    @Test
    void rendersAGenericInterface() {
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("me.supcheg.example", "Contract"));
        builder.withTypeParam("T").withAbstractMethod("render", Types.typeVar("T"));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public interface Contract<T> {
                            T render();
                        }
                        """);
    }

    @Test
    void rendersAStaticGenericMethodWithTypeParamAfterModifiers() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Factories"));
        ClassDesc contract = ClassDesc.of("me.supcheg.example", "Contract");
        builder.withMethod(
                "of",
                Types.parameterized(contract, Types.exact(Types.typeVar("T"))),
                mb -> mb.withModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .withTypeParam("T")
                        .withParam("value", Types.typeVar("T"))
                        .withBody(b -> b.return_(b.literalNull())));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public class Factories {
                            public static <T> Contract<T> of(T value) {
                                return null;
                            }
                        }
                        """);
    }

    @Test
    void rendersAMethodThrowingATypeVariable() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Thrower"));
        builder.withVoidMethod(
                "rethrow",
                mb -> mb.withTypeParam("X", Types.of(ClassDesc.of("java.lang", "Exception")))
                        .withThrows(Types.typeVar("X"))
                        .withBody(b -> {}));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public class Thrower {
                            public <X extends Exception> void rethrow() throws X {
                            }
                        }
                        """);
    }

    @Test
    void rendersAnEnumWithARegularMethodMember() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Mode"));
        builder.withConstant("ON")
                .withMethod(
                        "label",
                        Types.of(ClassDesc.of("java.lang", "String")),
                        mb -> mb.withBody(b -> b.return_(b.literal("on"))));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public enum Mode {
                            ON;

                            public String label() {
                                return "on";
                            }
                        }
                        """);
    }

    @Test
    void rendersAnInterfaceExtendingOtherInterfaces() {
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("me.supcheg.example", "Combined"));
        builder.withExtends(ClassDesc.of("java.lang", "AutoCloseable"))
                .withExtends(ClassDesc.of("java.lang", "Runnable"));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public interface Combined extends AutoCloseable, Runnable {
                        }
                        """);
    }

    @Test
    void rendersDefaultAndStaticInterfaceMethodsAndAConstant() {
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("me.supcheg.example", "Ops"));
        ClassDesc string = ClassDesc.of("java.lang", "String");
        builder.withConstant("NAME", Types.of(string), new me.supcheg.javafile.code.StringLiteral("ops"))
                .withDefaultMethod(
                        "describe",
                        Types.of(string),
                        mb -> mb.withTypeParam("T").withBody(b -> b.return_(b.literal("d"))))
                .withStaticMethod("create", Types.of(string), mb -> mb.withBody(b -> b.return_(b.literal("c"))));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public interface Ops {
                            String NAME = "ops";

                            default <T> String describe() {
                                return "d";
                            }

                            static String create() {
                                return "c";
                            }
                        }
                        """);
    }

    @Test
    void rendersAnEnumImplementingAnInterface() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Status"));
        builder.withInterface(ClassDesc.of("java.io", "Serializable")).withConstant("OK");

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public enum Status implements Serializable {
                            OK;
                        }
                        """);
    }

    @Test
    void rendersAnEnumWithMembersOnlyAndNoConstants() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Utility"));
        builder.withVoidMethod("noop", mb -> mb.withBody(b -> {}));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public enum Utility {
                            ;

                            public void noop() {
                            }
                        }
                        """);
    }

    @Test
    void rendersAnEnumConstantBodyContainingAFieldMember() {
        var fieldMember = new me.supcheg.javafile.model.FieldDecl(
                "cached",
                PrimitiveTypeRef.INT,
                java.util.List.of(),
                java.util.Set.of(Modifier.PRIVATE),
                java.util.Optional.of(new me.supcheg.javafile.code.IntLiteral(0)));
        var constant = new me.supcheg.javafile.model.EnumConstant(
                "A", java.util.List.of(), java.util.List.of(), java.util.List.of(fieldMember));
        var enumDecl = new me.supcheg.javafile.model.EnumDecl(
                ClassDesc.of("me.supcheg.example", "WithField"),
                java.util.List.of(),
                java.util.Set.of(Modifier.PUBLIC),
                java.util.List.of(constant),
                java.util.List.of(),
                java.util.List.of());

        String rendered = TypeDeclRenderer.renderTypeDecl(
                enumDecl, Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public enum WithField {
                            A() {
                                private int cached = 0;
                            };
                        }
                        """);
    }

    @Test
    void rendersAnAnnotatedClassWithAnnotatedFieldMethodAndConstructor() {
        ClassDesc nullable = ClassDesc.of("me.supcheg.example", "Nullable");
        ClassDesc deprecated = ClassDesc.of("java.lang", "Deprecated");
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Widget"));
        builder.withAnnotation(deprecated)
                .withField("name", Types.of(ClassDesc.of("java.lang", "String")), fb -> fb.withAnnotation(nullable))
                .withMethod(
                        "label",
                        Types.of(ClassDesc.of("java.lang", "String")),
                        mb -> mb.withAnnotation(deprecated)
                                .withParam(new Param(
                                        "suffix",
                                        Types.of(ClassDesc.of("java.lang", "String")),
                                        java.util.List.of(new me.supcheg.javafile.annotation.AnnotationUse(
                                                nullable, java.util.List.of()))))
                                .withBody(b -> b.return_(b.literal("x"))))
                .withConstructor(cb -> cb.withAnnotation(deprecated));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        @Deprecated
                        public class Widget {
                            @Nullable
                            public String name;

                            @Deprecated
                            public String label(@Nullable String suffix) {
                                return "x";
                            }

                            @Deprecated
                            public Widget() {
                            }
                        }
                        """);
    }

    @Test
    void rendersAnAnnotatedInterfaceWithAnAnnotatedConstant() {
        ClassDesc nullable = ClassDesc.of("me.supcheg.example", "Nullable");
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("me.supcheg.example", "Bounds"));
        builder.withAnnotation(nullable)
                .accept(new me.supcheg.javafile.model.ConstantDecl(
                        "MAX",
                        PrimitiveTypeRef.INT,
                        java.util.List.of(
                                new me.supcheg.javafile.annotation.AnnotationUse(nullable, java.util.List.of())),
                        new me.supcheg.javafile.code.IntLiteral(10)));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        @Nullable
                        public interface Bounds {
                            @Nullable
                            int MAX = 10;
                        }
                        """);
    }

    @Test
    void rendersAnAnnotatedRecordWithAnAnnotatedComponentAndStaticField() {
        ClassDesc nullable = ClassDesc.of("me.supcheg.example", "Nullable");
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("me.supcheg.example", "Point"));
        builder.withAnnotation(nullable)
                .withComponent(new me.supcheg.javafile.model.RecordComponent(
                        "x",
                        PrimitiveTypeRef.INT,
                        java.util.List.of(
                                new me.supcheg.javafile.annotation.AnnotationUse(nullable, java.util.List.of()))));
        builder.accept(new me.supcheg.javafile.model.StaticFieldDecl(
                "ORIGIN",
                PrimitiveTypeRef.INT,
                java.util.List.of(new me.supcheg.javafile.annotation.AnnotationUse(nullable, java.util.List.of())),
                new me.supcheg.javafile.code.IntLiteral(0)));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        @Nullable
                        public record Point(@Nullable int x) {
                            @Nullable
                            public static final int ORIGIN = 0;
                        }
                        """);
    }

    @Test
    void rendersAnAnnotatedEnumWithAnAnnotatedConstant() {
        ClassDesc nullable = ClassDesc.of("me.supcheg.example", "Nullable");
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Suit"));
        builder.withAnnotation(nullable)
                .withConstant(new me.supcheg.javafile.model.EnumConstant(
                        "HEARTS",
                        java.util.List.of(
                                new me.supcheg.javafile.annotation.AnnotationUse(nullable, java.util.List.of())),
                        java.util.List.of(),
                        java.util.List.of()));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        @Nullable
                        public enum Suit {
                            @Nullable HEARTS;
                        }
                        """);
    }

    @Test
    void rendersAStaticInitializerBlockFollowedByAnInstanceInitializerBlockInAClass() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Config"));
        builder.withField("ready", PrimitiveTypeRef.BOOLEAN, fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.STATIC))
                .withStaticInitializerBlock(b -> b.assign(b.field("ready"), b.literal(true)))
                .withField("id", PrimitiveTypeRef.INT, fb -> fb.withModifiers(Modifier.PRIVATE))
                .withInitializerBlock(b -> b.assign(b.field("id"), b.literal(1)));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public class Config {
                            private static boolean ready;

                            static {
                                ready = true;
                            }

                            private int id;

                            {
                                id = 1;
                            }
                        }
                        """);
    }

    @Test
    void rendersAStaticInitializerBlockInAnEnum() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Counter"));
        builder.withConstant("INSTANCE")
                .withField("count", PrimitiveTypeRef.INT, fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.STATIC))
                .withStaticInitializerBlock(b -> b.assign(b.field("count"), b.literal(0)));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public enum Counter {
                            INSTANCE;

                            private static int count;

                            static {
                                count = 0;
                            }
                        }
                        """);
    }

    @Test
    void rendersStaticNestedClassInsideClass() {
        ClassDesc outer = ClassDesc.of("me.supcheg.example", "Outer");
        ClassDecl nested = new ClassDecl(
                ClassDesc.of("me.supcheg.example", "Nested"),
                List.of(),
                Set.of(Modifier.PUBLIC, Modifier.STATIC),
                List.of(),
                Optional.empty(),
                List.of(),
                List.of(),
                List.of());
        ClassDecl decl = new ClassDecl(
                outer,
                List.of(),
                Set.of(Modifier.PUBLIC),
                List.of(),
                Optional.empty(),
                List.of(),
                List.of(),
                List.of(nested));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                decl, Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).contains("public static class Nested {");
    }

    @Test
    void rendersStaticNestedClassInsideInterface() {
        ClassDesc outer = ClassDesc.of("me.supcheg.example", "Outer");
        ClassDecl nested = new ClassDecl(
                ClassDesc.of("me.supcheg.example", "Nested"),
                List.of(),
                Set.of(Modifier.PUBLIC, Modifier.STATIC),
                List.of(),
                Optional.empty(),
                List.of(),
                List.of(),
                List.of());
        var decl = new me.supcheg.javafile.model.InterfaceDecl(
                outer, List.of(), Set.of(Modifier.PUBLIC), List.of(), List.of(), List.of(), List.of(nested));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                decl, Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public interface Outer {
                            public static class Nested {
                            }
                        }
                        """);
    }

    @Test
    void rendersStaticNestedClassInsideRecord() {
        ClassDesc outer = ClassDesc.of("me.supcheg.example", "Outer");
        ClassDecl nested = new ClassDecl(
                ClassDesc.of("me.supcheg.example", "Nested"),
                List.of(),
                Set.of(Modifier.PUBLIC, Modifier.STATIC),
                List.of(),
                Optional.empty(),
                List.of(),
                List.of(),
                List.of());
        var decl = new me.supcheg.javafile.model.RecordDecl(
                outer, List.of(), Set.of(Modifier.PUBLIC), List.of(), List.of(), List.of(), List.of(nested));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                decl, Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public record Outer() {
                            public static class Nested {
                            }
                        }
                        """);
    }

    @Test
    void rendersStaticNestedClassInsideEnumOwnBody() {
        ClassDesc outer = ClassDesc.of("me.supcheg.example", "Outer");
        ClassDecl nested = new ClassDecl(
                ClassDesc.of("me.supcheg.example", "Nested"),
                List.of(),
                Set.of(Modifier.PUBLIC, Modifier.STATIC),
                List.of(),
                Optional.empty(),
                List.of(),
                List.of(),
                List.of());
        var constant = new me.supcheg.javafile.model.EnumConstant("A", List.of(), List.of(), List.of());
        var decl = new me.supcheg.javafile.model.EnumDecl(
                outer, List.of(), Set.of(Modifier.PUBLIC), List.of(constant), List.of(), List.of(nested));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                decl, Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public enum Outer {
                            A;

                            public static class Nested {
                            }
                        }
                        """);
    }

    @Test
    void rendersStaticNestedClassInsideEnumConstantBody() {
        ClassDesc outer = ClassDesc.of("me.supcheg.example", "Outer");
        ClassDecl nested = new ClassDecl(
                ClassDesc.of("me.supcheg.example", "Nested"),
                List.of(),
                Set.of(Modifier.PUBLIC, Modifier.STATIC),
                List.of(),
                Optional.empty(),
                List.of(),
                List.of(),
                List.of());
        var constant = new me.supcheg.javafile.model.EnumConstant("A", List.of(), List.of(), List.of(nested));
        var decl = new me.supcheg.javafile.model.EnumDecl(
                outer, List.of(), Set.of(Modifier.PUBLIC), List.of(constant), List.of(), List.of());

        String rendered = TypeDeclRenderer.renderTypeDecl(
                decl, Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public enum Outer {
                            A() {
                                public static class Nested {
                                }
                            };
                        }
                        """);
    }

    @Test
    void rendersAnAnnotationTypeWithADefaultAndANonDefaultElement() {
        AnnotationTypeBuilder builder = new AnnotationTypeBuilder(ClassDesc.of("me.supcheg.example", "MaxLength"));
        builder.withElement("value", PrimitiveTypeRef.INT, new LiteralValue(new IntLiteral(255)))
                .withElement("message", Types.of(ClassDesc.of("java.lang", "String")));

        String rendered = TypeDeclRenderer.renderTypeDecl(
                builder.build(), Context.of(standardFormat(), new ImportManager("me.supcheg.example")));

        assertThat(rendered).isEqualTo("""
                        public @interface MaxLength {
                            int value() default 255;
                            String message();
                        }
                        """);
    }
}
