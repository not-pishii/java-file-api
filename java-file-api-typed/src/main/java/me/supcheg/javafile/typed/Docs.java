package me.supcheg.javafile.typed;

import me.supcheg.javafile.doc.DocComment;

/// The documentation comments of the typed layer: what is accepted, and
/// what is refused before it can be rendered into broken source.
///
/// A comment is prose, not a name: the typed layer takes it as it is written.
/// What a traditional comment cannot hold is `*/`, which ends the comment
/// early, so a comment whose text contains it is refused where it is given.
final class Docs {
    private Docs() {}

    /// The comment of a one-paragraph text.
    ///
    /// @param text the text
    /// @param described what the comment documents, for the message
    /// @return the comment
    /// @throws IllegalArgumentException if `text` is empty, or contains `*/`
    static DocComment of(String text, String described) {
        return requireSafe(DocComment.of(text), described);
    }

    /// The comment, if it can be written as one.
    ///
    /// @param doc the comment
    /// @param described what the comment documents, for the message
    /// @return the comment
    /// @throws IllegalArgumentException if the text of `doc` contains `*/`
    static DocComment requireSafe(DocComment doc, String described) {
        // a record prints every text it holds, so this is every word of the comment before it is escaped
        if (doc.toString().contains("*/")) {
            throw new IllegalArgumentException("the documentation of " + described + " contains */: a traditional"
                    + " comment ends there, and the typed layer renders no broken source");
        }
        return doc;
    }
}
