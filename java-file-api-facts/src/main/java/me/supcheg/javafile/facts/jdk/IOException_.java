package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;
import java.io.IOException;

/// Facts of `java.io.IOException`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class IOException_ {
    /// `IOException`.
    public static final OpenClassToken<IOException> TOKEN = Jdk.openClass(IOException.class);

    /// `new IOException()`.
    public static final CtorRef0<IOException> new_ = UnsafeFacts.ctor(TOKEN, Jdk.FINAL);

    /// `new IOException(String)`.
    public static final CtorRef1<IOException, String> new_String = UnsafeFacts.ctor(TOKEN, String_.TOKEN, Jdk.FINAL);

    private IOException_() {}
}
