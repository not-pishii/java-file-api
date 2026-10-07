package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class Implementation12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> implements Implementation<Self> {
    private final Implemented how;

    Implementation12(Implemented how) {
        this.how = how;
    }

    @Override
    public Implemented how() {
        return this.how;
    }

    public MethodRef12<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> method() {
        return Invocables.own(this.how);
    }

    @Override
    public String toString() {
        return Invocables.describe(this.how);
    }
}
