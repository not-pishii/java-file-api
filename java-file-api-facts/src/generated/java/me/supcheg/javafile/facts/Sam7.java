package me.supcheg.javafile.facts;

public final class Sam7<F, R, A1, A2, A3, A4, A5, A6, A7> {
    private final InterfaceToken<F> owner;

    private final MethodRef7<F, R, A1, A2, A3, A4, A5, A6, A7> method;

    private Sam7(MethodRef7<F, R, A1, A2, A3, A4, A5, A6, A7> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public static <F, R, A1, A2, A3, A4, A5, A6, A7> Sam7<F, R, A1, A2, A3, A4, A5, A6, A7> introduce(MethodRef7<F, R, A1, A2, A3, A4, A5, A6, A7> method) {
        return new Sam7<>(method);
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public MethodRef7<F, R, A1, A2, A3, A4, A5, A6, A7> method() {
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

    public TypeToken<A5> param5() {
        return this.method.param5();
    }

    public TypeToken<A6> param6() {
        return this.method.param6();
    }

    public TypeToken<A7> param7() {
        return this.method.param7();
    }

    @Override
    public String toString() {
        return this.method.toString();
    }
}
