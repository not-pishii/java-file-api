package me.supcheg.javafile.typed.fixtures;

/// An open class of members of one, two and three parameters, the last of variable arity, each of a name of its own so
/// that no other method of the name is a choice for javac: what a generated class calls with the arguments of the
/// last parameter one by one.
public class Spreads {
    /// What the `void` methods were last called with.
    public String told = "";

    /// How many arguments the `static` `void` methods were given, together.
    public static int counted;

    public Spreads() {}

    public String join1(String... parts) {
        return String.join("+", parts);
    }

    public String join2(int first, String... parts) {
        return first + ":" + join1(parts);
    }

    public String join3(int first, int second, String... parts) {
        return first + "," + second + ":" + join1(parts);
    }

    public void put1(String... parts) {
        told = join1(parts);
    }

    public void put2(int first, String... parts) {
        told = join2(first, parts);
    }

    public void put3(int first, int second, String... parts) {
        told = join3(first, second, parts);
    }

    public static String sjoin1(String... parts) {
        return String.join("+", parts);
    }

    public static String sjoin2(int first, String... parts) {
        return first + ":" + sjoin1(parts);
    }

    public static String sjoin3(int first, int second, String... parts) {
        return first + "," + second + ":" + sjoin1(parts);
    }

    public static void count1(String... parts) {
        counted += parts.length;
    }

    public static void count2(int first, String... parts) {
        counted += 1 + parts.length;
    }

    public static void count3(int first, int second, String... parts) {
        counted += 2 + parts.length;
    }

    public static String mix() {
        return "none";
    }

    public static String mix(String... parts) {
        return "parts:" + String.join("+", parts);
    }

    public static String mix(int first, int second, String[] parts) {
        return "array";
    }

    public static int rows(String[]... rows) {
        return rows.length;
    }

    public static String pick(String... parts) {
        return "parts:" + String.join("+", parts);
    }

    public static String pick(int first, String... parts) {
        return "number:" + first;
    }
}
