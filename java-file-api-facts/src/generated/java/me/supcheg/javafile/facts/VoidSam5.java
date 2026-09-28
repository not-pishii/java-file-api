package me.supcheg.javafile.facts;

public final class VoidSam5<F, A1, A2, A3, A4, A5> {
    private final InterfaceToken<F> owner;

    private final VoidMethodRef5<F, A1, A2, A3, A4, A5> method;

    private VoidSam5(VoidMethodRef5<F, A1, A2, A3, A4, A5> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public static <F, A1, A2, A3, A4, A5> VoidSam5<F, A1, A2, A3, A4, A5> introduce(VoidMethodRef5<F, A1, A2, A3, A4, A5> method) {
        return new VoidSam5<>(method);
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public VoidMethodRef5<F, A1, A2, A3, A4, A5> method() {
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

    public TypeToken<A5> param5() {
        return this.method.param5();
    }

    @Override
    public String toString() {
        return this.method.toString();
    }
}
