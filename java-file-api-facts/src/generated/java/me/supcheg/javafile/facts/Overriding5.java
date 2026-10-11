package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Implemented.ByOverride;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class Overriding5<Self, R, A1, A2, A3, A4, A5> extends Implementation5<Self, R, A1, A2, A3, A4, A5> {
    private final ByOverride how;

    Overriding5(ByOverride how) {
        this.how = how;
    }

    @Override
    public ByOverride how() {
        return this.how;
    }

    public MethodRef5<Self, R, A1, A2, A3, A4, A5> method() {
        return Invocables.own(this.how);
    }

    @Override
    public String toString() {
        return Invocables.describe(this.how);
    }
}
