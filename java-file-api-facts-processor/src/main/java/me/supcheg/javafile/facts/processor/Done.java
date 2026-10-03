package me.supcheg.javafile.facts.processor;

import java.lang.constant.ClassDesc;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/// What became of a type in an earlier round: the one thing about a type the
/// processor has to remember, since a metamodel is written once and the
/// models of a round are gone with it.
sealed interface Done {

    /// A metamodel this compilation generated.
    ///
    /// @param metamodel the metamodel class
    /// @param completeness whether it is full or token-only
    /// @return what became of its type
    static Done generated(ClassDesc metamodel, ReuseIndex.Completeness completeness) {
        return switch (completeness) {
            case FULL -> new GeneratedFull(metamodel);
            case TOKEN -> new GeneratedToken(metamodel);
        };
    }

    /// A metamodel of the classpath that is reused.
    ///
    /// @param metamodel the metamodel class
    /// @param completeness whether it is full or token-only
    /// @return what became of its type
    static Done reused(ClassDesc metamodel, ReuseIndex.Completeness completeness) {
        return switch (completeness) {
            case FULL -> new ReusedFull(metamodel);
            case TOKEN -> new ReusedToken(metamodel);
        };
    }

    /// This compilation generated its full metamodel, as that of a type `@Facts` asks for or of a
    /// supertype of one.
    ///
    /// @param metamodel the metamodel class
    record GeneratedFull(ClassDesc metamodel) implements Done {}

    /// This compilation generated its token-only metamodel: there will be no full one.
    ///
    /// @param metamodel the metamodel class
    record GeneratedToken(ClassDesc metamodel) implements Done {}

    /// A full metamodel on the classpath is reused.
    ///
    /// @param metamodel the metamodel class
    record ReusedFull(ClassDesc metamodel) implements Done {}

    /// A token-only metamodel on the classpath is reused for the tokens of the type: if a full one is
    /// wanted after all, it is looked for anew.
    ///
    /// @param metamodel the metamodel class
    record ReusedToken(ClassDesc metamodel) implements Done {}

    /// It has no metamodel; an error is reported.
    record Failed() implements Done {}

    /// Its metamodel is owed: metamodels written in earlier rounds refer to it, but it is not written
    /// yet, for the type may turn out a supertype of a type that is not there
    /// ([TypeGraph.Token.Held]). The types that mention it are done with, and are not read again to
    /// find it mentioned, so it is remembered here.
    ///
    /// @param mentioners the binary names of the types whose metamodels refer to it, sorted
    record Held(SortedSet<String> mentioners) implements Done {
        /// Copies the names.
        public Held {
            mentioners = Collections.unmodifiableSortedSet(new TreeSet<>(mentioners));
        }
    }
}
