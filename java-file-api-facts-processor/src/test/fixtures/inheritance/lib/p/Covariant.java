package p;

public class Covariant extends Near<String> {
    public Covariant self() {
        return this;
    }

    public String api() {
        return "api";
    }

    public String pub() {
        return "pub";
    }
}
