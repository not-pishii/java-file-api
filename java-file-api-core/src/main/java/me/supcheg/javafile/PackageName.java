package me.supcheg.javafile;

import java.lang.constant.ClassDesc;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

/// The package of a compilation unit: a named one, or the unnamed package.
///
/// A named package is a dot-separated list of Java identifiers (JLS 7.4.1), so
/// `a.int.b`, `a..b` and `a.b.` are no packages and are refused with
/// `IllegalArgumentException` when the name is made, instead of producing a
/// file that does not compile.
public sealed interface PackageName permits PackageName.Named, PackageName.Unnamed {

    /// Returns the package `name` denotes: the unnamed package for the empty
    /// string, a named package otherwise.
    ///
    /// @param name the dot-separated name of the package, or the empty string
    /// @return the package
    /// @throws IllegalArgumentException if `name` is neither empty nor a
    ///                                  dot-separated list of Java identifiers
    static PackageName of(String name) {
        return name.isEmpty() ? new Unnamed() : new Named(name);
    }

    /// Returns the package of the class or interface `desc` describes.
    ///
    /// @param desc the descriptor of a class or an interface
    /// @return its package
    /// @throws IllegalArgumentException if a part of the package name is not a Java identifier
    static PackageName of(ClassDesc desc) {
        return of(desc.packageName());
    }

    /// Returns the name as it is written in source, the empty string for the
    /// unnamed package.
    ///
    /// @return the dot-separated name
    String name();

    /// Returns the directory of the package, relative to a source root: one
    /// directory per identifier, the empty path for the unnamed package.
    ///
    /// @return the relative directory
    Path directory();

    /// A named package, e.g. `com.example`.
    ///
    /// @param name the dot-separated name: Java identifiers, at least one
    record Named(String name) implements PackageName {
        private static final Pattern DOT = Pattern.compile("[.]");

        /// Creates a named package.
        ///
        /// @throws IllegalArgumentException if `name` is not a dot-separated list of Java identifiers
        public Named {
            // a limit of -1 keeps the empty parts, which no identifier is: "a..b", "a.", ""
            for (String identifier : DOT.split(name, -1)) {
                try {
                    Identifiers.requireValid(identifier);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            "not a valid Java package name: '" + name + "': " + e.getMessage(), e);
                }
            }
        }

        /// Returns the identifiers of the name, outermost first.
        ///
        /// @return the identifiers
        public List<String> identifiers() {
            return List.of(DOT.split(name));
        }

        @Override
        public Path directory() {
            return Path.of("", identifiers().toArray(String[]::new));
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /// The unnamed package: its compilation units have no package declaration
    /// (JLS 7.4.2), and it has no `package-info.java`.
    record Unnamed() implements PackageName {
        @Override
        public String name() {
            return "";
        }

        @Override
        public Path directory() {
            return Path.of("");
        }

        @Override
        public String toString() {
            return "the unnamed package";
        }
    }
}
