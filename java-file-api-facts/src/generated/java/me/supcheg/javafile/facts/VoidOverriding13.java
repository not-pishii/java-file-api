package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Implemented.ByOverride;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class VoidOverriding13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> extends VoidImplementation13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> {
    private final ByOverride how;

    VoidOverriding13(ByOverride how) {
        this.how = how;
    }

    @Override
    public ByOverride how() {
        return this.how;
    }

    public VoidMethodRef13<Self, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13> method() {
        return Invocables.own(this.how);
    }

    @Override
    public String toString() {
        return Invocables.describe(this.how);
    }
}
