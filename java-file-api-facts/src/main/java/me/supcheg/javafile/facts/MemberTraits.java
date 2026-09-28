package me.supcheg.javafile.facts;

import java.lang.constant.ConstantDescs;
import java.util.List;

/// Runtime data of a method or constructor fact that generics cannot carry.
///
/// - The throws-set (§3.7): Java has no union types to put checked exceptions
///   into a signature, so lowering checks exception coverage from this list.
/// - Overridability, for `override` and the completeness checks.
/// - Explicit type arguments of a generic method (§3.4): the witnesses the
///   fact was instantiated with, rendered as `recv.<A, B>m(...)` so that the
///   compiler never infers something else.
///
/// @param throwsTypes the exception types in the `throws` clause
/// @param overridability whether the method can be overridden
/// @param typeArgs the explicit type arguments of a generic method; empty for none
public record MemberTraits(
        List<ClassToken<? extends Throwable>> throwsTypes, Overridability overridability, List<RefToken<?>> typeArgs) {

    /// No `throws` clause, overridable, not generic.
    public static final MemberTraits DEFAULT = new MemberTraits(List.of(), Overridability.OVERRIDABLE, List.of());

    public MemberTraits {
        throwsTypes = List.copyOf(throwsTypes);
        typeArgs = List.copyOf(typeArgs);
        for (ClassToken<? extends Throwable> type : throwsTypes) {
            if (!type.isSubclassOf(ConstantDescs.CD_Throwable)) {
                throw new IllegalArgumentException("not a Throwable: " + type);
            }
        }
    }

    /// Returns these traits with a `throws` clause.
    ///
    /// @param types the thrown exception types
    /// @return the new traits
    @SafeVarargs
    @SuppressWarnings("varargs")
    public final MemberTraits throwing(ClassToken<? extends Throwable>... types) {
        return new MemberTraits(List.of(types), overridability, typeArgs);
    }

    /// Returns these traits with the given overridability.
    ///
    /// @param overridability the overridability
    /// @return the new traits
    public MemberTraits with(Overridability overridability) {
        return new MemberTraits(throwsTypes, overridability, typeArgs);
    }

    /// Returns these traits with explicit type arguments.
    ///
    /// @param typeArgs the type arguments, in order
    /// @return the new traits
    public MemberTraits withTypeArgs(RefToken<?>... typeArgs) {
        return new MemberTraits(throwsTypes, overridability, List.of(typeArgs));
    }
}
