package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.PrimitiveToken;

/// Facts of `java.lang.StringBuilder`.
public final class StringBuilder_ {
    /// `StringBuilder`.
    public static final FinalClassToken<StringBuilder> TOKEN = Jdk.finalClass(StringBuilder.class);

    /// `new StringBuilder()`.
    public static final CtorRef0<StringBuilder> new_ = CtorRef0.introduce(TOKEN, Jdk.FINAL);

    /// `StringBuilder append(String)`.
    public static final MethodRef1<StringBuilder, StringBuilder, String> append_String =
            MethodRef1.introduce(TOKEN, "append", TOKEN, String_.TOKEN, Jdk.FINAL);

    /// `StringBuilder append(int)`.
    public static final MethodRef1<StringBuilder, StringBuilder, Integer> append_int =
            MethodRef1.introduce(TOKEN, "append", TOKEN, PrimitiveToken.INT, Jdk.FINAL);

    /// `String toString()`.
    public static final MethodRef0<StringBuilder, String> toString =
            MethodRef0.introduce(TOKEN, "toString", String_.TOKEN, Jdk.FINAL);

    private StringBuilder_() {}
}
