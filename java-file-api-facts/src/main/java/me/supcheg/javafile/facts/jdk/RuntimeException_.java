package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;

/// Facts of `java.lang.RuntimeException`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class RuntimeException_ {
    /// `RuntimeException`.
    public static final OpenClassToken<RuntimeException> TOKEN = Jdk.openClass(RuntimeException.class);

    /// `new RuntimeException()`.
    public static final CtorRef0<RuntimeException> new_ = UnsafeFacts.ctor(TOKEN, Jdk.FINAL);

    /// `new RuntimeException(String)`.
    public static final CtorRef1<RuntimeException, String> new_String =
            UnsafeFacts.ctor(TOKEN, String_.TOKEN, Jdk.FINAL);

    private RuntimeException_() {}
}
