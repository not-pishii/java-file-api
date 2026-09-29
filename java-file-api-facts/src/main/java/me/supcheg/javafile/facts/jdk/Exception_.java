package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;

/// Facts of `java.lang.Exception`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class Exception_ {
    /// `Exception`.
    public static final OpenClassToken<Exception> TOKEN = Jdk.openClass(Exception.class);

    /// `new Exception()`.
    public static final CtorRef0<Exception> new_ = UnsafeFacts.ctor(TOKEN, MemberTraits.FINAL);

    /// `new Exception(String)`.
    public static final CtorRef1<Exception, String> new_String =
            UnsafeFacts.ctor(TOKEN, String_.TOKEN, MemberTraits.FINAL);

    private Exception_() {}
}
