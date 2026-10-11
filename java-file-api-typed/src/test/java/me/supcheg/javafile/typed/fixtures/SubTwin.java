package me.supcheg.javafile.typed.fixtures;

/// A generic class with overloads of the methods of [BaseTwin] that take a `String`: in a
/// `SubTwin<String>` each pair is two methods of one erased signature, which no call tells apart.
///
/// @param <T> the type of the values
public class SubTwin<T> extends BaseTwin<T> {
    public String m(String value) {
        return "sub:" + value;
    }

    protected String p(String value) {
        return "sub:" + value;
    }
}
