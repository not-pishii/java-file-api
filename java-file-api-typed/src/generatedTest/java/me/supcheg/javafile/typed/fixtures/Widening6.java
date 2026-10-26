package me.supcheg.javafile.typed.fixtures;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public interface Widening6 {
    String inh(String a1, String a2, String a3, String a4, String a5, String a6);

    void inhVoid(String a1, String a2, String a3, String a4, String a5, String a6);

    CharSequence narrow(String a1, String a2, String a3, String a4, String a5, String a6);

    String plain(String a1, String a2, String a3, String a4, String a5, String a6);

    Widening6 self(String a1, String a2, String a3, String a4, String a5, String a6);

    void take(String a1, String a2, String a3, String a4, String a5, String a6);

    default String given(String a1, String a2, String a3, String a4, String a5, String a6) {
        return "given:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6;
    }

    default CharSequence chosen(String a1, String a2, String a3, String a4, String a5, String a6) {
        return "chosen:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6;
    }

    default Widening6 chained(String a1, String a2, String a3, String a4, String a5, String a6) {
        return this;
    }

    default void held(String a1, String a2, String a3, String a4, String a5, String a6) {
    }
}
