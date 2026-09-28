package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.MethodRef2;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;

/// Facts of `java.lang.String`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class String_ {
    /// `String`.
    public static final FinalClassToken<String> TOKEN = Jdk.finalClass(String.class);

    /// `int length()`.
    public static final MethodRef0<String, Prim.Int> length =
            UnsafeFacts.method(TOKEN, "length", PrimitiveToken.INT, Jdk.FINAL);

    /// `boolean isEmpty()`.
    public static final MethodRef0<String, Prim.Bool> isEmpty =
            UnsafeFacts.method(TOKEN, "isEmpty", PrimitiveToken.BOOLEAN, Jdk.FINAL);

    /// `char charAt(int)`.
    public static final MethodRef1<String, Prim.Char, Prim.Int> charAt =
            UnsafeFacts.method(TOKEN, "charAt", PrimitiveToken.CHAR, PrimitiveToken.INT, Jdk.FINAL);

    /// `String substring(int)`.
    public static final MethodRef1<String, String, Prim.Int> substring =
            UnsafeFacts.method(TOKEN, "substring", TOKEN, PrimitiveToken.INT, Jdk.FINAL);

    /// `String substring(int, int)`.
    public static final MethodRef2<String, String, Prim.Int, Prim.Int> substring_int_int =
            UnsafeFacts.method(TOKEN, "substring", TOKEN, PrimitiveToken.INT, PrimitiveToken.INT, Jdk.FINAL);

    /// `String concat(String)`.
    public static final MethodRef1<String, String, String> concat =
            UnsafeFacts.method(TOKEN, "concat", TOKEN, TOKEN, Jdk.FINAL);

    /// `String toUpperCase()`.
    public static final MethodRef0<String, String> toUpperCase =
            UnsafeFacts.method(TOKEN, "toUpperCase", TOKEN, Jdk.FINAL);

    /// `static String valueOf(int)`.
    public static final StaticMethodRef1<String, Prim.Int> valueOf_int =
            UnsafeFacts.staticMethod(TOKEN, "valueOf", TOKEN, PrimitiveToken.INT, Jdk.FINAL);

    private String_() {}
}
