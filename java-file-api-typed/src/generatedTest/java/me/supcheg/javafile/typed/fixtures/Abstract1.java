package me.supcheg.javafile.typed.fixtures;

import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public abstract class Abstract1 {
    public String told = "";

    protected Abstract1(String a1) {
        this.told = "abs:" + a1;
    }

    protected Abstract1(Integer... rest) {
        this.told = "absAll:" + "|" + Arrays.toString(rest);
    }
}
