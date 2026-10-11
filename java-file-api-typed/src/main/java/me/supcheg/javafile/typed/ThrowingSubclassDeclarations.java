package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;

import java.util.List;

/// The declarations of the methods and constructors of a class that extends
/// `S` that declare a `throws` clause, from [TypedSubclassBuilder#throwing];
/// see [ThrowingDeclarations].
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class ThrowingSubclassDeclarations<Self, S> extends Declarations<Self>
        implements SubclassConstructors<Self, S>, OverridingDeclarations<Self, S> {
    private final TypedSubclassBuilder<Self, S> builder;
    private final List<ClassToken<? extends Throwable>> thrown;

    ThrowingSubclassDeclarations(TypedSubclassBuilder<Self, S> builder, List<ClassToken<? extends Throwable>> thrown) {
        this.builder = builder;
        this.thrown = List.copyOf(thrown);
    }

    @Override
    MemberBuilder<Self> builder() {
        return builder;
    }

    /// The builder of the class, with its superclass.
    TypedSubclassBuilder<Self, S> subclass() {
        return builder;
    }

    @Override
    List<ClassToken<? extends Throwable>> thrown() {
        return thrown;
    }
}
