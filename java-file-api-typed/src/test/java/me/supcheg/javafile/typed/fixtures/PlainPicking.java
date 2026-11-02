package me.supcheg.javafile.typed.fixtures;

/// A non-generic method of the erasure of a generic one of [Picking]: a fact it cannot be implemented
/// by, which the typed layer refuses.
public interface PlainPicking {
    CharSequence pick(CharSequence value);
}
