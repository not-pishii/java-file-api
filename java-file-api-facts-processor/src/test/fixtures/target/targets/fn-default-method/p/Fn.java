package p;

public interface Fn {
    default String apply(String s) {
        return s;
    }
}
