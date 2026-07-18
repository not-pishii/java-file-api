package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.type.TypeRef;

import java.util.Optional;

/// An `instanceof` test, optionally binding the matched value to a pattern name.
///
/// @param target the tested expression
/// @param type the tested type
/// @param bindingName the name bound to the matched value, or empty for a plain test
public record InstanceOfExpr(Expr target, TypeRef type, Optional<String> bindingName) implements Expr {
    public InstanceOfExpr {
        bindingName = bindingName.map(Identifiers::requireValid);
    }
}
