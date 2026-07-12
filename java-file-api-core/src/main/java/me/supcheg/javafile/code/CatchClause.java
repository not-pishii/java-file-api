package me.supcheg.javafile.code;

import me.supcheg.javafile.type.ClassOrInterfaceTypeRef;

/// A `catch` clause attached to a [TryStmt].
///
/// @param exceptionTypes the caught exception types; more than one models a
/// multi-catch (`A | B`)
/// @param paramName the caught exception's parameter name
/// @param body the clause's body
public record CatchClause(NonEmptyList<ClassOrInterfaceTypeRef> exceptionTypes, String paramName, CodeBody body) {}
