package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class VoidSam3<F, A1, A2, A3> {
    private final InterfaceToken<F> owner;

    private final VoidMethodRef3<F, A1, A2, A3> method;

    VoidSam3(VoidMethodRef3<F, A1, A2, A3> method) {
        this.owner = Invocables.requireSam(method.owner(), method);
        this.method = method;
    }

    public InterfaceToken<F> owner() {
        return this.owner;
    }

    public VoidMethodRef3<F, A1, A2, A3> method() {
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

    @Override
    public String toString() {
        return this.method.toString();
    }
}
