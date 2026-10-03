package p;

public class Pub extends Near<String> {
    public String redeclared() {
        return "pub";
    }

    public String api() {
        return "api";
    }

    public String pub() {
        return "pub";
    }
}
