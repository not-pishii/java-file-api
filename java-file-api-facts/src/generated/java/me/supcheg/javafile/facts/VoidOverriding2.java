package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Implemented.ByOverride;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class VoidOverriding2<Self, A1, A2> extends VoidImplementation2<Self, A1, A2> {
    private final ByOverride how;

    VoidOverriding2(ByOverride how) {
        this.how = how;
    }

    @Override
    public ByOverride how() {
        return this.how;
    }

    public VoidMethodRef2<Self, A1, A2> method() {
        return Invocables.own(this.how);
    }

    @Override
    public String toString() {
        return Invocables.describe(this.how);
    }
}
