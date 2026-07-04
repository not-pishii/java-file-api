package me.supcheg.javafile.code;

/// A switch case body that throws, e.g. `-> throw exception`.
///
/// @param exception the thrown expression
public record ThrowCaseBody(Expr exception) implements CaseBody {}
