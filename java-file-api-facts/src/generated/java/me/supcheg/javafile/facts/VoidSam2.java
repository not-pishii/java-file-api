package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class VoidSam2<F, A1, A2> {
    private final InterfaceToken<F> owner;

    private final VoidMethodRef2<F, A1, A2> method;

    VoidSam2(VoidMethodRef2<F, A1, A2> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public VoidMethodRef2<F, A1, A2> method() {
        return this.method;
    }

    public TypeToken<A1> param1() {
        return this.method.param1();
    }

    public TypeToken<A2> param2() {
        return this.method.param2();
    }

    @Override
    public String toString() {
        return this.method.toString();
    }
}
