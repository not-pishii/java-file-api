package me.supcheg.javafile.doc;

import me.supcheg.javafile.Identifiers;

import java.lang.constant.ClassDesc;

/// A block tag of a [DocComment].
public sealed interface DocTag {

    /// `@param name`: a parameter of a method or constructor, or a component of a record.
    ///
    /// @param name the name of the parameter
    /// @param description what it is
    record Param(String name, DocText description) implements DocTag {
        /// @throws IllegalArgumentException if `name` is not an identifier
        public Param {
            name = Identifiers.requireValid(name);
        }
    }

    /// `@param <T>`: a type parameter.
    ///
    /// @param name the name of the type parameter
    /// @param description what it is
    record TypeParam(String name, DocText description) implements DocTag {
        /// @throws IllegalArgumentException if `name` is not an identifier
        public TypeParam {
            name = Identifiers.requireValid(name);
        }
    }

    /// `@return`: what a method returns.
    ///
    /// @param description what it is
    record Return(DocText description) implements DocTag {}

    /// `@throws`: an exception a method or constructor throws.
    ///
    /// @param exception the class of the exception
    /// @param description when it is thrown
    record Throws(ClassDesc exception, DocText description) implements DocTag {
        /// @throws IllegalArgumentException if `exception` is a primitive or an array
        public Throws {
            if (!exception.isClassOrInterface()) {
                throw new IllegalArgumentException("not a class: " + exception.displayName());
            }
        }
    }

    /// `@see`: a program element to look at.
    ///
    /// @param target the element
    record See(DocRef target) implements DocTag {}

    /// `@since`: the version that introduced the declaration.
    ///
    /// @param version the version
    record Since(DocText version) implements DocTag {}

    /// `@deprecated`: why the declaration should not be used, and what to use instead.
    ///
    /// @param description the explanation
    record Deprecated(DocText description) implements DocTag {}
}
