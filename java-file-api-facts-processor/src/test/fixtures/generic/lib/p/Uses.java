package p;

public class Uses {
    public java.util.Map<String, ? extends Number> wild;

    public Box<String> strings() {
        return null;
    }

    public void take(Sorted<Integer> sorted) {}

    public Box raw() {
        return null;
    }

    public java.util.List<? super Integer>[] lists() {
        return null;
    }

    public Pair<int[], String[]> arrays() {
        return null;
    }

    public java.util.List<?> any() {
        return null;
    }
}
