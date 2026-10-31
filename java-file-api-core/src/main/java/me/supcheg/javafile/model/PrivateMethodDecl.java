package me.supcheg.javafile.model;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.code.CodeBody;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;

import java.util.List;
import java.util.Optional;

/// A `private` method in an interface, e.g. `private int twice(int x) { ... }`
/// or `private static int twice(int x) { ... }` (JLS 9.4).
///
/// @param name the method name
/// @param returnType the declared return type, or empty for `void`
/// @param annotations the annotations declared on the method
/// @param typeParams the method's type parameters, in declaration order
/// @param params the method's parameters, in declaration order
/// @param body the method's body
/// @param isStatic whether the method is `private static`
/// @param throwsTypes the checked exception types declared in the method's
///                     `throws` clause
/// @param doc the documentation comment, if any
public record PrivateMethodDecl(
        String name,
        Optional<TypeRef> returnType,
        List<AnnotationUse> annotations,
        List<TypeParam> typeParams,
        List<Param> params,
        CodeBody body,
        boolean isStatic,
        List<ClassOrInterfaceTypeRef> throwsTypes,
        Optional<DocComment> doc)
        implements InterfaceMember {
    public PrivateMethodDecl {
        name = Identifiers.requireValid(name);
        annotations = List.copyOf(annotations);
        typeParams = List.copyOf(typeParams);
        params = ModifierValidation.requireVarargsOnlyLast(List.copyOf(params));
        throwsTypes = List.copyOf(throwsTypes);
    }
}
