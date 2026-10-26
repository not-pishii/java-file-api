package me.supcheg.javafile.typed.fixtures;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public interface Widening4 {
    String inh(String a1, String a2, String a3, String a4);

    void inhVoid(String a1, String a2, String a3, String a4);

    CharSequence narrow(String a1, String a2, String a3, String a4);

    String plain(String a1, String a2, String a3, String a4);

    Widening4 self(String a1, String a2, String a3, String a4);

    void take(String a1, String a2, String a3, String a4);

    default String given(String a1, String a2, String a3, String a4) {
        return "given:" + a1 + "," + a2 + "," + a3 + "," + a4;
    }

    default CharSequence chosen(String a1, String a2, String a3, String a4) {
        return "chosen:" + a1 + "," + a2 + "," + a3 + "," + a4;
    }

    default Widening4 chained(String a1, String a2, String a3, String a4) {
        return this;
    }

    default void held(String a1, String a2, String a3, String a4) {
    }
}
