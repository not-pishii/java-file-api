package me.supcheg.javafile.facts;

import java.util.List;
import java.util.stream.Stream;

/// The steps taken so far of the proof that the class `Self` extends or
/// implements the type `X`: what the stages of a generated `X_Heritage`
/// hand on to each other, each adding the [Implementation] of its abstract
/// method, until the last makes the [Complete] proof.
///
/// The steps are not for a generator to hold: a stage keeps them to itself,
/// and they start with [UnsafeFacts#steps(DeclaredToken, RefToken)].
///
/// @param <Self> the class being declared
/// @param <X> the type it extends or implements
public final class Steps<Self, X> {
    private final DeclaredToken<X> supertype;
    private final RefToken<Self> self;
    private final List<Complete.Step<Self>> taken;

    Steps(DeclaredToken<X> supertype, RefToken<Self> self, List<Complete.Step<Self>> taken) {
        this.supertype = supertype;
        this.self = self;
        this.taken = List.copyOf(taken);
    }

    /// The steps with one more: the implementation of an abstract method of
    /// the type.
    ///
    /// @param method the signature of the abstract method in the method table of the type
    /// @param how how the class has the method
    /// @return the steps taken, this one included
    /// @throws IllegalArgumentException if `how` is of another method: one of another name, or of
    ///                                  parameters that erase otherwise
    public Steps<Self, X> with(MethodTableTemplate.Signature method, Implementation<Self> how) {
        MethodSignature expected = method.instantiate(supertype.argumentErasures());
        MethodSignature given = how.how().satisfies().signature();
        if (!given.equals(expected)) {
            throw new IllegalArgumentException(
                    "the step " + expected + " of " + supertype + " takes an implementation of"
                            + " that method, got one of " + how.how().satisfies());
        }
        return new Steps<>(
                supertype,
                self,
                Stream.concat(taken.stream(), Stream.of(new Complete.Step<>(method, how)))
                        .toList());
    }

    /// The proof, every step taken.
    ///
    /// @return the proof
    public Complete<Self, X> complete() {
        return new Complete<>(supertype, self, taken);
    }
}
