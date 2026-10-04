package me.supcheg.javafile.doc;

import java.util.List;
import java.util.function.Consumer;

/// The documentation comment of a declaration: a description and block tags.
/// It says something: a comment with neither is rejected.
///
/// Create one with [#of(Consumer)], or [#of(String)] for a single paragraph
/// of plain text.
///
/// @param description the paragraphs and lists of the main description, in order
/// @param tags the block tags, in the order they are written
public record DocComment(List<DocBlock> description, List<DocTag> tags) {
    /// @throws IllegalArgumentException if both `description` and `tags` are empty
    public DocComment {
        description = List.copyOf(description);
        tags = List.copyOf(tags);
        if (description.isEmpty() && tags.isEmpty()) {
            throw new IllegalArgumentException("a documentation comment must have a description or a tag");
        }
    }

    /// Builds a comment.
    ///
    /// @param spec receives the builder to populate the comment
    /// @return the comment
    /// @throws IllegalArgumentException if `spec` adds neither a description nor a tag
    public static DocComment of(Consumer<? super DocCommentBuilder> spec) {
        DocCommentBuilder builder = new DocCommentBuilder();
        spec.accept(builder);
        return builder.build();
    }

    /// A comment of one paragraph of plain text.
    ///
    /// @param text the text, written as it is: nothing in it is markup
    /// @return the comment
    /// @throws IllegalArgumentException if `text` is empty
    public static DocComment of(String text) {
        return new DocComment(List.of(new DocBlock.Paragraph(DocText.of(text))), List.of());
    }
}
