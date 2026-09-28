package me.supcheg.javafile.facts.jdk;

import me.supcheg.javafile.facts.FinalClassToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.RefToken;
import me.supcheg.javafile.facts.StaticMethodRef1;
import me.supcheg.javafile.facts.StaticMethodRef2;

import java.util.Objects;

/// Facts of `java.util.Objects`.
public final class Objects_ {
    /// `Objects`.
    public static final FinalClassToken<Objects> TOKEN = Jdk.finalClass(Objects.class);

    /// `static boolean equals(Object, Object)`.
    public static final StaticMethodRef2<Boolean, Object, Object> equals = StaticMethodRef2.introduce(
            TOKEN, "equals", PrimitiveToken.BOOLEAN, Object_.TOKEN, Object_.TOKEN, Jdk.FINAL);

    private Objects_() {}

    /// `static <T> T requireNonNull(T)`, with `T` as the explicit type
    /// argument (§3.4).
    ///
    /// @param type the witness for `T`
    /// @param <T> the checked type
    /// @return the method fact
    public static <T> StaticMethodRef1<T, T> requireNonNull(RefToken<T> type) {
        return StaticMethodRef1.introduce(TOKEN, "requireNonNull", type, type, Jdk.FINAL.withTypeArgs(type));
    }
}
