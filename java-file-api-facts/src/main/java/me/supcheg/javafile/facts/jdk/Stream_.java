package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.type.Types;

import java.util.List;
import java.util.Map;
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
        this.token = Jdk.iface(Stream.class, Map.of("T", element), Jdk.arg(element));
        this.toList = MethodRef0.introduce(token, "toList", listToken(), Jdk.OVERRIDABLE);
        InterfaceToken<Predicate<? super E>> predicate =
                Jdk.iface(Predicate.class, Map.of(), Types.superBound(element.typeRef()));
        this.filter = MethodRef1.introduce(token, "filter", token, predicate, Jdk.ABSTRACT);
    }

    /// `<R> Stream<R> map(Function<? super E, ? extends R>)`, with `R` as the
    /// explicit type argument.
    ///
    /// @param result the witness for `R`
    /// @param <R> the mapped element type
    /// @return the method fact
    public <R> MethodRef1<Stream<E>, Stream<R>, Function<? super E, ? extends R>> map(RefToken<R> result) {
        InterfaceToken<Function<? super E, ? extends R>> function = Jdk.iface(
                Function.class, Map.of(), Types.superBound(element.typeRef()), Types.extendsBound(result.typeRef()));
        return MethodRef1.introduce(
                token, "map", new Stream_<>(result).token, function, Jdk.ABSTRACT.withTypeArgs(result));
    }

    private InterfaceToken<List<E>> listToken() {
        return Jdk.iface(List.class, Map.of("E", element), Jdk.arg(element));
    }
}
