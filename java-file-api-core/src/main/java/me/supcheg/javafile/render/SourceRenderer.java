package me.supcheg.javafile.render;

import me.supcheg.javafile.RenderableFile;

/// Turns a file into source text with custom formatting.
///
/// [me.supcheg.javafile.RenderableFile#render()] uses 4-space indentation and
/// `\n` line breaks. For other settings, render through [StandardRenderer]:
///
/// ```java
/// String source = StandardRenderer.instance()
///         .render(file.renderMeta(), SourceRenderer.format("\t", "\r\n"));
/// ```
public interface SourceRenderer {

    /// Returns the source text of a file.
    ///
    /// @param meta the file's contents, from [me.supcheg.javafile.RenderableFile#renderMeta()]
    /// @param format the indentation and line-separator preferences to render with
    /// @return the complete source text
    String render(RenderableFile.Meta meta, Format format);

    /// Formatting settings: indentation and line separator. Create one with
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
    }

    /// The default format: 4-space indentation, `\n` line separator.
    ///
    /// @return the default format
    static Format standardFormat() {
        return format(" ".repeat(4), "\n");
    }

    /// Creates a format with custom indentation and line separator.
    ///
    /// @param padUnit one level of indentation, e.g. `"\t"` or `"  "`
    /// @param lineSeparator the line separator, e.g. `"\n"` or `"\r\n"`
    /// @return the format
    static Format format(String padUnit, String lineSeparator) {
        record Impl(String padUnit, String pad, String newline) implements Format {
            @Override
            public Format withIncreasedPad() {
                return new Impl(padUnit, pad + padUnit, newline);
            }

            @Override
            public Format withoutPad() {
                return new Impl(padUnit, "", newline);
            }
        }

        return new Impl(padUnit, "", lineSeparator);
    }
}
