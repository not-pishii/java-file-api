package me.supcheg.javafile.typed.fixtures;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public interface Widening7 {
    String inh(String a1, String a2, String a3, String a4, String a5, String a6, String a7);

    void inhVoid(String a1, String a2, String a3, String a4, String a5, String a6, String a7);

    CharSequence narrow(String a1, String a2, String a3, String a4, String a5, String a6, String a7);

    String plain(String a1, String a2, String a3, String a4, String a5, String a6, String a7);

    Widening7 self(String a1, String a2, String a3, String a4, String a5, String a6, String a7);

    void take(String a1, String a2, String a3, String a4, String a5, String a6, String a7);

    default String given(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
        return "given:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7;
    }

    default CharSequence chosen(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
        return "chosen:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7;
    }

    default Widening7 chained(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
        return this;
    }

    default void held(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
    }
}
