package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.model.Modifier;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// The declarations of the methods and constructors of a class that declare
/// annotations, from [Declarations#annotated(AnnotationRef[])] and
/// [Declarations#annotatedParameter(int, AnnotationRef[])]: each member
/// declared here is rendered with its annotations, and its fact carries them.
///
/// A field is annotated where it is declared, by the overload of
/// [MemberBuilder#declareField(String, me.supcheg.javafile.facts.TypeToken,
/// AnnotationRef[])].
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class AnnotatedDeclarations<Self, S> extends Declarations<Self, S> {
    private final Declarations<Self, S> declarations;
    private final List<AnnotationRef<?>> annotations;
    private final Map<Integer, List<AnnotationRef<?>>> parameterAnnotations;

    AnnotatedDeclarations(
            Declarations<Self, S> declarations,
            List<AnnotationRef<?>> annotations,
            Map<Integer, List<AnnotationRef<?>>> parameterAnnotations) {
        this.declarations = declarations;
        this.annotations = List.copyOf(annotations);
        this.parameterAnnotations = Map.copyOf(parameterAnnotations);
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
    java.util.Optional<me.supcheg.javafile.doc.DocComment> doc() {
        return declarations.doc();
    }

    @Override
    MemberTraits traits(MemberTraits traits) {
        MemberTraits applied = declarations.traits(traits);
        return annotations().isEmpty()
                ? applied
                : applied.annotated(annotations().stream()
                        .map(AnnotationRef::toUse)
                        .toArray(me.supcheg.javafile.annotation.AnnotationUse[]::new));
    }
}
