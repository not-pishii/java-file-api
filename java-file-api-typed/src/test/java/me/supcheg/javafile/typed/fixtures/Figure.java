package me.supcheg.javafile.typed.fixtures;

/// A sealed interface of three records, the hierarchy a pattern `switch` is
/// exhaustive over.
public sealed interface Figure permits Disc, Square, Piece {}
