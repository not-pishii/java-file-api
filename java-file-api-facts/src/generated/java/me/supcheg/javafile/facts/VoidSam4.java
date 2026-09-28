package me.supcheg.javafile.facts;

public final class VoidSam4<F, A1, A2, A3, A4> {
    private final InterfaceToken<F> owner;

    private final VoidMethodRef4<F, A1, A2, A3, A4> method;

    private VoidSam4(VoidMethodRef4<F, A1, A2, A3, A4> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public static <F, A1, A2, A3, A4> VoidSam4<F, A1, A2, A3, A4> introduce(VoidMethodRef4<F, A1, A2, A3, A4> method) {
        return new VoidSam4<>(method);
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public VoidMethodRef4<F, A1, A2, A3, A4> method() {
        return this.method;
    }

    public TypeToken<A1> param1() {
        return this.method.param1();
    }

    public TypeToken<A2> param2() {
        return this.method.param2();
    }

    public TypeToken<A3> param3() {
        return this.method.param3();
    }

    public TypeToken<A4> param4() {
        return this.method.param4();
    }

    @Override
    public String toString() {
        return this.method.toString();
    }
}
