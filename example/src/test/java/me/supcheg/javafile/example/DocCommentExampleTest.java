package me.supcheg.javafile.example;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;
import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.doc.DocRef;
import me.supcheg.javafile.doc.DocStyle;
import me.supcheg.javafile.doc.DocText;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.render.SourceRenderer;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.literal;
import static org.assertj.core.api.Assertions.assertThat;

/// The example of the README: one comment, written in either syntax.
class DocCommentExampleTest {
    private static final ClassDesc GREETER = ClassDesc.of("com.example", "Greeter");
    private static final ClassDesc CD_LOCALE = ClassDesc.of("java.util", "Locale");

    private static JavaFile greeter() {
        return JavaFile.class_(
                GREETER,
                cb -> cb.withDoc(d -> d.paragraph("Greets people by name.")
                                .paragraph(DocText.of(t -> t.text("The greeting is formatted with ")
                                        .link(DocRef.method(
                                                ConstantDescs.CD_String,
                                                "format",
                                                CD_LOCALE,
                                                ConstantDescs.CD_String,
                                                ConstantDescs.CD_Object.arrayType()))
                                        .text(", so ")
                                        .code("%s")
                                        .text(" & co. work in <templates>.")))
                                .since("1.0"))
                        .withModifiers(Modifier.FINAL)
                        .withMethod(
                                "greet",
                                Types.STRING,
                                mb -> mb.withDoc(d -> d.paragraph("Greets one person.")
                                                .param("name", "who is greeted")
                                                .returns(DocText.of(t -> t.text("the greeting, never ")
                                                        .code("null")))
                                                .see(DocRef.type(CD_LOCALE)))
                                        .withParam("name", Types.STRING)
                                        .withBody(b ->
                                                b.return_(literal("Hello, ").call("concat", field("name"))))));
    }

    @Test
    void aTraditionalCommentIsTheDefault() {
        String source = greeter().render();

        assertThat(source).isEqualTo("""
                        package com.example;

                        /**
                         * Greets people by name.
                         *
                         * <p>The greeting is formatted with {@link String#format(java.util.Locale, String, Object[])}, so {@code %s} &amp; co. work in &lt;templates&gt;.
                         *
                         * @since 1.0
                         */
                        public final class Greeter {
                            /**
                             * Greets one person.
                             *
                             * @param name who is greeted
                             * @return the greeting, never {@code null}
                             * @see java.util.Locale
                             */
                            public String greet(String name) {
                                return "Hello, ".concat(name);
                            }
                        }
                        """);
        assertPassesDoclint(source);
    }

    @Test
    void theSameCommentInMarkdown() {
        String source = greeter().render(SourceRenderer.standardFormat(DocStyle.MARKDOWN));

        assertThat(source).isEqualTo("""
                        package com.example;

                        /// Greets people by name.
                        ///
                        /// The greeting is formatted with [String#format(java.util.Locale, String, Object\\[\\])], so `%s` &amp; co. work in &lt;templates>.
                        ///
                        /// @since 1.0
                        public final class Greeter {
                            /// Greets one person.
                            ///
                            /// @param name who is greeted
                            /// @return the greeting, never `null`
                            /// @see java.util.Locale
                            public String greet(String name) {
                                return "Hello, ".concat(name);
                            }
                        }
                        """);
        assertPassesDoclint(source);
    }

    /// All but `missing`: the class has a default constructor, which has no comment.
    private static void assertPassesDoclint(String source) {
        Compilation compilation = javac().withOptions("-Xdoclint:all,-missing", "-Xlint:all")
                .compile(JavaFileObjects.forSourceString("com.example.Greeter", source));

        assertThat(compilation).succeededWithoutWarnings();
    }
}
