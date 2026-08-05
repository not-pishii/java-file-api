package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.code.ExprStmt;
import me.supcheg.javafile.code.LocalVarDeclStmt;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.PrimitiveTypeRef;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.Optional;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.code.Exprs.add;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.gt;
import static me.supcheg.javafile.code.Exprs.le;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.lt;
import static me.supcheg.javafile.code.Exprs.new_;
import static me.supcheg.javafile.code.Exprs.postIncrement;
import static me.supcheg.javafile.code.Exprs.switchExpr;
import static me.supcheg.javafile.code.Exprs.this_;

class Stage2And3FeaturesCompileTest {

    private static final ClassDesc OBJECT = ClassDesc.of("java.lang", "Object");
    private static final ClassDesc INTEGER = ClassDesc.of("java.lang", "Integer");
    private static final ClassDesc ILLEGAL_ARGUMENT = ClassDesc.of("java.lang", "IllegalArgumentException");
    private static final ClassDesc DOUBLE = ClassDesc.of("java.lang", "Double");
    private static final ClassDesc IO_EXCEPTION = ClassDesc.of("java.io", "IOException");

    @Test
    void sealedAbstractClassAndItsPermittedSubclassCompileTogether() {
        JavaFile shape = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Shape"),
                cb -> cb.withModifiers(Modifier.ABSTRACT)
                        .withPermits(ClassDesc.of("me.supcheg.example", "Circle"))
                        .withAbstractMethod("area", PrimitiveTypeRef.DOUBLE));

        JavaFile circle = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Circle"),
                cb -> cb.withModifiers(Modifier.FINAL)
                        .withSuperclass(ClassDesc.of("me.supcheg.example", "Shape"))
                        .withField(
                                "radius",
                                PrimitiveTypeRef.DOUBLE,
                                fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL))
                        .withConstructor(ctor -> ctor.withParam("radius", PrimitiveTypeRef.DOUBLE)
                                .withBody(b -> b.assign(this_().field("radius"), field("radius"))))
                        .withMethod(
                                "area", PrimitiveTypeRef.DOUBLE, mb -> mb.withBody(b -> b.return_(field("radius")))));

        Compilation compilation = javac().compile(
                        JavaFileObjects.forSourceString(shape.qualifiedName(), shape.render()),
                        JavaFileObjects.forSourceString(circle.qualifiedName(), circle.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }

    @Test
    void enumWithAConstructorAndPerConstantOverridesCompiles() {
        JavaFile op = JavaFile.enum_(
                ClassDesc.of("me.supcheg.example", "Op"),
                eb -> eb.withVoidAbstractMethod("describe")
                        .withConstant(
                                "PLUS", ecb -> ecb.withVoidMethod("describe", mb -> mb.withBody(b -> b.return_())))
                        .withConstant(
                                "MINUS", ecb -> ecb.withVoidMethod("describe", mb -> mb.withBody(b -> b.return_()))));

        Compilation compilation = javac().compile(JavaFileObjects.forSourceString(op.qualifiedName(), op.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }

    @Test
    void controlFlowHeavyMethodsCompile() {
        JavaFile calculator = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Calculator"),
                cb -> cb.withMethod(
                                "sum",
                                PrimitiveTypeRef.INT,
                                mb -> mb.withParam("n", PrimitiveTypeRef.INT).withBody(b -> {
                                    b.localVar("total", PrimitiveTypeRef.INT, literal(0));
                                    b.for_(
                                            new LocalVarDeclStmt.Typed(
                                                    PrimitiveTypeRef.INT, "i", Optional.of(literal(0))),
                                            lt(field("i"), field("n")),
                                            new ExprStmt(postIncrement(field("i"))),
                                            body -> body.assign(field("total"), add(field("total"), field("i"))));
                                    b.return_(field("total"));
                                }))
                        .withMethod(
                                "describe",
                                Types.of(ClassDesc.of("java.lang", "String")),
                                mb -> mb.withParam("obj", Types.of(OBJECT))
                                        .withBody(b -> b.return_(switchExpr(
                                                field("obj"),
                                                sb -> sb.caseTypeWithGuard(
                                                                Types.of(INTEGER),
                                                                "i",
                                                                gt(field("i"), literal(0)),
                                                                body -> body.yield_(literal("positive int")))
                                                        .caseType(
                                                                Types.of(INTEGER),
                                                                "i",
                                                                body -> body.yield_(literal("non-positive int")))
                                                        .defaultValue(literal("other"))))))
                        .withVoidMethod(
                                "requirePositive",
                                mb -> mb.withParam("n", PrimitiveTypeRef.INT)
                                        .withBody(b -> b.if_(
                                                le(field("n"), literal(0)),
                                                ib -> ib.then(body -> body.throw_(new_(
                                                        Types.of(ILLEGAL_ARGUMENT),
                                                        literal("n must be positive"))))))));

        Compilation compilation =
                javac().compile(JavaFileObjects.forSourceString(calculator.qualifiedName(), calculator.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }

    @Test
    void enumWithAnExplicitConstructorCompiles() {
        JavaFile planet = JavaFile.enum_(
                ClassDesc.of("me.supcheg.example", "Planet"),
                eb -> eb.withField("mass", Types.of(DOUBLE), fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL))
                        .withConstructor(cb -> cb.withParam("mass", Types.of(DOUBLE))
                                .withBody(b -> b.assign(this_().field("mass"), field("mass"))))
                        .withConstant(
                                "MERCURY", ecb -> ecb.withArgs(new me.supcheg.javafile.code.DoubleLiteral(3.3e23))));

        Compilation compilation =
                javac().compile(JavaFileObjects.forSourceString(planet.qualifiedName(), planet.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }

    @Test
    void methodWithThrowsAndControlFlowBodyCompiles() {
        JavaFile validator = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Validator"),
                cb -> cb.withMethod(
                        "withParam",
                        Types.of(OBJECT),
                        mb -> mb.withParam("n", PrimitiveTypeRef.INT)
                                .withThrows(IO_EXCEPTION)
                                .withBody(b -> {
                                    b.localVar("total", PrimitiveTypeRef.INT, literal(0));
                                    b.if_(
                                            lt(field("n"), literal(0)),
                                            ib -> ib.then(body -> body.throw_(new_(
                                                    Types.of(ILLEGAL_ARGUMENT), literal("n must be non-negative")))));
                                    b.for_(
                                            new LocalVarDeclStmt.Typed(
                                                    PrimitiveTypeRef.INT, "i", Optional.of(literal(0))),
                                            lt(field("i"), field("n")),
                                            new ExprStmt(postIncrement(field("i"))),
                                            body -> body.assign(field("total"), add(field("total"), field("i"))));
                                    b.localVar("boxedTotal", Types.of(OBJECT), field("total"));
                                    b.return_(switchExpr(
                                            field("boxedTotal"),
                                            sb -> sb.caseTypeWithGuard(
                                                            Types.of(INTEGER),
                                                            "i",
                                                            gt(field("i"), literal(0)),
                                                            body -> body.yield_(literal("positive")))
                                                    .caseType(
                                                            Types.of(INTEGER),
                                                            "i",
                                                            body -> body.yield_(literal("non-positive")))
                                                    .defaultValue(literal("other"))));
                                })));

        Compilation compilation =
                javac().compile(JavaFileObjects.forSourceString(validator.qualifiedName(), validator.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }

    @Test
    void transformClassThenCompileTheResult() {
        JavaFile original = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Config"),
                cb -> cb.withField(
                        "count",
                        PrimitiveTypeRef.INT,
                        fb -> fb.withInitializer(new me.supcheg.javafile.code.IntLiteral(0))));

        JavaFile transformed = original.transformClass((builder, member) -> {
            if (member instanceof me.supcheg.javafile.model.FieldDecl f) {
                java.util.Set<Modifier> mods = new java.util.LinkedHashSet<>(f.modifiers());
                mods.add(Modifier.FINAL);
                builder.accept(new me.supcheg.javafile.model.FieldDecl(
                        f.name(), f.type(), f.annotations(), mods, f.initializer()));
            } else {
                builder.accept(member);
            }
        });

        Compilation compilation =
                javac().compile(JavaFileObjects.forSourceString(transformed.qualifiedName(), transformed.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
