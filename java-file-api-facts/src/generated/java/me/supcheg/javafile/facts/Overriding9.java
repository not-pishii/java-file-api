package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Implemented.ByOverride;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class Overriding9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> extends Implementation9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> {
    private final ByOverride how;

    Overriding9(ByOverride how) {
        this.how = how;
    }

    @Override
    public ByOverride how() {
        return this.how;
    }

    public MethodRef9<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9> method() {
        return Invocables.own(this.how);
    }

    @Override
    public String toString() {
        return Invocables.describe(this.how);
    }
}
