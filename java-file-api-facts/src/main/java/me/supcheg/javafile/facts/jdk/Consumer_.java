package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidSam1;

import java.util.Map;
import java.util.function.Consumer;

/// Facts of `java.util.function.Consumer<T>`.
///
/// @param <T> the argument type
public final class Consumer_<T> {
    /// `Consumer<T>`.
    public final InterfaceToken<Consumer<T>> token;

    /// `void accept(T)`.
    public final VoidMethodRef1<Consumer<T>, T> accept;

    /// The single abstract method, [#accept].
    public final VoidSam1<Consumer<T>, T> sam;

    private Consumer_(RefToken<T> argument) {
        this.token = Jdk.iface(Consumer.class, Map.of("T", argument), Jdk.arg(argument));
        this.accept = VoidMethodRef1.introduce(token, "accept", argument, Jdk.ABSTRACT);
        this.sam = VoidSam1.introduce(accept);
    }

    /// Instantiates the facts.
    ///
    /// @param argument the argument type
    /// @param <T> the argument type
    /// @return the facts
    public static <T> Consumer_<T> of(RefToken<T> argument) {
        return new Consumer_<>(argument);
    }
}
