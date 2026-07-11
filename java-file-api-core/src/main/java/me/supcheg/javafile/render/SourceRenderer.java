package me.supcheg.javafile.render;

import me.supcheg.javafile.model.TypeDecl;

/// Renders a package declaration, computed imports, and a top-level type
/// declaration into Java source text.
///
/// [StandardRenderer] is the only implementation. Type resolution (how a
/// `ClassDesc` becomes a simple or fully qualified name) is entirely owned by
/// the renderer, not the caller — a [Format] only carries formatting
/// preferences, so there is no way to construct one that resolves types
/// incorrectly or not at all.
public interface SourceRenderer {

    /// Renders `decl` as a complete source file in package `packageName`.
    ///
    /// @param packageName the file's package
    /// @param decl the top-level type declaration to render
    /// @param format the indentation and line-separator preferences to render with
    /// @return the complete source text
    String render(String packageName, TypeDecl decl, Format format);

    /// The caller-controlled formatting preferences for a render call:
    /// indentation unit and line separator. Carries no type-resolution
    /// capability — only the renderer that receives a `Format` decides how
    /// types are resolved into source text.
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
    static Format standardFormat() {
        return format(" ".repeat(4), "\n");
    }

    /// Builds a format with a custom indentation unit and line separator.
    ///
    /// @param padUnit the whitespace repeated per nesting level
    /// @param lineSeparator the line separator inserted between rendered lines
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
