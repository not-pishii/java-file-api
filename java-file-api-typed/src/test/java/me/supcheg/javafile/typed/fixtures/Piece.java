package me.supcheg.javafile.typed.fixtures;

/// A piece at a [Point], a permitted record of [Figure] whose component a
/// record pattern deconstructs in turn.
///
/// @param at where it is
public record Piece(Point at) implements Figure {}
