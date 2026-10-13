package p;

public interface Overloaded {
    default <T> void run(T value, int times) {}

    default void run(int times) {}

    void run(long times);
}
