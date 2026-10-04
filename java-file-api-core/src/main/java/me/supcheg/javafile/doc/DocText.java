package me.supcheg.javafile.doc;

import me.supcheg.javafile.code.NonEmptyList;

import java.util.List;
import java.util.function.Consumer;

/// Text of a documentation comment: plain text, code and links, one after
/// another.
///
/// @param parts the parts, in order
public record DocText(NonEmptyList<DocInline> parts) {

    /// Plain text.
    ///
    /// @param text the text, written as it is: nothing in it is markup
    /// @return the text
    /// @throws IllegalArgumentException if `text` is empty
    public static DocText of(String text) {
        return new DocText(new NonEmptyList<>(new DocInline.Text(text), List.of()));
    }

    /// Text of the given parts.
    ///
    /// @param first the first part
    /// @param rest the parts after it, in order
    /// @return the text
    public static DocText of(DocInline first, DocInline... rest) {
        return new DocText(new NonEmptyList<>(first, List.of(rest)));
    }

    /// Builds text part by part.
    ///
    /// @param spec receives the builder to add the parts to
    /// @return the text
    /// @throws IllegalArgumentException if `spec` adds no part
    public static DocText of(Consumer<? super DocTextBuilder> spec) {
        DocTextBuilder builder = new DocTextBuilder();
        spec.accept(builder);
        return builder.build();
    }
}
