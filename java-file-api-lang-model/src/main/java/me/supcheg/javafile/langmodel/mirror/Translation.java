package me.supcheg.javafile.langmodel.mirror;

/// The result of translating a `javax.lang.model` mirror with
/// [MirrorTranslator]: the translation, or why there is none. It is a value,
/// not an exception, so a caller handles all three cases with an exhaustive
/// `switch`.
///
/// When a mirror is both unresolved and unrepresentable, [Deferred] wins:
/// the missing type may be generated in a later round, and only then is it
/// known what the mirror is.
///
/// @param <T> the type of the translation
public sealed interface Translation<T> permits Translation.Ok, Translation.Deferred, Translation.Unrepresentable {

    /// The mirror is translated.
    ///
    /// @param value the translation
    /// @param <T> the type of the translation
    record Ok<T>(T value) implements Translation<T> {}

    /// The mirror mentions a type that does not exist yet (`TypeKind.ERROR`),
    /// such as a type another annotation processor generates in this round;
    /// try again in the next round.
    ///
    /// @param unresolved the unresolved type, as javac names it
    /// @param <T> the type of the translation
    record Deferred<T>(String unresolved) implements Translation<T> {}

    /// The mirror cannot be expressed in the model of generated code: a
    /// member class of a parameterized type (`Outer<T>.Inner`), an
    /// annotation interface, a type that is not a type (`void`, a package,
    /// …).
    ///
    /// @param reason why, for a diagnostic
    /// @param <T> the type of the translation
    record Unrepresentable<T>(String reason) implements Translation<T> {}
}
