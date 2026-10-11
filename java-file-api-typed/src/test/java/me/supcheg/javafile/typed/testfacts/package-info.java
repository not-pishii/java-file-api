/// The JDK types the tests of the typed layer need, as metamodels the `@Facts` processor generates: they are in
/// `me.supcheg.javafile.typed.testfacts` (`-Ajavafile.facts.package`), apart from the ones the main code is built
/// with (`me.supcheg.javafile.typed.jdk.facts`), and are not listed in the index (`-Ajavafile.facts.index=false`).
/// The supertypes and the types of the signatures come with them: `Object`, `CharSequence`, `Collection`, ...
///
/// With them the fixtures of the tests ([me.supcheg.javafile.typed.fixtures]): the functional interfaces of the
/// lambda tests and the enum of the `switch` tests are known by generated facts, as a generator knows its own.
@Facts({
    AbstractList.class,
    ArrayList.class,
    Base.class,
    Mid.class,
    Shape.class,
    Joining.class,
    Labelled.class,
    Risking.class,
    Useful.class,
    Local.class,
    Sealing.class,
    Thread.class,
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
    UnsupportedEncodingException.class,
    Holder.class,
    Named.class,
    Sourced.class,
    Texts.class,
    Numbers.class,
    TakesTexts.class,
    TakesNumbers.class,
    Renamed.class,
    Unnamed.class,
    Counted.class,
    Measured.class,
    Joiner.class,
    Titled.class,
    Nothing.class,
    Op.class,
    Outer.Level.class,
    Thrower.class,
    Words.class,
    Origin.class,
    Worded.class,
    Numbered.class,
    Maker.class,
    Resetting.class,
    BaseTwin.class,
    SubTwin.class,
    Picked.class,
    Linked.class,
    Chained.class
})
package me.supcheg.javafile.typed.testfacts;

import me.supcheg.javafile.facts.meta.Facts;
import me.supcheg.javafile.typed.fixtures.Base;
import me.supcheg.javafile.typed.fixtures.Holder;
import me.supcheg.javafile.typed.fixtures.Mid;
import me.supcheg.javafile.typed.fixtures.Shape;
import me.supcheg.javafile.typed.fixtures.Joining;
import me.supcheg.javafile.typed.fixtures.Labelled;
import me.supcheg.javafile.typed.fixtures.Risking;
import me.supcheg.javafile.typed.fixtures.Useful;
import me.supcheg.javafile.typed.fixtures.Local;
import me.supcheg.javafile.typed.fixtures.Sealing;
import me.supcheg.javafile.typed.fixtures.Named;
import me.supcheg.javafile.typed.fixtures.Sourced;
import me.supcheg.javafile.typed.fixtures.Texts;
import me.supcheg.javafile.typed.fixtures.Numbers;
import me.supcheg.javafile.typed.fixtures.TakesTexts;
import me.supcheg.javafile.typed.fixtures.TakesNumbers;
import me.supcheg.javafile.typed.fixtures.Renamed;
import me.supcheg.javafile.typed.fixtures.Unnamed;
import me.supcheg.javafile.typed.fixtures.Counted;
import me.supcheg.javafile.typed.fixtures.Measured;
import me.supcheg.javafile.typed.fixtures.Joiner;
import me.supcheg.javafile.typed.fixtures.Titled;
import me.supcheg.javafile.typed.fixtures.Nothing;
import me.supcheg.javafile.typed.fixtures.Op;
import me.supcheg.javafile.typed.fixtures.Outer;
import me.supcheg.javafile.typed.fixtures.Signal;
import me.supcheg.javafile.typed.fixtures.Tasks;
import me.supcheg.javafile.typed.fixtures.Thrower;
import me.supcheg.javafile.typed.fixtures.TriConsumer;
import me.supcheg.javafile.typed.fixtures.TriFunction;
import me.supcheg.javafile.typed.fixtures.Words;
import me.supcheg.javafile.typed.fixtures.Origin;
import me.supcheg.javafile.typed.fixtures.Worded;
import me.supcheg.javafile.typed.fixtures.Numbered;
import me.supcheg.javafile.typed.fixtures.Maker;
import me.supcheg.javafile.typed.fixtures.Resetting;
import me.supcheg.javafile.typed.fixtures.BaseTwin;
import me.supcheg.javafile.typed.fixtures.SubTwin;
import me.supcheg.javafile.typed.fixtures.Picked;
import me.supcheg.javafile.typed.fixtures.Linked;
import me.supcheg.javafile.typed.fixtures.Chained;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.AbstractList;
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
