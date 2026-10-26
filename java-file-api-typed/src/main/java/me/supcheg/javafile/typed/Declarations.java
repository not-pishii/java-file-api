package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;

import java.util.List;

/// The declarations of the methods and constructors of a class being
/// declared (§6.5): what the builder of its members ([MemberBuilder])
/// declares of them itself, and what its `throwing` declares with a `throws`
/// clause ([ThrowingDeclarations]). The two differ in the clause alone.
///
/// **A declaration has no body.** It gives the fact of the member; the body
/// is given to the stage after, [MemberBodies], which the builder of the
/// members ends in. So every member of the class is declared before any body
/// is built, and a body is lowered where it is defined, against all the
/// class has.
///
/// **Overriding is by the fact.** A method the class inherits is overridden
/// by naming its fact, never by declaring a method of its name:
/// `declareOverride(Object_.toString)` declares `@Override public String
/// toString()`. A fact of the superclass `S` or of a class above it is taken
/// as it is — javac holds the generator to it, `S` being the type the class
/// was declared to extend; the method of an interface by the witness that
/// the class is of the interface, `declareOverride(up, fact)`. The witness is
/// asked for its type alone: that the compiler of the generator takes it is
/// the proof that the class is of a type that has the method, and nothing of
/// it is read. What the class inherits under the signature is held against
/// the method where it is declared ([SignatureRegistry]).
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public abstract sealed class Declarations<Self, S> extends DeclarationArities<Self, S>
        permits MemberBuilder, ThrowingDeclarations {

    Declarations() {}

    // The declarations of constructors, methods and static methods by name and types (`declareConstructor`,
    // `declareMethod`, `declareVoidMethod`, `declareStaticMethod`, `declareVoidStaticMethod`) and of the overrides
    // (`declareOverride`), of every arity, are generated into the superclass of this class (DeclarationArities, see
    // TypedCodegen). They call the hooks below.

    @Override
    <F extends Invocable> F declare(F member) {
        return builder().declare(member, thrown());
    }

    @Override
    <F extends Invocable> F overriding(F own) {
        return builder().declareOverride(own, thrown());
    }

    /// The traits of a member declared here: `traits`, with the `throws` clause.
    @Override
    MemberTraits traits(MemberTraits traits) {
        return new MemberTraits(
                List.copyOf(thrown()), traits.overridability(), traits.typeArgs(), traits.access(), traits.arity());
    }

    // ------------------------------------------------------------------
    // Members of variable arity
    // ------------------------------------------------------------------

    /// The declarations whose last parameter is of variable arity, `T... v`; the body sees it as the array `T[]`.
    ///
    /// @return the declarations of the members of variable arity
    public final VariableArityDeclarations<Self, S> variableArity() {
        return new VariableArityDeclarations<>(this);
    }
}
