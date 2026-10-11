package me.supcheg.javafile.typed.fixtures;

import java.util.Collection;
import java.util.List;

/// A superclass whose methods a subclass overrides with narrower results, or has for those of an
/// interface.
public class Maker {
    public Object make() {
        return "made";
    }

    public String get() {
        return "maker";
    }

    public Collection<String> words() {
        return List.of("maker");
    }

    public Collection<? extends CharSequence> texts() {
        return List.of("maker");
    }

    public int count() {
        return 1;
    }

    public void reset() {}
}
