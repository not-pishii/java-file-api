package me.supcheg.javafile.typed.fixtures;

import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public abstract class Abstract8 {
    public String told = "";

    protected Abstract8(String a1, String a2, String a3, String a4, String a5, String a6, String a7, String a8) {
        this.told = "abs:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7 + "," + a8;
    }

    protected Abstract8(String a1, String a2, String a3, String a4, String a5, String a6, String a7, Integer... rest) {
        this.told = "absAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7 + "|" + Arrays.toString(rest);
    }
}
