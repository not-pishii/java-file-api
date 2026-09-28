package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef1;

/// Facts of `java.lang.Integer`.
public final class Integer_ {
    /// `Integer`, the box of [PrimitiveToken#INT].
    public static final FinalClassToken<Integer> TOKEN = PrimitiveToken.INT.boxed();

    /// `static final int MAX_VALUE`, a constant variable.
    public static final StaticFieldRef<Integer> MAX_VALUE =
            StaticFieldRef.introduceConstant(TOKEN, "MAX_VALUE", PrimitiveToken.INT, Integer.MAX_VALUE);

    /// `static Integer valueOf(int)`.
    public static final StaticMethodRef1<Integer, Integer> valueOf_int =
            StaticMethodRef1.introduce(TOKEN, "valueOf", TOKEN, PrimitiveToken.INT, Jdk.FINAL);

    /// `static int parseInt(String) throws NumberFormatException`.
    public static final StaticMethodRef1<Integer, String> parseInt = StaticMethodRef1.introduce(
            TOKEN, "parseInt", PrimitiveToken.INT, String_.TOKEN, Jdk.FINAL.throwing(NumberFormatException_.TOKEN));

    /// `int intValue()`.
    public static final MethodRef0<Integer, Integer> intValue =
            MethodRef0.introduce(TOKEN, "intValue", PrimitiveToken.INT, Jdk.FINAL);

    private Integer_() {}
}
