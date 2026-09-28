package me.supcheg.javafile.facts;

import java.util.Set;

/// The instance methods of a declared type, declared and inherited, by erased
/// signature. Lowering uses it to check that a class implements every
/// abstract method it inherits.
///
/// @param abstractMethods the methods without an implementation
/// @param concreteMethods the methods with an implementation
public record MethodTable(Set<MethodSignature> abstractMethods, Set<MethodSignature> concreteMethods) {

    /// A table with no methods.
    public static final MethodTable EMPTY = new MethodTable(Set.of(), Set.of());

    public MethodTable {
        abstractMethods = Set.copyOf(abstractMethods);
        concreteMethods = Set.copyOf(concreteMethods);
    }
}
