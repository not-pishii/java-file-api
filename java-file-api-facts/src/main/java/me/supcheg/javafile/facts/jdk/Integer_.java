package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;

/// Facts of `java.lang.Integer`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class Integer_ {
    /// `Integer`, the box of [PrimitiveToken#INT].
    public static final FinalClassToken<Integer> TOKEN = PrimitiveToken.INT.boxed();

    /// `static final int MAX_VALUE`, a constant variable.
    public static final StaticFieldRef<Prim.Int> MAX_VALUE =
            UnsafeFacts.constantField(TOKEN, "MAX_VALUE", PrimitiveToken.INT, Integer.MAX_VALUE);

    /// `static Integer valueOf(int)`.
    public static final StaticMethodRef1<Integer, Prim.Int> valueOf_int =
            UnsafeFacts.staticMethod(TOKEN, "valueOf", TOKEN, PrimitiveToken.INT, Jdk.FINAL);

    /// `static int parseInt(String) throws NumberFormatException`.
    public static final StaticMethodRef1<Prim.Int, String> parseInt = UnsafeFacts.staticMethod(
            TOKEN, "parseInt", PrimitiveToken.INT, String_.TOKEN, Jdk.FINAL.throwing(NumberFormatException_.TOKEN));

    /// `int intValue()`.
    public static final MethodRef0<Integer, Prim.Int> intValue =
            UnsafeFacts.method(TOKEN, "intValue", PrimitiveToken.INT, Jdk.FINAL);

    private Integer_() {}
}
