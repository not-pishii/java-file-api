package me.supcheg.javafile.typed.fixtures;

import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public abstract class Abstract6 {
    public String told = "";

    protected Abstract6(String a1, String a2, String a3, String a4, String a5, String a6) {
        this.told = "abs:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6;
    }

    protected Abstract6(String a1, String a2, String a3, String a4, String a5, Integer... rest) {
        this.told = "absAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "|" + Arrays.toString(rest);
    }
}
