package me.supcheg.javafile.typed.fixtures;

import java.util.Arrays;
import javax.annotation.processing.Generated;

@Generated("me.supcheg.javafile.typed.codegen.TypedCodegen")
public class Wide1 {
    public static String noted = "";

    public String told = "";

    public Wide1(String a1) {
        this.told = "new:" + a1;
    }

    public Wide1(Integer... rest) {
        this.told = "newAll:" + "|" + Arrays.toString(rest);
    }

    public String pick(String a1) {
        return "pick:" + a1;
    }

    public CharSequence cs(String a1) {
        return "cs:" + a1;
    }

    public Wide1 same(String a1) {
        return this;
    }

    public void put(String a1) {
        told += "put:" + a1 + ";";
    }

    public String inh(String a1) {
        return "inh:" + a1;
    }

    public void inhVoid(String a1) {
        told += "inhVoid:" + a1 + ";";
    }

    public static String join(String a1) {
        return "join:" + a1;
    }

    public static void note(String a1) {
        noted += "note:" + a1 + ";";
    }

    public String pickAll(String... rest) {
        return "pickAll:" + "|" + Arrays.toString(rest);
    }

    public void putAll(String... rest) {
        told += "putAll:" + "|" + Arrays.toString(rest) + ";";
    }

    public static String joinAll(String... rest) {
        return "joinAll:" + "|" + Arrays.toString(rest);
    }

    public static void noteAll(String... rest) {
        noted += "noteAll:" + "|" + Arrays.toString(rest) + ";";
    }
}
