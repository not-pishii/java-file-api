package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.StaticFieldRef;
import me.supcheg.javafile.facts.StaticMethodRef0;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;
import java.io.PrintStream;

/// Facts of `java.lang.System`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class System_ {
    /// `System`.
    public static final FinalClassToken<System> TOKEN = Jdk.finalClass(System.class);

    /// `static final PrintStream out`.
    public static final StaticFieldRef<PrintStream> out = UnsafeFacts.staticField(TOKEN, "out", PrintStream_.TOKEN);

    /// `static long currentTimeMillis()`.
    public static final StaticMethodRef0<Prim.Long> currentTimeMillis =
            UnsafeFacts.staticMethod(TOKEN, "currentTimeMillis", PrimitiveToken.LONG, MemberTraits.FINAL);

    private System_() {}
}
