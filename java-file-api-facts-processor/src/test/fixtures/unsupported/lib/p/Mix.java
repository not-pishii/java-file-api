package p;

public class Mix {
    public String plain() {
        return null;
    }

    public java.util.List<String> names() {
        return null;
    }

    public java.util.List raw() {
        return null;
    }

    public <T> T id(T t) {
        return null;
    }

    public void wild(java.util.Map<?, ? extends Number> m) {}

    public Mix(java.util.Set<String> s) {}

    public <T> Mix(T t, int x) {}

    public java.util.List<String>[] array() {
        return null;
    }

    public java.util.function.Function<String, String> field;

    public Dol$lar dollar() {
        return null;
    }

    public Marker marker() {
        return null;
    }

    public java.util.List<Marker> markers() {
        return null;
    }

    public <T extends Marker> void marked(T t) {}
}
