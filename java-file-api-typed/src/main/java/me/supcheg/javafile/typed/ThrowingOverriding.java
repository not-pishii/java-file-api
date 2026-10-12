package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;

import java.util.List;

/// The intentions of a class to override methods with a method that
/// declares a `throws` clause, from [Header#throwing] and
/// [SubclassHeader#throwing]: an override declares
/// no exception unless it is said here, whatever the overridden method
/// declares, and may declare only what every method it overrides allows (JLS
/// 8.4.8.3).
///
/// @param <Self> the brand of the class being declared
public final class ThrowingOverriding<Self> extends Overriding<Self> {
    private final ClassDeclaration<Self> declaration;
    private final List<ClassToken<? extends Throwable>> thrown;

    ThrowingOverriding(ClassDeclaration<Self> declaration, List<ClassToken<? extends Throwable>> thrown) {
        this.declaration = declaration;
        this.thrown = List.copyOf(thrown);
    }

    @Override
    ClassDeclaration<Self> declaration() {
        return declaration;
    }

    @Override
    List<ClassToken<? extends Throwable>> thrown() {
        return thrown;
    }
}
