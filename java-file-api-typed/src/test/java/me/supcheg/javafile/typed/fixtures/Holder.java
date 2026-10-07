package me.supcheg.javafile.typed.fixtures;

import java.util.function.Supplier;

/// A class whose constructor takes a functional interface.
public final class Holder {
    private final Supplier<String> supplier;

    /// @param supplier what [#get()] asks
    public Holder(Supplier<String> supplier) {
        this.supplier = supplier;
    }

    /// @return what the supplier gives
    public String get() {
        return supplier.get();
    }
}
