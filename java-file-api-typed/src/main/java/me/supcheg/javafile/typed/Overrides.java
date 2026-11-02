package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Invocable;
import me.supcheg.javafile.facts.MemberTraits;

import java.util.List;

/// The facts of the methods a class declares to override methods of its
/// supertypes: each of the name, the parameters and the result of the
/// method it overrides, as that one is a member of the supertype, `public`
/// and — the class being final — never overridden itself.
final class Overrides extends OverrideArities {
    private Overrides() {}

    /// The traits of the method that overrides `method`: of the arity of `method`, whose last parameter
    /// the override takes as `method` does (JLS 8.4.1, 8.4.8.3).
    static MemberTraits traits(Invocable method, List<ClassToken<? extends Throwable>> thrown) {
        return new MemberTraits(
                List.copyOf(thrown),
                MemberTraits.FINAL.overridability(),
                method.traits().typeArgs(),
                MemberTraits.FINAL.access(),
                method.traits().arity(),
                MemberTraits.FINAL.annotations(),
                MemberTraits.FINAL.parameterAnnotations(),
                method.traits().typeParams());
    }
}
