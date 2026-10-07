package p;

public class ProtG<T> extends ProtBase {
    protected T item;
    protected final int fin = 1;
    protected static int count;
    protected static final Object LOCK = new Object();
    protected static final int LIMIT = 3;
    protected static final String NAME = "g";

    public ProtG() {}

    protected ProtG(T item) throws java.io.IOException {}

    protected T get() {
        return item;
    }

    protected void set(T value) throws java.io.IOException {}

    protected final void fixed() {}

    protected static void reset() {}

    protected <X> X pick(X x, T fallback) {
        return x;
    }

    protected static <X extends Number> X spick(X x) {
        return x;
    }

    public void pub() {}
}
