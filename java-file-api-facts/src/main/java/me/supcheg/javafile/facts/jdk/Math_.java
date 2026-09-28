package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef2;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;

/// Facts of `java.lang.Math`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class Math_ {
    /// `Math`.
    public static final FinalClassToken<Math> TOKEN = Jdk.finalClass(Math.class);

    /// `static int max(int, int)`.
    public static final StaticMethodRef2<Prim.Int, Prim.Int, Prim.Int> max_int_int = UnsafeFacts.staticMethod(
            TOKEN, "max", PrimitiveToken.INT, PrimitiveToken.INT, PrimitiveToken.INT, Jdk.FINAL);

    /// `static int abs(int)`.
    public static final StaticMethodRef1<Prim.Int, Prim.Int> abs_int =
            UnsafeFacts.staticMethod(TOKEN, "abs", PrimitiveToken.INT, PrimitiveToken.INT, Jdk.FINAL);

    /// `static int toIntExact(long)`: the checked narrowing of `long` to `int`.
    public static final StaticMethodRef1<Prim.Int, Prim.Long> toIntExact =
            UnsafeFacts.staticMethod(TOKEN, "toIntExact", PrimitiveToken.INT, PrimitiveToken.LONG, Jdk.FINAL);

    private Math_() {}
}
