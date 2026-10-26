package me.supcheg.javafile.typed.fixtures;

import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public abstract class Abstract7 {
    public String told = "";

    protected Abstract7(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
        this.told = "abs:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7;
    }

    protected Abstract7(String a1, String a2, String a3, String a4, String a5, String a6, Integer... rest) {
        this.told = "absAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "|" + Arrays.toString(rest);
    }
}
