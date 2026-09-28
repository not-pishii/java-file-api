package me.supcheg.javafile.facts;

public final class Sam4<F, R, A1, A2, A3, A4> {
    private final InterfaceToken<F> owner;

    private final MethodRef4<F, R, A1, A2, A3, A4> method;

    private Sam4(MethodRef4<F, R, A1, A2, A3, A4> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public static <F, R, A1, A2, A3, A4> Sam4<F, R, A1, A2, A3, A4> introduce(MethodRef4<F, R, A1, A2, A3, A4> method) {
        return new Sam4<>(method);
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public MethodRef4<F, R, A1, A2, A3, A4> method() {
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

    public TypeToken<A4> param4() {
        return this.method.param4();
    }

    @Override
    public String toString() {
        return this.method.toString();
    }
}
