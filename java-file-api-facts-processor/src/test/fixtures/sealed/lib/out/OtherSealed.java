package out;

/// A sealed class whose permitted subtype is [OtherOut] alone.
public sealed class OtherSealed permits OtherOut {
    public OtherSealed() {}
}
