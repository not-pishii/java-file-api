package p;

public interface Source<T> {
    T next() throws java.io.IOException;
}
