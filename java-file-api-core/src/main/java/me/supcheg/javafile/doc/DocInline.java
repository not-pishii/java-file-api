package me.supcheg.javafile.doc;

/// A part of a [DocText].
public sealed interface DocInline {

    /// Plain text. It is written so that it reads as given, whatever
    /// characters it has: what would be markup, end the comment or start a
    /// tag is escaped. A line break in it breaks the line of the comment.
    ///
    /// @param text the text
    record Text(String text) implements DocInline {
        /// @throws IllegalArgumentException if `text` is empty
        public Text {
            if (text.isEmpty()) {
                throw new IllegalArgumentException("text must not be empty");
            }
        }
    }

    /// Code, shown in a monospaced font, as given.
    ///
    /// @param code the code
    record Code(String code) implements DocInline {
        /// @throws IllegalArgumentException if `code` is empty
        public Code {
            if (code.isEmpty()) {
                throw new IllegalArgumentException("code must not be empty");
            }
        }
    }

    /// A link to a program element, shown as its name.
    ///
    /// @param target the element
    record Link(DocRef target) implements DocInline {}
}
