package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.DeclaredToken;
import me.supcheg.javafile.facts.Implemented;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.Overriding0;
import me.supcheg.javafile.facts.Overriding1;
import me.supcheg.javafile.facts.Overriding2;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.SelfOverriding0;
import me.supcheg.javafile.facts.SelfOverriding1;
import me.supcheg.javafile.facts.SelfOverriding2;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidMethodRef2;
import me.supcheg.javafile.facts.VoidOverriding0;
import me.supcheg.javafile.facts.VoidOverriding1;
import me.supcheg.javafile.facts.VoidOverriding2;

import java.util.List;

/// The intentions of a class to override methods of its supertypes, said
/// in its header: `overriding(fact)` says the class declares a method that
/// overrides the method of the fact, and gives the `OverridingN` — an `ImplementationN` — a step
/// of the proof that the class has a supertype takes for the method —
/// `cmp.implement(h.self()).compareTo_T(compareTo)`.
///
/// The intention declares the method: `public`, of the name, the parameters
/// and the result of the overridden one as that is a member of the type of
/// its fact, rendered with `@Override`. Its fact is `method()` of the
/// implementation, by which the builder of the members defines it — a
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
/// @param <Self> the brand of the class being declared
public abstract sealed class Overriding<Self> permits Header, ThrowingOverriding, SubclassOverriding, ExtendingHeader {
    Overriding() {}

    abstract ClassDeclaration<Self> declaration();

    /// The exception classes of the `throws` clause of the methods declared here.
    abstract List<ClassToken<? extends Throwable>> thrown();

    /// Says the class overrides a method.
    ///
    /// @param method the fact of the overridden method
    /// @param <O> the type that has the method
    /// @param <R> the result type
    /// @return how the class has the method: by one of its own, `method()` of the result
    /// @throws IllegalArgumentException if the method is `final` or generic, the class overrides a
    ///     method of that signature already, or cannot override what it inherits under it
    /// @throws IllegalStateException if the header of the class has ended
    public <O, R> Overriding0<Self, R> overriding(MethodRef0<O, R> method) {
        return UnsafeFacts.overriding0(new Implemented.ByOverride(
                method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /// Says the class overrides a method with one parameter.
    ///
    /// @param method the fact of the overridden method
    /// @param <O> the type that has the method
    /// @param <R> the result type
    /// @param <A1> the parameter type
    /// @return how the class has the method: by one of its own, `method()` of the result
    /// @throws IllegalArgumentException if the method is `final` or generic, the class overrides a
    ///     method of that signature already, or cannot override what it inherits under it
    /// @throws IllegalStateException if the header of the class has ended
    public <O, R, A1> Overriding1<Self, R, A1> overriding(MethodRef1<O, R, A1> method) {
        return UnsafeFacts.overriding1(new Implemented.ByOverride(
                method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /// Says the class overrides a method with two parameters.
    ///
    /// @param method the fact of the overridden method
    /// @param <O> the type that has the method
    /// @param <R> the result type
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return how the class has the method: by one of its own, `method()` of the result
    /// @throws IllegalArgumentException if the method is `final` or generic, the class overrides a
    ///     method of that signature already, or cannot override what it inherits under it
    /// @throws IllegalStateException if the header of the class has ended
    public <O, R, A1, A2> Overriding2<Self, R, A1, A2> overriding(MethodRef2<O, R, A1, A2> method) {
        return UnsafeFacts.overriding2(new Implemented.ByOverride(
                method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /// Says the class overrides a method with a method of a narrower result (JLS 8.4.5): `result`
    /// is the result type of the method the class declares, a subtype of that of the overridden
    /// one — which the compiler of the generator holds the token to, and the typed layer proves
    /// again of every method the class inherits under the signature ([Subtyping]), where a
    /// supertype that has one is named. A step of a proof takes the implementation as it takes one
    /// of the result of its method.
    ///
    /// ```java
    /// var get = h.overriding(Source_.get, String_.TOKEN);   // Overriding0<Self, String>
    /// ```
    ///
    /// A primitive and a `void` result have no narrower one, and no token to say it by. The
    /// class itself is the result by a witness, not by its token — the brand `Self` is a subtype
    /// of `Object` alone to the compiler of the generator: [#overriding(MethodRef0, Upcast)].
    ///
    /// @param method the fact of the overridden method
    /// @param result the result type of the method of the class
    /// @param <O> the type that has the method
    /// @param <R> the result type of the overridden method
    /// @param <R2> the result type of the method of the class
    /// @return how the class has the method: by one of its own, `method()` of the result
    /// @throws IllegalArgumentException if the method is `final` or generic, the class overrides a
    ///     method of that signature already, or cannot override what it inherits under it — `result`
    ///     is not proven a subtype of the result of a method among them
    /// @throws IllegalStateException if the header of the class has ended
    public <O, R, R2 extends R> Overriding0<Self, R2> overriding(MethodRef0<O, R> method, RefToken<R2> result) {
        return UnsafeFacts.overriding0(new Implemented.ByOverride(
                method,
                declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /// Says the class overrides a method with one parameter with a method of a narrower result,
    /// see [#overriding(MethodRef0, RefToken)].
    ///
    /// @param method the fact of the overridden method
    /// @param result the result type of the method of the class
    /// @param <O> the type that has the method
    /// @param <R> the result type of the overridden method
    /// @param <R2> the result type of the method of the class
    /// @param <A1> the parameter type
    /// @return how the class has the method: by one of its own, `method()` of the result
    /// @throws IllegalArgumentException as [#overriding(MethodRef0, RefToken)]
    /// @throws IllegalStateException if the header of the class has ended
    public <O, R, R2 extends R, A1> Overriding1<Self, R2, A1> overriding(
            MethodRef1<O, R, A1> method, RefToken<R2> result) {
        return UnsafeFacts.overriding1(new Implemented.ByOverride(
                method,
                declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

    /// Says the class overrides a method with two parameters with a method of a narrower result,
    /// see [#overriding(MethodRef0, RefToken)].
    ///
    /// @param method the fact of the overridden method
    /// @param result the result type of the method of the class
    /// @param <O> the type that has the method
    /// @param <R> the result type of the overridden method
    /// @param <R2> the result type of the method of the class
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return how the class has the method: by one of its own, `method()` of the result
    /// @throws IllegalArgumentException as [#overriding(MethodRef0, RefToken)]
    /// @throws IllegalStateException if the header of the class has ended
    public <O, R, R2 extends R, A1, A2> Overriding2<Self, R2, A1, A2> overriding(
            MethodRef2<O, R, A1, A2> method, RefToken<R2> result) {
        return UnsafeFacts.overriding2(new Implemented.ByOverride(
                method,
                declaration().intend(method, Overrides.own(declaration().self(), method, result, thrown()), thrown())));
    }

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

    /// Says the class overrides a method with a method whose result is the class itself (JLS
    /// 8.4.5): `public Chain next()` for `Node next()`. The brand `Self` is a subtype of nothing
    /// to the compiler of the generator, so that the class is of the result of the method is
    /// said by a witness — of the result type itself or of a subtype of it, which the compiler
    /// holds the witness to; the typed layer proves it again of every method the class inherits
    /// under the signature ([Subtyping]), where a supertype that has one is named.
    ///
    /// The implementation is typed by the supertype of the witness, by which a step of a proof
    /// takes it (`? extends R`); its `method()` is the fact of the method of the class, of the
    /// result `Self`. The witness of a supertype the class has yet to name is [#becoming].
    ///
    /// @param method the fact of the overridden method
    /// @param result the witness that the class is of the result type of the method
    /// @param <O> the type that has the method
    /// @param <R> the result type of the overridden method
    /// @param <X> the supertype of the class the witness is of
    /// @return how the class has the method: by one of its own, `method()` of the result
    /// @throws IllegalArgumentException if the method is `final` or generic, the class overrides a
    ///     method of that signature already, or cannot override what it inherits under it
    /// @throws IllegalStateException if the header of the class has ended
    public <O, R, X extends R> SelfOverriding0<Self, X> overriding(MethodRef0<O, R> method, Upcast<Self, X> result) {
        return UnsafeFacts.selfOverriding0(new Implemented.ByOverride(
                method,
                declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /// Says the class overrides a method with one parameter with a method whose result is the
    /// class itself, see [#overriding(MethodRef0, Upcast)].
    ///
    /// @param method the fact of the overridden method
    /// @param result the witness that the class is of the result type of the method
    /// @param <O> the type that has the method
    /// @param <R> the result type of the overridden method
    /// @param <X> the supertype of the class the witness is of
    /// @param <A1> the parameter type
    /// @return how the class has the method: by one of its own, `method()` of the result
    /// @throws IllegalArgumentException as [#overriding(MethodRef0, Upcast)]
    /// @throws IllegalStateException if the header of the class has ended
    public <O, R, X extends R, A1> SelfOverriding1<Self, X, A1> overriding(
            MethodRef1<O, R, A1> method, Upcast<Self, X> result) {
        return UnsafeFacts.selfOverriding1(new Implemented.ByOverride(
                method,
                declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /// Says the class overrides a method with two parameters with a method whose result is the
    /// class itself, see [#overriding(MethodRef0, Upcast)].
    ///
    /// @param method the fact of the overridden method
    /// @param result the witness that the class is of the result type of the method
    /// @param <O> the type that has the method
    /// @param <R> the result type of the overridden method
    /// @param <X> the supertype of the class the witness is of
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return how the class has the method: by one of its own, `method()` of the result
    /// @throws IllegalArgumentException as [#overriding(MethodRef0, Upcast)]
    /// @throws IllegalStateException if the header of the class has ended
    public <O, R, X extends R, A1, A2> SelfOverriding2<Self, X, A1, A2> overriding(
            MethodRef2<O, R, A1, A2> method, Upcast<Self, X> result) {
        return UnsafeFacts.selfOverriding2(new Implemented.ByOverride(
                method,
                declaration().intend(method, Overrides.ownOfSelf(declaration().self(), method, thrown()), thrown())));
    }

    /// Says the class overrides a `void` method.
    ///
    /// @param method the fact of the overridden method
    /// @param <O> the type that has the method
    /// @return how the class has the method: by one of its own, `method()` of the result
    /// @throws IllegalArgumentException if the method is `final` or generic, the class overrides a
    ///     method of that signature already, or cannot override what it inherits under it
    /// @throws IllegalStateException if the header of the class has ended
    public <O> VoidOverriding0<Self> overriding(VoidMethodRef0<O> method) {
        return UnsafeFacts.voidOverriding0(new Implemented.ByOverride(
                method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /// Says the class overrides a `void` method with one parameter.
    ///
    /// @param method the fact of the overridden method
    /// @param <O> the type that has the method
    /// @param <A1> the parameter type
    /// @return how the class has the method: by one of its own, `method()` of the result
    /// @throws IllegalArgumentException if the method is `final` or generic, the class overrides a
    ///     method of that signature already, or cannot override what it inherits under it
    /// @throws IllegalStateException if the header of the class has ended
    public <O, A1> VoidOverriding1<Self, A1> overriding(VoidMethodRef1<O, A1> method) {
        return UnsafeFacts.voidOverriding1(new Implemented.ByOverride(
                method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }

    /// Says the class overrides a `void` method with two parameters.
    ///
    /// @param method the fact of the overridden method
    /// @param <O> the type that has the method
    /// @param <A1> the first parameter type
    /// @param <A2> the second parameter type
    /// @return how the class has the method: by one of its own, `method()` of the result
    /// @throws IllegalArgumentException if the method is `final` or generic, the class overrides a
    ///     method of that signature already, or cannot override what it inherits under it
    /// @throws IllegalStateException if the header of the class has ended
    public <O, A1, A2> VoidOverriding2<Self, A1, A2> overriding(VoidMethodRef2<O, A1, A2> method) {
        return UnsafeFacts.voidOverriding2(new Implemented.ByOverride(
                method, declaration().intend(method, Overrides.own(declaration().self(), method, thrown()), thrown())));
    }
}
