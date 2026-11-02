package me.supcheg.javafile.typed;

import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.MethodTypeParameter;
import me.supcheg.javafile.model.Modifier;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/// The declarations of the generic methods of an interface, a record or an enum that declare a `throws`
/// clause, from [TypeGenericDeclarations#throwing]: each member declared here has the clause in its fact
/// and in the rendered type.
///
/// It declares and nothing else. A member declared here is defined by the bodies of the type, as any
/// other; and there is no second clause to add to the first — all the exceptions of a member are given to
/// one `throwing`.
///
/// @param <Self> the brand of the type being declared
public final class TypeGenericThrowingDeclarations<Self> extends TypeGenericDeclarations<Self> {
    private final TypeGenericDeclarations<Self> declarations;
    private final List<ClassToken<? extends Throwable>> thrown;

    TypeGenericThrowingDeclarations(
            TypeGenericDeclarations<Self> declarations, List<ClassToken<? extends Throwable>> thrown) {
        this.declarations = declarations;
        this.thrown = List.copyOf(thrown);
    }

    @Override
    TypeMemberDeclarations<Self> declarations() {
        return declarations.declarations();
    }

    @Override
    List<ClassToken<? extends Throwable>> thrown() {
        return thrown;
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
        return declarations.doc();
    }

    @Override
    List<MethodTypeParameter> typeParams() {
        return declarations.typeParams();
    }
}
