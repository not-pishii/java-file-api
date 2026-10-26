package me.supcheg.javafile.typed.fixtures;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public interface Widening10 {
    String inh(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10);

    void inhVoid(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10);

    CharSequence narrow(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10);

    String plain(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10);

    Widening10 self(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10);

    void take(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10);

    default String given(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10) {
        return "given:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7 + "," + a8 + "," + a9 + "," + a10;
    }

    default CharSequence chosen(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10) {
        return "chosen:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7 + "," + a8 + "," + a9 + "," + a10;
    }

    default Widening10 chained(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10) {
        return this;
    }

    default void held(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8, String a9, String a10) {
    }
}
