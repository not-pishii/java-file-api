package me.supcheg.javafile.facts.processor;

import java.lang.constant.ClassDesc;

/// What became of a type in an earlier round: the one thing about a type the
/// processor has to remember, since a metamodel is written once and the
/// models of a round are gone with it.
sealed interface Done {

    /// This compilation generated its metamodel.
    ///
    /// @param metamodel the metamodel class
    /// @param full whether the metamodel is full, as `@Facts` asks for, rather than token-only
    record Generated(ClassDesc metamodel, boolean full) implements Done {}

    /// A metamodel on the classpath is reused.
    ///
    /// @param metamodel the metamodel class
    /// @param full whether the metamodel is full
    record Reused(ClassDesc metamodel, boolean full) implements Done {}

    /// It has no metamodel; an error is reported.
    record Failed() implements Done {}
}
