package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.doc.DocInline;
import me.supcheg.javafile.doc.DocStyle;
import me.supcheg.javafile.doc.DocText;
import me.supcheg.javafile.render.SourceRenderer;
import me.supcheg.javafile.type.Types;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import java.lang.constant.ClassDesc;
import java.util.List;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.code.Exprs.literal;

/// Whatever the text and the code of a comment are, the file compiles, and
/// doclint finds nothing in the comment: no text is read as markup, a tag or
/// the end of the comment.
class DocCommentCompileProperties {

    /// Text of the characters that mean something to Java, javadoc, HTML or Markdown, among others, and not blank.
    @Provide
    Arbitrary<String> hostile() {
        return Arbitraries.strings()
                .withChars("*/\\u@{}<>&`[]_#-+=|~.)(!:;\"' \n\r\t012aZxlinkcodeparamé")
                .ofMinLength(1)
                .ofMaxLength(60)
                // a tag that says nothing is what doclint reports as missing
                .filter(text -> !text.isBlank());
    }

    @Property(tries = 60)
    void aCommentOfAnyTextCompilesAndPassesDoclint(
            @ForAll("hostile") String text, @ForAll("hostile") String code, @ForAll DocStyle style) {
        JavaFile file = JavaFile.class_(
                ClassDesc.of("me.supcheg.example", "Hostile"),
                cb -> cb.withDoc(d -> d.paragraph(text)
                                .paragraph(DocText.of(
                                        new DocInline.Text(text), new DocInline.Code(code), new DocInline.Text(text)))
                                .list(List.of(DocText.of(text), DocText.of(new DocInline.Code(code))))
                                .since(text))
                        .withConstructor(ctor -> ctor.withDoc(d -> d.paragraph("Makes one.")))
                        .withMethod(
                                "answer",
                                Types.INT,
                                mb -> mb.withDoc(d -> d.paragraph(text)
                                                .param("question", text)
                                                .returns(text))
                                        .withParam("question", Types.STRING)
                                        .withBody(b -> b.return_(literal(42)))));

        Compilation compilation = javac().withOptions("-Xdoclint:all", "-Xlint:all")
                .compile(JavaFileObjects.forSourceString(
                        "me.supcheg.example.Hostile", file.render(SourceRenderer.standardFormat(style))));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
