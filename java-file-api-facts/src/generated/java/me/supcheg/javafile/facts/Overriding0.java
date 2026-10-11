package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Implemented.ByOverride;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class Overriding0<Self, R> extends Implementation0<Self, R> {
    private final ByOverride how;

    Overriding0(ByOverride how) {
        this.how = how;
    }

    @Override
    public ByOverride how() {
        return this.how;
    }

    public MethodRef0<Self, R> method() {
        return Invocables.own(this.how);
    }

    @Override
    public String toString() {
        return Invocables.describe(this.how);
    }
}
