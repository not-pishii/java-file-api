package me.supcheg.javafile.render;

import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.builder.EnumBuilder;
import me.supcheg.javafile.builder.InterfaceBuilder;
import me.supcheg.javafile.builder.RecordBuilder;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TypeDeclRendererTest {

    @Test
    void rendersAClassWithFieldAndMethod() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Messages"));
        ClassDesc bundle = ClassDesc.of("java.util", "ResourceBundle");
        ClassDesc string = ClassDesc.of("java.lang", "String");
        builder.withModifiers(Modifier.FINAL)
                .withField("bundle", Types.of(bundle), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL))
                .withMethod("greeting", Types.of(string), mb -> mb.withParam("name", Types.of(string))
                        .withBody(b -> b.return_(b.call(b.field("bundle"), "getString", b.literal("greeting")))));

        ImportManager imports = new ImportManager("me.supcheg.example");
        String rendered = TypeDeclRenderer.renderTypeDecl(builder.build(), imports, 0);

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

        String rendered = TypeDeclRenderer.renderTypeDecl(builder.build(), new ImportManager("ast"), 0);

        assertThat(rendered).isEqualTo("""
                        public sealed interface Node permits Leaf {
                            String kind();
                        }
                        """);
    }

    @Test
    void rendersARecordWithComponentsAndAStaticField() {
        RecordBuilder builder = new RecordBuilder(ClassDesc.of("geom", "Point"));
        builder.withComponent("x", PrimitiveTypeRef.INT)
                .withComponent("y", PrimitiveTypeRef.INT)
                .withStaticField("ORIGIN", PrimitiveTypeRef.INT, new me.supcheg.javafile.code.IntLiteral(0));

        String rendered = TypeDeclRenderer.renderTypeDecl(builder.build(), new ImportManager("geom"), 0);

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

        String rendered = TypeDeclRenderer.renderTypeDecl(builder.build(), new ImportManager("me.supcheg.example"), 0);

        assertThat(rendered).isEqualTo("""
                        public enum Suit {
                            HEARTS, SPADES;
                        }
                        """);
    }

    @Test
    void rendersAFullyEmptyEnumWithoutStraySemicolon() {
        EnumBuilder builder = new EnumBuilder(ClassDesc.of("me.supcheg.example", "Empty"));

        String rendered = TypeDeclRenderer.renderTypeDecl(builder.build(), new ImportManager("me.supcheg.example"), 0);

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

        String rendered = TypeDeclRenderer.renderTypeDecl(builder.build(), new ImportManager("me.supcheg.example"), 0);

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

        String rendered = TypeDeclRenderer.renderTypeDecl(builder.build(), new ImportManager("me.supcheg.example"), 0);

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
    void nestedTypeDeclarationsAreNotSupportedInMvp() {
        var classDecl = new me.supcheg.javafile.model.ClassDecl(
                ClassDesc.of("p", "Outer"),
                java.util.Set.of(Modifier.PUBLIC),
                java.util.List.of(),
                java.util.Optional.empty(),
                java.util.List.of(),
                java.util.List.of(),
                java.util.List.of(new ClassBuilder(ClassDesc.of("p", "Inner")).build()));

        assertThatThrownBy(() -> TypeDeclRenderer.renderTypeDecl(classDecl, new ImportManager("p"), 0))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void rendersASealedClassWithPermitsAndAnAbstractMethod() {
        ClassBuilder builder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Shape"));
        ClassDesc circle = ClassDesc.of("me.supcheg.example", "Circle");
        builder.withModifiers(Modifier.ABSTRACT)
                .permits(circle)
                .withAbstractMethod("area", Types.of(ClassDesc.of("java.lang", "Double")));

        String rendered = TypeDeclRenderer.renderTypeDecl(builder.build(), new ImportManager("me.supcheg.example"), 0);

        assertThat(rendered).isEqualTo("""
                        public abstract sealed class Shape permits Circle {
                            public abstract Double area();
                        }
                        """);
    }

    @Test
    void rendersThrowsClauseOnAMethodAConstructorAndAnInterfaceMethod() {
        ClassDesc ioException = ClassDesc.of("java.io", "IOException");
        ClassBuilder classBuilder = new ClassBuilder(ClassDesc.of("me.supcheg.example", "Reader"));
        classBuilder
                .withConstructor(cb -> cb.withThrows(ioException).withBody(b -> {}))
                .withMethod("read", Types.of(ClassDesc.of("java.lang", "String")), mb -> mb.withThrows(ioException)
                        .withBody(b -> b.return_(b.literalNull())));

        String renderedClass =
                TypeDeclRenderer.renderTypeDecl(classBuilder.build(), new ImportManager("me.supcheg.example"), 0);

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

        String renderedInterface =
                TypeDeclRenderer.renderTypeDecl(interfaceBuilder.build(), new ImportManager("me.supcheg.example"), 0);

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

        String rendered = TypeDeclRenderer.renderTypeDecl(builder.build(), new ImportManager("me.supcheg.example"), 0);

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

        String rendered = TypeDeclRenderer.renderTypeDecl(builder.build(), new ImportManager("me.supcheg.example"), 0);

        assertThat(rendered).isEqualTo("""
                        public record Impl<T>(T value) implements Contract<T> {
                        }
                        """);
    }

    @Test
    void rendersAGenericInterface() {
        InterfaceBuilder builder = new InterfaceBuilder(ClassDesc.of("me.supcheg.example", "Contract"));
        builder.withTypeParam("T").withAbstractMethod("render", Types.typeVar("T"));

        String rendered = TypeDeclRenderer.renderTypeDecl(builder.build(), new ImportManager("me.supcheg.example"), 0);

        assertThat(rendered).isEqualTo("""
                        public interface Contract<T> {
                            T render();
                        }
                        """);
    }
}
