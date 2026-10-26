/// Examples of the typed layer (`java-file-api-typed`): classes declared through facts, so that what is rendered is
/// code javac accepts. The types the examples name are known by the metamodels the `@Facts` processor generates of
/// the annotation below, into `me.supcheg.javafile.example.typed.facts`; their supertypes and the types of their
/// signatures come with them.
@NullMarked
@Facts({
    AbstractList.class,
    ArrayList.class,
    Comparable.class,
    Consumer.class,
    Double.class,
    Function.class,
    Integer.class,
    List.class,
    Math.class,
    NumberFormatException.class,
    Predicate.class,
    Signal.class,
    String.class
})
package me.supcheg.javafile.example.typed;

import me.supcheg.javafile.facts.meta.Facts;
import org.jspecify.annotations.NullMarked;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
