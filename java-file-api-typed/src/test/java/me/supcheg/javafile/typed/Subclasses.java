package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.TypeToken;
import me.supcheg.javafile.typed.TypedSubclassBuilder.CtorBody0;
import me.supcheg.javafile.typed.TypedSubclassBuilder.CtorBody1;

/// The header of a subclass ended in one step, for the tests that are not about its
/// constructor: `began(sh, body)` is `sh.body(sh.declareConstructor(), body)`.
final class Subclasses {
    private Subclasses() {}

    static <Self, S> TypedSubclassBuilder<Self, S> began(SubclassOverriding<Self, S> sh, CtorBody0<Self, S> first) {
        return sh.body(sh.declareConstructor(), first);
    }

    static <Self, S, A1> TypedSubclassBuilder<Self, S> began(
            SubclassOverriding<Self, S> sh, TypeToken<A1> p1, CtorBody1<Self, S, A1> first) {
        return sh.body(sh.declareConstructor(p1), first);
    }
}
