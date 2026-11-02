package me.supcheg.javafile.typed;

import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.MethodTypeParameter;
import me.supcheg.javafile.model.Modifier;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/// The declarations of the generic methods of an interface, a record or an enum that are rendered with
/// modifiers of their own, from [TypeGenericDeclarations#modified]: `synchronized`, or `native`, beside
/// the modifiers the form of the member fixes.
///
/// A `synchronized` method has a body, which the bodies define; a `native` method has none, and is there
/// whole when it is declared.
///
/// @param <Self> the brand of the type being declared
public final class TypeGenericModifiedDeclarations<Self> extends TypeGenericDeclarations<Self> {
    private final TypeGenericDeclarations<Self> declarations;
    private final Set<Modifier> modifiers;

    TypeGenericModifiedDeclarations(TypeGenericDeclarations<Self> declarations, Set<Modifier> modifiers) {
        this.declarations = declarations;
        this.modifiers = Set.copyOf(modifiers);
    }

    @Override
    TypeMemberDeclarations<Self> declarations() {
        return declarations.declarations();
    }

    @Override
    List<ClassToken<? extends Throwable>> thrown() {
        return declarations.thrown();
    }

    @Override
    List<AnnotationRef<?>> annotations() {
        return declarations.annotations();
    }

    @Override
    List<List<AnnotationRef<?>>> parameterAnnotations() {
        return declarations.parameterAnnotations();
    }

    @Override
    Set<Modifier> modifiers() {
        return modifiers;
    }

    @Override
    Optional<DocComment> doc() {
        return declarations.doc();
    }

    @Override
    List<MethodTypeParameter> typeParams() {
        return declarations.typeParams();
    }
}
