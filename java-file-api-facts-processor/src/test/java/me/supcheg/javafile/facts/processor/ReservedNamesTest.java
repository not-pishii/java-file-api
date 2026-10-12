package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.harness.Compiled;
import me.supcheg.javafile.facts.processor.harness.Javac;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.lang.reflect.Member;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// The names [MetamodelNames] keeps from the facts are the names of what a
/// metamodel has besides its facts: read off the metamodels the processor
/// writes for types without members, of every kind, so a member the emitter
/// gains is a name the facts give way to, or this test fails.
class ReservedNamesTest {

    @TempDir
    Path classes;

    @Test
    void everyMemberAMetamodelHasBesidesItsFactsIsAReservedName() throws Exception {
        Compiled compiled = Javac.facts()
                .compile(
                        """
                        package gen;
                        @me.supcheg.javafile.facts.meta.Facts({
                            p.Plain.class, p.Generic.class, p.Api.class, p.Fn.class, p.GenericFn.class, p.Kind.class,
                            p.Closed.class, p.Abstract.class
                        })
                        class G {}
                        """,
                        "package p; public class Plain { private Plain() {} }",
                        "package p; public abstract class Generic<T> { private Generic() {} }",
                        "package p; public interface Api {}",
                        "package p; @FunctionalInterface public interface Fn { void run(); }",
                        "package p; @FunctionalInterface public interface GenericFn<T> { T get(); }",
                        "package p; public enum Kind {}",
                        "package p; public final class Closed { private Closed() {} }",
                        "package p; public abstract class Abstract { protected Abstract() {} }")
                .orFail();
        compiled.writeTo(classes);
        // the facts of these types: of the constructor a subclass calls, and of the methods a type declares
        // — an enum those the compiler gives it
        List<String> facts = List.of("super_", "run", "get", "values", "valueOf_String");

        try (URLClassLoader loader =
                new URLClassLoader(new URL[] {classes.toUri().toURL()}, ReservedNamesTest.class.getClassLoader())) {
            List<String> metamodels = compiled.metamodels().stream()
                    .filter(name -> name.startsWith("gen.facts.p."))
                    .toList();
            assertThat(metamodels).hasSize(8);
            for (String name : metamodels) {
                Class<?> metamodel = loader.loadClass(name);
                assertThat(Stream.of(
                                        Stream.of(metamodel.getDeclaredFields()),
                                        Stream.of(metamodel.getDeclaredMethods()),
                                        Stream.of(metamodel.getDeclaredClasses())
                                                .map(ReservedNamesTest::named))
                                .flatMap(members -> members)
                                .filter(member -> !member.isSynthetic())
                                .map(Member::getName)
                                .filter(member -> !facts.contains(member))
                                .distinct())
                        .as("the members of %s that are no facts", name)
                        .isSubsetOf(MetamodelNames.RESERVED);
            }
        }
    }

    /// A nested class as a member with a name: its simple name.
    private static Member named(Class<?> nested) {
        return new Member() {
            @Override
            public Class<?> getDeclaringClass() {
                return nested.getDeclaringClass();
            }

            @Override
            public String getName() {
                return nested.getSimpleName();
            }

            @Override
            public int getModifiers() {
                return nested.getModifiers();
            }

            @Override
            public boolean isSynthetic() {
                return nested.isSynthetic();
            }
        };
    }
}
