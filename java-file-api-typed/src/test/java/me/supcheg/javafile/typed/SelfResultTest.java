package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Copied;
import me.supcheg.javafile.typed.fixtures.Following;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Chained_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Copied_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Following_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Linked_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Origin_;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;
import static me.supcheg.javafile.typed.Subclasses.began;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// An override whose result is the class itself (JLS 8.4.5), of a method
/// whose result is not `Object`: said by a witness that the class is of the
/// result type, the brand of the class being a subtype of nothing to the
/// compiler of the generator — the witness of a supertype the class has
/// named, or of one it says it is about to name ([Overriding#becoming]). The
/// classes are compiled under `-Xlint:all` and run.
class SelfResultTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Test
    void anAbstractMethodOfAnInterfaceIsAnsweredWithTheClassByTheWitnessOfTheInterfaceItIsAboutToName()
            throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                // Following next()
                Upcast<Self, Following> asFollowing = h.becoming(Following_.TOKEN);
                var next = h.overriding(Following_.next, asFollowing);
                var oh = h.extendsObject();
                oh.implements_(Following_.implement(h.self()).next(next));
                var cb = oh.body();
                cb.define(next.method(), (b, self) -> b.return_(self));
                // the fact of the class is of the class: its result has the methods of the class
                cb.method("twice", cb.self(), (b, self) -> b.return_(call(call(self, next.method()), next.method())));
            }
        }));

        assertThat(compiled.source())
                .contains("public final class Probe implements Following {")
                .contains("@Override\n    public Probe next() {")
                .contains("return this.next().next();");
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThat(((Following) probe).next()).isSameAs(probe);
        assertThat(compiled.generated().getMethod("twice").invoke(probe)).isSameAs(probe);
    }

    @Test
    void theMethodsOfASuperclassAreAnsweredWithTheClass() throws Throwable {
        CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                // abstract Copied copy(): the intention is what the proof takes, so the witness is a promise
                var copy = h.overriding(Copied_.copy, h.becoming(Copied_.TOKEN));
                var sh = h.extends_(Copied_.extend(h.self()).copy(copy));
                var cb = began(sh, ctor -> ctor.super_(Copied_.super_, (b, self) -> b.end()));
                cb.define(copy.method(), (b, self) -> b.return_(self));
                // Copied same() and Copied other(String) have bodies: overridden among the members
                var same = cb.override(Copied_.same, sh.asSuper(), (b, self) -> b.return_(self));
                cb.override(sh.asSuper(), Copied_.other_String, sh.asSuper(), (b, self, label) -> b.return_(self));
                cb.method("chain", cb.self(), (b, self) -> b.return_(call(call(self, copy.method()), same)));
            }
        }));

        assertThat(compiled.source())
                .contains("public final class Probe extends Copied {")
                .contains("@Override\n    public Probe copy() {")
                .contains("@Override\n    public Probe same() {")
                .contains("@Override\n    public Probe other(String v0) {")
                .contains("return this.copy().same();");
        Object probe = compiled.generated().getConstructor().newInstance();
        assertThat(((Copied) probe).copy()).isSameAs(probe);
        assertThat(((Copied) probe).other("x")).isSameAs(probe);
        assertThat(compiled.generated().getMethod("chain").invoke(probe)).isSameAs(probe);
    }

    @Test
    void aSupertypeTheClassIsAboutToNameAnswersTheOneNamedBeforeIt() {
        // Linked next() of Chained has a body and is named first; that the class is a Linked is said before
        String source = file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var next = h.overriding(Linked_.next, h.becoming(Linked_.TOKEN));
                        var oh = h.extendsObject();
                        oh.implements_(Chained_.implement(h.self()));
                        oh.implements_(Linked_.implement(h.self()).next(next));
                        oh.body().define(next.method(), (b, self) -> b.return_(self));
                    }
                })
                .render();

        assertThat(source)
                .contains("public final class Probe implements Chained, Linked {")
                .contains("@Override\n    public Probe next() {");
        com.google.testing.compile.CompilationSubject.assertThat(com.google.testing.compile.Compiler.javac()
                        .withOptions("-Xlint:all")
                        .compile(com.google.testing.compile.JavaFileObjects.forSourceString(
                                "me.supcheg.example.Probe", source)))
                .succeededWithoutWarnings();
    }

    @Test
    void aClassThatNeverNamesTheSupertypeItSaidItIsAboutToHasNoMembers() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        h.becoming(Following_.TOKEN);
                        var oh = h.extendsObject();
                        oh.implements_(Runnable_.implement(h.self()).run(h.overriding(Runnable_.run)));
                        oh.body();
                    }
                }))
                .withMessageContaining("class me.supcheg.example.Probe said it was about to be"
                        + " me.supcheg.javafile.typed.fixtures.Following (becoming), and its header ends without it");
    }

    @Test
    void theWitnessOfASupertypeIsNotAskedForOnceTheMembersBegan() {
        assertThatIllegalStateException()
                .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        var oh = h.extendsObject();
                        oh.body();
                        h.becoming(Following_.TOKEN);
                    }
                }))
                .withMessageContaining("the header of the class has ended");
    }

    @Test
    void theClassIsProvenOfTheResultOfEveryMethodItInheritsUnderTheSignature() {
        // Object next() of Linked is answered with the class by the witness of Origin, which the compiler of
        // the generator takes; Linked next() of Chained asks for a Linked, which the class is not
        classAsJavac(
                IllegalArgumentException.class,
                () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                    @Override
                    public <Self> void build(ClassHeader<Self> h) {
                        h.overriding(Linked_.next, h.becoming(Origin_.TOKEN));
                        var oh = h.extendsObject();
                        oh.implements_(Chained_.implement(h.self()));
                    }
                }),
                "cannot override next() of me.supcheg.javafile.typed.fixtures.Chained: its result is"
                        + " me.supcheg.javafile.typed.fixtures.Linked, and that of the method"
                        + " me.supcheg.example.Probe: the return types are incompatible (JLS 8.4.8.3)",
                "implements Chained",
                "@Override public Probe next() { return this; }",
                "return type me.supcheg.example.Probe is not compatible with me.supcheg.javafile.typed.fixtures.Linked");
    }

    @Test
    void theMethodOfTheClassIsOfTheClassAndNotOfTheSupertypeOfTheWitness() {
        // the implementation is typed by the supertype, for the step; its method by the class
        file(new TypedJavaFile.TypedClassHeaderSpec() {
            @Override
            public <Self> void build(ClassHeader<Self> h) {
                var next = h.overriding(Following_.next, h.becoming(Following_.TOKEN));
                assertThat(next.method().result()).isSameAs(h.self());
                assertThat(next.method().owner()).isSameAs(h.self());
                var oh = h.extendsObject();
                oh.implements_(Following_.implement(h.self()).next(next));
                oh.body().define(next.method(), (b, self) -> b.return_(self));
            }
        });
    }
}
