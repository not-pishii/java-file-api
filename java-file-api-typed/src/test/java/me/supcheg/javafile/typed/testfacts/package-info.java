/// The JDK types the tests of the typed layer need, as metamodels the `@Facts` processor generates: they are in
/// `me.supcheg.javafile.typed.testfacts` (`-Ajavafile.facts.package`), apart from the ones the main code is built
/// with (`me.supcheg.javafile.typed.jdk.facts`), and are not listed in the index (`-Ajavafile.facts.index=false`).
/// The supertypes and the types of the signatures come with them: `Object`, `CharSequence`, `Collection`, ...
///
/// With them the fixtures of the tests ([me.supcheg.javafile.typed.fixtures]): the functional interfaces of the
/// lambda tests and the enum of the `switch` tests are known by generated facts, as a generator knows its own.
@Facts({
    ArrayList.class,
    BiConsumer.class,
    BiFunction.class,
    Callable.class,
    Comparator.class,
    Consumer.class,
    FileNotFoundException.class,
    IllegalStateException.class,
    Function.class,
    Integer.class,
    InterruptedException.class,
    IntBinaryOperator.class,
    IntSupplier.class,
    List.class,
    Math.class,
    NumberFormatException.class,
    Objects.class,
    PrintStream.class,
    Runnable.class,
    RuntimeException.class,
    Signal.class,
    Stream.class,
    String.class,
    StringBuilder.class,
    StringReader.class,
    Supplier.class,
    System.class,
    Tasks.class,
    TriConsumer.class,
    TriFunction.class,
    UnaryOperator.class,
    UnsupportedEncodingException.class
})
package me.supcheg.javafile.typed.testfacts;

import me.supcheg.javafile.facts.meta.Facts;
import me.supcheg.javafile.typed.fixtures.Signal;
import me.supcheg.javafile.typed.fixtures.Tasks;
import me.supcheg.javafile.typed.fixtures.TriConsumer;
import me.supcheg.javafile.typed.fixtures.TriFunction;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
