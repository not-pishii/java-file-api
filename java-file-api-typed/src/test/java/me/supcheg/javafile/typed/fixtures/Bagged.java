package me.supcheg.javafile.typed.fixtures;

import java.util.List;

/// A record whose components are an array and a parameterization: a record
/// pattern of it matches them by their types exactly.
///
/// @param tags the tags
/// @param notes the notes
// an array component is what the test of a component pattern needs: a pattern of it matches it exactly
@SuppressWarnings("ArrayRecordComponent")
public record Bagged(String[] tags, List<String> notes) {}
