/// A hand-written metamodel of a small part of the JDK, standing in until
/// the `@Facts` processor (§5) generates metamodels.
///
/// It follows the conventions the processor's output will have: a class `<Name>_`
/// per type; for a non-generic type static fields — `TOKEN` and one fact per
/// member, overloads disambiguated by a parameter-type suffix
/// (`substring`, `substring_int_int`); for a generic type (§3.3) an instance
/// built from the tokens of its type arguments, e.g.
/// `new List_<>(String_.TOKEN).get`; a generic method (§3.4) is a factory
/// taking a witness token per method type parameter, e.g.
/// `new Stream_<>(String_.TOKEN).map(Integer_.TOKEN)`.
///
/// Primitive types appear as their [me.supcheg.javafile.facts.Prim] markers:
/// `String_.length` is a `MethodRef0<String, Prim.Int>`.
///
/// The facts here are vouched for by hand through
/// [me.supcheg.javafile.facts.UnsafeFacts] and verified against the running
/// JDK by reflection in the tests. Every class is marked `@Generated`: the
/// package stands in for the output of the `@Facts` processor (§5) and is
/// excluded, like it, from the `grep Unsafe` audit of hand-written code.
/// It is to be replaced by the processor's output; keep its surface small.
@NullMarked
package me.supcheg.javafile.facts.jdk;

import org.jspecify.annotations.NullMarked;
