package me.supcheg.javafile.facts;

public final class VoidSam12<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> {
    private final InterfaceToken<F> owner;

    private final VoidMethodRef12<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method;

    private VoidSam12(VoidMethodRef12<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public static <F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> VoidSam12<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> introduce(VoidMethodRef12<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method) {
        return new VoidSam12<>(method);
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public VoidMethodRef12<F, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method() {
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

    public TypeToken<A6> param6() {
        return this.method.param6();
    }

    public TypeToken<A7> param7() {
        return this.method.param7();
    }

    public TypeToken<A8> param8() {
        return this.method.param8();
    }

    public TypeToken<A9> param9() {
        return this.method.param9();
    }

    public TypeToken<A10> param10() {
        return this.method.param10();
    }

    public TypeToken<A11> param11() {
        return this.method.param11();
    }

    public TypeToken<A12> param12() {
        return this.method.param12();
    }

    @Override
    public String toString() {
        return this.method.toString();
    }
}
