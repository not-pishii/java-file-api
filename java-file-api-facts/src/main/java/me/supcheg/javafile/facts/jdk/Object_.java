package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.Prim;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;

import javax.annotation.processing.Generated;

/// Facts of `java.lang.Object`.
@Generated(value = "hand-written", comments = "stand-in for the output of the @Facts processor (§5)")
public final class Object_ {
    /// `Object`.
    public static final OpenClassToken<Object> TOKEN = Jdk.openClass(Object.class);

    /// `new Object()`.
    public static final CtorRef0<Object> new_ = UnsafeFacts.ctor(TOKEN, Jdk.FINAL);

    /// `String toString()`.
    public static final MethodRef0<Object, String> toString =
            UnsafeFacts.method(TOKEN, "toString", String_.TOKEN, Jdk.OVERRIDABLE);

    /// `int hashCode()`.
    public static final MethodRef0<Object, Prim.Int> hashCode =
            UnsafeFacts.method(TOKEN, "hashCode", PrimitiveToken.INT, Jdk.OVERRIDABLE);

    /// `boolean equals(Object)`.
    public static final MethodRef1<Object, Prim.Bool, Object> equals =
            UnsafeFacts.method(TOKEN, "equals", PrimitiveToken.BOOLEAN, TOKEN, Jdk.OVERRIDABLE);

    private Object_() {}
}
