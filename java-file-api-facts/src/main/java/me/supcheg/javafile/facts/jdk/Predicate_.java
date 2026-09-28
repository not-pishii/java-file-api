package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;

import java.util.Map;
import java.util.function.Predicate;

/// Facts of `java.util.function.Predicate<T>`.
///
/// @param <T> the argument type
public final class Predicate_<T> {
    /// `Predicate<T>`.
    public final InterfaceToken<Predicate<T>> token;

    /// `boolean test(T)`.
    public final MethodRef1<Predicate<T>, Boolean, T> test;

    /// The single abstract method, [#test].
    public final Sam1<Predicate<T>, Boolean, T> sam;

    private Predicate_(RefToken<T> argument) {
        this.token = Jdk.iface(Predicate.class, Map.of("T", argument), Jdk.arg(argument));
        this.test = MethodRef1.introduce(token, "test", PrimitiveToken.BOOLEAN, argument, Jdk.ABSTRACT);
        this.sam = Sam1.introduce(test);
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
