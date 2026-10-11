package me.supcheg.javafile.typed.fixtures;

/// A class between [Base] and a generated class: it overrides the `protected` hook of [Base], implements
/// `Runnable` — what a generated class has from it by inheritance —, narrows the `throws` clause of a method
/// of [Base], has the method a default one of [Named] is of, and a `protected` method of its own.
public class Mid extends Base implements Runnable {
    public int runs;

    @Override
    protected String hook(String value) {
        return "mid:" + value;
    }

    @Override
    public void run() {
        runs++;
    }

    @Override
    protected void risky() {}

    public String label() {
        return "mid";
    }

    protected void quiet() {}
}
