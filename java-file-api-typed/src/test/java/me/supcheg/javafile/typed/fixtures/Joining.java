package me.supcheg.javafile.typed.fixtures;

/// An interface of methods with parameters [Mid] has with a body each.
public interface Joining {
    String join(String first, String second);

    void put(String key);

    void put(String key, String value);

    String pick(String value);
}
