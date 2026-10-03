package p;

class HNest {
    public static class In {}

    public In make() {
        return new In();
    }

    public String plain() {
        return "plain";
    }
}
