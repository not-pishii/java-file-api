package me.supcheg.javafile.typed.fixtures;

/// An interface whose method [Maker] has `protected`.
public interface Faulty {
    RuntimeException fault();
}
