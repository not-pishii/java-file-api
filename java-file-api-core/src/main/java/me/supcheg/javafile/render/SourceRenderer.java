package me.supcheg.javafile.render;

import me.supcheg.javafile.RenderableFile;
import me.supcheg.javafile.doc.DocStyle;

/// Turns a file into source text with custom formatting.
///
/// [me.supcheg.javafile.RenderableFile#render()] uses 4-space indentation and
/// `\n` line breaks, and writes documentation comments as traditional ones,
/// `/** ... */`. For other settings, render with another format:
///
/// ```java
/// String tabs = file.render(SourceRenderer.format("\t", "\r\n"));
/// String markdown = file.render(SourceRenderer.standardFormat(DocStyle.MARKDOWN));
/// ```
public interface SourceRenderer {

    /// Returns the source text of a file.
    ///
    /// @param meta the file's contents, from [me.supcheg.javafile.RenderableFile#renderMeta()]
    /// @param format the indentation and line-separator preferences to render with
    /// @return the complete source text
    String render(RenderableFile.Meta meta, Format format);

    /// Formatting settings: indentation, line separator and the syntax of
    /// documentation comments. Create one with [#format(String,String,DocStyle)],
    /// [#format(String,String)] or [#standardFormat()].
    interface Format {
        /// The current indentation, already repeated to the current nesting depth.
        String pad();

        /// The line separator inserted between rendered lines.
        String newline();

        /// A copy of this format one nesting level deeper.
        Format withIncreasedPad();

        /// A copy of this format at the top nesting level (no indentation).
        Format withoutPad();

        /// The syntax documentation comments are written in.
        ///
        /// @return the syntax; [DocStyle#TRADITIONAL] unless the format says otherwise
        default DocStyle docStyle() {
            return DocStyle.TRADITIONAL;
        }
    }

    /// The default format: 4-space indentation, `\n` line separator.
    ///
    /// @return the default format
    static Format standardFormat() {
        return standardFormat(DocStyle.TRADITIONAL);
    }

    /// The default format with documentation comments in the given syntax.
    ///
    /// @param docStyle the syntax of documentation comments
    /// @return the format
    static Format standardFormat(DocStyle docStyle) {
        return format(" ".repeat(4), "\n", docStyle);
    }

    /// Creates a format with custom indentation and line separator, and
    /// traditional documentation comments.
    ///
    /// @param padUnit one level of indentation, e.g. `"\t"` or `"  "`
    /// @param lineSeparator the line separator, e.g. `"\n"` or `"\r\n"`
    /// @return the format
    static Format format(String padUnit, String lineSeparator) {
        return format(padUnit, lineSeparator, DocStyle.TRADITIONAL);
    }

    /// Creates a format with custom indentation, line separator and syntax
    /// of documentation comments.
    ///
    /// @param padUnit one level of indentation, e.g. `"\t"` or `"  "`
    /// @param lineSeparator the line separator, e.g. `"\n"` or `"\r\n"`
    /// @param docStyle the syntax of documentation comments
    /// @return the format
    static Format format(String padUnit, String lineSeparator, DocStyle docStyle) {
        record Impl(String padUnit, String pad, String newline, DocStyle docStyle) implements Format {
            @Override
            public Format withIncreasedPad() {
                return new Impl(padUnit, pad + padUnit, newline, docStyle);
            }

            @Override
            public Format withoutPad() {
                return new Impl(padUnit, "", newline, docStyle);
            }
        }

        return new Impl(padUnit, "", lineSeparator, docStyle);
    }
}
