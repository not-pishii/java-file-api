package me.supcheg.javafile.langmodel.mirror;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// What is reached from things step by step: the supertypes of a type, the
/// types that wait for a type, anything that leads on to more of its kind.
public final class Hierarchy {
    private Hierarchy() {}

    /// Everything a step leads to from `start`, and from that in turn:
    /// each thing once, the nearer first, and nothing of `start` itself.
    ///
    /// @param start where to start from
    /// @param step what a thing leads to directly
    /// @param <T> the kind of the things
    /// @return what is reached
    public static <T> Stream<T> beyond(List<T> start, Function<? super T, ? extends Stream<? extends T>> step) {
        return further(start, Set.copyOf(start), step);
    }

    private static <T> Stream<T> further(
            List<T> nearer, Set<T> seen, Function<? super T, ? extends Stream<? extends T>> step) {
        List<T> next = nearer.stream()
                .<T>flatMap(step)
                .distinct()
                .filter(reached -> !seen.contains(reached))
                .toList();
        return next.isEmpty()
                ? Stream.empty()
                : Stream.concat(
                        next.stream(),
                        further(
                                next,
                                Stream.concat(seen.stream(), next.stream()).collect(Collectors.toSet()),
                                step));
    }
}
