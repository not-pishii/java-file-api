package p;

interface Root {
    int ROOT = 1;

    default String root() {
        return "root";
    }
}
