package p;

public abstract class Abs {
    public Abs() {}

    public Abs(String s, int i) {}

    protected Abs(long l) {}

    public abstract void run();

    public void done() {}

    public static Abs make() {
        return null;
    }
}
