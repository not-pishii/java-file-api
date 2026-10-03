package p;

public class T2<T> {
    public final String made;

    public T2() {
        made = "none";
    }

    public T2(T t) {
        made = "t";
    }

    public T2(String s) {
        made = "string";
    }

    public String m(T t) {
        return "t";
    }

    public String m(String s) {
        return "string";
    }

    public String one(T t) {
        return "one";
    }
}
