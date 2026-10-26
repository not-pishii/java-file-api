package me.supcheg.javafile.typed.fixtures;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public interface Widening1 {
    String inh(String a1);

    void inhVoid(String a1);

    CharSequence narrow(String a1);

    String plain(String a1);

    Widening1 self(String a1);

    void take(String a1);

    default String given(String a1) {
        return "given:" + a1;
    }

    default CharSequence chosen(String a1) {
        return "chosen:" + a1;
    }

    default Widening1 chained(String a1) {
        return this;
    }

    default void held(String a1) {
    }
}
