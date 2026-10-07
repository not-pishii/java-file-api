package me.supcheg.javafile.facts;

import java.util.Locale;

/// The fact of a `protected` member of a class, held back: a `protected`
/// member is accessible in the body of a subclass alone (JLS 6.6.2), so its
/// fact is not handed out as the fact of a `public` member is. The
/// declaration of a subclass of `O` in the typed layer takes the fact out;
/// nothing else does.
///
/// A `protected` constructor is not wrapped: its `SuperCtorRefN` is taken
/// by a subclass constructor alone as it is.
///
/// @param <O> the class owning the member
/// @param <F> the fact of the member
public final class Protected<O, F extends MemberFact> {
    private final ExtendableClassToken<O> owner;
    private final F fact;

    Protected(ExtendableClassToken<O> owner, F fact) {
        if (fact.access() != Access.PROTECTED) {
            throw new IllegalArgumentException(fact + " is not a protected member: its fact tells it is "
                    + fact.access().name().toLowerCase(Locale.ROOT));
        }
        if (fact.owner().shape() != owner.shape()) {
            throw new IllegalArgumentException(fact + " is a member of " + fact.owner() + ", not of " + owner);
        }
        this.owner = owner;
        this.fact = fact;
    }

    /// The class owning the member: only a subclass of it reaches the
    /// member.
    ///
    /// @return the owner token
    public ExtendableClassToken<O> owner() {
        return owner;
    }

    /// The fact of the member, for the declaration of a subclass.
    F fact() {
        return fact;
    }

    @Override
    public String toString() {
        return "protected " + fact;
    }
}
