package me.supcheg.javafile.langmodel.mirror;

/// Which members [MirrorTranslator#type(javax.lang.model.element.TypeElement, MemberFilter)] translates into
/// [TypeModel#members()]: the members a metamodel makes facts of.
///
/// Whatever the filter, the rest of the [TypeModel] — the method table
/// included, which lists every method a call may resolve to — is complete.
public enum MemberFilter {
    /// No members: a token-only metamodel, which describes the type but
    /// makes no facts of its members.
    NONE,
    /// The fields, constructors and methods the type declares that code of
    /// another package can name — the `public` ones, and the `protected`
    /// ones of a class that can be extended, which a subclass reaches —,
    /// `static` ones included, but not those it inherits: a full metamodel,
    /// whose inherited members are reached through the metamodels of its
    /// supertypes. A supertype that is not `public` has no metamodel, so the
    /// such fields and methods the type inherits from it count as declared
    /// by the type, see
    /// [MirrorTranslator#members(javax.lang.model.element.TypeElement)].
    /// Enum constants are always in [TypeModel#enumConstants()].
    DECLARED_ACCESSIBLE
}
