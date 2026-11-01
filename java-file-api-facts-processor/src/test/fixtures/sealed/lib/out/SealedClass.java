package out;

/// A sealed class whose only permitted subtype is [Out].
public sealed class SealedClass permits Out {
    public SealedClass() {}
}
