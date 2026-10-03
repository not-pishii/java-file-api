package me.supcheg.javafile.facts.processor.harness;

import java.util.Optional;
import java.util.function.Supplier;

/// A value computed when first asked for, and once: the library of a
/// fixture is compiled for the first test that needs it. A computation
/// that fails is run again by the next test, which fails the same way.
final class Memo<T> implements Supplier<T> {
    private final Supplier<T> computation;
    private Optional<T> value = Optional.empty();

    private Memo(Supplier<T> computation) {
        this.computation = computation;
    }

    static <T> Memo<T> of(Supplier<T> computation) {
        return new Memo<>(computation);
    }

    @Override
    public synchronized T get() {
        value = value.or(() -> Optional.of(computation.get()));
        return value.orElseThrow();
    }
}
