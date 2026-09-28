package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;

import java.util.Map;

/// Facts of `java.lang.Comparable<T>`.
///
/// @param <T> the compared type
public final class Comparable_<T> {
    /// `Comparable<T>`.
    public final InterfaceToken<Comparable<T>> token;

    /// `int compareTo(T)`.
    public final MethodRef1<Comparable<T>, Integer, T> compareTo;

    private Comparable_(RefToken<T> compared) {
        this.token = Jdk.iface(Comparable.class, Map.of("T", compared), Jdk.arg(compared));
        this.compareTo = MethodRef1.introduce(token, "compareTo", PrimitiveToken.INT, compared, Jdk.ABSTRACT);
    }

    /// Instantiates the facts.
    ///
    /// @param compared the compared type
    /// @param <T> the compared type
    /// @return the facts
    public static <T> Comparable_<T> of(RefToken<T> compared) {
        return new Comparable_<>(compared);
    }
}
