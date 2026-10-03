package p;

public interface Spread<T> {
    @SuppressWarnings("unchecked")
    void accept(T... ts);
}
