package me.supcheg.javafile.typed.fixtures;

import java.util.List;

/// A generic record whose components are a record of its type parameter, a
/// class, a primitive, an array of it and a parameterization of it: a record
/// pattern of it substitutes every kind of component type.
///
/// @param box the nested record
/// @param label the label
/// @param count the count
/// @param tags the tags
/// @param notes the notes
/// @param <T> the type of the value
// an array component is what the test of a substituted array type needs:
// a pattern of it matches it exactly
@SuppressWarnings("ArrayRecordComponent")
public record Crated<T>(Boxed<T> box, String label, int count, T[] tags, List<T> notes) {}
