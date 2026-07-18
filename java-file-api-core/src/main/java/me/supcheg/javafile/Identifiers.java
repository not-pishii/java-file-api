package me.supcheg.javafile;

import java.util.Set;

/// Validates that a name is a syntactically valid Java identifier (JLS 3.8),
/// excluding the reserved keywords and boolean/null literals (JLS 3.9).
/// Contextual keywords (`var`, `yield`, `record`, `sealed`, `permits`,
/// `when`, ...) are not reserved and remain valid identifiers.
public final class Identifiers {

    private static final Set<String> RESERVED = Set.of(
            "abstract",
            "assert",
            "boolean",
            "break",
            "byte",
            "case",
            "catch",
            "char",
            "class",
            "const",
            "continue",
            "default",
            "do",
            "double",
            "else",
            "enum",
            "extends",
            "final",
            "finally",
            "float",
            "for",
            "goto",
            "if",
            "implements",
            "import",
            "instanceof",
            "int",
            "interface",
            "long",
            "native",
            "new",
            "package",
            "private",
            "protected",
            "public",
            "return",
            "short",
            "static",
            "strictfp",
            "super",
            "switch",
            "synchronized",
            "this",
            "throw",
            "throws",
            "transient",
            "try",
            "void",
            "volatile",
            "while",
            "true",
            "false",
            "null",
            "_");

    private Identifiers() {}

    /// Validates that `name` is a syntactically valid Java identifier.
    ///
    /// @param name the candidate identifier
    /// @return `name`, unchanged
    /// @throws IllegalArgumentException if `name` is not a valid Java identifier
    public static String requireValid(String name) {
        if (name.isEmpty() || !Character.isJavaIdentifierStart(name.charAt(0))) {
            throw new IllegalArgumentException("not a valid Java identifier: '" + name + "'");
        }
        for (int i = 1; i < name.length(); i++) {
            if (!Character.isJavaIdentifierPart(name.charAt(i))) {
                throw new IllegalArgumentException("not a valid Java identifier: '" + name + "'");
            }
        }
        if (RESERVED.contains(name)) {
            throw new IllegalArgumentException("not a valid Java identifier: '" + name + "'");
        }
        return name;
    }
}
