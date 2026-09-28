package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.OpenClassToken;

/// Facts of `java.lang.Exception`.
public final class Exception_ {
    /// `Exception`.
    public static final OpenClassToken<Exception> TOKEN = Jdk.openClass(Exception.class);

    /// `new Exception()`.
    public static final CtorRef0<Exception> new_ = CtorRef0.introduce(TOKEN, Jdk.FINAL);

    /// `new Exception(String)`.
    public static final CtorRef1<Exception, String> new_String = CtorRef1.introduce(TOKEN, String_.TOKEN, Jdk.FINAL);

    private Exception_() {}
}
