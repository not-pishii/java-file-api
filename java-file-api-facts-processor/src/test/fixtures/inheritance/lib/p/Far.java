package p;

abstract class Far<T> {
    public static final String FAR = "far";
    public static final String HID = "far";
    public static int counter = 5;
    public T item;

    public T get() {
        return item;
    }

    public void set(T value) {
        item = value;
    }

    public Far<T> self() {
        return this;
    }

    public String overridden() {
        return "far";
    }

    public String redeclared() {
        return "far";
    }

    public static String sfar() {
        return "sfar";
    }

    protected void prot() {}

    void pack() {}
}
