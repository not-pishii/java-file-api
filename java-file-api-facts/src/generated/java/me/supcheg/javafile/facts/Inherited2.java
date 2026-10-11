package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Implemented.ByInheritance;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class Inherited2<Self, R, A1, A2> extends Implementation2<Self, R, A1, A2> {
    private final ByInheritance how;

    Inherited2(ByInheritance how) {
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
