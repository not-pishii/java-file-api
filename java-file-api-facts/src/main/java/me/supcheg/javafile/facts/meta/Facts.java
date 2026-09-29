package me.supcheg.javafile.facts.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/// Asks the `@Facts` processor for the full metamodels of types (§5): a
/// class `<Name>_` per type, with a token, a fact per member and the
/// [me.supcheg.javafile.facts.TypeShape] the token applies to its type
/// arguments.
///
/// Put it on the generator's class or on its `package-info`:
///
/// ```java
/// @Facts({String.class, List.class, DayOfWeek.class})
/// final class MyGenerator extends AbstractProcessor { … }
/// ```
///
/// Several `@Facts` of one compilation are merged, and a type requested more
/// than once is generated once. A generic type is requested by its raw
/// literal, `List.class`; the metamodel is parameterized, and the generator
/// instantiates it, `new List_<>(String_.TOKEN)`. A nested type is requested
/// by its own literal, `Map.Entry.class`.
///
/// The processor rejects, with an error on the annotation: primitive and
/// array types, types that are not `public` or are nested in a type that is
/// not, annotation types, and inner classes of a generic class.
///
/// The retention is [RetentionPolicy#CLASS]: Gradle lets an incremental
/// annotation processor read only annotations that survive into the class
/// file, and `SOURCE` would cost the generator its incremental compilation.
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.PACKAGE})
public @interface Facts {

    /// The types to generate metamodels for: declared classes, interfaces,
    /// enums and records, each `public` with every enclosing type `public`.
    ///
    /// @return the types, by class literal
    Class<?>[] value();
}
