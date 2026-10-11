package me.supcheg.javafile.facts;

/// How the class `Self` has a method of a supertype, typed by the result
/// and the parameters of the method in the generated families
/// `ImplementationN<Self, R, A1..AN>` and `VoidImplementationN<Self, A1..AN>`.
///
/// A step of the proof that a class extends or implements a type — a
/// method of the class `X_Heritage` beside the metamodel of the type — takes
/// one for the abstract method it is the step of: of exactly its parameters,
/// and of its result or a subtype (JLS 8.4.5). One implementation may answer
/// the steps of several supertypes that have the method.
///
/// Each family is these classes and no more: `OverridingN`, of a method the
/// class declares, which gives the fact of that method; `InheritedN`, of a
/// method the class has from its superclass, which has none to give; and, of
/// a method with a result, `SelfOverridingN`, of a method the class declares
/// with the class itself for its result — typed by a supertype of the class
/// a witness of the typed layer names, which is what a step takes it by,
/// while the fact it gives is of the result `Self`.
///
/// An implementation is made by the declaration of the class in the typed
/// layer, and by no generator: the families are all there are, their
/// constructors are not public, and the factories are those of [UnsafeFacts].
///
/// @param <Self> the class being declared
public sealed interface Implementation<Self>
        permits Implementation0,
                Implementation1,
                Implementation2,
                Implementation3,
                Implementation4,
                Implementation5,
                Implementation6,
                Implementation7,
                Implementation8,
                Implementation9,
                Implementation10,
                Implementation11,
                Implementation12,
                VoidImplementation0,
                VoidImplementation1,
                VoidImplementation2,
                VoidImplementation3,
                VoidImplementation4,
                VoidImplementation5,
                VoidImplementation6,
                VoidImplementation7,
                VoidImplementation8,
                VoidImplementation9,
                VoidImplementation10,
                VoidImplementation11,
                VoidImplementation12 {

    /// How the class has the method.
    ///
    /// @return by a method of its own, or by one it inherits
    Implemented how();
}
