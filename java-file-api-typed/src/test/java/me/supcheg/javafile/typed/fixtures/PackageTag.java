package me.supcheg.javafile.typed.fixtures;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/// An annotation a package may carry.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PACKAGE)
public @interface PackageTag {
    /// The tag.
    ///
    /// @return the tag
    String value();
}
