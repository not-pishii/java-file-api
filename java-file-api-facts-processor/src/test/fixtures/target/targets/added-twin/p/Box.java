package p;

public class Box<T> {
    public String put(T t) {
        return "put(T) " + t;
    }

    public String put(String s) {
        return "put(String) " + s;
    }
}
