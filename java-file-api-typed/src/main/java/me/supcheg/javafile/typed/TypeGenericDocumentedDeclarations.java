package me.supcheg.javafile.typed;

import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.MethodTypeParameter;
import me.supcheg.javafile.model.Modifier;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/// The declarations of the generic methods of an interface, a record or an enum that carry a
/// documentation comment, from [TypeGenericDeclarations#documented]: each member declared here is
/// rendered with the comment.
///
/// @param <Self> the brand of the type being declared
public final class TypeGenericDocumentedDeclarations<Self> extends TypeGenericDeclarations<Self> {
    private final TypeGenericDeclarations<Self> declarations;
    private final DocComment doc;

    TypeGenericDocumentedDeclarations(TypeGenericDeclarations<Self> declarations, DocComment doc) {
        this.declarations = declarations;
        this.doc = doc;
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
        return declarations.modifiers();
    }

    @Override
    Optional<DocComment> doc() {
        return Optional.of(doc);
    }

    @Override
    List<MethodTypeParameter> typeParams() {
        return declarations.typeParams();
    }
}
