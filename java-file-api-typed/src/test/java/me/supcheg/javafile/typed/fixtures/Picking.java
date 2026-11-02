package me.supcheg.javafile.typed.fixtures;

import java.util.List;

/// An interface with generic abstract methods, which a class implements by generic methods of its own:
/// the type parameters of the method are its own, and their bounds are those of the method it
/// implements.
public interface Picking {
    <T extends CharSequence> T pick(T value);

    <T extends CharSequence> void hold(List<T> values);
}
