package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.DeclaredToken;

/// The intentions of a class to override methods of its supertypes, said
/// in its header: `overriding(fact)` says the class declares a method that
/// overrides the method of the fact, and gives the `OverridingN` — an `ImplementationN` — a step
/// of the proof that the class has a supertype takes for the method —
/// `cmp.implement(h.self()).compareTo_T(compareTo)`.
///
/// The intention declares the method: `public`, of the name, the parameters
/// and the result of the overridden one as that is a member of the type of
/// its fact, rendered with `@Override`. Its fact is `method()` of the
/// implementation, by which the bodies of the members define it — a
/// method the class says it overrides and never defines is rejected when the
/// declaration completes — and any body calls it.
///
/// One method of the class answers every supertype that has it: the one
/// implementation is given to the step of each, and a second intention of
/// the same signature is rejected. An intention is checked against what the
/// class inherits where a supertype that has the method is named, and no
/// later than where the header ends: a method no supertype of the class has
/// is not overridden.
///
/// **The forms** are those of [OverridingArities], the superclass of this class, for a method of any number
/// of parameters:
///
/// - `overriding(method)` overrides a method (`void` or not) as it is;
/// - `overriding(method, token)` overrides it with a method of a narrower result (JLS 8.4.5): `token` is the
///   result type of the method the class declares, a subtype of that of the overridden one — which the compiler
///   of the generator holds the token to, and the typed layer proves again of every method the class inherits
///   under the signature ([Subtyping]), where a supertype that has one is named. A step of a proof takes the
///   implementation as it takes one of the result of its method:
///   `var get = h.overriding(Source_.get, String_.TOKEN);   // Overriding0<Self, String>`.
///   A primitive and a `void` result have no narrower one, and no token to say it by;
/// - `overriding(method, witness)` overrides it with a method whose result is the class itself (JLS 8.4.5):
///   `public Chain next()` for `Node next()`. The brand `Self` is a subtype of nothing to the compiler of the
///   generator, so that the class is of the result of the method is said by a witness — of the result type itself
///   or of a subtype of it, which the compiler holds the witness to; the typed layer proves it again of every
///   method the class inherits under the signature, where a supertype that has one is named. The implementation
///   is typed by the supertype of the witness, by which a step of a proof takes it (`? extends R`); its `method()`
///   is the fact of the method of the class, of the result `Self`. The witness of a supertype the class has yet to
///   name is [#becoming].
///
/// @param <Self> the brand of the class being declared
public abstract sealed class Overriding<Self> extends OverridingArities<Self>
        permits Header, ThrowingOverriding, SubclassHeaderArities, ExtendingHeader {
    Overriding() {}

    /// The witness that the class is of a supertype it has not named yet and is about to: what an
    /// intention to override a method of that very supertype with the class itself for its result
    /// ([#overriding(MethodRef0, Upcast)]) needs before the proof the supertype is named by —
    /// the proof takes the intention, and the witness of [ObjectHeader#implements_] comes of the
    /// proof.
    ///
    /// ```java
    /// var asNode = h.becoming(Node_.TOKEN);
    /// var next = h.overriding(Node_.next, asNode);            // public Chain next()
    /// var oh = h.extendsObject();
    /// oh.implements_(Node_.implement(h.self()).next(next));
    /// ```
    ///
    /// The witness is a promise the header is held to where it ends: a class that never names
    /// the type — as its superclass or among its superinterfaces, of these very type arguments —
    /// has no members, so no body is built by a witness that does not hold.
    ///
    /// @param supertype the type, with its type arguments
    /// @param <X> the type
    /// @return the witness that the class is of the type
    /// @throws IllegalStateException if the header of the class has ended
    public <X> Upcast<Self, X> becoming(DeclaredToken<X> supertype) {
        return declaration().promise(supertype);
    }
}
