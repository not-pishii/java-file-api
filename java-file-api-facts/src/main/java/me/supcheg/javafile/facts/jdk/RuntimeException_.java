package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.CtorRef0;
import me.supcheg.javafile.facts.CtorRef1;
import me.supcheg.javafile.facts.OpenClassToken;

/// Facts of `java.lang.RuntimeException`.
public final class RuntimeException_ {
    /// `RuntimeException`.
    public static final OpenClassToken<RuntimeException> TOKEN = Jdk.openClass(RuntimeException.class);

    /// `new RuntimeException()`.
    public static final CtorRef0<RuntimeException> new_ = CtorRef0.introduce(TOKEN, Jdk.FINAL);

    /// `new RuntimeException(String)`.
    public static final CtorRef1<RuntimeException, String> new_String =
            CtorRef1.introduce(TOKEN, String_.TOKEN, Jdk.FINAL);

    private RuntimeException_() {}
}
