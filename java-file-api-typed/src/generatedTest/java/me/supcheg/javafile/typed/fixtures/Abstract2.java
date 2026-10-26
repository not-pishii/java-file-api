package me.supcheg.javafile.typed.fixtures;

import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public abstract class Abstract2 {
    public String told = "";

    protected Abstract2(String a1, String a2) {
        this.told = "abs:" + a1 + "," + a2;
    }

    protected Abstract2(String a1, Integer... rest) {
        this.told = "absAll:" + a1 + "|" + Arrays.toString(rest);
    }
}
