package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class VoidSam1<F, A1> {
    private final InterfaceToken<F> owner;

    private final VoidMethodRef1<F, A1> method;

    VoidSam1(VoidMethodRef1<F, A1> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public VoidMethodRef1<F, A1> method() {
        return this.method;
    }

    public TypeToken<A1> param1() {
        return this.method.param1();
    }

    @Override
    public String toString() {
        return this.method.toString();
    }
}
