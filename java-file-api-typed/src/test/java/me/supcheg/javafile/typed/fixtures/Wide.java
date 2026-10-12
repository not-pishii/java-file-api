package me.supcheg.javafile.typed.fixtures;

/// An open class of members of one, two and three parameters, each of which tells what it was called
/// with: what a generated class calls, overrides and declares over at the arities beyond none.
public class Wide {
    /// What the static methods were last called with.
    public static String noted = "";

    /// What the constructor and the `void` methods were called with, in order.
    public String told = "";

    public Wide() {}

    public Wide(String first, String second) {
        told = "new:" + first + second;
    }

    public Wide(String first, String second, String third) {
        told = "new:" + first + second + third;
    }

    public Object one(String first) {
        return "one:" + first;
    }

    public Object two(String first, String second) {
        return "two:" + first + second;
    }

    public Object pair(String first, String second) {
        return "pair:" + first + second;
    }

    public Wide same(String first) {
        return this;
    }

    public Wide same(String first, String second) {
        return this;
    }

    public void put(String first) {
        told += "put:" + first;
    }

    public void put(String first, String second) {
        told += "put:" + first + second;
    }

    public void put(String first, String second, String third) {
        told += "put:" + first + second + third;
    }

    public static String join(String first, String second, String third) {
        return first + second + third;
    }

    public static void note(String first) {
        noted = first;
    }

    public static void note(String first, String second) {
        noted = first + second;
    }

    public static void note(String first, String second, String third) {
        noted = first + second + third;
    }
}
