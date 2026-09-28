package me.supcheg.javafile.facts;

public final class Sam3<F, R, A1, A2, A3> {
    private final InterfaceToken<F> owner;

    private final MethodRef3<F, R, A1, A2, A3> method;

    private Sam3(MethodRef3<F, R, A1, A2, A3> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public static <F, R, A1, A2, A3> Sam3<F, R, A1, A2, A3> introduce(MethodRef3<F, R, A1, A2, A3> method) {
        return new Sam3<>(method);
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public MethodRef3<F, R, A1, A2, A3> method() {
        return this.method;
    }

    public TypeToken<R> result() {
        return this.method.result();
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

    @Override
    public String toString() {
        return this.method.toString();
    }
}
