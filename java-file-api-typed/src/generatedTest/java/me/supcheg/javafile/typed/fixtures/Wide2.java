package me.supcheg.javafile.typed.fixtures;

import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public class Wide2 {
    public static String noted = "";

    public String told = "";

    public Wide2(String a1, String a2) {
        this.told = "new:" + a1 + "," + a2;
    }

    public Wide2(String a1, Integer... rest) {
        this.told = "newAll:" + a1 + "|" + Arrays.toString(rest);
    }

    public String pick(String a1, String a2) {
        return "pick:" + a1 + "," + a2;
    }

    public CharSequence cs(String a1, String a2) {
        return "cs:" + a1 + "," + a2;
    }

    public Wide2 same(String a1, String a2) {
        return this;
    }

    public void put(String a1, String a2) {
        told += "put:" + a1 + "," + a2 + ";";
    }

    public String inh(String a1, String a2) {
        return "inh:" + a1 + "," + a2;
    }

    public void inhVoid(String a1, String a2) {
        told += "inhVoid:" + a1 + "," + a2 + ";";
    }

    public static String join(String a1, String a2) {
        return "join:" + a1 + "," + a2;
    }

    public static void note(String a1, String a2) {
        noted += "note:" + a1 + "," + a2 + ";";
    }

    public String pickAll(String a1, String... rest) {
        return "pickAll:" + a1 + "|" + Arrays.toString(rest);
    }

    public void putAll(String a1, String... rest) {
        told += "putAll:" + a1 + "|" + Arrays.toString(rest) + ";";
    }

    public static String joinAll(String a1, String... rest) {
        return "joinAll:" + a1 + "|" + Arrays.toString(rest);
    }

    public static void noteAll(String a1, String... rest) {
        noted += "noteAll:" + a1 + "|" + Arrays.toString(rest) + ";";
    }
}
