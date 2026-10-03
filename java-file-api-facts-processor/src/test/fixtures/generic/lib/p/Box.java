package p;

public class Box<T> {
    public T value;
    public final T initial = null;
    public static int count;
    public static final String NAME = "box";

    public Box(T value) {}

    public Box() {}

    public T get() {
        return value;
    }

    public void set(T value) {}

    public Box<T> self() {
        return this;
    }

    public Box<Box<T>> nested() {
        return null;
    }

    public java.util.List<T> asList() {
        return null;
    }

    public T[] toArray(T[] into) {
        return into;
    }

    public void addAll(java.util.Collection<? extends T> from) {}

    public void drainTo(java.util.Collection<? super T> to) {}

    public boolean sameAs(Box<?> other) {
        return false;
    }

    public java.util.List raw(java.util.Map raw) {
        return null;
    }

    public Box rawSelf() {
        return null;
    }

    public static Box<String> ofString() {
        return null;
    }

    public static int size(Box<?> box) {
        return 0;
    }

    public int[] ints() {
        return null;
    }
}
