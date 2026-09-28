package me.supcheg.javafile.facts;

public final class VoidSam0<F> {
    private final InterfaceToken<F> owner;

    private final VoidMethodRef0<F> method;

    private VoidSam0(VoidMethodRef0<F> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public static <F> VoidSam0<F> introduce(VoidMethodRef0<F> method) {
        return new VoidSam0<>(method);
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public VoidMethodRef0<F> method() {
        return this.method;
    }

    @Override
    public String toString() {
        return this.method.toString();
    }
}
