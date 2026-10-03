package me.supcheg.javafile.facts.processor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/// The names a rendered class starts a name with in its body: every
/// identifier that does not follow a `.` — a class or field by its simple
/// name, the first name of a qualified name, a keyword. A field of the class
/// with such a name would hide what the name stands for.
///
/// The source is that of the core renderer for a metamodel: string and
/// character literals, no comments and no text blocks.
final class SourceNames {
    private SourceNames() {}

    /// The names in the body of a class.
    ///
    /// @param type the simple name of the top-level class of the source
    /// @param source the source file
    /// @return the names, each once
    /// @throws IllegalArgumentException if the source does not declare the class
    static Stream<String> inBodyOf(String type, String source) {
        Set<String> names = new HashSet<>();
        Place place = Place.BEFORE;
        boolean afterDot = false;
        int i = 0;
        while (i < source.length()) {
            char c = source.charAt(i);
            if (c == '"' || c == '\'') {
                i = afterLiteral(source, i);
                afterDot = false;
            } else if (Character.isJavaIdentifierStart(c)) {
                int end = i;
                while (end < source.length() && Character.isJavaIdentifierPart(source.charAt(end))) {
                    end++;
                }
                String word = source.substring(i, end);
                switch (place) {
                    case BEFORE -> place = word.equals("class") ? Place.DECLARATION : Place.BEFORE;
                    case DECLARATION -> place = word.equals(type) ? Place.HEADER : Place.BEFORE;
                    case HEADER -> {}
                    case BODY -> {
                        if (!afterDot) {
                            names.add(word);
                        }
                    }
                }
                afterDot = false;
                i = end;
            } else if (Character.isDigit(c)) {
                // a number, with its fraction, exponent and suffix
                while (i < source.length()
                        && (Character.isJavaIdentifierPart(source.charAt(i)) || source.charAt(i) == '.')) {
                    i++;
                }
                afterDot = false;
            } else {
                if (!Character.isWhitespace(c)) {
                    afterDot = c == '.';
                    if (place == Place.DECLARATION) {
                        place = Place.BEFORE;
                    } else if (place == Place.HEADER && c == '{') {
                        place = Place.BODY;
                    }
                }
                i++;
            }
        }
        if (place != Place.BODY) {
            throw new IllegalArgumentException("no body of class " + type);
        }
        return names.stream();
    }

    /// The index after the string or character literal that starts at `start`.
    private static int afterLiteral(String source, int start) {
        char quote = source.charAt(start);
        int i = start + 1;
        while (source.charAt(i) != quote) {
            i += source.charAt(i) == '\\' ? 2 : 1;
        }
        return i + 1;
    }

    /// Where in the source of a class a character is.
    private enum Place {
        /// Before the declaration: the package, the imports, the annotations of the class.
        BEFORE,
        /// After `class`, where the name of the class follows.
        DECLARATION,
        /// After the name of the class: its type parameters.
        HEADER,
        /// In the body.
        BODY
    }
}
