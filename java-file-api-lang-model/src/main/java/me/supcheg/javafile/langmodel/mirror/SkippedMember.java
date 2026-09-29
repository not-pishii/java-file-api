package me.supcheg.javafile.langmodel.mirror;

/// A `public` member a [MemberFilter] selected that has no [MemberModel]:
/// its signature mentions a type that is not `public` or cannot be
/// expressed, or it has more than [MemberModel#MAX_ARITY] parameters. A
/// metamodel makes no fact of it and warns.
///
/// @param member the member, such as `method greet(p.Hidden)`
/// @param reason why it is skipped, for a diagnostic
public record SkippedMember(String member, String reason) {}
