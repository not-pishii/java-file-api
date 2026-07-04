package me.supcheg.javafile.model;

import me.supcheg.javafile.code.Expr;

import java.util.List;

/// A single constant of an [EnumDecl].
///
/// The argument and body lists are defensively copied into unmodifiable
/// lists. A non-empty [#body] renders as a constant-specific class body
/// (an anonymous subclass of the enum), scoped with the same [ClassMember]
/// kinds allowed in a regular class.
///
/// @param name the constant's name, a valid Java identifier
/// @param args the arguments passed to the enum's constructor for this
///             constant
/// @param body the constant-specific class body members, empty if the
///             constant has no body
public record EnumConstant(String name, List<Expr> args, List<ClassMember> body) {
    public EnumConstant {
        args = List.copyOf(args);
        body = List.copyOf(body);
    }
}
