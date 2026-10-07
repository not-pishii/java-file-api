package me.supcheg.javafile.facts;

import java.util.List;

/// The proof that the class `Self` has every abstract method of the type
/// `X`: what the declaration of a class in the typed layer takes to extend
/// or implement the type. The class `X_Heritage` beside the metamodel of
/// the type gives it, after a step per abstract method the type had when
/// the metamodel was generated — so a method left out does not compile.
///
/// A type without such a class — one that is `final` or `sealed`, or has an
/// abstract method no generated class can implement — has no proof, and is
/// neither extended nor implemented.
///
/// The proof is of the type as the generator was compiled against it: the
/// typed layer holds it against the heritage of the type on the target
/// classpath, where the type may have got an abstract method.
///
/// @param <Self> the class being declared
/// @param <X> the type it extends or implements
public final class Complete<Self, X> {
    private final DeclaredToken<X> supertype;
    private final RefToken<Self> self;
    private final List<Step<Self>> steps;

    Complete(DeclaredToken<X> supertype, RefToken<Self> self, List<Step<Self>> steps) {
        this.supertype = supertype;
        this.self = self;
        this.steps = List.copyOf(steps);
    }

    /// The type the class extends or implements.
    ///
    /// @return the token of the type
    public DeclaredToken<X> supertype() {
        return supertype;
    }

    /// The class being declared.
    ///
    /// @return the token of the class
    public RefToken<Self> self() {
        return self;
    }

    /// The steps of the proof, in the order they were taken: an
    /// implementation per abstract method of the type.
    ///
    /// @return the steps
    public List<Step<Self>> steps() {
        return steps;
    }

    @Override
    public String toString() {
        return self + " has every abstract method of " + supertype;
    }

    /// A step of the proof.
    ///
    /// @param method the signature of the abstract method in the method table of the type
    /// @param how how the class has the method
    /// @param <Self> the class being declared
    public record Step<Self>(MethodTableTemplate.Signature method, Implementation<Self> how) {}
}
