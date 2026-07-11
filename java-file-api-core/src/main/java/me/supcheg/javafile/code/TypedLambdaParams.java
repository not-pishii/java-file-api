package me.supcheg.javafile.code;

import me.supcheg.javafile.model.Param;

import java.util.List;

/// Lambda parameters with explicit types, e.g. `(String a, int b) -> ...`.
///
/// Parameters are defensively copied into an unmodifiable list.
///
/// @param params the parameters, in order
public record TypedLambdaParams(List<Param> params) implements LambdaParams {
    public TypedLambdaParams {
        params = List.copyOf(params);
    }
}
