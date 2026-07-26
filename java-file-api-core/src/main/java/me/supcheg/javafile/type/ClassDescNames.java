package me.supcheg.javafile.type;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.regex.Pattern;

/// Recovers a [ClassDesc]'s nesting chain of simple names from its binary
/// display name.
///
/// `ClassDesc` has no API that returns a nested type's chain of simple
/// names directly — only [ClassDesc#displayName()], which joins them with
/// `$` (e.g. `"Outer$Inner"`), the JVM binary-name separator, not valid Java
/// source syntax. Splitting on `$` is the only practical way to recover the
/// chain.
///
/// This is a deliberately accepted limitation, shared with other code
/// generators (e.g. JavaPoet): a top-level class whose own simple name
/// contains a literal `$` (legal but strongly discouraged) is
/// indistinguishable from a nested type and will be split incorrectly.
public final class ClassDescNames {
    private static final Pattern DOLLAR_SIGN = Pattern.compile("\\$");

    private ClassDescNames() {}

    /// Splits `desc`'s display name into its nesting chain of simple names.
    ///
    /// @param desc the type descriptor to inspect
    /// @return the chain from the outermost enclosing type to `desc` itself;
    ///         a single-element list for a top-level type
    public static List<String> nestingChain(ClassDesc desc) {
        return List.of(DOLLAR_SIGN.split(desc.displayName()));
    }

    /// The last segment of [#nestingChain(ClassDesc)] — the type's own
    /// simple name, without any enclosing type's name.
    ///
    /// @param desc the type descriptor to inspect
    /// @return the leaf simple name
    public static String leafSimpleName(ClassDesc desc) {
        return nestingChain(desc).getLast();
    }

    /// [#nestingChain(ClassDesc)] joined with `.`, without the package name
    /// — valid Java syntax for referencing `desc` from within its own
    /// package (or after importing its outermost enclosing type).
    ///
    /// @param desc the type descriptor to inspect
    /// @return the dot-joined nesting chain, e.g. `"Outer.Inner"`
    public static String qualifiedByDots(ClassDesc desc) {
        return String.join(".", nestingChain(desc));
    }
}
