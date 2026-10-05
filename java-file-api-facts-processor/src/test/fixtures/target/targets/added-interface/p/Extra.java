package p;

public interface Extra {
    default String only(String s) {
        return "only(String) " + s;
    }
}
