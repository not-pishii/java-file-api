package me.supcheg.javafile.facts.source;

import me.supcheg.javafile.facts.MemberTraits;

import java.util.Optional;

/// What a [FactSource] learned about a member it proved to exist.
///
/// @param traits the throws-set and overridability of a method or constructor;
///               [MemberTraits#DEFAULT] for a field
/// @param constantValue the value of a constant variable, for a static field
public record Resolution(MemberTraits traits, Optional<Object> constantValue) {

    /// Creates the resolution of a method or constructor.
    ///
    /// @param traits the member traits
    /// @return the resolution
    public static Resolution of(MemberTraits traits) {
        return new Resolution(traits, Optional.empty());
    }
}
