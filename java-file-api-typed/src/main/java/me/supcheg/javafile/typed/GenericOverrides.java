package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.Heritage;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MethodTypeParameter;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.TypeVarToken;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/// The type parameters of an override of a generic method of a supertype (JLS 8.4.8.3): the ones the
/// fact of the method the class declares names, which the declaration renders, and whether they are the
/// ones of the method it overrides — the names may differ, the bounds may not.
///
/// The fact of the method it overrides tells its type parameters by position ([Heritage.Variable]) and
/// says its signature in terms of those positions ([Heritage.Type.OfMember]); the fact of the override
/// names them by the type variables it was instantiated with
/// ([me.supcheg.javafile.facts.MemberTraits#typeArgs()], `Generic_.id_K(k)`), whose names the
/// declaration writes.
final class GenericOverrides {
    private GenericOverrides() {}

    /// The type parameters the override declares: the type variables its fact is instantiated with, with
    /// the bounds of the method it overrides.
    ///
    /// @param own the fact of the method the class declares
    /// @param generic the method it overrides, as the class inherits it
    /// @return the type parameters, in declaration order
    /// @throws ClassCastException if a type argument of the fact is no type variable, which a call refuses
    ///     ([#refusal(Invocable, Heritage.Generic)]) before the declaration is made
    static List<MethodTypeParameter> typeParams(Invocable own, Heritage.Generic generic) {
        List<Heritage.Variable> variables = generic.told().typeParams();
        List<RefToken<?>> witnesses = own.traits().typeArgs();
        return IntStream.range(0, variables.size())
                .mapToObj(index -> new MethodTypeParameter(
                        (TypeVarToken<?>) witnesses.get(index),
                        variables
                                .get(index)
                                .boundsAsWritten(other -> witnesses.get(other).typeRef())))
                .toList();
    }

    /// Why the method `own` cannot override the generic method `generic`, if it cannot: its fact names a
    /// type variable per type parameter of the method it overrides, the fact of its witness. A witness
    /// of another bound is refused where the step is taken, or by javac where the method is declared:
    /// the step and the declaration write the bounds of the method they implement.
    ///
    /// @param own the fact of the method the class declares
    /// @param generic the method it overrides, as the class inherits it
    /// @return why it cannot, or empty if it can
    static Optional<String> refusal(Invocable own, Heritage.Generic generic) {
        List<RefToken<?>> witnesses = own.traits().typeArgs();
        int variables = generic.told().typeParams().size();
        if (witnesses.size() != variables) {
            return Optional.of("the method names " + witnesses.size() + " type parameters, and the method it"
                    + " overrides has " + variables + ": an override of a generic method declares a type"
                    + " parameter per type parameter of the method it overrides (JLS 8.4.8.3)");
        }
        return witnesses.stream()
                .filter(witness -> !(witness instanceof TypeVarToken<?>))
                .findFirst()
                .map(witness -> "the method is generic, and the fact of the override is instantiated with " + witness
                        + ", which is no type variable: an override of a generic method declares the type"
                        + " parameters of the method it overrides (JLS 8.4.8.3)");
    }
}
