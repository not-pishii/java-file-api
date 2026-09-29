package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;

/// Facts of `java.lang.StringBuilder`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class StringBuilder_ {
    /// `StringBuilder`.
    public static final FinalClassToken<StringBuilder> TOKEN = Jdk.finalClass(StringBuilder.class);

    /// `new StringBuilder()`.
    public static final CtorRef0<StringBuilder> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// `StringBuilder append(String)`.
    public static final MethodRef1<StringBuilder, StringBuilder, String> append_String =
            UnsafeFacts.method(TOKEN, "append", TOKEN, String_.TOKEN, MemberTraits.FINAL);

    /// `StringBuilder append(int)`.
    public static final MethodRef1<StringBuilder, StringBuilder, Prim.Int> append_int =
            UnsafeFacts.method(TOKEN, "append", TOKEN, PrimitiveToken.INT, MemberTraits.FINAL);

    /// `String toString()`.
    public static final MethodRef0<StringBuilder, String> toString =
            UnsafeFacts.method(TOKEN, "toString", String_.TOKEN, MemberTraits.FINAL);

    private StringBuilder_() {}
}
