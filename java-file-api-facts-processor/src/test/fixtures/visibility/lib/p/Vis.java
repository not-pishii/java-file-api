package p;

public class Vis {
    public int pub;
    protected int prot;
    int pkg;
    private int priv;
    public Hidden hf;
    public java.util.List<Hidden> hl;

    public Vis() {}

    protected Vis(int x) {}

    Vis(String s) {}

    private Vis(long l) {}

    public Vis(Hidden h) {}

    public void pubM() {}

    protected void protM() {}

    void pkgM() {}

    private void privM() {}

    public Hidden hidden() {
        return null;
    }

    public void takes(Hidden h) {}

    public Hidden[] arr() {
        return null;
    }

    public void nested(Outer.PkgNested n) {}

    public Outer.Pub ok() {
        return null;
    }

    public static void sPub() {}
}
