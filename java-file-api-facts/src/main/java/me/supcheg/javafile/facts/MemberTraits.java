package me.supcheg.javafile.facts;

import me.supcheg.javafile.annotation.AnnotationUse;

import java.lang.constant.ConstantDescs;
import java.util.List;

/// Runtime data of a method or constructor fact that generics cannot carry.
///
/// - The throws-set (§3.7): Java has no union types to put checked exceptions
///   into a signature, so the typed layer checks from this list, where a call
///   of the member is built, that each checked exception is caught or
///   declared. The token of a class tells whether it is checked
///   ([ClassToken#isCheckedException()]); a type variable is known by the
///   class of its bound alone.
/// - Overridability, for `override` and the completeness checks.
/// - The access (JLS 6.6): a `protected` member is used in a subclass alone.
/// - Explicit type arguments of a generic method (§3.4): the witnesses the
///   fact was instantiated with, rendered as `recv.<A, B>m(...)` so that the
///   compiler never infers something else.
///
/// @param throwsTypes the exception types in the `throws` clause: classes, or type variables bounded by `Throwable`
/// @param overridability whether the method can be overridden
/// @param typeArgs the explicit type arguments of a generic method; empty for none
/// @param access the access of the member
/// @param arity whether the last parameter of the method or constructor is of variable arity, and whether
///              its declaration is `@SafeVarargs`
/// @param annotations the annotations the member declares, in declaration order
/// @param parameterAnnotations the annotations of each parameter, in parameter order; an empty list for a
///                             parameter without one
public record MemberTraits(
        List<RefToken<? extends Throwable>> throwsTypes,
        Overridability overridability,
        List<RefToken<?>> typeArgs,
        Access access,
        Arity arity,
        List<AnnotationUse> annotations,
        List<List<AnnotationUse>> parameterAnnotations) {

    /// No `throws` clause, overridable, not generic, `public`, of fixed arity.
    public static final MemberTraits DEFAULT = new MemberTraits(
            List.of(), Overridability.OVERRIDABLE, List.of(), Access.PUBLIC, Arity.FIXED, List.of(), List.of());

    /// The traits of a member that declares no annotation.
    ///
    /// @param throwsTypes the exception types in the `throws` clause
    /// @param overridability whether the method can be overridden
    /// @param typeArgs the explicit type arguments of a generic method
    /// @param access the access of the member
    /// @param arity whether the last parameter is of variable arity
    public MemberTraits(
            List<RefToken<? extends Throwable>> throwsTypes,
            Overridability overridability,
            List<RefToken<?>> typeArgs,
            Access access,
            Arity arity) {
        this(throwsTypes, overridability, typeArgs, access, arity, List.of(), List.of());
    }

    /// No `throws` clause, overridable, not generic, `public`; the same as [#DEFAULT].
    public static final MemberTraits OVERRIDABLE = DEFAULT;

    /// No `throws` clause, `final` or `static`, not generic, `public`.
    public static final MemberTraits FINAL = DEFAULT.with(Overridability.FINAL);

    /// No `throws` clause, `abstract`, not generic, `public`.
    public static final MemberTraits ABSTRACT = DEFAULT.with(Overridability.ABSTRACT);

    public MemberTraits {
        throwsTypes = List.copyOf(throwsTypes);
        typeArgs = List.copyOf(typeArgs);
        annotations = List.copyOf(annotations);
        parameterAnnotations = parameterAnnotations.stream().map(List::copyOf).toList();
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
        return new MemberTraits(
                List.of(types), overridability, typeArgs, access, arity, annotations, parameterAnnotations);
    }

    /// Returns these traits with the given overridability.
    ///
    /// @param overridability the overridability
    /// @return the new traits
    public MemberTraits with(Overridability overridability) {
        return new MemberTraits(
                throwsTypes, overridability, typeArgs, access, arity, annotations, parameterAnnotations);
    }

    /// Returns these traits with the given access.
    ///
    /// @param access the access
    /// @return the new traits
    public MemberTraits with(Access access) {
        return new MemberTraits(
                throwsTypes, overridability, typeArgs, access, arity, annotations, parameterAnnotations);
    }

    /// Returns these traits with the given arity.
    ///
    /// @param arity the arity
    /// @return the new traits
    public MemberTraits with(Arity arity) {
        return new MemberTraits(
                throwsTypes, overridability, typeArgs, access, arity, annotations, parameterAnnotations);
    }

    /// Returns these traits with explicit type arguments.
    ///
    /// @param typeArgs the type arguments, in order
    /// @return the new traits
    public MemberTraits withTypeArgs(RefToken<?>... typeArgs) {
        return new MemberTraits(
                throwsTypes, overridability, List.of(typeArgs), access, arity, annotations, parameterAnnotations);
    }

    /// Returns these traits with the annotations the member declares.
    ///
    /// @param annotations the annotations, in declaration order
    /// @return the new traits
    public MemberTraits annotated(AnnotationUse... annotations) {
        return new MemberTraits(
                throwsTypes, overridability, typeArgs, access, arity, List.of(annotations), parameterAnnotations);
    }

    /// Returns these traits with the annotations of each parameter.
    ///
    /// @param parameterAnnotations the annotations of each parameter, in order
    /// @return the new traits
    public MemberTraits annotatedParameters(List<List<AnnotationUse>> parameterAnnotations) {
        return new MemberTraits(
                throwsTypes, overridability, typeArgs, access, arity, annotations, parameterAnnotations);
    }
}
