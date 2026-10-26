package me.supcheg.javafile.typed.fixtures;

import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public class Wide5 {
    public static String noted = "";

    public String told = "";

    public Wide5(String a1, String a2, String a3, String a4, String a5) {
        this.told = "new:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5;
    }

    public Wide5(String a1, String a2, String a3, String a4, Integer... rest) {
        this.told = "newAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "|" + Arrays.toString(rest);
    }

    public String pick(String a1, String a2, String a3, String a4, String a5) {
        return "pick:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5;
    }

    public CharSequence cs(String a1, String a2, String a3, String a4, String a5) {
        return "cs:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5;
    }

    public Wide5 same(String a1, String a2, String a3, String a4, String a5) {
        return this;
    }

    public void put(String a1, String a2, String a3, String a4, String a5) {
        told += "put:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + ";";
    }

    public String inh(String a1, String a2, String a3, String a4, String a5) {
        return "inh:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5;
    }

    public void inhVoid(String a1, String a2, String a3, String a4, String a5) {
        told += "inhVoid:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + ";";
    }

    public static String join(String a1, String a2, String a3, String a4, String a5) {
        return "join:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5;
    }

    public static void note(String a1, String a2, String a3, String a4, String a5) {
        noted += "note:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + ";";
    }

    public String pickAll(String a1, String a2, String a3, String a4, String... rest) {
        return "pickAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "|" + Arrays.toString(rest);
    }

    public void putAll(String a1, String a2, String a3, String a4, String... rest) {
        told += "putAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "|" + Arrays.toString(rest) + ";";
    }

    public static String joinAll(String a1, String a2, String a3, String a4, String... rest) {
        return "joinAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "|" + Arrays.toString(rest);
    }

    public static void noteAll(String a1, String a2, String a3, String a4, String... rest) {
        noted += "noteAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "|" + Arrays.toString(rest) + ";";
    }
}
