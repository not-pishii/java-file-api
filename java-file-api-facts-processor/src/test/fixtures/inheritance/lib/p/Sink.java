package p;

public final class Sink {
    private Sink() {}

    public static String take(Object o) {
        return "object";
    }

    public static String take(String s) {
        return "string";
    }
}
