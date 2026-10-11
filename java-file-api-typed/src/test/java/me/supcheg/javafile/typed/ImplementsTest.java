package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.Implemented;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Named;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.lang.Comparable_;
import me.supcheg.javafile.typed.testfacts.java.lang.Exception_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.Runnable_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.Comparator_;
import me.supcheg.javafile.typed.testfacts.java.util.concurrent.Callable_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Named_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Titled_;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.typed.Expressions.assignField;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.concat;
import static me.supcheg.javafile.typed.Expressions.field;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.subInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/// A class that implements interfaces (§8d): the header of the class, the
/// proof it implements an interface by, the witness the proof gives, and the
/// checks of what the class inherits. The classes that are accepted are
/// compiled with `-Xlint:all` and run; what is rejected is rejected where it
/// is said, and javac is asked about the Java the declaration would have been
/// — where the typed layer is stricter than javac, the test says so.
class ImplementsTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final Callable_<String> CALLABLE = new Callable_<>(String_.TOKEN);
    private static final Comparator_<String> COMPARATOR = new Comparator_<>(String_.TOKEN);

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Nested
    class Accepted {

        @Test
        void aClassImplementsInterfacesByProofAndIsOfThemByWitness() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var cmp = new Comparable_<>(h.self());
                    var compareTo = h.overriding(cmp.compareTo_T);
                    var run = h.overriding(Runnable_.run);
                    var oh = h.extendsObject();
                    Upcast<Self, Comparable<Self>> asComparable =
                            oh.implements_(cmp.implement(h.self()).compareTo_T(compareTo));
                    Upcast<Self, Runnable> asRunnable =
                            oh.implements_(Runnable_.implement(h.self()).run(run));
                    var cb = oh.body();

                    var rank = cb.mutableField("rank", PrimitiveToken.INT, literal(1));
                    cb.define(
                            compareTo.method(),
                            (b, self, other) -> b.return_(subInt(field(self, rank), field(other, rank))));
                    cb.define(
                            run.method(),
                            (b, self) ->
                                    b.exec(assignField(self, rank, literal(7))).end());
                    cb.override(Object_.toString, (b, self) -> b.return_(literal("ranked")));
                    // by the fact of the class, and by that of the interface through the witness
                    cb.method(
                            "own",
                            PrimitiveToken.INT,
                            cb.self(),
                            (b, self, other) -> b.return_(call(self, compareTo.method(), other)));
                    cb.method(
                            "witnessed",
                            PrimitiveToken.INT,
                            cb.self(),
                            (b, self, other) -> b.return_(call(asComparable.apply(self), cmp.compareTo_T, other)));
                    cb.voidMethod(
                            "ranked",
                            (b, self) -> b.exec(Expressions.voidCall(asRunnable.apply(self), Runnable_.run))
                                    .end());
                }
            }));

            assertThat(compiled.source())
                    .contains("public final class Probe implements Comparable<Probe>, Runnable {")
                    .contains("@Override\n    public int compareTo(Probe v0) {")
                    .contains("@Override\n    public void run() {")
                    .contains("@Override\n    public String toString() {")
                    .contains("return this.compareTo(v0);");
            Object probe = compiled.generated().getConstructor().newInstance();
            Object other = compiled.generated().getConstructor().newInstance();
            assertThat(probe).isInstanceOf(Comparable.class).isInstanceOf(Runnable.class);
            ((Runnable) probe).run();
            @SuppressWarnings("unchecked")
            Comparable<Object> comparable = (Comparable<Object>) probe;
            assertThat(comparable.compareTo(other)).isEqualTo(6);
            assertThat(probe).hasToString("ranked");
        }

        @Test
        void aCallThroughAWitnessGoesByTheThrowsClauseOfTheInterface() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    // call() of the class declares IOException alone; that of Callable declares Exception
                    var call = h.throwing(IOException_.TOKEN).overriding(CALLABLE.call);
                    var oh = h.extendsObject();
                    Upcast<Self, Callable<String>> asCallable =
                            oh.implements_(CALLABLE.implement(h.self()).call(call));
                    var cb = oh.body();
                    cb.define(call.method(), (b, self) -> b.return_(literal("called")));
                    cb.throwing(IOException_.TOKEN)
                            .method("narrow", String_.TOKEN, (b, self) -> b.return_(call(self, call.method())));
                    cb.throwing(Exception_.TOKEN)
                            .method(
                                    "wide",
                                    String_.TOKEN,
                                    (b, self) -> b.return_(call(asCallable.apply(self), CALLABLE.call)));
                }
            }));

            assertThat(compiled.source())
                    .contains("public String call() throws IOException {")
                    .contains("return this.call();")
                    .contains("return ((Callable<String>) this).call();");
            assertThat(compiled.invoke("wide")).isEqualTo("called");
            assertThat(compiled.generated().getMethod("call").getExceptionTypes())
                    .containsExactly(IOException.class);
        }

        @Test
        void aDefaultMethodIsOverriddenThroughTheWitness() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var compare = h.overriding(COMPARATOR.compare_T_T);
                    var oh = h.extendsObject();
                    Upcast<Self, Comparator<String>> asComparator =
                            oh.implements_(COMPARATOR.implement(h.self()).compare_T_T(compare));
                    var cb = oh.body();
                    cb.define(compare.method(), (b, self, l, r) -> b.return_(literal(0)));
                    cb.override(asComparator, COMPARATOR.reversed, (b, self) -> b.return_(asComparator.apply(self)));
                }
            }));

            assertThat(compiled.source())
                    .contains("implements Comparator<String>")
                    .contains("@Override\n    public Comparator<String> reversed() {\n        return this;");
            Object probe = compiled.generated().getConstructor().newInstance();
            assertThat(((Comparator<?>) probe).reversed()).isSameAs(probe);
        }

        @Test
        void aMethodTwoUnrelatedInterfacesGiveABodyIsOverridden() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var oh = h.extendsObject();
                    Upcast<Self, Named> asNamed = oh.implements_(Named_.implement(h.self()));
                    oh.implements_(Titled_.implement(h.self()));
                    var cb = oh.body();
                    cb.override(asNamed, Named_.label, (b, self) -> b.return_(concat(literal("both"), literal("!"))));
                }
            }));

            assertThat(compiled.source()).contains("implements Named, Titled");
            assertThat(((Named) compiled.generated().getConstructor().newInstance()).label())
                    .isEqualTo("both!");
        }

        @Test
        void theClassOfAMembersOnlySpecificationExtendsObjectAndImplementsNothing() {
            assertThat(TypedJavaFile.class_(
                                    UnsafeFacts.unverifiedClasspath(), DESC, new TypedJavaFile.TypedClassSpec() {
                                        @Override
                                        public <Self> void build(TypedClassBuilder<Self> cb) {}
                                    })
                            .render())
                    .contains("public final class Probe {");
        }
    }

    @Nested
    class Stages {

        private void header(Consumer<ClassHeader<?>> spec) {
            file(spec::accept);
        }

        @Test
        void aHeaderThatNeverComesToTheMembersIsRejected() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> header(h -> {}))
                    .withMessageContaining("its header never came to the members: end it with extendsObject().body()");
            assertThatIllegalStateException()
                    .isThrownBy(() -> header(ClassHeader::extendsObject))
                    .withMessageContaining("its header never came to the members");
        }

        @Test
        void aStageIsUsedOnce() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> header(h -> {
                        h.extendsObject();
                        h.extendsObject();
                    }))
                    .withMessageContaining("has its superclass already");
            assertThatIllegalStateException()
                    .isThrownBy(() -> header(h -> {
                        var oh = h.extendsObject();
                        oh.body();
                        oh.body();
                    }))
                    .withMessageContaining("the members of class me.supcheg.example.Probe do not begin here");
            assertThatIllegalStateException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            oh.body();
                            oh.implements_(Named_.implement(h.self()));
                        }
                    }))
                    .withMessageContaining("a superinterface is named after the superclass and before the members");
            assertThatIllegalStateException()
                    .isThrownBy(() -> header(h -> {
                        h.extendsObject().body();
                        h.overriding(Object_.toString);
                    }))
                    .withMessageContaining("the header of the class has ended");
        }

        @Test
        void aMethodTheClassSaysItOverridesIsDefined() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> header(h -> {
                        h.overriding(Object_.toString);
                        h.extendsObject().body();
                    }))
                    .withMessageContaining("these declared members are never defined:"
                            + " java.lang.String me.supcheg.example.Probe.toString()");
            // javac: a class that is not abstract has every abstract method of its interfaces
            assertThatIllegalStateException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var run = h.overriding(Runnable_.run);
                            var oh = h.extendsObject();
                            oh.implements_(Runnable_.implement(h.self()).run(run));
                            oh.body();
                        }
                    }))
                    .withMessageContaining("never defined: void me.supcheg.example.Probe.run()");
            assertThat(JavacVerdict.compiledClass("implements Runnable", ""))
                    .hadErrorContaining("Probe is not abstract and does not override abstract method run()");
        }

        @Test
        void aMethodNoSupertypeHasIsNotOverridden() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> header(h -> {
                        h.overriding(Runnable_.run);
                        h.extendsObject().body();
                    }))
                    .withMessageContaining("overrides nothing: no supertype of the class has a method run()");
            assertThat(JavacVerdict.compiledClass("", "@Override public void run() {}"))
                    .hadErrorContaining("method does not override or implement a method from a supertype");
        }

        @Test
        void aMethodIsOverriddenByOneMethodOfTheClass() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> header(h -> {
                        h.overriding(Object_.toString);
                        h.overriding(Object_.toString);
                    }))
                    .withMessageContaining("overrides toString() already");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> header(h -> {
                        h.overriding(Object_.toString);
                        h.extendsObject().body().override(Object_.toString, (b, _) -> b.return_(literal("x")));
                    }))
                    .withMessageContaining("declared before: both erase to toString()");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> header(h -> h.overriding(Object_.notify)))
                    .withMessageContaining("the method is final");
        }
    }

    @Nested
    class Supertypes {

        @Test
        void aProofOfAClassIsNoProofOfAnInterface() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            h.extendsObject().implements_(Exception_.extend(h.self()));
                        }
                    }))
                    .withMessageContaining("cannot implement java.lang.Exception: it is a class");
            assertThat(JavacVerdict.compiledClass("implements Exception", ""))
                    .hadErrorContaining("interface expected here");
        }

        @Test
        void anInterfaceIsImplementedOnce() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            oh.implements_(Named_.implement(h.self()));
                            oh.implements_(Named_.implement(h.self()));
                        }
                    }))
                    .withMessageContaining("has the supertype me.supcheg.javafile.typed.fixtures.Named already");
            assertThat(JavacVerdict.compiledClass("implements Named, Named", ""))
                    .hadErrorContaining("repeated interface");
        }

        @Test
        void aSupertypeHasNoWildcardAndIsNotRaw() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            h.extendsObject()
                                    .implements_(UnsafeFacts.steps(Comparable_.ANY, h.self())
                                            .complete());
                        }
                    }))
                    .withMessageContaining("a supertype has no wildcard type argument");
            assertThat(JavacVerdict.compiledClass(
                            "implements Comparable<?>", "public int compareTo(Object o) { return 0; }"))
                    .hadErrorContaining("unexpected type");
        }

        @Test
        void aProofIsHeldAgainstTheTypeOfTheTarget() {
            // a proof without a step, as a metamodel of a Runnable that had no abstract method gave it
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            h.extendsObject()
                                    .implements_(UnsafeFacts.steps(Runnable_.TOKEN, h.self())
                                            .complete());
                        }
                    }))
                    .withMessageContaining("on the target classpath the type has the abstract method run() of"
                            + " java.lang.Runnable, which the proof has no step for");
        }

        @Test
        void aStepTakesAnImplementationOfTheHeader() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            h.extendsObject()
                                    .implements_(Runnable_.implement(h.self())
                                            .run(UnsafeFacts.voidImplementation0(
                                                    new Implemented.ByInheritance(Runnable_.run))));
                        }
                    }))
                    .withMessageContaining("by inheritance: it extends Object, which has no such method");
        }

        @Test
        void anOverrideDeclaresNoExceptionTheInterfaceMethodDoesNot() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var run = h.throwing(IOException_.TOKEN).overriding(Runnable_.run);
                            h.extendsObject()
                                    .implements_(Runnable_.implement(h.self()).run(run));
                        }
                    }))
                    .withMessageContaining("cannot override run() of java.lang.Runnable: the method declares no"
                            + " exception: an override cannot declare the checked exception java.io.IOException");
            assertThat(JavacVerdict.compiledClass(
                            "implements Runnable", "public void run() throws java.io.IOException {}"))
                    .hadErrorContaining("overridden method does not throw java.io.IOException");
        }

        @Test
        void aMethodDeclaredByNameDoesNotImplementOrClash() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            oh.implements_(Named_.implement(h.self()));
                            oh.body().declareMethod("label", String_.TOKEN);
                        }
                    }))
                    .withMessageContaining("has the signature of label() of me.supcheg.javafile.typed.fixtures.Named,"
                            + " which the class inherits");
            // compareTo(Object) beside the compareTo(Probe) of Comparable<Probe>: the bridge of one is the other
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var cmp = new Comparable_<>(h.self());
                            var compareTo = h.overriding(cmp.compareTo_T);
                            var oh = h.extendsObject();
                            oh.implements_(cmp.implement(h.self()).compareTo_T(compareTo));
                            oh.body().declareMethod("compareTo", PrimitiveToken.INT, Object_.TOKEN);
                        }
                    }))
                    .withMessageContaining("name clash");
            assertThat(JavacVerdict.compiledClass(
                            "implements Comparable<Probe>",
                            "public int compareTo(Probe o) { return 0; } public int compareTo(Object o) { return 0; }"))
                    .hadErrorContaining("name clash");
        }

        @Test
        void aMethodTwoUnrelatedInterfacesGiveABodyHasToBeOverridden() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            oh.implements_(Named_.implement(h.self()));
                            oh.implements_(Titled_.implement(h.self()));
                            oh.body();
                        }
                    }))
                    .withMessageContaining("unrelated methods with a body each, and overrides neither");
            assertThat(JavacVerdict.compiledClass("implements Named, Titled", ""))
                    .hadErrorContaining("inherits unrelated defaults for label()");
        }
    }
}
