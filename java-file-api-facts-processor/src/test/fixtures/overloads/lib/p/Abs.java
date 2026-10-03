package p;

public abstract class Abs<T> {
    public abstract String m(T t);

    public String m(String s) {
        return "string";
    }
}
