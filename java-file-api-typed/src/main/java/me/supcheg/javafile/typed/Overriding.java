package me.supcheg.javafile.typed;

import me.supcheg.javafile.facts.ClassToken;
import me.supcheg.javafile.facts.Implemented;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.Overriding0;
import me.supcheg.javafile.facts.Overriding1;
import me.supcheg.javafile.facts.Overriding2;
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
public abstract sealed class Overriding<Self> permits Header, ThrowingOverriding, SubclassOverriding {
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
