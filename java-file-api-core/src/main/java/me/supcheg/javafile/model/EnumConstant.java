package me.supcheg.javafile.model;

import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.Expr;

import java.util.List;

/// A single constant of an [EnumDecl].
///
/// The annotation, argument, and body lists are defensively copied into
/// unmodifiable lists. A non-empty [#body] renders as a constant-specific
/// class body (an anonymous subclass of the enum), scoped with the narrower
/// [EnumConstantMember] kinds an anonymous class body actually allows,
/// which excludes constructors and abstract methods.
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
        annotations = List.copyOf(annotations);
        args = List.copyOf(args);
        body = List.copyOf(body);
    }
}
