package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Implemented.ByInheritance;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class Inherited7<Self, R, A1, A2, A3, A4, A5, A6, A7> extends Implementation7<Self, R, A1, A2, A3, A4, A5, A6, A7> {
    private final ByInheritance how;

    Inherited7(ByInheritance how) {
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
