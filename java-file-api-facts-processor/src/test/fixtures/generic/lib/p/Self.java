package p;

public abstract class Self<S extends Self<S>> {
    public abstract S me();

    public int compare(S other) {
        return 0;
    }
}
