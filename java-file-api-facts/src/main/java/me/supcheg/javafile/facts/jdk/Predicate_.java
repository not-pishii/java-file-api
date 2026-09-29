package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;
import java.util.function.Predicate;

/// Facts of `java.util.function.Predicate<T>`.
///
/// @param <T> the argument type
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class Predicate_<T> {
    /// `Predicate<T>`.
    public final InterfaceToken<Predicate<T>> token;

    /// `boolean test(T)`.
    public final MethodRef1<Predicate<T>, Prim.Bool, T> test;

    /// The single abstract method, [#test].
    public final Sam1<Predicate<T>, Prim.Bool, T> sam;

    private Predicate_(RefToken<T> argument) {
        this.token = Jdk.iface(Predicate.class, TokenArg.exact(argument));
        this.test = UnsafeFacts.method(token, "test", PrimitiveToken.BOOLEAN, argument, MemberTraits.ABSTRACT);
        this.sam = UnsafeFacts.sam(test);
    }

    /// Instantiates the facts.
    ///
    /// @param argument the argument type
    /// @param <T> the argument type
    /// @return the facts
    public static <T> Predicate_<T> of(RefToken<T> argument) {
        return new Predicate_<>(argument);
    }
}
