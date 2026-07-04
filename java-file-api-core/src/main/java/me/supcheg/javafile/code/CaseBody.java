package me.supcheg.javafile.code;

/// The body executed by a matched [SwitchCase].
///
/// The permitted implementations mirror the three arrow-form (`->`) bodies
/// Java allows on a switch case: a single expression, a block of statements,
/// or a `throw`.
public sealed interface CaseBody permits ExprCaseBody, BlockCaseBody, ThrowCaseBody {}
