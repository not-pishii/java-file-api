package me.supcheg.javafile.typed.fixtures;

/// A record of two [Point] components: a record pattern of it deconstructs one
/// [Point] inside another.
///
/// @param start the first point
/// @param end the second point
public record Line(Point start, Point end) {}
