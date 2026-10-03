package p;

public interface WithDefault { int f(int x); default int g(int x) { return f(x); } static WithDefault id() { return x -> x; } }
