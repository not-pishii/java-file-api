package me.supcheg.javafile.facts;

import javax.annotation.processing.Generated;
import me.supcheg.javafile.facts.Implemented.ByInheritance;

@Generated("me.supcheg.javafile.facts.codegen.FactsCodegen")
public final class VoidInherited4<Self, A1, A2, A3, A4> extends VoidImplementation4<Self, A1, A2, A3, A4> {
    private final ByInheritance how;

    VoidInherited4(ByInheritance how) {
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
