package me.supcheg.javafile.code;

/// A pattern matched by [InstanceOfExpr] or [PatternLabel]: a flat type test
/// with an optional binding ([TypePattern]), or a record deconstruction
/// ([RecordPattern]).
public sealed interface Pattern permits TypePattern, RecordPattern {}
