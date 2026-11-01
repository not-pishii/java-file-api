package out;

/// The permitted subtype of [SealedClass] and [SealedInterface], itself sealed by nothing.
public final class Out extends SealedClass implements SealedInterface {
    public Out() {}

    @Override
    public int sides() {
        return 3;
    }
}
