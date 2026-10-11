package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Implemented.ByOverride;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class Overriding4<Self, R, A1, A2, A3, A4> extends Implementation4<Self, R, A1, A2, A3, A4> {
    private final ByOverride how;

    Overriding4(ByOverride how) {
        this.how = how;
    }

    @Override
    public ByOverride how() {
        return this.how;
    }

    public MethodRef4<Self, R, A1, A2, A3, A4> method() {
        return Invocables.own(this.how);
    }

    @Override
    public String toString() {
        return Invocables.describe(this.how);
    }
}
