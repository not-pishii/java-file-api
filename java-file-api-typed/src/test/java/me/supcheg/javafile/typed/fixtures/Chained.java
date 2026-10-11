package me.supcheg.javafile.typed.fixtures;

/** An interface of the method of Linked with a Linked for the result, and a body. */
public interface Chained {
    default Linked next() {
        return null;
    }
}
