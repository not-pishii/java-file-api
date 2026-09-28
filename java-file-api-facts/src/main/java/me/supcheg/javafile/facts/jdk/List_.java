package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.StaticMethodRef1;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/// Facts of `java.util.List<E>`, instantiated with the token of `E` (§3.3):
///
/// ```java
/// var strings = new List_<>(String_.TOKEN);
/// MethodRef1<List<String>, String, Integer> get = strings.get;
/// ```
///
/// @param <E> the element type
public final class List_<E> {
    /// `List<E>`.
    public final InterfaceToken<List<E>> token;

    /// `E get(int)`.
    public final MethodRef1<List<E>, E, Integer> get;

    /// `boolean add(E)`.
    public final MethodRef1<List<E>, Boolean, E> add;

    /// `int size()`.
    public final MethodRef0<List<E>, Integer> size;

    /// `boolean isEmpty()`.
    public final MethodRef0<List<E>, Boolean> isEmpty;

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
        this.token = Jdk.iface(List.class, Map.of("E", element), Jdk.arg(element));
        this.get = MethodRef1.introduce(token, "get", element, PrimitiveToken.INT, Jdk.ABSTRACT);
        this.add = MethodRef1.introduce(token, "add", PrimitiveToken.BOOLEAN, element, Jdk.ABSTRACT);
        this.size = MethodRef0.introduce(token, "size", PrimitiveToken.INT, Jdk.ABSTRACT);
        this.isEmpty = MethodRef0.introduce(token, "isEmpty", PrimitiveToken.BOOLEAN, Jdk.ABSTRACT);
        this.stream = MethodRef0.introduce(token, "stream", new Stream_<>(element).token, Jdk.OVERRIDABLE);
        this.of = StaticMethodRef0.introduce(token, "of", token, Jdk.FINAL.withTypeArgs(element));
        this.of_E = StaticMethodRef1.introduce(token, "of", token, element, Jdk.FINAL.withTypeArgs(element));
    }
}
