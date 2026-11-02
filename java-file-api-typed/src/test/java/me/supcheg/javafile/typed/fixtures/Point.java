package me.supcheg.javafile.typed.fixtures;

/// A record of two `int` components, the type a record pattern deconstructs.
///
/// @param x the first component
/// @param y the second component
public record Point(int x, int y) {}
