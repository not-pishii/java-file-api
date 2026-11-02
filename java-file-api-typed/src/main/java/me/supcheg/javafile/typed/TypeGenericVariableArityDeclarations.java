package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Arity;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.typed.TypeDeclarationSupport.Member.Kind;

/// The declarations of the generic methods of an interface, a record or an enum whose last parameter is
/// of variable arity, `T... v` ([TypeGenericDeclarations#variableArity]): the body sees the parameter as
/// the array `T[]`, and the member is `@SafeVarargs` where its elements are not reifiable, as a member of
/// a class declared by [Declarations#variableArity()] is.
///
/// Made by [TypeGenericDeclarations#variableArity], so `throwing(...)`, `documented(...)`,
/// `annotated(...)` and `modified(...)` compose with it.
///
/// @param <Self> the brand of the type being declared
public final class TypeGenericVariableArityDeclarations<Self> extends TypeGenericVariableArityDeclarationArities<Self> {
    private final TypeGenericDeclarations<Self> declarations;

    TypeGenericVariableArityDeclarations(TypeGenericDeclarations<Self> declarations) {
        this.declarations = declarations;
    }

    // The declarations, `declareMethod`, `declareVoidMethod`, `declareStaticMethod`, `declareVoidStaticMethod`
    // and the abstract, `default` and `private` forms, of every arity, are generated into the superclass of
    // this class (TypeGenericVariableArityDeclarationArities, see TypedCodegen). They call the hooks below.

    @Override
    TypeMemberDeclarations<Self> declarations() {
        return declarations.declarations();
    }

    @Override
    <F extends Invocable> F declared(F member, Kind kind) {
        return declarations.declared(member, kind);
    }

    /// The traits of a member of variable arity: those of the stage, with the arity its last parameter has.
    @Override
    MemberTraits traits(MemberTraits traits, ArrayToken<?, ?> last) {
        return declarations.traits(
                traits.with(Tokens.isReifiable(last.component()) ? Arity.VARIABLE : Arity.SAFE_VARARGS));
    }
}
