package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.OpenClassToken;

/// Facts of `java.lang.IllegalArgumentException`.
public final class IllegalArgumentException_ {
    /// `IllegalArgumentException`.
    public static final OpenClassToken<IllegalArgumentException> TOKEN = Jdk.openClass(IllegalArgumentException.class);

    /// `new IllegalArgumentException()`.
    public static final CtorRef0<IllegalArgumentException> new_ = CtorRef0.introduce(TOKEN, Jdk.FINAL);

    /// `new IllegalArgumentException(String)`.
    public static final CtorRef1<IllegalArgumentException, String> new_String =
            CtorRef1.introduce(TOKEN, String_.TOKEN, Jdk.FINAL);

    private IllegalArgumentException_() {}
}
