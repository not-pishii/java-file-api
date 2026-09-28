package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticMethodRef1;

/// Facts of `java.lang.String`.
public final class String_ {
    /// `String`.
    public static final FinalClassToken<String> TOKEN = Jdk.finalClass(String.class);

    /// `int length()`.
    public static final MethodRef0<String, Integer> length =
            MethodRef0.introduce(TOKEN, "length", PrimitiveToken.INT, Jdk.FINAL);

    /// `boolean isEmpty()`.
    public static final MethodRef0<String, Boolean> isEmpty =
            MethodRef0.introduce(TOKEN, "isEmpty", PrimitiveToken.BOOLEAN, Jdk.FINAL);

    /// `char charAt(int)`.
    public static final MethodRef1<String, Character, Integer> charAt =
            MethodRef1.introduce(TOKEN, "charAt", PrimitiveToken.CHAR, PrimitiveToken.INT, Jdk.FINAL);

    /// `String substring(int)`.
    public static final MethodRef1<String, String, Integer> substring =
            MethodRef1.introduce(TOKEN, "substring", TOKEN, PrimitiveToken.INT, Jdk.FINAL);

    /// `String substring(int, int)`.
    public static final MethodRef2<String, String, Integer, Integer> substring_int_int =
            MethodRef2.introduce(TOKEN, "substring", TOKEN, PrimitiveToken.INT, PrimitiveToken.INT, Jdk.FINAL);

    /// `String concat(String)`.
    public static final MethodRef1<String, String, String> concat =
            MethodRef1.introduce(TOKEN, "concat", TOKEN, TOKEN, Jdk.FINAL);

    /// `String toUpperCase()`.
    public static final MethodRef0<String, String> toUpperCase =
            MethodRef0.introduce(TOKEN, "toUpperCase", TOKEN, Jdk.FINAL);

    /// `static String valueOf(int)`.
    public static final StaticMethodRef1<String, Integer> valueOf_int =
            StaticMethodRef1.introduce(TOKEN, "valueOf", TOKEN, PrimitiveToken.INT, Jdk.FINAL);

    private String_() {}
}
