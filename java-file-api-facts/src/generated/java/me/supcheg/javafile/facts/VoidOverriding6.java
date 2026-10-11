package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Implemented.ByOverride;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class VoidOverriding6<Self, A1, A2, A3, A4, A5, A6> extends VoidImplementation6<Self, A1, A2, A3, A4, A5, A6> {
    private final ByOverride how;

    VoidOverriding6(ByOverride how) {
        this.how = how;
    }

    @Override
    public ByOverride how() {
        return this.how;
    }

    public VoidMethodRef6<Self, A1, A2, A3, A4, A5, A6> method() {
        return Invocables.own(this.how);
    }

    @Override
    public String toString() {
        return Invocables.describe(this.how);
    }
}
