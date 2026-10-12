package me.supcheg.javafile.typed;

import java.util.function.Consumer;

/// The specification of a class as a lambda gives it, for the tests whose
/// subject is not the brand: a specification is a generic method, which no
/// lambda implements, and a builder of a wildcard brand declares facts its
/// own bodies do not take — each use of it is a capture of its own. Here the
/// brand is given up for `Object`, so what a lambda declares it can define.
final class Unbranded {
    private Unbranded() {}

    /// The specification that declares the members `members` declares.
    ///
    /// @param members declares the members of the class, given its builder
    /// @return the specification
    static TypedJavaFile.TypedClassSpec spec(Consumer<TypedClassBuilder<Object>> members) {
        return new TypedJavaFile.TypedClassSpec() {
            @Override
            @SuppressWarnings("unchecked")
            public <Self> void build(TypedClassBuilder<Self> cb) {
                members.accept((TypedClassBuilder<Object>) cb);
            }
        };
    }
}
