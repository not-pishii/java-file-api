/// A hand-written metamodel of a small part of the JDK, standing in until
/// metagen (§5) generates metamodels from class files.
///
/// It follows the conventions metagen output will have: a class `<Name>_`
/// per type; for a non-generic type static fields — `TOKEN` and one fact per
/// member, overloads disambiguated by a parameter-type suffix
/// (`substring`, `substring_int_int`); for a generic type (§3.3) an instance
/// built from the tokens of its type arguments, e.g.
/// `new List_<>(String_.TOKEN).get`; a generic method (§3.4) is a factory
/// taking a witness token per method type parameter, e.g.
/// `new Stream_<>(String_.TOKEN).map(Integer_.TOKEN)`.
///
/// The facts here are vouched for by hand and verified against the running
/// JDK by reflection in the tests. This package is to be replaced by metagen
/// output; keep its surface small.
@NullMarked
package me.supcheg.javafile.facts.jdk;

import org.jspecify.annotations.NullMarked;
