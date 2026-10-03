package p;

public record Rec(int x, String label) {
    public Rec(int x) {
        this(x, "l");
    }

    public static Rec of() {
        return null;
    }
}
