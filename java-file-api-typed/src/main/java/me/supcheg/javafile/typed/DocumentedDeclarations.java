package me.supcheg.javafile.typed;

import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.model.Modifier;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/// The declarations of the methods and constructors of a class that carry
/// a documentation comment, from [Declarations#documented(DocComment)]:
/// each member declared here is rendered with the comment.
///
/// A field is documented where it is declared, by the overload of
/// [MemberBuilder#declareField(String, me.supcheg.javafile.facts.TypeToken,
/// DocComment, AnnotationRef[])].
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class DocumentedDeclarations<Self, S> extends Declarations<Self, S> {
    private final Declarations<Self, S> declarations;
    private final DocComment doc;

    DocumentedDeclarations(Declarations<Self, S> declarations, DocComment doc) {
        this.declarations = declarations;
        this.doc = doc;
    }

    @Override
    MemberBuilder<Self, S> builder() {
        return declarations.builder();
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
    List<me.supcheg.javafile.facts.MethodTypeParameter> typeParams() {
        return declarations.typeParams();
    }

    @Override
    Optional<DocComment> doc() {
        return Optional.of(doc);
    }
}
