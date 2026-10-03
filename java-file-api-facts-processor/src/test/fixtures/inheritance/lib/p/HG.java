package p;

import java.util.List;

class HG<T> {
    public T value;

    public T get() {
        return value;
    }

    public void put(T value) {
        this.value = value;
    }

    public List<T> all() {
        return List.of();
    }
}
