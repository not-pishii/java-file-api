package me.supcheg.javafile.example;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.code.ConstructorBody;
import me.supcheg.javafile.code.ConstructorInvocation;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.ConstructorDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.testing.CompiledClasses;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.util.List;

import static me.supcheg.javafile.code.Exprs.add;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.this_;
import static org.assertj.core.api.Assertions.assertThat;

class ConstructorInvocationCompileTest {

    private static final ClassDesc ILLEGAL_ARGUMENT = ClassDesc.of("java.lang", "IllegalArgumentException");

    @Test
    void aConstructorInvokesAnotherOfItsClassOrOfItsSuperclassOnceAndWhereJavaHasIt() throws Exception {
        // JLS 8.8.7: the invocation is a part of the body of a constructor, after the statements that do not
        // use the object and before the rest
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Problem"),
                cb -> cb.withSuperclass(ILLEGAL_ARGUMENT)
                        .withField(
                                "serialVersionUID",
                                Types.LONG,
                                fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                                        .withInitializer(literal(1L)))
                        .withField("code", Types.INT, fb -> fb.withModifiers(Modifier.PRIVATE, Modifier.FINAL))
                        .withConstructor(ctor -> ctor.withParam("message", Types.STRING)
                                .withParam("code", Types.INT)
                                .withSuperCall(
                                        prologue -> prologue.localVar(
                                                "text", Types.STRING, add(field("message"), literal("!"))),
                                        field("text"))
                                .withBody(b -> b.assign(this_().field("code"), field("code"))))
                        .withConstructor(ctor ->
                                ctor.withParam("message", Types.STRING).withThisCall(field("message"), literal(7)))
                        .withConstructor(ctor -> ctor.withSuperCall(literal("none"))
                                .withBody(b -> b.assign(this_().field("code"), literal(-1))))
                        .withMethod("code", Types.INT, mb -> mb.withBody(b -> b.return_(this_().field("code")))));

        String source = file.render();
        Class<?> problem = CompiledClasses.of(file).generated();

        assertThat(source).contains("""
                            public Problem(String message, int code) {
                                String text = message + "!";
                                super(text);
                                this.code = code;
                            }
                        """).contains("""
                            public Problem(String message) {
                                this(message, 7);
                            }
                        """).contains("""
                            public Problem() {
                                super("none");
                                this.code = -1;
                            }
                        """);
        Object ofTwo = problem.getConstructor(String.class, int.class).newInstance("bad", 3);
        Object ofOne = problem.getConstructor(String.class).newInstance("worse");
        Object ofNone = problem.getConstructor().newInstance();
        assertThat(ofTwo).isInstanceOf(IllegalArgumentException.class).hasToString("me.supcheg.example.Problem: bad!");
        assertThat(problem.getMethod("code").invoke(ofTwo)).isEqualTo(3);
        assertThat(ofOne).hasToString("me.supcheg.example.Problem: worse!");
        assertThat(problem.getMethod("code").invoke(ofOne)).isEqualTo(7);
        assertThat(ofNone).hasToString("me.supcheg.example.Problem: none");
        assertThat(problem.getMethod("code").invoke(ofNone)).isEqualTo(-1);
    }

    @Test
    void theLastInvocationSaidIsTheOneOfTheConstructor() {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Twice"),
                cb -> cb.withConstructor(ctor -> ctor.withThisCall(literal(1)).withSuperCall()));

        ClassDecl decl = (ClassDecl) file.renderMeta().typeDecl();

        assertThat(decl.members())
                .singleElement()
                .isInstanceOfSatisfying(
                        ConstructorDecl.class,
                        ctor -> assertThat(ctor.body())
                                .isEqualTo(new ConstructorBody.Explicit(
                                        CodeBody.EMPTY, new ConstructorInvocation.Super(List.of()), CodeBody.EMPTY)));
    }
}
