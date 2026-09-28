package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidMethodRef1;

import javax.annotation.processing.Generated;
import java.io.PrintStream;

/// Facts of `java.io.PrintStream`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class PrintStream_ {
    /// `PrintStream`.
    public static final OpenClassToken<PrintStream> TOKEN = Jdk.openClass(PrintStream.class);

    /// `void println()`.
    public static final VoidMethodRef0<PrintStream> println = UnsafeFacts.voidMethod(TOKEN, "println", Jdk.OVERRIDABLE);

    /// `void println(String)`.
    public static final VoidMethodRef1<PrintStream, String> println_String =
            UnsafeFacts.voidMethod(TOKEN, "println", String_.TOKEN, Jdk.OVERRIDABLE);

    /// `void println(int)`.
    public static final VoidMethodRef1<PrintStream, Prim.Int> println_int =
            UnsafeFacts.voidMethod(TOKEN, "println", PrimitiveToken.INT, Jdk.OVERRIDABLE);

    private PrintStream_() {}
}
