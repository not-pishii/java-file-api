package me.supcheg.javafile.code;

/// What follows `->` in a switch case: an expression, a block, or a `throw`.
public sealed interface CaseBody permits ExprCaseBody, BlockCaseBody, ThrowCaseBody {}
