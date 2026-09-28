package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.MethodRef0;
import me.supcheg.javafile.facts.MethodRef1;
import me.supcheg.javafile.facts.OpenClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;

/// Facts of `java.lang.Object`.
public final class Object_ {
    /// `Object`.
    public static final OpenClassToken<Object> TOKEN = Jdk.openClass(Object.class);

    /// `new Object()`.
    public static final CtorRef0<Object> new_ = CtorRef0.introduce(TOKEN, Jdk.FINAL);

    /// `String toString()`.
    public static final MethodRef0<Object, String> toString =
            MethodRef0.introduce(TOKEN, "toString", String_.TOKEN, Jdk.OVERRIDABLE);

    /// `int hashCode()`.
    public static final MethodRef0<Object, Integer> hashCode =
            MethodRef0.introduce(TOKEN, "hashCode", PrimitiveToken.INT, Jdk.OVERRIDABLE);

    /// `boolean equals(Object)`.
    public static final MethodRef1<Object, Boolean, Object> equals =
            MethodRef1.introduce(TOKEN, "equals", PrimitiveToken.BOOLEAN, TOKEN, Jdk.OVERRIDABLE);

    private Object_() {}
}
