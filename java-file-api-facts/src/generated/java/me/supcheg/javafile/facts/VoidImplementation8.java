package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class VoidImplementation8<Self, A1, A2, A3, A4, A5, A6, A7, A8> implements Implementation<Self> {
    private final Implemented how;

    VoidImplementation8(Implemented how) {
        this.how = how;
    }

    @Override
    public Implemented how() {
        return this.how;
    }

    @Override
    public String toString() {
        return Invocables.describe(this.how);
    }
}
