package me.supcheg.javafile.render;

import me.supcheg.javafile.InheritedName;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.builder.ClassBuilder;
import me.supcheg.javafile.code.Exprs;
import me.supcheg.javafile.model.ClassDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.net.URI;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// A name that means something else where the renderer writes a type (JLS
/// 6.4.1, 6.4.2): a variable of the file, a field or a member type its type
/// inherits, a class declared in a body. What is rendered is compiled by
/// javac.
class ObscuredNamesTest {
    private static final ClassDesc PROBE = ClassDesc.of("gen", "Probe");
    private static final ClassDesc LIST = ClassDesc.of("java.util", "List");
    private static final ClassDesc AWT_LIST = ClassDesc.of("java.awt", "List");
    private static final ClassDesc COLLECTIONS = ClassDesc.of("java.util", "Collections");
    private static final ClassDesc OPTIONAL = ClassDesc.of("java.util", "Optional");

    private static JavaFile probe(Consumer<ClassBuilder> spec) {
        return JavaFile.class_(PROBE, spec::accept);
    }

    /// The errors javac has of a rendered file.
    private static List<String> errors(String source) {
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaFileObject file =
                new SimpleJavaFileObject(URI.create("string:///gen/Probe.java"), JavaFileObject.Kind.SOURCE) {
                    @Override
                    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                        return source;
                    }
                };
        ToolProvider.getSystemJavaCompiler()
                .getTask(null, null, diagnostics, List.of("-proc:none", "-d", tempDir()), null, List.of(file))
                .call();
        return diagnostics.getDiagnostics().stream()
                .filter(diagnostic -> diagnostic.getKind() == Diagnostic.Kind.ERROR)
                .map(diagnostic -> diagnostic.getMessage(null))
                .toList();
    }

    private static String tempDir() {
        try {
            return java.nio.file.Files.createTempDirectory("obscured").toString();
        } catch (java.io.IOException e) {
            throw new java.io.UncheckedIOException(e);
        }
    }

    @Test
    void aTypeNamedLikeAFieldIsWrittenQualifiedBeforeAMember() {
        // the field is declared after the method that names the type: the file is rendered knowing it
        String source = probe(cb -> cb.withMethod(
                                "none",
                                Types.of(OPTIONAL),
                                mb -> mb.withModifiers(Modifier.PUBLIC)
                                        .withBody(b -> b.return_(Exprs.staticCall(OPTIONAL, "empty"))))
                        .withField("Optional", Types.INT, fb -> fb.withModifiers(Modifier.PUBLIC)))
                .render();

        assertThat(source)
                .contains("return java.util.Optional.empty();")
                // where only a type can stand, the simple name is the type
                .contains("public Optional none()");
        assertThat(errors(source)).isEmpty();
    }

    @Test
    void aTypeNamedLikeAParameterOrALocalVariableIsWrittenQualifiedBeforeAMember() {
        String source = probe(cb -> cb.withMethod(
                                "of",
                                Types.of(ConstantDescs.CD_Object),
                                mb -> mb.withModifiers(Modifier.PUBLIC)
                                        .withParam("Optional", Types.INT)
                                        .withBody(b -> b.return_(Exprs.staticCall(OPTIONAL, "empty"))))
                        .withMethod(
                                "ref",
                                Types.of(ConstantDescs.CD_Object),
                                mb -> mb.withModifiers(Modifier.PUBLIC)
                                        .withBody(b -> b.localVar("Collections", Types.INT, Exprs.literal(1))
                                                .localVar(
                                                        "supplier",
                                                        Types.parameterized(
                                                                ClassDesc.of("java.util.function", "Supplier"),
                                                                Types.of(ConstantDescs.CD_Object)),
                                                        Exprs.methodRef(COLLECTIONS, "emptyList"))
                                                .return_(Exprs.staticField(COLLECTIONS, "EMPTY_LIST")))))
                .render();

        assertThat(source)
                .contains("return java.util.Optional.empty();")
                .contains("= java.util.Collections::emptyList;")
                .contains("return java.util.Collections.EMPTY_LIST;");
        assertThat(errors(source)).isEmpty();
    }

    @Test
    void aQualifiedNameWhoseFirstNameIsAVariableIsNotWrittenBeforeAMember() {
        // two types named List: the second is written qualified, and `java` is a field
        JavaFile file = probe(cb -> cb.withField("java", Types.INT, fb -> fb.withModifiers(Modifier.PUBLIC))
                .withField("items", Types.of(AWT_LIST), fb -> fb.withModifiers(Modifier.PUBLIC))
                .withMethod(
                        "of",
                        Types.of(ConstantDescs.CD_Object),
                        mb -> mb.withModifiers(Modifier.PUBLIC)
                                .withBody(b -> b.return_(Exprs.staticCall(LIST, "of")))));

        assertThatIllegalStateException()
                .isThrownBy(file::render)
                .withMessage("cannot name java.util.List before a member in a file that declares the variable java:"
                        + " where an expression may stand the name of the variable obscures the first name of the"
                        + " qualified one — rename the variable");
        // and javac would not have it: the name is the variable
        assertThat(errors("""
                package gen;
                public class Probe {
                    public int java;
                    public Object of() { return java.util.List.of(); }
                }
                """)).isNotEmpty();
    }

    @Test
    void aQualifiedNameIsWrittenWhereOnlyATypeCanStandWhateverTheVariables() {
        String source = probe(cb -> cb.withField("java", Types.INT, fb -> fb.withModifiers(Modifier.PUBLIC))
                        .withField("items", Types.of(AWT_LIST), fb -> fb.withModifiers(Modifier.PUBLIC))
                        .withField(
                                "more",
                                Types.parameterized(LIST, Types.of(ConstantDescs.CD_String)),
                                fb -> fb.withModifiers(Modifier.PUBLIC)))
                .render();

        assertThat(source).contains("public java.util.List<String> more;");
        assertThat(errors(source)).isEmpty();
    }

    @Test
    void aTypeNamedLikeAnInheritedFieldIsWrittenQualifiedBeforeAMember() {
        JavaFile file = probe(cb -> cb.withMethod(
                "none",
                Types.of(OPTIONAL),
                mb -> mb.withModifiers(Modifier.PUBLIC).withBody(b -> b.return_(Exprs.staticCall(OPTIONAL, "empty")))));

        assertThat(file.render()).contains("return Optional.empty();");
        String source =
                file.inheriting(List.of(new InheritedName.Field("Optional"))).render();
        assertThat(source).contains("return java.util.Optional.empty();").contains("public Optional none()");
        assertThat(errors(source)).isEmpty();
    }

    @Test
    void aTypeNamedLikeAnInheritedMemberTypeIsWrittenQualifiedEverywhere() {
        // Probe extends Thread inherits the member type State: another State is not meant by the name
        ClassDesc state = ClassDesc.of("java.util.concurrent", "Future$State");
        JavaFile file = probe(cb -> cb.withSuperclass(ClassDesc.of("java.lang", "Thread"))
                .withMethod(
                        "state",
                        Types.of(state),
                        mb -> mb.withModifiers(Modifier.PUBLIC)
                                .withBody(b -> b.return_(Exprs.staticField(state, "RUNNING")))));

        // without the name the file imports Future.State, and Thread.State is meant in the body
        assertThat(errors(file.render())).isNotEmpty();
        String source =
                file.inheriting(List.of(new InheritedName.MemberType("State"))).render();
        assertThat(source)
                .contains("public java.util.concurrent.Future.State state()")
                .contains("return java.util.concurrent.Future.State.RUNNING;")
                .doesNotContain("import");
        assertThat(errors(source)).isEmpty();
    }

    @Test
    void aQualifiedNameWhoseFirstNameIsAnInheritedMemberTypeIsNotWritten() {
        JavaFile file = probe(cb -> cb.withField("items", Types.of(LIST), fb -> fb.withModifiers(Modifier.PUBLIC)))
                .inheriting(List.of(new InheritedName.MemberType("List"), new InheritedName.MemberType("java")));

        assertThatIllegalStateException()
                .isThrownBy(file::render)
                .withMessageContaining("cannot name java.util.List in a file whose type inherits the member type java");
    }

    @Test
    void theTypeParametersOfAClassDeclaredInABodyAreOfTheFile() {
        ClassDesc otherT = ClassDesc.of("java.util", "Optional");
        ClassDecl local = new ClassBuilder(ClassDesc.of("gen", "Local"))
                .withExactModifiers(java.util.Set.of())
                .withTypeParam(new TypeParam("Optional", List.of()))
                .build();
        String source = probe(cb -> cb.withMethod(
                        "none",
                        Types.of(ConstantDescs.CD_Object),
                        mb -> mb.withModifiers(Modifier.PUBLIC)
                                .withBody(b -> b.localVar("before", Types.of(otherT), Exprs.staticCall(otherT, "empty"))
                                        .localType(local)
                                        .return_(Exprs.field("before")))))
                .render();

        // the local class declares the type parameter Optional: java.util.Optional is written qualified
        // throughout, though it was named before the class was met
        assertThat(source)
                .contains("java.util.Optional before = java.util.Optional.empty();")
                .doesNotContain("import");
        assertThat(errors(source)).isEmpty();
    }
}
