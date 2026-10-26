package me.supcheg.javafile.typed.fixtures;

/// Static methods of variable arity beside methods of the name that javac may take for the same arguments, or may
/// not: each tells which it is.
public final class Overloads {
    private Overloads() {}

    public static String m(Object... xs) {
        return "var";
    }

    public static String m(Object a, Object b) {
        return "fixed";
    }

    public static String two(String a, Object... xs) {
        return "string";
    }

    public static String two(Number a, Object... xs) {
        return "number";
    }

    public static String ints(int... xs) {
        return "var";
    }

    public static String ints(long a) {
        return "long";
    }

    public static String objs(Integer... xs) {
        return "var";
    }

    public static String objs(int a) {
        return "int";
    }
}
