package me.supcheg.javafile.typed.fixtures;

import java.util.List;

/// A generic method of the erasure of one of [Picking], of other bounds: a fact it cannot be
/// implemented by, which the typed layer refuses.
public interface WrongBound {
    <T> void hold(List<T> values);
}
