package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.type.TypeRef;

import java.util.Optional;

/// A flat type pattern, e.g. `Type` or `Type bindingName`.
///
/// @param type the matched type
/// @param bindingName the name bound to the matched value, or empty for an unnamed match
public record TypePattern(TypeRef type, Optional<String> bindingName) implements Pattern {
    public TypePattern {
        bindingName = bindingName.map(Identifiers::requireValid);
    }
}
