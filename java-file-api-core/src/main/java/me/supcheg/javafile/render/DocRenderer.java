package me.supcheg.javafile.render;

import me.supcheg.javafile.doc.DocBlock;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.doc.DocInline;
import me.supcheg.javafile.doc.DocRef;
import me.supcheg.javafile.doc.DocStyle;
import me.supcheg.javafile.doc.DocTag;
import me.supcheg.javafile.doc.DocText;
import me.supcheg.javafile.type.ClassDescNames;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// Writes a documentation comment in the syntax of the format, each line at
// the indentation of the declaration that follows.
//
// Plain text is written so that it reads as given. What would mean something
// else is written as a character reference (`&#42;`), in either syntax:
//
// - `\` everywhere: javac reads `\` `u` as a Unicode escape in a comment too,
//   which may end the comment or the line, or fail to compile. That is also
//   why a Markdown comment has no backslash escapes — and javadoc, looking
//   for tags, skips two characters after a backslash, a line break among
//   them, and then misses the block tag of the next line;
// - `@` at the start of a line, where it would start a block tag, and after
//   `{`, where it would start an inline tag;
// - in a traditional comment `&`, `<`, `>`, which are HTML, and `/` after
//   `*`, which would end the comment;
// - in a Markdown comment every character of inline markup — `_` only where
//   it would start emphasis, not within a name or after one — and at the
//   start of a line what would start a heading, a quote, a list or a rule.
//
// A line break breaks the line of the comment, and the spaces after it are
// dropped: a line of a Markdown comment indented by four is a code block.
//
// A type is named as `TypeContext#mention` names it: a comment imports
// nothing.
final class DocRenderer {
    private static final String MARKDOWN_INLINE = "\\`*[]<&|~";
    private static final String MARKDOWN_LINE_START = "#>-+=";
    private static final String CONTINUATION = "    ";

    private DocRenderer() {}

    static String render(Optional<DocComment> doc, Context ctx) {
        return doc.map(comment -> render(comment, ctx)).orElse("");
    }

    static String render(DocComment doc, Context ctx) {
        DocStyle style = ctx.docStyle();
        Stream<String> lines = lines(doc, ctx, style).stream();
        return switch (style) {
            case MARKDOWN ->
                lines.map(line -> ctx.pad() + (line.isEmpty() ? "///" : "/// " + line) + ctx.newline())
                        .collect(Collectors.joining());
            case TRADITIONAL ->
                lines.map(line -> ctx.pad() + (line.isEmpty() ? " *" : " * " + line) + ctx.newline())
                        .collect(Collectors.joining(
                                "", ctx.pad() + "/**" + ctx.newline(), ctx.pad() + " */" + ctx.newline()));
        };
    }

    // The lines of a comment without what starts them: the blocks of the
    // description that say something, an empty line between two, then the
    // tags. A traditional comment marks every paragraph but the first.
    private static List<String> lines(DocComment doc, Context ctx, DocStyle style) {
        List<DocBlock> said = doc.description().stream()
                .filter(block -> !block(block, ctx, style).isEmpty())
                .toList();
        Stream<List<String>> description = IntStream.range(0, said.size()).mapToObj(i -> {
            List<String> lines = block(said.get(i), ctx, style);
            return style == DocStyle.TRADITIONAL && i > 0 && said.get(i) instanceof DocBlock.Paragraph
                    ? prefixed("<p>", "", lines)
                    : lines;
        });
        List<String> tags =
                doc.tags().stream().flatMap(tag -> tag(tag, ctx, style)).toList();
        return Stream.concat(description, Stream.of(tags).filter(lines -> !lines.isEmpty()))
                .reduce((before, after) -> Stream.of(before, List.of(""), after)
                        .flatMap(List::stream)
                        .toList())
                .orElse(List.of());
    }

    private static List<String> block(DocBlock block, Context ctx, DocStyle style) {
        return switch (block) {
            case DocBlock.Paragraph(DocText text) -> text(text, ctx, style);
            case DocBlock.BulletList list -> {
                List<List<String>> items = list.items().toList().stream()
                        .map(item -> text(item, ctx, style))
                        .filter(item -> !item.isEmpty())
                        .toList();
                yield switch (style) {
                    case MARKDOWN ->
                        items.stream()
                                .flatMap(item -> prefixed("- ", "  ", item).stream())
                                .toList();
                    case TRADITIONAL ->
                        items.isEmpty()
                                ? List.<String>of()
                                : Stream.of(
                                                Stream.of("<ul>"),
                                                items.stream().flatMap(DocRenderer::item),
                                                Stream.of("</ul>"))
                                        .flatMap(Function.identity())
                                        .toList();
                };
            }
        };
    }

    // `<li>` … `</li>` around the lines of an item.
    private static Stream<String> item(List<String> lines) {
        List<String> opened = prefixed("  <li>", CONTINUATION, lines);
        return IntStream.range(0, opened.size())
                .mapToObj(i -> i == opened.size() - 1 ? opened.get(i) + "</li>" : opened.get(i));
    }

    private static Stream<String> tag(DocTag tag, Context ctx, DocStyle style) {
        return switch (tag) {
            case DocTag.Param(String name, DocText description) -> tag("@param " + name, description, ctx, style);
            case DocTag.TypeParam(String name, DocText description) ->
                tag("@param <" + name + ">", description, ctx, style);
            case DocTag.Return(DocText description) -> tag("@return", description, ctx, style);
            case DocTag.Throws(ClassDesc exception, DocText description) ->
                tag("@throws " + ctx.mention(exception), description, ctx, style);
            case DocTag.See(DocRef target) -> Stream.of("@see " + reference(target, ctx, "[]"));
            case DocTag.Since(DocText version) -> tag("@since", version, ctx, style);
            case DocTag.Deprecated(DocText description) -> tag("@deprecated", description, ctx, style);
        };
    }

    private static Stream<String> tag(String head, DocText description, Context ctx, DocStyle style) {
        List<String> lines = text(description, ctx, style);
        return lines.isEmpty() ? Stream.of(head) : prefixed(head + " ", CONTINUATION, lines).stream();
    }

    private static List<String> prefixed(String first, String rest, List<String> lines) {
        return IntStream.range(0, lines.size())
                .mapToObj(i -> (i == 0 ? first : rest) + lines.get(i))
                .toList();
    }

    // The lines of a text: none of them empty, none ending with a space.
    private static List<String> text(DocText text, Context ctx, DocStyle style) {
        Line line = new Line(style);
        for (DocInline part : text.parts().toList()) {
            switch (part) {
                case DocInline.Text(String plain) -> line.text(plain);
                case DocInline.Code(String code) -> line.markup(code(code, style));
                case DocInline.Link(DocRef target) ->
                    line.markup(
                            switch (style) {
                                case MARKDOWN -> "[" + reference(target, ctx, "\\[\\]") + "]";
                                case TRADITIONAL -> "{@link " + reference(target, ctx, "[]") + "}";
                            });
            }
        }
        return line.out
                .toString()
                .lines()
                .map(String::stripTrailing)
                .filter(each -> !each.isEmpty())
                .toList();
    }

    // A program element as javadoc reads a reference to it.
    private static String reference(DocRef ref, Context ctx, String array) {
        return switch (ref) {
            case DocRef.Type(ClassDesc type) -> ctx.mention(type);
            case DocRef.Field(ClassDesc owner, String name) -> ctx.mention(owner) + "#" + name;
            case DocRef.Method(ClassDesc owner, String name, List<ClassDesc> params) ->
                ctx.mention(owner) + "#" + name + params(params, ctx, array);
            case DocRef.Constructor(ClassDesc owner, List<ClassDesc> params) ->
                ctx.mention(owner) + "#" + ClassDescNames.leafSimpleName(owner) + params(params, ctx, array);
        };
    }

    private static String params(List<ClassDesc> params, Context ctx, String array) {
        return params.stream().map(param -> param(param, ctx, array)).collect(Collectors.joining(", ", "(", ")"));
    }

    private static String param(ClassDesc param, Context ctx, String array) {
        if (param.isArray()) {
            return param(param.componentType(), ctx, array) + array;
        }
        return param.isPrimitive() ? param.displayName() : ctx.mention(param);
    }

    private static String code(String code, DocStyle style) {
        String flat = code.chars()
                .map(c -> Character.isISOControl(c) ? ' ' : c)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return switch (style) {
            case MARKDOWN -> flat.indexOf('\\') < 0 ? codeSpan(flat) : "<code>" + entities(flat) + "</code>";
            case TRADITIONAL ->
                flat.chars().noneMatch(c -> "{}@\\".indexOf(c) >= 0) && !flat.contains("*/")
                        ? "{@code " + flat + "}"
                        : "<code>" + entities(flat) + "</code>";
        };
    }

    // A code span: between more backticks than the code has in a row, and
    // spaces if it starts or ends with one or with a space.
    private static String codeSpan(String code) {
        int longest = 0;
        int run = 0;
        for (int i = 0; i < code.length(); i++) {
            run = code.charAt(i) == '`' ? run + 1 : 0;
            longest = Math.max(longest, run);
        }
        String fence = "`".repeat(longest + 1);
        boolean blank = code.chars().allMatch(c -> c == ' ');
        boolean padded =
                !blank && ("` ".indexOf(code.charAt(0)) >= 0 || "` ".indexOf(code.charAt(code.length() - 1)) >= 0);
        return padded ? fence + " " + code + " " + fence : fence + code + fence;
    }

    // Every ASCII character that is not a letter, a digit or a space as a character reference.
    private static String entities(String code) {
        return code.chars()
                .mapToObj(c -> c >= 0x80 || c == ' ' || Character.isLetterOrDigit(c)
                        ? String.valueOf((char) c)
                        : "&#" + c + ";")
                .collect(Collectors.joining());
    }

    // The text of a paragraph, an item or a tag as it is written, with `\n` between its lines.
    private static final class Line {
        private final DocStyle style;
        private final StringBuilder out = new StringBuilder();
        private boolean lineStart = true;
        // What stands for the markup around a text: no letter, no digit, no space.
        private static final char MARKUP = '`';

        private char previous = '\n';

        Line(DocStyle style) {
            this.style = style;
        }

        void markup(String markup) {
            out.append(markup);
            lineStart = false;
            previous = MARKUP;
        }

        void text(String text) {
            int i = 0;
            while (i < text.length()) {
                char c = text.charAt(i);
                if (c == '\r' || c == '\n') {
                    i += c == '\r' && i + 1 < text.length() && text.charAt(i + 1) == '\n' ? 2 : 1;
                    out.append('\n');
                    lineStart = true;
                    previous = '\n';
                    continue;
                }
                if (Character.isISOControl(c)) {
                    c = ' ';
                }
                if (lineStart && c == ' ') {
                    i++;
                    continue;
                }
                int marker = style == DocStyle.MARKDOWN && lineStart ? orderedListMarker(text, i) : -1;
                if (marker >= 0) {
                    // `1.` and `1)` start an ordered list
                    out.append(text, i, marker).append(reference(text.charAt(marker)));
                    previous = text.charAt(marker);
                    i = marker + 1;
                } else {
                    // what follows the text is not known: as if it went on
                    out.append(escaped(c, i + 1 < text.length() ? text.charAt(i + 1) : MARKUP));
                    previous = c;
                    i++;
                }
                lineStart = false;
            }
        }

        // The index of the `.` or `)` that makes the digits a line starts with the marker of a list
        // item — the line ends after it, or goes on with a space —, or -1.
        private static int orderedListMarker(String text, int start) {
            int end = start;
            while (end < text.length() && text.charAt(end) >= '0' && text.charAt(end) <= '9') {
                end++;
            }
            boolean delimiter = end > start && end < text.length() && ").".indexOf(text.charAt(end)) >= 0;
            return delimiter && (end + 1 == text.length() || Character.isWhitespace(text.charAt(end + 1))) ? end : -1;
        }

        private String escaped(char c, char next) {
            if (c == '@' && (lineStart || previous == '{')) {
                return reference(c);
            }
            boolean markup =
                    switch (style) {
                        case MARKDOWN ->
                            MARKDOWN_INLINE.indexOf(c) >= 0
                                    || lineStart && MARKDOWN_LINE_START.indexOf(c) >= 0
                                    // `_` starts emphasis only after what is not a letter or a digit and
                                    // before what is not a space, and nothing ends what did not start
                                    || c == '_'
                                            && !Character.isLetterOrDigit(previous)
                                            && !Character.isWhitespace(next);
                        case TRADITIONAL -> "&<>\\".indexOf(c) >= 0 || c == '/' && previous == '*';
                    };
            return markup ? reference(c) : String.valueOf(c);
        }

        // A character as HTML and Markdown read it whatever is around it.
        private static String reference(char c) {
            return switch (c) {
                case '&' -> "&amp;";
                case '<' -> "&lt;";
                case '>' -> "&gt;";
                default -> "&#" + (int) c + ";";
            };
        }
    }
}
