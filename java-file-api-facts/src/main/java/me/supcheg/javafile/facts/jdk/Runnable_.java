package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.InterfaceToken;
import me.supcheg.javafile.facts.VoidMethodRef0;
import me.supcheg.javafile.facts.VoidSam0;

/// Facts of `java.lang.Runnable`.
public final class Runnable_ {
    /// `Runnable`.
    public static final InterfaceToken<Runnable> TOKEN = Jdk.iface(Runnable.class);

    /// `void run()`.
    public static final VoidMethodRef0<Runnable> run = VoidMethodRef0.introduce(TOKEN, "run", Jdk.ABSTRACT);

    /// The single abstract method, [#run].
    public static final VoidSam0<Runnable> sam = VoidSam0.introduce(run);

    private Runnable_() {}
}
