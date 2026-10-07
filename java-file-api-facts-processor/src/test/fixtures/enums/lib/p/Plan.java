package p;

public final class Plan {
    private Plan() {}

    public static Mode mode(boolean fast) {
        return fast ? Mode.FAST : Mode.SLOW;
    }
}
