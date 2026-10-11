package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;

import java.util.List;

/// The declarations of the methods and constructors of a class that
/// declare a `throws` clause, from [TypedClassBuilder#throwing]: each member
/// declared here has the clause in its fact and in the rendered class.
///
/// It declares and nothing else. A member declared here is defined by the
/// builder of the class, as any other; a field has no `throws` clause; and
/// there is no second clause to add to the first — all the exceptions of a
/// member are given to one `throwing`.
///
/// @param <Self> the brand of the class being declared
public final class ThrowingDeclarations<Self> extends Declarations<Self>
        implements ObjectConstructors<Self>, OverridingDeclarations<Self, Object> {
    private final MemberBuilder<Self> builder;
    private final List<ClassToken<? extends Throwable>> thrown;

    ThrowingDeclarations(MemberBuilder<Self> builder, List<ClassToken<? extends Throwable>> thrown) {
        this.builder = builder;
        this.thrown = List.copyOf(thrown);
    }

    @Override
    MemberBuilder<Self> builder() {
        return builder;
    }

    @Override
    List<ClassToken<? extends Throwable>> thrown() {
        return thrown;
    }
}
