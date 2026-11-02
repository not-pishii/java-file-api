package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;

import java.util.List;

/// The declarations of the methods and constructors of a class that
/// declare a `throws` clause, from [MemberBuilder#throwing]: each member
/// declared here has the clause in its fact and in the rendered class.
///
/// It declares and nothing else. A member declared here is defined by the
/// bodies of the class, as any other; a field has no `throws` clause; and
/// there is no second clause to add to the first — all the exceptions of a
/// member are given to one `throwing`.
///
/// @param <Self> the brand of the class being declared
/// @param <S> the superclass of the class
public final class ThrowingDeclarations<Self, S> extends Declarations<Self, S> {
    private final Declarations<Self, S> declarations;
    private final List<ClassToken<? extends Throwable>> thrown;

    ThrowingDeclarations(Declarations<Self, S> declarations, List<ClassToken<? extends Throwable>> thrown) {
        this.declarations = declarations;
        this.thrown = List.copyOf(thrown);
    }

    @Override
    MemberBuilder<Self, S> builder() {
        return declarations.builder();
    }

    @Override
    List<ClassToken<? extends Throwable>> thrown() {
        return thrown;
    }

    @Override
    List<me.supcheg.javafile.facts.MethodTypeParameter> typeParams() {
        return declarations.typeParams();
    }
}
