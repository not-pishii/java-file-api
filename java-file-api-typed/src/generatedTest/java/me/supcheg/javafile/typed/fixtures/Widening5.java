package me.supcheg.javafile.typed.fixtures;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public interface Widening5 {
    String inh(String a1, String a2, String a3, String a4, String a5);

    void inhVoid(String a1, String a2, String a3, String a4, String a5);

    CharSequence narrow(String a1, String a2, String a3, String a4, String a5);

    String plain(String a1, String a2, String a3, String a4, String a5);

    Widening5 self(String a1, String a2, String a3, String a4, String a5);

    void take(String a1, String a2, String a3, String a4, String a5);

    default String given(String a1, String a2, String a3, String a4, String a5) {
        return "given:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5;
    }

    default CharSequence chosen(String a1, String a2, String a3, String a4, String a5) {
        return "chosen:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5;
    }

    default Widening5 chained(String a1, String a2, String a3, String a4, String a5) {
        return this;
    }

    default void held(String a1, String a2, String a3, String a4, String a5) {
    }
}
