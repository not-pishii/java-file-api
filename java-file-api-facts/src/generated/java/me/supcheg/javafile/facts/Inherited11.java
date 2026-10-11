package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Implemented.ByInheritance;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class Inherited11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> extends Implementation11<Self, R, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> {
    private final ByInheritance how;

    Inherited11(ByInheritance how) {
        this.how = how;
    }

    @Override
    public ByInheritance how() {
        return this.how;
    }

    @Override
    public String toString() {
        return Invocables.describe(this.how);
    }
}
