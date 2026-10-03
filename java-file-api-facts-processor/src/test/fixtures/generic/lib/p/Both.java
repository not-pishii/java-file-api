package p;

public abstract class Both<T extends Number & Comparable<T>> {
    public Both() {}

    public abstract T pick();
}
