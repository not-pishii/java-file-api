package me.supcheg.javafile.typed.fixtures;

import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public class Wide3 {
    public static String noted = "";

    public String told = "";

    public Wide3(String a1, String a2, String a3) {
        this.told = "new:" + a1 + "," + a2 + "," + a3;
    }

    public Wide3(String a1, String a2, Integer... rest) {
        this.told = "newAll:" + a1 + "," + a2 + "|" + Arrays.toString(rest);
    }

    public String pick(String a1, String a2, String a3) {
        return "pick:" + a1 + "," + a2 + "," + a3;
    }

    public CharSequence cs(String a1, String a2, String a3) {
        return "cs:" + a1 + "," + a2 + "," + a3;
    }

    public Wide3 same(String a1, String a2, String a3) {
        return this;
    }

    public void put(String a1, String a2, String a3) {
        told += "put:" + a1 + "," + a2 + "," + a3 + ";";
    }

    public String inh(String a1, String a2, String a3) {
        return "inh:" + a1 + "," + a2 + "," + a3;
    }

    public void inhVoid(String a1, String a2, String a3) {
        told += "inhVoid:" + a1 + "," + a2 + "," + a3 + ";";
    }

    public static String join(String a1, String a2, String a3) {
        return "join:" + a1 + "," + a2 + "," + a3;
    }

    public static void note(String a1, String a2, String a3) {
        noted += "note:" + a1 + "," + a2 + "," + a3 + ";";
    }

    public String pickAll(String a1, String a2, String... rest) {
        return "pickAll:" + a1 + "," + a2 + "|" + Arrays.toString(rest);
    }

    public void putAll(String a1, String a2, String... rest) {
        told += "putAll:" + a1 + "," + a2 + "|" + Arrays.toString(rest) + ";";
    }

    public static String joinAll(String a1, String a2, String... rest) {
        return "joinAll:" + a1 + "," + a2 + "|" + Arrays.toString(rest);
    }

    public static void noteAll(String a1, String a2, String... rest) {
        noted += "noteAll:" + a1 + "," + a2 + "|" + Arrays.toString(rest) + ";";
    }
}
