package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef2;

/// Facts of `java.lang.Math`.
public final class Math_ {
    /// `Math`.
    public static final FinalClassToken<Math> TOKEN = Jdk.finalClass(Math.class);

    /// `static int max(int, int)`.
    public static final StaticMethodRef2<Integer, Integer, Integer> max_int_int = StaticMethodRef2.introduce(
            TOKEN, "max", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, Jdk.FINAL);

    /// `static int abs(int)`.
    public static final StaticMethodRef1<Integer, Integer> abs_int =
            StaticMethodRef1.introduce(TOKEN, "abs", PrimitiveToken.INT, PrimitiveToken.INT, Jdk.FINAL);

    /// `static int toIntExact(long)`: the checked narrowing of `long` to `int`.
    public static final StaticMethodRef1<Integer, Long> toIntExact =
            StaticMethodRef1.introduce(TOKEN, "toIntExact", PrimitiveToken.INT, PrimitiveToken.LONG, Jdk.FINAL);

    private Math_() {}
}
