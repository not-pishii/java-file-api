package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class Implementation0<Self, R> implements Implementation<Self> {
    private final Implemented how;

    Implementation0(Implemented how) {
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
