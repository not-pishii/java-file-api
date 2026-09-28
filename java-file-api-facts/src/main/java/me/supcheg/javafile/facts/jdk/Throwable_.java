package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.OpenClassToken;

/// Facts of `java.lang.Throwable`.
public final class Throwable_ {
    /// `Throwable`.
    public static final OpenClassToken<Throwable> TOKEN = Jdk.openClass(Throwable.class);

    /// `String getMessage()`.
    public static final MethodRef0<Throwable, String> getMessage =
            MethodRef0.introduce(TOKEN, "getMessage", String_.TOKEN, Jdk.OVERRIDABLE);

    private Throwable_() {}
}
