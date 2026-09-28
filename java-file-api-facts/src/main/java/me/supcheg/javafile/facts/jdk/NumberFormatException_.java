package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.OpenClassToken;

/// Facts of `java.lang.NumberFormatException`.
public final class NumberFormatException_ {
    /// `NumberFormatException`.
    public static final OpenClassToken<NumberFormatException> TOKEN = Jdk.openClass(NumberFormatException.class);

    /// `new NumberFormatException()`.
    public static final CtorRef0<NumberFormatException> new_ = CtorRef0.introduce(TOKEN, Jdk.FINAL);

    /// `new NumberFormatException(String)`.
    public static final CtorRef1<NumberFormatException, String> new_String =
            CtorRef1.introduce(TOKEN, String_.TOKEN, Jdk.FINAL);

    private NumberFormatException_() {}
}
