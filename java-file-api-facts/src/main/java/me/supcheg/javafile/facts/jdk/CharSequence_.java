package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;

/// Facts of `java.lang.CharSequence`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class CharSequence_ {
    /// `CharSequence`.
    public static final InterfaceToken<CharSequence> TOKEN = Jdk.iface(CharSequence.class);

    /// `int length()`.
    public static final MethodRef0<CharSequence, Prim.Int> length =
            UnsafeFacts.method(TOKEN, "length", PrimitiveToken.INT, MemberTraits.ABSTRACT);

    private CharSequence_() {}
}
