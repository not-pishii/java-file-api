package me.supcheg.javafile.typed.fixtures;

/// A class with a static method, which a subclass has beside the methods it declares.
// a generated subclass extends it, so it is no utility class
@SuppressWarnings("PrivateConstructorForUtilityClass")
public class StaticPick {
    public static String pick(String value) {
        return "static";
    }
}
