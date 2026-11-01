package me.supcheg.javafile.typed.fixtures;

/// An interface of abstract methods of none to four parameters, of a result and of none: what a
/// record, an enum constant body or an interface overrides at every arity the typed layer declares.
public interface Overridden {
    int r0();

    int r1(int a);

    int r2(int a, int b);

    int r3(int a, int b, int c);

    int r4(int a, int b, int c, int d);

    void v0();

    void v1(int a);

    void v2(int a, int b);

    void v3(int a, int b, int c);

    void v4(int a, int b, int c, int d);
}
