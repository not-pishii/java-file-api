package out;

/// A sealed interface whose only permitted subtype is [Out].
public sealed interface SealedInterface permits Out {
    int sides();
}
