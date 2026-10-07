package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class VoidImplementation4<Self, A1, A2, A3, A4> implements Implementation<Self> {
    private final Implemented how;

    VoidImplementation4(Implemented how) {
        this.how = how;
    }

    @Override
    public Implemented how() {
        return this.how;
    }

    public VoidMethodRef4<Self, A1, A2, A3, A4> method() {
        return Invocables.own(this.how);
    }

    @Override
    public String toString() {
        return Invocables.describe(this.how);
    }
}
