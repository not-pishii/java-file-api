package p;

public class Greeter {
    public static final String DEFAULT = "say \"hi\"\n\t\\ é\u0001";
    public static final int INT = -2147483648;
    public static final long LONG = 9007199254740993L;
    public static final short SHORT = -3;
    public static final byte BYTE = 127;
    public static final char CHAR = '\'';
    public static final boolean BOOL = true;
    public static final float FLOAT = 1.1f;
    public static final double DOUBLE = 0.1;
    public static final double NAN = 0.0 / 0.0;
    public static final double INFINITY = 1.0 / 0.0;
    public static final double NEGATIVE_INFINITY = -1.0 / 0.0;
    public static final double NEGATIVE_ZERO = -0.0;
    public static final float FLOAT_NAN = 0.0f / 0.0f;
    public static final float FLOAT_INFINITY = 1.0f / 0.0f;
    public static final String NOT_CONSTANT = new String("x");
    public static final Object OBJECT = null;
    public static long counter;
    public static String label;
    public final int count = 1;
    public final String name = "n";
    public int mutable;
    public String text;
    public int[] numbers;
    public String[][] grid;

    public Greeter() {}

    public Greeter(String prefix) {}

    public Greeter(int a, long b, boolean[] c) {}

    public String greet() {
        return null;
    }

    public String greet(String name) {
        return null;
    }

    public String greet(String name, java.util.Locale locale) {
        return null;
    }

    public void log(String message) {}

    public static void main(String[] args) {}

    public static int parse(String s) {
        return 0;
    }

    public int[] arr(int[][] a, Greeter[] g) {
        return null;
    }
}
