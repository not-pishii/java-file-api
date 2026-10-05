package me.supcheg.javafile.facts;

import java.lang.constant.ConstantDescs;
import java.util.List;

/// Runtime data of a method or constructor fact that generics cannot carry.
///
/// - The throws-set (§3.7): Java has no union types to put checked exceptions
///   into a signature, so the typed layer checks from this list, where a call
///   of the member is built, that each checked exception is caught or
///   declared. Which of the types are checked their tokens tell
///   ([ClassToken#isCheckedException()]).
/// - Overridability, for `override` and the completeness checks.
/// - Explicit type arguments of a generic method (§3.4): the witnesses the
///   fact was instantiated with, rendered as `recv.<A, B>m(...)` so that the
///   compiler never infers something else.
///
/// @param throwsTypes the exception types in the `throws` clause: classes, or type variables bounded by `Throwable`
/// @param overridability whether the method can be overridden
/// @param typeArgs the explicit type arguments of a generic method; empty for none
public record MemberTraits(
        List<RefToken<? extends Throwable>> throwsTypes, Overridability overridability, List<RefToken<?>> typeArgs) {

    /// No `throws` clause, overridable, not generic.
    public static final MemberTraits DEFAULT = new MemberTraits(List.of(), Overridability.OVERRIDABLE, List.of());

    /// No `throws` clause, overridable, not generic; the same as [#DEFAULT].
    public static final MemberTraits OVERRIDABLE = DEFAULT;

    /// No `throws` clause, `final` or `static`, not generic.
    public static final MemberTraits FINAL = DEFAULT.with(Overridability.FINAL);

    /// No `throws` clause, `abstract`, not generic.
    public static final MemberTraits ABSTRACT = DEFAULT.with(Overridability.ABSTRACT);

    public MemberTraits {
        throwsTypes = List.copyOf(throwsTypes);
        typeArgs = List.copyOf(typeArgs);
        for (RefToken<? extends Throwable> type : throwsTypes) {
            if (type instanceof ClassToken<?> cls && !cls.isSubclassOf(ConstantDescs.CD_Throwable)) {
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
    public final MemberTraits throwing(RefToken<? extends Throwable>... types) {
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
