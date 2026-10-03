package p;

public class Io {
    public Io() throws java.io.IOException {}

    public Io(int x) {}

    public void read() throws java.io.IOException {}

    public void multi() throws java.io.IOException, InterruptedException, IllegalStateException {}

    public int unchecked() throws IllegalArgumentException {
        return 0;
    }

    public static void util() throws Exception {}

    public void custom() throws Failure {}

    public final void locked() throws Failure {}

    public <X extends Throwable> void generic() throws X {}

    public void hidden() throws Secret {}

    public Io(String s) throws Secret {}
}
