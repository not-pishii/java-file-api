package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;

/// Facts of `java.lang.IllegalStateException`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class IllegalStateException_ {
    /// `IllegalStateException`.
    public static final OpenClassToken<IllegalStateException> TOKEN = Jdk.openClass(IllegalStateException.class);

    /// `new IllegalStateException()`.
    public static final CtorRef0<IllegalStateException> new_ = UnsafeFacts.ctor(TOKEN, Jdk.FINAL);

    /// `new IllegalStateException(String)`.
    public static final CtorRef1<IllegalStateException, String> new_String =
            UnsafeFacts.ctor(TOKEN, String_.TOKEN, Jdk.FINAL);

    private IllegalStateException_() {}
}
