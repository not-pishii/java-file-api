package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Implemented.ByOverride;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class Overriding2<Self, R, A1, A2> extends Implementation2<Self, R, A1, A2> {
    private final ByOverride how;

    Overriding2(ByOverride how) {
        this.how = how;
    }

    @Override
    public ByOverride how() {
        return this.how;
    }

    public MethodRef2<Self, R, A1, A2> method() {
        return Invocables.own(this.how);
    }

    @Override
    public String toString() {
        return Invocables.describe(this.how);
    }
}
