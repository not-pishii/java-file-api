package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/// The abstract methods of an abstract class (§6.5): declared with the class's members, without a
/// body, rendered and compiled; refused in a class that is not abstract, as javac refuses one.
class AbstractMethodsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Shape");

    @Test
    void anAbstractClassDeclaresAbstractMethodsWithoutABody() {
        JavaFile file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.abstract_();
                        var cb = h.extendsObject().members();
                        cb.declareAbstractMethod("area", PrimitiveToken.DOUBLE);
                        cb.declareVoidAbstractMethod("draw", String_.TOKEN);
                        cb.declareAbstractMethod("ping", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        return cb.bodies();
                    }
                });

        assertThat(file.render()).isEqualTo("""
                        package me.supcheg.example;

                        public abstract class Shape {
                            public abstract double area();

                            public abstract void draw(String v0);

                            public abstract int ping(int v0, int v1);
                        }
                        """);

        CompiledClasses.of(file);
    }

    @Test
    void anAbstractClassDeclaresAbstractMethodsOfEveryArity() {
        JavaFile file = TypedJavaFile.declareClass(
                UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                        h.abstract_();
                        var cb = h.extendsObject().members();
                        cb.declareVoidAbstractMethod("v0");
                        cb.declareVoidAbstractMethod("v1", PrimitiveToken.INT);
                        cb.declareVoidAbstractMethod("v2", PrimitiveToken.INT, PrimitiveToken.INT);
                        cb.declareVoidAbstractMethod("v3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        cb.declareVoidAbstractMethod(
                                "v4", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        cb.declareAbstractMethod("r0", PrimitiveToken.INT);
                        cb.declareAbstractMethod("r1", PrimitiveToken.INT, PrimitiveToken.INT);
                        cb.declareAbstractMethod("r2", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        cb.declareAbstractMethod(
                                "r3", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT);
                        cb.declareAbstractMethod(
                                "r4",
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT,
                                PrimitiveToken.INT);
                        return cb.bodies();
                    }
                });

        assertThat(file.render())
                .contains("public abstract void v0();")
                .contains("public abstract void v1(int v0);")
                .contains("public abstract void v2(int v0, int v1);")
                .contains("public abstract void v3(int v0, int v1, int v2);")
                .contains("public abstract void v4(int v0, int v1, int v2, int v3);")
                .contains("public abstract int r0();")
                .contains("public abstract int r1(int v0);")
                .contains("public abstract int r2(int v0, int v1);")
                .contains("public abstract int r3(int v0, int v1, int v2);")
                .contains("public abstract int r4(int v0, int v1, int v2, int v3);");
        CompiledClasses.of(file);
    }

    @Test
    void anAbstractMethodIsRefusedInAClassThatIsNotAbstract() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> TypedJavaFile.class_(
                        UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                            @Override
                            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                cb.declareAbstractMethod("area", PrimitiveToken.DOUBLE);
                                return cb.bodies();
                            }
                        }))
                .withMessageContaining("the class is not abstract")
                .withMessageContaining("implements every abstract method it inherits");

        JavacVerdict.asJavac(
                IllegalArgumentException.class,
                () -> TypedJavaFile.class_(UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                    @Override
                    public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                        cb.declareAbstractMethod("area", PrimitiveToken.DOUBLE);
                        return cb.bodies();
                    }
                }),
                "the class is not abstract",
                "abstract double area();",
                "Probe is not abstract and does not override abstract method area()");
    }
}
