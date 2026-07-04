package me.supcheg.javafile.code;

/// A switch case body that is a block of statements.
///
/// @param body the case's statements
public record BlockCaseBody(CodeBody body) implements CaseBody {}
