package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef0;

import java.io.PrintStream;

/// Facts of `java.lang.System`.
public final class System_ {
    /// `System`.
    public static final FinalClassToken<System> TOKEN = Jdk.finalClass(System.class);

    /// `static final PrintStream out`.
    public static final StaticFieldRef<PrintStream> out = StaticFieldRef.introduce(TOKEN, "out", PrintStream_.TOKEN);

    /// `static long currentTimeMillis()`.
    public static final StaticMethodRef0<Long> currentTimeMillis =
            StaticMethodRef0.introduce(TOKEN, "currentTimeMillis", PrimitiveToken.LONG, Jdk.FINAL);

    private System_() {}
}
