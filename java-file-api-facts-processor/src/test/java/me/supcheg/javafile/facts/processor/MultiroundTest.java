package me.supcheg.javafile.facts.processor;

import me.supcheg.javafile.facts.processor.harness.Compiled;
import me.supcheg.javafile.facts.processor.harness.Javac;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/// Fixture `multiround` (mini-spec §8, §9.2, Q13): types another processor
/// generates, requested in `@Facts` or mentioned by a requested type, get
/// their metamodels in a later round; while a requested type or a supertype
/// of one is not there, the token-only metamodel of a type it may extend or
/// implement is held back, so a type is known to be a supertype before its
/// metamodel is written, and every other metamodel is written at once; a
/// type that never appears is an error in the last round, which tells what
/// each type waited for, and the metamodels held back are written there.
class MultiroundTest {
    @TempDir
    Path classes;

    @TempDir
    Path rendered;

    /// Generates `gen.Missing` and `gen.Other` in its first round.
    private static final class Generating extends AbstractProcessor {
        private boolean done;

        @Override
        public Set<String> getSupportedAnnotationTypes() {
            return Set.of("*");
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latestSupported();
        }

        @Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
            if (!done) {
                done = true;
                write("gen.Missing", "package gen; public class Missing { public Other other() { return null; } }");
                write("gen.Other", "package gen; public final class Other {}");
            }
            return false;
        }

        private void write(String name, String source) {
            try (Writer writer = processingEnv.getFiler().createSourceFile(name).openWriter()) {
                writer.write(source);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    /// The metamodels every compilation generates besides those of its own types: the full one of `java.lang.Object`,
    /// which every requested type extends, and the token-only ones of the types the signatures of `Object` mention.
    ///
    /// @param base the base of the mirror packages
    /// @param others more metamodels, by qualified name
    /// @return `others` and those of `Object`
    private static List<String> withObject(String base, String... others) {
        return Stream.concat(
                        Stream.of(others),
                        Stream.of("Object_", "Class_", "InterruptedException_", "String_")
                                .map(name -> base + ".java.lang." + name))
                .toList();
    }

    private static Compiled process(String... sources) {
        return Javac.facts().with(new Generating()).compile(sources);
    }

    /// Generates sources round by round: those of the first map in its first round, of the second
    /// in its second, and so on.
    private static final class InRounds extends AbstractProcessor {
        private final Iterator<Map<String, String>> rounds;

        /// @param rounds the sources of each round, by qualified name
        InRounds(List<Map<String, String>> rounds) {
            this.rounds = rounds.iterator();
        }

        @Override
        public Set<String> getSupportedAnnotationTypes() {
            return Set.of("*");
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latestSupported();
        }

        @Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
            if (!round.processingOver() && rounds.hasNext()) {
                rounds.next().forEach((name, source) -> {
                    try (Writer writer =
                            processingEnv.getFiler().createSourceFile(name).openWriter()) {
                        writer.write(source);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
            }
            return false;
        }
    }

    private static final String LATE = "package gen; public class Late extends p.Base { public void late() {} }";
    private static final String HOLDER = "package p; public class Holder { public Base base() { return null; }"
            + " public Other other() { return null; } }";
    private static final String BASE = "package p; public class Base { public int inherited() { return 7; } }";
    private static final String OTHER = "package p; public class Other { public void other() {} }";
    private static final String USE = """
            package use;
            import gen.facts.java.lang.Object_;
            import gen.facts.java.lang.String_;
            import gen.facts.p.Holder_;
            class Use {
                Object holder = Holder_.TOKEN;
                Object base = Holder_.base;
                Object string = String_.TOKEN;
                Object object = Object_.toString;
            }
            """;

    /// The warning of javac for a file a processor writes in the last round.
    private static String lastRound(String metamodel) {
        return "File for type '" + metamodel + "' created in the last round will not be subject to annotation"
                + " processing.";
    }

    /// Records the types each round starts with: the sources it is given, and from the second round on
    /// those the processors wrote in the round before.
    private static final class Rounds extends AbstractProcessor {
        private final List<Set<String>> roots = new ArrayList<>();

        /// The types the processors wrote in a round, which are what the next round starts with.
        ///
        /// @param round the round, from 1
        /// @return the qualified names, sorted
        Set<String> writtenIn(int round) {
            return roots.get(round);
        }

        @Override
        public Set<String> getSupportedAnnotationTypes() {
            return Set.of("*");
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latestSupported();
        }

        @Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
            roots.add(round.getRootElements().stream()
                    .filter(TypeElement.class::isInstance)
                    .map(root -> ((TypeElement) root).getQualifiedName().toString())
                    .collect(Collectors.toCollection(TreeSet::new)));
            return false;
        }
    }

    private static Compiled process(InRounds generating, String... sources) {
        return Javac.facts().with(generating).compile(sources);
    }

    /// The errors of the last round about the requested types that never became ready, in order.
    private static List<String> unresolvable(Compiled compilation) {
        return compilation.errors().stream()
                .filter(message -> message.startsWith("type "))
                .toList();
    }

    @Test
    void aRequestedTypeGeneratedByAnotherProcessorIsReadInTheNextRound() {
        Compiled compilation = process(
                        """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({gen.Missing.class, p.Uses.class})
                class G {}
                """, "package p; public class Uses { public gen.Missing missing() { return null; } }")
                .orFail();
        Map<String, String> sources = compilation.sources();
        assertThat(sources.keySet())
                .containsExactlyInAnyOrderElementsOf(withObject(
                        "gen.facts",
                        "gen.Missing",
                        "gen.Other",
                        "gen.facts.gen.Missing_",
                        "gen.facts.p.Uses_",
                        "gen.facts.gen.Other_"));
        assertThat(sources.get("gen.facts.gen.Missing_")).contains("OpenClassToken<Missing> TOKEN");
    }

    @Test
    void aTypeMentionedButNotGeneratedYetDefersTheRequestedType() {
        Compiled compilation = process(
                        """
                @me.supcheg.javafile.facts.meta.Facts(p.Uses.class)
                package gen;
                """, "package p; public class Uses { public gen.Missing missing() { return null; } }")
                .orFail();
        assertThat(compilation.sources().keySet())
                .containsExactlyInAnyOrderElementsOf(withObject(
                        "gen.facts", "gen.Missing", "gen.Other", "gen.facts.p.Uses_", "gen.facts.gen.Missing_"));
    }

    @Test
    void aSupertypeGeneratedByAnotherProcessorGetsItsFullMetamodelInTheNextRound() {
        Compiled compilation = process("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.Sub.class)
                class G {}
                """, "package p; public class Sub extends gen.Missing { public void sub() {} }")
                .orFail();
        Map<String, String> sources = compilation.sources();

        // Sub waits for its superclass; then Missing, which is not asked for, is read as a supertype, and
        // Other, which Missing mentions, gets a token
        assertThat(sources.keySet())
                .containsExactlyInAnyOrderElementsOf(withObject(
                        "gen.facts",
                        "gen.Missing",
                        "gen.Other",
                        "gen.facts.p.Sub_",
                        "gen.facts.gen.Missing_",
                        "gen.facts.gen.Other_"));
        assertThat(sources.get("gen.facts.gen.Missing_"))
                .contains("complete = true")
                .contains("MethodRef0<Missing, Other> other");
        assertThat(sources.get("gen.facts.gen.Other_")).contains("complete = false");
        assertThat(compilation.diagnostics()).isEmpty();
    }

    @Test
    void aSupertypeThatMentionsATypeThatNeverAppearsIsToldWithTheRequestItIsThereFor() {
        Compiled compilation = process("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.Sub.class)
                class G {}
                """, "package p; public class Sub extends Sup { public void sub() {} }", """
                package p;
                public class Sup { public gen.Never never() { return null; } }
                """);

        // Sub does not wait for the metamodel of its supertype: only Sup is stuck
        assertThat(unresolvable(compilation))
                .containsExactly("type p.Sup (a supertype of p.Sub) in @Facts is not resolvable after all rounds: it"
                        + " mentions gen.Never, which no processor generated");
    }

    @Test
    void aTypeMentionedBeforeARequestedTypeThatExtendsItAppearsGetsItsFullMetamodel() throws Exception {
        Compiled compilation = process(new InRounds(List.of(Map.of("gen.Late", LATE))), """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.Holder.class, gen.Late.class})
                class G {}
                """, HOLDER, BASE, OTHER, """
                package use;

                import gen.facts.gen.Late_;
                import gen.facts.p.Base_;
                import java.lang.constant.ClassDesc;
                import me.supcheg.javafile.facts.PrimitiveToken;
                import me.supcheg.javafile.typed.TypedClassBuilder;
                import me.supcheg.javafile.typed.TypedJavaFile;

                import static me.supcheg.javafile.typed.Expressions.call;

                public final class Run {
                    public static String inherited() {
                        return TypedJavaFile.class_(ClassDesc.of("out", "Out"), new TypedJavaFile.TypedClassSpec() {
                                    @Override
                                    public <Self> void build(TypedClassBuilder<Self> cb) {
                                        cb.staticMethod(
                                                "go",
                                                PrimitiveToken.INT,
                                                Late_.TOKEN,
                                                (b, late) -> b.return_(call(late, Base_.inherited)));
                                    }
                                })
                                .render();
                    }
                }
                """)
                .orFail();
        Map<String, String> sources = compilation.sources();

        // round 1 has Base as a type Holder mentions, and writes nothing: gen.Late is not there; round 2
        // finds Base to be a supertype of it, and writes its full metamodel
        assertThat(sources.keySet())
                .containsExactlyInAnyOrderElementsOf(withObject(
                        "gen.facts",
                        "gen.Late",
                        "gen.facts.gen.Late_",
                        "gen.facts.p.Holder_",
                        "gen.facts.p.Base_",
                        "gen.facts.p.Other_"));
        assertThat(sources.get("gen.facts.p.Base_"))
                .contains("complete = true")
                .contains("MethodRef0<Base, Int> inherited");
        assertThat(sources.get("gen.facts.p.Other_")).contains("complete = false");
        assertThat(sources.get("gen.facts.gen.Late_"))
                .contains("complete = true")
                .contains("VoidMethodRef0<Late> late")
                .doesNotContain("> inherited");
        assertThat(sources.get("gen.facts.p.Holder_")).contains("Base_.Data.SHAPE");
        assertThat(compilation.resources())
                .containsKey("META-INF/javafile/metamodel/full/p.Base")
                .doesNotContainKey("META-INF/javafile/metamodel/token/p.Base");
        assertThat(compilation.diagnostics()).isEmpty();

        // the member Late inherits is called through the metamodel of Base
        compilation.writeTo(classes);
        String source;
        try (URLClassLoader run =
                new URLClassLoader(new URL[] {classes.toUri().toURL()}, MultiroundTest.class.getClassLoader())) {
            source = (String) run.loadClass("use.Run").getMethod("inherited").invoke(null);
        }
        Compiled out = Javac.plain()
                .alone()
                .linted()
                .classpath(classes)
                .compile(source)
                .clean();
        out.writeTo(rendered);
        try (URLClassLoader loader = new URLClassLoader(
                new URL[] {rendered.toUri().toURL(), classes.toUri().toURL()}, null)) {
            Class<?> late = loader.loadClass("gen.Late");
            assertThat(loader.loadClass("out.Out")
                            .getMethod("go", late)
                            .invoke(null, late.getConstructor().newInstance()))
                    .isEqualTo(7);
        }
    }

    @Test
    void whatMayYetBeASupertypeIsHeldBackUntilTheLastOfTheRequestedTypesIsThere() {
        Rounds rounds = new Rounds();
        Compiled compilation = Javac.facts()
                .with(
                        new InRounds(List.of(
                                Map.of("gen.Late", LATE),
                                Map.of("gen.Later", "package gen; public class Later extends p.Other {}"))),
                        rounds)
                .compile("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.Holder.class, gen.Late.class, gen.Later.class})
                class G {}
                """, HOLDER, BASE, OTHER, USE)
                .orFail();
        Map<String, String> sources = compilation.sources();

        // round 1 has neither gen.Late nor gen.Later: the full metamodels are what they are whatever
        // those turn out to extend, and so are the token-only ones of final classes; those of Base, Other
        // and InterruptedException, which a missing type may extend, are held back
        assertThat(rounds.writtenIn(1))
                .containsExactly(
                        "gen.Late",
                        "gen.facts.java.lang.Class_",
                        "gen.facts.java.lang.Object_",
                        "gen.facts.java.lang.String_",
                        "gen.facts.p.Holder_");
        // round 2 has gen.Late, and Base as its supertype, but not gen.Later yet, which extends Other
        assertThat(rounds.writtenIn(2)).containsExactly("gen.Later", "gen.facts.gen.Late_", "gen.facts.p.Base_");
        // round 3 has every type: Other is a supertype, InterruptedException is only mentioned after all
        assertThat(rounds.writtenIn(3))
                .containsExactly(
                        "gen.facts.gen.Later_", "gen.facts.java.lang.InterruptedException_", "gen.facts.p.Other_");
        // Holder_ of round 1 names Base_ of round 2 and Other_ of round 3
        assertThat(sources.get("gen.facts.p.Holder_"))
                .contains("Base_.Data.SHAPE")
                .contains("Other_.Data.SHAPE");
        assertThat(sources.get("gen.facts.java.lang.InterruptedException_")).contains("complete = false");
        assertThat(sources.get("gen.facts.p.Base_")).contains("complete = true");
        assertThat(sources.get("gen.facts.p.Other_"))
                .contains("complete = true")
                .contains("VoidMethodRef0<Other> other");
        assertThat(sources.get("gen.facts.gen.Late_")).contains("complete = true");
        assertThat(sources.get("gen.facts.gen.Later_")).contains("complete = true");
        assertThat(compilation.diagnostics()).isEmpty();
    }

    @Test
    void whatMayYetBeASupertypeIsHeldBackUntilTheSupertypeOfARequestedTypeIsThere() {
        Rounds rounds = new Rounds();
        Compiled compilation = Javac.facts()
                .with(new InRounds(List.of(Map.of("gen.Late", LATE))), rounds)
                .compile("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.Holder.class, p.Sub.class})
                class G {}
                """, HOLDER, BASE, OTHER, "package p; public class Sub extends gen.Late {}", USE)
                .orFail();
        Map<String, String> sources = compilation.sources();

        // gen.Late is not asked for; what it extends is not known before it is there
        assertThat(rounds.writtenIn(1))
                .containsExactly(
                        "gen.Late",
                        "gen.facts.java.lang.Class_",
                        "gen.facts.java.lang.Object_",
                        "gen.facts.java.lang.String_",
                        "gen.facts.p.Holder_");
        assertThat(rounds.writtenIn(2))
                .containsExactly(
                        "gen.facts.gen.Late_",
                        "gen.facts.java.lang.InterruptedException_",
                        "gen.facts.p.Base_",
                        "gen.facts.p.Other_",
                        "gen.facts.p.Sub_");
        assertThat(sources.get("gen.facts.p.Base_")).contains("complete = true");
        assertThat(sources.get("gen.facts.gen.Late_")).contains("complete = true");
        assertThat(sources.get("gen.facts.p.Other_")).contains("complete = false");
        assertThat(compilation.diagnostics()).isEmpty();
    }

    @Test
    void theGraphOfARoundIsNotCompleteWhileASupertypeIsMissing() {
        GraphProbe probe = new GraphProbe("p.Sub", "p.Holder");
        Javac.facts()
                .with(new InRounds(List.of(Map.of("gen.Late", LATE))), probe)
                .compile(
                        "package gen; class G {}",
                        HOLDER,
                        BASE,
                        OTHER,
                        "package p; public class Sub extends gen.Late {}")
                .orFail();
        TypeGraph graph = probe.graph();

        assertThat(graph.complete()).isFalse();
        // what gen.Late may extend: not String or Class, which are final, nor Object, a supertype already
        assertThat(graph.held()).containsExactly("java.lang.InterruptedException", "p.Base", "p.Other");
        assertThat(graph.missingSupertypes()).containsExactly(new TypeGraph.Edge.Supertype("p.Sub", "gen.Late"));
        assertThat(graph.nodes().get("gen.Late")).isEqualTo(new TypeGraph.Node.Absent("gen.Late"));
        // Base is only mentioned as far as this round knows
        assertThat(graph.nodes().get("p.Base")).isInstanceOf(TypeGraph.Node.Mentioned.class);
    }

    @Test
    void aTypeThatIsOnlyMentionedWhenEveryRequestedTypeIsThereKeepsItsTokenOnlyMetamodel() {
        Compiled compilation = process(
                        new InRounds(List.of(
                                Map.of("gen.Unrelated", "package gen; public class Unrelated extends p.Base {}"))),
                        """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.Holder.class)
                class G {}
                """,
                        HOLDER,
                        BASE,
                        OTHER)
                .orFail();
        Map<String, String> sources = compilation.sources();

        // a type another processor generates that nothing asks for changes nothing
        assertThat(sources.keySet())
                .containsExactlyInAnyOrderElementsOf(withObject(
                        "gen.facts",
                        "gen.Unrelated",
                        "gen.facts.p.Holder_",
                        "gen.facts.p.Base_",
                        "gen.facts.p.Other_"));
        assertThat(sources.get("gen.facts.p.Base_")).contains("complete = false");
        assertThat(sources.get("gen.facts.p.Other_")).contains("complete = false");
        assertThat(compilation.resources()).containsKey("META-INF/javafile/metamodel/token/p.Base");
        assertThat(compilation.diagnostics()).isEmpty();
    }

    private static final String[] HELD_BACK = {
        "gen.facts.java.lang.InterruptedException_", "gen.facts.p.Base_", "gen.facts.p.Other_"
    };

    @Test
    void aRequestedTypeThatNeverAppearsIsOneErrorOfJavacAndOneOfTheProcessor() {
        Rounds rounds = new Rounds();
        Compiled compilation = Javac.facts().with(rounds).compile("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.Holder.class, gen.Late.class})
                class G {}
                """, HOLDER, BASE, OTHER, USE);

        // javac tells that gen.Late is missing, the processor that it waited for it; the code that
        // imports the metamodels written in round 1 has nothing to complain of
        assertThat(compilation.errors())
                .containsExactly(
                        "cannot find symbol\n  symbol:   class Late\n  location: package gen",
                        "a type in @Facts is not resolvable after all rounds");
        assertThat(rounds.writtenIn(1))
                .containsExactly(
                        "gen.facts.java.lang.Class_",
                        "gen.facts.java.lang.Object_",
                        "gen.facts.java.lang.String_",
                        "gen.facts.p.Holder_");
        // what was held back for gen.Late is written when no round is left: javac warns of each file
        assertThat(compilation.warnings())
                .containsExactlyInAnyOrder(
                        Stream.of(HELD_BACK).map(MultiroundTest::lastRound).toArray(String[]::new));
    }

    @Test
    void anImportOfAMetamodelHeldBackForATypeThatNeverAppearsIsAnErrorOfItsOwn() {
        Compiled compilation = Javac.facts().compile("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.Holder.class, gen.Late.class})
                class G {}
                """, HOLDER, BASE, OTHER, USE, """
                package use;
                import gen.facts.p.Base_;
                class UseHeld {
                    Object base = Base_.TOKEN;
                }
                """);

        // javac does not find a file of the last round through an import
        assertThat(compilation.errors())
                .containsExactly(
                        "cannot find symbol\n  symbol:   class Base_\n  location: package gen.facts.p",
                        "cannot find symbol\n  symbol:   class Late\n  location: package gen",
                        "a type in @Facts is not resolvable after all rounds");
    }

    @Test
    void aCompilerThatGoesOnAfterTheErrorFindsTheMetamodelsOfTheLastRound() {
        Compiled compilation =
                Javac.facts().options("-XDshould-stop.ifError=FLOW").compile("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.Holder.class, gen.Late.class})
                class G {}
                """, HOLDER, BASE, OTHER, USE);

        // Holder_ names Base_ and Other_, which the last round wrote, by their qualified names: javac
        // attributes it, and has nothing to add
        assertThat(compilation.errors())
                .containsExactly(
                        "cannot find symbol\n  symbol:   class Late\n  location: package gen",
                        "a type in @Facts is not resolvable after all rounds");
    }

    @Test
    void aSupertypeThatNeverAppearsIsOneErrorOfJavacAndOneOfTheProcessor() {
        Compiled compilation =
                Javac.facts().compile("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.Holder.class, p.Sub.class})
                class G {}
                """, HOLDER, BASE, OTHER, "package p; public class Sub extends gen.Late {}", USE);

        assertThat(compilation.errors())
                .containsExactly(
                        "cannot find symbol\n  symbol:   class Late\n  location: package gen",
                        "type p.Sub in @Facts is not resolvable after all rounds: it mentions gen.Late, which no"
                                + " processor generated");
        assertThat(compilation.warnings())
                .containsExactlyInAnyOrder(
                        Stream.of(HELD_BACK).map(MultiroundTest::lastRound).toArray(String[]::new));
    }

    @Test
    void aSupertypeWithoutAFullMetamodelIsToldToTheRequestOfALaterRoundToo() {
        String bounded = "package p; public class Bounded<T extends Secret> { public void lost() {} }";
        Compiled compilation = process(
                        new InRounds(List.of(Map.of(
                                "gen.More",
                                "package gen; @me.supcheg.javafile.facts.meta.Facts(p.Second.class) class More {}"))),
                        """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.First.class)
                class G {}
                """,
                        bounded,
                        "package p; class Secret {}",
                        "package p; public class First extends Bounded<Secret> {}",
                        "package p; public class Second extends Bounded<Secret> {}")
                .orFail();

        String why = ": no facts of the public members inherited from p.Bounded, which has no full metamodel: the"
                + " bounds of the type parameters of p.Bounded mention types that are not public: p.Secret";
        assertThat(compilation.warnings()).containsExactly("p.First" + why, "p.Second" + why);
    }

    @Test
    void aFactsAnotherProcessorGeneratesCannotMakeAMetamodelThatIsWrittenFull() {
        Compiled compilation = process(
                        new InRounds(List.of(Map.of(
                                "gen.Late",
                                LATE,
                                "gen.More",
                                "package gen; @me.supcheg.javafile.facts.meta.Facts(gen.Late.class) class More {}"))),
                        """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.Holder.class)
                class G {}
                """,
                        HOLDER,
                        BASE,
                        OTHER)
                .orFail();
        Map<String, String> sources = compilation.sources();

        // the graph of round 1 is complete for the @Facts there is, so Base, which Holder mentions, gets
        // its token-only metamodel; the @Facts of round 2 is the first to ask for a subtype of Base
        assertThat(sources.get("gen.facts.p.Base_")).contains("complete = false");
        assertThat(sources.get("gen.facts.gen.Late_")).contains("complete = true");
        assertThat(compilation.warnings())
                .containsExactly("gen.Late: no facts of the public members inherited from p.Base, which has no full"
                        + " metamodel: its token-only metamodel gen.facts.p.Base_ was generated in an earlier round,"
                        + " before a type that extends or implements p.Base was asked for");
    }

    @Test
    void aRequestedTypeThatNeverAppearsIsAnErrorInTheLastRound() {
        Compiled compilation = process("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({gen.Never.class, gen.Missing.class})
                class G {}
                """);
        assertThat(compilation.errors()).contains("a type in @Facts is not resolvable after all rounds");
    }

    @Test
    void aTypeMentioningATypeThatNeverAppearsIsAnErrorInTheLastRound() {
        Compiled compilation =
                process("""
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.Broken.class)
                class G {}
                """, "package p; public class Broken { public gen.Never never() { return null; } }");
        assertThat(compilation.errors())
                .contains("type p.Broken in @Facts is not resolvable after all rounds: it mentions gen.Never, which"
                        + " no processor generated");
    }

    @Test
    void aTypeThatExistsButCannotBeNamedWhereItIsMentionedIsNotSaidToBeGeneratedByNoProcessor() {
        Compiled compilation = Javac.facts()
                .compile(
                        """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.Broken.class)
                class G {}
                """,
                        "package p; public class Broken { public q.Hid hid() { return null; } }",
                        "package q; class Hid {}");

        assertThat(compilation.errors())
                .containsExactly(
                        "q.Hid is not public in q; cannot be accessed from outside package",
                        "type p.Broken in @Facts is not resolvable after all rounds: it mentions q.Hid, which is not"
                                + " accessible there");
    }

    @Test
    void aFullMetamodelWaitsForTheMetamodelOfARequestedTypeItMentions() {
        Compiled compilation = process(
                        """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.A.class, p.B.class})
                class G {}
                """,
                        "package p; public class A { public B b() { return null; } }",
                        "package p; public class B { public gen.Missing missing() { return null; } }")
                .orFail();

        // B has to wait for gen.Missing, and A for B: else A would have no fact of b()
        Map<String, String> sources = compilation.sources();
        assertThat(sources.get("gen.facts.p.A_")).contains("B_.Data.SHAPE").contains("MethodRef0<A, B> b");
        assertThat(sources.get("gen.facts.p.B_")).contains("MethodRef0<B, Missing> missing");
        assertThat(compilation.diagnostics()).isEmpty();
    }

    @Test
    void aFullMetamodelWaitsForARequestedTypeATypeArgumentOrABoundMentions() {
        Compiled compilation = process(
                        """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.A.class, p.C.class, p.B.class})
                class G {}
                """,
                        "package p; public class A { public void all(java.util.List<? extends B> all) {} }",
                        "package p; public class C { public <T extends B> void bound(T t) {} }",
                        "package p; public class B { public gen.Missing missing() { return null; } }")
                .orFail();

        Map<String, String> sources = compilation.sources();
        assertThat(sources.get("gen.facts.p.A_"))
                .contains("VoidMethodRef1<A, List<? extends B>> all_List")
                .contains("TokenArg.extendsBound(UnsafeFacts.<B>openClassToken(B_.Data.SHAPE))");
        assertThat(sources.get("gen.facts.p.C_"))
                .contains("public static <T extends B> VoidMethodRef1<C, T> bound_T(RefToken<T> t) {");
        assertThat(compilation.diagnostics()).isEmpty();
    }

    @Test
    void aFullMetamodelWhoseMentionedRequestedTypeNeverGetsReadyIsAnErrorInTheLastRound() {
        Compiled compilation = process(
                """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.A.class, p.B.class})
                class G {}
                """,
                "package p; public class A { public B b() { return null; } }",
                "package p; public class B { public gen.Never never() { return null; } }");

        assertThat(unresolvable(compilation))
                .containsExactly(
                        "type p.A in @Facts is not resolvable after all rounds: it waits for the metamodel of p.B,"
                                + " which mentions gen.Never, which no processor generated",
                        "type p.B in @Facts is not resolvable after all rounds: it mentions gen.Never, which no"
                                + " processor generated");
    }

    @Test
    void theErrorOfTheLastRoundTellsTheChainOfMetamodelsATypeWaitsFor() {
        Compiled compilation = process(
                """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.C.class, p.A.class, p.B.class, p.Fine.class})
                class G {}
                """,
                "package p; public class A { public B b() { return null; } }",
                "package p; public class B { public C c() { return null; } }",
                "package p; public class C { public gen.Never never() { return null; } }",
                "package p; public class Fine { public int size() { return 0; } }");

        // A waits for B though B is read: its metamodel is not written before that of C is
        assertThat(unresolvable(compilation))
                .containsExactly(
                        "type p.A in @Facts is not resolvable after all rounds: it waits for the metamodel of p.B,"
                                + " which waits for the metamodel of p.C, which mentions gen.Never, which no"
                                + " processor generated",
                        "type p.B in @Facts is not resolvable after all rounds: it waits for the metamodel of p.C,"
                                + " which mentions gen.Never, which no processor generated",
                        "type p.C in @Facts is not resolvable after all rounds: it mentions gen.Never, which no"
                                + " processor generated");
    }

    @Test
    void typesThatMentionEachOtherWaitForTheMissingTypeNotForEachOther() {
        Compiled compilation = process(
                """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.A.class, p.B.class, p.C.class})
                class G {}
                """,
                "package p; public class A { public B b() { return null; } public C c() { return null; } }",
                "package p; public class B { public A a() { return null; } public C c() { return null; } }",
                "package p; public class C { public A a() { return null; } public gen.Never never() { return null; } }");

        // the three mention each other in a circle; what is reported is the missing type, by the shortest way
        assertThat(unresolvable(compilation))
                .containsExactly(
                        "type p.A in @Facts is not resolvable after all rounds: it waits for the metamodel of p.C,"
                                + " which mentions gen.Never, which no processor generated",
                        "type p.B in @Facts is not resolvable after all rounds: it waits for the metamodel of p.C,"
                                + " which mentions gen.Never, which no processor generated",
                        "type p.C in @Facts is not resolvable after all rounds: it mentions gen.Never, which no"
                                + " processor generated");
    }

    @Test
    void requestedTypesThatMentionEachOtherAreGeneratedInOneRound() {
        Compiled compilation = process(
                        """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.A.class, p.B.class})
                class G {}
                """,
                        "package p; public class A { public B b() { return null; } }",
                        "package p; public class B { public A a() { return null; } }")
                .orFail();

        // a metamodel refers to another through its Data.SHAPE only: neither has to be there first
        Map<String, String> sources = compilation.sources();
        assertThat(sources.get("gen.facts.p.A_")).contains("B_.Data.SHAPE").contains("MethodRef0<A, B> b");
        assertThat(sources.get("gen.facts.p.B_")).contains("A_.Data.SHAPE").contains("MethodRef0<B, A> a");
        assertThat(compilation.diagnostics()).isEmpty();
    }

    @Test
    void typesThatMentionEachOtherAndATypeGeneratedLaterAreGeneratedOnceItIsThere() {
        Compiled compilation = process(
                        """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.A.class, p.B.class, p.C.class})
                class G {}
                """,
                        "package p; public class A { public B b() { return null; } }",
                        "package p; public class B { public A a() { return null; } public C c() { return null; } }",
                        "package p; public class C { public gen.Missing missing() { return null; } }")
                .orFail();

        // C waits for gen.Missing, B for C and A for B, though A and B mention each other
        Map<String, String> sources = compilation.sources();
        assertThat(sources.get("gen.facts.p.A_")).contains("MethodRef0<A, B> b");
        assertThat(sources.get("gen.facts.p.B_")).contains("MethodRef0<B, A> a").contains("MethodRef0<B, C> c");
        assertThat(sources.get("gen.facts.p.C_")).contains("MethodRef0<C, Missing> missing");
        assertThat(compilation.diagnostics()).isEmpty();
    }

    @Test
    void theGraphOfARoundHasAnEdgeToWhatEachTypeWaitsFor() {
        GraphProbe probe = new GraphProbe("p.E", "p.D", "p.C", "p.B", "p.A");
        Javac.facts()
                .with(new Generating(), probe)
                .compile(
                        "package gen; class G {}",
                        "package p; public class A { public B b() { return null; } }",
                        "package p; public class B { public gen.Missing missing() { return null; } }",
                        "package p; public class C { public D d() { return null; } }",
                        "package p; public class D { public C c() { return null; } }",
                        "package p; public class E { public A a() { return null; } public <T> E(T t, B b) {} }")
                .orFail();
        TypeGraph graph = probe.graph();

        // in the first round gen.Missing is not generated yet; C and D mention each other, and await nothing;
        // E mentions B in a constructor that gets no fact, and so does not await it
        assertThat(graph.edges().stream().filter(edge -> edge instanceof TypeGraph.Edge.Awaits))
                .containsExactly(
                        new TypeGraph.Edge.Awaits("p.A", "p.B"),
                        new TypeGraph.Edge.Awaits("p.B", "gen.Missing"),
                        new TypeGraph.Edge.Awaits("p.E", "p.A"));
        assertThat(graph.nodes().get("gen.Missing")).isEqualTo(new TypeGraph.Node.Absent("gen.Missing"));
        assertThat(graph.nodes().get("p.B"))
                .isEqualTo(new TypeGraph.Node.Requested("p.B", new TypeGraph.Request.Waiting()));
        assertThat(graph.waitOf("p.E"))
                .contains(new TypeGraph.Wait.Missing(List.of("p.E", "p.A", "p.B", "gen.Missing")));
        assertThat(graph.waitOf("p.C")).isEmpty();
        assertThat(graph.waitOf("p.D")).isEmpty();
        assertThat(graph.from("p.E", TypeGraph.Edge.Signature.class).map(TypeGraph.Edge::to))
                .containsExactly("p.A", "p.B");
    }

    @Test
    void aFullMetamodelDoesNotWaitForATypeOnlyMembersWithoutAFactMention() {
        Compiled compilation = process(
                """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts({p.A.class, p.B.class})
                class G {}
                """,
                "package p; public class A { public <T> A(T t, B b) {} public void take(Hidden h, B b) {}"
                        + " public int size() { return 0; } }",
                "package p; class Hidden {}",
                "package p; public class B { public gen.Never never() { return null; } }");

        // A(T, B) and take(Hidden, B) get no fact whatever becomes of B: A has nothing to wait for
        // the compilation fails for B, so what was generated cannot be read: the warnings of A, given
        // as its metamodel is written, tell that it was
        assertThat(compilation.errors())
                .contains("type p.B in @Facts is not resolvable after all rounds: it mentions gen.Never, which no"
                        + " processor generated")
                .noneMatch(message -> message.startsWith("type p.A in @Facts"));
        assertThat(compilation.warnings())
                .contains(
                        "p.A: no fact of constructor <T>A(T,p.B), which is a generic constructor, whose type"
                                + " arguments a fact cannot give explicitly",
                        "p.A: no fact of method take(p.Hidden,p.B), which mentions types that are not public:"
                                + " p.Hidden");
    }

    @Test
    void anInterfaceWaitsForATypeItsInheritedSamMentions() {
        Compiled compilation = process(
                        """
                package gen;
                @me.supcheg.javafile.facts.meta.Facts(p.Sub.class)
                class G {}
                """,
                        "package p; public interface Base { gen.Missing run(); }",
                        "package p; public interface Sub extends Base {}")
                .orFail();

        Map<String, String> sources = compilation.sources();
        assertThat(sources.get("gen.facts.p.Sub_"))
                .contains("Sam0<Sub, Missing> sam")
                .contains("Missing_.Data.SHAPE");
        assertThat(sources.keySet()).contains("gen.facts.gen.Missing_");
    }
}
