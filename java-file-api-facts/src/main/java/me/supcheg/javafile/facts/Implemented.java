package me.supcheg.javafile.facts;

/// How a class being declared has a method of a supertype: the answer an
/// [Implementation] gives to a step of the proof that the class extends or
/// implements the supertype.
public sealed interface Implemented permits Implemented.ByOverride, Implemented.ByInheritance {

    /// The method of the supertype the class has so: the one it overrides,
    /// or the one it inherits.
    ///
    /// @return the fact of the method
    Invocable satisfies();

    /// The class declares a method that overrides the method.
    ///
    /// @param overridden the fact of the method of the supertype
    /// @param own the fact of the method the class declares
    record ByOverride(Invocable overridden, Invocable own) implements Implemented {
        @Override
        public Invocable satisfies() {
            return overridden;
        }
    }

    /// The class inherits the method, with its body, from its superclass.
    ///
    /// @param inherited the fact of the method of the superclass
    record ByInheritance(Invocable inherited) implements Implemented {
        @Override
        public Invocable satisfies() {
            return inherited;
        }
    }
}
