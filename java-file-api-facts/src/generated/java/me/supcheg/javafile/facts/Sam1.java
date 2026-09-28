package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class Sam1<F, R, A1> {
    private final InterfaceToken<F> owner;

    private final MethodRef1<F, R, A1> method;

    Sam1(MethodRef1<F, R, A1> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public MethodRef1<F, R, A1> method() {
        return this.method;
    }

    public TypeToken<R> result() {
        return this.method.result();
    }

    public TypeToken<A1> param1() {
        return this.method.param1();
    }

    @Override
    public String toString() {
        return this.method.toString();
    }
}
