package p;

public class Box<T extends Comparable<T>> {
    public T value;

    public Box(T value) {}

    @SafeVarargs
    public final T[] all(T... more) {
        return more;
    }

    public <R> R as(R other) {
        return other;
    }
}
