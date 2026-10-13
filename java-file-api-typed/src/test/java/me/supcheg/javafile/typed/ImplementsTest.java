package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.Implemented;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.facts.UnsafeInvocables;
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
                public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                    var cmp = new Comparable_<>(h.self());
                    var compareTo = h.overriding(cmp.compareTo_T);
                    var run = h.overriding(Runnable_.run);
                    var oh = h.extendsObject();
                    Upcast<Self, Comparable<Self>> asComparable =
                            oh.implements_(cmp.implement(h.self()).compareTo_T(compareTo));
                    Upcast<Self, Runnable> asRunnable =
                            oh.implements_(Runnable_.implement(h.self()).run(run));
                    var cb = oh.members();

                    var rank = cb.declareMutableField("rank", PrimitiveToken.INT);
                    var toString = cb.declareOverride(Object_.toString);
                    // by the fact of the class, and by that of the interface through the witness
                    var own = cb.declareMethod("own", PrimitiveToken.INT, cb.self());
                    var witnessed = cb.declareMethod("witnessed", PrimitiveToken.INT, cb.self());
                    var ranked = cb.declareVoidMethod("ranked");
                    var bodies = cb.bodies();
                    bodies.initialize(rank, literal(1));
                    bodies.define(
                            compareTo.method(),
                            (b, self, other) -> b.return_(subInt(field(self, rank), field(other, rank))));
                    bodies.define(
                            run.method(),
                            (b, self) ->
                                    b.exec(assignField(self, rank, literal(7))).end());
                    bodies.define(toString, (b, self) -> b.return_(literal("ranked")));
                    bodies.define(own, (b, self, other) -> b.return_(call(self, compareTo.method(), other)));
                    bodies.define(
                            witnessed,
                            (b, self, other) -> b.return_(call(asComparable.apply(self), cmp.compareTo_T, other)));
                    bodies.define(
                            ranked,
                            (b, self) -> b.exec(Expressions.voidCall(asRunnable.apply(self), Runnable_.run))
                                    .end());
                    return bodies;
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
                public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                    // call() of the class declares IOException alone; that of Callable declares Exception
                    var call = h.throwing(IOException_.TOKEN).overriding(CALLABLE.call);
                    var oh = h.extendsObject();
                    Upcast<Self, Callable<String>> asCallable =
                            oh.implements_(CALLABLE.implement(h.self()).call(call));
                    var cb = oh.members();
                    var narrow = cb.throwing(IOException_.TOKEN).declareMethod("narrow", String_.TOKEN);
                    var wide = cb.throwing(Exception_.TOKEN).declareMethod("wide", String_.TOKEN);
                    var bodies = cb.bodies();
                    bodies.define(call.method(), (b, self) -> b.return_(literal("called")));
                    bodies.define(narrow, (b, self) -> b.return_(call(self, call.method())));
                    bodies.define(wide, (b, self) -> b.return_(call(asCallable.apply(self), CALLABLE.call)));
                    return bodies;
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
                public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                    var compare = h.overriding(COMPARATOR.compare_T_T);
                    var oh = h.extendsObject();
                    Upcast<Self, Comparator<String>> asComparator =
                            oh.implements_(COMPARATOR.implement(h.self()).compare_T_T(compare));
                    var cb = oh.members();
                    var reversed = cb.declareOverride(asComparator, COMPARATOR.reversed);
                    var bodies = cb.bodies();
                    bodies.define(compare.method(), (b, self, l, r) -> b.return_(literal(0)));
                    bodies.define(reversed, (b, self) -> b.return_(asComparator.apply(self)));
                    return bodies;
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
                public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                    var oh = h.extendsObject();
                    Upcast<Self, Named> asNamed = oh.implements_(Named_.implement(h.self()));
                    oh.implements_(Titled_.implement(h.self()));
                    var cb = oh.members();
                    var label = cb.declareOverride(asNamed, Named_.label);
                    var bodies = cb.bodies();
                    bodies.define(label, (b, self) -> b.return_(concat(literal("both"), literal("!"))));
                    return bodies;
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
                                        public <Self> TypedClassBodies<Self> build(TypedClassBuilder<Self> cb) {
                                            return cb.bodies();
                                        }
                                    })
                            .render())
                    .contains("public final class Probe {");
        }
    }

    @Nested
    class Stages {

        /// A header that is to be rejected on its way to the members.
        private void header(Consumer<ClassHeader<?>> spec) {
            file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                    spec.accept(h);
                    return Unreached.here();
                }
            });
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
                        oh.members().bodies();
                        oh.members().bodies();
                    }))
                    .withMessageContaining("the members of class me.supcheg.example.Probe do not begin here");
            assertThatIllegalStateException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            var bodies = oh.members().bodies();
                            oh.implements_(Named_.implement(h.self()));
                            return bodies;
                        }
                    }))
                    .withMessageContaining("a superinterface is named after the superclass and before the members");
            assertThatIllegalStateException()
                    .isThrownBy(() -> header(h -> {
                        h.extendsObject().members().bodies();
                        h.overriding(Object_.toString);
                    }))
                    .withMessageContaining("the header of the class has ended");
        }

        @Test
        void aMethodTheClassSaysItOverridesIsDefined() {
            assertThatIllegalStateException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                            h.overriding(Object_.toString);
                            return h.extendsObject().members().bodies();
                        }
                    }))
                    .withMessageContaining("these declared members are never defined:"
                            + " java.lang.String me.supcheg.example.Probe.toString()");
            // javac: a class that is not abstract has every abstract method of its interfaces
            assertThatIllegalStateException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                            var run = h.overriding(Runnable_.run);
                            var oh = h.extendsObject();
                            oh.implements_(Runnable_.implement(h.self()).run(run));
                            return oh.members().bodies();
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
                        h.extendsObject().members().bodies();
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
                        h.extendsObject().members().declareOverride(Object_.toString);
                    }))
                    .withMessageContaining("declared before: both erase to toString()");
            assertThat(JavacVerdict.compiledClass(
                            "",
                            "@Override public String toString() { return \"x\"; }"
                                    + " @Override public String toString() { return \"y\"; }"))
                    .hadErrorContaining("method toString() is already defined in class");
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> header(h -> h.overriding(Object_.notify)))
                    .withMessageContaining("the method is final");
            assertThat(JavacVerdict.compiledClass("", "@Override public void notify() {}"))
                    .hadErrorContaining("overridden method is final");
        }
    }

    @Nested
    class Supertypes {

        @Test
        void aProofOfAClassIsNoProofOfAnInterface() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                            h.extendsObject().implements_(Exception_.extend(h.self()));
                            return Unreached.here();
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
                        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            oh.implements_(Named_.implement(h.self()));
                            oh.implements_(Named_.implement(h.self()));
                            return Unreached.here();
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
                        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                            h.extendsObject()
                                    .implements_(UnsafeFacts.steps(Comparable_.ANY, h.self())
                                            .complete());
                            return Unreached.here();
                        }
                    }))
                    .withMessageContaining("a supertype has no wildcard type argument");
            assertThat(JavacVerdict.compiledClass(
                            "implements Comparable<?>", "public int compareTo(Object o) { return 0; }"))
                    .hadErrorContaining("unexpected type");
            // a raw supertype: javac takes it, and warns
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                            h.extendsObject()
                                    .implements_(UnsafeFacts.steps(
                                                    UnsafeFacts.<Comparable<Object>>interfaceToken(
                                                            Comparable_.Data.SHAPE),
                                                    h.self())
                                            .complete());
                            return Unreached.here();
                        }
                    }))
                    .withMessageContaining("cannot have the raw supertype java.lang.Comparable: the type is"
                            + " generic, name it with its type arguments");
            assertThat(JavacVerdict.compiledClass(
                            "implements Comparable", "public int compareTo(Object o) { return 0; }"))
                    .hadWarningContaining("found raw type: java.lang.Comparable");
        }

        @Test
        void aProofIsHeldAgainstTheTypeOfTheTarget() {
            // a proof without a step, as a metamodel of a Runnable that had no abstract method gave it
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                            h.extendsObject()
                                    .implements_(UnsafeFacts.steps(Runnable_.TOKEN, h.self())
                                            .complete());
                            return Unreached.here();
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
                        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                            h.extendsObject()
                                    .implements_(Runnable_.implement(h.self())
                                            .run(UnsafeInvocables.voidInherited0(
                                                    new Implemented.ByInheritance(Runnable_.run))));
                            return Unreached.here();
                        }
                    }))
                    .withMessageContaining("by inheritance: it extends Object, which has no such method");
        }

        @Test
        void anOverrideDeclaresNoExceptionTheInterfaceMethodDoesNot() {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                            var run = h.throwing(IOException_.TOKEN).overriding(Runnable_.run);
                            h.extendsObject()
                                    .implements_(Runnable_.implement(h.self()).run(run));
                            return Unreached.here();
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
                        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            oh.implements_(Named_.implement(h.self()));
                            oh.members().declareMethod("label", String_.TOKEN);
                            return Unreached.here();
                        }
                    }))
                    .withMessageContaining("has the signature of label() of me.supcheg.javafile.typed.fixtures.Named,"
                            + " which the class inherits");
            // stricter than javac, to which a method of that signature overrides whether it says so or not
            assertThat(JavacVerdict.compiledClass("implements Named", "public String label() { return \"\"; }"))
                    .succeeded();
            // compareTo(Object) beside the compareTo(Probe) of Comparable<Probe>: the bridge of one is the other
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                            var cmp = new Comparable_<>(h.self());
                            var compareTo = h.overriding(cmp.compareTo_T);
                            var oh = h.extendsObject();
                            oh.implements_(cmp.implement(h.self()).compareTo_T(compareTo));
                            oh.members().declareMethod("compareTo", PrimitiveToken.INT, Object_.TOKEN);
                            return Unreached.here();
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
                        public <Self> MemberBodies<Self, ?> build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            oh.implements_(Named_.implement(h.self()));
                            oh.implements_(Titled_.implement(h.self()));
                            return oh.members().bodies();
                        }
                    }))
                    .withMessageContaining("overrides none of them")
                    .withMessageContaining("are unrelated methods with a body each");
            assertThat(JavacVerdict.compiledClass("implements Named, Titled", ""))
                    .hadErrorContaining("inherits unrelated defaults for label()");
        }
    }
}
