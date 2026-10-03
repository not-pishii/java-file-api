package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodTableTemplate.Param;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef1;
import me.supcheg.javafile.facts.VoidSam1;

import javax.annotation.processing.Generated;
import java.util.function.Consumer;

/// Facts of `java.util.function.Consumer<T>`.
///
/// @param <T> the argument type
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class Consumer_<T> {
    /// `Consumer<T>`.
    public final InterfaceToken<Consumer<T>> token;

    /// `void accept(T)`.
    public final VoidMethodRef1<Consumer<T>, T> accept;

    /// The single abstract method, [#accept].
    public final VoidSam1<Consumer<T>, T> sam;

    private Consumer_(RefToken<T> argument) {
        this.token = Jdk.iface(Consumer.class, TokenArg.exact(argument));
        this.accept = UnsafeFacts.voidMethod(
                token, "accept", UnsafeFacts.param(argument, Param.var(0)), MemberTraits.ABSTRACT);
        this.sam = UnsafeFacts.voidSam(accept);
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
