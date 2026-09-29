package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.MemberTraits;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidSam0;

import javax.annotation.processing.Generated;

/// Facts of `java.lang.Runnable`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class Runnable_ {
    /// `Runnable`.
    public static final InterfaceToken<Runnable> TOKEN = Jdk.iface(Runnable.class);

    /// `void run()`.
    public static final VoidMethodRef0<Runnable> run = UnsafeFacts.voidMethod(TOKEN, "run", MemberTraits.ABSTRACT);

    /// The single abstract method, [#run].
    public static final VoidSam0<Runnable> sam = UnsafeFacts.voidSam(run);

    private Runnable_() {}
}
