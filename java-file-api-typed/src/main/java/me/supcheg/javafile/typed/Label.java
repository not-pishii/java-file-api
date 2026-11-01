package me.supcheg.javafile.typed;

/// The label of a statement being built (6.3): a `break` or a `continue` of the
/// block inside it names it. Obtained from [Block#label()] and attached by the
/// statement the label names - [Block#labeled(Label, java.util.function.Consumer)],
/// a loop, or [Block#loopForever(Label, java.util.function.Consumer)]. A label
/// names exactly one statement, and is in scope only inside it.
public final class Label {
    private Block<?, ?> owner;
    private boolean loop;

    Label() {}

    /// Attaches this label to the statement it names.
    ///
    /// @param owner the block the statement is appended to
    /// @param loop whether the statement is a loop, which a `continue` targets
    /// @throws IllegalStateException if the label already names a statement
    void attach(Block<?, ?> owner, boolean loop) {
        if (this.owner != null) {
            throw new IllegalStateException(
                    "the label already names a statement of the " + this.owner.path() + ": a label names one");
        }
        this.owner = owner;
        this.loop = loop;
    }

    /// The block the labeled statement is appended to.
    ///
    /// @return the block
    /// @throws IllegalStateException if the label names no statement yet
    Block<?, ?> owner() {
        if (owner == null) {
            throw new IllegalStateException(
                    "the label names no statement yet: attach it with labeled, a loop, or loopForever");
        }
        return owner;
    }

    /// Whether the labeled statement is a loop, which a `continue` targets.
    ///
    /// @return whether it is
    boolean labelsLoop() {
        return loop;
    }
}
