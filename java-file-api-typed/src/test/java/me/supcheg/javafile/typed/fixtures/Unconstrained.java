package me.supcheg.javafile.typed.fixtures;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/// An annotation of no `@Target`: it annotates a package as it annotates anything else.
@Retention(RetentionPolicy.RUNTIME)
public @interface Unconstrained {
    /// The tag.
    ///
    /// @return the tag
    String value();
}
