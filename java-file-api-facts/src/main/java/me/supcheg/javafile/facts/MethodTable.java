package me.supcheg.javafile.facts;

import java.util.Set;

/// The methods of a declared type, declared and inherited, by erased
/// signature. Lowering uses it to check that a class implements every
/// abstract method it inherits, and to tell whether a call has any other
/// candidate javac would consider: a `static` method of the same name and
/// arity is one, though it cannot be the target of an instance call.
///
/// @param abstractMethods the instance methods without an implementation
/// @param concreteMethods the instance methods with an implementation
/// @param staticMethods the `static` methods
public record MethodTable(
        Set<MethodSignature> abstractMethods,
        Set<MethodSignature> concreteMethods,
        Set<MethodSignature> staticMethods) {

    /// A table with no methods.
    public static final MethodTable EMPTY = new MethodTable(Set.of(), Set.of(), Set.of());

    public MethodTable {
        abstractMethods = Set.copyOf(abstractMethods);
        concreteMethods = Set.copyOf(concreteMethods);
        staticMethods = Set.copyOf(staticMethods);
    }
}
