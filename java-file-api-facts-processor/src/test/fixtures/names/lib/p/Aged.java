package p;

public class Aged {
    @Deprecated
    public int was;

    public int is;

    @Deprecated
    public static final String OLD = "old";

    public Aged() {}

    @Deprecated
    public Aged(String name) {}

    @Deprecated(forRemoval = true)
    public void gone() {}

    public void here() {}
}
