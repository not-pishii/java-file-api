package me.supcheg.javafile.typed.fixtures;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public interface Widening0 {
    String inh();

    void inhVoid();

    CharSequence narrow();

    String plain();

    Widening0 self();

    void take();

    default String given() {
        return "given:";
    }

    default CharSequence chosen() {
        return "chosen:";
    }

    default Widening0 chained() {
        return this;
    }

    default void held() {
    }
}
