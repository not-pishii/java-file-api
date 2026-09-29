package me.supcheg.javafile.langmodel.mirror;

/// A member of a [TypeModel] a metamodel makes a fact of: a method, a
/// constructor or a field. Types are as seen from the type itself, in terms
/// of its type parameters and those of the member.
///
/// Only members every type of whose signature is translatable, `public` and
/// within [#MAX_ARITY] become models; the others are [SkippedMember]s.
public sealed interface MemberModel permits MethodModel, CtorModel, FieldModel {

    /// The largest number of parameters of a method or constructor that has
    /// a fact: the arity families of `facts` end at 12.
    int MAX_ARITY = 12;
}
