package me.supcheg.javafile.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

final class JavaStrings {

    private JavaStrings() {}

    static String escape(String raw) {
        StringBuilder sb = new StringBuilder(raw.length());
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            switch (c) {
                case '\\' -> sb.append("\\\\");
                case '"' -> sb.append("\\\"");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                // `\u0085`, `\u2028` and `\u2029` are no line terminators of the source, but
                // they are ones of the white space check of a text block, so the escape keeps
                // them out of the source text while the string keeps them in its value
                case '\u0085', '\u2028', '\u2029' -> sb.append(String.format(Locale.ROOT, "\\u%04x", (int) c));
                default -> {
                    // a surrogate without its pair is no character: no encoding writes it
                    boolean paired = Character.isHighSurrogate(c)
                            ? i + 1 < raw.length() && Character.isLowSurrogate(raw.charAt(i + 1))
                            : i > 0 && Character.isHighSurrogate(raw.charAt(i - 1));
                    if (c < 0x20 || (Character.isSurrogate(c) && !paired)) {
                        sb.append(String.format(Locale.ROOT, "\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }

    /// Whether a text block can write `value` without a warning under `-Xlint:all`.
    ///
    /// javac tells where the white space of a text block is trailing by splitting its content on
    /// `\R`, and `\R` matches `\u0085`, `\u2028` and `\u2029` even though the source does not
    /// read them as line terminators. A block that holds one of them cuts a line of the source
    /// in two: the content before it reads as ending in white space and the content after it
    /// reads as unindented, so `checkWhitespace` reports both kinds of white space. No escape
    /// writes such a character into a block, as a Unicode escape is translated to the character
    /// before the check runs, so a value that holds one is rendered as a string literal.
    ///
    /// @param value any string
    /// @return whether a text block can hold `value`
    static boolean fitsTextBlock(String value) {
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '\u0085' || c == '\u2028' || c == '\u2029') {
                return false;
            }
        }
        return true;
    }

    /// Splits `value` into the content lines of a text block that evaluates to exactly `value`.
    ///
    /// Each line goes on its own source line between the opening `"""` line and a closing `"""`
    /// on a separate line, indented no deeper than any non-empty line. The lines carry no line
    /// terminators and no indentation; an empty line stays empty, so it can be written without
    /// trailing white space.
    ///
    /// The lines survive every step of JLS 3.10.6:
    /// - `\` becomes `\\`, so no `\` of the value starts an escape or a Unicode escape;
    /// - every third `"` of a run becomes `\"`, so no `"""` of the value closes the block;
    /// - `\r`, `\t`, `\b`, `\f` get their escapes and other control characters three-digit octal
    ///   escapes: a raw `\r` is a line terminator, and a Unicode escape is translated back to the
    ///   raw character before white space stripping;
    /// - a trailing space becomes `\s`, and other trailing white space is followed by `\n` and a
    ///   `\` line continuation instead of the line terminator, so no trailing white space is stripped;
    /// - the last line of a value that does not end with `\n` ends with a `\` line continuation,
    ///   so the closing delimiter adds no line terminator.
    ///
    /// @param value any string
    /// @return the content lines, at least one
    static List<String> textBlockLines(String value) {
        String[] logicalLines = value.split("\n", -1);
        int last = logicalLines.length - 1;
        int count = last > 0 && logicalLines[last].isEmpty() ? last : last + 1;

        List<String> lines = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String escaped = escapeTextBlockLine(logicalLines[i]);
            lines.add(i == last ? escaped + "\\" : keepTrailingWhiteSpace(escaped));
        }
        return lines;
    }

    private static String escapeTextBlockLine(String line) {
        StringBuilder sb = new StringBuilder(line.length());
        int quoteRun = 0;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            quoteRun = c == '"' ? quoteRun + 1 : 0;
            switch (c) {
                case '\\' -> sb.append("\\\\");
                case '"' -> sb.append(quoteRun % 3 == 0 ? "\\\"" : "\"");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                case '\b' -> sb.append("\\b");
                case '\f' -> sb.append("\\f");
                default -> {
                    if (c < 0x20) {
                        sb.append(String.format(Locale.ROOT, "\\%03o", (int) c));
                    } else {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }

    private static String keepTrailingWhiteSpace(String escapedLine) {
        if (escapedLine.isEmpty()) {
            return escapedLine;
        }
        char lastChar = escapedLine.charAt(escapedLine.length() - 1);
        if (lastChar == ' ') {
            return escapedLine.substring(0, escapedLine.length() - 1) + "\\s";
        }
        if (Character.isWhitespace(lastChar)) {
            return escapedLine + "\\n\\";
        }
        return escapedLine;
    }
}
