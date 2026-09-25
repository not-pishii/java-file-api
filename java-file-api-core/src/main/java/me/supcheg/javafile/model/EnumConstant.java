package me.supcheg.javafile.model;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.Expr;

import java.util.List;

/// An enum constant, e.g. `RED`, `PLUS("+")`, or `PLUS("+") { ... }`.
///
/// A non-empty [#body] becomes the constant's class body; it can hold fields,
/// methods, and nested types.
///
/// @param name the constant's name, a valid Java identifier
/// @param annotations the annotations declared on the constant
/// @param args the arguments passed to the enum's constructor for this
///             constant
/// @param body the constant-specific class body members, empty if the
///             constant has no body
public record EnumConstant(
        String name, List<AnnotationUse> annotations, List<Expr> args, List<EnumConstantMember> body) {
    public EnumConstant {
        name = Identifiers.requireValid(name);
        annotations = List.copyOf(annotations);
        args = List.copyOf(args);
        body = List.copyOf(body);
    }
}
