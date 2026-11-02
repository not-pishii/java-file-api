package me.supcheg.javafile.typed.fixtures;

import java.util.List;

/// A generic record whose components are parameterizations with a wildcard
/// type argument: a record pattern of it substitutes the type parameter in the
/// bound of each wildcard.
///
/// @param upper the upper-bounded wildcard
/// @param lower the lower-bounded wildcard
/// @param either the unbounded wildcard
/// @param <T> the type of the values
public record Bounded<T>(List<? extends T> upper, List<? super T> lower, List<?> either) {}
