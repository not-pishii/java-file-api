package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Implemented.ByOverride;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class VoidOverriding8<Self, A1, A2, A3, A4, A5, A6, A7, A8> extends VoidImplementation8<Self, A1, A2, A3, A4, A5, A6, A7, A8> {
    private final ByOverride how;

    VoidOverriding8(ByOverride how) {
        this.how = how;
    }

    @Override
    public ByOverride how() {
        return this.how;
    }

    public VoidMethodRef8<Self, A1, A2, A3, A4, A5, A6, A7, A8> method() {
        return Invocables.own(this.how);
    }

    @Override
    public String toString() {
        return Invocables.describe(this.how);
    }
}
