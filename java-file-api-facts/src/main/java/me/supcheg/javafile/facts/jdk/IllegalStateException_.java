package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.OpenClassToken;

/// Facts of `java.lang.IllegalStateException`.
public final class IllegalStateException_ {
    /// `IllegalStateException`.
    public static final OpenClassToken<IllegalStateException> TOKEN = Jdk.openClass(IllegalStateException.class);

    /// `new IllegalStateException()`.
    public static final CtorRef0<IllegalStateException> new_ = CtorRef0.introduce(TOKEN, Jdk.FINAL);

    /// `new IllegalStateException(String)`.
    public static final CtorRef1<IllegalStateException, String> new_String =
            CtorRef1.introduce(TOKEN, String_.TOKEN, Jdk.FINAL);

    private IllegalStateException_() {}
}
