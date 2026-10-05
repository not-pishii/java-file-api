/// The full set of JDK types the hand-written metamodels (`me.supcheg.javafile.facts.jdk`) have, as the `@Facts`
/// processor generates them, for [GoldenTest] to compare with: the metamodels are in
/// `me.supcheg.javafile.typed.testfacts` (`-Ajavafile.facts.package`), apart from the ones the main code is built
/// with (`me.supcheg.javafile.typed.jdk.facts`).
@Facts({
    ArrayList.class,
    CharSequence.class,
    Comparable.class,
    Consumer.class,
    Exception.class,
    Function.class,
    IOException.class,
    IllegalArgumentException.class,
    IllegalStateException.class,
    Integer.class,
    List.class,
    Math.class,
    NumberFormatException.class,
    Object.class,
    Objects.class,
    Predicate.class,
    PrintStream.class,
    Runnable.class,
    RuntimeException.class,
    Stream.class,
    String.class,
    StringBuilder.class,
    Supplier.class,
    System.class,
    Throwable.class
})
package me.supcheg.javafile.typed.testfacts;

import me.supcheg.javafile.facts.meta.Facts;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
