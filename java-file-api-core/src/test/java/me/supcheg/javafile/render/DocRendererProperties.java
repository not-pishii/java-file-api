package me.supcheg.javafile.render;

import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.doc.DocInline;
import me.supcheg.javafile.doc.DocStyle;
import me.supcheg.javafile.doc.DocText;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.constraints.StringLength;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/// Whatever the text and the code of a comment are, the comment stays one:
/// it does not end early, no line of it is outside it, and javac reads no
/// Unicode escape in it.
class DocRendererProperties {
    private static final String INDENT = "    ";

    /// Text of the characters that mean something to Java, javadoc, HTML or Markdown, among others.
    @Provide
    Arbitrary<String> hostile() {
        return Arbitraries.strings()
                .withChars("*/\\u@{}<>&`[]_#-+=|~.) \n\r\t01aZé\u0000")
                .ofMinLength(1)
                .ofMaxLength(40);
    }

    private static String render(DocStyle style, String text, String code) {
        DocComment doc = DocComment.of(d -> d.paragraph(
                        DocText.of(new DocInline.Text(text), new DocInline.Code(code), new DocInline.Text(text)))
                .list(List.of(DocText.of(text), DocText.of(new DocInline.Code(code))))
                .returns(text)
                .since(text));
        Context ctx = Context.of(SourceRenderer.standardFormat(style), new ImportManager("p"))
                .withIncreasedPad();
        return DocRenderer.render(doc, ctx);
    }

    @Property
    void everyLineOfAMarkdownCommentStartsTheComment(@ForAll("hostile") String text, @ForAll("hostile") String code) {
        String rendered = render(DocStyle.MARKDOWN, text, code);

        assertThat(rendered).endsWith("\n").doesNotContain("\r");
        assertThat(rendered.split("\n"))
                .isNotEmpty()
                .allSatisfy(line -> assertThat(line).startsWith(INDENT + "///"));
    }

    /// javadoc misses a block tag on the line after one that ends with a backslash escape.
    @Property
    void noLineOfAMarkdownCommentEndsWithABackslashEscape(
            @ForAll("hostile") String text, @ForAll("hostile") String code) {
        String rendered = render(DocStyle.MARKDOWN, text, code);

        assertThat(rendered.split("\n")).allSatisfy(line -> {
            int backslashes = 0;
            while (backslashes < line.length() - 1 && line.charAt(line.length() - 2 - backslashes) == '\\') {
                backslashes++;
            }
            assertThat(backslashes % 2).as(line).isZero();
        });
    }

    @Property
    void aTraditionalCommentEndsOnlyAtItsEnd(@ForAll("hostile") String text, @ForAll("hostile") String code) {
        String rendered = render(DocStyle.TRADITIONAL, text, code);

        assertThat(rendered)
                .startsWith(INDENT + "/**\n")
                .endsWith("\n" + INDENT + " */\n")
                .doesNotContain("\r");
        assertThat(rendered.indexOf("*/")).isEqualTo(rendered.length() - "*/\n".length());
        String[] lines = rendered.split("\n");
        assertThat(List.of(lines).subList(1, lines.length))
                .allSatisfy(line -> assertThat(line).startsWith(INDENT + " *"));
    }

    @Property
    void noBackslashOfACommentStartsAUnicodeEscape(
            @ForAll("hostile") String text, @ForAll("hostile") String code, @ForAll DocStyle style) {
        String rendered = render(style, text, code);

        // JLS 3.3: a backslash starts an escape if an even number of backslashes is before it and a `u` after it
        for (int i = 0; i < rendered.length(); i++) {
            if (rendered.charAt(i) == 'u') {
                int backslashes = 0;
                while (backslashes < i && rendered.charAt(i - 1 - backslashes) == '\\') {
                    backslashes++;
                }
                assertThat(backslashes % 2)
                        .as("backslashes before the u at %d of %s", i, rendered)
                        .isZero();
            }
        }
    }

    @Property
    void noControlCharacterIsLeftInAComment(
            @ForAll @StringLength(min = 1, max = 30) String text, @ForAll DocStyle style) {
        String rendered = render(style, text, text);

        assertThat(rendered.chars().filter(c -> Character.isISOControl(c) && c != '\n'))
                .isEmpty();
    }

    @Property
    void plainTextWithoutMarkupIsWrittenAsItIs(
            @ForAll @net.jqwik.api.constraints.AlphaChars @StringLength(min = 1, max = 30) String word,
            @ForAll DocStyle style) {
        Context ctx = Context.of(SourceRenderer.standardFormat(style), new ImportManager("p"));

        assertThat(DocRenderer.render(DocComment.of(word + " and " + word + "."), ctx))
                .contains(word + " and " + word + ".");
    }
}
