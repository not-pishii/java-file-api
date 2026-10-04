package me.supcheg.javafile.facts.processor.harness;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// [Javac#documented()] is what the metamodels of every fixture are compiled
/// with: it must fail on what a comment of a metamodel may get wrong.
class JavacTest {

    private static Compiled documented(String source) {
        return Javac.plain().alone().documented().compile(source);
    }

    @Test
    void anActionRunsInTheFirstRoundOfACompilationOfTheDescription() {
        // the types are those of the classpath and the options: an older release has no SequencedCollection
        String name = "java.util.SequencedCollection";

        assertThat(Javac.plain()
                        .<Boolean>inFirstRound(env -> env.getElementUtils().getTypeElement(name) != null))
                .isTrue();
        assertThat(Javac.plain()
                        .options("--release", "17")
                        .<Boolean>inFirstRound(env -> env.getElementUtils().getTypeElement(name) != null))
                .isFalse();
        assertThatIllegalStateException()
                .isThrownBy(() -> Javac.plain().inFirstRound(env -> {
                    throw new IllegalStateException("thrown in the round");
                }))
                .withMessage("thrown in the round");
    }

    @Test
    void aSourceWhoseCommentsAreRightIsClean() {
        Compiled compiled = documented("""
                package p;
                /// A thing, like [String#substring(int)] and [java.util.Map.Entry#getKey()].
                public final class Thing {
                    /// The fact of [Thing#size(int\\[\\], Object)].
                    public static final int SIZE = 1;

                    private Thing() {}

                    /// The size.
                    ///
                    /// @param <T> a type argument
                    /// @param ints the ints
                    /// @param value the value
                    /// @return the size
                    public static <T> int size(int[] ints, T value) {
                        return ints.length;
                    }
                }
                """);

        assertThat(compiled.succeeded()).as(compiled.rendered()).isTrue();
        assertThat(compiled.diagnostics()).isEmpty();
    }

    @Test
    void aLinkThatDoesNotResolveFails() {
        Compiled compiled = documented("""
                package p;
                /// A thing, like [String#substring(long)].
                public final class Thing {
                    private Thing() {}
                }
                """);

        assertThat(compiled.succeeded()).isFalse();
        assertThat(compiled.errors()).singleElement().asString().contains("reference not found");
    }

    @Test
    void aLinkToATypeThatIsNotThereFails() {
        Compiled compiled = documented("""
                package p;
                /// A thing, like [p.Missing].
                public final class Thing {
                    private Thing() {}
                }
                """);

        assertThat(compiled.succeeded()).isFalse();
        assertThat(compiled.errors()).singleElement().asString().contains("reference not found");
    }

    @Test
    void aPublicDeclarationWithoutACommentFails() {
        Compiled compiled = documented("""
                package p;
                /// A thing.
                public final class Thing {
                    public static final int SIZE = 1;

                    private Thing() {}
                }
                """);

        assertThat(compiled.succeeded()).isFalse();
        assertThat(compiled.rendered()).contains("no comment");
    }

    @Test
    void aParameterWithoutATagFails() {
        Compiled compiled = documented("""
                package p;
                /// A thing.
                public final class Thing {
                    private Thing() {}

                    /// The size.
                    public static int size(int[] ints) {
                        return ints.length;
                    }
                }
                """);

        assertThat(compiled.succeeded()).isFalse();
        assertThat(compiled.rendered()).contains("no @param for ints").contains("no @return");
    }

    @Test
    void whatIsNotPublicNeedsNoComment() {
        Compiled compiled = documented("""
                package p;
                /// A thing.
                public final class Thing {
                    static final int SIZE = 1;
                    private final int count = 0;

                    private Thing() {}

                    static final class Canonical {
                        static final String TEXT = "";
                    }
                }
                """);

        assertThat(compiled.succeeded()).as(compiled.rendered()).isTrue();
    }
}
