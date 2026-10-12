package me.supcheg.javafile.typed;

import java.util.function.Function;

/// The specification of a class as a lambda gives it, for the tests whose
/// subject is not the brand: a specification is a generic method, which no
/// lambda implements, and a builder of a wildcard brand declares facts its
/// own bodies do not take — each use of it is a capture of its own. Here the
/// brand is given up for `Object`, so what a lambda declares it can define.
final class Unbranded {
    private Unbranded() {}

    /// The specification that declares and defines the members `members` does.
    ///
    /// @param members declares the members of the class, given its builder, and returns their bodies
    /// @return the specification
    static TypedJavaFile.TypedClassSpec spec(Function<TypedClassBuilder<Object>, TypedClassBodies<Object>> members) {
        return new TypedJavaFile.TypedClassSpec() {
            @Override
            @SuppressWarnings("unchecked")
            public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                return (TypedClassBodies<Self>) members.apply((TypedClassBuilder<Object>) cb);
            }
        };
    }
}
