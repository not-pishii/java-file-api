package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.PrimitiveToken;

/// Facts of `java.lang.CharSequence`.
public final class CharSequence_ {
    /// `CharSequence`.
    public static final InterfaceToken<CharSequence> TOKEN = Jdk.iface(CharSequence.class);

    /// `int length()`.
    public static final MethodRef0<CharSequence, Integer> length =
            MethodRef0.introduce(TOKEN, "length", PrimitiveToken.INT, Jdk.ABSTRACT);

    private CharSequence_() {}
}
