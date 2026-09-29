package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.Sam1;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;
import java.util.function.Function;

/// Facts of `java.util.function.Function<T, R>`.
///
/// [#sam] types a lambda (§6.4):
/// `lambda(Function_.of(String_.TOKEN, Integer_.TOKEN).sam, s -> call(s, String_.length))`.
///
/// @param <T> the argument type
/// @param <R> the result type
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class Function_<T, R> {
    /// `Function<T, R>`.
    public final InterfaceToken<Function<T, R>> token;

    /// `R apply(T)`.
    public final MethodRef1<Function<T, R>, R, T> apply;

    /// The single abstract method, [#apply].
    public final Sam1<Function<T, R>, R, T> sam;

    private Function_(RefToken<T> argument, RefToken<R> result) {
        this.token = Jdk.iface(Function.class, TokenArg.exact(argument), TokenArg.exact(result));
        this.apply = UnsafeFacts.method(token, "apply", result, argument, Jdk.ABSTRACT);
        this.sam = UnsafeFacts.sam(apply);
    }

    /// Instantiates the facts.
    ///
    /// @param argument the argument type
    /// @param result the result type
    /// @param <T> the argument type
    /// @param <R> the result type
    /// @return the facts
    public static <T, R> Function_<T, R> of(RefToken<T> argument, RefToken<R> result) {
        return new Function_<>(argument, result);
    }
}
