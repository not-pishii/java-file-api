package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class Sam6<F, R, A1, A2, A3, A4, A5, A6> {
    private final InterfaceToken<F> owner;

    private final MethodRef6<F, R, A1, A2, A3, A4, A5, A6> method;

    Sam6(MethodRef6<F, R, A1, A2, A3, A4, A5, A6> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public MethodRef6<F, R, A1, A2, A3, A4, A5, A6> method() {
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

    @Override
    public String toString() {
        return this.method.toString();
    }
}
