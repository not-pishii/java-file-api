package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.ModuleFile;
import me.supcheg.javafile.PackageInfoFile;
import me.supcheg.javafile.RenderableFile;
import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.doc.DocRef;
import me.supcheg.javafile.doc.DocStyle;
import me.supcheg.javafile.doc.DocText;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.render.SourceRenderer;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.literal;
import static me.supcheg.javafile.code.Exprs.this_;

/// A file with a comment on every kind of declaration, with every kind of
/// link and tag, passes `-Xdoclint:all` in both syntaxes: every reference
/// resolves, the markup is well-formed, nothing is missing.
class DocCommentCompileTest {
    private static final ClassDesc DOCUMENTED = ClassDesc.of("me.supcheg.example", "Documented");
    private static final ClassDesc LEVEL = DOCUMENTED.nested("Level");
    private static final ClassDesc POINT = DOCUMENTED.nested("Point");
    private static final ClassDesc MARK = DOCUMENTED.nested("Mark");
    private static final ClassDesc SHAPE = DOCUMENTED.nested("Shape");
    private static final ClassDesc CD_ENTRY = ClassDesc.of("java.util", "Map").nested("Entry");
    private static final ClassDesc CD_IO_EXCEPTION = ClassDesc.of("java.io", "IOException");
    private static final ClassDesc CD_ARRAYS = ClassDesc.of("java.util", "Arrays");

    /// What no comment may be broken by: the end of a comment, tags, HTML, Markdown, a Unicode escape.
    private static final String HOSTILE =
            "*/ @param {@link x} <b> & a_b_c *bold* `tick` [link] \\u000a \\u002a\\u002f # 1. - + > | ~\n"
                    + "@return on a line of its own\n    indented";

    private static JavaFile documented() {
        return JavaFile.class_(
                DOCUMENTED,
                cb -> cb.withDoc(d -> d.paragraph(DocText.of(t -> t.text("Links to a type ")
                                        .link(DocRef.type(CD_ENTRY))
                                        .text(", a field ")
                                        .link(DocRef.field(ConstantDescs.CD_Integer, "MAX_VALUE"))
                                        .text(", a method ")
                                        .link(DocRef.method(CD_ENTRY, "getKey"))
                                        .text(", one with an array and a class ")
                                        .link(DocRef.method(
                                                CD_ARRAYS,
                                                "fill",
                                                ConstantDescs.CD_Object.arrayType(),
                                                ConstantDescs.CD_Object))
                                        .text(", a varargs one ")
                                        .link(DocRef.method(CD_ARRAYS, "asList", ConstantDescs.CD_Object.arrayType()))
                                        .text(", one of two dimensions ")
                                        .link(DocRef.method(
                                                CD_ARRAYS, "deepToString", ConstantDescs.CD_Object.arrayType()))
                                        .text(", a constructor ")
                                        .link(DocRef.constructor(
                                                ClassDesc.of("java.lang", "StringBuilder"), ConstantDescs.CD_int))
                                        .text(" and a member of this class, ")
                                        .link(DocRef.method(
                                                DOCUMENTED, "read", ConstantDescs.CD_int.arrayType(2), CD_ENTRY))
                                        .text(".")))
                                .paragraph(DocText.of(t -> t.text("Code: ")
                                        .code("List<String> names = List.of();")
                                        .text(", ")
                                        .code("a `tick` and a \\ and {braces} and */")
                                        .text(".")))
                                .list(List.of(
                                        DocText.of("an item"),
                                        DocText.of(t -> t.code("code").text(" in an item,\nwhich goes on"))))
                                .paragraph(HOSTILE)
                                .typeParam("T", "what is kept")
                                .since("1.0 & <later>"))
                        .withTypeParam("T")
                        .withField(
                                "value",
                                Types.typeVar("T"),
                                fb -> fb.withDoc(DocComment.of("The value. " + HOSTILE))
                                        .withModifiers(Modifier.PRIVATE))
                        .withConstructor(ctor -> ctor.withDoc(
                                        d -> d.paragraph("Keeps a value.").param("value", "the value"))
                                .withParam("value", Types.typeVar("T"))
                                .withBody(b -> b.assign(this_().field("value"), field("value"))))
                        .withMethod(
                                "read",
                                Types.typeVar("T"),
                                mb -> mb.withDoc(d -> d.paragraph("Reads.")
                                                .param(
                                                        "grid",
                                                        DocText.of(t -> t.text("the grid, see ")
                                                                .link(DocRef.type(LEVEL))))
                                                .param("entry", "an entry")
                                                .typeParam("K", "the key")
                                                .returns("the value")
                                                .throws_(CD_IO_EXCEPTION, "never, in fact")
                                                .see(DocRef.method(
                                                        CD_ARRAYS,
                                                        "fill",
                                                        ConstantDescs.CD_int.arrayType(),
                                                        ConstantDescs.CD_int))
                                                .see(DocRef.type(POINT))
                                                .deprecated(DocText.of(
                                                        t -> t.text("use ").link(DocRef.field(DOCUMENTED, "value")))))
                                        .withAnnotation(ClassDesc.of("java.lang", "Deprecated"))
                                        .withTypeParam("K")
                                        .withParam("grid", Types.array(Types.array(Types.INT)))
                                        .withParam(
                                                "entry",
                                                Types.parameterized(CD_ENTRY, Types.typeVar("K"), Types.typeVar("T")))
                                        .withThrows(CD_IO_EXCEPTION)
                                        .withBody(b -> b.return_(field("value"))))
                        .withNestedEnum(
                                LEVEL,
                                eb -> eb.withDoc(DocComment.of("A level."))
                                        .withModifiers(Modifier.STATIC)
                                        .withConstant("LOW", c -> c.withDoc(DocComment.of("The low one.")))
                                        .withConstant(
                                                "HIGH",
                                                c -> c.withDoc(d -> d.paragraph(DocText.of(t -> t.text("Above ")
                                                        .link(DocRef.field(LEVEL, "LOW"))
                                                        .text("."))))))
                        .withNestedRecord(
                                POINT,
                                rb -> rb.withDoc(d -> d.paragraph("A point.")
                                                .param("x", "the abscissa")
                                                .param("y", "the ordinate"))
                                        .withModifiers(Modifier.STATIC)
                                        .withComponent("x", Types.INT)
                                        .withComponent("y", Types.INT))
                        .withNestedAnnotationType(
                                MARK,
                                ab -> ab.withDoc(DocComment.of("A mark."))
                                        .withModifiers(Modifier.STATIC)
                                        .withElement(
                                                "value",
                                                Types.STRING,
                                                AnnotationValues.literal(""),
                                                DocComment.of(d -> d.paragraph("The text of the mark.")
                                                        .returns("the text"))))
                        .withNestedInterface(
                                SHAPE,
                                ib -> ib.withDoc(DocComment.of("A shape."))
                                        .withModifiers(Modifier.STATIC)
                                        .withConstant(
                                                "SIDES",
                                                Types.INT,
                                                kb -> kb.withDoc(DocComment.of("How many sides."))
                                                        .withInitializer(literal(4)))
                                        .withAbstractMethod(
                                                "area",
                                                Types.DOUBLE,
                                                mb -> mb.withDoc(d ->
                                                        d.paragraph("The area.").returns("the area")))
                                        .withDefaultMethod(
                                                "sides",
                                                Types.INT,
                                                mb -> mb.withDoc(d -> d.paragraph("The sides.")
                                                                .returns("how many"))
                                                        .withBody(b -> b.return_(field("SIDES"))))));
    }

    @ParameterizedTest
    @EnumSource(DocStyle.class)
    void aFileWithACommentOnEveryDeclarationPassesDoclint(DocStyle style) {
        String source = documented().render(SourceRenderer.standardFormat(style));

        Compilation compilation = javac().withOptions("-Xdoclint:all", "-Xlint:all")
                .compile(JavaFileObjects.forSourceString("me.supcheg.example.Documented", source));

        assertThat(compilation).succeededWithoutWarnings();
    }

    /// The module is compiled from files: the in-memory file manager of compile-testing has no modules, see
    /// [ModuleInfoCompileTest].
    @ParameterizedTest
    @EnumSource(DocStyle.class)
    void aPackageAndAModuleWithACommentPassDoclint(DocStyle style, @TempDir Path sourceDir, @TempDir Path outputDir)
            throws IOException {
        SourceRenderer.Format format = SourceRenderer.standardFormat(style);
        DocComment doc = DocComment.of(d -> d.paragraph(DocText.of(t -> t.text("Uses ")
                        .link(DocRef.method(CD_ENTRY, "getKey"))
                        .text(" and ")
                        .link(DocRef.type(ConstantDescs.CD_String))
                        .text(".")))
                .paragraph(HOSTILE));
        List<RenderableFile> files = List.of(
                ModuleFile.of("me.supcheg.example", mb -> mb.withDoc(doc).withExports("me.supcheg.example")),
                PackageInfoFile.of("me.supcheg.example").withDoc(doc),
                documented());
        for (RenderableFile file : files) {
            Path path = sourceDir.resolve(file.pathSuffix());
            Files.createDirectories(path.getParent());
            Files.writeString(path, file.render(format));
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
            boolean succeeded = compiler.getTask(
                            null,
                            fileManager,
                            diagnostics,
                            List.of("-d", outputDir.toString(), "-Xdoclint:all", "-Xlint:all"),
                            null,
                            fileManager.getJavaFileObjectsFromPaths(files.stream()
                                    .map(file -> sourceDir.resolve(file.pathSuffix()))
                                    .toList()))
                    .call();

            org.assertj.core.api.Assertions.assertThat(diagnostics.getDiagnostics())
                    .isEmpty();
            org.assertj.core.api.Assertions.assertThat(succeeded).isTrue();
        }
    }
}
