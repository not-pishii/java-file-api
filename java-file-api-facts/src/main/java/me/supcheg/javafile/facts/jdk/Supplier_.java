package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam0;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;
import java.util.function.Supplier;

/// Facts of `java.util.function.Supplier<T>`.
///
/// @param <T> the result type
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class Supplier_<T> {
    /// `Supplier<T>`.
    public final InterfaceToken<Supplier<T>> token;

    /// `T get()`.
    public final MethodRef0<Supplier<T>, T> get;

    /// The single abstract method, [#get].
    public final Sam0<Supplier<T>, T> sam;

    private Supplier_(RefToken<T> result) {
        this.token = Jdk.iface(Supplier.class, TokenArg.exact(result));
        this.get = UnsafeFacts.method(token, "get", result, Jdk.ABSTRACT);
        this.sam = UnsafeFacts.sam(get);
    }

    /// Instantiates the facts.
    ///
    /// @param result the result type
    /// @param <T> the result type
    /// @return the facts
    public static <T> Supplier_<T> of(RefToken<T> result) {
        return new Supplier_<>(result);
    }
}
