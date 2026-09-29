package me.supcheg.javafile.facts.processor;

import java.util.SortedSet;

/// The base of the mirror packages (mini-spec §2.1, Q2): the metamodel of
/// `p.q.T` is `<base>.p.q.T_`.
///
/// The base is `-Ajavafile.facts.package` when given. Without it, the base
/// is `P.facts` when every `@Facts` of the compilation is in one package
/// `P`, and there is none otherwise: a fixed default would give two modules
/// the same metamodel classes in a split package, of which the classpath
/// silently picks the first.
sealed interface BasePackage {

    /// The name of the default base under package `P`.
    String DEFAULT_SUFFIX = "facts";

    /// Decides the base.
    ///
    /// @param options the processor options
    /// @param packages the packages of the `@Facts` annotations seen so far, `""` for the unnamed one
    /// @return the base, or why there is none
    static BasePackage of(Options options, SortedSet<String> packages) {
        if (options.basePackage().isPresent()) {
            return new Chosen(options.basePackage().get());
        }
        if (packages.size() == 1) {
            String only = packages.first();
            return new Chosen(only.isEmpty() ? DEFAULT_SUFFIX : only + "." + DEFAULT_SUFFIX);
        }
        return new Ambiguous(packages);
    }

    /// The base is known.
    ///
    /// @param name the package name
    record Chosen(String name) implements BasePackage {}

    /// `@Facts` is in several packages and no option names the base.
    ///
    /// @param packages the packages, sorted
    record Ambiguous(SortedSet<String> packages) implements BasePackage {

        /// The error to report.
        ///
        /// @return the message
        String message() {
            return "@Facts is used in several packages " + packages + "; choose the package of the metamodels with -A"
                    + Options.PACKAGE + "=<package>";
        }
    }
}
