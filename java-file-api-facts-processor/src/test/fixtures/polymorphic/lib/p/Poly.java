package p;

public class Poly {
    public int t;
    public static final int T = 1;

    public <T> Poly(T t) {}

    public Poly() {}

    public <T> T id(T t) {
        return t;
    }

    @SafeVarargs
    public static <T> java.util.List<T> listOf(T... items) {
        return null;
    }

    public static <T extends Comparable<? super T>> T max(java.util.Collection<? extends T> items) {
        return null;
    }

    public <A, B extends A> A widen(B b) {
        return b;
    }

    public <X extends Exception> void run(Class<X> type) throws X, java.io.IOException {}

    public <T extends Number & Comparable<T>> T clamp(T value) {
        return value;
    }

    public <TOKEN, Gen> void odd(TOKEN a, Gen b) {}

    public <T> void none() {}
}
