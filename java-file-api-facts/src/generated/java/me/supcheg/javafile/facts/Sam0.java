package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class Sam0<F, R> {
    private final InterfaceToken<F> owner;

    private final MethodRef0<F, R> method;

    Sam0(MethodRef0<F, R> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public MethodRef0<F, R> method() {
        return this.method;
    }

    public TypeToken<R> result() {
        return this.method.result();
    }

    @Override
    public String toString() {
        return this.method.toString();
    }
}
