package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/// Facts of `java.util.stream.Stream<E>`, instantiated with the token of `E`.
///
/// A generic method is a factory taking a token per method type parameter —
/// an explicit witness (§3.4) — and its calls render the witnesses as
/// explicit type arguments, `stream.<R>map(f)`:
///
/// ```java
/// MethodRef1<Stream<String>, Stream<Integer>, Function<? super String, ? extends Integer>> map =
///         new Stream_<>(String_.TOKEN).map(Integer_.TOKEN);
/// ```
///
/// @param <E> the element type
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class Stream_<E> {
    /// `Stream<E>`.
    public final InterfaceToken<Stream<E>> token;

    /// `List<E> toList()`.
    public final MethodRef0<Stream<E>, List<E>> toList;

    /// `Stream<E> filter(Predicate<? super E>)`.
    public final MethodRef1<Stream<E>, Stream<E>, Predicate<? super E>> filter;

    private final RefToken<E> element;

    /// Instantiates the facts for an element type.
    ///
    /// @param element the element type
    public Stream_(RefToken<E> element) {
        this.element = element;
        this.token = Jdk.iface(Stream.class, TokenArg.exact(element));
        this.toList = UnsafeFacts.method(token, "toList", listToken(), MemberTraits.OVERRIDABLE);
        InterfaceToken<Predicate<? super E>> predicate = Jdk.iface(Predicate.class, TokenArg.superBound(element));
        this.filter = UnsafeFacts.method(token, "filter", token, predicate, MemberTraits.ABSTRACT);
    }

    /// `<R> Stream<R> map(Function<? super E, ? extends R>)`, with `R` as the
    /// explicit type argument.
    ///
    /// @param result the witness for `R`
    /// @param <R> the mapped element type
    /// @return the method fact
    public <R> MethodRef1<Stream<E>, Stream<R>, Function<? super E, ? extends R>> map(RefToken<R> result) {
        InterfaceToken<Function<? super E, ? extends R>> function =
                Jdk.iface(Function.class, TokenArg.superBound(element), TokenArg.extendsBound(result));
        return UnsafeFacts.method(
                token, "map", new Stream_<>(result).token, function, MemberTraits.ABSTRACT.withTypeArgs(result));
    }

    private InterfaceToken<List<E>> listToken() {
        return Jdk.iface(List.class, TokenArg.exact(element));
    }
}
