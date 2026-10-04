package me.supcheg.javafile.doc;

/// The syntax documentation comments are written in.
///
/// A [DocComment] is rendered in either; its text is escaped for the syntax
/// chosen.
public enum DocStyle {
    /// A traditional comment, `/** ... */`, in HTML and inline tags: read by
    /// every version of Java.
    TRADITIONAL,
    /// A Markdown comment, each line after `///` (JEP 467). A compiler older
    /// than Java 23 reads it as an ordinary comment: the code compiles, the
    /// documentation is lost.
    MARKDOWN
}
