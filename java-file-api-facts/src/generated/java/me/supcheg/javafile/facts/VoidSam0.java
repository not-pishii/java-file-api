package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class VoidSam0<F> {
    private final InterfaceToken<F> owner;

    private final VoidMethodRef0<F> method;

    VoidSam0(VoidMethodRef0<F> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public VoidMethodRef0<F> method() {
        return this.method;
    }

    @Override
    public String toString() {
        return this.method.toString();
    }
}
