package me.supcheg.javafile.typed.fixtures;

import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public class Wide6 {
    public static String noted = "";

    public String told = "";

    public Wide6(String a1, String a2, String a3, String a4, String a5, String a6) {
        this.told = "new:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6;
    }

    public Wide6(String a1, String a2, String a3, String a4, String a5, Integer... rest) {
        this.told = "newAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "|" + Arrays.toString(rest);
    }

    public String pick(String a1, String a2, String a3, String a4, String a5, String a6) {
        return "pick:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6;
    }

    public CharSequence cs(String a1, String a2, String a3, String a4, String a5, String a6) {
        return "cs:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6;
    }

    public Wide6 same(String a1, String a2, String a3, String a4, String a5, String a6) {
        return this;
    }

    public void put(String a1, String a2, String a3, String a4, String a5, String a6) {
        told += "put:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + ";";
    }

    public String inh(String a1, String a2, String a3, String a4, String a5, String a6) {
        return "inh:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6;
    }

    public void inhVoid(String a1, String a2, String a3, String a4, String a5, String a6) {
        told += "inhVoid:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + ";";
    }

    public static String join(String a1, String a2, String a3, String a4, String a5, String a6) {
        return "join:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6;
    }

    public static void note(String a1, String a2, String a3, String a4, String a5, String a6) {
        noted += "note:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + ";";
    }

    public String pickAll(String a1, String a2, String a3, String a4, String a5, String... rest) {
        return "pickAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "|" + Arrays.toString(rest);
    }

    public void putAll(String a1, String a2, String a3, String a4, String a5, String... rest) {
        told += "putAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "|" + Arrays.toString(rest) + ";";
    }

    public static String joinAll(String a1, String a2, String a3, String a4, String a5, String... rest) {
        return "joinAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "|" + Arrays.toString(rest);
    }

    public static void noteAll(String a1, String a2, String a3, String a4, String a5, String... rest) {
        noted += "noteAll:" + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "|" + Arrays.toString(rest) + ";";
    }
}
