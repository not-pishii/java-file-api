package me.supcheg.javafile.doc;

import me.supcheg.javafile.code.NonEmptyList;

/// A block of the description of a [DocComment].
public sealed interface DocBlock {

    /// A paragraph.
    ///
    /// @param text what it says
    record Paragraph(DocText text) implements DocBlock {}

    /// A list of items, each marked with a bullet.
    ///
    /// @param items the items, in order
    record BulletList(NonEmptyList<DocText> items) implements DocBlock {}
}
