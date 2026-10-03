package p;

public interface Iface {
    int LIMIT = 10;
    String NAME = "n";

    void run();

    default int size() {
        return 0;
    }

    static Iface empty() {
        return null;
    }
}
