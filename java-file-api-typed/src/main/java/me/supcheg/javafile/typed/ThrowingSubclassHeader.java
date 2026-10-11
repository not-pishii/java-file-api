package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.ExtendableClassToken;

import java.util.List;

/// The stage of the header of a class that extends `S` with a `throws`
/// clause, from [SubclassHeader#throwing]: the methods it says the class
/// overrides declare the clause ([ThrowingOverriding] tells what an override
/// may declare), and so does the first constructor it ends the header with.
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class ThrowingSubclassHeader<Self, S> extends SubclassOverriding<Self, S> {
    private final List<ClassToken<? extends Throwable>> thrown;

    ThrowingSubclassHeader(
            ClassDeclaration<Self> declaration,
            ExtendableClassToken<S> superclass,
            List<ClassToken<? extends Throwable>> thrown) {
        super(declaration, superclass);
        this.thrown = List.copyOf(thrown);
    }

    @Override
    List<ClassToken<? extends Throwable>> thrown() {
        return thrown;
    }
}
