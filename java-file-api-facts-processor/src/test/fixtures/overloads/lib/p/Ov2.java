package p;

public class Ov2 {
    public static <T> java.util.Optional<T> wrap(T t) {
        return java.util.Optional.of(t);
    }

    public static StringBuilder wrap(String s) {
        return new StringBuilder(s);
    }
}
