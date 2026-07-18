package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.type.TypeRef;

import java.util.Optional;

/// A resource declared or referenced by a try-with-resources [TryStmt].
public sealed interface Resource permits Resource.Declared, Resource.Existing {

    /// A newly declared resource, e.g. `Reader r = open()` or `var r = open()`.
    ///
    /// @param type the declared resource type, or empty to infer it with `var`
    /// @param name the resource variable name
    /// @param initializer the initializer expression
    record Declared(Optional<TypeRef> type, String name, Expr initializer) implements Resource {
        public Declared {
            name = Identifiers.requireValid(name);
        }
    }

    /// A reference to an existing effectively-final variable used as a
    /// resource, e.g. `try (existingReader) { ... }`.
    ///
    /// @param name the referenced variable's name
    record Existing(String name) implements Resource {
        public Existing {
            name = Identifiers.requireValid(name);
        }
    }
}
