package me.supcheg.javafile.typed.fixtures;

import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public abstract class Abstract3 {
    public String told = "";

    protected Abstract3(String a1, String a2, String a3) {
        this.told = "abs:" + a1 + "," + a2 + "," + a3;
    }

    protected Abstract3(String a1, String a2, Integer... rest) {
        this.told = "absAll:" + a1 + "," + a2 + "|" + Arrays.toString(rest);
    }
}
