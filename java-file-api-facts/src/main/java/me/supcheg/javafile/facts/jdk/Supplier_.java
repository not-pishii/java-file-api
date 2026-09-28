package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam0;

import java.util.Map;
import java.util.function.Supplier;

/// Facts of `java.util.function.Supplier<T>`.
///
/// @param <T> the result type
public final class Supplier_<T> {
    /// `Supplier<T>`.
    public final InterfaceToken<Supplier<T>> token;

    /// `T get()`.
    public final MethodRef0<Supplier<T>, T> get;

    /// The single abstract method, [#get].
    public final Sam0<Supplier<T>, T> sam;

    private Supplier_(RefToken<T> result) {
        this.token = Jdk.iface(Supplier.class, Map.of("T", result), Jdk.arg(result));
        this.get = MethodRef0.introduce(token, "get", result, Jdk.ABSTRACT);
        this.sam = Sam0.introduce(get);
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
