package p;

public class Svc extends Base implements Marker {
    public static final int LIMIT = 3;

    public String only(Object o) {
        return "only(Object) " + o;
    }

    public Dep dep() {
        return new Dep();
    }

    public void close() {}
}
