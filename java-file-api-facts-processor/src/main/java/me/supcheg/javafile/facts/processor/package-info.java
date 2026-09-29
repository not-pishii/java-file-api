/// The `@Facts` annotation processor (§5 of the main spec): generates the
/// metamodels of the types `@Facts` asks for, and token-only metamodels of
/// the types their signatures mention, as sources written with
/// `java-file-api-core`.
///
/// [me.supcheg.javafile.facts.processor.FactsProcessor] is the only public
/// class; javac finds it through `META-INF/services`, and Gradle treats it as
/// an aggregating incremental processor.
@NullMarked
package me.supcheg.javafile.facts.processor;

import org.jspecify.annotations.NullMarked;
