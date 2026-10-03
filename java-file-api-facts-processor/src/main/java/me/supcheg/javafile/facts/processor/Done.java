package me.supcheg.javafile.facts.processor;

import java.lang.constant.ClassDesc;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/// What became of a type in an earlier round: the one thing about a type the
/// processor has to remember, since a metamodel is written once and the
/// models of a round are gone with it.
sealed interface Done {

    /// This compilation generated its metamodel.
    ///
    /// @param metamodel the metamodel class
    /// @param full whether the metamodel is full, as that of a type `@Facts` asks for or of a supertype of
    ///     one, rather than token-only
    record Generated(ClassDesc metamodel, boolean full) implements Done {}

    /// A metamodel on the classpath is reused.
    ///
    /// @param metamodel the metamodel class
    /// @param full whether the metamodel is full
    record Reused(ClassDesc metamodel, boolean full) implements Done {}

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
