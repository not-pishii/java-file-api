package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class Implementation4<Self, R, A1, A2, A3, A4> implements Implementation<Self> {
    private final Implemented how;

    Implementation4(Implemented how) {
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
