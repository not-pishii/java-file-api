package p;

import java.util.function.Function;

/// A class whose type parameter has a bound of every shape: an array, a primitive in one, and each wildcard.
public final class Shapes<T extends Comparable<? super T> & Function<? extends T[], int[]> & Iterable<?>> {
    public Shapes() {}
}
