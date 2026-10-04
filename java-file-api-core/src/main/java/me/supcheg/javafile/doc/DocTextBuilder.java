package me.supcheg.javafile.doc;

import me.supcheg.javafile.code.NonEmptyList;

import java.util.ArrayList;
import java.util.List;

/// Builds a [DocText] part by part.
///
/// Obtained from [DocText#of(java.util.function.Consumer)].
///
/// Instances are not thread-safe.
public final class DocTextBuilder {

    private final List<DocInline> parts = new ArrayList<>();

    DocTextBuilder() {}

    /// Adds plain text.
    ///
    /// @param text the text, written as it is: nothing in it is markup
    /// @return this builder
    /// @throws IllegalArgumentException if `text` is empty
    public DocTextBuilder text(String text) {
        return part(new DocInline.Text(text));
    }

    /// Adds code.
    ///
    /// @param code the code, shown as given
    /// @return this builder
    /// @throws IllegalArgumentException if `code` is empty
    public DocTextBuilder code(String code) {
        return part(new DocInline.Code(code));
    }

    /// Adds a link to a program element.
    ///
    /// @param target the element
    /// @return this builder
    public DocTextBuilder link(DocRef target) {
        return part(new DocInline.Link(target));
    }

    /// Adds a pre-built part.
    ///
    /// @param part the part
    /// @return this builder
    public DocTextBuilder part(DocInline part) {
        parts.add(part);
        return this;
    }

    DocText build() {
        return new DocText(NonEmptyList.copyOf(parts));
    }
}
