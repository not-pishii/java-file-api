package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.OpenClassToken;

import java.io.IOException;

/// Facts of `java.io.IOException`.
public final class IOException_ {
    /// `IOException`.
    public static final OpenClassToken<IOException> TOKEN = Jdk.openClass(IOException.class);

    /// `new IOException()`.
    public static final CtorRef0<IOException> new_ = CtorRef0.introduce(TOKEN, Jdk.FINAL);

    /// `new IOException(String)`.
    public static final CtorRef1<IOException, String> new_String = CtorRef1.introduce(TOKEN, String_.TOKEN, Jdk.FINAL);

    private IOException_() {}
}
