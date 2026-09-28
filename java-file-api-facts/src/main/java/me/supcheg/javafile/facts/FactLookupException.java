package me.supcheg.javafile.facts;

import java.io.Serial;
import java.util.List;

/// Thrown when a fact is asked for that its source cannot prove, at the
/// earliest point possible — the lookup itself (§3.8).
///
/// The message says what was looked for, where, and what similar facts the
/// source does have, e.g.
/// `no method java.lang.String com.example.User.nam() in mirror of com.example.User; similar: name()`.
public final class FactLookupException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String sought;
    private final String origin;
    private final transient List<String> similar;

    /// Creates an exception.
    ///
    /// @param sought a description of the missing fact
    /// @param origin where it was looked for
    /// @param similar descriptions of similar facts the source has; may be empty
    public FactLookupException(String sought, String origin, List<String> similar) {
        super("no " + sought + " in " + origin + (similar.isEmpty() ? "" : "; similar: " + String.join(", ", similar)));
        this.sought = sought;
        this.origin = origin;
        this.similar = List.copyOf(similar);
    }

    /// A description of the missing fact.
    ///
    /// @return what was looked for
    public String sought() {
        return sought;
    }

    /// Where the fact was looked for.
    ///
    /// @return the origin
    public String origin() {
        return origin;
    }

    /// Descriptions of similar facts the source has.
    ///
    /// @return the similar facts, possibly empty
    public List<String> similar() {
        return similar;
    }
}
