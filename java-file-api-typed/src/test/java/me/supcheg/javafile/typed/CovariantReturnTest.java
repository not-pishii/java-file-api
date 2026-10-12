package me.supcheg.javafile.typed;

import me.supcheg.javafile.JavaFile;
import me.supcheg.javafile.facts.ArrayToken;
import me.supcheg.javafile.facts.PrimitiveToken;
import me.supcheg.javafile.facts.UnsafeFacts;
import me.supcheg.javafile.typed.fixtures.Counted;
import me.supcheg.javafile.typed.fixtures.Origin;
import me.supcheg.javafile.typed.fixtures.Resetting;
import me.supcheg.javafile.typed.fixtures.Worded;
import me.supcheg.javafile.typed.testfacts.java.io.IOException_;
import me.supcheg.javafile.typed.testfacts.java.lang.CharSequence_;
import me.supcheg.javafile.typed.testfacts.java.lang.Exception_;
import me.supcheg.javafile.typed.testfacts.java.lang.IllegalStateException_;
import me.supcheg.javafile.typed.testfacts.java.lang.Integer_;
import me.supcheg.javafile.typed.testfacts.java.lang.Object_;
import me.supcheg.javafile.typed.testfacts.java.lang.String_;
import me.supcheg.javafile.typed.testfacts.java.util.ArrayList_;
import me.supcheg.javafile.typed.testfacts.java.util.List_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Counted_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Labelled_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Maker_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Measured_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Mid_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Numbered_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Origin_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Resetting_;
import me.supcheg.javafile.typed.testfacts.me.supcheg.javafile.typed.fixtures.Worded_;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static me.supcheg.javafile.typed.Expressions.assignAt;
import static me.supcheg.javafile.typed.Expressions.call;
import static me.supcheg.javafile.typed.Expressions.eqRef;
import static me.supcheg.javafile.typed.Expressions.literal;
import static me.supcheg.javafile.typed.Expressions.newArray;
import static me.supcheg.javafile.typed.Expressions.new_;
import static me.supcheg.javafile.typed.JavacVerdict.classAsJavac;
import static me.supcheg.javafile.typed.JavacVerdict.classUnlikeJavac;
import static org.assertj.core.api.Assertions.assertThat;

/// An override with a narrower result (§8f, JLS 8.4.5): the forms that say
/// it, the proof it is held to against every method the class inherits under
/// the signature ([Subtyping]), and how a call is rendered once the method of
/// the class is of a narrower type than the fact of a supertype tells — the
/// classes are compiled under `-Xlint:all` and run, and what is rejected is
/// held against javac.
class CovariantReturnTest {
    private static final ClassDesc DESC = ClassDesc.of("me.supcheg.example", "Probe");
    private static final Class<IllegalArgumentException> AT_THE_PLACE = IllegalArgumentException.class;

    private static JavaFile file(TypedJavaFile.TypedClassHeaderSpec spec) {
        return TypedJavaFile.declareClass(UnsafeFacts.unverifiedClasspath(), DESC, spec);
    }

    @Nested
    class Accepted {

        @Test
        void oneMethodAnswersTwoInterfacesWithAResultNarrowerThanBoth() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    // Object get() of Origin, CharSequence get() of Worded
                    var get = h.overriding(Origin_.get, String_.TOKEN);
                    var oh = h.extendsObject();
                    Upcast<Self, Origin> asOrigin =
                            oh.implements_(Origin_.implement(h.self()).get(get));
                    Upcast<Self, Worded> asWorded =
                            oh.implements_(Worded_.implement(h.self()).get(get));
                    var cb = oh.members();
                    // the fact of the class is of the narrower result, those of the interfaces of theirs
                    var own = cb.declareMethod("own", PrimitiveToken.INT);
                    var worded = cb.declareMethod("worded", PrimitiveToken.INT);
                    var origin = cb.declareMethod("origin", Object_.TOKEN);
                    var bodies = cb.bodies();
                    bodies.define(get.method(), (b, self) -> b.return_(literal("mine")));
                    bodies.define(own, (b, self) -> b.return_(call(call(self, get.method()), String_.length)));
                    bodies.define(
                            worded,
                            (b, self) ->
                                    b.return_(call(call(asWorded.apply(self), Worded_.get), CharSequence_.length)));
                    bodies.define(origin, (b, self) -> b.return_(call(asOrigin.apply(self), Origin_.get)));
                }
            }));

            assertThat(compiled.source())
                    .contains("public final class Probe implements Origin, Worded {")
                    .contains("@Override\n    public String get() {")
                    .contains("return this.get().length();");
            Object probe = compiled.generated().getConstructor().newInstance();
            assertThat(((Origin) probe).get()).isEqualTo("mine");
            assertThat(((Worded) probe).get()).isEqualTo("mine");
            assertThat(compiled.invoke("own")).isEqualTo(4);
            assertThat(compiled.invoke("worded")).isEqualTo(4);
            assertThat(compiled.invoke("origin")).isEqualTo("mine");
        }

        @Test
        void cloneOfAClassReturnsTheClass() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var cb = h.extendsObject().members();
                    var clone = cb.declareOverride(cb.protected_(Object_.clone), cb.self());
                    var twice = cb.declareMethod("twice", cb.self());
                    var bodies = cb.bodies();
                    bodies.define(clone, (b, self) -> b.return_(self));
                    bodies.define(twice, (b, self) -> b.return_(call(call(self, clone), clone)));
                }
            }));

            assertThat(compiled.source())
                    .contains("@Override\n    public Probe clone() {")
                    .contains("return this.clone().clone();");
            Object probe = compiled.generated().getConstructor().newInstance();
            assertThat(compiled.generated().getMethod("clone").invoke(probe)).isSameAs(probe);
        }

        @Test
        void aMethodOfTheSuperclassIsOverriddenWithANarrowerResult() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var sh = h.extends_(Maker_.extend(h.self()));
                    var strings = new ArrayList_<>(String_.TOKEN);
                    var ctor1 = sh.declareConstructor();
                    var cb = sh.members();
                    // Object make()
                    var make = cb.declareOverride(Maker_.make, String_.TOKEN);
                    // Collection<String> words(): an ArrayList<String> is one, a List<String> too
                    var words = cb.declareOverride(Maker_.words, strings.token);
                    // Collection<? extends CharSequence> texts(): of the very type argument alone
                    var asSuper =
                            cb.declareOverride(sh.asSuper(), Maker_.texts, new List_<>(CharSequence_.TOKEN).token);
                    var made = cb.declareMethod("made", PrimitiveToken.INT);
                    var bodies = cb.bodies(ctor1, ctor -> ctor.super_(Maker_.new_, (b, self) -> b.end()));
                    bodies.define(make, (b, self) -> b.return_(literal("mine")));
                    bodies.define(words, (b, self) -> b.return_(new_(strings.new_)));
                    bodies.define(asSuper, (b, self) -> b.return_(literalNullList()));
                    bodies.define(made, (b, self) -> b.return_(call(call(self, make), String_.length)));
                }

                private static Expr<java.util.List<CharSequence>> literalNullList() {
                    return Expressions.literalNull(new List_<>(CharSequence_.TOKEN).token);
                }
            }));

            assertThat(compiled.source())
                    .contains("public final class Probe extends Maker {")
                    .contains("@Override\n    public String make() {")
                    .contains("@Override\n    public ArrayList<String> words() {")
                    .contains("@Override\n    public List<CharSequence> texts() {");
            assertThat(compiled.invoke("made")).isEqualTo(4);
            assertThat(compiled.invoke("make")).isEqualTo("mine");
        }

        @Test
        void aMethodOfTheSuperclassStandsForThatOfAnInterfaceWithANarrowerResult() throws Throwable {
            // String label() of Mid, CharSequence label() of Labelled
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var sh = h.extends_(Mid_.extend(h.self()));
                    sh.implements_(Labelled_.implement(h.self()).label(sh.inherited(Mid_.label)));
                    var ctor4 = sh.declareConstructor();
                    sh.members().bodies(ctor4, ctor -> ctor.super_(Mid_.new_, (b, self) -> b.end()));
                }
            }));

            assertThat(compiled.source()).contains("public final class Probe extends Mid implements Labelled {");
            Object probe = compiled.generated().getConstructor().newInstance();
            assertThat(((me.supcheg.javafile.typed.fixtures.Labelled) probe).label())
                    .isEqualTo("mid");
        }
    }

    /// The class has `String get()` for `Object get()` of [Origin]: javac types `this.get()` by the
    /// method of the class, the typed layer a call by the fact of the interface as an `Object`.
    /// Where the type decides, the call is rendered through the interface.
    @Nested
    class Lowered {

        @Test
        void aCallByTheFactOfTheInterfaceKeepsTheOverloadItsTokenChose() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var get = h.overriding(Origin_.get, String_.TOKEN);
                    var oh = h.extendsObject();
                    Upcast<Self, Origin> asOrigin =
                            oh.implements_(Origin_.implement(h.self()).get(get));
                    var cb = oh.members();
                    var whichObject = cb.declareMethod("which", String_.TOKEN, Object_.TOKEN);
                    var whichString = cb.declareMethod("which", String_.TOKEN, String_.TOKEN);
                    var byWitness = cb.declareMethod("byWitness", String_.TOKEN);
                    var byOwn = cb.declareMethod("byOwn", String_.TOKEN);
                    // == of an Object and an Integer: javac rejects that of a String and an Integer
                    var same = cb.declareMethod("same", PrimitiveToken.BOOLEAN, Integer_.TOKEN);
                    // the receiver of a method with parameters: String has overloads Object has not
                    var equal = cb.declareMethod("equal", PrimitiveToken.BOOLEAN, Object_.TOKEN);
                    var text = cb.declareMethod("text", String_.TOKEN);
                    var bodies = cb.bodies();
                    bodies.define(get.method(), (b, self) -> b.return_(literal("mine")));
                    bodies.define(whichObject, (b, self, v) -> b.return_(literal("object")));
                    bodies.define(whichString, (b, self, v) -> b.return_(literal("string")));
                    bodies.define(
                            byWitness,
                            (b, self) -> b.return_(call(self, whichObject, call(asOrigin.apply(self), Origin_.get))));
                    bodies.define(byOwn, (b, self) -> b.return_(call(self, whichString, call(self, get.method()))));
                    bodies.define(same, (b, self, n) -> b.return_(eqRef(call(asOrigin.apply(self), Origin_.get), n)));
                    bodies.define(
                            equal,
                            (b, self, o) ->
                                    b.return_(call(call(asOrigin.apply(self), Origin_.get), Object_.equals_Object, o)));
                    bodies.define(
                            text,
                            (b, self) -> b.return_(call(call(asOrigin.apply(self), Origin_.get), Object_.toString)));
                }
            }));

            assertThat(compiled.source())
                    .contains("return this.which(((Origin) this).get());")
                    .contains("return this.which(this.get());")
                    .contains("return ((Origin) this).get() == v0;")
                    .contains("return ((Origin) this).get().equals(v0);")
                    .contains("return this.get().toString();");
            assertThat(compiled.invoke("byWitness")).isEqualTo("object");
            assertThat(compiled.invoke("byOwn")).isEqualTo("string");
            assertThat(compiled.invoke("same", 1)).isEqualTo(false);
            assertThat(compiled.invoke("equal", "mine")).isEqualTo(true);
            assertThat(compiled.invoke("text")).isEqualTo("mine");
        }

        @Test
        void anArrayAndAnExceptionOfANarrowerTypeAreUsedAsOfTheTypeOfTheFact() throws Throwable {
            CompiledClasses compiled = CompiledClasses.of(file(new TypedJavaFile.TypedClassHeaderSpec() {
                @Override
                public <Self> void build(ClassHeader<Self> h) {
                    var get = h.overriding(Origin_.get);
                    var oh = h.extendsObject();
                    Upcast<Self, Origin> asOrigin =
                            oh.implements_(Origin_.implement(h.self()).get(get));
                    var cb = oh.members();
                    var objects = ArrayToken.of(Object_.TOKEN);
                    var strings = ArrayToken.of(String_.TOKEN);
                    // Object[] all() of Origin as String[] all(), Exception failure() as IllegalStateException
                    var all = cb.declareOverride(asOrigin, Origin_.all, strings);
                    var failure = cb.declareOverride(asOrigin, Origin_.failure, IllegalStateException_.TOKEN);
                    // an Object stored into what javac would type a String[]
                    var store = cb.declareVoidMethod("store", Object_.TOKEN);
                    // an Exception thrown: of an IllegalStateException javac would say no IOException is
                    var caught = cb.declareMethod("caught", String_.TOKEN);
                    var bodies = cb.bodies();
                    bodies.define(get.method(), (b, self) -> b.return_(literal("mine")));
                    bodies.define(all, (b, self) -> b.return_(newArray(strings, literal(1))));
                    bodies.define(
                            failure, (b, self) -> b.return_(new_(IllegalStateException_.new_String, literal("mine"))));
                    bodies.define(
                            store,
                            (b, self, value) -> b.exec(assignAt(
                                            objects, call(asOrigin.apply(self), Origin_.all), literal(0), value))
                                    .end());
                    bodies.define(
                            caught,
                            (b, self) -> b.tryTerminated(
                                    handlers -> handlers.catch_(IOException_.TOKEN, (c, _) -> c.return_(literal("io")))
                                            .catch_(Exception_.TOKEN, (c, _) -> c.return_(literal("caught"))),
                                    t -> t.throw_(call(asOrigin.apply(self), Origin_.failure))));
                }
            }));

            assertThat(compiled.source())
                    .contains("@Override\n    public String[] all() {")
                    .contains("@Override\n    public IllegalStateException failure() {")
                    .contains("((Origin) this).all()[0] = v0;")
                    .contains("throw ((Origin) this).failure();");
            assertThat(compiled.invoke("caught")).isEqualTo("caught");
        }
    }

    @Nested
    class Rejected {

        @Test
        void aResultThatIsNoSubtypeOfTheResultOfAMethodWithoutAStepIsRejected() {
            // Integer get() for Object get() of Origin, which javac holds the token to — and for
            // String get() of Maker, which no step is of
            classAsJavac(
                    AT_THE_PLACE,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            h.overriding(Origin_.get, Integer_.TOKEN);
                            h.extends_(Maker_.extend(h.self()));
                        }
                    }),
                    "cannot override get() of me.supcheg.javafile.typed.fixtures.Maker: its result is"
                            + " java.lang.String, and that of the method java.lang.Integer: the return types are"
                            + " incompatible (JLS 8.4.8.3)",
                    "extends Maker implements Origin",
                    "@Override public Integer get() { return null; }",
                    "return type java.lang.Integer is not compatible with java.lang.String");
            // the same by the method of an interface: Integer get() of Numbered
            classAsJavac(
                    AT_THE_PLACE,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var sh = h.extends_(Maker_.extend(h.self()));
                            sh.overriding(Numbered_.get);
                        }
                    }),
                    "its result is java.lang.String, and that of the method java.lang.Integer",
                    "extends Maker implements Numbered",
                    "@Override public Integer get() { return null; }",
                    "return type java.lang.Integer is not compatible with java.lang.String");
        }

        @Test
        void aPrimitiveResultIsOfTheVeryTypeAndAVoidOneVoid() {
            // int size() of Counted, long size() of Measured
            classAsJavac(
                    AT_THE_PLACE,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var oh = h.extendsObject();
                            Upcast<Self, Counted> asCounted = oh.implements_(Counted_.implement(h.self()));
                            oh.implements_(Measured_.implement(h.self()));
                            var cb102 = oh.members();
                            var size = cb102.declareOverride(asCounted, Counted_.size);
                            cb102.bodies().define(size, (b, self) -> b.return_(literal(1)));
                        }
                    }),
                    "its result is long, and that of the method int: the return types are incompatible"
                            + " (JLS 8.4.8.3)",
                    "implements Counted, Measured",
                    "@Override public int size() { return 1; }",
                    "return type int is not compatible with long");
            // void reset() of Maker, Object reset() of Resetting
            classAsJavac(
                    AT_THE_PLACE,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var sh = h.extends_(Maker_.extend(h.self()));
                            sh.implements_(Resetting_.implement(h.self()));
                            var ctor7 = sh.declareConstructor();
                            var cb = sh.members();
                            var reset = cb.declareOverride(Maker_.reset);
                            var bodies = cb.bodies(ctor7, ctor -> ctor.super_(Maker_.new_, (b, self) -> b.end()));
                            bodies.define(reset, (b, self) -> b.end());
                        }
                    }),
                    "its result is java.lang.Object, and that of the method void: the return types are"
                            + " incompatible (JLS 8.4.8.3) — a void method overrides a void one alone",
                    "extends Maker implements Resetting",
                    "@Override public void reset() {}",
                    "return type void is not compatible with java.lang.Object");
            classAsJavac(
                    AT_THE_PLACE,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var sh = h.extends_(Maker_.extend(h.self()));
                            Upcast<Self, Resetting> asResetting = sh.implements_(Resetting_.implement(h.self()));
                            var ctor9 = sh.declareConstructor();
                            var cb = sh.members();
                            var reset = cb.declareOverride(asResetting, Resetting_.reset);
                            var bodies = cb.bodies(ctor9, ctor -> ctor.super_(Maker_.new_, (b, self) -> b.end()));
                            bodies.define(reset, (b, self) -> b.return_(literal("")));
                        }
                    }),
                    "its result is void, and that of the method java.lang.Object: the return types are"
                            + " incompatible (JLS 8.4.8.3) — a method that overrides a void one is void",
                    "extends Maker implements Resetting",
                    "@Override public Object reset() { return \"\"; }",
                    "return type java.lang.Object is not compatible with void");
        }
    }

    /// Where the proof refuses what javac takes: it goes by what the facts record and proves
    /// nothing else ([Subtyping]). Listed with the rest in `InheritanceVerdictTest.StricterThanJavac`.
    @Nested
    class StricterThanJavac {

        @Test
        void aWildcardBoundIsMetByTheVeryTypeAlone() {
            // Collection<? extends CharSequence> texts() as List<String> texts()
            classUnlikeJavac(
                    AT_THE_PLACE,
                    () -> file(new TypedJavaFile.TypedClassHeaderSpec() {
                        @Override
                        public <Self> void build(ClassHeader<Self> h) {
                            var sh = h.extends_(Maker_.extend(h.self()));
                            var ctor11 = sh.declareConstructor();
                            var cb = sh.members();
                            var strings = new List_<>(String_.TOKEN).token;
                            var texts = cb.declareOverride(Maker_.texts, strings);
                            var bodies = cb.bodies(ctor11, ctor -> ctor.super_(Maker_.new_, (b, self) -> b.end()));
                            bodies.define(texts, (b, self) -> b.return_(Expressions.literalNull(strings)));
                        }
                    }),
                    "whose type arguments are not known to be contained by those of"
                            + " java.util.Collection<? extends java.lang.CharSequence> (JLS 4.5.1)",
                    "extends Maker",
                    "@Override public List<String> texts() { return null; }");
        }
    }
}
