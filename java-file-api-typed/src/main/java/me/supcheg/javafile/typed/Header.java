package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.FinalClassToken;

import java.util.List;

/// The header of a class being declared: what is said of the class before
/// its members — its superclass, its superinterfaces, and the methods of
/// theirs it overrides ([Overriding]).
///
/// The header is a sequence of stages, each giving the next:
///
/// ```
/// ClassHeader<Self>  --extendsObject()-->  ObjectHeader<Self>  --body()-->  TypedClassBuilder<Self>
///                                            implements_(proof)
///                    --extends_(proof)-->  SubclassHeader<Self, S>  --body(constructor, (ctor, ...) -> ...)-->
///                                            implements_(proof), inherited(fact)      TypedSubclassBuilder<Self, S>
///                                            declareConstructor(...)
/// ```
///
/// A class has a superclass before it names a superinterface, and all its
/// supertypes before a member: the stage that offers a step is the one the
/// step before gave. A stage is used once — the types do not say that a
/// value is not used twice, so a stage the declaration has left rejects what
/// is asked of it.
///
/// @param <Self> the brand of the class being declared
public abstract sealed class Header<Self> extends Overriding<Self> permits ClassHeader, ObjectHeader {
    private final ClassDeclaration<Self> declaration;

    Header(ClassDeclaration<Self> declaration) {
        this.declaration = declaration;
    }

    /// The fact of the class being declared: a [FinalClassToken], usable as a type — of a type
    /// argument of a supertype, `new Comparable_<>(h.self())`, among others. Its
    /// [FinalClassToken#methods()] is known once the declaration completes.
    ///
    /// @return the self token
    public final FinalClassToken<Self> self() {
        return declaration.self();
    }

    /// The intentions to override methods with a method that declares `exceptions` in its
    /// `throws` clause.
    ///
    /// @param exceptions the exception types of the `throws` clause, in order
    /// @return the intentions that declare `exceptions`
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final ThrowingOverriding<Self> throwing(ClassToken<? extends Throwable>... exceptions) {
        return new ThrowingOverriding<>(declaration, List.of(exceptions));
    }

    @Override
    final ClassDeclaration<Self> declaration() {
        return declaration;
    }

    @Override
    final List<ClassToken<? extends Throwable>> thrown() {
        return List.of();
    }
}
