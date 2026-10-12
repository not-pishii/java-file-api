package me.supcheg.javafile.typed.fixtures;

/// A class with an instance method and a static overload of it that is more specific.
public class StaticTwin {
    public String m(Object value) {
        return "instance";
    }

    public static String m(String value) {
        return "static";
    }
}
