package me.supcheg.javafile.typed.fixtures;

import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public class Wide7 {
    public static String noted = "";

    public String told = "";

    public Wide7(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
        this.told = "new:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7;
    }

    public Wide7(String a1, String a2, String a3, String a4, String a5, String a6, Integer... rest) {
        this.told = "newAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "|" + Arrays.toString(rest);
    }

    public String pick(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
        return "pick:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7;
    }

    public CharSequence cs(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
        return "cs:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7;
    }

    public Wide7 same(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
        return this;
    }

    public void put(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
        told += "put:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7 + ";";
    }

    public String inh(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
        return "inh:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7;
    }

    public void inhVoid(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
        told += "inhVoid:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7 + ";";
    }

    public static String join(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
        return "join:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7;
    }

    public static void note(String a1, String a2, String a3, String a4, String a5, String a6, String a7) {
        noted += "note:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + a7 + ";";
    }

    public String pickAll(String a1, String a2, String a3, String a4, String a5, String a6, String... rest) {
        return "pickAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "|" + Arrays.toString(rest);
    }

    public void putAll(String a1, String a2, String a3, String a4, String a5, String a6, String... rest) {
        told += "putAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "|" + Arrays.toString(rest) + ";";
    }

    public static String joinAll(String a1, String a2, String a3, String a4, String a5, String a6, String... rest) {
        return "joinAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "|" + Arrays.toString(rest);
    }

    public static void noteAll(String a1, String a2, String a3, String a4, String a5, String a6, String... rest) {
        noted += "noteAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "|" + Arrays.toString(rest) + ";";
    }
}
