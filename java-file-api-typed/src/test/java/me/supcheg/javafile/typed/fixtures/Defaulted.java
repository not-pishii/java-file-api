package me.supcheg.javafile.typed.fixtures;

/// An interface whose default method has a wider result than the one of [Mid].
public interface Defaulted {
    default CharSequence label() {
        return "defaulted";
    }
}
