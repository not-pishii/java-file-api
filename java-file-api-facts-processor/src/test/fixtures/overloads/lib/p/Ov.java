package p;

public class Ov {
    public <T> String m(T t) {
        return "generic";
    }

    public String m(String s) {
        return "string";
    }

    public static <T> String s(T t) {
        return "generic";
    }

    public static String s(Integer i) {
        return "integer";
    }

    public <T extends Comparable<T>> String c(T t) {
        return "generic";
    }

    public String c(String s) {
        return "string";
    }

    public <T> String solo(T t) {
        return "solo";
    }
}
