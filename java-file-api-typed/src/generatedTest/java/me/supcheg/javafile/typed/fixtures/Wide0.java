package me.supcheg.javafile.typed.fixtures;

import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public class Wide0 {
    public static String noted = "";

    public String told = "";

    public Wide0() {
        this.told = "new:";
    }

    public String pick() {
        return "pick:";
    }

    public CharSequence cs() {
        return "cs:";
    }

    public Wide0 same() {
        return this;
    }

    public void put() {
        told += "put:" + ";";
    }

    public String inh() {
        return "inh:";
    }

    public void inhVoid() {
        told += "inhVoid:" + ";";
    }

    public static String join() {
        return "join:";
    }

    public static void note() {
        noted += "note:" + ";";
    }
}
