package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.AnnotationRef;
import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.model.Modifier;

import java.util.List;
import java.util.Set;

/// The declarations of the methods of a class that are rendered with modifiers
/// of their own, from [Declarations#modified(Modifier[])]: `synchronized`, or
/// `native`, beside the modifiers the form of the member fixes.
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class ModifiedDeclarations<Self, S> extends Declarations<Self, S> {
    private final Declarations<Self, S> declarations;
    private final Set<Modifier> modifiers;

    ModifiedDeclarations(Declarations<Self, S> declarations, List<Modifier> modifiers) {
        this.declarations = declarations;
        this.modifiers = Set.copyOf(modifiers);
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
        return modifiers;
    }
}
