package me.supcheg.javafile.typed.fixtures;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public interface Widening2 {
    String inh(String a1, String a2);

    void inhVoid(String a1, String a2);

    CharSequence narrow(String a1, String a2);

    String plain(String a1, String a2);

    Widening2 self(String a1, String a2);

    void take(String a1, String a2);

    default String given(String a1, String a2) {
        return "given:" + a1 + "," + a2;
    }

    default CharSequence chosen(String a1, String a2) {
        return "chosen:" + a1 + "," + a2;
    }

    default Widening2 chained(String a1, String a2) {
        return this;
    }

    default void held(String a1, String a2) {
    }
}
