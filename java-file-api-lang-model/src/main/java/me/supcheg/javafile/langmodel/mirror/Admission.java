package me.supcheg.javafile.langmodel.mirror;

/// Whether a full metamodel can be made of a type that has a model
/// ([MirrorTranslator#rejection]): it can, it cannot, or it is not known yet.
public sealed interface Admission permits Admission.Admitted, Admission.Rejected, Admission.Deferred {
    /// A full metamodel can be made of the type.
    record Admitted() implements Admission {}

    /// No full metamodel can be made of the type.
    ///
    /// @param reason why, a sentence
    record Rejected(String reason) implements Admission {}

    /// A bound of the type parameters of the type mentions a type that does
    /// not exist yet; try again in the next round.
    ///
    /// @param unresolved the unresolved type, as javac names it
    record Deferred(String unresolved) implements Admission {}
}
