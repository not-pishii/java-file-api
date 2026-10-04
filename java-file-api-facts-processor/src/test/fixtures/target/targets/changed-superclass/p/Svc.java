package p;

public class Svc implements Marker {
    public static final int LIMIT = 3;

    public String m(String s) {
        return "m(String) " + s;
    }

    public String only(Object o) {
        return "only(Object) " + o;
    }

    public Dep dep() {
        return new Dep();
    }

    public void close() {}
}
