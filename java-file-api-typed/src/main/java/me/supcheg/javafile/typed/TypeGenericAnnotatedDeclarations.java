package me.supcheg.javafile.typed;

import me.supcheg.javafile.doc.DocComment;
import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTypeParameter;
import me.supcheg.javafile.model.Modifier;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// The declarations of the generic methods of an interface, a record or an enum that declare annotations,
/// from [TypeGenericDeclarations#annotated] and
/// [TypeGenericDeclarations#annotatedParameter]: each member declared here is rendered with its
/// annotations, and its fact carries them.
///
/// @param <Self> the brand of the type being declared
public final class TypeGenericAnnotatedDeclarations<Self> extends TypeGenericDeclarations<Self> {
    private final TypeGenericDeclarations<Self> declarations;
    private final List<AnnotationRef<?>> annotations;
    private final Map<Integer, List<AnnotationRef<?>>> parameterAnnotations;

    TypeGenericAnnotatedDeclarations(
            TypeGenericDeclarations<Self> declarations,
            List<AnnotationRef<?>> annotations,
            Map<Integer, List<AnnotationRef<?>>> parameterAnnotations) {
        this.declarations = declarations;
        this.annotations = List.copyOf(annotations);
        this.parameterAnnotations = Map.copyOf(parameterAnnotations);
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
        return Stream.concat(declarations.annotations().stream(), annotations.stream())
                .toList();
    }

    @Override
    List<List<AnnotationRef<?>>> parameterAnnotations() {
        List<List<AnnotationRef<?>>> before = declarations.parameterAnnotations();
        int size = Math.max(
                before.size(),
                parameterAnnotations.keySet().stream()
                        .mapToInt(index -> index + 1)
                        .max()
                        .orElse(0));
        return IntStream.range(0, size)
                .mapToObj(index -> Stream.concat(
                                index < before.size() ? before.get(index).stream() : Stream.empty(),
                                parameterAnnotations.getOrDefault(index, List.of()).stream())
                        .toList())
                .toList();
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

    @Override
    MemberTraits traits(MemberTraits traits) {
        MemberTraits applied = super.traits(traits);
        return annotations.isEmpty()
                ? applied
                : applied.annotated(annotations.stream()
                        .map(AnnotationRef::toUse)
                        .toArray(me.supcheg.javafile.annotation.AnnotationUse[]::new));
    }
}
