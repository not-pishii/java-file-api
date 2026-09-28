package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;

/// Facts of `java.lang.NumberFormatException`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class NumberFormatException_ {
    /// `NumberFormatException`.
    public static final OpenClassToken<NumberFormatException> TOKEN = Jdk.openClass(NumberFormatException.class);

    /// `new NumberFormatException()`.
    public static final CtorRef0<NumberFormatException> new_ = UnsafeFacts.ctor(TOKEN, Jdk.FINAL);

    /// `new NumberFormatException(String)`.
    public static final CtorRef1<NumberFormatException, String> new_String =
            UnsafeFacts.ctor(TOKEN, String_.TOKEN, Jdk.FINAL);

    private NumberFormatException_() {}
}
