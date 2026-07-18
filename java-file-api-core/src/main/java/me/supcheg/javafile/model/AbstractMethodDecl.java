package me.supcheg.javafile.model;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/// An abstract method declaration: no body, usable in both class and
/// interface bodies.
///
/// Type parameters, parameters, annotations, modifiers, and thrown types are
/// defensively copied into unmodifiable collections.
///
/// @param name the method name
/// @param returnType the declared return type, or empty for `void`
/// @param typeParams the method's type parameters, in declaration order
/// @param params the method's parameters, in declaration order
/// @param annotations the annotations declared on the method
/// @param modifiers the modifiers on the method declaration
/// @param throwsTypes the checked exception types declared in the method's
///                     `throws` clause
public record AbstractMethodDecl(
        String name,
        Optional<TypeRef> returnType,
        List<TypeParam> typeParams,
        List<Param> params,
        List<AnnotationUse> annotations,
        Set<Modifier> modifiers,
        List<ClassOrInterfaceTypeRef> throwsTypes)
        implements InterfaceMember, ClassMember, EnumMember {
    public AbstractMethodDecl {
        name = Identifiers.requireValid(name);
        annotations = List.copyOf(annotations);
        typeParams = List.copyOf(typeParams);
        params = List.copyOf(params);
        modifiers = ModifierValidation.requireValidMember(
                Set.copyOf(modifiers),
                EnumSet.of(Modifier.PUBLIC, Modifier.PROTECTED, Modifier.ABSTRACT),
                "abstract method");
        throwsTypes = List.copyOf(throwsTypes);
    }
}
