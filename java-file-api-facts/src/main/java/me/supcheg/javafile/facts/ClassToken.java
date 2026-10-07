package me.supcheg.javafile.facts;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.List;

/// A token of a class type. Which subtype a token is decides what the class
/// can be used for: only [ConcreteClassToken]s are instantiated, only
/// [ExtendableClassToken]s — [OpenClassToken]s and [AbstractClassToken]s — are
/// extended.
///
/// @param <T> the Java type this token stands for
public sealed interface ClassToken<T> extends DeclaredToken<T>
        permits ExtendableClassToken, ConcreteClassToken, EnumToken {

    /// The erasures of the superclass chain, the direct superclass first and
    /// `java.lang.Object` last; empty only for `Object` itself.
    ///
    /// @return the superclass chain
    List<ClassDesc> superclasses();

    /// Whether the class is `type` or a subclass of it.
    ///
    /// @param type a class
    /// @return `true` if this class is `type` or extends it
    default boolean isSubclassOf(ClassDesc type) {
        return erasure().equals(type) || superclasses().contains(type);
    }

    /// Whether the class is a checked exception class (JLS 11.1.1): a
    /// `Throwable` that is neither a `RuntimeException` nor an `Error`.
    ///
    /// @return `true` for a checked exception class
    default boolean isCheckedException() {
        return isSubclassOf(ConstantDescs.CD_Throwable)
                && !isSubclassOf(ClassDesc.of("java.lang.RuntimeException"))
                && !isSubclassOf(ClassDesc.of("java.lang.Error"));
    }
}
