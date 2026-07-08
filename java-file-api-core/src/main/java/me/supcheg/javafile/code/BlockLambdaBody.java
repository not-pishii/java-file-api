package me.supcheg.javafile.code;

/// A lambda body consisting of a statement block, e.g. `x -> { ... }`.
///
/// @param block the statements of the lambda body
public record BlockLambdaBody(CodeBody block) implements LambdaBody {}
