package me.supcheg.javafile.model;

import me.supcheg.javafile.Identifiers;
import me.supcheg.javafile.annotation.AnnotationUse;
import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;
import me.supcheg.javafile.type.TypeParam;
import me.supcheg.javafile.type.TypeRef;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/// A `native` method, e.g. `public native int read(byte[] buffer);`, in a class.
/// It has no body, as an abstract method has none, and is rendered with a
/// semicolon.
///
/// @param name the method name
/// @param returnType the declared return type, or empty for `void`
/// @param typeParams the method's type parameters, in declaration order
/// @param params the method's parameters, in declaration order
/// @param annotations the annotations declared on the method
/// @param modifiers the modifiers on the method declaration; `native` is always one of them
/// @param throwsTypes the checked exception types declared in the method's
///                     `throws` clause
/// @param doc the documentation comment, if any
public record NativeMethodDecl(
        String name,
        Optional<TypeRef> returnType,
        List<TypeParam> typeParams,
        List<Param> params,
        List<AnnotationUse> annotations,
        Set<Modifier> modifiers,
        List<ClassOrInterfaceTypeRef> throwsTypes,
        Optional<DocComment> doc)
        implements ClassMember, RecordMember, EnumMember {
    public NativeMethodDecl {
        name = Identifiers.requireValid(name);
        annotations = List.copyOf(annotations);
        typeParams = List.copyOf(typeParams);
        params = ModifierValidation.requireVarargsOnlyLast(List.copyOf(params));
        modifiers = ModifierValidation.requireValidMember(
                Set.copyOf(modifiers),
                EnumSet.of(
                        Modifier.PUBLIC,
                        Modifier.PROTECTED,
                        Modifier.PRIVATE,
                        Modifier.STATIC,
                        Modifier.FINAL,
                        Modifier.SYNCHRONIZED,
                        Modifier.NATIVE),
                "native method");
        if (!modifiers.contains(Modifier.NATIVE)) {
            throw new IllegalArgumentException("a native method is declared native, got " + modifiers);
        }
        throwsTypes = List.copyOf(throwsTypes);
    }
}
