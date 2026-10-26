package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;

/// The declarations of the methods and constructors whose last parameter is of variable arity, `T... v` (JLS
/// 8.4.1): the body sees the parameter as the array `T[]`, and calls of the member give the arguments of the parameter
/// one by one ([Expressions#varargs(ArrayToken, Expr[])]) or the array.
///
/// What javac says of the declaration is told by the type of the elements, so the declaration does not have to:
/// where it is not reifiable, javac warns of possible heap pollution (`-Xlint:unchecked`), and the member is declared
/// `@SafeVarargs` — which javac accepts of a `static` method, of a constructor and of a `final` method alone, and
/// which the methods here are —, and the array of its parameter is held to what the annotation vouches for: it is
/// read by `at`, measured by `length`, stored into by `assignAt` and looped over by `forEach`, and leaves the member
/// nowhere. Where the elements are reifiable javac would warn of the annotation as redundant (`-Xlint:varargs`), and
/// there is none.
///
/// Made by [Declarations#variableArity()], so `throwing(...)` composes with it.
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class VariableArityDeclarations<Self, S> extends VariableArityDeclarationArities<Self, S> {
    private final Declarations<Self, S> declarations;

    VariableArityDeclarations(Declarations<Self, S> declarations) {
        this.declarations = declarations;
    }

    // The declarations, `declareConstructor`, `declareMethod`, `declareVoidMethod`, `declareStaticMethod` and
    // `declareVoidStaticMethod` of every arity, are generated into the superclass of this class
    // (VariableArityDeclarationArities, see TypedCodegen). They call the hooks below.

    @Override
    <F extends Invocable> F declare(F member) {
        return declarations.declare(member);
    }

    @Override
    FinalClassToken<Self> self() {
        return declarations.builder().self();
    }

    /// The traits of a member of variable arity: `traits`, with the arity its last parameter has.
    @Override
    MemberTraits traits(MemberTraits traits, ArrayToken<?, ?> last) {
        return declarations.traits(
                traits.with(Tokens.isReifiable(last.component()) ? Arity.VARIABLE : Arity.SAFE_VARARGS));
    }
}
