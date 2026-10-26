package me.supcheg.javafile.typed.fixtures;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public interface Widening3 {
    String inh(String a1, String a2, String a3);

    void inhVoid(String a1, String a2, String a3);

    CharSequence narrow(String a1, String a2, String a3);

    String plain(String a1, String a2, String a3);

    Widening3 self(String a1, String a2, String a3);

    void take(String a1, String a2, String a3);

    default String given(String a1, String a2, String a3) {
        return "given:" + a1 + "," + a2 + "," + a3;
    }

    default CharSequence chosen(String a1, String a2, String a3) {
        return "chosen:" + a1 + "," + a2 + "," + a3;
    }

    default Widening3 chained(String a1, String a2, String a3) {
        return this;
    }

    default void held(String a1, String a2, String a3) {
    }
}
