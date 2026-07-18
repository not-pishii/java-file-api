package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.type.TypeRef;

import java.util.Optional;

/// A switch case label matching a type pattern, with an optional guard.
///
/// @param type the matched type
/// @param bindingName the name bound to the matched value
/// @param guard an additional `when` condition, or empty if absent
public record TypePatternLabel(TypeRef type, String bindingName, Optional<Expr> guard) implements CaseLabel {
    public TypePatternLabel {
        bindingName = Identifiers.requireValid(bindingName);
    }
}
