package p;

/// `impl.k` and `Impl.s` are ambiguous to javac: each is a field of `PubA` and of `PubB`.
public class Impl extends PubA implements PubB {}
