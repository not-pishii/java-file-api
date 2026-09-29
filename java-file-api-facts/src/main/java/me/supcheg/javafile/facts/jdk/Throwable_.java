package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;

/// Facts of `java.lang.Throwable`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class Throwable_ {
    /// `Throwable`.
    public static final OpenClassToken<Throwable> TOKEN = Jdk.openClass(Throwable.class);

    /// `String getMessage()`.
    public static final MethodRef0<Throwable, String> getMessage =
            UnsafeFacts.method(TOKEN, "getMessage", String_.TOKEN, MemberTraits.OVERRIDABLE);

    private Throwable_() {}
}
