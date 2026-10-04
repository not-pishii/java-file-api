package me.supcheg.javafile.render;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.ModuleFile;
import me.supcheg.javafile.PackageInfoFile;
import me.supcheg.javafile.annotation.AnnotationValues;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.doc.DocInline;
import me.supcheg.javafile.doc.DocRef;
import me.supcheg.javafile.doc.DocStyle;
import me.supcheg.javafile.doc.DocText;
import me.supcheg.javafile.model.CanonicalConstructorDecl;
import me.supcheg.javafile.model.CompactConstructorDecl;
import me.supcheg.javafile.model.Modifier;
import me.supcheg.javafile.model.Param;
import me.supcheg.javafile.type.Types;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static me.supcheg.javafile.code.Exprs.field;
import static me.supcheg.javafile.code.Exprs.literal;
import static org.assertj.core.api.Assertions.assertThat;

class DocRendererTest {
    private static final ClassDesc CD_LIST = ClassDesc.of("java.util", "List");
    private static final ClassDesc CD_ENTRY = ClassDesc.of("java.util", "Map").nested("Entry");
    private static final ClassDesc CD_IO_EXCEPTION = ClassDesc.of("java.io", "IOException");
    private static final ClassDesc GREETER = ClassDesc.of("com.example", "Greeter");

    private static final Context MARKDOWN =
            Context.of(SourceRenderer.standardFormat(DocStyle.MARKDOWN), new ImportManager("com.example"));
    private static final Context TRADITIONAL =
            Context.of(SourceRenderer.standardFormat(DocStyle.TRADITIONAL), new ImportManager("com.example"));

    private static String markdown(DocComment doc) {
        return DocRenderer.render(doc, MARKDOWN);
    }

    private static String traditional(DocComment doc) {
        return DocRenderer.render(doc, TRADITIONAL);
    }

    private static final DocComment EVERYTHING = DocComment.of(d -> d.paragraph("Greets.")
            .paragraph(DocText.of(t -> t.text("Like ")
                    .link(DocRef.method(ConstantDescs.CD_String, "concat", ConstantDescs.CD_String))
                    .text(", but with ")
                    .code("name")
                    .text(".")))
            .list(List.of(DocText.of("first"), DocText.of(t -> t.code("second").text(" item\nover two lines"))))
            .paragraph("The end.")
            .typeParam("T", "what is greeted")
            .param("name", "who is greeted,\nby name")
            .returns("the greeting")
            .throws_(CD_IO_EXCEPTION, "if nobody listens")
            .see(DocRef.field(ConstantDescs.CD_Integer, "MAX_VALUE"))
            .since("1.0")
            .deprecated(DocText.of(t -> t.text("use ").link(DocRef.type(ConstantDescs.CD_String)))));

    @Test
    void noCommentIsNoText() {
        assertThat(DocRenderer.render(Optional.empty(), MARKDOWN)).isEmpty();
        assertThat(DocRenderer.render(Optional.empty(), TRADITIONAL)).isEmpty();
    }

    @Test
    void aMarkdownCommentHasAnEmptyLineBetweenBlocksAndBeforeTheTags() {
        assertThat(markdown(EVERYTHING)).isEqualTo("""
                        /// Greets.
                        ///
                        /// Like [String#concat(String)], but with `name`.
                        ///
                        /// - first
                        /// - `second` item
                        ///   over two lines
                        ///
                        /// The end.
                        ///
                        /// @param <T> what is greeted
                        /// @param name who is greeted,
                        ///     by name
                        /// @return the greeting
                        /// @throws java.io.IOException if nobody listens
                        /// @see Integer#MAX_VALUE
                        /// @since 1.0
                        /// @deprecated use [String]
                        """);
    }

    @Test
    void aTraditionalCommentMarksTheParagraphsAfterTheFirstAndWritesAListInHtml() {
        assertThat(traditional(EVERYTHING)).isEqualTo("""
                        /**
                         * Greets.
                         *
                         * <p>Like {@link String#concat(String)}, but with {@code name}.
                         *
                         * <ul>
                         *   <li>first</li>
                         *   <li>{@code second} item
                         *     over two lines</li>
                         * </ul>
                         *
                         * <p>The end.
                         *
                         * @param <T> what is greeted
                         * @param name who is greeted,
                         *     by name
                         * @return the greeting
                         * @throws java.io.IOException if nobody listens
                         * @see Integer#MAX_VALUE
                         * @since 1.0
                         * @deprecated use {@link String}
                         */
                        """);
    }

    @Test
    void aCommentOfTagsAloneHasNoEmptyLine() {
        DocComment doc = DocComment.of(d -> d.returns("the name"));

        assertThat(markdown(doc)).isEqualTo("/// @return the name\n");
        assertThat(traditional(doc)).isEqualTo("/**\n * @return the name\n */\n");
    }

    @Test
    void aCommentIsWrittenAtTheIndentationAndWithTheLineSeparatorOfTheFormat() {
        DocComment doc = DocComment.of(d -> d.paragraph("One.").paragraph("Two."));
        Context markdown = Context.of(SourceRenderer.format("\t", "\r\n", DocStyle.MARKDOWN), new ImportManager("p"))
                .withIncreasedPad();
        Context traditional = Context.of(SourceRenderer.format("\t", "\r\n"), new ImportManager("p"))
                .withIncreasedPad()
                .withIncreasedPad();

        assertThat(DocRenderer.render(doc, markdown)).isEqualTo("\t/// One.\r\n\t///\r\n\t/// Two.\r\n");
        assertThat(DocRenderer.render(doc, traditional))
                .isEqualTo("\t\t/**\r\n\t\t * One.\r\n\t\t *\r\n\t\t * <p>Two.\r\n\t\t */\r\n");
    }

    @Test
    void aFormatWritesTraditionalCommentsUnlessItSaysOtherwise() {
        assertThat(SourceRenderer.standardFormat().docStyle()).isEqualTo(DocStyle.TRADITIONAL);
        assertThat(SourceRenderer.format("  ", "\n").withIncreasedPad().docStyle())
                .isEqualTo(DocStyle.TRADITIONAL);
        assertThat(SourceRenderer.standardFormat(DocStyle.MARKDOWN)
                        .withIncreasedPad()
                        .withoutPad()
                        .docStyle())
                .isEqualTo(DocStyle.MARKDOWN);
        SourceRenderer.Format custom = new SourceRenderer.Format() {
            @Override
            public String pad() {
                return "";
            }

            @Override
            public String newline() {
                return "\n";
            }

            @Override
            public SourceRenderer.Format withIncreasedPad() {
                return this;
            }

            @Override
            public SourceRenderer.Format withoutPad() {
                return this;
            }
        };
        assertThat(custom.docStyle()).isEqualTo(DocStyle.TRADITIONAL);
    }

    // ---- references

    @Test
    void aReferenceNamesTheErasedParametersAndAConstructorByTheNameOfItsClass() {
        DocComment doc = DocComment.of(d -> d.paragraph(DocText.of(
                new DocInline.Link(DocRef.type(CD_ENTRY)),
                new DocInline.Text(" "),
                new DocInline.Link(DocRef.field(GREETER, "name")),
                new DocInline.Text(" "),
                new DocInline.Link(DocRef.method(GREETER, "greet")),
                new DocInline.Text(" "),
                new DocInline.Link(DocRef.method(
                        GREETER,
                        "greet",
                        CD_LIST,
                        ConstantDescs.CD_int.arrayType(2),
                        CD_ENTRY.arrayType(),
                        ConstantDescs.CD_Object)),
                new DocInline.Text(" "),
                new DocInline.Link(DocRef.constructor(CD_ENTRY, ConstantDescs.CD_int)),
                new DocInline.Text(" "),
                new DocInline.Link(DocRef.constructor(GREETER)))));

        assertThat(markdown(doc)).isEqualTo("""
                        /// [java.util.Map.Entry] [Greeter#name] [Greeter#greet()] \
                        [Greeter#greet(java.util.List, int\\[\\]\\[\\], java.util.Map.Entry\\[\\], Object)] \
                        [java.util.Map.Entry#Entry(int)] [Greeter#Greeter()]
                        """);
        assertThat(traditional(doc)).isEqualTo("""
                        /**
                         * {@link java.util.Map.Entry} {@link Greeter#name} {@link Greeter#greet()} \
                        {@link Greeter#greet(java.util.List, int[][], java.util.Map.Entry[], Object)} \
                        {@link java.util.Map.Entry#Entry(int)} {@link Greeter#Greeter()}
                         */
                        """);
    }

    @Test
    void seeTakesAReferenceAsItIsInEitherSyntax() {
        DocComment doc = DocComment.of(d -> d.see(DocRef.method(GREETER, "greet", ConstantDescs.CD_int.arrayType())));

        assertThat(markdown(doc)).isEqualTo("/// @see Greeter#greet(int[])\n");
        assertThat(traditional(doc)).isEqualTo("/**\n * @see Greeter#greet(int[])\n */\n");
    }

    // ---- text

    @Test
    void markdownTextIsEscapedWhereItWouldBeMarkup() {
        assertThat(markdown(DocComment.of("a*b `d` [e] <f> g&h; i|j ~k~ back\\slash")))
                .isEqualTo(
                        "/// a&#42;b &#96;d&#96; &#91;e&#93; &lt;f> g&amp;h; i&#124;j &#126;k&#126; back&#92;slash\n");
    }

    @Test
    void anUnderscoreIsEscapedOnlyWhereItWouldStartEmphasis() {
        assertThat(markdown(DocComment.of("MAX_VALUE a_1_b _c_ d_ x_, _e __f__ _ g _")))
                .isEqualTo("/// MAX_VALUE a_1_b &#95;c_ d_ x_, &#95;e &#95;&#95;f__ _ g &#95;\n");
        assertThat(markdown(DocComment.of(d -> d.paragraph(DocText.of(
                        new DocInline.Text("a_"),
                        new DocInline.Code("b"),
                        new DocInline.Text("_c _"),
                        new DocInline.Code("d"))))))
                .isEqualTo("/// a_`b`&#95;c &#95;`d`\n");
        assertThat(traditional(DocComment.of("_c_"))).isEqualTo("/**\n * _c_\n */\n");
    }

    @Test
    void markdownTextIsEscapedAtTheStartOfALineWhereItWouldStartABlock() {
        assertThat(markdown(DocComment.of(
                        "# heading\n> quote\n- item\n+ item\n=\n1. one\n22) two\n3 three\n4.5\n6.\n@tag")))
                .isEqualTo("""
                        /// &#35; heading
                        /// &gt; quote
                        /// &#45; item
                        /// &#43; item
                        /// &#61;
                        /// 1&#46; one
                        /// 22&#41; two
                        /// 3 three
                        /// 4.5
                        /// 6&#46;
                        /// &#64;tag
                        """);
        assertThat(markdown(DocComment.of("a # b > c - d + e = f 1. g @h")))
                .isEqualTo("/// a # b > c - d + e = f 1. g @h\n");
    }

    @Test
    void traditionalTextIsEscapedWhereItWouldBeHtmlATagOrTheEndOfTheComment() {
        assertThat(traditional(DocComment.of("a<b> & c */ d *\\/ back\\slash {@code e} f@g\n@tag # - 1. *x* `y`")))
                .isEqualTo("""
                        /**
                         * a&lt;b&gt; &amp; c *&#47; d *&#92;/ back&#92;slash {&#64;code e} f@g
                         * &#64;tag # - 1. *x* `y`
                         */
                        """);
    }

    @Test
    void theEndOfACommentDoesNotComeFromTwoPartsOfAText() {
        DocComment doc = DocComment.of(d -> d.paragraph(
                DocText.of(new DocInline.Text("a*"), new DocInline.Text("/b {"), new DocInline.Text("@c"))));

        assertThat(traditional(doc)).isEqualTo("/**\n * a*&#47;b {&#64;c\n */\n");
        assertThat(markdown(doc)).isEqualTo("/// a&#42;/b {&#64;c\n");
    }

    @Test
    void aBackslashNeverStartsAUnicodeEscape() {
        DocComment doc = DocComment.of("\\u000a and \\\\u002a/");

        assertThat(markdown(doc)).isEqualTo("/// &#92;u000a and &#92;&#92;u002a/\n");
        assertThat(traditional(doc)).isEqualTo("/**\n * &#92;u000a and &#92;&#92;u002a/\n */\n");
    }

    @Test
    void aLineBreakBreaksTheLineAndTheSpacesAroundItAreDropped() {
        DocComment doc = DocComment.of("  one  \r\n\r\n    two\rthree\n\tfour\u0000five\n   ");

        assertThat(markdown(doc)).isEqualTo("/// one\n/// two\n/// three\n/// four five\n");
        assertThat(traditional(doc)).isEqualTo("/**\n * one\n * two\n * three\n * four five\n */\n");
    }

    @Test
    void aTextOfSpacesAloneIsNoLine() {
        DocComment doc = DocComment.of(d -> d.paragraph(" \n ")
                .paragraph("text")
                .list(List.of(DocText.of(" ")))
                .returns(" "));

        assertThat(markdown(doc)).isEqualTo("/// text\n///\n/// @return\n");
        assertThat(traditional(doc)).isEqualTo("/**\n * text\n *\n * @return\n */\n");
    }

    // ---- code

    @Test
    void markdownCodeIsBetweenMoreBackticksThanItHas() {
        DocComment doc = DocComment.of(d -> d.paragraph(DocText.of(
                new DocInline.Code("a*b_c<d>&[e]"),
                new DocInline.Text(" "),
                new DocInline.Code("a `b` ``c"),
                new DocInline.Text(" "),
                new DocInline.Code("`"),
                new DocInline.Text(" "),
                new DocInline.Code(" x"),
                new DocInline.Text(" "),
                new DocInline.Code("  "))));

        assertThat(markdown(doc)).isEqualTo("/// `a*b_c<d>&[e]` ```a `b` ``c``` `` ` `` `  x ` `  `\n");
    }

    @Test
    void markdownCodeWithABackslashOrALineBreakIsHtml() {
        DocComment doc = DocComment.of(d -> d.paragraph(
                DocText.of(new DocInline.Code("\\u000a"), new DocInline.Text(" "), new DocInline.Code("a\nb*"))));

        assertThat(markdown(doc)).isEqualTo("/// <code>&#92;u000a</code> `a b*`\n");
    }

    @Test
    void traditionalCodeIsAnInlineTagUnlessTheTagWouldNotHoldIt() {
        DocComment doc = DocComment.of(d -> d.paragraph(DocText.of(
                new DocInline.Code("List<String> a & b"),
                new DocInline.Text(" "),
                new DocInline.Code("{a}"),
                new DocInline.Text(" "),
                new DocInline.Code("@b"),
                new DocInline.Text(" "),
                new DocInline.Code("c */"),
                new DocInline.Text(" "),
                new DocInline.Code("\\u000a"),
                new DocInline.Text(" "),
                new DocInline.Code("d\neé"))));

        assertThat(traditional(doc)).isEqualTo("""
                        /**
                         * {@code List<String> a & b} <code>&#123;a&#125;</code> <code>&#64;b</code> \
                        <code>c &#42;&#47;</code> <code>&#92;u000a</code> {@code d eé}
                         */
                        """);
    }

    // ---- declarations

    @Test
    void aCommentComesBeforeTheAnnotationsOfEveryKindOfDeclaration() {
        ClassDesc outer = ClassDesc.of("com.example", "Outer");
        JavaFile file = JavaFile.class_(
                outer,
                cb -> cb.withDoc(DocComment.of("A class."))
                        .withAnnotation(ClassDesc.of("java.lang", "Deprecated"))
                        .withField(
                                "count",
                                Types.INT,
                                fb -> fb.withDoc(DocComment.of("A field."))
                                        .withAnnotation(ClassDesc.of("java.lang", "Deprecated")))
                        .withConstructor(ctor -> ctor.withDoc(DocComment.of("A constructor.")))
                        .withVoidMethod("run", mb -> mb.withDoc(d -> d.paragraph("A method.")))
                        .withAbstractMethod(
                                "area", Types.DOUBLE, mb -> mb.withDoc(DocComment.of("An abstract method.")))
                        .withNestedInterface(
                                outer.nested("Shape"),
                                ib -> ib.withDoc(DocComment.of("An interface."))
                                        .withConstant(
                                                "SIDES",
                                                Types.INT,
                                                kb -> kb.withDoc(DocComment.of("A constant."))
                                                        .withInitializer(literal(4)))
                                        .withAbstractMethod(
                                                "area",
                                                Types.DOUBLE,
                                                mb -> mb.withDoc(DocComment.of("An abstract method.")))
                                        .withDefaultMethod(
                                                "sides",
                                                Types.INT,
                                                mb -> mb.withDoc(DocComment.of("A default method."))
                                                        .withBody(b -> b.return_(field("SIDES"))))
                                        .withStaticMethod(
                                                "none",
                                                Types.INT,
                                                mb -> mb.withDoc(DocComment.of("A static method."))
                                                        .withBody(b -> b.return_(literal(0)))))
                        .withNestedRecord(outer.nested("Point"), rb -> {
                            rb.withDoc(d -> d.paragraph("A record.").param("x", "the abscissa"))
                                    .withComponent("x", Types.INT)
                                    .withStaticField(
                                            "ORIGIN",
                                            Types.INT,
                                            fb -> fb.withDoc(DocComment.of("A static field."))
                                                    .withInitializer(literal(0)));
                            rb.accept(new CompactConstructorDecl(
                                    List.of(),
                                    Set.of(Modifier.PUBLIC),
                                    new me.supcheg.javafile.code.CodeBody(List.of()),
                                    List.of(),
                                    Optional.of(DocComment.of("A compact constructor."))));
                            rb.accept(new CanonicalConstructorDecl(
                                    List.of(),
                                    Set.of(Modifier.PUBLIC),
                                    List.of(new Param("x", Types.INT)),
                                    new me.supcheg.javafile.code.CodeBody(List.of()),
                                    List.of(),
                                    Optional.of(DocComment.of("A canonical constructor."))));
                        })
                        .withNestedEnum(
                                outer.nested("Level"),
                                eb -> eb.withDoc(DocComment.of("An enum."))
                                        .withConstant(
                                                "LOW",
                                                c -> c.withDoc(DocComment.of("A constant."))
                                                        .withAnnotation(ClassDesc.of("java.lang", "Deprecated")))
                                        .withConstant("HIGH", literal(1))
                                        .withConstructor(ctor -> ctor.withDoc(DocComment.of("An enum constructor."))))
                        .withNestedAnnotationType(
                                outer.nested("Mark"),
                                ab -> ab.withDoc(DocComment.of("An annotation."))
                                        .withElement("value", Types.STRING, DocComment.of("An element."))
                                        .withElement(
                                                "count",
                                                Types.INT,
                                                AnnotationValues.literal(1),
                                                DocComment.of("Another."))
                                        .withElement("plain", Types.INT)));

        assertThat(file.render(SourceRenderer.standardFormat(DocStyle.MARKDOWN)))
                .isEqualTo("""
                        package com.example;

                        /// A class.
                        @Deprecated
                        public class Outer {
                            /// A field.
                            @Deprecated
                            public int count;

                            /// A constructor.
                            public Outer() {
                            }

                            /// A method.
                            public void run() {
                            }

                            /// An abstract method.
                            public abstract double area();

                            /// An interface.
                            public interface Shape {
                                /// A constant.
                                int SIDES = 4;

                                /// An abstract method.
                                double area();

                                /// A default method.
                                default int sides() {
                                    return SIDES;
                                }

                                /// A static method.
                                static int none() {
                                    return 0;
                                }
                            }

                            /// A record.
                            ///
                            /// @param x the abscissa
                            public record Point(int x) {
                                /// A static field.
                                public static final int ORIGIN = 0;

                                /// A compact constructor.
                                public Point {
                                }

                                /// A canonical constructor.
                                public Point(int x) {
                                }
                            }

                            /// An enum.
                            public enum Level {
                                /// A constant.
                                @Deprecated LOW,
                                HIGH(1);

                                /// An enum constructor.
                                Level() {
                                }
                            }

                            /// An annotation.
                            public @interface Mark {
                                /// An element.
                                String value();
                                /// Another.
                                int count() default 1;
                                int plain();
                            }
                        }
                        """);
    }

    @Test
    void theConstantsOfAnEnumWithoutACommentStayOnOneLine() {
        JavaFile file = JavaFile.enum_(
                ClassDesc.of("com.example", "Level"),
                eb -> eb.withDoc(DocComment.of("An enum.")).withConstant("LOW").withConstant("HIGH"));

        assertThat(file.render()).isEqualTo("""
                        package com.example;

                        /**
                         * An enum.
                         */
                        public enum Level {
                            LOW, HIGH;
                        }
                        """);
    }

    @Test
    void aPackageAndAModuleHaveTheirCommentFirst() {
        DocComment doc = DocComment.of(d -> d.paragraph(DocText.of(
                t -> t.text("See ").link(DocRef.type(CD_LIST)).text(" and ").link(DocRef.type(GREETER)))));

        assertThat(PackageInfoFile.of(
                                "com.example",
                                new me.supcheg.javafile.annotation.AnnotationUse(
                                        ClassDesc.of("java.lang", "Deprecated"), List.of()))
                        .withDoc(doc)
                        .render(SourceRenderer.standardFormat(DocStyle.MARKDOWN)))
                .isEqualTo("""
                        /// See [java.util.List] and [Greeter]
                        @Deprecated
                        package com.example;
                        """);
        assertThat(ModuleFile.of("com.example", mb -> mb.withDoc(doc).withExports("com.example"))
                        .render())
                .isEqualTo("""
                        /**
                         * See {@link java.util.List} and {@link com.example.Greeter}
                         */
                        module com.example {
                            exports com.example;
                        }
                        """);
        assertThat(ModuleFile.of("com.example", mb -> mb.withDoc(d -> d.since("1")))
                        .render(SourceRenderer.standardFormat(DocStyle.MARKDOWN)))
                .isEqualTo("""
                        /// @since 1
                        module com.example {
                        }
                        """);
    }
}
