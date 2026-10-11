package me.supcheg.javafile.typed.fixtures;

/** A class whose constructors are overloads of an Object and of a String. */
public class Picked {
    public final String which;

    public Picked(Object value) {
        this.which = "object";
    }

    public Picked(String value) {
        this.which = "string";
    }
}
