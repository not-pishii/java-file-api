package me.supcheg.javafile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/// Checks that a name can be used as a Java identifier.
///
/// The library checks every name you pass (fields, methods, parameters, ...),
/// so a mistake such as `"class"` or `"my-field"` fails immediately with
/// `IllegalArgumentException` instead of producing a file that does not
/// compile. Contextual keywords such as `var`, `record`, or `yield` are allowed
/// as the names of variables and methods, and refused as the names of types:
/// see [#requireTypeIdentifier(String)].
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

    // JLS 3.8: an identifier that is none of these is a TypeIdentifier.
    private static final Set<String> NOT_TYPE_IDENTIFIERS = Set.of("permits", "record", "sealed", "var", "yield");

    private Identifiers() {}

    /// Returns `name` if it is a valid Java identifier.
    ///
    /// @param name the candidate identifier
    /// @return `name`, unchanged
    /// @throws IllegalArgumentException if `name` is not a valid Java identifier
    public static String requireValid(String name) {
        if (!isValid(name)) {
            throw new IllegalArgumentException("not a valid Java identifier: '" + name + "'");
        }
        return name;
    }

    /// Tells whether `name` is a valid Java identifier — a class file may
    /// name a member by what is none, such as `toString-impl`, and Java
    /// source cannot name that member.
    ///
    /// @param name the candidate identifier
    /// @return `true` if [#requireValid(String)] accepts `name`
    public static boolean isValid(String name) {
        return !name.isEmpty()
                && Character.isJavaIdentifierStart(name.codePointAt(0))
                && name.codePoints().allMatch(Identifiers::isWrittenIdentifierPart)
                && !RESERVED.contains(name);
    }

    // The compiler drops an ignorable character of an identifier (`Character.isIdentifierIgnorable`), so a name
    // with one is another name in the class file, and two names that differ by one are the same name.
    private static boolean isWrittenIdentifierPart(int codePoint) {
        return Character.isJavaIdentifierPart(codePoint) && !Character.isIdentifierIgnorable(codePoint);
    }

    /// Returns `name` if it can name a class, an interface or a type parameter:
    /// a valid Java identifier that is not `permits`, `record`, `sealed`, `var`
    /// or `yield` (JLS 3.8, `TypeIdentifier`).
    ///
    /// @param name the candidate name of a type
    /// @return `name`, unchanged
    /// @throws IllegalArgumentException if `name` is not a valid Java type identifier
    public static String requireTypeIdentifier(String name) {
        if (NOT_TYPE_IDENTIFIERS.contains(requireValid(name))) {
            throw new IllegalArgumentException("not a valid Java type identifier: '" + name + "'");
        }
        return name;
    }

    /// Returns `names` if it contains only valid Java identifiers.
    ///
    /// @param names collection with candidate identifiers
    /// @return `names`, copied via [List#copyOf]
    /// @throws IllegalArgumentException if `names` contains any invalid Java identifier
    public static List<String> requireValid(List<? extends String> names) {
        var violations = new ArrayList<IllegalArgumentException>();
        for (var name : names) {
            try {
                requireValid(name);
            } catch (IllegalArgumentException e) {
                violations.add(e);
            }
        }
        if (!violations.isEmpty()) {
            var ex = new IllegalArgumentException(names + " contains invalid Java identifier(s)");
            violations.forEach(ex::addSuppressed);
            throw ex;
        }
        return List.copyOf(names);
    }
}
