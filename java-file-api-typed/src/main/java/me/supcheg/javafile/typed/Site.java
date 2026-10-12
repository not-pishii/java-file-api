package me.supcheg.javafile.typed;

import java.security.CodeSource;
import java.util.Objects;

/// The place in a generator where a thing was said: the frame of the code
/// that called the typed layer.
///
/// What is wrong is rejected where it is said, and the stack of the
/// rejection is that place. What cannot be judged until the declaration
/// ends — a member declared and never defined — is rejected there, and tells
/// where the member was declared: by its message, and by its cause, an
/// exception whose stack is this place.
///
/// @param at the frame of the generator
record Site(StackTraceElement at) {
    private static final String PACKAGE = Site.class.getPackageName();
    private static final CodeSource OWN = Site.class.getProtectionDomain().getCodeSource();
    private static final StackWalker WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    /// The place the typed layer was called from: the innermost frame that is none of its own.
    ///
    /// @return the place
    static Site here() {
        return new Site(WALKER.walk(frames -> frames.filter(frame -> !own(frame.getDeclaringClass()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("the typed layer is called by code of its own alone"))
                .toStackTraceElement()));
    }

    /// Whether a class is of the typed layer: of its packages and of the code it was loaded with —
    /// a generator in a package of the same name is none.
    private static boolean own(Class<?> type) {
        return type.getPackageName().startsWith(PACKAGE)
                && Objects.equals(type.getProtectionDomain().getCodeSource(), OWN);
    }

    /// An exception for the cause of a rejection made elsewhere, whose stack is this place.
    ///
    /// @param said what was said here
    /// @return the exception
    IllegalStateException asCause(String said) {
        IllegalStateException cause = new IllegalStateException(said + " here");
        cause.setStackTrace(new StackTraceElement[] {at});
        return cause;
    }

    @Override
    public String toString() {
        return at.toString();
    }
}
