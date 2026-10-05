/// The JDK types the tests of the typed layer need, as metamodels the `@Facts` processor generates: they are in
/// `me.supcheg.javafile.typed.testfacts` (`-Ajavafile.facts.package`), apart from the ones the main code is built
/// with (`me.supcheg.javafile.typed.jdk.facts`), and are not listed in the index (`-Ajavafile.facts.index=false`).
/// The supertypes and the types of the signatures come with them: `Object`, `CharSequence`, `Collection`, ...
@Facts({
    ArrayList.class,
    FileNotFoundException.class,
    Integer.class,
    InterruptedException.class,
    List.class,
    Math.class,
    NumberFormatException.class,
    Objects.class,
    PrintStream.class,
    RuntimeException.class,
    Stream.class,
    String.class,
    StringReader.class,
    System.class
})
package me.supcheg.javafile.typed.testfacts;

import me.supcheg.javafile.facts.meta.Facts;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
