package p;

public class PBox<T> {
    public T t;

    public <R> PBox<R> map(java.util.function.Function<? super T, ? extends R> f) {
        return null;
    }

    public <T> T shadow(T other) {
        return other;
    }

    public static <T> PBox<T> of(T value) {
        return null;
    }

    public <U extends T> void put(U u) {}

    public static <E extends Enum<E>> PBox<E> ofEnum(Class<E> type) {
        return null;
    }
}
