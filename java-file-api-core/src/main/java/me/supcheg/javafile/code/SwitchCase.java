package me.supcheg.javafile.code;

import java.util.List;

/// A single case of a `switch` statement or expression: one or more labels
/// sharing a body.
///
/// @param labels the labels matched by this case, in order; copied defensively
/// @param body the case's body
public record SwitchCase(List<CaseLabel> labels, CaseBody body) {
    public SwitchCase {
        labels = List.copyOf(labels);
    }
}
