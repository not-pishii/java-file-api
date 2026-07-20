package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

class GenericsCompileTest {

    private static final ClassDesc STRING = ClassDesc.of("java.lang", "String");
    private static final ClassDesc FUNCTION = ClassDesc.of("java.util.function", "Function");
    private static final ClassDesc CONTRACT = ClassDesc.of("me.supcheg.example", "Contract");
    private static final ClassDesc IMPL = ClassDesc.of("me.supcheg.example", "Impl");

    @Test
    void genericRecordImplementingParameterizedInterfaceWithGenericFactoryCompiles() {
        JavaFile contract = JavaFile.interface_(
                CONTRACT, ib -> ib.withTypeParam("T").withAbstractMethod("render", Types.typeVar("T")));

        JavaFile impl = JavaFile.record(
                IMPL,
                rb -> rb.withTypeParam("T")
                        .withComponent(
                                "renderer",
                                Types.parameterized(
                                        FUNCTION, Types.exact(Types.of(STRING)), Types.exact(Types.typeVar("T"))))
                        .withInterface(Types.parameterized(CONTRACT, Types.exact(Types.typeVar("T"))))
                        .withMethod(
                                "render",
                                Types.typeVar("T"),
                                mb -> mb.withBody(
                                        b -> b.return_(b.call(b.field("renderer"), "apply", b.literal("key")))))
                        .withMethod(
                                "of",
                                Types.parameterized(CONTRACT, Types.exact(Types.typeVar("T"))),
                                mb -> mb.withModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                        .withTypeParam("T")
                                        .withParam(
                                                "renderer",
                                                Types.parameterized(
                                                        FUNCTION,
                                                        Types.exact(Types.of(STRING)),
                                                        Types.exact(Types.typeVar("T"))))
                                        .withBody(b -> b.return_(b.new_(
                                                Types.parameterized(IMPL, Types.exact(Types.typeVar("T"))),
                                                b.field("renderer"))))));

        Compilation compilation = javac().compile(
                        JavaFileObjects.forSourceString(contract.qualifiedName(), contract.render()),
                        JavaFileObjects.forSourceString(impl.qualifiedName(), impl.render()));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
