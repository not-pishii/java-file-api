package me.supcheg.javafile.type;

import java.lang.constant.ClassDesc;
import java.util.List;
import java.util.regex.Pattern;

/// Source-level names of nested types described by a [ClassDesc].
///
/// A nested type is described as `ClassDesc.of("com.example", "Outer$Inner")`;
/// these helpers turn that into `Outer.Inner` or `Inner`.
///
/// A class whose own name contains `$` is treated as nested, so avoid `$` in
/// generated type names.
public final class ClassDescNames {
    private static final Pattern DOLLAR_SIGN = Pattern.compile("\\$");

    private ClassDescNames() {}

    /// Returns the simple names from the outermost type down to `desc`,
    /// e.g. `[Outer, Inner]`.
    ///
    /// @param desc the type descriptor to inspect
    /// @return the chain from the outermost enclosing type to `desc` itself;
    ///         a single-element list for a top-level type
    public static List<String> nestingChain(ClassDesc desc) {
        return List.of(DOLLAR_SIGN.split(desc.displayName()));
    }

    /// Returns the type's own simple name, e.g. `Inner` for `Outer$Inner`.
    ///
    /// @param desc the type descriptor to inspect
    /// @return the leaf simple name
    public static String leafSimpleName(ClassDesc desc) {
        return nestingChain(desc).getLast();
    }

    /// Returns the name as written in source without the package,
    /// e.g. `Outer.Inner` for `Outer$Inner`.
    ///
    /// @param desc the type descriptor to inspect
    /// @return the dot-joined nesting chain, e.g. `"Outer.Inner"`
    public static String qualifiedByDots(ClassDesc desc) {
        return String.join(".", nestingChain(desc));
    }
}
