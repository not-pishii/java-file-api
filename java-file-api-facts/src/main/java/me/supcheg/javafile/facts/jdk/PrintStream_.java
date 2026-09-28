package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;

import java.io.PrintStream;

/// Facts of `java.io.PrintStream`.
public final class PrintStream_ {
    /// `PrintStream`.
    public static final OpenClassToken<PrintStream> TOKEN = Jdk.openClass(PrintStream.class);

    /// `void println()`.
    public static final VoidMethodRef0<PrintStream> println =
            VoidMethodRef0.introduce(TOKEN, "println", Jdk.OVERRIDABLE);

    /// `void println(String)`.
    public static final VoidMethodRef1<PrintStream, String> println_String =
            VoidMethodRef1.introduce(TOKEN, "println", String_.TOKEN, Jdk.OVERRIDABLE);

    /// `void println(int)`.
    public static final VoidMethodRef1<PrintStream, Integer> println_int =
            VoidMethodRef1.introduce(TOKEN, "println", PrimitiveToken.INT, Jdk.OVERRIDABLE);

    private PrintStream_() {}
}
