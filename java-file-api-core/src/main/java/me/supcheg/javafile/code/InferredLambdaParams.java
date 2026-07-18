package me.supcheg.javafile.code;

import me.supcheg.javafile.Identifiers;

import java.util.List;

/// Lambda parameters with inferred types, e.g. `(a, b) -> ...`.
///
/// Names are defensively copied into an unmodifiable list.
///
/// @param names the parameter names, in order
public record InferredLambdaParams(List<String> names) implements LambdaParams {
    public InferredLambdaParams {
        names = names.stream().map(Identifiers::requireValid).toList();
    }
}
