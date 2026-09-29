package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.TokenArg;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;
import java.util.List;
import java.util.stream.Stream;

/// Facts of `java.util.List<E>`, instantiated with the token of `E` (§3.3):
///
/// ```java
/// var strings = new List_<>(String_.TOKEN);
/// MethodRef1<List<String>, String, Prim.Int> get = strings.get;
/// ```
///
/// @param <E> the element type
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class List_<E> {
    /// `List<E>`.
    public final InterfaceToken<List<E>> token;

    /// `E get(int)`.
    public final MethodRef1<List<E>, E, Prim.Int> get;

    /// `boolean add(E)`.
    public final MethodRef1<List<E>, Prim.Bool, E> add;

    /// `int size()`.
    public final MethodRef0<List<E>, Prim.Int> size;

    /// `boolean isEmpty()`.
    public final MethodRef0<List<E>, Prim.Bool> isEmpty;

    /// `Stream<E> stream()`.
    public final MethodRef0<List<E>, Stream<E>> stream;

    /// `static <E> List<E> of()`, with `E` as the explicit type argument.
    public final StaticMethodRef0<List<E>> of;

    /// `static <E> List<E> of(E)`, with `E` as the explicit type argument.
    public final StaticMethodRef1<List<E>, E> of_E;

    /// Instantiates the facts for an element type.
    ///
    /// @param element the element type
    public List_(RefToken<E> element) {
        this.token = Jdk.iface(List.class, TokenArg.exact(element));
        this.get = UnsafeFacts.method(token, "get", element, PrimitiveToken.INT, MemberTraits.ABSTRACT);
        this.add = UnsafeFacts.method(token, "add", PrimitiveToken.BOOLEAN, element, MemberTraits.ABSTRACT);
        this.size = UnsafeFacts.method(token, "size", PrimitiveToken.INT, MemberTraits.ABSTRACT);
        this.isEmpty = UnsafeFacts.method(token, "isEmpty", PrimitiveToken.BOOLEAN, MemberTraits.ABSTRACT);
        this.stream = UnsafeFacts.method(token, "stream", new Stream_<>(element).token, MemberTraits.OVERRIDABLE);
        this.of = UnsafeFacts.staticMethod(token, "of", token, MemberTraits.FINAL.withTypeArgs(element));
        this.of_E = UnsafeFacts.staticMethod(token, "of", token, element, MemberTraits.FINAL.withTypeArgs(element));
    }
}
